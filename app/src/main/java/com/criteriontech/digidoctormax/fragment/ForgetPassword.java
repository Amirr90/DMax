package com.criteriontech.digidoctormax.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.criteriontech.digidoctormax.databinding.FragmentForgetPasswordBinding;
import com.criteriontech.digidoctormax.request.ForgotPwd;
import com.criteriontech.digidoctormax.request.GenerateOTP;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

public class ForgetPassword extends Fragment {
    FragmentForgetPasswordBinding binding;
    NavController navController;
    ForgotPwd changePwd;
    public static int SERVICE_PROVIDER_DOCTOR = 3;
    public static int SERVICE_PROVIDER_CLINIC = 2;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForgetPasswordBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        changePwd = new ForgotPwd();
        binding.imgBack.setOnClickListener(view1 -> navController.navigateUp());
        binding.setForget(changePwd);


        changePwd = binding.getForget();


        if (SharedPrefManager.getInstance(requireActivity()).getLoginType() == 1)
            changePwd.setServiceProviderTypeId(SERVICE_PROVIDER_CLINIC);
        else changePwd.setServiceProviderTypeId(SERVICE_PROVIDER_DOCTOR);

        binding.btnChange.setOnClickListener(view1 -> {
            if (null != changePwd) {
                if (null != changePwd.getOtp() && changePwd.getOtp().isEmpty()) {
                    Toast.makeText(requireActivity(), "Please enter old password!", Toast.LENGTH_SHORT).show();
                    binding.etOld.setError("Please enter old password");
                    return;
                }
                if (null != changePwd.getPassword() && changePwd.getPassword().isEmpty()) {
                    Toast.makeText(requireActivity(), "Please enter new password!", Toast.LENGTH_SHORT).show();
                    binding.etNew.setError("Please enter new password");
                    return;
                }
                if (null != changePwd.getPassword() && changePwd.getPassword().length() < 8) {
                    Toast.makeText(requireActivity(), "Please enter new password!", Toast.LENGTH_SHORT).show();
                    binding.etNew.setError("Please enter new password");
                    return;
                }
                if (binding.etConfirm.getText().toString().isEmpty()) {
                    Toast.makeText(requireActivity(), "Please enter confirm password!", Toast.LENGTH_SHORT).show();
                    binding.etConfirm.setError("Please enter confirm password");
                    return;
                }
                if (null != changePwd.getPassword() && !changePwd.getPassword().trim().equalsIgnoreCase(binding.etConfirm.getText().toString().trim())) {
                    Toast.makeText(requireActivity(), "New and confirm password should be same!", Toast.LENGTH_SHORT).show();
                    binding.etConfirm.setError("New and confirm password should be same");
                    return;
                }

                AppUtils.hideSoftKeyboard(requireActivity());
                AppUtils.showRequestDialog(requireActivity());
                ApiUtils.forgetPassword(changePwd, new ApiCallbackInterface() {
                    @Override
                    public void onSuccess(Object obj) {
                        AppUtils.hideDialog();
                        Toast.makeText(requireActivity(), "Password saved successfully!", Toast.LENGTH_SHORT).show();
                        navController.navigateUp();

                    }

                    @Override
                    public void onFailure(String msg) {
                        AppUtils.hideDialog();
                        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (null != charSequence) {
                    binding.btnGetOTP.setVisibility(charSequence.length() == 10 ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.btnGetOTP.setOnClickListener(view12 -> {

            String number = binding.etNumber.getText().toString();
            if (number.length() == 10) {
                sendOTP(number);
            }
        });

    }

    private void sendOTP(String number) {
        Toast.makeText(requireActivity(), "OTP sent successfully to mobile number +91" + number, Toast.LENGTH_SHORT).show();
        binding.btnGetOTP.setEnabled(false);
        GenerateOTP generateOTP = new GenerateOTP();
        generateOTP.setServiceProviderTypeID(binding.getForget().getServiceProviderTypeId());
        generateOTP.setMobileNo(number);
        ApiUtils.generateOTPForForgotPassword(generateOTP, new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                AppUtils.hideDialog();
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });

    }
}