package com.criteriontech.digidoctormax.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.FragmentLoginBinding;
import com.criteriontech.digidoctormax.drViewModel.DrViewModel;
import com.criteriontech.digidoctormax.model.ClinicDetails;
import com.criteriontech.digidoctormax.request.GenerateOTP;
import com.criteriontech.digidoctormax.request.Login;
import com.criteriontech.digidoctormax.response.LoginResp;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class LoginFragment extends Fragment {
    GenerateOTP generateOTP;
    DrViewModel viewModel;
    FragmentLoginBinding loginBinding;
    Login login;
    Boolean userType;
    Bundle bundle;
    NavController navController;
    Gson gson;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loginBinding = FragmentLoginBinding.inflate(getLayoutInflater());
        return loginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        generateOTP = new GenerateOTP();
        gson = new Gson();
        bundle = new Bundle();
        bundle.putBoolean("add", false);

        generateOTP.setCallingCodeID(101);
        generateOTP.setServiceProviderTypeID(3);
        loginBinding.setGenerateOTP(generateOTP);


        login = new Login();
        login.setAccessToken("");
        login.setAppType("dd");
        login.setDeviceToken(SharedPrefManager.getInstance(requireActivity()).getFCMToken());
        login.setDeviceType("1");
        login.setMobileNo("");
        login.setOtp("");
        login.setPassword("");
        if (SharedPrefManager.getInstance(requireActivity()).getLoginType() == 1)
            login.setServiceProviderTypeID("2");
        else login.setServiceProviderTypeID("3");
        login.setUniqueNOS("");
        loginBinding.setLogin(login);

        viewModel = new ViewModelProvider(requireActivity()).get(DrViewModel.class);
        loginBinding.tvLogin.setOnClickListener(view1 -> login());
        loginBinding.tvSignUp.setOnClickListener(view1 -> {
            if (SharedPrefManager.getInstance(requireActivity()).getLoginType() == 1)
                navController.navigate(R.id.action_loginFragment_to_registrationActivity2);
            else navController.navigate(R.id.action_loginFragment_to_drRegistration, bundle);
        });
        loginBinding.tvForgotPass.setOnClickListener(view1 -> navController.navigate(R.id.action_loginFragment_to_forgetPassword));
    }

    public void login() {
        login = loginBinding.getLogin();
        if (login.getMobileNo().isEmpty() && (login.getMobileNo().length() < 10)) {
            Toast.makeText(requireActivity(), "Please enter valid mobile No.!", Toast.LENGTH_SHORT).show();
            loginBinding.etClinicNam.setError("Please enter valid mobile No.");
            return;
        }
        if (login.getPassword().isEmpty()) {
            Toast.makeText(requireActivity(), "Please enter password!", Toast.LENGTH_SHORT).show();
            loginBinding.etPassword.setError("Please enter password!");
            return;
        }
        if (AppUtils.isNetworkConnected(requireActivity())) {
            AppUtils.showRequestDialog(requireActivity());
            ApiUtils.login(login, new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
                    Toast.makeText(requireActivity(), ((LoginResp) obj).getResponseMessage(), Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(requireActivity()).saveUser(((LoginResp) obj).getResponseValue().get(0));
                    SharedPrefManager.getInstance(requireActivity()).setToken((String) ((LoginResp) obj).getToken());
                    TypeToken<List<ClinicDetails>> token = new TypeToken<List<ClinicDetails>>() {};
                    List<ClinicDetails> clinicDetails = gson.fromJson(((LoginResp) obj).getResponseValue().get(0).getClinicDetails(), token.getType());
                    SharedPrefManager.getInstance(requireActivity()).saveClinicLogin(clinicDetails.get(0));
                    AppUtils.hideDialog();
                    if (SharedPrefManager.getInstance(requireActivity()).getLoginType() == 1)
                        navController.navigate(R.id.action_loginFragment_to_homeFragment2);
                    else {
                        navController.navigate(R.id.action_loginFragment_to_drHomeActivity);
                    }

                    requireActivity().finish();
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


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }
}