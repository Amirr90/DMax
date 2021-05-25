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

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.activity.RegistrationActivity;
import com.criteriontech.digidoctormax.databinding.FragmentVerifyOtpClinicBinding;
import com.criteriontech.digidoctormax.response.ClinicRegistrationResp;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

public class VerifyOtpClinic extends Fragment {
    FragmentVerifyOtpClinicBinding binding;
    NavController navController;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVerifyOtpClinicBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = new Bundle();
        navController = Navigation.findNavController(view);
        RegistrationActivity.clinicRegistration.setDeviceToken(SharedPrefManager.getInstance(requireActivity()).getFCMToken());
        RegistrationActivity.clinicRegistration.setDeviceType(1);
        binding.imageView14.setOnClickListener(view1 -> navController.navigateUp());

        binding.etOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) binding.etOtp2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) binding.etOtp3.requestFocus();
                else binding.etOtp1.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) binding.etOtp4.requestFocus();
                else binding.etOtp2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) binding.etOtp3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.tvVerify.setOnClickListener(view1 -> {
            if (binding.etOtp1.getText().toString().isEmpty() || binding.etOtp2.getText().toString().isEmpty() ||
                    binding.etOtp3.getText().toString().isEmpty() || binding.etOtp4.getText().toString().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter valid OTP!", Toast.LENGTH_SHORT).show();
                return;
            }
            AppUtils.showRequestDialog(requireActivity());
            RegistrationActivity.clinicRegistration.setOtp(binding.etOtp1.getText().toString() + binding.etOtp2.getText().toString() +
                    binding.etOtp3.getText().toString() + binding.etOtp4.getText().toString());
            if (AppUtils.isNetworkConnected(requireActivity())) {
                ApiUtils.clinicRegistration(RegistrationActivity.clinicRegistration, new ApiCallbackInterface() {
                    @Override
                    public void onSuccess(Object obj) {
                        Toast.makeText(requireActivity(), ((ClinicRegistrationResp) obj).getResponseMessage(), Toast.LENGTH_SHORT).show();
                        SharedPrefManager.getInstance(requireActivity()).saveUser(((ClinicRegistrationResp) obj).getResponseValue().get(0));
                        SharedPrefManager.getInstance(requireActivity()).setToken(((ClinicRegistrationResp) obj).getToken());
                        SharedPrefManager.getInstance(requireActivity()).setLoginType(1);
                        bundle.putBoolean("login", false);
                        AppUtils.hideDialog();
                        navController.navigate(R.id.action_verifyOtpClinic_to_homeFragment2, bundle);
                        requireActivity().finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                        AppUtils.hideDialog();
                    }
                });
            } else
                Toast.makeText(requireActivity(), "No internet connection !", Toast.LENGTH_SHORT).show();
        });
    }
}