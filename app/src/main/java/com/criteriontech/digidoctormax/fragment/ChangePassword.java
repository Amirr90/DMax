package com.criteriontech.digidoctormax.fragment;

import android.os.Bundle;
import android.util.Log;
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

import com.criteriontech.digidoctormax.databinding.FragmentChangePasswordBinding;
import com.criteriontech.digidoctormax.request.ChangePwd;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.criteriontech.digidoctormax.utils.AppUtils.fadeIn;

public class ChangePassword extends Fragment {
    private static final String TAG = "ChangePassword";
    FragmentChangePasswordBinding binding;
    NavController navController;
    ChangePwd changePwd;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.getRoot().setAnimation(fadeIn(requireActivity()));
        navController = Navigation.findNavController(view);
        changePwd = new ChangePwd();
        binding.imgBack.setOnClickListener(view1 -> navController.navigateUp());
        binding.setChangePwd(changePwd);
        changePwd = binding.getChangePwd();
        changePwd.setId(SharedPrefManager.getInstance(requireActivity()).getUser().getId());
        changePwd.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
        changePwd.setMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
        if (SharedPrefManager.getInstance(requireActivity()).getLoginType() == 1) {

        }

        changePwd.setServiceProviderTypeId(SharedPrefManager.getInstance(requireActivity()).getLoginType().equals(1) ? 2 : 3);

        Log.d(TAG, "onViewCreated: " + SharedPrefManager.getInstance(requireActivity()).getLoginType());
        binding.btnChange.setOnClickListener(view1 -> {
            if (null == changePwd.getOldPassword() || changePwd.getOldPassword().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter old password!", Toast.LENGTH_SHORT).show();
                binding.etOld.setError("Please enter old password");
                return;
            }
            if (null == changePwd.getNewPassword() || changePwd.getNewPassword().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter new password!", Toast.LENGTH_SHORT).show();
                binding.etNew.setError("Please enter new password");
                return;
            }
            if (changePwd.getNewPassword().length() < 8) {
                Toast.makeText(requireActivity(), "Please enter valid password!", Toast.LENGTH_SHORT).show();
                binding.etNew.setError("Please enter valid password");
                return;
            }
            if (binding.etConfirm.getText().toString().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter confirm password!", Toast.LENGTH_SHORT).show();
                binding.etConfirm.setError("Please enter confirm password");
                return;
            }
            if (!changePwd.getNewPassword().trim().equalsIgnoreCase(binding.etConfirm.getText().toString().trim())) {
                Toast.makeText(requireActivity(), "New and confirm password should be same!", Toast.LENGTH_SHORT).show();
                binding.etConfirm.setError("New and confirm password should be same");
                return;
            }
            if (changePwd.getNewPassword().trim().equalsIgnoreCase(changePwd.getOldPassword().trim())) {
                Toast.makeText(requireActivity(), "Old and new password should not be same!", Toast.LENGTH_SHORT).show();
                binding.etOld.setError("Old and new password should not be same!");
                binding.etNew.setError("Old and new password should not be same!");
                return;
            }
            AppUtils.hideSoftKeyboard(requireActivity());
            AppUtils.showRequestDialog(requireActivity());
            ApiUtils.changePassword(changePwd, new ApiCallbackInterface() {
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
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}