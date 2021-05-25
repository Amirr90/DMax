package com.criteriontech.digidoctormax.utils;

import android.app.Activity;
import android.util.Log;

import com.criteriontech.digidoctormax.activity.StartActivity;
import com.criteriontech.digidoctormax.fragment.home.DrHomeActivity;
import com.criteriontech.digidoctormax.interfaces.NewApiInterface;
import com.criteriontech.digidoctormax.interfaces.UploadImageInterface;
import com.criteriontech.digidoctormax.model.ChatModel;
import com.criteriontech.digidoctormax.model.ChatResponse;
import com.criteriontech.digidoctormax.model.CheckSlotAvailabilityRes;
import com.criteriontech.digidoctormax.model.CheckTimeSlotModel;
import com.criteriontech.digidoctormax.model.DoctorProfileValue;
import com.criteriontech.digidoctormax.model.DrugInteractionModel;
import com.criteriontech.digidoctormax.model.GetAppointmentSlotsRes;
import com.criteriontech.digidoctormax.model.GetBanModel;
import com.criteriontech.digidoctormax.model.GetBankDetailsModel;
import com.criteriontech.digidoctormax.model.GetBankDetailsRes;
import com.criteriontech.digidoctormax.model.GetPatientMedicationMainModel;
import com.criteriontech.digidoctormax.model.GetPatientMedicationRes;
import com.criteriontech.digidoctormax.model.InvestigationRes;
import com.criteriontech.digidoctormax.model.LoginValue;
import com.criteriontech.digidoctormax.model.MonitorResponse;
import com.criteriontech.digidoctormax.model.NoOfPatientValue;
import com.criteriontech.digidoctormax.model.OnlineAppointmentSlots;
import com.criteriontech.digidoctormax.model.QuarantinePatientListRes;
import com.criteriontech.digidoctormax.model.ResponseModel;
import com.criteriontech.digidoctormax.model.SaveMultipleFileRes;
import com.criteriontech.digidoctormax.model.UpdateBankModel;
import com.criteriontech.digidoctormax.model.UpdateBankRes;
import com.criteriontech.digidoctormax.model.UpdateIsolationModel;
import com.criteriontech.digidoctormax.model.User;
import com.criteriontech.digidoctormax.model.VitalResponse;
import com.criteriontech.digidoctormax.model.WritePrescriptionForQuarantineModel;
import com.criteriontech.digidoctormax.request.AddNewDoctor;
import com.criteriontech.digidoctormax.request.AddVital;
import com.criteriontech.digidoctormax.request.ChangePwd;
import com.criteriontech.digidoctormax.request.ClinicDashboard;
import com.criteriontech.digidoctormax.request.ClinicDashboardDetails;
import com.criteriontech.digidoctormax.request.ClinicRegistration;
import com.criteriontech.digidoctormax.request.ClinicUpdate;
import com.criteriontech.digidoctormax.request.DoctorProfile;
import com.criteriontech.digidoctormax.request.DrRegistration;
import com.criteriontech.digidoctormax.request.ForgotPwd;
import com.criteriontech.digidoctormax.request.GenerateOTP;
import com.criteriontech.digidoctormax.request.Login;
import com.criteriontech.digidoctormax.request.MedicationDetail;
import com.criteriontech.digidoctormax.request.PatientMedicationDetails;
import com.criteriontech.digidoctormax.request.SavePrescription;
import com.criteriontech.digidoctormax.request.SubTest;
import com.criteriontech.digidoctormax.request.VideoCall;
import com.criteriontech.digidoctormax.request.VitalModel;
import com.criteriontech.digidoctormax.request.VoiceCall;
import com.criteriontech.digidoctormax.response.AddNewDoctorResp;
import com.criteriontech.digidoctormax.response.AddVitalResp;
import com.criteriontech.digidoctormax.response.ClinicDashboardResp;
import com.criteriontech.digidoctormax.response.ClinicNumOfDocResp;
import com.criteriontech.digidoctormax.response.ClinicRegistrationResp;
import com.criteriontech.digidoctormax.response.CollectedFeeResp;
import com.criteriontech.digidoctormax.response.DoctorProfileResp;
import com.criteriontech.digidoctormax.response.DrDashboardResp;
import com.criteriontech.digidoctormax.response.DrRegistrationResp;
import com.criteriontech.digidoctormax.response.GetAllTestResp;
import com.criteriontech.digidoctormax.response.GlobalResponse;
import com.criteriontech.digidoctormax.response.IsolationResponse;
import com.criteriontech.digidoctormax.response.LoginResp;
import com.criteriontech.digidoctormax.response.MedicationDataResp;
import com.criteriontech.digidoctormax.response.MedicationDetailResp;
import com.criteriontech.digidoctormax.response.NoOfPatientResp;
import com.criteriontech.digidoctormax.response.OnlineAppointmentResp;
import com.criteriontech.digidoctormax.response.OtpResp;
import com.criteriontech.digidoctormax.response.PatientMedicationResp;
import com.criteriontech.digidoctormax.response.SavePrescriptionResp;
import com.criteriontech.digidoctormax.response.SpecialityResp;
import com.criteriontech.digidoctormax.response.UpdateClinicProfileResp;
import com.criteriontech.digidoctormax.response.UpdateDrProfileResp;
import com.criteriontech.digidoctormax.response.VideoCallRes;
import com.criteriontech.digidoctormax.response.VisitedPatientClinicResp;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static com.criteriontech.digidoctormax.utils.AppUtils.APPOINTMENT_DATE;
import static com.criteriontech.digidoctormax.utils.AppUtils.APPOINTMENT_TIME;
import static com.criteriontech.digidoctormax.utils.AppUtils.KEY_APPOINTMENT_ID;
import static com.criteriontech.digidoctormax.utils.AppUtils.KEY_DOC_ID;
import static com.criteriontech.digidoctormax.utils.AppUtils.KEY_IS_ERA_USER;
import static com.criteriontech.digidoctormax.utils.AppUtils.MEMBER_ID;
import static com.criteriontech.digidoctormax.utils.AppUtils.MOBILE_NUMBER;

