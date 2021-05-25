package com.criteriontech.digidoctormax.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.criteriontech.digidoctormax.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardBinding.inflate(getLayoutInflater());


    }
}