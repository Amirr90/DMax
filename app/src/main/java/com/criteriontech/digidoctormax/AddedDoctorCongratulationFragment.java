package com.criteriontech.digidoctormax;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.criteriontech.digidoctormax.databinding.FragmentAddedDoctorCongratulationBinding;
import com.criteriontech.digidoctormax.fragment.home.ClinicHomeActivity;

import org.jetbrains.annotations.NotNull;


public class AddedDoctorCongratulationFragment extends Fragment {

    FragmentAddedDoctorCongratulationBinding congratulationBinding;
    NavController navController;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        congratulationBinding = FragmentAddedDoctorCongratulationBinding.inflate(getLayoutInflater());
        return congratulationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        congratulationBinding.tvDone.setOnClickListener(view1 -> ClinicHomeActivity.getInstance().onSupportNavigateUp());

        congratulationBinding.tvGetStart.setOnClickListener(view12 -> navController.navigate(R.id.action_addedDoctorCongratulationFragment_to_addNewDocByClinicFragment));
    }
}