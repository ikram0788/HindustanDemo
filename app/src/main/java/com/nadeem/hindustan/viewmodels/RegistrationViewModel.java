package com.nadeem.hindustan.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.nadeem.hindustan.HindustanApplication;
import com.nadeem.hindustan.R;
import com.nadeem.hindustan.database.AppDatabase;
import com.nadeem.hindustan.database.entities.User;
import com.nadeem.hindustan.interfaces.ViewListner;
import com.nadeem.hindustan.utils.AppPreferences;
import com.nadeem.hindustan.utils.Utils;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ikram on 5/2/18.
 */

public class RegistrationViewModel extends AndroidViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ViewListner mViewListner;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }

    public void setViewListner(ViewListner viewListner) {
        mViewListner = viewListner;
    }

    public void login(View view, String... args) {
        switch (view.getId()) {
            case R.id.btn_signup:
                if (!Utils.isValidEmail(args[0])) {
                    Utils.showToast("Please enter correct email");
                    return;
                } else if (TextUtils.isEmpty(args[1])) {
                    Utils.showToast("Please enter your name");
                    return;
                } else if (args[2].length() < 10) {
                    Utils.showToast("Please enter correct mobile no.");
                    return;
                } else if (args[3].length() < 6) {
                    Utils.showToast("Please enter password having min length.");
                    return;
                } else if (!args[4].equals(args[3])) {
                    Utils.showToast("Passwords do not match in both password field.");
                    return;
                }
                long rowCount = HindustanApplication.getInstance().getDatabase().getUserDao().insertUser(new User(args[0], args[1], args[2], args[3]));
                if (rowCount > 0) {
                    AppPreferences.getInstance().setString(AppPreferences.Keys.EMAIL, args[0]);
                    AppPreferences.getInstance().setString(AppPreferences.Keys.NAME, args[1]);
                    AppPreferences.getInstance().setString(AppPreferences.Keys.MOBILE, args[2]);
                    AppPreferences.getInstance().setString(AppPreferences.Keys.PASSWORD, args[3]);
                    mViewListner.onViewClick(view);
                } else {
                    Utils.showToast("There is some error while registration");
                }
                break;
            case R.id.txt_already_registered:
                mViewListner.onViewClick(view);
                break;
        }

    }

}
