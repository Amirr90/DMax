package com.criteriontech.digidoctormax.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class NewDashboardUtils {


    public static final int PAY_MODE_CASH = 0;
    public static final int PAY_MODE_RAZOR_PAY = 1;

    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            try {
                @SuppressLint("WrongConstant") InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
                View view = activity.getCurrentFocus();
                if (view != null) {
                    IBinder binder = view.getWindowToken();
                    if (binder != null) {
                        inputMethodManager.hideSoftInputFromWindow(binder, 0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Objects.requireNonNull(activity).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                return info.getState() == NetworkInfo.State.CONNECTED;
            }
        }

        return false;
    }


    public static ArrayList<HashMap<String, String>> getNextWeekDays() {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();


        HashMap<String, String> hashMap;

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat sdfDay = new SimpleDateFormat("EEE", Locale.getDefault());
        SimpleDateFormat sdfDateSend = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        for (int i = 0; i < 7; i++) {
            hashMap = new HashMap<>();

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            String date = sdfDate.format(calendar.getTime());
            String day = sdfDay.format(calendar.getTime());
            String dateSend = sdfDateSend.format(calendar.getTime());

            hashMap.put("date", date);
            hashMap.put("day", day);
            hashMap.put("dateSend", dateSend);

            list.add(hashMap);

        }
        Log.d("TAG", "getNextWeekDays: " + list.size());
        return list;
    }


    public static String getJSONFromModel(Object o) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(o);
        try {
            JSONObject request = new JSONObject(jsonString);
            return request.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

}
