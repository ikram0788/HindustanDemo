package com.nadeem.hindustan.ui.fragments;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nadeem.hindustan.BR;
import com.nadeem.hindustan.interfaces.ViewListner;
import com.nadeem.hindustan.ui.activities.BaseActivity;
import com.nadeem.hindustan.utils.AppPreferences;
import com.nadeem.hindustan.utils.Constants;
import com.nadeem.hindustan.utils.LogReport;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by ikram on 31/1/18.
 */

public abstract class BaseFragment<VM extends ViewModel, DB extends ViewDataBinding> extends Fragment implements ViewListner, LifecycleRegistryOwner {
    public VM viewModelObject;
    public DB dataBinding;
    protected OnFragmentInteractionListener mListener;
    protected AppPreferences preferences;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @LayoutRes
    public abstract int getLayoutRes();

    public abstract Class<VM> getViewModel();

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
       // AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        viewModelObject = ViewModelProviders.of(this, viewModelFactory).get(getViewModel());
        preferences = AppPreferences.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        dataBinding.setVariable(BR.viewModel, viewModelObject);
        return dataBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
           /* throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener"); */
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateContent(int position, Object data) {
    }
    // view listner methods


    @Override
    public void onViewClick(View view) {

    }

    @Override
    public void sendData(View view,String type, Object data) {

    }

    @Override
    public void setLoading(boolean enableLoading) {
        try {
            ((BaseActivity) getActivity()).showProgress(enableLoading);
        } catch (Throwable th) {
            LogReport.e("enableLoading", th.getMessage());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(BaseFragment baseFragment, @Constants.PageTitle String fragmentTitle, Object data);

        void updateTitle(String pageTitle, String subtitle);

        void updateContent(int position, Object data);
    }

}
