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
import com.nadeem.hindustan.databinding.FragmentHomeBinding;
import com.nadeem.hindustan.utils.AppPreferences;
import com.nadeem.hindustan.utils.Constants;
import com.nadeem.hindustan.utils.Utils;
import com.nadeem.hindustan.viewmodels.HomeViewModel;

import static com.nadeem.hindustan.utils.Constants.ARG_TITLE;


/**
 * A simple {@link BaseFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> {

    private String mPageTitle;
    private String TAG = HomeFragment.class.getSimpleName();

    public HomeFragment() {
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
    public static HomeFragment newInstance(String title, Object orderItem) {
        HomeFragment fragment = new HomeFragment();
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
        initMerchantAdapter("");
    }
    private void initMerchantAdapter(String queryString){
        viewModelObject.initQueriedMerchantList(queryString.trim());
        viewModelObject.getmMerchant().observe(this, merchants -> {
            if ((RecyclerPagedAdapter) dataBinding.rvMerchant.getAdapter() != null)
                ((RecyclerPagedAdapter) dataBinding.rvMerchant.getAdapter()).setPagedList(merchants);
        });
    }
    @Override
    public Class<HomeViewModel> getViewModel() {
        return HomeViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(true);
        //SearchView searchView = new SearchView(((HomeActivity)getContext()).getSupportActionBar().getThemedContext());
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        ((ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn)).setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        ((ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon)).setColorFilter(Color.parseColor("#edecec"), PorterDuff.Mode.SRC_ATOP);
        ((ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_go_btn)).setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        ((ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_button)).setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        EditText editText = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
                editText.setTextColor(getResources().getColor(R.color.colorTextPrimary, null));
        editText.setHintTextColor(getResources().getColor(R.color.colorGray, null));
        editText.setHint(getResources().getString(R.string.hint_search));
        searchView.setSubmitButtonEnabled(false);
        MenuItemCompat.setOnActionExpandListener(search,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        initMerchantAdapter("");
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true; // Return true to expand action view
                    }
                });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                initMerchantAdapter(newText);
                return false;
            }
        });


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

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_favorite:
                                ((Merchant)data).setIsFavorite(1);
                                HindustanApplication.getInstance().getDatabase().getMerchantDao().updateMerchant((Merchant)data);
                                Utils.showToast("Added in favorite list");
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
