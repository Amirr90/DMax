package com.criteriontech.digidoctormax.repository;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.criteriontech.digidoctormax.interfaces.NewApiInterface;
import com.criteriontech.digidoctormax.model.ChatModel;
import com.criteriontech.digidoctormax.model.ClinicNumOfDocValue;
import com.criteriontech.digidoctormax.model.CollectedFeeValue;
import com.criteriontech.digidoctormax.model.GetPatientMedicationMainModel;
import com.criteriontech.digidoctormax.model.InvestigationModel;
import com.criteriontech.digidoctormax.model.NoOfPatientValue;
import com.criteriontech.digidoctormax.model.User;
import com.criteriontech.digidoctormax.model.VisitedPatientClinicValue;
import com.criteriontech.digidoctormax.model.VitalResponse;
import com.criteriontech.digidoctormax.request.ClinicDashboardDetails;
import com.criteriontech.digidoctormax.request.GenerateOTP;
import com.criteriontech.digidoctormax.request.VitalModel;
import com.criteriontech.digidoctormax.response.ClinicNumOfDocResp;
import com.criteriontech.digidoctormax.response.CollectedFeeResp;
import com.criteriontech.digidoctormax.response.NoOfPatientResp;
import com.criteriontech.digidoctormax.response.OtpResp;
import com.criteriontech.digidoctormax.response.VisitedPatientClinicResp;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;

import java.util.List;

import static com.criteriontech.digidoctormax.utils.ApiUtils.getPatientMedicationDetail2;

public class DoctorRepo {
    public MutableLiveData<OtpResp> memberModelMutableLiveData;
    public MutableLiveData<List<ClinicNumOfDocValue>> clinicListMutableLiveData;
    public MutableLiveData<List<CollectedFeeValue>> collectedFeeMutableLiveData;
    public MutableLiveData<List<VisitedPatientClinicValue>> visitedPatientMutableLiveData;
    public MutableLiveData<List<NoOfPatientValue>> numOfPatientMutableLiveData;
    public MutableLiveData<List<GetPatientMedicationMainModel>> prescriptionModelMutableLiveData;

    public MutableLiveData<List<ChatModel>> chatMutableLiveData;

    public MutableLiveData<List<InvestigationModel>> investigationMutableLiveData;


    public MutableLiveData<List<VitalResponse.VitalDateVise>> vitalsMutableLiveData;


    public LiveData<List<VitalResponse.VitalDateVise>> getVitals(VitalModel vitalModel, Activity activity) {
        vitalsMutableLiveData = new MutableLiveData<>();

        loadVitalData(vitalModel, activity);
        return vitalsMutableLiveData;

    }

