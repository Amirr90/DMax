

package com.criteriontech.digidoctormax;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Rational;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.criteriontech.digidoctormax.databinding.FragmentPipVideoCallBinding;
import com.criteriontech.digidoctormax.fragment.home.DrHomeActivity;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.CameraCapturerCompat;
import com.criteriontech.digidoctormax.utils.VideoCallUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.twilio.audioswitch.selection.AudioDevice;
import com.twilio.audioswitch.selection.AudioDeviceSelector;
import com.twilio.video.AudioCodec;
import com.twilio.video.CameraCapturer;
import com.twilio.video.ConnectOptions;
import com.twilio.video.EncodingParameters;
import com.twilio.video.G722Codec;
import com.twilio.video.H264Codec;
import com.twilio.video.IsacCodec;
import com.twilio.video.LocalAudioTrack;
import com.twilio.video.LocalParticipant;
import com.twilio.video.LocalVideoTrack;
import com.twilio.video.LogLevel;
import com.twilio.video.LogModule;
import com.twilio.video.OpusCodec;
import com.twilio.video.PcmaCodec;
import com.twilio.video.PcmuCodec;
import com.twilio.video.RemoteAudioTrack;
import com.twilio.video.RemoteAudioTrackPublication;
import com.twilio.video.RemoteDataTrack;
import com.twilio.video.RemoteDataTrackPublication;
import com.twilio.video.RemoteParticipant;
import com.twilio.video.RemoteVideoTrack;
import com.twilio.video.RemoteVideoTrackPublication;
import com.twilio.video.Room;
import com.twilio.video.TwilioException;
import com.twilio.video.Video;
import com.twilio.video.VideoCodec;
import com.twilio.video.VideoRenderer;
import com.twilio.video.VideoTrack;
import com.twilio.video.VideoView;
import com.twilio.video.Vp8Codec;
import com.twilio.video.Vp9Codec;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import kotlin.Unit;

public class PipVideoCallFragment extends BottomSheetDialogFragment {


    FragmentPipVideoCallBinding binding;


    CharSequence[] item;
    private static final int CAMERA_MIC_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "VideoActivity";

    int isConnected = 0;

    private static final String LOCAL_AUDIO_TRACK_NAME = "mic";
    private static final String LOCAL_VIDEO_TRACK_NAME = "camera";

    /*
     * You must provide a Twilio Access Token to connect to the Video service
     */
    public static String TWILIO_ACCESS_TOKEN = "";
    public static final boolean USE_TOKEN_SERVER = false;

    /*
     * Access token used to connect. This field will be set either from the console generated token
     * or the request to the token server.
     */
    private String accessToken;

    /*
     * A Room represents communication between a local participant and one or more participants.
     */
    private Room room;
    private LocalParticipant localParticipant;

    /*
     * AudioCodec and VideoCodec represent the preferred codec for encoding and decoding audio and
     * video.
     */
    private AudioCodec audioCodec;
    private VideoCodec videoCodec;

    /*
     * Encoding parameters represent the sender side bandwidth constraints.
     */
    private EncodingParameters encodingParameters;

    /*
     * A VideoView receives frames from a local or remote video track and renders them
     * to an associated view.
     */
    private VideoView primaryVideoView;
    private VideoView thumbnailVideoView;

    /*
     * Android shared preferences used for settings
     */
    private SharedPreferences preferences;

    /*
     * Android application UI elements
     */
    private CameraCapturerCompat cameraCapturerCompat;
    private LocalAudioTrack localAudioTrack;
    private LocalVideoTrack localVideoTrack;
    private FloatingActionButton connectActionFab;
    private FloatingActionButton switchCameraActionFab;
    private FloatingActionButton localVideoActionFab;
    private FloatingActionButton muteActionFab;
    private ProgressBar reconnectingProgressBar;
    private String remoteParticipantIdentity;
    private ImageView ivSpeaker, ivBack;
    private ConstraintLayout clHeader;

    private VideoRenderer localVideoView;
    private boolean disconnectedFromOnDestroy;
    private boolean enableAutomaticSubscription;

    NotificationManager notificationManager;

