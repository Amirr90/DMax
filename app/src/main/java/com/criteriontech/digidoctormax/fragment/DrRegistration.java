package com.criteriontech.digidoctormax.fragment;

import android.os.Bundle;
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
import com.criteriontech.digidoctormax.activity.StartActivity;
import com.criteriontech.digidoctormax.databinding.FragmentDrRegistrationBinding;
import com.criteriontech.digidoctormax.request.AddNewDoctor;
import com.criteriontech.digidoctormax.request.GenerateOTP;
import com.criteriontech.digidoctormax.response.AddNewDoctorResp;
import com.criteriontech.digidoctormax.response.OtpResp;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

public class DrRegistration extends Fragment {
    FragmentDrRegistrationBinding binding;
    GenerateOTP generateOTP;
    NavController navController;
    Bundle bundle = new Bundle();
    public static com.criteriontech.digidoctormax.request.DrRegistration drRegistration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDrRegistrationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        drRegistration = new com.criteriontech.digidoctormax.request.DrRegistration();
        generateOTP = new GenerateOTP();
        generateOTP.setCallingCodeID(101);
        generateOTP.setServiceProviderTypeID(3);
        binding.setGenerateOTP(generateOTP);
        if (getArguments().getBoolean("add")) {
            drRegistration.setClinicName(SharedPrefManager.getInstance(requireActivity()).getUser().getName());
            binding.etClinicNam.setEnabled(false);
        }
//        else binding.clClinicNam.setVisibility(View.GONE);
        binding.setRegistration(drRegistration);
        binding.tvGetStart.setOnClickListener(view1 -> {
            try {
                drRegistration = binding.getRegistration();
                if (!getArguments().getBoolean("add")) {
                    if (drRegistration.getClinicName().isEmpty()) {
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
                    if (drRegistration.getDoctorName().isEmpty()) {
                        Toast.makeText(requireActivity(), "Please enter doctor name!", Toast.LENGTH_SHORT).show();
                        binding.etDoctorName.setError("Please enter valid doctor name");
                        return;
                    }
                    if (drRegistration.getEmailId().isEmpty()) {
                        Toast.makeText(requireActivity(), "Please enter valid email!", Toast.LENGTH_SHORT).show();
                        binding.etEmail.setError("Please enter valid email");
                        return;
                    }
                    if (!AppUtils.isEmailValid(drRegistration.getEmailId())) {
                        Toast.makeText(requireActivity(), "Please enter valid email!", Toast.LENGTH_SHORT).show();
                        binding.etEmail.setError("Please enter valid email");
                        return;
                    }
                    if (drRegistration.getPassword().isEmpty()) {
                        Toast.makeText(requireActivity(), "Please enter password!", Toast.LENGTH_SHORT).show();
                        binding.etPassword.setError("Please enter password");
                        return;
                    }
                    if (drRegistration.getPassword().length() < 8) {
                        Toast.makeText(requireActivity(), "Password length should be 8 digits !", Toast.LENGTH_SHORT).show();
                        binding.etPassword.setError("Please enter password");
                        return;
                    }
                    if (binding.etConfirmPassword.getText().toString().isEmpty() || !(binding.etConfirmPassword.getText().toString().equals(drRegistration.getPassword()))) {
                        Toast.makeText(requireActivity(), "Password and Confirm password mismatched!", Toast.LENGTH_SHORT).show();
                        binding.etPassword.setError("Password and Confirm password mismatched");
                        return;
                    }
                    AppUtils.showRequestDialog(requireActivity());
                    generateOTP = binding.getGenerateOTP();
                    drRegistration.setMobileNo(generateOTP.getMobileNo());
                    if (AppUtils.isNetworkConnected(requireActivity())) {
                        ApiUtils.getOtp(generateOTP, new ApiCallbackInterface() {
                            @Override
                            public void onSuccess(Object obj) {
                                Toast.makeText(requireActivity(), ((OtpResp) obj).getResponseMessage(), Toast.LENGTH_LONG).show();
                                bundle.putBoolean("add", false);
                                bundle.putBoolean("login", false);
                                AppUtils.hideDialog();
                                if (getArguments().getBoolean("add"))
                                    navController.navigate(R.id.action_drRegistration_to_drCreateProfile, bundle);
                                else
                                    navController.navigate(R.id.action_drRegistration_to_verifyOtpActivity, bundle);
                            }

                            @Override
                            public void onFailure(String msg) {
                                Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show();
                                AppUtils.hideDialog();
                            }
                        });
                    } else
                        Toast.makeText(requireActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
                } else {
                    generateOTP = binding.getGenerateOTP();
                    drRegistration.setMobileNo(generateOTP.getMobileNo());
                    AddNewDoctor addNewDoctor = new AddNewDoctor();
//                addNewDoctor.setOtp(DrRegistration.drRegistration.getOtp());
                    addNewDoctor.setDoctorName(DrRegistration.drRegistration.getDoctorName());
                    addNewDoctor.setEmailId(DrRegistration.drRegistration.getEmailId());
                    addNewDoctor.setPassword(DrRegistration.drRegistration.getPassword());
                    addNewDoctor.setMobileNo(DrRegistration.drRegistration.getMobileNo());
                    if (AppUtils.isNetworkConnected(requireActivity())) {
                        ApiUtils.addNewDoctor(addNewDoctor, new ApiCallbackInterface() {
                            @Override
                            public void onSuccess(Object obj) {
                                AppUtils.hideDialog();
                                bundle.putBoolean("login", getArguments().getBoolean("login"));
                                bundle.putBoolean("add", getArguments().getBoolean("add"));
                                StartActivity.doctorProfileValue = ((AddNewDoctorResp) obj).getResponseValue().get(0);
                                navController.navigate(R.id.action_drRegistration_to_selectSlotFragment, bundle);
                            }

                            @Override
                            public void onFailure(String msg) {
                                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                                AppUtils.hideDialog();
                            }
                        });
                    } else
                        Toast.makeText(requireActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(requireActivity(), "Please fill all the details!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }
}