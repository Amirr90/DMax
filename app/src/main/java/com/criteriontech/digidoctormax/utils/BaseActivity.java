package com.criteriontech.digidoctormax.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

//import code.common.SimpleHTTPConnection;
//import code.database.OSettings;


public class BaseActivity extends AppCompatActivity {
    protected Activity mActivity;
    private boolean noAnimation = true;
    protected static final String TAG = BaseActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        mActivity = this;
   //     new OSettings(mActivity);
    //    new SimpleHTTPConnection(mActivity);
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon_round);
//        ActivityManager.TaskDescription taskDesc = new ActivityManager.TaskDescription(getString(R.string.app_name), bm, Color.WHITE);
//        setTaskDescription(taskDesc);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }

    @Override
    public void finish() {
        super.finish();
        //overridePendingTransitionExit();
    }

    public void finishNew() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (noAnimation)
            overridePendingTransitionEnter();
        else
            noAnimation = true;
    }

    public void startActivity(Intent intent, boolean noAnimation) {
        this.noAnimation = noAnimation;
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransitionEnter();
    }


    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
     //   overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
     //   overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
