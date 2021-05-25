package com.criteriontech.digidoctormax.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.FragmentDrCreateProfileBinding;
import com.criteriontech.digidoctormax.databinding.FragmentDrCreateProfileBindingImpl;
import com.criteriontech.digidoctormax.interfaces.UploadImageInterface;
import com.criteriontech.digidoctormax.model.DoctorProfileValue;
import com.criteriontech.digidoctormax.model.SpecialityValue;
import com.criteriontech.digidoctormax.request.DoctorProfile;
import com.criteriontech.digidoctormax.response.SpecialityResp;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.criteriontech.digidoctormax.activity.StartActivity.doctorProfileValue;

public class DrCreateProfile extends Fragment {
    private static final String TAG = "DrCreateProfile";
    FragmentDrCreateProfileBinding binding;
    NavController navController;
    DoctorProfile doctorProfile;
    Bundle bundle;
    private int year = 0, month = 0, day = 0;
    private Calendar c;
    List<Gender> genderList;
    ArrayAdapter<SpecialityValue> specialityAdp;
    ArrayAdapter<Gender> genderAdp;
    List<SpecialityValue> specialityList;

    Integer specialityId = null;

    String ProfilePath;

    boolean isPicChange = false;

    String imagePath = null;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDrCreateProfileBinding.inflate(getLayoutInflater());
        specialityList = new ArrayList<>();
        genderList = new ArrayList<>();
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        genderList.add(0, new Gender(0, "-Select Gender-"));
        genderList.add(1, new Gender(1, "Male"));
        genderList.add(2, new Gender(2, "Female"));


