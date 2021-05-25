package com.criteriontech.digidoctormax.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.FragmentAddNewDocByClinicBinding;
import com.criteriontech.digidoctormax.model.DoctorProfileValue;
import com.criteriontech.digidoctormax.model.Gender;
import com.criteriontech.digidoctormax.model.SpecialityValue;
import com.criteriontech.digidoctormax.response.SpecialityResp;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddNewDocByClinicFragment extends Fragment {

    FragmentAddNewDocByClinicBinding binding;
    DoctorProfileValue doctorProfileValue = new DoctorProfileValue();
    NavController navController;

    private int year = 0, month = 0, day = 0;
    private Calendar c;
    List<Gender> genderList;
    ArrayAdapter<SpecialityValue> specialityAdp;
    ArrayAdapter<Gender> genderAdp;
    List<SpecialityValue> specialityList;

    private static final String TAG = "AddNewDocByClinicFragme";

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddNewDocByClinicBinding.inflate(getLayoutInflater());

        specialityList = new ArrayList<>();
        genderList = new ArrayList<>();
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        genderList.add(0, new Gender(0, "-Select Gender-"));
        genderList.add(1, new Gender(1, "Male"));
        genderList.add(2, new Gender(2, "Female"));

        genderAdp = new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, genderList);
        binding.spnGender.setAdapter(genderAdp);
        if (AppUtils.isNetworkConnected(requireActivity())) {
            ApiUtils.getSpeciality(new JSONObject(), new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
                    specialityList.clear();
                    specialityList.add(0, new SpecialityValue(0, "-Select Specialty-", "", ""));
                    specialityList.addAll(((SpecialityResp) obj).getResponseValue());
                    try {
                        specialityAdp = new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, specialityList);
                        binding.etSpecialities.setAdapter(specialityAdp);
                    } catch (Exception e) {
                        e.printStackTrace();
                        binding.etSpecialities.setAdapter(specialityAdp);
                    }


                    Log.d(TAG, "onSuccessAdapter : " + specialityAdp.getCount());
                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                    AppUtils.hideDialog();
                    requireActivity().onBackPressed();
                }
            });
        } else {
            Toast.makeText(requireActivity(), "No internet connection !", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        doctorProfileValue.setClinicName(SharedPrefManager.getInstance(requireActivity()).getUser().getName());
        binding.setRegistration(doctorProfileValue);

        binding.tvAddTimeSlot.setOnClickListener(view1 -> {
            doctorProfileValue = binding.getRegistration();

            if (AllFieldsFilled(doctorProfileValue)) {
                doctorProfileValue.setServiceProviderTypeId(3);
                doctorProfileValue.setMobileNo(doctorProfileValue.getUserMobileNo());
                doctorProfileValue.setId(SharedPrefManager.getInstance(requireActivity()).getUser().getUserLoginId());
                doctorProfileValue.setSpecialityId(specialityList.get(binding.etSpecialities.getSelectedItemPosition()).getId());
                doctorProfileValue.setDepartmentId(specialityList.get(binding.etSpecialities.getSelectedItemPosition()).getId());
                // doctorProfileValue.setGender(doctorProfileValue.getGender().equalsIgnoreCase("male") ? "1" : "2");

                if (AppUtils.isNetworkConnected(requireActivity())) {
                    ApiUtils.addNewDoctor(doctorProfileValue, new ApiCallbackInterface() {
                        @Override
                        public void onSuccess(Object obj) {
                            List<DoctorProfileValue> values = (List<DoctorProfileValue>) obj;
                            AppUtils.hideDialog();
                            if (values.isEmpty())
                                return;

                            Bundle bundle = new Bundle();
                            bundle.putBoolean("login", true);
                            bundle.putString("model", values.get(0).toString());

                            Log.d(TAG, "onSuccess: " + values.get(0).toString());
                            navController.navigate(R.id.action_addNewDocByClinicFragment_to_addTimeSlotFoNewlyAddedDoctorFragment, bundle);
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
        });


        binding.etDob.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, (datePicker, i, i1, i2) -> {
                year = i;
                month = i1;
                day = i2;
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month + 1);
                c.set(Calendar.DAY_OF_MONTH, day);
                binding.etDob.setText(AppUtils.formatInputDate(c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR)));
            }, year, month, day);
            datePickerDialog.setTitle("Select Date");
            datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();

        });
    }

    private boolean AllFieldsFilled(DoctorProfileValue doctorProfileValue) {
        if (TextUtils.isEmpty(doctorProfileValue.getName())) {
            Toast.makeText(requireActivity(), "Please enter Doctor name!", Toast.LENGTH_SHORT).show();
            binding.etClinicNam.setError("Please enter Doctor name");
            return false;

        } else if (TextUtils.isEmpty(doctorProfileValue.getUserMobileNo())) {
            Toast.makeText(requireActivity(), "Please enter mobile No.!", Toast.LENGTH_SHORT).show();
            binding.etMobile.setError("Please enter valid mobile No.");
            return false;
        } else if (doctorProfileValue.getUserMobileNo().length() < 10) {

            Toast.makeText(requireActivity(), "Please enter mobile No.!", Toast.LENGTH_SHORT).show();
            binding.etMobile.setError("Please enter valid mobile No.");
            return false;

        } else if (TextUtils.isEmpty(doctorProfileValue.getEmailId())) {

            Toast.makeText(requireActivity(), "Please enter valid email!", Toast.LENGTH_SHORT).show();
            binding.etEmail.setError("Please enter valid email");
            return false;

        } else if (!AppUtils.isEmailValid(this.doctorProfileValue.getEmailId())) {

            Toast.makeText(requireActivity(), "Please enter valid email!", Toast.LENGTH_SHORT).show();
            binding.etEmail.setError("Please enter valid email");
            return false;

        } else if (binding.etDob.getText().toString().trim().equals("")) {

            Toast.makeText(requireActivity(), "Please enter dob!", Toast.LENGTH_SHORT).show();
            binding.etDob.setError("Please enter dob");
            return false;

        } else if (binding.spnGender.getSelectedItemPosition() == 0) {

            Toast.makeText(requireActivity(), "Please select gender!", Toast.LENGTH_SHORT).show();
            return false;

        } else if (TextUtils.isEmpty(doctorProfileValue.getYearOfExperience())) {

            Toast.makeText(requireActivity(), "Please enter year of experience!", Toast.LENGTH_SHORT).show();
            binding.etYearOfExp.setError("Please enter year of experience");
            return false;

        } else if (TextUtils.isEmpty(doctorProfileValue.getDegree())) {

            Toast.makeText(requireActivity(), "Please enter degree!", Toast.LENGTH_SHORT).show();
            binding.etDegree.setError("Please enter degree");
            return false;

        } else if (TextUtils.isEmpty(doctorProfileValue.getRegistrationNo())) {

            Toast.makeText(requireActivity(), "Please enter registration No.!", Toast.LENGTH_SHORT).show();
            binding.etRegistrationNo.setError("Please enter registration No.");
            return false;

        } else if (TextUtils.isEmpty(doctorProfileValue.getDrFee())) {
            Toast.makeText(requireActivity(), "Please enter fee!", Toast.LENGTH_SHORT).show();
            binding.etFee.setError("Please enter fee");
            return false;

        } else if (TextUtils.isEmpty(doctorProfileValue.getWorkDescription())) {

            Toast.makeText(requireActivity(), "Please enter work description!", Toast.LENGTH_SHORT).show();
            binding.etDescription.setError("Please enter work description");
            return false;

        } else return true;
    }
}