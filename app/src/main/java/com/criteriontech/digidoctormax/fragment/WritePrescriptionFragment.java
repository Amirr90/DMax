package com.criteriontech.digidoctormax.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.adapters.DrugInteractionAdapter;
import com.criteriontech.digidoctormax.databinding.FragmentWritePrescriptionBinding;
import com.criteriontech.digidoctormax.databinding.InnerPrescriptionBinding;
import com.criteriontech.digidoctormax.databinding.InnerviewProblemBinding;
import com.criteriontech.digidoctormax.databinding.SymImageBinding;
import com.criteriontech.digidoctormax.interfaces.NewApiInterface;
import com.criteriontech.digidoctormax.jitsiVideoCall.InitVideoCallActivity;
import com.criteriontech.digidoctormax.jitsiVideoCall.VideoCallActivity;
import com.criteriontech.digidoctormax.model.DrugInteractionModel;
import com.criteriontech.digidoctormax.model.DrugIntrcationModel;
import com.criteriontech.digidoctormax.model.FrequencyList;
import com.criteriontech.digidoctormax.model.LoginValue;
import com.criteriontech.digidoctormax.model.MedicineDetail;
import com.criteriontech.digidoctormax.model.MonitorResponse;
import com.criteriontech.digidoctormax.model.SuggestedTestDetail;
import com.criteriontech.digidoctormax.model.SymptomFilePath;
import com.criteriontech.digidoctormax.request.MedicationDetail;
import com.criteriontech.digidoctormax.request.SavePrescription;
import com.criteriontech.digidoctormax.request.SubTest;
import com.criteriontech.digidoctormax.request.VideoCall;
import com.criteriontech.digidoctormax.request.VoiceCall;
import com.criteriontech.digidoctormax.response.GetAllTestResp;
import com.criteriontech.digidoctormax.response.MedicationDetailResp;
import com.criteriontech.digidoctormax.response.SavePrescriptionResp;
import com.criteriontech.digidoctormax.response.VideoCallRes;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class WritePrescriptionFragment extends Fragment {
    FragmentWritePrescriptionBinding binding;
    NavController navController;
    MedicationDetail patientMedicationDetails;
    List<String> problemList;
    Chip chip;
    Bundle bundle;
    Gson gson;
    AddMedAdp addMedAdp;
    SubTest subTest;
    int j = 0;

    DrugInteractionAdapter drugInteractionAdapter;
    List<DrugIntrcationModel> GetDrugInteraction = new ArrayList<>();
    List<FrequencyList> frequencyList;
    List<MedicineDetail> medicineDetails;
    List<MedicineDetail> addedMedDetails;
    MedicineDetail itemMedicine;
    ArrayAdapter<SuggestedTestDetail> suggestedTestAdp;
    SavePrescription savePrescription;
    ArrayAdapter<MedicineDetail> medicineDetailAdp;
    ArrayAdapter<FrequencyList> frequencyAdp;
    MedicationDetailResp medicationDetailResp;
    //BottomDialog dialog;
    int monitor;
    String ids;
    AlertDialog.Builder alertDialog;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWritePrescriptionBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        patientMedicationDetails = new MedicationDetail();
        savePrescription = new SavePrescription();
        medicineDetails = new ArrayList<>();
        frequencyList = new ArrayList<>();
        addedMedDetails = new ArrayList<>();
        binding.edtMedName.setThreshold(1);


        binding.edtInvestigations.setThreshold(1);
        subTest = new SubTest();
        addMedAdp = new AddMedAdp(addedMedDetails);
        binding.rvMed.setAdapter(addMedAdp);
        gson = new Gson();
        bundle = new Bundle();
        problemList = new ArrayList<>();
        frequencyList.add(new FrequencyList(1, "-Select-"));
        savePrescription.setAppointmentID(getArguments().getInt("appointmentId"));
        savePrescription.setServiceProviderDetailsID(SharedPrefManager.getInstance(requireActivity()).getUser().getId());
        savePrescription.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
        binding.setPrescription(savePrescription);
        binding.btnAddVital.setOnClickListener(view1 -> {
            bundle.putInt("memberId", medicationDetailResp.getResponseValue().get(0).getUserDetails().get(0).getMemberId());
            navController.navigate(R.id.action_writePrescriptionFragment_to_addvitalfragment, bundle);
        });

        binding.MonitorSwitch.setOnClickListener(v -> {
            AppUtils.showRequestDialog(requireActivity());
            MonitorResponse monitorResponse = new MonitorResponse();

            monitorResponse.setIsMonitoring(binding.MonitorSwitch.isChecked() ? 1 : 0);
            monitorResponse.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
            monitorResponse.setAppointmentId(getArguments().getInt("appointmentId"));
            ApiUtils.updatemonitor(monitorResponse, new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
                    AppUtils.hideDialog();
                    Toast.makeText(requireActivity(), binding.MonitorSwitch.isChecked() ? "Patient is monitoring !!" : "Patient is not monitoring !!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String msg) {
                    AppUtils.hideDialog();
                    Toast.makeText(requireActivity(), "something went wrong !!, try again", Toast.LENGTH_SHORT).show();
                }
            });
        });


        binding.txtFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    itemMedicine.setFrequencyId(frequencyList.get(i).getId());
                    itemMedicine.setFrequencyName(frequencyList.get(i).getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnVoice.setOnClickListener(view1 -> hitVoiceCall());
        binding.btnVideoCall.setOnClickListener(view1 -> {
            //hitVideoCall();

            //startActivity(new Intent(requireActivity(), InitVideoCallActivity.class));

            LoginValue loginValue = SharedPrefManager.getInstance(requireActivity()).getUser();
            startActivity(new Intent(requireActivity(), InitVideoCallActivity.class)
                    .putExtra("docId", String.valueOf(loginValue.getId()))
                    .putExtra(AppUtils.DOCTOR_NAME, String.valueOf(loginValue.getName()))
                    .putExtra(AppUtils.PATIENT_NAME, "" + binding.tvdoctor.getText().toString())
                    .putExtra("memberId", "" + medicationDetailResp.getResponseValue().get(0).getUserDetails().get(0).getMemberId()));



           /* LoginValue loginValue = SharedPrefManager.getInstance(requireActivity()).getUser();
            startActivity(new Intent(requireActivity(), VideoCallActivity.class)
                    .putExtra("docId",String.valueOf(loginValue.getId()))
                    .putExtra("memberId", "" + medicationDetailResp.getResponseValue().get(0).getUserDetails().get(0).getMemberId()));


            */
        });
        binding.btnSubmit.setOnClickListener(view1 -> {

            if (TextUtils.isEmpty(binding.edtMedName.getText().toString())) {
                Toast.makeText(requireActivity(), "Kindly Add Medicine first", Toast.LENGTH_SHORT).show();
            }

            try {
                if (!addedMedDetails.isEmpty()) {
                    savePrescription = binding.getPrescription();
                    savePrescription.setDtDataTableMedicine(getDtTableValue(addedMedDetails));
                    savePrescription.setTestID(csv(medicationDetailResp.getResponseValue().get(0).getSuggestedTestDetails()));
                    if (AppUtils.isNetworkConnected(requireActivity())) {
                        ApiUtils.drMedication(savePrescription, new ApiCallbackInterface() {
                            @Override
                            public void onSuccess(Object obj) {
                                Toast.makeText(requireActivity(), ((SavePrescriptionResp) obj).getResponseMessage(), Toast.LENGTH_SHORT).show();
                                navController.navigateUp();
                            }

                            @Override
                            public void onFailure(String msg) {
                                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else
                        Toast.makeText(requireActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        binding.edtInvestigations.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                subTest.setSubtestName(String.valueOf(charSequence).trim());
                if (AppUtils.isNetworkConnected(requireActivity())) {
                    ApiUtils.getAllTest(subTest, new ApiCallbackInterface() {
                        @Override
                        public void onSuccess(Object obj) {
                            suggestedTestAdp = new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, ((GetAllTestResp) obj).getResponseValue());
                            binding.edtInvestigations.setAdapter(suggestedTestAdp);
                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });
                } else
                    Toast.makeText(requireActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edtInvestigations.setOnItemClickListener((adapterView, view1, i, l) -> {
            Object item = adapterView.getItemAtPosition(i);
            if (item instanceof SuggestedTestDetail) {
                medicationDetailResp.getResponseValue().get(0).getSuggestedTestDetails().add((SuggestedTestDetail) item);
            }
            binding.chpInvestigation.removeAllViews();
            for (j = 0; j < (medicationDetailResp.getResponseValue().get(0).getSuggestedTestDetails().size()); j++) {
                chip = (Chip) getLayoutInflater().inflate(R.layout.item_chip, binding.chpInvestigation, false);
                chip.setCheckable(false);
                chip.setText(medicationDetailResp.getResponseValue().get(0).getSuggestedTestDetails().get(j).getName());
                binding.chpInvestigation.addView(chip);
                chip.setOnCloseIconClickListener(v -> {
                    try {
                        binding.chpInvestigation.removeView(v);
                        medicationDetailResp.getResponseValue().get(0).getSuggestedTestDetails().remove(medicationDetailResp.getResponseValue().get(0).getSuggestedTestDetails().size() - 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            }
            binding.edtInvestigations.setText("");
        });
        binding.chpInvestigation.setOnClickListener(view1 -> {
            binding.chpInvestigation.removeView(view1.getRootView());
            binding.chpInvestigation.invalidate();
        });

        binding.edtMedName.setOnItemClickListener((adapterView, view1, i, l) -> {


            List<String> medicineIds = addMedAdp.getMedicineList();

            StringBuilder builder = new StringBuilder();
            for (int a = 0; a < medicineIds.size(); a++) {
                Log.d("TAG", "onViewCreated: " + medicineIds.get(a));
                builder.append(medicineIds.get(a));
                builder.append(",");
            }


            Object item = adapterView.getItemAtPosition(i);
            if (item instanceof MedicineDetail) {
                itemMedicine = (MedicineDetail) item;
                for (int j = 0; j < frequencyList.size(); j++) {
                    if (itemMedicine.getFrequencyId().equals(frequencyList.get(j).getId()))
                        binding.txtFrequency.setSelection(j);

                }
                builder.append(itemMedicine.getId());
                binding.txtFrequency.setEnabled(true);

            }

            DrugInteractionModel drugInteractionModel = new DrugInteractionModel();
            // drugInteractionModel.setMedicineId(medicineDetails.get(i).getId());
            drugInteractionModel.setMedicineId(builder.toString());
            // drugInteractionModel.setMedicineId("393,418,577");
            drugInteractionModel.setMobileNo(SharedPrefManager.getInstance(getActivity()).getUser().getMobileNo());
            drugInteractionModel.setUserId(2);

            drugInteractionAdapter = new DrugInteractionAdapter(GetDrugInteraction, requireActivity());

            ApiUtils.checkDrugInteraction(drugInteractionModel, new NewApiInterface() {
                @Override
                public void onSuccess(Object obj) {
                    List<DrugIntrcationModel> models = (List<DrugIntrcationModel>) obj;
                    GetDrugInteraction.clear();
                    GetDrugInteraction.addAll(models);
                    drugInteractionAdapter.notifyDataSetChanged();
                    showDrugInteractionDialog(obj, medicineIds);
                }

                @Override
                public void onFailed(String msg) {
                    Toast.makeText(requireActivity(), "" + msg, Toast.LENGTH_SHORT).show();


                }
            });

        });


        binding.btnAdd.setOnClickListener((View view1) -> {


            if (TextUtils.isEmpty(binding.edtMedName.getText())) {
                Toast.makeText(requireActivity(), "Fill Medicine Name", Toast.LENGTH_SHORT).show();
                binding.edtMedName.setError("Fill Medicine Name");
            }

            if (itemMedicine == null) {
                return;
            }
            if (itemMedicine.getMedicineName() != null) {
                itemMedicine.setDuration(binding.txtDuration.getText().toString().trim());
                itemMedicine.setRemark(binding.edtRemark.getText().toString().trim());
                addedMedDetails.add(itemMedicine);
                binding.rvMed.post(this::run);
                binding.edtMedName.setText("");
                binding.txtDuration.setText("");
                binding.edtRemark.setText("");
                itemMedicine = null;
                binding.txtFrequency.setSelection(0);
                binding.txtFrequency.setEnabled(false);
            }

        });
        patientMedicationDetails.setAppointmentId(getArguments().getInt("appointmentId"));
        patientMedicationDetails.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());

        if (AppUtils.isNetworkConnected(requireActivity())) {
            AppUtils.showRequestDialog(requireActivity());
            ApiUtils.getMedicationDetails(patientMedicationDetails, new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
                    medicationDetailResp = ((MedicationDetailResp) obj);
                    if (!medicationDetailResp.getResponseValue().isEmpty())
                        binding.setPatient(medicationDetailResp.getResponseValue().get(0));
                    if (!(medicationDetailResp.getResponseValue().get(0).getUserDetails().get(0).getSignSymptom().equalsIgnoreCase(""))) {
                        problemList = Arrays.asList(medicationDetailResp.getResponseValue().get(0).getUserDetails().get(0).getSignSymptom().split(", "));
                        binding.rvProblem.setAdapter(new ProblemAdp(problemList));
                    }
                    medicationDetailResp.getResponseValue().get(0).getSuggestedTestDetails().removeAll(medicationDetailResp.getResponseValue().get(0).getSuggestedTestDetails());
                    if (!(medicationDetailResp.getResponseValue().get(0).getSuggestedTestDetails().isEmpty())) {
                        for (int i = 0; i < (medicationDetailResp.getResponseValue().get(0).getSuggestedTestDetails().size()); i++) {
                            chip = (Chip) getLayoutInflater().inflate(R.layout.item_chip, binding.chpInvestigation, false);
                            chip.setText(medicationDetailResp.getResponseValue().get(0).getSuggestedTestDetails().get(i).getName());
                            binding.chpInvestigation.addView(chip);
                        }
                    }
                    if (medicationDetailResp.getResponseValue().get(0).getUserDetails().get(0).getFilePath() != null) {
                        TypeToken<List<SymptomFilePath>> token = new TypeToken<List<SymptomFilePath>>() {
                        };
                        List<SymptomFilePath> symptomFilePath = gson.fromJson(medicationDetailResp.getResponseValue().get(0).getUserDetails().get(0).getFilePath(), token.getType());
                        binding.rvAttachment.setAdapter(new ProbImageAdp(symptomFilePath));
                    }
                    if (!medicationDetailResp.getResponseValue().get(0).getMedicineDetails().isEmpty()) {
                        medicineDetails.addAll(medicationDetailResp.getResponseValue().get(0).getMedicineDetails());
                        if (isAdded())
                            medicineDetailAdp = new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, medicineDetails);
                        binding.edtMedName.setAdapter(medicineDetailAdp);
                    }
                    if (!medicationDetailResp.getResponseValue().get(0).getFrequencyDetails().isEmpty()) {
                        frequencyList.addAll(medicationDetailResp.getResponseValue().get(0).getFrequencyDetails());
                        frequencyAdp = new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, frequencyList);
                        binding.txtFrequency.setAdapter(frequencyAdp);
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

        //show Past MedicalHistory Page
        //  binding.textView28.setOnClickListener(view12 -> ShowBottomDialog());
    }

    private void showDrugInteractionDialog(Object obj, List<String> medicineIds) {


        Log.d("TAG", "showDrugInteractionDialog: " + obj);

        Toast.makeText(requireActivity(), "showing  drug Intraction Dialog !!", Toast.LENGTH_SHORT).show();

        alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.drug_interaction_layout, null);
        Button btnRemoveMedicine = (Button) view.findViewById(R.id.btnRemoveMdcn);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView2);
        Button btncont = view.findViewById(R.id.button2);
        recyclerView.setAdapter(drugInteractionAdapter);
        btnRemoveMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdded = addMedAdp.removeMedicine(medicineIds);
                Toast.makeText(requireActivity(), isAdded ? "Removed " : "unable to remove medicine ,try again", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.setView(view);
        alertDialog.setNegativeButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

        btncont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private String csv(List<SuggestedTestDetail> suggestedTestDetails) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < suggestedTestDetails.size() - 1; i++) {
            sb.append(suggestedTestDetails.get(i).getId());
            sb.append(",");
        }
        if (suggestedTestDetails.size() > 0) {
            sb.append(suggestedTestDetails.get(suggestedTestDetails.size() - 1).getId());
        }
        return sb.toString();
    }

    private String getDtTableValue(List<MedicineDetail> medicineDetail) throws JSONException {
        JSONArray dtTableArray = new JSONArray();
        JSONObject jsonObject;
        for (int i = 0; i < medicineDetail.size(); i++) {
            jsonObject = new JSONObject();
            jsonObject.put("medicineId", medicineDetail.get(i).getId());
            jsonObject.put("dosageFormId", medicineDetail.get(i).getDosageId());
            jsonObject.put("strength", medicineDetail.get(i).getStrength());
            jsonObject.put("doseUnitId", medicineDetail.get(i).getDoseUnitId());
            jsonObject.put("frequencyId", medicineDetail.get(i).getFrequencyId());
            jsonObject.put("durationInDays", medicineDetail.get(i).getDuration());
            dtTableArray.put(jsonObject);
        }
        return String.valueOf(dtTableArray);
    }

/*    private void ShowBottomDialog() {
        dialog = new BottomDialog(requireActivity());
        dialog.title("Past Medical History");
        dialog.canceledOnTouchOutside(true);
        dialog.cancelable(true);
        dialog.inflateMenu(R.menu.menu_bottom_dialog);
        dialog.setOnItemSelectedListener(id -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", getArguments().getInt("memberId"));
            bundle.putInt("id", getArguments().getInt("appointmentId"));
            switch (id) {
                case R.id.investigation:
                    navController.navigate(R.id.action_writePrescriptionFragment_to_investigationsFragment, bundle);
                    return true;
                case R.id.prescription:
                    navController.navigate(R.id.action_writePrescriptionFragment_to_prescriptionsFragment, bundle);
                    return true;
                case R.id.vital:
                    navController.navigate(R.id.action_writePrescriptionFragment_to_vitalsFragment, bundle);
                    return true;
                default:
                    return false;
            }
        });
        dialog.show();
    }*/

    private void hitVoiceCall() {
        VoiceCall voiceCall = new VoiceCall();
        voiceCall.setCustomer_number(getArguments().getString("phone"));
        voiceCall.setAgent_number("+91" + SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
        voiceCall.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());

        if (AppUtils.isNetworkConnected(requireActivity())) {
            ApiUtils.callingAPI(voiceCall, new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
                    Toast.makeText(requireActivity(), "Call initiated successfully!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void hitVideoCall() {
        String roomName = String.valueOf(System.currentTimeMillis());
        LoginValue loginValue = SharedPrefManager.getInstance(requireActivity()).getUser();
        VideoCall videoCall = new VideoCall();
        videoCall.setCallType("1");
        videoCall.setIsEraUser(String.valueOf(loginValue.getIsEraUser()));
        videoCall.setDoctorId(String.valueOf(loginValue.getId()));
        videoCall.setMemberId(String.valueOf(medicationDetailResp.getResponseValue().get(0).getUserDetails().get(0).getMemberId()));
        videoCall.setRoomName(roomName);
        videoCall.setUserMobileNo(loginValue.getMobileNo());

        if (AppUtils.isNetworkConnected(requireActivity())) {
            ApiUtils.videoCall(videoCall, new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
                    try {

                        VideoCallRes responseModel = (VideoCallRes) obj;
                 /*       startActivity(new Intent(requireActivity(), VideoActivity.class).putExtra("roomName", roomName)
                                .putExtra("accessToken", responseModel.getDoctorTwilioAccessToken())
                                .putExtra("memberId", videoCall.getMemberId()));*/
                        
                       /* Bundle bundle = new Bundle();
                        bundle.putString("accessToken", responseModel.getDoctorTwilioAccessToken());
                        bundle.putString("memberId", videoCall.getMemberId());
                        bundle.putString("roomName", roomName);
                        navController.navigate(R.id.action_writePrescriptionFragment_to_pipVideoCallFragment, bundle);*/

                        startActivity(new Intent(requireActivity(), VideoCallActivity.class)
                                .putExtra("roomName", roomName)
                                .putExtra("accessToken", responseModel.getDoctorTwilioAccessToken())
                                .putExtra("memberId", videoCall.getMemberId()));


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("TAG", "onSuccess: " + e.getLocalizedMessage());
                    }

                    AppUtils.hideDialog();
                }

                @Override
                public void onFailure(String msg) {

                }
            });
        } else
            Toast.makeText(requireActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
    }

    private void run() {
        addMedAdp.notifyDataSetChanged();
    }

    public class ProblemAdp extends RecyclerView.Adapter<ProblemAdp.ViewHolder> {
        List<String> problemList;

        ProblemAdp(List<String> problemList) {
            this.problemList = problemList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            InnerviewProblemBinding innerviewProblemBinding = InnerviewProblemBinding.inflate(layoutInflater, parent, false);
            return new ViewHolder(innerviewProblemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.innerviewProblemBinding.setProblem(problemList.get(position));
        }

        @Override
        public int getItemCount() {
            return problemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            InnerviewProblemBinding innerviewProblemBinding;

            public ViewHolder(InnerviewProblemBinding innerviewProblemBinding) {
                super(innerviewProblemBinding.getRoot());
                this.innerviewProblemBinding = innerviewProblemBinding;
            }
        }
    }

    public class ProbImageAdp extends RecyclerView.Adapter<ProbImageAdp.ViewHolder> {
        List<SymptomFilePath> problemList;

        ProbImageAdp(List<SymptomFilePath> problemList) {
            this.problemList = problemList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            SymImageBinding innerViewProblemBinding = SymImageBinding.inflate(layoutInflater, parent, false);
            return new ViewHolder(innerViewProblemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            SymptomFilePath symptomFilePath = problemList.get(position);
            holder.symImageBinding.setImage(problemList.get(position));


            holder.symImageBinding.symptomimag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppUtils.showImagePreview(requireActivity(), symptomFilePath.getFilePath());

                }
            });
        }

        @Override
        public int getItemCount() {
            return problemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            SymImageBinding symImageBinding;

            public ViewHolder(SymImageBinding symImageBinding) {
                super(symImageBinding.getRoot());
                this.symImageBinding = symImageBinding;
            }
        }
    }

    public class AddMedAdp extends RecyclerView.Adapter<AddMedAdp.ViewHolder> {
        List<MedicineDetail> medicineDetails;

        public AddMedAdp(List<MedicineDetail> medicineDetails) {
            this.medicineDetails = medicineDetails;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            InnerPrescriptionBinding innerPrescriptionBinding = InnerPrescriptionBinding.inflate(layoutInflater, parent, false);
            return new ViewHolder(innerPrescriptionBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.innerPrescriptionBinding.setMed(medicineDetails.get(position));
            holder.innerPrescriptionBinding.imgDel.setOnClickListener(view -> {
                medicineDetails.remove(position);
                addMedAdp.notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return medicineDetails.size();
        }

        public List<String> getMedicineList() {
            List<String> medicineIds = new ArrayList<>();
            if (null == medicineDetails)
                medicineDetails = new ArrayList<>();

            else {
                for (MedicineDetail detail : medicineDetails)
                    medicineIds.add(String.valueOf(detail.getId()));
            }
            return medicineIds;
        }

        public Boolean removeMedicine(List<String> medicineIds) {

            if (null == medicineDetails)
                medicineDetails = new ArrayList<>();
            for (String medicineId : medicineIds)
                for (int a = 0; a < medicineDetails.size(); a++) {
                    Log.d("TAG", "removeMedicine: Idget " + medicineId);
                    Log.d("TAG", "removeMedicine: IdInAList " + medicineDetails.get(a).getId());
                    Log.d("TAG", "-------------------- ");

                    String id = String.valueOf(medicineDetails.get(a).getId());
                    if (id.equals(medicineId)) {
                        medicineDetails.remove(a);
                        Log.d("TAG", "removeMedicine: removed Ids " + id);

                    }
                }

            notifyDataSetChanged();
            return true;

        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            InnerPrescriptionBinding innerPrescriptionBinding;

            public ViewHolder(InnerPrescriptionBinding innerPrescriptionBinding) {
                super(innerPrescriptionBinding.getRoot());
                this.innerPrescriptionBinding = innerPrescriptionBinding;
            }
        }
    }
}