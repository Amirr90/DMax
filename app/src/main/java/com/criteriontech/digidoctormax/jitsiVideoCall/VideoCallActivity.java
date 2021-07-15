package com.criteriontech.digidoctormax.jitsiVideoCall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.criteriontech.digidoctormax.model.LoginValue;
import com.criteriontech.digidoctormax.request.VideoCall;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.App;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetActivityDelegate;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.util.Map;

public class VideoCallActivity extends JitsiMeetActivity implements JitsiMeetActivityInterface, JitsiMeetViewListener {

    private static final String TAG = "VideoCallActivity";
    String roomCode;
    String uid;
    String docId;

    String memberId;

    private JitsiMeetView view;
    public static final String SERVER_URL = "https://theorganicdelight.com/";
    public static final String JIT_SI_SERVER_URL = "https://meet.jit.si/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_video_call);

        roomCode = "" + System.currentTimeMillis();
        memberId = getIntent().getStringExtra("memberId");
        uid = getIntent().getStringExtra("memberId");
        docId = getIntent().getStringExtra("docId");


        joinMeeting(roomCode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(AppUtils.VIDEO_CALLS).document(roomCode).addSnapshotListener((value, error) -> {
            if (error == null && value.getData() != null) {
                String callStatus = value.getString(AppUtils.CALL_STATUS);

                if (null != value.getString(AppUtils.CALL_REJECTED_BY) && AppUtils.CALL_REJECTED_BY.equalsIgnoreCase(AppUtils.PATIENT))
                    Toast.makeText(App.context, "Patient rejected your call", Toast.LENGTH_SHORT).show();

                if (AppUtils.CALL_DISCONNECTED.equalsIgnoreCase(callStatus) || AppUtils.CALL_MISSED.equalsIgnoreCase(callStatus)) {
                    view.dispose();
                    finish();
                }

            }
        });
    }

    private void joinMeeting(String roomCode) {
        view = new JitsiMeetView(this);
        JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                .setRoom(JIT_SI_SERVER_URL + roomCode)
                .setFeatureFlag("toolbox.enabled", false)
                .setFeatureFlag("raise-hand.enabled", false)
                .setFeatureFlag("recording.enabled", false)
                .setFeatureFlag("pip.enabled", true)
                .setFeatureFlag("notifications.enabled", true)
                .setFeatureFlag("call-integration.enabled", true)
                .setFeatureFlag("toolbox.alwaysVisible", false)
                .setFeatureFlag("video-share.enabled", false)
                .setWelcomePageEnabled(false)
                .build();

        view.setListener(this);
        view.join(options);
        setContentView(view);
    }


    @Override
    public void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        JitsiMeetActivityDelegate.onActivityResult(
                this, requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        JitsiMeetActivityDelegate.onBackPressed();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        view.dispose();
        view = null;
        JitsiMeetActivityDelegate.onHostDestroy(this);
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onConferenceWillJoin(Map<String, Object> data) {
        super.onConferenceWillJoin(data);

    }

    @Override
    public void onConferenceJoined(Map<String, Object> data) {
        super.onConferenceJoined(data);
        hitVideoCall();
        AppUtils.createCallData(AppUtils.CALL_STARTED, roomCode, uid, docId);

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

                }

                @Override
                public void onFailure(String msg) {

                }
            });
        } else
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConferenceTerminated(Map<String, Object> data) {
        super.onConferenceTerminated(data);
        JitsiMeetActivityDelegate.onHostDestroy(this);
        Toast.makeText(getApplicationContext(), "Call Disconnected ", Toast.LENGTH_SHORT).show();
        AppUtils.updateTodatabase(AppUtils.CALL_DISCONNECTED, roomCode);

    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void join(JitsiMeetConferenceOptions options) {
        super.join(options);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        JitsiMeetActivityDelegate.onNewIntent(intent);
    }

    @Override
    public void onRequestPermissionsResult(
            final int requestCode,
            final String[] permissions,
            final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        JitsiMeetActivityDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JitsiMeetActivityDelegate.onHostResume(this);
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        JitsiMeetActivityDelegate.onHostPause(this);
        Log.d(TAG, "onStop: ");
    }
}
