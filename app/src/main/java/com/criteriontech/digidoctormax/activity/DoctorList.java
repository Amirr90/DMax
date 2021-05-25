package com.criteriontech.digidoctormax.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.ActivityDoctorListBinding;
import com.criteriontech.digidoctormax.databinding.InnerviewDoctorListBinding;
import com.criteriontech.digidoctormax.drViewModel.DrViewModel;
import com.criteriontech.digidoctormax.model.ClinicNumOfDocValue;
import com.criteriontech.digidoctormax.request.ClinicDashboardDetails;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import java.util.Objects;

public class DoctorList extends Fragment {
    ActivityDoctorListBinding binding;
    ClinicDashboardDetails clinicDashboardDetails;
    DoctorAdp doctorAdp;
    DrViewModel drViewModel;
    NavController navController;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityDoctorListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireActivity()));
        clinicDashboardDetails = new ClinicDashboardDetails();
        doctorAdp = new DoctorAdp();
        bundle = new Bundle();
        drViewModel = new ViewModelProvider(requireActivity()).get(DrViewModel.class);
        binding.recyclerview.setAdapter(doctorAdp);
        //clinicDashboardDetails.setId(SharedPrefManager.getInstance(requireActivity()).getClinicLogin().getId());
        clinicDashboardDetails.setId(SharedPrefManager.getInstance(requireActivity()).getUser().getId());
//        clinicDashboardDetails.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getClinicLogin().getUserMobileNo());
        clinicDashboardDetails.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
        clinicDashboardDetails.setType("noOfDoctors");
        drViewModel.clinicDashboardNumOfDoctors(clinicDashboardDetails, requireActivity()).observe(getViewLifecycleOwner(),
                clinicNumOfDocValues -> doctorAdp.submitList(clinicNumOfDocValues));
        binding.imgBack.setOnClickListener(view1 -> StartActivity.getInstance().onSupportNavigateUp());
        binding.tvAddNewDoctor.setOnClickListener(view1 -> {
            bundle.putBoolean("login", true);
            navController.navigate(R.id.action_doctorList_to_addNewDocByClinicFragment, bundle);
        });
    }

    public class DoctorAdp extends ListAdapter<ClinicNumOfDocValue, DoctorAdp.ViewHolder> {
        protected DoctorAdp() {
            super(ClinicNumOfDocValue.itemCallback);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            InnerviewDoctorListBinding innerviewDoctorListBinding = InnerviewDoctorListBinding.inflate(layoutInflater, parent, false);
            return new ViewHolder(innerviewDoctorListBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ClinicNumOfDocValue clinicNumOfDocValue = getItem(position);
            Log.v("drName", clinicNumOfDocValue.getName());
            holder.innerviewDoctorListBinding.setDocList(clinicNumOfDocValue);
            holder.innerviewDoctorListBinding.getRoot().setOnClickListener(view -> {
                bundle.putString("drId", clinicNumOfDocValue.getId());
                navController.navigate(R.id.action_doctorList_to_doctorDetailActivity, bundle);
            });
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            InnerviewDoctorListBinding innerviewDoctorListBinding;

            public ViewHolder(@NonNull InnerviewDoctorListBinding innerviewDoctorListBinding) {
                super(innerviewDoctorListBinding.getRoot());
                this.innerviewDoctorListBinding = innerviewDoctorListBinding;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}