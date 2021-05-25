package com.criteriontech.digidoctormax.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.FragmentHomeBinding;
import com.criteriontech.digidoctormax.request.ClinicDashboard;
import com.criteriontech.digidoctormax.response.ClinicDashboardResp;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ClinicHome extends Fragment {
    FragmentHomeBinding binding;
    NavController navController;
    ClinicDashboard clinicDashboard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        AppUtils.showRequestDialog(requireActivity());
        clinicDashboard = new ClinicDashboard();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        binding.tvDate.setText(" " + formatter.format(new Date()));
        binding.imageView.setOnClickListener(view1 -> ClinicHomeActivity.getInstance().drawer.open());
        clinicDashboard.setId(SharedPrefManager.getInstance(requireActivity()).getUser().getId());
        clinicDashboard.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo() == null ? SharedPrefManager
                .getInstance(requireActivity()).getClinicReg().getMobileNo() : SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
        if (AppUtils.isNetworkConnected(requireActivity())) {
            ApiUtils.clinicDashbord(clinicDashboard, new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
                    binding.setClinicDashboard(((ClinicDashboardResp) obj).getResponseValue().get(0));
                    if (!((ClinicDashboardResp) obj).getResponseValue().isEmpty()) {
                        try {
                            if (((ClinicDashboardResp) obj).getResponseValue().get(0).getIsProfileComplete().equals(0)) {
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("login", true);
                                navController.navigate(R.id.action_createProfile2_to_nav_account, bundle);
                            } else if (((ClinicDashboardResp) obj).getResponseValue().get(0).getNoOfDoctors().equals(0)) {
                                navController.navigate(R.id.action_nav_account_to_addNewDocByClinicFragment);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    AppUtils.hideDialog();
                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show();
                    AppUtils.hideDialog();
                }
            });
        } else
            Toast.makeText(requireActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();

        binding.tvDoctor.setOnClickListener(view1 -> navController.navigate(R.id.action_nav_account_to_doctorList));
        binding.imageView8.setOnClickListener(view1 -> navController.navigate(R.id.action_nav_account_to_doctorList));


        binding.tvCollectedFee.setOnClickListener(view1 -> navController.navigate(R.id.action_nav_account_to_totalCollectedFees));
        binding.ivArrow.setOnClickListener(view1 -> navController.navigate(R.id.action_nav_account_to_totalCollectedFees));


        binding.tvPatientVisited.setOnClickListener(view1 -> navController.navigate(R.id.action_nav_account_to_totalPatientVisited));
        binding.ivArrow1.setOnClickListener(view1 -> navController.navigate(R.id.action_nav_account_to_totalPatientVisited));
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }
}
