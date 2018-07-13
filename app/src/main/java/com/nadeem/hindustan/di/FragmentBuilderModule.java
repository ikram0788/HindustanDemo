package com.nadeem.hindustan.di;

import com.nadeem.hindustan.ui.fragments.FavoriteFragment;
import com.nadeem.hindustan.ui.fragments.HomeFragment;
import com.nadeem.hindustan.ui.fragments.MerchantFragment;
import com.nadeem.hindustan.ui.fragments.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by ikram on 7/03/2018.
 */
@Module
public abstract class FragmentBuilderModule {
/*
    @ContributesAndroidInjector
    abstract BaseFragment contributeBaseFragment();*/
    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();
    @ContributesAndroidInjector
    abstract FavoriteFragment contributeFavoriteFragment();
    @ContributesAndroidInjector
    abstract MerchantFragment contributeMerchantFragment();
    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();
}
