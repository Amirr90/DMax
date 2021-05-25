package com.criteriontech.digidoctormax.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.databinding.ActivityTotalBinding;
import com.criteriontech.digidoctormax.databinding.InnerDoctorTransactionBinding;
import com.criteriontech.digidoctormax.drViewModel.DrViewModel;
import com.criteriontech.digidoctormax.model.CollectedFeeValue;
import com.criteriontech.digidoctormax.request.ClinicDashboardDetails;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import java.util.Objects;

public class TotalCollectedFees extends Fragment {
    ActivityTotalBinding binding;
    DrViewModel drViewModel;
    FeeAdp feeAdp;
    ClinicDashboardDetails clinicDashboardDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityTotalBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clinicDashboardDetails = new ClinicDashboardDetails();
        drViewModel = new DrViewModel();
        feeAdp = new FeeAdp();
        binding.rvFee.setAdapter(feeAdp);

/*        clinicDashboardDetails.setId(SharedPrefManager.getInstance(requireActivity()).getClinicLogin().getId());
        clinicDashboardDetails.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getClinicLogin().getUserMobileNo());*/

        clinicDashboardDetails.setId(SharedPrefManager.getInstance(requireActivity()).getUser().getId());
        clinicDashboardDetails.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
        clinicDashboardDetails.setType("totalCollectedFees");
        binding.rvFee.setLayoutManager(new LinearLayoutManager(requireActivity()));
        drViewModel.clinicDashboardFeeCollected(clinicDashboardDetails, requireActivity()).observe(getViewLifecycleOwner(),
                collectedFeeValues -> {
                    if (collectedFeeValues.isEmpty())
                        return;

                    feeAdp.submitList(collectedFeeValues);
                    binding.setFee(collectedFeeValues.get(0));
                });
    }

    private class FeeAdp extends ListAdapter<CollectedFeeValue, FeeAdp.ViewHolder> {
        protected FeeAdp() {
            super(CollectedFeeValue.itemCallback);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            InnerDoctorTransactionBinding innerDoctorTransactionBinding = InnerDoctorTransactionBinding.inflate(layoutInflater, parent, false);
            return new ViewHolder(innerDoctorTransactionBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CollectedFeeValue collectedFeeValue = getItem(position);
            Log.v("feeee", collectedFeeValue.getName());
            holder.innerDoctorTransactionBinding.setFee(collectedFeeValue);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            InnerDoctorTransactionBinding innerDoctorTransactionBinding;

            public ViewHolder(@NonNull InnerDoctorTransactionBinding innerDoctorTransactionBinding) {
                super(innerDoctorTransactionBinding.getRoot());
                this.innerDoctorTransactionBinding = innerDoctorTransactionBinding;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

}