public class ApiUtils {

    public static final int RESPONSE_SUCCESS = 1;
    public static final int RESPONSE_FAILED = 0;
    public static final int RESPONSE_LOGOUT = 2;

    public static void getVitalsList(VitalModel model, final ApiCallbackInterface apiCallbackInterface) {

        Call<VitalResponse> call = RetrofitClient.getInstance().getApi().getVitals(model);


        call.enqueue(new Callback<VitalResponse>() {
            @Override
            public void onResponse(@NotNull Call<VitalResponse> call, @NotNull Response<VitalResponse> response) {
                if ((response.code() == 200 && null != response.body())) {
                    VitalResponse responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(responseModel.getResponseValue());
                    } else {
                        apiCallbackInterface.onFailure(responseModel.getResponseMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<VitalResponse> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void videoCall(VideoCall videoCall, ApiCallbackInterface apiCallbackInterface) {
        Call<VideoCallRes> call = RetrofitClient.getInstance().getApi().videoCall(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), videoCall);
        call.enqueue(new Callback<VideoCallRes>() {
            @Override
            public void onResponse(Call<VideoCallRes> call, Response<VideoCallRes> response) {
                if ((response.code() == 200 && null != response.body())) {
                    VideoCallRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(responseModel);
                    } else {
                        apiCallbackInterface.onFailure(responseModel.getResponseMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoCallRes> call, Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void getInvestigationData(User model, final ApiCallbackInterface apiCallbackInterface) {


        Call<InvestigationRes> call = RetrofitClient.getInstance().getApi().getInvestigationData(model);

        call.enqueue(new Callback<InvestigationRes>() {
            @Override
            public void onResponse(@NotNull Call<InvestigationRes> call, @NotNull Response<InvestigationRes> response) {
                if ((response.code() == 200 && null != response.body())) {
                    InvestigationRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(responseModel.getResponseValue());
                    } else {
                        apiCallbackInterface.onFailure(responseModel.getResponseMessage());
                    }
                } else
                    apiCallbackInterface.onFailure(response.message());
            }

            @Override
            public void onFailure(@NotNull Call<InvestigationRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void changePassword(ChangePwd changePwd, ApiCallbackInterface apiCallbackInterface) {
        Call<GlobalResponse> call = RetrofitClient.getInstance().getApi().changePassword(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), changePwd);
        call.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(@NotNull Call<GlobalResponse> call, @NotNull Response<GlobalResponse> response) {
                if (response.code() == 200 && null != response.body()) {
                    if (response.body().getResponseCode() == 0) {
                        apiCallbackInterface.onFailure(response.body().getResponseMessage());
                    } else apiCallbackInterface.onSuccess(response.body());
                } else apiCallbackInterface.onFailure("Error " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<GlobalResponse> call, @NotNull Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void forgetPassword(ForgotPwd changePwd, ApiCallbackInterface apiCallbackInterface) {
        Call<GlobalResponse> call = RetrofitClient.getInstance().getApi().forgetPassword(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), changePwd);
        call.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(@NotNull Call<GlobalResponse> call, Response<GlobalResponse> response) {
                if (response.code() == 200 && null != response.body()) {
                    if (response.body().getResponseCode() == 0) {
                        apiCallbackInterface.onFailure(response.body().getResponseMessage());
                    } else apiCallbackInterface.onSuccess(response.body());
                } else apiCallbackInterface.onFailure("Error " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<GlobalResponse> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void getOtp(GenerateOTP generateOTP, ApiCallbackInterface apiCallbackInterface) {
        Call<OtpResp> call = RetrofitClient.getInstance().getApi().generateOTP(generateOTP);
        call.enqueue(new Callback<OtpResp>() {
            @Override
            public void onResponse(Call<OtpResp> call, @NotNull Response<OtpResp> response) {
                if (response.code() == 200 && null != response.body()) {
                    if (response.body().getResponseCode() == 0) {
                        apiCallbackInterface.onFailure(response.body().getResponseMessage());
                    } else apiCallbackInterface.onSuccess(response.body());
                } else apiCallbackInterface.onFailure("Error " + response.code());
            }

            @Override
            public void onFailure(Call<OtpResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void generateOTPForForgotPassword(GenerateOTP generateOTP, ApiCallbackInterface apiCallbackInterface) {
        Call<OtpResp> call = RetrofitClient.getInstance().getApi().generateOTPForForgotPassword(generateOTP);
        call.enqueue(new Callback<OtpResp>() {
            @Override
            public void onResponse(Call<OtpResp> call, @NotNull Response<OtpResp> response) {
                if (response.code() == 200 && null != response.body()) {
                    if (response.body().getResponseCode() == 0) {
                        apiCallbackInterface.onFailure(response.body().getResponseMessage());
                    } else apiCallbackInterface.onSuccess(response.body());
                } else apiCallbackInterface.onFailure("Error " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<OtpResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void login(Login login, ApiCallbackInterface apiCallbackInterface) {
        Call<LoginResp> call = RetrofitClient.getInstance().getApi().checkLogin(login);
        call.enqueue(new Callback<LoginResp>() {
            @Override
            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<LoginResp> call, @NotNull Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void clinicRegistration(ClinicRegistration clinicRegistration, ApiCallbackInterface apiCallbackInterface) {
        Call<ClinicRegistrationResp> call = RetrofitClient.getInstance().getApi().clinicRegistration(clinicRegistration);
        call.enqueue(new Callback<ClinicRegistrationResp>() {
            @Override
            public void onResponse(Call<ClinicRegistrationResp> call, Response<ClinicRegistrationResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<ClinicRegistrationResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void drRegistration(DrRegistration drRegistration, ApiCallbackInterface apiCallbackInterface) {
        Call<DrRegistrationResp> call = RetrofitClient.getInstance().getApi().doctorRegistration(drRegistration);
        call.enqueue(new Callback<DrRegistrationResp>() {
            @Override
            public void onResponse(Call<DrRegistrationResp> call, Response<DrRegistrationResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<DrRegistrationResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void clinicDashbord(ClinicDashboard clinicDashboard, ApiCallbackInterface apiCallbackInterface) {
        Call<ClinicDashboardResp> call = RetrofitClient.getInstance().getApi().clinicDashboard(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), clinicDashboard);
        call.enqueue(new Callback<ClinicDashboardResp>() {
            @Override
            public void onResponse(Call<ClinicDashboardResp> call, Response<ClinicDashboardResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<ClinicDashboardResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void doctorDashboard(ClinicDashboard clinicDashboard, ApiCallbackInterface apiCallbackInterface) {
        Call<DrDashboardResp> call = RetrofitClient.getInstance().getApi().doctorDashboard(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), clinicDashboard);
        call.enqueue(new Callback<DrDashboardResp>() {
            @Override
            public void onResponse(Call<DrDashboardResp> call, Response<DrDashboardResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<DrDashboardResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void drDashboardNumOfPatients(ClinicDashboardDetails clinicDashboardDetails, ApiCallbackInterface apiCallbackInterface) {
        Call<NoOfPatientResp> call = RetrofitClient.getInstance().getApi().drDashboardNumOfPatients(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), clinicDashboardDetails);
        call.enqueue(new Callback<NoOfPatientResp>() {
            @Override
            public void onResponse(Call<NoOfPatientResp> call, Response<NoOfPatientResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<NoOfPatientResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void callingAPI(VoiceCall voiceCall, ApiCallbackInterface apiCallbackInterface) {
        Call<GlobalResponse> call = RetrofitClient.getInstance().getApi().callingAPI(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), voiceCall);
        call.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<GlobalResponse> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void getOnlineAppointmentList(PatientMedicationDetails patientMedicationDetails, ApiCallbackInterface apiCallbackInterface) {
        Call<OnlineAppointmentResp> call = RetrofitClient.getInstance().getApi().getOnlineAppointmentList(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), patientMedicationDetails);
        call.enqueue(new Callback<OnlineAppointmentResp>() {
            @Override
            public void onResponse(Call<OnlineAppointmentResp> call, Response<OnlineAppointmentResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<OnlineAppointmentResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void clinicDashboardNumOfDoctors(ClinicDashboardDetails clinicDashboardDetails, ApiCallbackInterface apiCallbackInterface) {
        Call<ClinicNumOfDocResp> call = RetrofitClient.getInstance().getApi().clinicDashboardNumOfDoctors(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), clinicDashboardDetails);
        call.enqueue(new Callback<ClinicNumOfDocResp>() {
            @Override
            public void onResponse(Call<ClinicNumOfDocResp> call, Response<ClinicNumOfDocResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<ClinicNumOfDocResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void clinicDashboardFeeCollected(ClinicDashboardDetails clinicDashboardDetails, ApiCallbackInterface apiCallbackInterface) {
        Call<CollectedFeeResp> call = RetrofitClient.getInstance().getApi().clinicDashboardFeeCollected(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), clinicDashboardDetails);
        call.enqueue(new Callback<CollectedFeeResp>() {
            @Override
            public void onResponse(Call<CollectedFeeResp> call, Response<CollectedFeeResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<CollectedFeeResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void updateClinicProfile(ClinicUpdate clinicUpdate, ApiCallbackInterface apiCallbackInterface) {
        Call<UpdateClinicProfileResp> call = RetrofitClient.getInstance().getApi().updateClinicProfile(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), clinicUpdate);
        call.enqueue(new Callback<UpdateClinicProfileResp>() {
            @Override
            public void onResponse(Call<UpdateClinicProfileResp> call, Response<UpdateClinicProfileResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<UpdateClinicProfileResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void getSpeciality(JSONObject pa, ApiCallbackInterface apiCallbackInterface) {
        Call<SpecialityResp> call = RetrofitClient.getInstance().getApi().getSpeciality(pa);
        call.enqueue(new Callback<SpecialityResp>() {
            @Override
            public void onResponse(Call<SpecialityResp> call, Response<SpecialityResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<SpecialityResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void getMedicationDetails(MedicationDetail medicationDetail, ApiCallbackInterface apiCallbackInterface) {
        Call<MedicationDetailResp> call = RetrofitClient.getInstance().getApi().getMedicationDetails(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), medicationDetail);
        call.enqueue(new Callback<MedicationDetailResp>() {
            @Override
            public void onResponse(Call<MedicationDetailResp> call, Response<MedicationDetailResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<MedicationDetailResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void getAllTest(SubTest subTest, ApiCallbackInterface apiCallbackInterface) {
        Call<GetAllTestResp> call = RetrofitClient.getInstance().getApi().getAllTest(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), subTest);
        call.enqueue(new Callback<GetAllTestResp>() {
            @Override
            public void onResponse(Call<GetAllTestResp> call, Response<GetAllTestResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<GetAllTestResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void getAllMedicationDataList(ApiCallbackInterface apiCallbackInterface) {
        Call<MedicationDataResp> call = RetrofitClient.getInstance().getApi().getAllMedicationDataList(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken());
        call.enqueue(new Callback<MedicationDataResp>() {
            @Override
            public void onResponse(Call<MedicationDataResp> call, Response<MedicationDataResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<MedicationDataResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void drMedication(SavePrescription savePrescription, ApiCallbackInterface apiCallbackInterface) {
        Call<SavePrescriptionResp> call = RetrofitClient.getInstance().getApi().drMedication(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), savePrescription);
        call.enqueue(new Callback<SavePrescriptionResp>() {
            @Override
            public void onResponse(Call<SavePrescriptionResp> call, Response<SavePrescriptionResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<SavePrescriptionResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    @EverythingIsNonNull
    public static void getPatientMedicationDetail2(String memberId, Activity activity, final ApiCallbackInterface apiCallbackInterface) {

        GetPatientMedicationMainModel model = new GetPatientMedicationMainModel();
        model.setMemberId(memberId);
        Call<GetPatientMedicationRes> call = RetrofitClient.getInstance().getApi().getPatientMedicationDetails2(model);

        call.enqueue(new Callback<GetPatientMedicationRes>() {
            @Override
            public void onResponse(@NotNull Call<GetPatientMedicationRes> call, Response<GetPatientMedicationRes> response) {

                if (response.isSuccessful() && null != response.body()) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {

                    AppUtils.hideDialog();
                    apiCallbackInterface.onFailure(response.message());
                }

            }

            @Override
            public void onFailure(@NotNull Call<GetPatientMedicationRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }

    public static void getPatientMedicationDetails(PatientMedicationDetails patientMedicationDetails, ApiCallbackInterface apiCallbackInterface) {
        Call<PatientMedicationResp> call = RetrofitClient.getInstance().getApi().getPatientMedicationDetails(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), patientMedicationDetails);
        call.enqueue(new Callback<PatientMedicationResp>() {
            @Override
            public void onResponse(Call<PatientMedicationResp> call, Response<PatientMedicationResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<PatientMedicationResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void clinicDashboardVisitedPatient(ClinicDashboardDetails clinicDashboardDetails, ApiCallbackInterface apiCallbackInterface) {
        Call<VisitedPatientClinicResp> call = RetrofitClient.getInstance().getApi().clinicDashboardVisitedPatient(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), clinicDashboardDetails);
        call.enqueue(new Callback<VisitedPatientClinicResp>() {
            @Override
            public void onResponse(Call<VisitedPatientClinicResp> call, Response<VisitedPatientClinicResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<VisitedPatientClinicResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void addNewDoctor(DoctorProfileValue addNewDoctor, ApiCallbackInterface apiCallbackInterface) {
        Call<AddNewDoctorResp> call = RetrofitClient.getInstance().getApi().addNewDoctor(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), addNewDoctor);
        call.enqueue(new Callback<AddNewDoctorResp>() {
            @Override
            public void onResponse(@NotNull Call<AddNewDoctorResp> call, Response<AddNewDoctorResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<AddNewDoctorResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void addNewDoctor(AddNewDoctor addNewDoctor, ApiCallbackInterface apiCallbackInterface) {
        Call<AddNewDoctorResp> call = RetrofitClient.getInstance().getApi().addNewDoctor(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), addNewDoctor);
        call.enqueue(new Callback<AddNewDoctorResp>() {
            @Override
            public void onResponse(@NotNull Call<AddNewDoctorResp> call, Response<AddNewDoctorResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<AddNewDoctorResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void getDoctorProfile(DoctorProfile doctorProfile, ApiCallbackInterface apiCallbackInterface) {
        Call<DoctorProfileResp> call = RetrofitClient.getInstance().getApi().getDoctorProfile(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), doctorProfile);
        call.enqueue(new Callback<DoctorProfileResp>() {
            @Override
            public void onResponse(@NotNull Call<DoctorProfileResp> call, @NotNull Response<DoctorProfileResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        DoctorProfileResp model = response.body();
                        if (!model.getResponseValue().isEmpty())
                            apiCallbackInterface.onSuccess(model.getResponseValue().get(0));
                        else apiCallbackInterface.onFailure("Error: " + "No Data found");
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<DoctorProfileResp> call, @NotNull Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void updateDoctorProfile(DoctorProfileValue doctorProfileValue, ApiCallbackInterface apiCallbackInterface) {
        Call<UpdateDrProfileResp> call = RetrofitClient.getInstance().getApi().updateDoctorProfile(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), doctorProfileValue);
        call.enqueue(new Callback<UpdateDrProfileResp>() {
            @Override
            public void onResponse(Call<UpdateDrProfileResp> call, Response<UpdateDrProfileResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<UpdateDrProfileResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void addVital(AddVital addVital, ApiCallbackInterface apiCallbackInterface) {
        Call<AddVitalResp> call = RetrofitClient.getInstance().getApi().addVital(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), addVital);
        call.enqueue(new Callback<AddVitalResp>() {
            @Override
            public void onResponse(Call<AddVitalResp> call, Response<AddVitalResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<AddVitalResp> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }


    public static void getDoctorsTimeSlots(String docId, String date, String isEraUser, final ApiCallbackInterface apiCallbackInterface) {
        try {

            final Api api = RetrofitClient.getInstance().getApi();

            OnlineAppointmentSlots slotsModel = new OnlineAppointmentSlots();
            slotsModel.setAppointDate(date);
            slotsModel.setServiceProviderDetailsId(docId);
            slotsModel.setIsEraUser(isEraUser);

            Call<GetAppointmentSlotsRes> getAppointmentSlotsResCall = api.getOnlineAppointmentSlots(slotsModel);
            getAppointmentSlotsResCall.enqueue(new Callback<GetAppointmentSlotsRes>() {
                @Override
                public void onResponse(@NotNull Call<GetAppointmentSlotsRes> call, @NotNull Response<GetAppointmentSlotsRes> response) {
                    if (response.code() == 200 && null != response.body()) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onFailure(response.body().getResponseMessage());
                    } else apiCallbackInterface.onFailure(response.message());
                }

                @Override
                public void onFailure(@NotNull Call<GetAppointmentSlotsRes> call, @NotNull Throwable t) {
                    apiCallbackInterface.onFailure(t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void checkTimeSlotAvailability(Map<String, Object> map, final ApiCallbackInterface apiCallbackInterface) {
        try {
            if (DrHomeActivity.getInstance() != null)
                AppUtils.showRequestDialog(DrHomeActivity.getInstance());

            CheckTimeSlotModel model = new CheckTimeSlotModel();
            model.setMemberId((String) map.get(MEMBER_ID));
            model.setServiceProviderDetailsId((String) map.get(KEY_DOC_ID));
            model.setAppointDate((String) map.get(APPOINTMENT_DATE));
            model.setAppointTime((String) map.get(APPOINTMENT_TIME));
            model.setUserMobileNo((String) map.get(MOBILE_NUMBER));
            model.setIsEraUser((String) map.get(KEY_IS_ERA_USER));
            model.setAppointmentId(String.valueOf(map.get(KEY_APPOINTMENT_ID)));
            //    model.setIsRevisit((Boolean) map.get(IS_REVISIT));

            Log.d("TAG", "checkTimeSlotAvailability: " + model.toString());

            Call<CheckSlotAvailabilityRes> specialityResCall = RetrofitClient.getInstance().getApi().checkTimeSlotAvailability(model);
            specialityResCall.enqueue(new Callback<CheckSlotAvailabilityRes>() {
                @Override
                public void onResponse(@NotNull Call<CheckSlotAvailabilityRes> call, @NotNull Response<CheckSlotAvailabilityRes> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200) {
                        CheckSlotAvailabilityRes resModel = response.body();
                        if (null != resModel) {
                            int responseCode = resModel.getResponseCode();
                            switch (responseCode) {
                                case RESPONSE_SUCCESS:
                                    apiCallbackInterface.onSuccess(resModel.getResponseValue());
                                    break;
                                case RESPONSE_FAILED:
                                    apiCallbackInterface.onFailure(resModel.getResponseMessage());
                                    break;
                                case RESPONSE_LOGOUT:
                                    //  logout(DrHomeActivity.getInstance());
                                    break;

                            }
                        }
                    } else {
                        apiCallbackInterface.onFailure(String.valueOf(response.code()));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CheckSlotAvailabilityRes> call, @NotNull Throwable t) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onFailure(t.getLocalizedMessage());

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getChatResponse(Call<ChatResponse> call, NewApiInterface newApiInterface) {
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(@NotNull Call<ChatResponse> call, @NotNull Response<ChatResponse> response) {
                if (response.code() == 200) {
                    ChatResponse responseModel = response.body();
                    if (null == responseModel)
                        return;
                    if (responseModel.getResponseCode() == 1) {
                        newApiInterface.onSuccess(responseModel.getResponseValue());
                    } else newApiInterface.onFailed(responseModel.getResponseMessage());
                } else newApiInterface.onFailed(String.valueOf(response.code()));
            }

            @Override
            public void onFailure(@NotNull Call<ChatResponse> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                newApiInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }

    public static Call<ChatResponse> sendMsg(ChatModel model) {
        return RetrofitClient.getInstance().getApi().sendMsg(model);
    }

    public static Call<ChatResponse> getChatData(NoOfPatientValue model) {
        return RetrofitClient.getInstance().getApi().getMsg(model);
    }

    public static RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }


    public static void uploadProfileImage(File imagFile, final UploadImageInterface apiCallbackInterface) {
        MultipartBody.Part[] fileParts = new MultipartBody.Part[1];
        try {
            MediaType mediaType = MediaType.parse("image/*");

            RequestBody fileBody;

            fileBody = RequestBody.create(mediaType, imagFile);

            fileParts[0] = MultipartBody.Part.createFormData("files", imagFile.getName(), fileBody);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, RequestBody> map = new HashMap<>();
        map.put("files", toRequestBody("files"));

        Api iRestInterfaces = RetrofitClient.getInstance().getApi();
        Call<SaveMultipleFileRes> call = iRestInterfaces.uploadImage(map, fileParts);


        call.enqueue(new Callback<SaveMultipleFileRes>() {
            @Override
            public void onResponse(@NotNull Call<SaveMultipleFileRes> call, @NotNull Response<SaveMultipleFileRes> response) {
                Log.d("TAG", "onResponse: " + response.body());
                Log.d("TAG", "onResponse: " + response.code());

                if ((response.code() == 200 && null != response.body())) {
                    SaveMultipleFileRes prescriptionResponse = response.body();
                    if (prescriptionResponse.getResponseCode() == 1) {
                        try {
                            JSONArray jsonArray = new JSONArray(response.body().getResponseData());
                            String fileName = jsonArray.getJSONObject(0).getString("filePath");
                            List<String> strings = new ArrayList<>();
                            strings.add(fileName);
                            apiCallbackInterface.onSuccess(strings);

                        } catch (Exception e) {
                            e.printStackTrace();
                            apiCallbackInterface.onError(e.getLocalizedMessage());
                        }

                    } else {
                        apiCallbackInterface.onError(prescriptionResponse.getResponseMessage());
                    }
                } else apiCallbackInterface.onError("Something went wrong, try again !!");

            }

            @Override
            public void onFailure(@NotNull Call<SaveMultipleFileRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }


    public static void updatemonitor(MonitorResponse monitorResponse, ApiCallbackInterface apiCallbackInterface) {

        String token = SharedPrefManager.getInstance(DrHomeActivity.getInstance()).getToken();
        Call<ResponseModel> call = RetrofitClient.getInstance().getApi().updatemonitoring(token, monitorResponse);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.code() == 200) {
                    apiCallbackInterface.onSuccess(response.body());
                } else {
                    apiCallbackInterface.onFailure(response.message());
                }
//                if (response.code() == 200 && response.body() != null) {
//                    if (response.body().getStatusCode() == 1) {
//                        apiCallbackInterface.onSuccess(response.body());
//                    } else
//                        apiCallbackInterface.onFailure("Error: " + response.body().getErrorMessages());
//                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }


    public static void checkDrugInteraction(DrugInteractionModel drugInteractionModel, NewApiInterface newApiInterface) {

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiZmlyc3ROYW1lIjoic2FkZGFtIiwibGFzdE5hbWUiOm51bGwsImVtYWlsSWQiOm51bGwsIm1vYmlsZU5vIjoiODk2MDI1MzEzMyIsImNvdW50cnkiOiJJTkRJQSIsInppcENvZGUiOiIyMjYwMjAiLCJvY2N1cGF0aW9uSWQiOjEsImFnZSI6bnVsbCwiZ2VuZGVyIjpudWxsLCJoZWlnaHRJbkZlZXQiOm51bGwsImhlaWdodEluSW5jaCI6bnVsbCwid2VpZ2h0IjpudWxsLCJwYWNrYWdlTmFtZSI6IkZyZWUiLCJpYXQiOjE1NjMwMTM4MDUsImV4cCI6MTU5NDU0OTgwNX0.l220lljQyTXmDPD-gyU53H4vV-I1GDPociKcp2qrWe8";
        Call<ResponseModel> call = RetrofitClient.getInstance().drugInteraction().getDrugInteraction(token, drugInteractionModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.code() == 200) {
                    ResponseModel responseModel = response.body();
                    if (null != responseModel) {
                        if (responseModel.getResponseCode() == 1 && !responseModel.getResponseValue().isEmpty()) {

                            newApiInterface.onSuccess(responseModel.getResponseValue());
                            Log.d("TAG", "onResponse:interaction found ");

                        } else {
                            Log.d("TAG", "onResponse1st:interaction not  found ");
                            newApiInterface.onFailed("failed to get interaction !!");
                        }
                    } else {
                        Log.d("TAG", "onResponse2nd:interaction not  found ");
                        newApiInterface.onFailed("failed to get interaction !!");
                    }
                } else {
                    Log.d("TAG", "onResponse3rd:interaction not  found ");
                    newApiInterface.onFailed("failed to get interaction !!");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                newApiInterface.onFailed(t.getLocalizedMessage());
            }
        });


    }


    public static void getDoctorBankDetails(Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        try {

            final Api api = RetrofitClient.getInstance().getApi();

            GetBanModel getBanModel = new GetBanModel();
            getBanModel.setServiceProviderDetailsId(SharedPrefManager.getInstance(activity).getUser().getId());
            Call<GetBankDetailsRes> getBankDetailsResCall = api.getBankDetails(getBanModel);
            getBankDetailsResCall.enqueue(new Callback<GetBankDetailsRes>() {
                @Override
                public void onResponse(@NotNull Call<GetBankDetailsRes> call, @NotNull Response<GetBankDetailsRes> response) {
                    if (response.code() == 200 && null != response.body()) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onFailure(response.body().getResponseMessage());
                    } else apiCallbackInterface.onFailure(response.message());
                }

                @Override
                public void onFailure(@NotNull Call<GetBankDetailsRes> call, @NotNull Throwable t) {
                    apiCallbackInterface.onFailure(t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void updateBankData(UpdateBankModel updateBankModel, ApiCallbackInterface apiCallbackInterface) {
        Call<UpdateBankRes> call = RetrofitClient.getInstance().getApi().updateBankInfo(updateBankModel);
        call.enqueue(new Callback<UpdateBankRes>() {
            @Override
            public void onResponse(Call<UpdateBankRes> call, Response<UpdateBankRes> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<UpdateBankRes> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }


    public static void deleteBankDetails(GetBankDetailsModel updateBankModel, ApiCallbackInterface apiCallbackInterface) {
        Call<UpdateBankRes> call = RetrofitClient.getInstance().getApi().deleteBankDetails(updateBankModel);
        call.enqueue(new Callback<UpdateBankRes>() {
            @Override
            public void onResponse(Call<UpdateBankRes> call, Response<UpdateBankRes> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(Call<UpdateBankRes> call, Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }


    public static void getQuarantinePatientList(int docId, ApiCallbackInterface apiCallbackInterface) {
        Call<QuarantinePatientListRes> call = RetrofitClient.getInstance().getApi().getQuarantinePatients();
        call.enqueue(new Callback<QuarantinePatientListRes>() {
            @Override
            public void onResponse(@NotNull Call<QuarantinePatientListRes> call, @NotNull Response<QuarantinePatientListRes> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<QuarantinePatientListRes> call, @NotNull Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void writeQuarantinePrescription(WritePrescriptionForQuarantineModel forQuarantineModel, ApiCallbackInterface apiCallbackInterface) {
        Call<SavePrescriptionResp> call = RetrofitClient.getInstance().getApi().writeQuarantinePrescription(SharedPrefManager.getInstance(StartActivity.getInstance()).getToken(), forQuarantineModel);
        call.enqueue(new Callback<SavePrescriptionResp>() {
            @Override
            public void onResponse(@NotNull Call<SavePrescriptionResp> call, @NotNull Response<SavePrescriptionResp> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<SavePrescriptionResp> call, @NotNull Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }


    public static void isolationData(LoginValue forQuarantineModel, ApiCallbackInterface apiCallbackInterface) {
        Call<IsolationResponse> call = RetrofitClient.getInstance().getApi().isolationData(forQuarantineModel);
        call.enqueue(new Callback<IsolationResponse>() {
            @Override
            public void onResponse(@NotNull Call<IsolationResponse> call, @NotNull Response<IsolationResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<IsolationResponse> call, @NotNull Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public static void approveDeclineRequest(UpdateIsolationModel isolationModel, ApiCallbackInterface apiCallbackInterface) {

        Call<IsolationResponse> call = RetrofitClient.getInstance().getApi().approveDeclineRequest(isolationModel);
        call.enqueue(new Callback<IsolationResponse>() {
            @Override
            public void onResponse(@NotNull Call<IsolationResponse> call, @NotNull Response<IsolationResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseMessage());
                    } else
                        apiCallbackInterface.onFailure("Error: " + response.body().getResponseMessage());
                } else apiCallbackInterface.onFailure("Error: " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<IsolationResponse> call, @NotNull Throwable t) {
                apiCallbackInterface.onFailure(t.getLocalizedMessage());
            }
        });
    }
}