        binding.imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadProfilePictures();
            }
        });


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

    private void UploadProfilePictures() {

        ImagePicker.Companion.with(this)
                .crop(4f, 4f)//Crop image(Optional), Check Customization for more option
                .compress(512)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (null != data) {
                Uri uri = data.getData();

                binding.imageView6.setImageURI(uri);
                File file = null;
                try {
                    file = FileUtil.from(requireActivity(), uri);

                    uploadImage(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }


    }

    private void uploadImage(File file) throws IOException {


        AppUtils.showRequestDialog(requireActivity());
        ApiUtils.uploadProfileImage(file, new UploadImageInterface() {

            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<String> Path = (List<String>) o;
                Log.d(TAG, "ImagePath: " + Path.get(0));
                imagePath = Path.get(0);
                binding.imageView6.setTag("1");
                Log.d(TAG, "onSuccess: " + imagePath);
            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        doctorProfile = new DoctorProfile();
        bundle = new Bundle();


        binding.clAddTimeSlot.setOnClickListener(view1 -> {


            int user = Integer.parseInt(String.valueOf(SharedPrefManager.getInstance(getActivity()).getUser().getIsEraUser()));
            Log.d(TAG, "onViewCreated: " + imagePath);
            doctorProfileValue = binding.getRegistration();

            doctorProfileValue.setSpecialityId(specialityList.get(binding.etSpecialities.getSelectedItemPosition()).getId());
            doctorProfileValue.setDepartmentId(specialityList.get(binding.etSpecialities.getSelectedItemPosition()).getId());
            doctorProfileValue.setAddress(binding.etAdd.getText().toString().trim());
            doctorProfileValue.setReVisitCount(binding.EtRevisit.getText().toString().trim());
            doctorProfileValue.setFollowUpDuration(binding.EtValidity.getText().toString().trim());
            doctorProfileValue.setProfilePhotoPath(imagePath);


            try {
                doctorProfileValue.setGender(String.valueOf(binding.spnGender.getSelectedItemPosition()));
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "onViewCreated: " + e.getLocalizedMessage());
            }


            if (doctorProfileValue.getName().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter Doctor name!", Toast.LENGTH_SHORT).show();
                binding.etClinicNam.setError("Please enter Doctor name");
                return;
            }

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
            if (binding.etDob.getText().toString().trim().equals("")) {
                Toast.makeText(requireActivity(), "Please enter dob!", Toast.LENGTH_SHORT).show();
                binding.etDob.setError("Please enter dob");
                return;
            }
            if (binding.spnGender.getSelectedItemPosition() == 0) {
                Toast.makeText(requireActivity(), "Please select gender!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (doctorProfileValue.getAddress().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter Address", Toast.LENGTH_SHORT).show();
                binding.etAdd.setError("Please enter Address");
                return;

            }
            if (doctorProfileValue.getReVisitCount().isEmpty()) {

                if (doctorProfileValue.getReVisitCount().equals("10")) {
                    Toast.makeText(requireActivity(), "Revisit Count Must be less than 10!", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(requireActivity(), "Please Enter Revisit Count", Toast.LENGTH_SHORT).show();
                binding.EtRevisit.setError("Please enter Revisit count");
                return;
            }
            if (doctorProfileValue.getFollowUpDuration().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter follow up duration", Toast.LENGTH_SHORT).show();
                binding.EtValidity.setError("Please enter follow up duration");
                return;
            }
            if (doctorProfileValue.getYearOfExperience().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter year of experience!", Toast.LENGTH_SHORT).show();
                binding.etYearOfExp.setError("Please enter year of experience");
                return;
            }
            if (doctorProfileValue.getDegree().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter degree!", Toast.LENGTH_SHORT).show();
                binding.etDegree.setError("Please enter degree");
                return;
            }
            if (doctorProfileValue.getRegistrationNo().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter registration No.!", Toast.LENGTH_SHORT).show();
                binding.etRegistrationNo.setError("Please enter registration No.");
                return;
            }
            if (doctorProfileValue.getDrFee().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter fee!", Toast.LENGTH_SHORT).show();
                binding.etFee.setError("Please enter fee");
                return;
            }

//            if (doctorProfileValue.getReVisitCount().equals(0) || binding.EtRevisit.getText().equals("0")) {
//                Toast.makeText(requireActivity(), "Validity must be zero", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (doctorProfileValue.getFollowUpDuration().equals("0") || binding.EtValidity.getText().equals("0")) {
//                Toast.makeText(requireActivity(), "Revisit must be zero", Toast.LENGTH_SHORT).show();
//                return;
//            }

            int followup = Integer.parseInt(doctorProfileValue.getFollowUpDuration());
            int followupEt = Integer.parseInt(binding.EtValidity.getText().toString());

            int revisit = Integer.parseInt(doctorProfileValue.getReVisitCount());
            int revistet = Integer.parseInt(binding.EtRevisit.getText().toString());

            if (doctorProfileValue.getReVisitCount().equals("0") || binding.EtRevisit.getText().equals("0") && followup > 1) {
                Toast.makeText(requireActivity(), "Revisit Can not be zero", Toast.LENGTH_SHORT).show();
                return;
            }


            if (doctorProfileValue.getFollowUpDuration().equals("0") || binding.EtValidity.getText().equals("0") && revistet > 1) {
                Toast.makeText(requireActivity(), "Validity can not be zero", Toast.LENGTH_SHORT).show();
                return;
            }


            if (binding.etFee.getText().equals("0") || doctorProfileValue.getDrFee().equals("0") && user == 0) {
                Toast.makeText(requireActivity(), "Fees Can not be Zero!", Toast.LENGTH_SHORT).show();
                binding.etFee.setError("Fees Can not be Zero!");
                return;
            }


            if (doctorProfileValue.getWorkDescription().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter work description!", Toast.LENGTH_SHORT).show();
                binding.etDescription.setError("Please enter work description");
                return;
            }


            if (binding.imageView6.getTag().equals("0")) {
                Toast.makeText(requireActivity(), "Please upload profile picture!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!getArguments().getBoolean("login")) {

                doctorProfileValue.setUserMobileNo(DrRegistration.drRegistration.getMobileNo());
                doctorProfileValue.setClinicName(DrRegistration.drRegistration.getClinicName());
                doctorProfileValue.setName(DrRegistration.drRegistration.getDoctorName());
                doctorProfileValue.setEmailId(DrRegistration.drRegistration.getEmailId());
                doctorProfileValue.setProfilePhotoPath(imagePath);

                binding.setRegistration(doctorProfileValue);

                if (binding.etSpecialities.getSelectedItemPosition() == 0) {
                    Toast.makeText(requireActivity(), "Please select specialty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                doctorProfileValue.setSpecialityId(specialityList.get(binding.etSpecialities.getSelectedItemPosition()).getId());
                doctorProfileValue.setDepartmentId(specialityList.get(binding.etSpecialities.getSelectedItemPosition()).getId());
                doctorProfileValue.setSpeciality(specialityList.get(binding.etSpecialities.getSelectedItemPosition()).getSpecialityName());

            } else {
                binding.etClinicName.setEnabled(false);
            }
            if (!getArguments().getBoolean("add")) {
                bundle.putBoolean("login", getArguments().getBoolean("login"));
                bundle.putBoolean("add", getArguments().getBoolean("add"));
                if (getArguments().getBoolean("login"))
                    navController.navigate(R.id.action_drCreateProfile3_to_selectSlotFragment2, bundle);
                else
                    navController.navigate(R.id.action_drCreateProfile_to_selectSlotFragment, bundle);
            } else {
                if (AppUtils.isNetworkConnected(requireActivity())) {
                    ApiUtils.addNewDoctor(doctorProfileValue, new ApiCallbackInterface() {
                        @Override
                        public void onSuccess(Object obj) {
                            AppUtils.hideDialog();
                            bundle.putBoolean("login", true);
                            navController.navigate(R.id.action_drCreateProfile2_to_selectSlotFragment3, bundle);
                        }

                        @Override
                        public void onFailure(String msg) {
                            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                            AppUtils.hideDialog();
                        }
                    });
                } else
                    Toast.makeText(requireActivity(), "No internet connection !", Toast.LENGTH_SHORT).show();
            }
        });

        if (getArguments().getBoolean("login")) {

            /*binding.txtSpecialities.setVisibility(View.VISIBLE);
            binding.etSpecialities.setVisibility(View.GONE);*/

            if (SharedPrefManager.getInstance(requireActivity()).getLoginType() == 2) {
                binding.textView2.setText(getString(R.string.update_your_profile_to_join_digimax));
                doctorProfile.setId(SharedPrefManager.getInstance(requireActivity()).getUser().getId());
                doctorProfile.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
                if (AppUtils.isNetworkConnected(requireActivity())) {
                    AppUtils.showRequestDialog(requireActivity());
                    ApiUtils.getDoctorProfile(doctorProfile, new ApiCallbackInterface() {
                        @Override
                        public void onSuccess(Object obj) {

                            binding.setRegistration(((DoctorProfileValue) obj));

                            doctorProfileValue = binding.getRegistration();
                            if (binding.getRegistration().getGender().equalsIgnoreCase("male"))
                                binding.spnGender.setSelection(1);
                            else if (binding.getRegistration().getGender().equalsIgnoreCase("female"))
                                binding.spnGender.setSelection(2);
                            doctorProfileValue.setId(((DoctorProfileValue) obj).getId());
                            if (((DoctorProfileValue) obj).getTimeSlots().equals(""))
                                bundle.putString("slotList", "[]");
                            else
                                bundle.putString("slotList", ((DoctorProfileValue) obj).getTimeSlots());
                            Integer ID = binding.getRegistration().getDepartmentId();
                            if (null != ID)
                                for (int a = 0; a < specialityList.size(); a++) {
                                    if (specialityList.get(a).getId().equals(ID)) {
                                        binding.etSpecialities.setSelection(a);
                                        specialityId = specialityList.get(a).getId();
                                    }
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
            } else {

            }


        } else {
            binding.txtSpecialities.setVisibility(View.GONE);
            binding.etSpecialities.setVisibility(View.VISIBLE);
        }
        binding.etDob.setOnClickListener(view1 ->

        {
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

    public class Gender {
        public int id;
        public String gender;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Gender(int id, String gender) {
            this.id = id;
            this.gender = gender;
        }

        @Override
        public String toString() {
            return gender;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}