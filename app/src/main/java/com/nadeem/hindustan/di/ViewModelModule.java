package com.nadeem.hindustan.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.nadeem.hindustan.viewmodels.FavoriteViewModel;
import com.nadeem.hindustan.viewmodels.HomeViewModel;
import com.nadeem.hindustan.viewmodels.MerchantViewModel;
import com.nadeem.hindustan.viewmodels.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by ikram on 7/3/18.
 */

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindsLoginFragViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel.class)
    abstract ViewModel bindsFavoriteViewModel(FavoriteViewModel favoriteViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MerchantViewModel.class)
    abstract ViewModel bindsMerchantFragmentViewModel(MerchantViewModel merchantViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel bindsProfileFragmentViewModel(ProfileViewModel merchantViewModel);
    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);
}
