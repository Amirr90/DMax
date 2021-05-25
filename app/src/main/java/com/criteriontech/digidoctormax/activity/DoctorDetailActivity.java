package com.criteriontech.digidoctormax.activity;

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

import com.criteriontech.digidoctormax.databinding.ActivityDoctorDetailBinding;
import com.criteriontech.digidoctormax.model.DoctorProfileValue;
import com.criteriontech.digidoctormax.request.DoctorProfile;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import org.json.JSONException;

import java.util.Objects;

import static com.criteriontech.digidoctormax.utils.AppUtils.getTimeSlots;

public class DoctorDetailActivity extends Fragment {
    ActivityDoctorDetailBinding binding;
    NavController navController;
    DoctorProfile doctorProfile;
    private static final String TAG = "DoctorDetailActivity";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityDoctorDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        doctorProfile = new DoctorProfile();

        if (getArguments() == null)
            return;
        doctorProfile.setId(Integer.parseInt(getArguments().getString("drId")));
        // doctorProfile.setId(SharedPrefManager.getInstance(requireActivity()).getUser().getUserLoginId());
        doctorProfile.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
        if (AppUtils.isNetworkConnected(requireActivity())) {
            ApiUtils.getDoctorProfile(doctorProfile, new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
                    binding.setProfile((DoctorProfileValue) obj);

                    try {
                        Log.d(TAG, "onSuccess: " + ((DoctorProfileValue) obj).toString());
                        binding.textView21.setText(getTimeSlots(((DoctorProfileValue) obj).getTimeDetails()).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "onSuccess: " + e.getLocalizedMessage());
                    }
                    AppUtils.hideDialog();
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
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}