package com.nadeem.hindustan.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nadeem.hindustan.database.entities.User;

import java.util.List;
@Dao
public interface UserDao {
    @Query("select * from user")
    public List<User> getUserList();
    @Query("select * from user")
    public User getUserData();
    @Query("select * from user")
    public LiveData<User> getUser();

    @Query("select * from user")
    public LiveData<List<User>> getUsers();

    @Query("select * from user where email = :arg0")
    public User getUser(String arg0);

    @Update
    void update(User user);

    @Query("select * from user where email = :arg0")
    public LiveData<User> getLiveUser(String arg0);

    @Query("select * from user")
    public LiveData<User> getLiveUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Query("DELETE FROM user where email = :arg0")
    public void logOutUser(String arg0);
}
