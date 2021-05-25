package com.criteriontech.digidoctormax.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ConnectivityChecker {
    public static boolean checker(Context mContext) {
        boolean hvwifi = false;
        boolean hvmob = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo info : networkInfo) {
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected())
                    hvwifi = true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                    hvmob = true;
        }
        return hvmob || hvwifi;
    }
}
