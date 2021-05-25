package com.criteriontech.digidoctormax.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.model.DoctorProfileValue;
import com.criteriontech.digidoctormax.utils.BaseActivity;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

public class StartActivity extends BaseActivity {
    private NavController navController;
    public static StartActivity instance;

    public static StartActivity getInstance() {
        return instance;
    }

    public static com.criteriontech.digidoctormax.model.DoctorProfileValue doctorProfileValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instance = this;
        navController = Navigation.findNavController(this, R.id.nav_home);
        NavigationUI.setupActionBarWithNavController(this, navController);


        doctorProfileValue = new DoctorProfileValue();
        int drFee = (int) Integer.parseInt(doctorProfileValue.getDrFee());

        doctorProfileValue.setDrFee(String.valueOf(drFee));
        insertFcmToken();
    }

    @Override
    protected void onStart() {
        super.onStart();
        NavigationUI.setupActionBarWithNavController(this, navController);

    }


    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    private void insertFcmToken() {
       /* FirebaseApp.initializeApp(getApplicationContext());
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("instanceId", task.getException());
                return;
            }
            String token = Objects.requireNonNull(task.getResult()).getToken();
            SharedPrefManager.getInstance(mActivity).setFCMToken(token);
            Log.d("token", token);
        });*/
    }

}