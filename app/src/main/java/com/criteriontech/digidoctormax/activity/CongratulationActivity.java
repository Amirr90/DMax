package com.criteriontech.digidoctormax.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.ActivityCongratulationBinding;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import java.util.Objects;

public class CongratulationActivity extends Fragment {
    ActivityCongratulationBinding binding;
    NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityCongratulationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.tvDone.setOnClickListener(view1 -> {

            navController.navigate(R.id.action_congratulationActivity2_to_nav_account);
        });
        if (!getArguments().getBoolean("login")) {
            binding.tvWelcome.setVisibility(View.VISIBLE);
            binding.tvDone.setVisibility(View.GONE);
            binding.textView2.setVisibility(View.GONE);
            binding.textView7.setText(R.string.do_oyu_want_to_add_doctor);
        } else {
            binding.tvWelcome.setVisibility(View.GONE);
            binding.tvDone.setVisibility(View.VISIBLE);
            binding.textView2.setVisibility(View.VISIBLE);
            binding.textView7.setText(R.string.do_you_want_to_add_more_doctors);
        }
        binding.tvGetStart.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("add", true);
            bundle.putBoolean("login", getArguments().getBoolean("login"));
            if (SharedPrefManager.getInstance(requireActivity()).getLoginType() == 1)
                navController.navigate(R.id.action_congratulationActivity_to_homeFragment2, bundle);
            else navController.navigate(R.id.drRegistration, bundle);
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }
}