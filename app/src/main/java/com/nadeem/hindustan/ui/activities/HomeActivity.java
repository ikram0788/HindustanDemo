package com.nadeem.hindustan.ui.activities;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nadeem.hindustan.R;
import com.nadeem.hindustan.database.entities.User;
import com.nadeem.hindustan.databinding.ActivityHomeBinding;
import com.nadeem.hindustan.ui.fragments.BaseFragment;
import com.nadeem.hindustan.ui.fragments.FavoriteFragment;
import com.nadeem.hindustan.ui.fragments.HomeFragment;
import com.nadeem.hindustan.ui.fragments.MerchantFragment;
import com.nadeem.hindustan.ui.fragments.ProfileFragment;
import com.nadeem.hindustan.utils.AppPreferences;
import com.nadeem.hindustan.utils.Constants;
import com.nadeem.hindustan.utils.LogReport;
import com.nadeem.hindustan.utils.PermissionUtil;
import com.nadeem.hindustan.viewmodels.NaveItemViewModel;

import dagger.android.AndroidInjection;

import static android.content.pm.PackageManager.*;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityHomeBinding mActivityHomeBinding;
    private NaveItemViewModel naveItemViewModel;
    private TextView navName;
    private TextView navEmail;
    private ImageView navImage;

    /**
     * Id to identify a write permission request.
     */
    private static final int REQUEST_READ_PERMISSION = 101;
    private static final int REQUEST_WRITE_PERMISSION = 102;
    /**
     * Permissions required to read and write contacts. Used by the {@link }.
     */
    private static String[] PERMISSIONS_WRITE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String TAG = HomeActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        naveItemViewModel = ViewModelProviders.of(this).get(NaveItemViewModel.class);
        naveItemViewModel.setViewListner(this);
        navName = mActivityHomeBinding.navView.getHeaderView(0).findViewById(R.id.txt_nave_name);

        navEmail = mActivityHomeBinding.navView.getHeaderView(0).findViewById(R.id.txt_nave_email);

        //navEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_arrow_right), null);

        navImage = mActivityHomeBinding.navView.getHeaderView(0).findViewById(R.id.iv_nave);
        naveItemViewModel.mUser.observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user == null) {
                    navName.setText("Hello Tourist!");
                    navEmail.setText("Please login to your account");
                    //addFragment(com.birdapps.odigostourist.UI.fragments.HireGuideFragment.newInstance(Constants.PageTitle.HireGuideFragment, null), Constants.PageTitle.HireGuideFragment);
                } else {
                    navName.setText(user.getName());
                    navEmail.setText(user.getEmail());
                    AppPreferences.getInstance().setString(AppPreferences.Keys.EMAIL, user.getEmail());

                }
                updateNaigationView(user != null);
            }
        });
        setupActionbar();
        mActivityHomeBinding.navView.setNavigationItemSelectedListener(this);
        addFragment(HomeFragment.newInstance(Constants.PageTitle.HomeFragment, null), Constants.PageTitle.HomeFragment);
        //addFragment(com.birdapps.guidet.UI.fragments.SocialRegisterFragment.newInstance(com.birdapps.guidet.utils.Constants.PageTitle.SocialRegisterFragment, ""), Constants.PageTitle.SocialRegisterFragment);
        mActivityHomeBinding.navView.getHeaderView(0).findViewById(R.id.ll_nav_header).setOnClickListener(naveItemViewModel);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PERMISSION_GRANTED) {
            requestReadWritePermission();
        }
    }

    @Override
    protected void setupActionbar() {
        super.setupActionbar();
        Toolbar toolbar = mActivityHomeBinding.appBarHome.layoutToolbar.toolbar;
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mActivityHomeBinding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mActivityHomeBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        //menu.findItem(R.id.action_search).setVisible(true);
        //menu.findItem(R.id.action_favorite).setVisible(true);
        //menu.findItem(R.id.action_notification).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        /*switch (item.getItemId()) {
            case R.id.action_search:
                break;
            case R.id.action_favorite:
                break;
        }*/

        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Snackbar snackbar;
        if (requestCode == REQUEST_WRITE_PERMISSION) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for camera permission.
            LogReport.i(TAG, "Received response for Camera permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                LogReport.i(TAG, "WRITE permission has now been granted. Showing preview.");
                snackbar = Snackbar.make(mActivityHomeBinding.appbarLayout, R.string.label_write_permission_available,
                        Snackbar.LENGTH_SHORT)
                        .setActionTextColor(getColor(R.color.colorPrimary));
                ;
                snackbar.getView().setBackgroundColor(getColor(R.color.colorAccent));

                snackbar.show();
            } else {
                LogReport.i(TAG, "WRITE permission was NOT granted.");
                snackbar = Snackbar.make(mActivityHomeBinding.appbarLayout, R.string.label_write_permission_not_available,
                        Snackbar.LENGTH_SHORT)
                        .setActionTextColor(getColor(R.color.colorPrimary));
                snackbar.getView().setBackgroundColor(getColor(R.color.colorAccent));
                snackbar.show();

            }
            // END_INCLUDE(permission_result)

        } else if (requestCode == REQUEST_READ_PERMISSION) {
            LogReport.i(TAG, "Received response for contact permissions request.");

            // We have requested multiple permissions for contacts, so all of them need to be
            // checked.
            if (PermissionUtil.verifyPermissions(grantResults)) {
                // All required permissions have been granted, display contacts fragment.
                snackbar = Snackbar.make(mActivityHomeBinding.appbarLayout, R.string.label_read_permission_available,
                        Snackbar.LENGTH_SHORT)
                        .setActionTextColor(getColor(R.color.colorPrimary));
                snackbar.getView().setBackgroundColor(getColor(R.color.colorAccent));

                snackbar.show();
            } else {
                LogReport.i(TAG, "Contacts permissions were NOT granted.");
                snackbar = Snackbar.make(mActivityHomeBinding.appbarLayout, R.string.label_read_permission_not_available,
                        Snackbar.LENGTH_SHORT)
                        .setActionTextColor(getColor(R.color.colorPrimary));
                ;
                snackbar.getView().setBackgroundColor(getColor(R.color.colorAccent));

                snackbar.show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void updateTitle(String pageTitle, String subTitle) {
        super.updateTitle(pageTitle, subTitle);
        if (subTitle != null && !subTitle.equals("")) {
            mActivityHomeBinding.appBarHome.layoutToolbar.subtitleToolbar.setVisibility(View.VISIBLE);
            mActivityHomeBinding.appBarHome.layoutToolbar.subtitleToolbar.setText(subTitle);
        } else
            mActivityHomeBinding.appBarHome.layoutToolbar.subtitleToolbar.setVisibility(View.GONE);
        mActivityHomeBinding.appBarHome.layoutToolbar.titleToolbar.setText(pageTitle);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_home:
                onFragmentInteraction(HomeFragment.newInstance(Constants.PageTitle.HomeFragment, null), Constants.PageTitle.HomeFragment, null);
                break;
            case R.id.nav_add_new:
                onFragmentInteraction(MerchantFragment.newInstance(Constants.PageTitle.MerchantFragment, new Long(0)), Constants.PageTitle.MerchantFragment, null);
                break;
            case R.id.nav_favorite:
                onFragmentInteraction(FavoriteFragment.newInstance(Constants.PageTitle.FavoriteFragment, new Long(0)), Constants.PageTitle.FavoriteFragment, null);
                break;
            case R.id.nav_logout:
                AppPreferences.getInstance().logOut();
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateNaigationView(boolean visible) {

    }

    @Override
    public void onBackPressed() {
        if (mActivityHomeBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mActivityHomeBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(BaseFragment baseFragment, @Constants.PageTitle String fragmentTitle, Object data) {
        super.onFragmentInteraction(baseFragment, fragmentTitle, data);
        addFragmentToBackStack(baseFragment, fragmentTitle.toString());
        setTitle(fragmentTitle);
    }

    /**
     * Requests the Camera permission.
     * If the permission has been denied previously, a SnackBar will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestReadWritePermission() {
        LogReport.i(TAG, "WRITE permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            LogReport.i(TAG,
                    "Displaying write permission rationale to provide additional context.");
            Snackbar snackbar = Snackbar.make(mActivityHomeBinding.appbarLayout, R.string.label_write_permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.actiton_ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(HomeActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_WRITE_PERMISSION);
                        }
                    })
                    .setActionTextColor(getColor(R.color.colorPrimary));
            snackbar.getView().setBackgroundColor(getColor(R.color.colorAccent));

            snackbar.show();
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_PERMISSION);
        }
        // END_INCLUDE(camera_permission_request)
    }

    @Override
    public void onViewClick(View view) {/*
        switch (view.getId()){
            case R.id.ll_nav_container:*/
        onFragmentInteraction(ProfileFragment.newInstance(Constants.PageTitle.ProfileFragment, null), Constants.PageTitle.ProfileFragment, null);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
                /*break;
        }*/
    }
}
