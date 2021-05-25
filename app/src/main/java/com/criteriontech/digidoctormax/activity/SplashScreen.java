package com.criteriontech.digidoctormax.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.ActivitySplashScreenBinding;
import com.criteriontech.digidoctormax.fragment.home.ClinicHomeActivity;
import com.criteriontech.digidoctormax.fragment.home.DrHomeActivity;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {
    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
        FirebaseApp.initializeApp(SplashScreen.this);
        insertFcmToken();
    }

    private void insertFcmToken() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(() -> {
            Log.v("loginType", String.valueOf(SharedPrefManager.getInstance(this).getLoginType()));
            if (SharedPrefManager.getInstance(this).getLoginType() == 0)
                startActivity(new Intent(this, StartActivity.class));
            else {
                if (SharedPrefManager.getInstance(SplashScreen.this).getToken() != null) {
                    if (SharedPrefManager.getInstance(this).getLoginType() == 1)
                        startActivity(new Intent(this, ClinicHomeActivity.class));
                    else
                        startActivity(new Intent(this, DrHomeActivity.class));
                } else
                    startActivity(new Intent(this, StartActivity.class));
            }

            finish();
        }, 2000);


    }
}