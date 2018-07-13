package com.nadeem.hindustan.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.nadeem.hindustan.HindustanApplication;
import com.nadeem.hindustan.R;
import com.nadeem.hindustan.database.entities.User;
import com.nadeem.hindustan.interfaces.ViewListner;
import com.nadeem.hindustan.ui.activities.HomeActivity;
import com.nadeem.hindustan.utils.AppPreferences;
import com.nadeem.hindustan.utils.Utils;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ikram on 5/2/18.
 */

public class LoginViewModel extends AndroidViewModel {
    public MutableLiveData<Boolean> showSociaBtn = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ViewListner mViewListner;
    private LiveData<User> mUserLive;
    private User mUser;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        mUserLive = HindustanApplication.getInstance().getDatabase().getUserDao().getLiveUser();
    }

    public LiveData<User> getUserLive() {
        return mUserLive;
    }

    public void setUserLive(LiveData<User> mUserLive) {
        this.mUserLive = mUserLive;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }

    public void setViewListner(ViewListner viewListner) {
        mViewListner = viewListner;
    }

    public void login(View view, String... args) {
        switch (view.getId()) {
            case R.id.btn_login:
                if(mUser==null){
                    Utils.showToast("There is no registered user");
                    return;
                } else if (!Utils.isValidEmail(args[0])) {
                    Utils.showToast("Please enter correct email");
                    return;
                }else if(!args[0].equalsIgnoreCase(mUser.getEmail())){
                    Utils.showToast("Invalid email");
                    return;
                }else if (TextUtils.isEmpty(args[1])) {
                    Utils.showToast("Please enter password");
                    return;
                }else if(!args[1].equals(mUser.getPassword())){
                    Utils.showToast("Incorrect password");
                    return;
                }

                mViewListner.onViewClick(view);
                break;
            case R.id.txt_register_now:
                mViewListner.onViewClick(view);
                break;

        }
    }

    }
