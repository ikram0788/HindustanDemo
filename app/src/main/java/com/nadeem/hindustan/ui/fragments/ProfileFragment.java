package com.nadeem.hindustan.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.nadeem.hindustan.HindustanApplication;
import com.nadeem.hindustan.R;
import com.nadeem.hindustan.database.entities.Merchant;
import com.nadeem.hindustan.databinding.FragmentMerchantBinding;
import com.nadeem.hindustan.databinding.FragmentProfileBinding;
import com.nadeem.hindustan.manager.XlsWorkManager;
import com.nadeem.hindustan.utils.AppPreferences;
import com.nadeem.hindustan.utils.FileChooser;
import com.nadeem.hindustan.utils.LogReport;
import com.nadeem.hindustan.utils.Utils;
import com.nadeem.hindustan.viewmodels.MerchantViewModel;
import com.nadeem.hindustan.viewmodels.ProfileViewModel;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import static com.nadeem.hindustan.utils.Constants.ARG_ITEM;
import static com.nadeem.hindustan.utils.Constants.ARG_TITLE;


/**
 * A simple {@link BaseFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends BaseFragment<ProfileViewModel, FragmentProfileBinding> implements FileChooser.FileSelectedListener {

    private String mPageTitle;
    private String TAG = ProfileFragment.class.getSimpleName();
    private WorkManager mWorkManager;
    private OneTimeWorkRequest exportData;
    private int REQUEST_CODE_DOC=201;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title      Parameter 1.
     * @param merchantId Parameter 2.
     * @return A new instance of fragment OrderDetailFragment.
     */
    public static ProfileFragment newInstance(String title, Object merchantId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPageTitle = getArguments().getString(ARG_TITLE);
        viewModelObject.setViewListner(this);
        viewModelObject.mUser.observe(this, user -> {
            dataBinding.edtEmail.setText(user.getEmail());
            dataBinding.edtEmail.setEnabled(false);
            dataBinding.edtName.setText(user.getName());
            dataBinding.edtMobile.setText(user.getMobile());
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mWorkManager = WorkManager.getInstance();

    }

    @Override
    public Class<ProfileViewModel> getViewModel() {
        return ProfileViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        final MenuItem menuItem = menu.findItem(R.id.action_import_export);
        menuItem.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        HindustanApplication.getInstance().getDatabase().getUserDao().getUser(AppPreferences.getInstance().getString(AppPreferences.Keys.ID));
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.action_import_export:
                showImportExportDialog("", "");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_DOC && resultCode== Activity.RESULT_OK){
            Uri fileUri = data.getData();
            createWork(false,fileUri.getPath());
        }

    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null)
            mListener.updateTitle(mPageTitle, null);
    }

    @Override
    public void sendData(View view, String type, Object data) {
        switch (type) {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void fileSelected(File file) {

    }

    private void showImportExportDialog(String type, Object data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.title_make_record_backup));
        builder.setMessage(getString(R.string.message_make_record_backup));
        builder.setCancelable(false);

        String positiveText = getString(R.string.action_export);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createWork(true,"");
                    }
                });

        String negativeText = getString(R.string.action_import);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //createWork(false);
                        String[] mimeTypes =
                                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                                        "text/plain",
                                        "application/pdf",
                                        "application/zip","application/x-excel"};
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                            if (mimeTypes.length > 0) {
                                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                            }
                        } else {
                            String mimeTypesStr = "";
                            for (String mimeType : mimeTypes) {
                                mimeTypesStr += mimeType + "|";
                            }
                            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
                        }
                        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), REQUEST_CODE_DOC);

                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    /***
     * Method to create Periodic Work
     * Minimum  Time interval is 900000 milli seconds
     */
    private void createWork(boolean isExport,String filePath) {
        Data data = new Data.Builder()
                .putBoolean("IS_EXPORT",isExport)
                .putString("FILE_PATH", filePath)
                .build();
        exportData =
                new OneTimeWorkRequest.Builder(XlsWorkManager.class)
                        .setInputData(data)
                        .build();
        mWorkManager.enqueue(exportData);
        mWorkManager.getStatusById(exportData.getId()).observe(this, workStatus -> {
            if (workStatus != null && workStatus.getState().isFinished()) {
                LogReport.i(TAG,""+workStatus.getOutputData().getInt("STATUS",0));
                Utils.showToast("File exported");
            }
        });


    }

}
