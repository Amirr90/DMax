package com.criteriontech.digidoctormax.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.criteriontech.digidoctormax.databinding.ActivityAddvitalfragmentBinding;
import com.criteriontech.digidoctormax.request.AddVital;
import com.criteriontech.digidoctormax.request.VitalModel;
import com.criteriontech.digidoctormax.response.AddVitalResp;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Addvitalfragment extends DialogFragment {
    ActivityAddvitalfragmentBinding binding;
    AddVital addVital;
    VitalModel vital;
    Date today;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityAddvitalfragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addVital = new AddVital();
        today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        addVital.setDate(dateFormat.format(today));
        addVital.setTime(timeFormat.format(today));
        addVital.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
        addVital.setMemberID(getArguments().getInt("memberId"));
        addVital.setAppointmentId(String.valueOf(getArguments().getInt("appointmentId")));
        binding.setAddVital(new VitalModel());
        binding.btnSubmit.setOnClickListener(view1 -> {
            if (binding.getAddVital() != null) {
                if (binding.textView62.getText().toString().trim().isEmpty() && binding.textView61.getText().toString().trim().isEmpty() && binding.textView64.getText().toString().trim().isEmpty() && binding.textView67.getText().toString().trim().isEmpty() && binding.textView69.getText().toString().trim().isEmpty() && binding.textView71.getText().toString().trim().isEmpty() && binding.textView73.getText().toString().trim().isEmpty()) {
                    Toast.makeText(requireActivity(), "Please enter any vital value", Toast.LENGTH_SHORT).show();
                } else {
                    AppUtils.showRequestDialog(requireActivity());
                    try {
                        addVital.setDtDataTable(getDtTableValue(binding.getAddVital()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (AppUtils.isNetworkConnected(requireActivity())) {
                        ApiUtils.addVital(addVital, new ApiCallbackInterface() {
                            @Override
                            public void onSuccess(Object obj) {
                                Toast.makeText(requireActivity(), ((AddVitalResp) obj).getResponseMessage(), Toast.LENGTH_SHORT).show();
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
            } else
                Toast.makeText(requireActivity(), "Please enter any vital value", Toast.LENGTH_SHORT).show();
        });
    }

    private String getDtTableValue(VitalModel vitalModel) throws JSONException {

        JSONArray dtTableArray = new JSONArray();

        JSONObject jsonObject1 = new JSONObject();

        jsonObject1.put("vitalId", "4");
        jsonObject1.put("vitalValue", vitalModel.getSystolic());
        dtTableArray.put(jsonObject1);


        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("vitalId", "6");
        jsonObject2.put("vitalValue", vitalModel.getDiastolic());
        dtTableArray.put(jsonObject2);


        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("vitalId", "3");
        jsonObject3.put("vitalValue", vitalModel.getPulse());
        dtTableArray.put(jsonObject3);


        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("vitalId", "56");
        jsonObject4.put("vitalValue", vitalModel.getSpo2());
        dtTableArray.put(jsonObject4);


        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("vitalId", "5");
        jsonObject5.put("vitalValue", vitalModel.getTemp());
        dtTableArray.put(jsonObject5);


        JSONObject jsonObject6 = new JSONObject();
        jsonObject6.put("vitalId", "10");
        jsonObject6.put("vitalValue", vitalModel.getRbs());
        dtTableArray.put(jsonObject6);


        JSONObject jsonObject7 = new JSONObject();
        jsonObject7.put("vitalId", "7");
        jsonObject7.put("vitalValue", vitalModel.getRr());
        dtTableArray.put(jsonObject7);
        return String.valueOf(dtTableArray);

    }
}