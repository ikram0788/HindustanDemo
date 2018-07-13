package com.nadeem.hindustan.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.nadeem.hindustan.HindustanApplication;
import com.nadeem.hindustan.R;
import com.nadeem.hindustan.adapters.RecyclerPagedAdapter;
import com.nadeem.hindustan.database.entities.Merchant;
import com.nadeem.hindustan.databinding.FragmentFavoriteBinding;
import com.nadeem.hindustan.databinding.FragmentHomeBinding;
import com.nadeem.hindustan.utils.AppPreferences;
import com.nadeem.hindustan.utils.Constants;
import com.nadeem.hindustan.utils.Utils;
import com.nadeem.hindustan.viewmodels.FavoriteViewModel;
import com.nadeem.hindustan.viewmodels.HomeViewModel;

import static com.nadeem.hindustan.utils.Constants.ARG_TITLE;


/**
 * A simple {@link BaseFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends BaseFragment<FavoriteViewModel, FragmentFavoriteBinding> {

    private String mPageTitle;
    private String TAG = FavoriteFragment.class.getSimpleName();

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title     Parameter 1.
     * @param orderItem Parameter 2.
     * @return A new instance of fragment OrderDetailFragment.
     */
    public static FavoriteFragment newInstance(String title, Object orderItem) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        //args.putParcelable(ARG_ITEM, orderItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPageTitle = getArguments().getString(ARG_TITLE);

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
        viewModelObject.setViewListner(this);
        dataBinding.rvMerchant.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        viewModelObject.getmMerchant().observe(this, merchants -> {
            if ((RecyclerPagedAdapter) dataBinding.rvMerchant.getAdapter() != null)
                ((RecyclerPagedAdapter) dataBinding.rvMerchant.getAdapter()).setPagedList(merchants);
        });
    }

    @Override
    public Class<FavoriteViewModel> getViewModel() {
        return FavoriteViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_favorite;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


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
            case "MENU_OPTION_CLICKED":
                Merchant merchant = (Merchant) data;
                PopupMenu popup = new PopupMenu(getContext(), view);
                popup.getMenuInflater().inflate(R.menu.menu_card, popup.getMenu());
                popup.getMenu().findItem(R.id.menu_favorite).setVisible(true);
                popup.getMenu().findItem(R.id.menu_remove_favorite).setVisible(true);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_remove_favorite:
                                ((Merchant)data).setIsFavorite(0);
                                HindustanApplication.getInstance().getDatabase().getMerchantDao().updateMerchant((Merchant)data);
                                Utils.showToast("Removed from favorite list");
                                break;
                            case R.id.menu_edit:
                                mListener.onFragmentInteraction(MerchantFragment.newInstance(Constants.PageTitle.MerchantFragment, new Long(merchant.getId())), Constants.PageTitle.MerchantFragment, null);
                                break;
                            case R.id.menu_delete:
                                showConfirmationDialog("DELETE", data);
                                break;
                        }
                        return true;
                    }
                });

                popup.show();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void showConfirmationDialog(String type, Object data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.label_dialog_warning));
        builder.setMessage(getString(R.string.label_dialog_message));
        builder.setCancelable(false);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (viewModelObject.removeMerchant((Merchant) data) > 0)
                            Utils.showToast("Record deleted");
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }
}
