package com.nadeem.hindustan.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.view.View;

import com.nadeem.hindustan.BR;
import com.nadeem.hindustan.HindustanApplication;
import com.nadeem.hindustan.R;
import com.nadeem.hindustan.adapters.binders.CompositeItemBinder;
import com.nadeem.hindustan.adapters.binders.ConditionalDataBinder;
import com.nadeem.hindustan.adapters.binders.ItemBinder;
import com.nadeem.hindustan.database.AppDatabase;
import com.nadeem.hindustan.database.entities.Merchant;
import com.nadeem.hindustan.database.entities.User;
import com.nadeem.hindustan.handlers.ClickHandler;
import com.nadeem.hindustan.handlers.LongClickHandler;
import com.nadeem.hindustan.handlers.RecyclerChieldClickHandler;
import com.nadeem.hindustan.interfaces.ViewListner;

import javax.inject.Inject;

/**
 * Created by ikram on 7/2/18.
 */

public class HomeViewModel extends ViewModel {
    public MutableLiveData<User> mUser = new MutableLiveData<>();
    public LiveData<PagedList<Merchant>> mMerchant = new MutableLiveData<>();
    private AppDatabase database;
    private ViewListner mViewListner;
    private static int PASE_SIZE = 20;

    @Inject
    public HomeViewModel(AppDatabase database) {
        this.database = database;
        }
    public void initQueriedMerchantList(String query){
        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(PASE_SIZE)
                .setInitialLoadSizeHint(50)
                .setPrefetchDistance(10)
                .setPageSize(PASE_SIZE)
                .setEnablePlaceholders(false)
                .build();
        if(query.length()==0)
        mMerchant = new LivePagedListBuilder(database.getMerchantDao().getMerchantFactory(), config).build();
        else
            mMerchant = new LivePagedListBuilder(database.getMerchantDao().getQueriedMerchantFactory(query+"%"), config).build();

    }
    public void setViewListner(ViewListner viewListner) {
        mViewListner = viewListner;
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
            return (T) new HomeViewModel(mAppDatabase);
        }
    }
    public int removeMerchant(Merchant merchant){
        return database.getMerchantDao().deleteMerchant(merchant);
    }
    public MutableLiveData<User> getmUser() {
        return mUser;
    }

    public void setmUser(MutableLiveData<User> mUser) {
        this.mUser = mUser;
    }

    public LiveData<PagedList<Merchant>> getmMerchant() {
        return mMerchant;
    }

    public void setmMerchant(LiveData<PagedList<Merchant>> mMerchant) {
        this.mMerchant = mMerchant;
    }

    public <T> ItemBinder<T> itemViewBinder() {

        return new CompositeItemBinder<T>(new ConditionalDataBinder<T>(BR.model,
                R.layout.item_list_merchant) {
            @Override
            public boolean canHandle(T model) {
                return true;
            }
        });

    }

    public <T> ClickHandler<T> clickHandler() {
        return new ClickHandler<T>() {
            @Override
            public void onClick(T item, View view) {
            }


        };
    }

    public <T> LongClickHandler<T> longClickHandler() {
        return new LongClickHandler<T>() {
            @Override
            public void onLongClick(T item) {

            }
        };
    }

    public <T> RecyclerChieldClickHandler<T> chieldClickHandler() {
        return new RecyclerChieldClickHandler<T>() {
            @Override
            public void onClick(T viewModel, View v, int position) {
                mViewListner.sendData(v, "MENU_OPTION_CLICKED", viewModel);
            }
        };
    }
}
