package com.criteriontech.digidoctormax.utils;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.activity.CallReceiveActivity;
import com.criteriontech.digidoctormax.activity.VideoActivity;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;

public class WakefulBroadcasterReceiver extends BroadcastReceiver {

    private boolean isRegistered;

    private static final String TAG = "CallBroadcastReceiver";
    public static String CHANNEL_ID = "CallChannel";
    public static String CHANNEL_NAME = "Alarm Channel";

    KeyguardManager keyguardManager;

    NotificationManager mManager;

    @Override
    public void onReceive(final Context context, Intent intent) {

        Log.d("Broadcast", "In Broadcast");

        showNotification(context, intent);

    }

    @SuppressLint("NewApi")
    public void showNotification(Context context, Intent intent) {

        Intent wakeIntent = new Intent(context, CallReceiveActivity.class);
        wakeIntent.putExtra("roomName", intent.getStringExtra("roomName"));
        wakeIntent.putExtra("accessToken", intent.getStringExtra("accessToken"));
        wakeIntent.putExtra("profilePhotoPath", intent.getStringExtra("profilePhotoPath"));
        wakeIntent.putExtra("doctorName", intent.getStringExtra("doctorName"));
        wakeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Intent videoIntent = new Intent(context, VideoActivity.class);
        videoIntent.putExtra("roomName", intent.getStringExtra("roomName"));
        videoIntent.putExtra("accessToken", intent.getStringExtra("accessToken"));
        videoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        String msg = "", title = "";
        msg = intent.getStringExtra("message");
        title = intent.getStringExtra("title");

/* Intent wakeupIntent = new Intent(this, CallReceiveActivity.class);
wakeupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/

        PendingIntent wakeupPendingIntent = PendingIntent.getActivity(context, 0, wakeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent videoPendingIntent = PendingIntent.getActivity(context, 0, videoIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);

        PowerManager powerManager = (PowerManager) context.getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP
                        | PowerManager.ON_AFTER_RELEASE,
                "MyApp::" + TAG);
        wakeLock.acquire(10000);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(wakeIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel androidChannel = new NotificationChannel(CHANNEL_ID,
                    title, NotificationManager.IMPORTANCE_HIGH);
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager(context).createNotificationChannel(androidChannel);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setTicker(title)
                    .setContentText(msg)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setCategory(NotificationCompat.CATEGORY_CALL)
                    .setVibrate(new long[]{200, 400});
            if (!Settings.canDrawOverlays(context)) {
                notificationBuilder.setContentIntent(videoPendingIntent);
            }

            getManager(context).notify(101, notificationBuilder.build());

            context.startActivity(wakeIntent);

        } else {
            try {

                @SuppressLint({"NewApi", "LocalSuppress"})
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setTicker(title)
                        .setContentText(msg)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
// .setFullScreenIntent(wakeupPendingIntent, true)
// .setContentIntent(resultPendingIntent)
// .setDefaults(Notification.DEFAULT_ALL)
                        .setLights(0xFF760193, 300, 1000)
// .setAutoCancel(true)
                        .setVibrate(new long[]{200, 400});

                if (!Settings.canDrawOverlays(context)) {
                    notificationBuilder.setContentIntent(videoPendingIntent);
                }

                int timestamp = 1000;

                getManager(context).notify(timestamp, notificationBuilder.build());

                context.startActivity(wakeIntent);

            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }

        wakeLock.release();


    }

    private NotificationManager getManager(Context context) {
        if (mManager == null) {
            mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public void register(final Context context) {
        if (!isRegistered) {
            context.registerReceiver(this, new IntentFilter("myReceiver"));
            isRegistered = true;
        }
    }

    public void unregister(final Context context) {
        if (isRegistered) {
            context.unregisterReceiver(this);
            isRegistered = false;
        }
    }

}