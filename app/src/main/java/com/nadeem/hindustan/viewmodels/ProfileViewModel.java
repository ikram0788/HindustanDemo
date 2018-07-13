package com.nadeem.hindustan.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.view.View;

import com.nadeem.hindustan.HindustanApplication;
import com.nadeem.hindustan.database.AppDatabase;
import com.nadeem.hindustan.database.entities.Merchant;
import com.nadeem.hindustan.database.entities.User;
import com.nadeem.hindustan.interfaces.ViewListner;
import com.nadeem.hindustan.utils.Utils;

import javax.inject.Inject;

/**
 * Created by ikram on 7/2/18.
 */

public class ProfileViewModel extends ViewModel implements View.OnClickListener {
    public LiveData<User> mUser = new MutableLiveData<>();
    private AppDatabase database;
    private ViewListner mViewListner;

    @Inject
    public ProfileViewModel(AppDatabase database) {
        this.database = database;
        mUser = database.getUserDao().getUser();
    }

    public void setViewListner(ViewListner viewListner) {
        mViewListner = viewListner;
    }

    public void save(View view, String... values) {

    }

    @Override
    public void onClick(View view) {
        mViewListner.onViewClick(view);
    }

    /**
     * A creator is used to inject the product ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final HindustanApplication mApplication;

        private final AppDatabase mAppDatabase;

        public Factory(@NonNull HindustanApplication application) {
            mApplication = application;
            mAppDatabase = application.getDatabase();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ProfileViewModel(mAppDatabase);
        }
    }
}
