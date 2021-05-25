package com.criteriontech.digidoctormax.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;
import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.ActivityCallReceiveBinding;
import com.criteriontech.digidoctormax.utils.BaseActivity;

public class CallReceiveActivity extends BaseActivity implements View.OnClickListener {
    private ActivityCallReceiveBinding binding;

    Ringtone r;

    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCallReceiveBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
        setListeners();

    }

    @SuppressLint("NewApi")
    private void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        playSound();

        notificationManager = getSystemService(NotificationManager.class);

        registerReceiver(receiver, new IntentFilter("finishCall"));

        if (!getIntent().getStringExtra("profilePhotoPath").equals("")) {
            Glide.with(mActivity)
                    .load(getIntent().getStringExtra("profilePhotoPath").trim())
                    .placeholder(R.drawable.ic_name)
                    .into(binding.ivUserImage);
        } else {
            binding.ivUserImage.setImageResource((R.drawable.ic_name));
        }

        binding.tvName.setText(getIntent().getStringExtra("doctorName"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void setListeners() {
        binding.ivReceiveCall.setOnClickListener(this);
        binding.ivDisconnectCall.setOnClickListener(this);

    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            notificationManager.cancelAll();

            if (r.isPlaying()) {
                r.stop();
            }

            finish();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivReceiveCall:

                animatePickCall();

                break;

            case R.id.ivDisconnectCall:

                animateDisconnectCall();

                break;

        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            activityManager.moveTaskToFront(getTaskId(), 0);
        }

    }

    @Override
    public void onBackPressed() {

    }

    @SuppressLint("NewApi")
    private void animatePickCall() {

        int x = binding.getRoot().getLeft();
        int y = binding.getRoot().getBottom();

        int startRadius = 0;
        int endRadius = (int) Math.hypot(binding.getRoot().getWidth(), binding.getRoot().getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(binding.rlReceiveCall, x, y, startRadius, endRadius);

        binding.rlReceiveCall.setVisibility(View.VISIBLE);

        anim.start();

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                notificationManager.cancelAll();

                Intent intent;
                intent = new Intent(mActivity, VideoActivity.class);
                intent.putExtra("roomName", getIntent().getStringExtra("roomName"));
                intent.putExtra("accessToken", getIntent().getStringExtra("accessToken"));
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    @SuppressLint("NewApi")
    private void animateDisconnectCall() {

        int x = binding.getRoot().getRight();
        int y = binding.getRoot().getBottom();

        int startRadius = 0;
        int endRadius = (int) Math.hypot(binding.getRoot().getWidth(), binding.getRoot().getHeight());

        binding.rlReceiveCall.setBackgroundColor(getResources().getColor(R.color.red));
        Animator anim = ViewAnimationUtils.createCircularReveal(binding.rlReceiveCall, x, y, startRadius, endRadius);

        binding.rlReceiveCall.setVisibility(View.VISIBLE);

        anim.start();

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                sendBroadcast(new Intent("finishCall"));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    public void playSound() {

        try {
            Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            r = RingtoneManager.getRingtone(this, notificationSoundUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                r.setLooping(true);
            } else {
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        if (!r.isPlaying()) {
                            r.play();
                        }
                    }
                }, 1000, 100);
            }

            r.play();

            new Handler().postDelayed(() -> {
                r.stop();
                notificationManager.cancelAll();
                finish();
            }, 15000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {

                }
                return true;

            default:
                return super.dispatchKeyEvent(event);
        }
    }
}