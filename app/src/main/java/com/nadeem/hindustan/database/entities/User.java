package com.nadeem.hindustan.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

@Entity(tableName = "user",primaryKeys = {"email"})
public class User {
    private String id;
    @NonNull
    private String email;
    private String name;
    private String mobile;
    private String profilePic;
    private String password;
    public User(){

    }
    @Ignore
    public User(String email,String name,String mobile,String password){
        this.email=email;
        this.name=name;
        this.mobile=mobile;
        this.password=password;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
