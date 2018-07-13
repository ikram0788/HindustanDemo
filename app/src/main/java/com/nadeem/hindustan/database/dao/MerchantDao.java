package com.nadeem.hindustan.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nadeem.hindustan.database.entities.Merchant;
import com.nadeem.hindustan.database.entities.User;

import java.util.List;

@Dao
public interface MerchantDao {
    @Query("select * from merchant")
    public List<Merchant> getMerchantsList();
    @Query("select * from merchant where isFavorite = :arg0")
    public DataSource.Factory<Integer, Merchant> getFavoriteMerchantFactory(int arg0);
    @Query("select * from merchant")
    public DataSource.Factory<Integer, Merchant> getMerchantFactory();
    @Query("select * from merchant where billNo LIKE :arg0 OR name LIKE :arg0")
    public DataSource.Factory<Integer, Merchant> getQueriedMerchantFactory(String arg0);

    @Query("select * from merchant")
    public LiveData<List<Merchant>> getMerchants();

    @Query("select * from merchant where billNo = :arg0")
    public Merchant getMerchant(long arg0);

    @Insert
    long addMerchant(Merchant user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMerchant(Merchant merchant);

    @Update
    int updateMerchant(Merchant user);

    @Query("select * from merchant where id = :arg0")
    public LiveData<Merchant> getLiveMerchant(long arg0);

    @Query("select * from merchant")
    public LiveData<Merchant> getLiveMerchant();

    @Delete
    int deleteMerchant(Merchant merchant);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Merchant> merchant);

}
