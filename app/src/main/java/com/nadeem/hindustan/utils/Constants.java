package com.nadeem.hindustan.utils;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * Created by ikram on 29/1/18.
 */

public interface Constants {
    //Request codes
    static int REQUEST_CODE_LOGIN = 1001;
    static int REQUEST_CODE_GUIDE_DETAIL = 1002;
    // page titles
    String ARG_TITLE = "PAGE_TITLE";
    String ARG_ITEM = "PAGE_ITEM";

    // Declare the @IntDef for these constants
    @StringDef({PageTitle.LoginFragment, PageTitle.RegistrationFragment,
            PageTitle.MerchantFragment, PageTitle.HomeFragment,
            PageTitle.HireGuideFragment, PageTitle.GuideListFragment,
            PageTitle.ChangePassFragment, PageTitle.MyTripFragment,
            PageTitle.ProfileFragment,PageTitle.FavoriteFragment
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface PageTitle {
        String LoginFragment = "LOGIN";
        String RegistrationFragment = " LOGIN ";
        String MerchantFragment = "Merchant";
        String HomeFragment = " Home ";
        String HireGuideFragment = "Hire a Guide";
        String GuideListFragment = "Select Guide";
        String ChangePassFragment = " Change Password";
        String ProfileFragment = "Profile";
        String FavoriteFragment = "Favorite";
        //Trips
        String MyTripFragment = "My Trips";

    }
}
