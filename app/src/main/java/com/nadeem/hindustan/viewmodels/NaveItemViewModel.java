package com.nadeem.hindustan.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import com.nadeem.hindustan.HindustanApplication;
import com.nadeem.hindustan.R;
import com.nadeem.hindustan.database.entities.User;
import com.nadeem.hindustan.interfaces.ViewListner;

/**
 * Created by ikram on 20/2/18.
 */

public class NaveItemViewModel extends AndroidViewModel implements View.OnClickListener {
    public LiveData<User> mUser;
    public LiveData<Integer> inboxCount;
    private ViewListner mViewListner;

    public NaveItemViewModel(@NonNull Application application) {
        super(application);
        //mUserLiveData = GuidetApplication.getInstance().getDatabase().userDao().getLiveUser(AppPreferences.getInstance().getString(AppPreferences.Keys.ID));
        mUser = HindustanApplication.getInstance().getDatabase().getUserDao().getLiveUser();

    }

    public LiveData<Integer> getInboxCount() {
        return inboxCount;
    }

    public void setInboxCount(LiveData<Integer> inboxCount) {
        this.inboxCount = inboxCount;
    }

    public void setViewListner(ViewListner viewListner) {
        mViewListner = viewListner;
    }

    @Override
    public void onClick(View view) {
        mViewListner.onViewClick(view);
    }
}
