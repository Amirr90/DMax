package com.criteriontech.digidoctormax.activity;

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
import com.criteriontech.digidoctormax.databinding.ActivityCreateProfileBinding;
import com.criteriontech.digidoctormax.fragment.home.ClinicHomeActivity;
import com.criteriontech.digidoctormax.fragment.home.DrHomeActivity;
import com.criteriontech.digidoctormax.model.DoctorProfileValue;
import com.criteriontech.digidoctormax.response.UpdateDrProfileResp;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import java.util.Objects;

public class CreateProfile extends Fragment {
    ActivityCreateProfileBinding binding;
    NavController navController;
    Bundle bundle;
    DoctorProfileValue doctorProfileValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityCreateProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        doctorProfileValue = new DoctorProfileValue();

        if (!getArguments().getBoolean("login")) {
            doctorProfileValue.setName(RegistrationActivity.clinicRegistration.getName());
            doctorProfileValue.setUserMobileNo(RegistrationActivity.clinicRegistration.getMobileNo());
            doctorProfileValue.setEmailId(RegistrationActivity.clinicRegistration.getEmailId());
        } else {
            binding.tvNext.setText("Update Profile");
            binding.tvWelcome.setText("Update Profile");
            binding.textView2.setText("Update your profile");
            try {

                doctorProfileValue.setName(SharedPrefManager.getInstance(requireActivity()).getUser().getName());
                doctorProfileValue.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
                doctorProfileValue.setEmailId(SharedPrefManager.getInstance(requireActivity()).getUser().getEmailId());

                doctorProfileValue.setRegistrationNo(SharedPrefManager.getInstance(requireActivity()).getRegNo());
                doctorProfileValue.setWorkDescription(SharedPrefManager.getInstance(requireActivity()).getWorkDes());
                doctorProfileValue.setAddress(SharedPrefManager.getInstance(requireActivity()).getAddress());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bundle = new Bundle();
        binding.setRegistration(doctorProfileValue);

        binding.leftarrow.setOnClickListener(view1 -> {
            if (null != ClinicHomeActivity.getInstance()) {
                ClinicHomeActivity.getInstance().onSupportNavigateUp();
            } else if (null != DrHomeActivity.getInstance()) {
                DrHomeActivity.getInstance().onSupportNavigateUp();
            } else if (null != StartActivity.getInstance()) {
                navController.navigate(R.id.action_createProfile_to_homeFragment22);
                requireActivity().finish();
            } else navController.navigateUp();

        });


        binding.tvNext.setOnClickListener(view1 -> {
            doctorProfileValue = binding.getRegistration();
            if (doctorProfileValue.getUserMobileNo().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter mobile No.!", Toast.LENGTH_SHORT).show();
                binding.etMobile.setError("Please enter valid mobile No.");
                return;
            }
            if (doctorProfileValue.getUserMobileNo().length() < 10) {
                Toast.makeText(requireActivity(), "Please enter mobile No.!", Toast.LENGTH_SHORT).show();
                binding.etMobile.setError("Please enter valid mobile No.");
                return;
            }
            if (doctorProfileValue.getEmailId().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter valid email!", Toast.LENGTH_SHORT).show();
                binding.etEmail.setError("Please enter valid email");
                return;
            }
            if (!AppUtils.isEmailValid(doctorProfileValue.getEmailId())) {
                Toast.makeText(requireActivity(), "Please enter valid email!", Toast.LENGTH_SHORT).show();
                binding.etEmail.setError("Please enter valid email");
                return;
            }
            if (AppUtils.isNetworkConnected(requireActivity())) {
                AppUtils.showRequestDialog(requireActivity());
                doctorProfileValue.setServiceProviderTypeId(2);
                doctorProfileValue.setId(SharedPrefManager.getInstance(requireActivity()).getUser().getId());
                ApiUtils.updateDoctorProfile(doctorProfileValue, new ApiCallbackInterface() {
                    @Override
                    public void onSuccess(Object obj) {
                        AppUtils.hideDialog();
                        Toast.makeText(requireActivity(), ((UpdateDrProfileResp) obj).getResponseMessage(), Toast.LENGTH_SHORT).show();

                        SharedPrefManager.getInstance(requireActivity()).setRegNo(((UpdateDrProfileResp) obj).getResponseValue().get(0).getRegistrationNo());
                        SharedPrefManager.getInstance(requireActivity()).setWorkDes(((UpdateDrProfileResp) obj).getResponseValue().get(0).getWorkDescription());
                        SharedPrefManager.getInstance(requireActivity()).setAddress(((UpdateDrProfileResp) obj).getResponseValue().get(0).getAddress());

                       // SharedPrefManager.getInstance(requireActivity()).saveUser(((UpdateDrProfileResp) obj).getResponseValue().get(0));

                        bundle.putBoolean("login", getArguments().getBoolean("login"));
                        if (getArguments().getBoolean("login"))
                            navController.navigate(R.id.action_createProfile2_to_nav_account, bundle);
                        else
                            navController.navigate(R.id.action_createProfile_to_congratulationActivity, bundle);
                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                        AppUtils.hideDialog();
                    }
                });
            } else
                Toast.makeText(requireActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }
}