    AudioDeviceSelector audioDeviceSelector;
    private int savedVolumeControlStream;
    private int statusPiPMode = 0;
    Activity mActivity;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPipVideoCallBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = requireActivity();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notificationManager = requireActivity().getSystemService(NotificationManager.class);
        } else {
            notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        }

        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Video.setLogLevel(LogLevel.ALL);
        Video.setModuleLogLevel(LogModule.WEBRTC, LogLevel.ALL);


        TWILIO_ACCESS_TOKEN = getArguments().getString("accessToken");

        Log.d(TAG, "onCreate: " + TWILIO_ACCESS_TOKEN);

        primaryVideoView = view.findViewById(R.id.primary_video_view);
        thumbnailVideoView = view.findViewById(R.id.thumbnail_video_view);
        reconnectingProgressBar = view.findViewById(R.id.reconnecting_progress_bar);

        connectActionFab = view.findViewById(R.id.connect_action_fab);
        switchCameraActionFab = view.findViewById(R.id.switch_camera_action_fab);
        localVideoActionFab = view.findViewById(R.id.local_video_action_fab);
        muteActionFab = view.findViewById(R.id.mute_action_fab);

        ivSpeaker = view.findViewById(R.id.ivSpeaker);
        ivBack = view.findViewById(R.id.ivBack);

        clHeader = view.findViewById(R.id.clHeader);

        audioDeviceSelector = new AudioDeviceSelector(mActivity);
        savedVolumeControlStream = mActivity.getVolumeControlStream();
        mActivity.setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);

        /*
         * Get shared preferences to read settings
         */
        preferences = PreferenceManager.getDefaultSharedPreferences(mActivity);

        /*
         * Check camera and microphone permissions. Needed in Android M.
         */
        if (!checkPermissionForCameraAndMicrophone()) {
            requestPermissionForCameraAndMicrophone();
        } else {
            createAudioAndVideoTracks();
        }

        /*
         * Set the initial state of the UI
         */
        intializeUI();

        new Handler().postDelayed(() -> {
            if (isConnected == 0) {
                if (room != null) {
                    room.disconnect();
                }
            }
        }, 18000);

        audioDeviceSelector.start((audioDevices, audioDevice) -> {
            updateAudioDeviceIcon(audioDevice);
            return Unit.INSTANCE;
        });

        ivSpeaker.setOnClickListener(view3 -> {

            showAudioDevices();

        });

        ivBack.setOnClickListener(view4 -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && isConnected == 1) {
                PackageManager packageManager = mActivity.getPackageManager();

                if (packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        PictureInPictureParams.Builder params = new PictureInPictureParams.Builder();
                        params.setAspectRatio(getPipRatio());
                        mActivity.enterPictureInPictureMode(params.build());

                    } else {
                        mActivity.enterPictureInPictureMode();

                    }
                    statusPiPMode = 1;
                }
            } else {
                mActivity.finish();
            }

        });
        clHeader.setVisibility(View.GONE);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Rational getPipRatio() {
        int width = mActivity.getWindow().getDecorView().getWidth();
        int height = mActivity.getWindow().getDecorView().getHeight();
        return new Rational(width, height);
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {

        super.onDismiss(dialog);
        dismiss();
    }


    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);

        if (isInPictureInPictureMode) {
            // Hide the full-screen UI (controls, etc.) while in picture-in-picture mode.

            connectActionFab.setVisibility(View.GONE);
            switchCameraActionFab.setVisibility(View.GONE);
            localVideoActionFab.setVisibility(View.GONE);
            muteActionFab.setVisibility(View.GONE);

            clHeader.setVisibility(View.GONE);

            primaryVideoView.setVisibility(View.VISIBLE);
            thumbnailVideoView.setVisibility(View.GONE);

        } else {
            // Restore the full-screen UI.

            connectActionFab.setVisibility(View.VISIBLE);
            switchCameraActionFab.setVisibility(View.VISIBLE);
            localVideoActionFab.setVisibility(View.VISIBLE);
            muteActionFab.setVisibility(View.VISIBLE);

            clHeader.setVisibility(View.VISIBLE);

            primaryVideoView.setVisibility(View.VISIBLE);
            thumbnailVideoView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_MIC_PERMISSION_REQUEST_CODE) {
            boolean cameraAndMicPermissionGranted = true;

            for (int grantResult : grantResults) {
                cameraAndMicPermissionGranted &= grantResult == PackageManager.PERMISSION_GRANTED;
            }

            if (cameraAndMicPermissionGranted) {
                createAudioAndVideoTracks();
            } else {
                Toast.makeText(mActivity,
                        R.string.permissions_needed,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();

        if (statusPiPMode == 0) {
            continueVideo();
            statusPiPMode = 0;
        }

    }

    private void continueVideo() {

        audioCodec = getAudioCodecPreference(VideoCallUtils.PREF_AUDIO_CODEC,
                VideoCallUtils.PREF_AUDIO_CODEC_DEFAULT);
        videoCodec = getVideoCodecPreference(VideoCallUtils.PREF_VIDEO_CODEC,
                VideoCallUtils.PREF_VIDEO_CODEC_DEFAULT);
        enableAutomaticSubscription = getAutomaticSubscriptionPreference(VideoCallUtils.PREF_ENABLE_AUTOMATIC_SUBSCRIPTION,
                VideoCallUtils.PREF_ENABLE_AUTOMATIC_SUBSCRIPTION_DEFAULT);

        /*
         * Get latest encoding parameters
         */
        final EncodingParameters newEncodingParameters = getEncodingParameters();

        /*
         * If the local video track was released when the app was put in the background, recreate.
         */

        if (localVideoTrack == null && checkPermissionForCameraAndMicrophone()) {
            localVideoTrack = LocalVideoTrack.create(requireContext(),
                    true,
                    cameraCapturerCompat.getVideoCapturer(),
                    LOCAL_VIDEO_TRACK_NAME);
            localVideoTrack.addRenderer(localVideoView);

            /*
             * If connected to a Room then share the local video track.
             */
            if (localParticipant != null) {
                localParticipant.publishTrack(localVideoTrack);

                /*
                 * Update encoding parameters if they have changed.
                 */
                if (!newEncodingParameters.equals(encodingParameters)) {
                    localParticipant.setEncodingParameters(newEncodingParameters);
                }
            }
        }
        /*
         * Update encoding parameters
         */
        encodingParameters = newEncodingParameters;

        /*
         * Route audio through cached value.
         */
//        audioManager.setSpeakerphoneOn(isSpeakerPhoneEnabled);

        /*
         * Update reconnecting UI
         */
        if (room != null) {
            reconnectingProgressBar.setVisibility((room.getState() != Room.State.RECONNECTING) ?
                    View.GONE :
                    View.VISIBLE);
        }

        connectToRoom(getArguments().getString("roomName"));

      /*  if (getIntent().getStringExtra("roomName") != null) {
            connectToRoom(getIntent().getStringExtra("roomName"));
        }*/
    }

    @Override
    public void onPause() {
        /*
         * Release the local video track before going in the background. This ensures that the
         * camera can be used by other applications while this app is in the background.
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!mActivity.isInPictureInPictureMode()) {
                pauseVideo();
            }
        } else {
            pauseVideo();
        }

        super.onPause();
    }

    private void pauseVideo() {
        if (localVideoTrack != null) {
            /*
             * If this local video track is being shared in a Room, unpublish from room before
             * releasing the video track. Participants will be notified that the track has been
             * unpublished.
             */
            if (localParticipant != null) {
                localParticipant.unpublishTrack(localVideoTrack);
            }

            localVideoTrack.release();
            localVideoTrack = null;
        }
    }

    @Override
    public void onDestroy() {

        /*
         * Tear down audio management and restore previous volume stream
         */
        audioDeviceSelector.stop();
        mActivity.setVolumeControlStream(savedVolumeControlStream);

        /*
         * Always disconnect from the room before leaving the Activity to
         * ensure any memory allocated to the Room resource is freed.
         */
        if (room != null && room.getState() != Room.State.DISCONNECTED) {
            room.disconnect();
            disconnectedFromOnDestroy = true;
        }

        /*
         * Release the local audio and video tracks ensuring any memory allocated to audio
         * or video is freed.
         */
        if (localAudioTrack != null) {
            localAudioTrack.release();
            localAudioTrack = null;
        }
        if (localVideoTrack != null) {
            localVideoTrack.release();
            localVideoTrack = null;
        }

        super.onDestroy();
    }

    private boolean checkPermissionForCameraAndMicrophone() {
        int resultCamera = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA);
        int resultMic = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.RECORD_AUDIO);
        return resultCamera == PackageManager.PERMISSION_GRANTED &&
                resultMic == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionForCameraAndMicrophone() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CAMERA) ||
                ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                        Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(mActivity,
                    R.string.permissions_needed,
                    Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(
                    mActivity,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},
                    CAMERA_MIC_PERMISSION_REQUEST_CODE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getShowsDialog()) {
            Objects.requireNonNull(getDialog()).setTitle("Video Call");
        } else {
            requireActivity().setTitle("Video Call");
        }
        return super.onCreateDialog(savedInstanceState);


    }

    private void createAudioAndVideoTracks() {

        Log.d(TAG, "createAudioAndVideoTracks: ");
        // Share your microphone
        localAudioTrack = LocalAudioTrack.create(mActivity, true, LOCAL_AUDIO_TRACK_NAME);

        // Share your camera
        cameraCapturerCompat = new CameraCapturerCompat(mActivity, getAvailableCameraSource());
        localVideoTrack = LocalVideoTrack.create(mActivity,
                true,
                cameraCapturerCompat.getVideoCapturer(),
                LOCAL_VIDEO_TRACK_NAME);
        primaryVideoView.setMirror(true);
        localVideoTrack.addRenderer(primaryVideoView);
        localVideoView = primaryVideoView;
    }

    private CameraCapturer.CameraSource getAvailableCameraSource() {
        Log.d(TAG, "getAvailableCameraSource: ");
        return (CameraCapturer.isSourceAvailable(CameraCapturer.CameraSource.FRONT_CAMERA)) ?
                (CameraCapturer.CameraSource.FRONT_CAMERA) :
                (CameraCapturer.CameraSource.BACK_CAMERA);


    }

    private void connectToRoom(String roomName) {

        audioDeviceSelector.activate();
        ConnectOptions.Builder connectOptionsBuilder = new ConnectOptions.Builder(TWILIO_ACCESS_TOKEN)
                .roomName(roomName);

        /*
         * Add local audio track to connect options to share with participants.
         */

        if (localAudioTrack != null) {
            connectOptionsBuilder
                    .audioTracks(Collections.singletonList(localAudioTrack));
        }

        /*
         * Add local video track to connect options to share with participants.
         */
        if (localVideoTrack != null) {
            Log.d(TAG, "connectToRoom: " + localVideoTrack.toString());
            connectOptionsBuilder.videoTracks(Collections.singletonList(localVideoTrack));
        }

        /*
         * Set the preferred audio and video codec for media.
         */
        connectOptionsBuilder.preferAudioCodecs(Collections.singletonList(audioCodec));
        connectOptionsBuilder.preferVideoCodecs(Collections.singletonList(videoCodec));
        Log.d(TAG, "connectToRoom: " + videoCodec.toString());

        /*
         * Set the sender side encoding parameters.
         */
        connectOptionsBuilder.encodingParameters(encodingParameters);
        Log.d(TAG, "encodingParameters: " + encodingParameters.toString());

        connectOptionsBuilder.enableAutomaticSubscription(enableAutomaticSubscription);

        room = Video.connect(mActivity, connectOptionsBuilder.build(), roomListener());

        Log.d(TAG, "connectToRoom: room:getMediaRegion" + room.getMediaRegion());
        setDisconnectAction();

    }


    private void intializeUI() {
        Log.d(TAG, "intializeUI: ");
        connectActionFab.setImageDrawable(ContextCompat.getDrawable(mActivity,
                R.drawable.ic_video_call_white_24dp));
        connectActionFab.show();
        setDisconnectAction();
        switchCameraActionFab.show();
        switchCameraActionFab.setOnClickListener(switchCameraClickListener());
        localVideoActionFab.show();
        localVideoActionFab.setOnClickListener(localVideoClickListener());
        muteActionFab.show();
        muteActionFab.setOnClickListener(muteClickListener());

    }

    /*
     * Get the preferred audio codec from shared preferences
     */
    private AudioCodec getAudioCodecPreference(String key, String defaultValue) {
        final String audioCodecName = preferences.getString(key, defaultValue);

        switch (audioCodecName) {
            case IsacCodec.NAME:
                return new IsacCodec();
            case OpusCodec.NAME:
                return new OpusCodec();
            case PcmaCodec.NAME:
                return new PcmaCodec();
            case PcmuCodec.NAME:
                return new PcmuCodec();
            case G722Codec.NAME:
                return new G722Codec();
            default:
                return new OpusCodec();
        }
    }

    /*
     * Get the preferred video codec from shared preferences
     */
    private VideoCodec getVideoCodecPreference(String key, String defaultValue) {
        final String videoCodecName = preferences.getString(key, defaultValue);

        switch (videoCodecName) {
            case Vp8Codec.NAME:
                boolean simulcast = preferences.getBoolean(VideoCallUtils.PREF_VP8_SIMULCAST,
                        VideoCallUtils.PREF_VP8_SIMULCAST_DEFAULT);
                return new Vp8Codec(simulcast);
            case H264Codec.NAME:
                return new H264Codec();
            case Vp9Codec.NAME:
                return new Vp9Codec();
            default:
                return new Vp8Codec();
        }
    }

    private boolean getAutomaticSubscriptionPreference(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    private EncodingParameters getEncodingParameters() {
        final int maxAudioBitrate = Integer.parseInt(
                preferences.getString(VideoCallUtils.PREF_SENDER_MAX_AUDIO_BITRATE,
                        VideoCallUtils.PREF_SENDER_MAX_AUDIO_BITRATE_DEFAULT));
        final int maxVideoBitrate = Integer.parseInt(
                preferences.getString(VideoCallUtils.PREF_SENDER_MAX_VIDEO_BITRATE,
                        VideoCallUtils.PREF_SENDER_MAX_VIDEO_BITRATE_DEFAULT));

        return new EncodingParameters(maxAudioBitrate, maxVideoBitrate);
    }

    /*
     * The actions performed during disconnect.
     */
    private void setDisconnectAction() {
        connectActionFab.setImageDrawable(ContextCompat.getDrawable(mActivity,
                R.drawable.ic_call_end_white_24px));
        connectActionFab.show();
        connectActionFab.setOnClickListener(disconnectClickListener());
    }


    /*
     * Called when remote participant joins the room
     */
    @SuppressLint("SetTextI18n")
    private void addRemoteParticipant(RemoteParticipant remoteParticipant) {
        /*
         * This app only displays video for one additional participant per Room
         */
        if (thumbnailVideoView.getVisibility() == View.VISIBLE) {
            Snackbar.make(connectActionFab,
                    "Multiple participants are not currently support in this UI",
                    Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }
        remoteParticipantIdentity = remoteParticipant.getIdentity();

        /*
         * Add remote participant renderer
         */
        if (remoteParticipant.getRemoteVideoTracks().size() > 0) {
            RemoteVideoTrackPublication remoteVideoTrackPublication =
                    remoteParticipant.getRemoteVideoTracks().get(0);

            /*
             * Only render video tracks that are subscribed to
             */
            if (remoteVideoTrackPublication.isTrackSubscribed()) {
                addRemoteParticipantVideo(remoteVideoTrackPublication.getRemoteVideoTrack());
            }
        }

        /*
         * Start listening for participant events
         */
        remoteParticipant.setListener(remoteParticipantListener());

        isConnected = 1;
        Log.d(TAG, "addRemoteParticipant: isConnected" + isConnected);
    }

    /*
     * Set primary view as renderer for participant video track
     */
    private void addRemoteParticipantVideo(VideoTrack videoTrack) {
        moveLocalVideoToThumbnailView();
        primaryVideoView.setMirror(false);
        videoTrack.addRenderer(primaryVideoView);
        Log.d(TAG, "addRemoteParticipantVideo: " + primaryVideoView.getVideoScaleType());
    }

    private void moveLocalVideoToThumbnailView() {
        if (thumbnailVideoView.getVisibility() == View.GONE) {

            try {
                thumbnailVideoView.setVisibility(View.VISIBLE);
                localVideoTrack.removeRenderer(primaryVideoView);
                localVideoTrack.addRenderer(thumbnailVideoView);
                localVideoView = thumbnailVideoView;
                thumbnailVideoView.setMirror(cameraCapturerCompat.getCameraSource() ==
                        CameraCapturer.CameraSource.FRONT_CAMERA);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /*
     * Called when remote participant leaves the room
     */
    @SuppressLint({"SetTextI18n", "NewApi"})
    private void removeRemoteParticipant(RemoteParticipant remoteParticipant) {
        if (!remoteParticipant.getIdentity().equals(remoteParticipantIdentity)) {
            return;
        }

        /*
         * Remove remote participant renderer
         */
        if (!remoteParticipant.getRemoteVideoTracks().isEmpty()) {
            RemoteVideoTrackPublication remoteVideoTrackPublication =
                    remoteParticipant.getRemoteVideoTracks().get(0);

            /*
             * Remove video only if subscribed to participant track
             */
            if (remoteVideoTrackPublication.isTrackSubscribed()) {
                removeParticipantVideo(remoteVideoTrackPublication.getRemoteVideoTrack());
            }
        }

        if (room != null) {
            room.disconnect();
        }

        audioDeviceSelector.deactivate();


        if (mActivity.isInPictureInPictureMode()) {
            mActivity.finish();
            mActivity.finish();
        } else {
            startActivity(new Intent(mActivity, DrHomeActivity.class));

        }

        notificationManager.cancelAll();


    }

    private void removeParticipantVideo(VideoTrack videoTrack) {
        videoTrack.removeRenderer(primaryVideoView);
    }

    private void moveLocalVideoToPrimaryView() {
        if (thumbnailVideoView.getVisibility() == View.VISIBLE) {
            thumbnailVideoView.setVisibility(View.GONE);
            if (localVideoTrack != null) {
                localVideoTrack.removeRenderer(thumbnailVideoView);
                localVideoTrack.addRenderer(primaryVideoView);
            }
            localVideoView = primaryVideoView;
            primaryVideoView.setMirror(cameraCapturerCompat.getCameraSource() ==
                    CameraCapturer.CameraSource.FRONT_CAMERA);
        }
    }

    /*
     * Room events listener
     */
    @SuppressLint("SetTextI18n")
    private Room.Listener roomListener() {
        return new Room.Listener() {
            @Override
            public void onConnected(Room room) {
                localParticipant = room.getLocalParticipant();
                mActivity.setTitle(getString(R.string.digidoctor_video_call));

                for (RemoteParticipant remoteParticipant : room.getRemoteParticipants()) {
                    addRemoteParticipant(remoteParticipant);
                    break;
                }
            }

            @Override
            public void onReconnecting(@NonNull Room room, @NonNull TwilioException twilioException) {
                reconnectingProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReconnected(@NonNull Room room) {
                reconnectingProgressBar.setVisibility(View.GONE);
            }

            @SuppressLint("NewApi")
            @Override
            public void onConnectFailure(Room room, TwilioException e) {
                e.printStackTrace();
                intializeUI();

                AppUtils.showToastSort(requireActivity(), getString(R.string.connection_failure));

                audioDeviceSelector.deactivate();


                startActivity(new Intent(mActivity, DrHomeActivity.class));
            }

            @SuppressLint("NewApi")
            @Override
            public void onDisconnected(Room room2, TwilioException e) {
                localParticipant = null;
                reconnectingProgressBar.setVisibility(View.GONE);
                room = null;
                if (!disconnectedFromOnDestroy) {
                    audioDeviceSelector.deactivate();
                    intializeUI();
                    moveLocalVideoToPrimaryView();

                    if (mActivity.isInPictureInPictureMode()) {
                        mActivity.finish();
                    } else {
                        if (isConnected == 0) {
                            AppUtils.showToastSort(mActivity, getString(R.string.call_didnt_connect));
                        } else {
                            AppUtils.showToastSort(mActivity, getString(R.string.call_has_been_disconnected));
                        }


                        startActivity(new Intent(mActivity, DrHomeActivity.class));
                    }

                    notificationManager.cancelAll();

                }
            }

            @Override
            public void onParticipantConnected(Room room, RemoteParticipant remoteParticipant) {
                addRemoteParticipant(remoteParticipant);

            }

            @Override
            public void onParticipantDisconnected(Room room, RemoteParticipant remoteParticipant) {

                removeRemoteParticipant(remoteParticipant);

            }

            @Override
            public void onRecordingStarted(Room room) {
                /*
                 * Indicates when media shared to a Room is being recorded. Note that
                 * recording is only available in our Group Rooms developer preview.
                 */
                Log.d(TAG, "onRecordingStarted");
            }

            @Override
            public void onRecordingStopped(Room room) {
                /*
                 * Indicates when media shared to a Room is no longer being recorded. Note that
                 * recording is only available in our Group Rooms developer preview.
                 */
                Log.d(TAG, "onRecordingStopped");
            }
        };
    }

    @SuppressLint("SetTextI18n")
    private RemoteParticipant.Listener remoteParticipantListener() {
        return new RemoteParticipant.Listener() {
            @Override
            public void onAudioTrackPublished(RemoteParticipant remoteParticipant,
                                              RemoteAudioTrackPublication remoteAudioTrackPublication) {
                Log.i(TAG, String.format("onAudioTrackPublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrackPublication.getTrackSid(),
                        remoteAudioTrackPublication.isTrackEnabled(),
                        remoteAudioTrackPublication.isTrackSubscribed(),
                        remoteAudioTrackPublication.getTrackName()));
            }

            @Override
            public void onAudioTrackUnpublished(RemoteParticipant remoteParticipant,
                                                RemoteAudioTrackPublication remoteAudioTrackPublication) {
                Log.i(TAG, String.format("onAudioTrackUnpublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrackPublication.getTrackSid(),
                        remoteAudioTrackPublication.isTrackEnabled(),
                        remoteAudioTrackPublication.isTrackSubscribed(),
                        remoteAudioTrackPublication.getTrackName()));
            }

            @Override
            public void onDataTrackPublished(RemoteParticipant remoteParticipant,
                                             RemoteDataTrackPublication remoteDataTrackPublication) {
                Log.i(TAG, String.format("onDataTrackPublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrackPublication.getTrackSid(),
                        remoteDataTrackPublication.isTrackEnabled(),
                        remoteDataTrackPublication.isTrackSubscribed(),
                        remoteDataTrackPublication.getTrackName()));
            }

            @Override
            public void onDataTrackUnpublished(RemoteParticipant remoteParticipant,
                                               RemoteDataTrackPublication remoteDataTrackPublication) {
                Log.i(TAG, String.format("onDataTrackUnpublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrackPublication.getTrackSid(),
                        remoteDataTrackPublication.isTrackEnabled(),
                        remoteDataTrackPublication.isTrackSubscribed(),
                        remoteDataTrackPublication.getTrackName()));
            }

            @Override
            public void onVideoTrackPublished(RemoteParticipant remoteParticipant,
                                              RemoteVideoTrackPublication remoteVideoTrackPublication) {
                Log.i(TAG, String.format("onVideoTrackPublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrackPublication.getTrackSid(),
                        remoteVideoTrackPublication.isTrackEnabled(),
                        remoteVideoTrackPublication.isTrackSubscribed(),
                        remoteVideoTrackPublication.getTrackName()));
            }

            @Override
            public void onVideoTrackUnpublished(RemoteParticipant remoteParticipant,
                                                RemoteVideoTrackPublication remoteVideoTrackPublication) {
                Log.i(TAG, String.format("onVideoTrackUnpublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrackPublication.getTrackSid(),
                        remoteVideoTrackPublication.isTrackEnabled(),
                        remoteVideoTrackPublication.isTrackSubscribed(),
                        remoteVideoTrackPublication.getTrackName()));
            }

            @Override
            public void onAudioTrackSubscribed(RemoteParticipant remoteParticipant,
                                               RemoteAudioTrackPublication remoteAudioTrackPublication,
                                               RemoteAudioTrack remoteAudioTrack) {
                Log.i(TAG, String.format("onAudioTrackSubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrack: enabled=%b, playbackEnabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrack.isEnabled(),
                        remoteAudioTrack.isPlaybackEnabled(),
                        remoteAudioTrack.getName()));
            }

            @Override
            public void onAudioTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                 RemoteAudioTrackPublication remoteAudioTrackPublication,
                                                 RemoteAudioTrack remoteAudioTrack) {
                Log.i(TAG, String.format("onAudioTrackUnsubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrack: enabled=%b, playbackEnabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrack.isEnabled(),
                        remoteAudioTrack.isPlaybackEnabled(),
                        remoteAudioTrack.getName()));
            }

            @Override
            public void onAudioTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                       RemoteAudioTrackPublication remoteAudioTrackPublication,
                                                       TwilioException twilioException) {
                Log.i(TAG, String.format("onAudioTrackSubscriptionFailed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrackPublication: sid=%b, name=%s]" +
                                "[TwilioException: code=%d, message=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrackPublication.getTrackSid(),
                        remoteAudioTrackPublication.getTrackName(),
                        twilioException.getCode(),
                        twilioException.getMessage()));
            }

            @Override
            public void onDataTrackSubscribed(RemoteParticipant remoteParticipant,
                                              RemoteDataTrackPublication remoteDataTrackPublication,
                                              RemoteDataTrack remoteDataTrack) {
                Log.i(TAG, String.format("onDataTrackSubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrack.isEnabled(),
                        remoteDataTrack.getName()));
            }

            @Override
            public void onDataTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                RemoteDataTrackPublication remoteDataTrackPublication,
                                                RemoteDataTrack remoteDataTrack) {
                Log.i(TAG, String.format("onDataTrackUnsubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrack.isEnabled(),
                        remoteDataTrack.getName()));
            }

            @Override
            public void onDataTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                      RemoteDataTrackPublication remoteDataTrackPublication,
                                                      TwilioException twilioException) {
                Log.i(TAG, String.format("onDataTrackSubscriptionFailed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrackPublication: sid=%b, name=%s]" +
                                "[TwilioException: code=%d, message=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrackPublication.getTrackSid(),
                        remoteDataTrackPublication.getTrackName(),
                        twilioException.getCode(),
                        twilioException.getMessage()));
            }

            @Override
            public void onVideoTrackSubscribed(RemoteParticipant remoteParticipant,
                                               RemoteVideoTrackPublication remoteVideoTrackPublication,
                                               RemoteVideoTrack remoteVideoTrack) {
                Log.i(TAG, String.format("onVideoTrackSubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrack.isEnabled(),
                        remoteVideoTrack.getName()));
                addRemoteParticipantVideo(remoteVideoTrack);
            }

            @Override
            public void onVideoTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                 RemoteVideoTrackPublication remoteVideoTrackPublication,
                                                 RemoteVideoTrack remoteVideoTrack) {
                Log.i(TAG, String.format("onVideoTrackUnsubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrack.isEnabled(),
                        remoteVideoTrack.getName()));
                removeParticipantVideo(remoteVideoTrack);
            }

            @Override
            public void onVideoTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                       RemoteVideoTrackPublication remoteVideoTrackPublication,
                                                       TwilioException twilioException) {
                Log.i(TAG, String.format("onVideoTrackSubscriptionFailed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrackPublication: sid=%b, name=%s]" +
                                "[TwilioException: code=%d, message=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrackPublication.getTrackSid(),
                        remoteVideoTrackPublication.getTrackName(),
                        twilioException.getCode(),
                        twilioException.getMessage()));
                Snackbar.make(connectActionFab,
                        String.format("Failed to subscribe to %s video track",
                                remoteParticipant.getIdentity()),
                        Snackbar.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onAudioTrackEnabled(RemoteParticipant remoteParticipant,
                                            RemoteAudioTrackPublication remoteAudioTrackPublication) {

            }

            @Override
            public void onAudioTrackDisabled(RemoteParticipant remoteParticipant,
                                             RemoteAudioTrackPublication remoteAudioTrackPublication) {

            }

            @Override
            public void onVideoTrackEnabled(RemoteParticipant remoteParticipant,
                                            RemoteVideoTrackPublication remoteVideoTrackPublication) {

            }

            @Override
            public void onVideoTrackDisabled(RemoteParticipant remoteParticipant,
                                             RemoteVideoTrackPublication remoteVideoTrackPublication) {

            }
        };
    }

    private DialogInterface.OnClickListener connectClickListener(final EditText roomEditText) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                 * Connect to room
                 */
                connectToRoom(roomEditText.getText().toString());
            }
        };
    }

    private View.OnClickListener disconnectClickListener() {
        return v -> {
            if (room != null) {
                room.disconnect();
            } else {
                if (isConnected == 0) {

                }

                AppUtils.showToastSort(mActivity, getString(R.string.call_has_been_disconnected));

                audioDeviceSelector.deactivate();

                startActivity(new Intent(mActivity, DrHomeActivity.class));

                notificationManager.cancelAll();

            }

        };
    }

    private View.OnClickListener switchCameraClickListener() {
        return v -> {
            if (cameraCapturerCompat != null) {
                CameraCapturer.CameraSource cameraSource = cameraCapturerCompat.getCameraSource();
                cameraCapturerCompat.switchCamera();
                if (thumbnailVideoView.getVisibility() == View.VISIBLE) {
                    thumbnailVideoView.setMirror(cameraSource == CameraCapturer.CameraSource.BACK_CAMERA);
                } else {
                    primaryVideoView.setMirror(cameraSource == CameraCapturer.CameraSource.BACK_CAMERA);
                }
            }
        };
    }

    private View.OnClickListener localVideoClickListener() {
        return v -> {
            /*
             * Enable/disable the local video track
             */
            if (localVideoTrack != null) {
                boolean enable = !localVideoTrack.isEnabled();
                localVideoTrack.enable(enable);
                int icon;
                if (enable) {
                    icon = R.drawable.ic_videocam_white_24dp;
                    switchCameraActionFab.show();
                } else {
                    icon = R.drawable.ic_videocam_off_black_24dp;
                    switchCameraActionFab.hide();
                }
                localVideoActionFab.setImageDrawable(
                        ContextCompat.getDrawable(mActivity, icon));
            }
        };
    }

    private View.OnClickListener muteClickListener() {
        return v -> {
            /*
             * Enable/disable the local audio track. The results of this operation are
             * signaled to other Participants in the same Room. When an audio track is
             * disabled, the audio is muted.
             */
            if (localAudioTrack != null) {
                boolean enable = !localAudioTrack.isEnabled();
                localAudioTrack.enable(enable);
                int icon = enable ?
                        R.drawable.ic_mic_white_24dp : R.drawable.ic_mic_off_black_24dp;
                muteActionFab.setImageDrawable(ContextCompat.getDrawable(
                        mActivity, icon));
            }
        };
    }

    private void showAudioDevices() {
        AudioDevice selectedDevice = audioDeviceSelector.getSelectedAudioDevice();
        List<AudioDevice> availableAudioDevices = audioDeviceSelector.getAvailableAudioDevices();

        if (selectedDevice != null) {
            int selectedDeviceIndex = availableAudioDevices.indexOf(selectedDevice);

            ArrayList<String> audioDeviceNames = new ArrayList<>();
            for (AudioDevice a : availableAudioDevices) {
                audioDeviceNames.add(a.getName());
            }

            new AlertDialog.Builder(mActivity)
                    .setTitle(R.string.room_screen_select_device)
                    .setSingleChoiceItems(
                            audioDeviceNames.toArray(new CharSequence[0]),
                            selectedDeviceIndex,
                            (dialog, index) -> {
                                dialog.dismiss();
                                AudioDevice selectedAudioDevice = availableAudioDevices.get(index);
                                updateAudioDeviceIcon(selectedAudioDevice);
                                audioDeviceSelector.selectDevice(selectedAudioDevice);
                            }).create().show();
        }
    }

    private void updateAudioDeviceIcon(AudioDevice selectedAudioDevice) {
        int audioDeviceMenuIcon = R.drawable.ic_phonelink_ring_white_24dp;

        if (selectedAudioDevice instanceof AudioDevice.BluetoothHeadset) {
            audioDeviceMenuIcon = R.drawable.ic_bluetooth_white_24dp;
        } else if (selectedAudioDevice instanceof AudioDevice.WiredHeadset) {
            audioDeviceMenuIcon = R.drawable.ic_headset_mic_white_24dp;
        } else if (selectedAudioDevice instanceof AudioDevice.Earpiece) {
            audioDeviceMenuIcon = R.drawable.ic_phonelink_ring_white_24dp;
        } else if (selectedAudioDevice instanceof AudioDevice.Speakerphone) {
            audioDeviceMenuIcon = R.drawable.ic_volume_up_white_24dp;
        }

        ivSpeaker.setImageResource(audioDeviceMenuIcon);
    }

}



