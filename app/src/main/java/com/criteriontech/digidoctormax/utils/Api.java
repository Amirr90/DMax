package com.criteriontech.digidoctormax.utils;

import com.criteriontech.digidoctormax.model.BookAppointment2;
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
import com.criteriontech.digidoctormax.model.OnlineAppointmentRes;
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

import org.json.JSONObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface Api {


    @POST("Patient/getPatientVitalList")
    Call<VitalResponse> getVitals(@Body VitalModel model);

    @POST("Patient/getpatientInvestigationDetails")
    Call<InvestigationRes> getInvestigationData(@Body User model);

    @POST("Doctor/changePassword")
    Call<GlobalResponse> changePassword(@Header("x-access-token") String token, @Body ChangePwd changePwd);

    @POST("Doctor/updateNewPassword")
    Call<GlobalResponse> forgetPassword(@Header("x-access-token") String token, @Body ForgotPwd forgotPwd);


    @POST("Doctor/generateOTP")
    Call<OtpResp> generateOTP(@Body GenerateOTP generateOTP);

    @POST("Doctor/generateOTPForForgotPassword")
    Call<OtpResp> generateOTPForForgotPassword(@Body GenerateOTP generateOTP);


    @POST("Patient/checkLogin")
    Call<LoginResp> checkLogin(@Body Login login);

    @POST("Doctor/registration")
    Call<ClinicRegistrationResp> clinicRegistration(@Body ClinicRegistration clinicRegistration);

    @POST("Doctor/doctorregistration")
    Call<DrRegistrationResp> doctorRegistration(@Body DrRegistration clinicRegistration);

    @POST("Doctor/clinicDashbord")
    Call<ClinicDashboardResp> clinicDashboard(@Header("x-access-token") String token, @Body ClinicDashboard clinicDashboard);

    @POST("Doctor/doctorDashbord")
    Call<DrDashboardResp> doctorDashboard(@Header("x-access-token") String token, @Body ClinicDashboard clinicDashboard);

    @POST("Doctor/doctorDashbordDetails")
    Call<NoOfPatientResp> drDashboardNumOfPatients(@Header("x-access-token") String token, @Body ClinicDashboardDetails clinicDashboardDetails);

    @POST("Doctor/getOnlineAppointmentList")
    Call<OnlineAppointmentResp> getOnlineAppointmentList(@Header("x-access-token") String token, @Body PatientMedicationDetails patientMedicationDetails);

    @POST("Doctor/clinicDashbordDetails")
    Call<ClinicNumOfDocResp> clinicDashboardNumOfDoctors(@Header("x-access-token") String token, @Body ClinicDashboardDetails clinicDashboardDetails);

    @POST("Doctor/clinicDashbordDetails")
    Call<CollectedFeeResp> clinicDashboardFeeCollected(@Header("x-access-token") String token, @Body ClinicDashboardDetails clinicDashboardDetails);

    @POST("Doctor/getMedicationDetails")
    Call<MedicationDetailResp> getMedicationDetails(@Header("x-access-token") String token, @Body MedicationDetail medicationDetail);

    @POST("Doctor/updateClinicProfile")
    Call<UpdateClinicProfileResp> updateClinicProfile(@Header("x-access-token") String token, @Body ClinicUpdate clinicUpdate);

    @POST("Patient/getSpeciality")
    Call<SpecialityResp> getSpeciality(@Body JSONObject type);

    @POST("Patient/getAllTest")
    Call<GetAllTestResp> getAllTest(@Header("x-access-token") String token, @Body SubTest subTest);

    @POST("Patient/getAllMedicationDataList")
    Call<MedicationDataResp> getAllMedicationDataList(@Header("x-access-token") String token);

    @POST("Doctor/drMedication")
    Call<SavePrescriptionResp> drMedication(@Header("x-access-token") String token, @Body SavePrescription savePrescription);

    @POST("Doctor/clinicDashbordDetails")
    Call<VisitedPatientClinicResp> clinicDashboardVisitedPatient(@Header("x-access-token") String token, @Body ClinicDashboardDetails clinicDashboardDetails);

    @POST("Patient/getPatientMedicationDetails")
    Call<PatientMedicationResp> getPatientMedicationDetails(@Header("x-access-token") String token, @Body PatientMedicationDetails patientMedicationDetails);

    @POST("Patient/getPatientMedicationDetails")
    Call<GetPatientMedicationRes> getPatientMedicationDetails2(
            @Body GetPatientMedicationMainModel model);

    @POST("Doctor/updateDoctorProfile")
    Call<UpdateDrProfileResp> updateDoctorProfile(@Header("x-access-token") String token, @Body DoctorProfileValue doctorProfileValue);

    @POST("Doctor/addNewDoctor")
    Call<AddNewDoctorResp> addNewDoctor(@Header("x-access-token") String token, @Body DoctorProfileValue addNewDoctor);

    @POST("Doctor/addNewDoctor")
    Call<AddNewDoctorResp> addNewDoctor(@Header("x-access-token") String token, @Body AddNewDoctor addNewDoctor);

    @POST("Doctor/getDoctorProfile")
    Call<DoctorProfileResp> getDoctorProfile(@Header("x-access-token") String token, @Body DoctorProfile doctorProfile);

    @POST("Patient/addVital")
    Call<AddVitalResp> addVital(@Header("x-access-token") String token, @Body AddVital addVital);

    @POST("Doctor/videoCall")
    Call<VideoCallRes> videoCall(@Header("x-access-token") String token, @Body VideoCall videoCall);

    @POST("Doctor/callingAPI")
    Call<GlobalResponse> callingAPI(@Header("x-access-token") String token, @Body VoiceCall voiceCall);


    @POST("Patient/getOnlineAppointmentSlots")
    Call<GetAppointmentSlotsRes> getOnlineAppointmentSlots(
            @Body OnlineAppointmentSlots slots);


    @POST("Patient/checkTimeSlotAvailability")
    Call<CheckSlotAvailabilityRes> checkTimeSlotAvailability(
            @Body CheckTimeSlotModel model);


    @POST("Patient/onlineAppointment")
    Call<OnlineAppointmentRes> onlineAppointment2(@Body BookAppointment2 appointment);

    @POST("Patient/revisitAppointment")
    Call<OnlineAppointmentRes> revisitAppointment(@Body BookAppointment2 appointment);

    @POST("Patient/patientDoctorChatting")
    Call<ChatResponse> sendMsg(@Body ChatModel model);

    @POST("Patient/getPatientDoctorChatting")
    Call<ChatResponse> getMsg(@Body NoOfPatientValue model);

    @Multipart
    @POST("Doctor/saveMultipleFile")
    Call<SaveMultipleFileRes> uploadImage(
            @PartMap() Map<String, RequestBody> partMap,
            @Part MultipartBody.Part[] multipleFile
    );

    @POST("Doctor/updateRemoteMonitoring")
    Call<ResponseModel> updatemonitoring(@Header("x-access-token") String token, @Body MonitorResponse model);


    @POST("interactionChecker")
    Call<ResponseModel> getDrugInteraction(@Header("x-access-token") String token, @Body DrugInteractionModel drugInteractionModel);


    @POST("Doctor/getRequestForBankDetails")
    Call<GetBankDetailsRes> getBankDetails(@Body GetBanModel model);


    @POST("Doctor/requestForBankDetails")
    Call<UpdateBankRes> updateBankInfo(@Body UpdateBankModel model);


    @POST("Doctor/deleteBankDetails")
    Call<UpdateBankRes> deleteBankDetails(@Body GetBankDetailsModel model);


    // Apis for Quarantine Patient List
    @POST("Patient/getQuarantinePatients")
    Call<QuarantinePatientListRes> getQuarantinePatients();


    @POST("Doctor/writePrescription")
    Call<SavePrescriptionResp> writeQuarantinePrescription(@Header("x-access-token") String token, @Body WritePrescriptionForQuarantineModel savePrescription);

    // Apis for Quarantine Patient List
    @POST("Doctor/getRequestedList")
    Call<IsolationResponse> isolationData(@Body LoginValue loginValue);

    @POST("Doctor/approveDeclineRequest")
    Call<IsolationResponse> approveDeclineRequest(@Body UpdateIsolationModel isolationModel);
}