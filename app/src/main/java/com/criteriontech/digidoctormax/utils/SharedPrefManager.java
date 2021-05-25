package com.criteriontech.digidoctormax.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.criteriontech.digidoctormax.model.ClinicDetails;
import com.criteriontech.digidoctormax.model.ClinicRegistrationValue;
import com.criteriontech.digidoctormax.model.DrRegistrationValue;
import com.criteriontech.digidoctormax.model.LoginValue;
import com.google.gson.Gson;

public class SharedPrefManager {


    private final SharedPreferences sharedprefs;
    private SharedPreferences.Editor editor;
    @SuppressLint("StaticFieldLeak")
    private static SharedPrefManager appSharedprefs;
    Gson gson = new Gson();
    private Context context;

    @SuppressLint("CommitPrefEdits")
    private SharedPrefManager(Context context) {
        this.context = context;
        this.sharedprefs = context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
        this.editor = sharedprefs.edit();
    }

    public static SharedPrefManager getInstance(Context context) {
        if (appSharedprefs == null)
            appSharedprefs = new SharedPrefManager(context);
        return appSharedprefs;
    }

    public void setFCMToken(String FCMToken) {
        this.editor = sharedprefs.edit();
        editor.putString("FCMToken", FCMToken);
        editor.apply();
    }

    public String getFCMToken() {
        return sharedprefs.getString("FCMToken", null);
    }

    public void setToken(String accessToken) {
        this.editor = sharedprefs.edit();
        editor.putString("accessToken", accessToken);
        editor.apply();
    }

    public void setRegNo(String accessToken) {
        this.editor = sharedprefs.edit();
        editor.putString("regNo", accessToken);
        editor.apply();
    }

    public void setWorkDes(String accessToken) {
        this.editor = sharedprefs.edit();
        editor.putString("workDes", accessToken);
        editor.apply();
    }

    public String getWorkDes() {
        return sharedprefs.getString("workDes", null);
    }

    public void setAddress(String accessToken) {
        this.editor = sharedprefs.edit();
        editor.putString("address", accessToken);
        editor.apply();
    }

    public String getAddress() {
        return sharedprefs.getString("address", null);
    }

    public String getToken() {
        return sharedprefs.getString("accessToken", null);
    }

    public String getRegNo() {
        return sharedprefs.getString("regNo", null);
    }

    public LoginValue getUser() {
        Gson gson = new Gson();
        String json = sharedprefs.getString("loginValue", "");
        return gson.fromJson(json, LoginValue.class);
    }

    public void saveUser(LoginValue loginValue) {
        Gson gson = new Gson();
        String json = gson.toJson(loginValue);
        editor.putString("loginValue", json);
        editor.commit();
    }

    public void saveClinicLogin(ClinicDetails clinicDetails) {
        String json = gson.toJson(clinicDetails);
        editor.putString("clinicDetails", json);
        editor.commit();
    }

    public ClinicDetails getClinicLogin() {
        String json = sharedprefs.getString("clinicDetails", "");
        return gson.fromJson(json, ClinicDetails.class);
    }

    public void saveClinicReg(ClinicRegistrationValue clinicRegistrationValue) {
        String json = gson.toJson(clinicRegistrationValue);
        editor.putString("clinicRegistrationValue", json);
        editor.commit();
    }

    public ClinicRegistrationValue getClinicReg() {
        String json = sharedprefs.getString("clinicRegistrationValue", "");
        return gson.fromJson(json, ClinicRegistrationValue.class);
    }

    public void saveDrReg(DrRegistrationValue drRegistrationValue) {
        String json = gson.toJson(drRegistrationValue);
        editor.putString("drRegistrationValue", json);
        editor.commit();
    }

    public DrRegistrationValue getDrReg() {
        String json = sharedprefs.getString("drRegistrationValue", "");
        return gson.fromJson(json, DrRegistrationValue.class);
    }

    public void clear() {
        sharedprefs.edit().clear().commit();
    }

    public void setLoginType(Integer clinic) {
        this.editor = sharedprefs.edit();
        editor.putInt("clinic", clinic).apply();
    }

    public Integer getLoginType() {
        return sharedprefs.getInt("clinic", 0);
    }

    /*public void saveClinicDetail(ClinicDetails clinicDetails){
        String json = gson.toJson(clinicDetails);
        editor.putString("clinicDetails", json);
        editor.commit();
    }

    public ClinicDetails getClinicDetail(){
        String json = sharedprefs.getString("clinicDetails", "");
        return gson.fromJson(json, ClinicDetails.class);
    }*/
}
