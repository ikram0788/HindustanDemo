package com.nadeem.hindustan.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.nadeem.hindustan.HindustanApplication;
import com.nadeem.hindustan.database.AppDatabase;
import com.nadeem.hindustan.database.entities.Merchant;
import com.nadeem.hindustan.database.entities.User;
import com.nadeem.hindustan.handlers.ClickHandler;
import com.nadeem.hindustan.handlers.LongClickHandler;
import com.nadeem.hindustan.handlers.RecyclerChieldClickHandler;
import com.nadeem.hindustan.interfaces.ViewListner;
import com.nadeem.hindustan.utils.AppPreferences;
import com.nadeem.hindustan.utils.Utils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ikram on 7/2/18.
 */

public class MerchantViewModel extends ViewModel implements View.OnClickListener {
    public LiveData<Merchant> mMerchant = new MutableLiveData<>();
    private AppDatabase database;
    private ViewListner mViewListner;
    private long merchantId;
    private LiveData<List<Merchant>> merchantListLiveData;

    @Inject
    public MerchantViewModel(AppDatabase database) {
        this.database = database;
    }
    public void initMerchantLiveData(){
        merchantListLiveData = database.getMerchantDao().getMerchants();
    }
    public void setViewListner(ViewListner viewListner) {
        mViewListner = viewListner;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
        mMerchant = database.getMerchantDao().getLiveMerchant(merchantId);
    }

    public LiveData<Merchant> getmMerchant() {
        return mMerchant;
    }

    public void setmMerchant(LiveData<Merchant> mMerchant) {
        this.mMerchant = mMerchant;
    }

    public LiveData<List<Merchant>> getMerchantListLiveData() {
        return merchantListLiveData;
    }

    public void setMerchantListLiveData(LiveData<List<Merchant>> merchantListLiveData) {
        this.merchantListLiveData = merchantListLiveData;
    }

    public void save(View view, String... values) {
        long rowId = 0;
        if (values[0].equals("") || values[1].equals("") || values[2].equals("") || values[3].equals("") || values[4].equals("") || values[5].equals("")) {
            Utils.showToast("All fields required");
            return;
        }
        if (merchantId > 0)
            rowId = database.getMerchantDao().updateMerchant(new Merchant(merchantId, values[0], values[1], values[2], Double.parseDouble(values[3]), Double.parseDouble(values[4]), Long.parseLong(values[5]), Long.parseLong(values[6])));
        else
            rowId = database.getMerchantDao().addMerchant(new Merchant(values[0], values[1], values[2], Double.parseDouble(values[3]), Double.parseDouble(values[4]), Long.parseLong(values[5]), Long.parseLong(values[6])));
        if (rowId > 0) {
            mViewListner.sendData(null, "MERCHAND_ADDED", "CLEAR");
        }
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
            return (T) new MerchantViewModel(mAppDatabase);
        }
    }
}
