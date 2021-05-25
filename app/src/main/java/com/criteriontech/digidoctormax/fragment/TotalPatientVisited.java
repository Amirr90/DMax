package com.criteriontech.digidoctormax.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.ActivityTotalpatientvisitedfragmentBinding;
import com.criteriontech.digidoctormax.drViewModel.DrViewModel;
import com.criteriontech.digidoctormax.model.ClinicVisitedPatientModel;
import com.criteriontech.digidoctormax.request.ClinicDashboardDetails;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class TotalPatientVisited extends Fragment {
    ActivityTotalpatientvisitedfragmentBinding binding;
    DrViewModel drViewModel;
    ClinicDashboardDetails clinicDashboardDetails;
    ClinicVisitedPatientModel visitedPatient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityTotalpatientvisitedfragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        drViewModel = new DrViewModel();
        clinicDashboardDetails = new ClinicDashboardDetails();
     /*   clinicDashboardDetails.setId(SharedPrefManager.getInstance(requireActivity()).getClinicLogin().getId());
        clinicDashboardDetails.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getClinicLogin().getUserMobileNo());*/

        clinicDashboardDetails.setId(SharedPrefManager.getInstance(requireActivity()).getUser().getId());
        clinicDashboardDetails.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());

        clinicDashboardDetails.setType("totalPatientVisited");
        visitedPatient = new ClinicVisitedPatientModel();
        drViewModel.clinicDashboardVisitedPatient(clinicDashboardDetails, requireActivity()).observe(getViewLifecycleOwner(),
                visitedPatientClinicValues -> {
                    visitedPatient.setTotalPatientVisited(visitedPatientClinicValues.get(0).getTotalPatientVisitedWeekly());
                    visitedPatient.setTotalWritePrescription(visitedPatientClinicValues.get(0).getTotalWritePrescriptionWeekly());
                    visitedPatient.setTotalCancelledAppointments(visitedPatientClinicValues.get(0).getTotalCancelledAppointmentsWeekly());
                    binding.setVisited(visitedPatient);
                });
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    drViewModel.clinicDashboardVisitedPatient(clinicDashboardDetails, requireActivity()).observe(getViewLifecycleOwner(),
                            visitedPatientClinicValues -> {
                                visitedPatient.setTotalPatientVisited(visitedPatientClinicValues.get(0).getTotalPatientVisitedWeekly());
                                visitedPatient.setTotalWritePrescription(visitedPatientClinicValues.get(0).getTotalWritePrescriptionWeekly());
                                visitedPatient.setTotalCancelledAppointments(visitedPatientClinicValues.get(0).getTotalCancelledAppointmentsWeekly());
                                binding.setVisited(visitedPatient);
                            });
                } else if (tab.getPosition() == 1) {
                    drViewModel.clinicDashboardVisitedPatient(clinicDashboardDetails, requireActivity()).observe(getViewLifecycleOwner(),
                            visitedPatientClinicValues -> {
                                visitedPatient.setTotalPatientVisited(visitedPatientClinicValues.get(0).getTotalPatientVisitedMonthly());
                                visitedPatient.setTotalWritePrescription(visitedPatientClinicValues.get(0).getTotalWritePrescriptionMonthly());
                                visitedPatient.setTotalCancelledAppointments(visitedPatientClinicValues.get(0).getTotalCancelledAppointmentsMonthly());
                                binding.setVisited(visitedPatient);
                            });
                } else {
                    drViewModel.clinicDashboardVisitedPatient(clinicDashboardDetails, requireActivity()).observe(getViewLifecycleOwner(),
                            visitedPatientClinicValues -> {
                                visitedPatient.setTotalPatientVisited(visitedPatientClinicValues.get(0).getTotalPatientVisitedYearly());
                                visitedPatient.setTotalWritePrescription(visitedPatientClinicValues.get(0).getTotalWritePrescriptionYearly());
                                visitedPatient.setTotalCancelledAppointments(visitedPatientClinicValues.get(0).getTotalCancelledAppointmentsYearly());
                                binding.setVisited(visitedPatient);
                            });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}