package com.nadeem.hindustan.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.nadeem.hindustan.HindustanApplication;
import com.nadeem.hindustan.R;
import com.nadeem.hindustan.databinding.ActivityLoginBinding;
import com.nadeem.hindustan.utils.AppPreferences;
import com.nadeem.hindustan.viewmodels.LoginViewModel;

import dagger.android.AndroidInjection;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding mActivityLoginBinding;
    private LoginViewModel mLoginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginViewModel.setViewListner(this);

        /* Assign our ViewModel to the layout for DataBinding */
        mActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mActivityLoginBinding.setViewModel(mLoginViewModel);
        Spannable spannable = new SpannableStringBuilder(getString(R.string.label_not_register));
        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), getString(R.string.label_not_register).indexOf("Register Now"), getString(R.string.label_not_register).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mActivityLoginBinding.txtRegisterNow.setText(spannable);
        if(AppPreferences.getInstance().getString(AppPreferences.Keys.EMAIL).length()>0){
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }
        mLoginViewModel.getUserLive().observe(this, user->{
            mLoginViewModel.setUser(user);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(HindustanApplication.getInstance().getDatabase().getUserDao().getUserList().size()>0)
            mActivityLoginBinding.txtRegisterNow.setVisibility(View.GONE);
    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Intent homeIntent = new Intent(view.getContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.txt_register_now:
                Intent registrationIntent = new Intent(view.getContext(), RegistrationActivity.class);
                startActivity(registrationIntent);
                finish();
                break;
        }
    }
}
