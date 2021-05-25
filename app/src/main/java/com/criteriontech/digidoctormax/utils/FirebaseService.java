package com.criteriontech.digidoctormax.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirebaseService extends FirebaseMessagingService {
    String CHANNEL_ID = "digi_channel";
    String CHANNEL_NAME= "DIGI DOCTOR";
    String CHANNEL_DESC = "DIGI Channel";

    @Override
    public void onNewToken(String token) {
        Log.v(TAG, "Refreshed token: " + token);
        //sendRegistrationToServer(token);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.v(TAG, "from" + remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            Log.v(TAG, "Message data payload: " + remoteMessage.getData());
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //  scheduleJob();
            } else {
                // Handle message within 10 seconds
                //  handleNow();
            }
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "notification1:" + remoteMessage.getNotification().getTag());
        }
    }
}