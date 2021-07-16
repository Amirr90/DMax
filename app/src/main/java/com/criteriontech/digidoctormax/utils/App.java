package com.criteriontech.digidoctormax.utils;

import android.app.Application;

public class App extends Application {

    public static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