    private void loadVitalData(final VitalModel vitalModel, final Activity activity) {

        //   showRequestDialog(activity);
        ApiUtils.getVitalsList(vitalModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                List<VitalResponse.VitalDateVise> vitalResponse = (List<VitalResponse.VitalDateVise>) obj;
                vitalsMutableLiveData.setValue(vitalResponse);
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

            }

        });
    }

    public LiveData<List<GetPatientMedicationMainModel>> getPrescriptionData(String memberId, Activity activity) {
        if (prescriptionModelMutableLiveData == null) {
            prescriptionModelMutableLiveData = new MutableLiveData<>();
        }
        loadPrescriptionData(memberId, activity);
        return prescriptionModelMutableLiveData;

    }


    private void loadPrescriptionData(String memberId, final Activity activity) {

        getPatientMedicationDetail2(memberId, activity, new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                List<GetPatientMedicationMainModel> patientMedicationMainModels = (List<GetPatientMedicationMainModel>) obj;
                if (patientMedicationMainModels != null) {
                    prescriptionModelMutableLiveData.setValue(patientMedicationMainModels);
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public LiveData<List<InvestigationModel>> getInvestigationData(User user) {
        if (investigationMutableLiveData == null) {
            investigationMutableLiveData = new MutableLiveData<>();
        }
        loadInvestigationData(user);
        return investigationMutableLiveData;

    }


    private void loadInvestigationData(User user) {

        ApiUtils.getInvestigationData(user, new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                List<InvestigationModel> models = (List<InvestigationModel>) obj;
                investigationMutableLiveData.setValue(models);
            }

            @Override
            public void onFailure(String msg) {
                Log.d("InvestigationData", "onFailed: " + msg);
            }

        });
    }


    public LiveData<List<ClinicNumOfDocValue>> clinicDashboardNumOfDoctors(ClinicDashboardDetails clinicDashboardDetails, Activity activity) {
        if (clinicListMutableLiveData == null) {
            clinicListMutableLiveData = new MutableLiveData<>();
        }
        loadClinicDashboardNumOfDoctors(clinicDashboardDetails, activity);
        return clinicListMutableLiveData;
    }

    public LiveData<List<CollectedFeeValue>> clinicDashboardFeeCollected(ClinicDashboardDetails clinicDashboardDetails, Activity activity) {
        if (collectedFeeMutableLiveData == null) {
            collectedFeeMutableLiveData = new MutableLiveData<>();
        }
        loadClinicFeeCollected(clinicDashboardDetails, activity);
        return collectedFeeMutableLiveData;
    }

    public LiveData<List<VisitedPatientClinicValue>> clinicDashboardVisitedPatient(ClinicDashboardDetails clinicDashboardDetails, Activity activity) {
        if (visitedPatientMutableLiveData == null) {
            visitedPatientMutableLiveData = new MutableLiveData<>();
        }
        loadClinicVisitedPatient(clinicDashboardDetails, activity);
        return visitedPatientMutableLiveData;
    }

    public LiveData<List<NoOfPatientValue>> drDashboardNumOfPatients(ClinicDashboardDetails clinicDashboardDetails, Activity activity) {
        if (numOfPatientMutableLiveData == null) {
            numOfPatientMutableLiveData = new MutableLiveData<>();
        }
        loadNumOfPatient(clinicDashboardDetails, activity);
        return numOfPatientMutableLiveData;
    }

    private void loadNumOfPatient(ClinicDashboardDetails clinicDashboardDetails, Activity activity) {
        if (AppUtils.isNetworkConnected(activity)) {
            AppUtils.showRequestDialog(activity);
            ApiUtils.drDashboardNumOfPatients(clinicDashboardDetails, new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
                    numOfPatientMutableLiveData.setValue(((NoOfPatientResp) obj).getResponseValue());
                    AppUtils.hideDialog();
                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    AppUtils.hideDialog();
                }
            });
        } else Toast.makeText(activity, "No internet connection!", Toast.LENGTH_SHORT).show();
    }

    private void loadClinicVisitedPatient(ClinicDashboardDetails clinicDashboardDetails, Activity activity) {
        if (AppUtils.isNetworkConnected(activity)) {
            AppUtils.showRequestDialog(activity);
            ApiUtils.clinicDashboardVisitedPatient(clinicDashboardDetails, new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
//                Toast.makeText(activity, ((VisitedPatientClinicResp) obj).getResponseMessage(), Toast.LENGTH_SHORT).show();
                    visitedPatientMutableLiveData.setValue(((VisitedPatientClinicResp) obj).getResponseValue());
                    AppUtils.hideDialog();
                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    AppUtils.hideDialog();
                }
            });
        } else Toast.makeText(activity, "No internet connection!", Toast.LENGTH_SHORT).show();
    }

    private void loadClinicDashboardNumOfDoctors(ClinicDashboardDetails clinicDashboardDetails, Activity activity) {
        if (AppUtils.isNetworkConnected(activity)) {
            AppUtils.showRequestDialog(activity);
            ApiUtils.clinicDashboardNumOfDoctors(clinicDashboardDetails, new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
                    clinicListMutableLiveData.setValue(((ClinicNumOfDocResp) obj).getResponseValue());
                    AppUtils.hideDialog();
                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    AppUtils.hideDialog();
                }
            });
        } else Toast.makeText(activity, "No internet connection!", Toast.LENGTH_SHORT).show();
    }

    private void loadClinicFeeCollected(ClinicDashboardDetails clinicDashboardDetails, Activity activity) {
        if (AppUtils.isNetworkConnected(activity)) {
            AppUtils.showRequestDialog(activity);
            ApiUtils.clinicDashboardFeeCollected(clinicDashboardDetails, new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
//                Toast.makeText(activity, ((CollectedFeeResp) obj).getResponseMessage(), Toast.LENGTH_SHORT).show();
                    collectedFeeMutableLiveData.setValue(((CollectedFeeResp) obj).getResponseValue());
                    AppUtils.hideDialog();
                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    AppUtils.hideDialog();
                }
            });
        } else Toast.makeText(activity, "No internet connection!", Toast.LENGTH_SHORT).show();
    }

    public LiveData<OtpResp> loadOtpData(GenerateOTP generateOTP, Activity activity) {
        if (memberModelMutableLiveData == null) {
            memberModelMutableLiveData = new MutableLiveData<>();
        }
        loadData(generateOTP, activity);
        return memberModelMutableLiveData;
    }

    public void loadData(GenerateOTP generateOTP, Activity activity) {
        ApiUtils.getOtp(generateOTP, new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                OtpResp otpResp = (OtpResp) obj;
                memberModelMutableLiveData.setValue(otpResp);
            }

            @Override
            public void onFailure(String msg) {
                Log.d("TAG", msg);
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public LiveData<List<ChatModel>> getChatData(NoOfPatientValue user) {

        if (chatMutableLiveData == null) {
            chatMutableLiveData = new MutableLiveData<>();
        }
        loadChatData(user);
        return chatMutableLiveData;


    }

    private void loadChatData(NoOfPatientValue user) {

        ApiUtils.getChatResponse(ApiUtils.getChatData(user), new NewApiInterface() {
            @Override
            public void onSuccess(Object obj) {
                List<ChatModel> chatModels = (List<ChatModel>) obj;
                chatMutableLiveData.setValue(chatModels);

            }

            @Override
            public void onFailed(String msg) {
                Log.d("loadChatData", "onFailed: " + msg);
            }
        });
    }
}
