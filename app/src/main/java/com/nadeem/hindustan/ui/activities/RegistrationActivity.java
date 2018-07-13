package com.nadeem.hindustan.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.nadeem.hindustan.R;
import com.nadeem.hindustan.databinding.ActivityRegistrationBinding;
import com.nadeem.hindustan.utils.Utils;
import com.nadeem.hindustan.viewmodels.RegistrationViewModel;

import dagger.android.AndroidInjection;

public class RegistrationActivity extends BaseActivity {

    private ActivityRegistrationBinding mActivityRegistrationBinding;
    private RegistrationViewModel mRegistrationViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mRegistrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        mRegistrationViewModel.setViewListner(this);

        /* Assign our ViewModel to the layout for DataBinding */
        mActivityRegistrationBinding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        mActivityRegistrationBinding.setViewModel(mRegistrationViewModel);

        Spannable spannable = new SpannableStringBuilder(getString(R.string.label_already_register));
        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), getString(R.string.label_already_register).indexOf("Login"), getString(R.string.label_already_register).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mActivityRegistrationBinding.txtAlreadyRegistered.setText(spannable);

    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                Utils.showToast("Registered Successfully");
                Intent homeIntent = new Intent(view.getContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.txt_already_registered:
                Intent loginIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(loginIntent);
                finish();
                break;
        }
    }
}
