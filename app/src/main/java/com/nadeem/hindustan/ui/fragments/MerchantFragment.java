package com.nadeem.hindustan.ui.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.nadeem.hindustan.HindustanApplication;
import com.nadeem.hindustan.R;
import com.nadeem.hindustan.database.entities.Merchant;
import com.nadeem.hindustan.databinding.FragmentMerchantBinding;
import com.nadeem.hindustan.utils.AppPreferences;
import com.nadeem.hindustan.utils.Utils;
import com.nadeem.hindustan.viewmodels.MerchantViewModel;

import java.util.Calendar;

import static com.nadeem.hindustan.utils.Constants.ARG_ITEM;
import static com.nadeem.hindustan.utils.Constants.ARG_TITLE;


/**
 * A simple {@link BaseFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MerchantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MerchantFragment extends BaseFragment<MerchantViewModel, FragmentMerchantBinding> {

    private String mPageTitle;
    private String TAG = MerchantFragment.class.getSimpleName();

    public MerchantFragment() {
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
    public static MerchantFragment newInstance(String title, Object merchantId) {
        MerchantFragment fragment = new MerchantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putLong(ARG_ITEM, ((Long) merchantId).longValue());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPageTitle = getArguments().getString(ARG_TITLE);
        viewModelObject.setMerchantId(getArguments().getLong(ARG_ITEM, 0));
        viewModelObject.setViewListner(this);
        if (getArguments().getLong(ARG_ITEM, 0) > 0)
            viewModelObject.mMerchant.observe(this, merchant -> {
                dataBinding.edtBillNo.setText("" + merchant.getBillNo());
                dataBinding.edtBillNo.setEnabled(false);
                dataBinding.edtName.setText(merchant.getName());
                dataBinding.edtMobile.setText(merchant.getMobile());
                dataBinding.edtCredit.setText("" + merchant.getCredit());
                dataBinding.edtDebit.setText("" + merchant.getDebit());
                dataBinding.edtCreditDate.setText(Utils.parseDate("" + merchant.getCreditDate(),11));
                dataBinding.edtDebitDate.setText(Utils.parseDate("" + merchant.getDebitDate(),11));
            });
        else {
            viewModelObject.initMerchantLiveData();
            viewModelObject.getMerchantListLiveData().observe(this,merchantList -> {
                ArrayAdapter<Merchant> adapter = new ArrayAdapter<Merchant>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, merchantList);

                dataBinding.edtName.setAdapter(adapter);

            });
        }
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        mHomeViewModel.liveData.setValue(getItemList());
        binding.rvTrending.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.setViewModel(mHomeViewModel);
        binding.setView(this);
        //binding.setViewModel(((LoginActivity)getActivity()).getLoginViewModel());

        View view = binding.getRoot();

        return view;
    }
*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public Class<MerchantViewModel> getViewModel() {
        return MerchantViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_merchant;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        HindustanApplication.getInstance().getDatabase().getUserDao().getUser(AppPreferences.getInstance().getString(AppPreferences.Keys.ID));
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.action_search:
                break;
            case R.id.action_add_favorite:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void onViewClick(View view) {
        Calendar calendar = null;
        DatePickerDialog dialog = null;
        switch (view.getId()) {
            case R.id.edt_credit_date:
                calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(getContext(), R.style.DialogWithBG, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String monthString = (monthOfYear + 1) / 10 == 0 ? "0" + (monthOfYear + 1) : "" + (monthOfYear + 1);
                        dataBinding.edtCreditDate.setTag(year + "" + monthString + "" + dayOfMonth);
                        dataBinding.edtCreditDate.setText(Utils.parseDate(dataBinding.edtCreditDate.getTag().toString(),11));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                //dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                dialog.show();
                break;
            case R.id.edt_debit_date:
                calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(getContext(), R.style.DialogWithBG, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String monthString = (monthOfYear + 1) / 10 == 0 ? "0" + (monthOfYear + 1) : "" + (monthOfYear + 1);
                        dataBinding.edtDebitDate.setTag(year + "" + monthString + "" + dayOfMonth);
                        dataBinding.edtDebitDate.setText(Utils.parseDate(dataBinding.edtDebitDate.getTag().toString(),11));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                //dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                dialog.show();
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
            case "MERCHAND_ADDED":
                if(getArguments().getLong(ARG_ITEM,0)>0) {
                    Utils.showToast("Record Updated");
                }else{
                    dataBinding.edtBillNo.setText("");
                    dataBinding.edtName.setText("");
                    dataBinding.edtMobile.setText("");
                    dataBinding.edtCredit.setText("");
                    dataBinding.edtDebit.setText("");
                    dataBinding.edtCreditDate.setText("");
                    dataBinding.edtDebitDate.setText("");
                    Utils.showToast("Record added in DB");
                }
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
