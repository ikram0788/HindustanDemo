package com.nadeem.hindustan.di;

import com.nadeem.hindustan.ui.activities.HomeActivity;
import com.nadeem.hindustan.ui.activities.LoginActivity;
import com.nadeem.hindustan.ui.activities.RegistrationActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by ikram on 7/03/2018.
 */
@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract HomeActivity homeActivity();
    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract LoginActivity loginActivity();
    @ContributesAndroidInjector
    abstract RegistrationActivity registrationActivity();
}
