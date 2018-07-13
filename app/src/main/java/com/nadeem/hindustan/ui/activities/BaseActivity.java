package com.nadeem.hindustan.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MotionEvent;
import android.view.View;
import com.nadeem.hindustan.R;
import com.nadeem.hindustan.interfaces.ViewListner;
import com.nadeem.hindustan.ui.fragments.BaseFragment;
import com.nadeem.hindustan.utils.AppPreferences;
import com.nadeem.hindustan.utils.Constants;
import com.nadeem.hindustan.utils.Utils;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by ikram on 31/1/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener, ViewListner, HasSupportFragmentInjector {
    public static boolean inboxAndChatFlag = false;
    protected AppPreferences appPreferences;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentAndroidInjector;
    private Handler mHandler;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        appPreferences = AppPreferences.getInstance();
        mHandler = new Handler();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setMessage("Loading...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorAccent));
        }

    }

    public void addFragment(BaseFragment fragment, String s) {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, fragment, s);
        transaction.commit();
        //getSupportFragmentManager().executePendingTransactions();
        //getFragmentManager().executePendingTransactions();
    }

    public void addFragmentToBackStack(final BaseFragment fragment, final String tag) {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(tag);

                /** Update to SupportLibraryVersion 27.1.1 to fix fragment transition flickering issues
                 * Until updated, use custom animators as an alternative fix
                 */
                //transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                transaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
                transaction.replace(R.id.home_container, fragment, tag);
                transaction.commit();
                //fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                //fragmentTransaction.commitAllowingStateLoss();
            }
        };
// If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }

    protected void startActivity(BaseActivity baseActivity, Bundle bundle, boolean finishCurrent) {
        Intent intent = new Intent(baseActivity, baseActivity.getClass());
        intent.putExtras(bundle);
        startActivity(intent);
        if (finishCurrent)
            baseActivity.finish();
    }

    @Override
    public void onFragmentInteraction(BaseFragment baseFragment, @Constants.PageTitle String fragmentTitle, Object data) {

    }

    @Override
    public void updateTitle(String pageTitle, String subTitle) {
    }

    @Override
    public void updateContent(int position, Object data) {

    }

    public void showProgress(boolean show) {
        if (show)
            mProgressDialog.show();
        else
            mProgressDialog.dismiss();


//        if (mProgressDialog.isShowing() && !show)
//            mProgressDialog.dismiss();
//        else
//            mProgressDialog.show();
    }

    @Override
    public void onViewClick(View view) {

    }

    @Override
    public void sendData(View view,String type, Object data) {

    }

    @Override
    public void setLoading(boolean enableLoading) {
        showProgress(enableLoading);
    }

    public void setProgressCancelable(boolean cancelable) {
        mProgressDialog.setCancelable(cancelable);
    }

    protected void setupActionbar() {

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentAndroidInjector;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Utils.hideSoftKeybord(this);
        return super.dispatchTouchEvent(ev);
    }
}
