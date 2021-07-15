package com.criteriontech.digidoctormax.jitsiVideoCall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.ActivityInitVideoCallBinding;
import com.criteriontech.digidoctormax.model.LoginValue;
import com.criteriontech.digidoctormax.request.VideoCall;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.App;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InitVideoCallActivity extends AppCompatActivity {

    private static final String TAG = "InitVideoCallActivity";
    String uid;
    FirebaseFirestore firestore;

    ActivityInitVideoCallBinding binding;

    String memberId, roomCode, docId, docName, patientName;

    boolean isCallInitiated = false;
    boolean isCallAccepted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_init_video_call);

        firestore = FirebaseFirestore.getInstance();

        roomCode = String.valueOf(System.currentTimeMillis());
        memberId = getIntent().getStringExtra("memberId");
        docId = getIntent().getStringExtra("docId");
        docName = getIntent().getStringExtra(AppUtils.DOCTOR_NAME);
        patientName = getIntent().getStringExtra(AppUtils.PATIENT_NAME);
        uid = memberId;


        listenForReaTimeCallStatus();


        binding.btnDisconnectCall.setOnClickListener(v -> {
            if (isCallInitiated) {
                Map<String, Object> map = new HashMap<>();
                map.put(AppUtils.CALL_STATUS, isCallAccepted ? AppUtils.CALL_DISCONNECTED : AppUtils.CALL_MISSED);
                map.put(AppUtils.CALL_REJECTED_BY, "doctor");
                firestore.collection(AppUtils.VIDEO_CALLS_DEMO)
                        .document(roomCode)
                        .update(map)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(InitVideoCallActivity.this, "Call Disconnected", Toast.LENGTH_SHORT).show();
                            finish();
                        }).addOnFailureListener(e -> Toast.makeText(InitVideoCallActivity.this, "unable to disconnect call, try again !!", Toast.LENGTH_SHORT).show());

            }
        });
    }

    private void listenForReaTimeCallStatus() {
        firestore.collection(AppUtils.VIDEO_CALLS_DEMO)
                .document(roomCode).addSnapshotListener(this, (value, error) -> {
            if (null == error && null != value) {
                String callStatus = value.getString(AppUtils.CALL_STATUS);
                if (null != callStatus && !TextUtils.isEmpty(callStatus)) {
                    if (callStatus.equalsIgnoreCase(AppUtils.CALL_STARTED))
                        binding.textView52.setText("Calling...");
                    else
                        binding.textView52.setText(callStatus);

                    if (callStatus.equalsIgnoreCase(AppUtils.CALL_ACCEPT)) {
                        isCallAccepted = false;
                        InitVideoCallActivity.this.openJitsiScreen();
                    } else if (callStatus.equalsIgnoreCase(AppUtils.CALL_DISCONNECTED) || callStatus.equalsIgnoreCase(AppUtils.CALL_REJECTED)) {
                        Toast.makeText(InitVideoCallActivity.this, "Call Disconnected", Toast.LENGTH_SHORT).show();
                        InitVideoCallActivity.this.finish();
                    }

                }
            }
        });
    }

    private void openJitsiScreen() {
        startActivity(new Intent(this, VideoCallJitsiActivity.class)
                .putExtra(AppUtils.ROOM_CODE, roomCode)
        );
        Log.d(TAG, "openJitsiScreen: " + roomCode);
        finish();

    }


    @Override
    protected void onStart() {
        super.onStart();
        //checkForUserIsLoggedInOrNot(uid);
        initVideoCall();

    }


    private void checkForUserIsLoggedInOrNot(String uid) {

        firestore.collection("Users")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (null != documentSnapshot) {
                        String token = documentSnapshot.getString("token");
                        if (null != token && !TextUtils.isEmpty(token))
                            initVideoCall();

                        else
                            userNotLoggedIn();

                    } else
                        userNotLoggedIn();

                }).addOnFailureListener(e -> userNotLoggedIn());
    }

    private void userNotLoggedIn() {
        Toast.makeText(InitVideoCallActivity.this, "User is not Logged in !!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void hitVideoCall() {
        LoginValue loginValue = SharedPrefManager.getInstance(this).getUser();
        VideoCall videoCall = new VideoCall();
        videoCall.setCallType("1");
        videoCall.setIsEraUser(String.valueOf(loginValue.getIsEraUser()));
        videoCall.setDoctorId(String.valueOf(loginValue.getId()));
        videoCall.setMemberId(memberId);
        videoCall.setRoomName(roomCode);
        videoCall.setUserMobileNo(loginValue.getMobileNo());

        if (AppUtils.isNetworkConnected(this)) {
            ApiUtils.videoCall(videoCall, new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
                    Toast.makeText(InitVideoCallActivity.this, "Call initiated !!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
    }

    private void initVideoCall() {


        Map<String, Object> map = new HashMap<>();
        map.put(AppUtils.CALL_STATUS, AppUtils.CALL_STARTED);
        map.put(AppUtils.UID, memberId);
        map.put(AppUtils.DOCTOR_ID, docId);
        map.put(AppUtils.DOCTOR_NAME, docName);
        map.put(AppUtils.PATIENT_NAME, patientName);
        map.put(AppUtils.CALL_INITIATED_TIMESTAMP, System.currentTimeMillis());
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(AppUtils.VIDEO_CALLS_DEMO)
                .document(roomCode)
                .set(map)
                .addOnSuccessListener(aVoid -> {
                    isCallInitiated = true;
                    hitVideoCall();
                })
                .addOnFailureListener(e -> Toast.makeText(InitVideoCallActivity.this, "Failed to make call, try again !!", Toast.LENGTH_SHORT).show());
    }
}