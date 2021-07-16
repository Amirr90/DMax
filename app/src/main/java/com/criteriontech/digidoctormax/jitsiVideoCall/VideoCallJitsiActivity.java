package com.criteriontech.digidoctormax.jitsiVideoCall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.criteriontech.digidoctormax.utils.AppUtils;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetActivityDelegate;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.util.Map;

public class VideoCallJitsiActivity extends JitsiMeetActivity implements JitsiMeetActivityInterface, JitsiMeetViewListener {


    String roomCode;

    FirebaseFirestore firestore;
    private JitsiMeetView view;
    public static final String SERVER_URL = "https://theorganicdelight.com/";
    public static final String JIT_SI_SERVER_URL = "https://meet.jit.si/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        roomCode = getIntent().getStringExtra(AppUtils.ROOM_CODE);
        firestore = FirebaseFirestore.getInstance();

        Log.d(TAG, "onCreate: " + roomCode);


        listenForReaTimeCallStatus();
        joinMeeting(roomCode);
    }


    private void listenForReaTimeCallStatus() {
        firestore.collection(AppUtils.VIDEO_CALLS_DEMO)
                .document(roomCode)
                .addSnapshotListener(this, (value, error) -> {
                    if (null == error && null != value) {
                        String callStatus = value.getString(AppUtils.CALL_STATUS);
                        if (null != callStatus && !TextUtils.isEmpty(callStatus)) {
                            if (callStatus.equalsIgnoreCase(AppUtils.CALL_DISCONNECTED)) {
                                closeJitsiVideoCall();
                            }
                        }
                    }
                });
    }

    private void closeJitsiVideoCall() {
        view.dispose();
        finish();
    }

    @Override
    public void onConferenceTerminated(Map<String, Object> data) {
        super.onConferenceTerminated(data);
        closeMeetingToDatabase(AppUtils.CALL_DISCONNECTED, roomCode);
    }

    private void closeMeetingToDatabase(String callStatus, String roomCode) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(AppUtils.VIDEO_CALLS_DEMO).document(roomCode).update(AppUtils.CALL_STATUS, callStatus);
    }

    private void joinMeeting(String roomCode) {
        view = new JitsiMeetView(this);
        JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                .setRoom(SERVER_URL + roomCode)
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
}