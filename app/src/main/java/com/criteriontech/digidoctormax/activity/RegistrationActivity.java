package com.criteriontech.digidoctormax.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.ActivityRegistrationBinding;
import com.criteriontech.digidoctormax.request.ClinicRegistration;
import com.criteriontech.digidoctormax.request.GenerateOTP;
import com.criteriontech.digidoctormax.response.OtpResp;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.BaseFragment;

public class RegistrationActivity extends BaseFragment {
    ActivityRegistrationBinding binding;
    NavController navController;
    GenerateOTP generateOTP;
    Bundle bundle;
    public static ClinicRegistration clinicRegistration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        clinicRegistration = new ClinicRegistration();
        generateOTP = new GenerateOTP();
        bundle = new Bundle();
        generateOTP.setCallingCodeID(101);
        generateOTP.setServiceProviderTypeID(2);
        binding.setGenerateOTP(generateOTP);
        binding.setRegistration(clinicRegistration);
        binding.tvGetStart.setOnClickListener(view1 -> {
            try {
                clinicRegistration = binding.getRegistration();
                if (clinicRegistration.getName().isEmpty()) {
                    Toast.makeText(requireActivity(), "Please enter clinic name!", Toast.LENGTH_SHORT).show();
                    binding.etClinicNam.setError("Please enter valid clinic name");
                    return;
                }
                if (generateOTP.getMobileNo().isEmpty()) {
                    Toast.makeText(requireActivity(), "Please enter mobile No.!", Toast.LENGTH_SHORT).show();
                    binding.etMobile.setError("Please enter valid mobile No.");
                    return;
                }
                if (generateOTP.getMobileNo().length() < 10) {
                    Toast.makeText(requireActivity(), "Please enter mobile No.!", Toast.LENGTH_SHORT).show();
                    binding.etMobile.setError("Please enter valid mobile No.");
                    return;
                }
                if (clinicRegistration.getEmailId().isEmpty()) {
                    Toast.makeText(requireActivity(), "Please enter valid email!", Toast.LENGTH_SHORT).show();
                    binding.etEmail.setError("Please enter valid email");
                    return;
                }
                if (!AppUtils.isEmailValid(clinicRegistration.getEmailId())) {
                    Toast.makeText(requireActivity(), "Please enter valid email!", Toast.LENGTH_SHORT).show();
                    binding.etEmail.setError("Please enter valid email");
                    return;
                }
                if (clinicRegistration.getPassword().isEmpty()) {
                    Toast.makeText(requireActivity(), "Please enter password!", Toast.LENGTH_SHORT).show();
                    binding.etPassword.setError("Please enter password");
                    return;
                }
                if (clinicRegistration.getPassword().length() < 8) {
                    Toast.makeText(requireActivity(), "password's length should be minimum 8 digits!", Toast.LENGTH_SHORT).show();
                    binding.etPassword.setError("Please enter password");
                    return;
                }
                if (binding.etConfirmPassword.getText().toString().isEmpty() || !(binding.etConfirmPassword.getText().toString().equals(clinicRegistration.getPassword()))) {
                    Toast.makeText(requireActivity(), "Password and Confirm password mismatched!", Toast.LENGTH_SHORT).show();
                    binding.etConfirmPassword.setError("Password and Confirm password mismatched");
                    return;
                }

                generateOTP = binding.getGenerateOTP();
                clinicRegistration.setMobileNo(generateOTP.getMobileNo());
                clinicRegistration.setServiceProviderTypeId(generateOTP.getServiceProviderTypeID());
                AppUtils.showRequestDialog(requireActivity());

                if (AppUtils.isNetworkConnected(requireActivity())) {
                    ApiUtils.getOtp(generateOTP, new ApiCallbackInterface() {
                        @Override
                        public void onSuccess(Object obj) {
                            Toast.makeText(requireActivity(), ((OtpResp) obj).getResponseMessage(), Toast.LENGTH_LONG).show();
                            bundle.putBoolean("login", false);
                            AppUtils.hideDialog();
                            navController.navigate(R.id.action_registrationActivity2_to_verifyOtpClinic, bundle);
                        }

                        @Override
                        public void onFailure(String msg) {
                            Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show();
                            AppUtils.hideDialog();
                        }
                    });
                } else
                    Toast.makeText(requireActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(mActivity, "Please fill all the details!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }
}