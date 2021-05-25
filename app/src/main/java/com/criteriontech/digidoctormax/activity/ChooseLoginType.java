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
import com.criteriontech.digidoctormax.databinding.ActivityChooseLoginTypeBinding;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import java.util.Objects;

public class ChooseLoginType extends Fragment {
    ActivityChooseLoginTypeBinding binding;
    NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityChooseLoginTypeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.activityChooseLoginType.setOnClickListener(view1 -> {
            SharedPrefManager.getInstance(requireActivity()).setLoginType(1);
            navController.navigate(R.id.action_activityChooseLoginType_to_loginFragment);
            /*if (SharedPrefManager.getInstance(requireActivity()).getClinicLogin() == null) {
                navController.navigate(R.id.action_activityChooseLoginType_to_loginFragment);
            } else navController.navigate(R.id.action_activityChooseLoginType_to_homeFragment2);*/
        });
        binding.tvDrChoose.setOnClickListener(view1 -> {
            SharedPrefManager.getInstance(requireActivity()).setLoginType(2);
            navController.navigate(R.id.action_activityChooseLoginType_to_loginFragment);
            /*if (SharedPrefManager.getInstance(requireActivity()).getClinicLogin() == null) {
                navController.navigate(R.id.action_activityChooseLoginType_to_loginFragment);
            } else navController.navigate(R.id.action_activityChooseLoginType_to_drHomeActivity);*/
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }
}