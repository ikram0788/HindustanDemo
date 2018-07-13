package com.nadeem.hindustan;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
//import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import com.nadeem.hindustan.database.AppDatabase;
import com.nadeem.hindustan.database.AppExecutors;
import com.nadeem.hindustan.di.DaggerAppComponent;
import com.nadeem.hindustan.utils.AppPreferences;
import com.nadeem.hindustan.utils.LogReport;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by ikram on 8/2/18.
 */

public class HindustanApplication extends Application implements HasActivityInjector {
    private static HindustanApplication instance;
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingInjector;
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    private AppExecutors mAppExecutors;

    public static synchronized HindustanApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
        instance = this;
        mAppExecutors = new AppExecutors();
        AppPreferences.initialize(this);

    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    private void initializeComponent() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingInjector;
    }

    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
