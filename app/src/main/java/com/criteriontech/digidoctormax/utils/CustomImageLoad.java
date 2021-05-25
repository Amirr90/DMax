package com.criteriontech.digidoctormax.utils;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.activity.StartActivity;
import com.criteriontech.digidoctormax.fragment.home.DrHomeActivity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CustomImageLoad {
    private static final String TAG = "CustomImageLoad";

    @BindingAdapter("android:loadCustomPrescriptionImage")
    public static void loadPrescriptionImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(DrHomeActivity
                        .getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.diagnosis_demo_image)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadPrescriptionImage: " + e.getLocalizedMessage());
            }
        } else imageView.setImageResource(R.drawable.diagnosis_demo_image);

    }

    @BindingAdapter("android:setCustomVisibility")
    public static void setCustomVisibility(View view, String text) {
        if (null == text || text.isEmpty())
            view.setVisibility(GONE);
        else view.setVisibility(VISIBLE);
    }

    @BindingAdapter("android:setCustomVisibility")
    public static void setCustomVisibility(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("android:loadHealthProductImage")
    public static void loadImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(StartActivity.getInstance())
                        .load(imagePath)
                        .placeholder(R.drawable.ic_man)
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
                imageView.setImageResource(R.drawable.ic_man);


            }
        } else imageView.setImageResource(R.drawable.ic_man);
    }

    @BindingAdapter("android:loadHealthProductImage")
    public static void loadImage(ImageView imageView, int imagePath) {
        imageView.setImageResource(imagePath);
    }

    @BindingAdapter("android:loadCustomImage")
    public static void loadCustomImage(ImageView imgView, String url) {
        try {
            if (url != null && !url.isEmpty()) {
                Glide.with(DrHomeActivity.getInstance())
                        .load(url)
                        .placeholder(R.drawable.ic_man)
                        .into(imgView);
            } else {
                imgView.setImageResource(R.drawable.ic_man);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d(TAG, "loadCustomImage: " + ex.getLocalizedMessage());
            imgView.setImageResource(R.drawable.ic_man);
        }
    }

    @BindingAdapter("android:loadProblemImage")
    public static void loadProblemImage(ImageView imgView, String url) {
        try {
            if (url != null && !url.isEmpty()) {
                Glide.with(DrHomeActivity.getInstance())
                        .load(url)
                        .placeholder(R.drawable.ic_man)
                        .into(imgView);
            } else imgView.setImageResource(R.drawable.ic_man);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d(TAG, "loadProblemImage: " + ex.getLocalizedMessage());
            imgView.setImageResource(R.drawable.ic_man);
        }
    }

    @BindingAdapter("android:loadDrawable")
    public static void loadProblemImage(ImageView imgView, int url) {
        try {
            Glide.with(StartActivity.getInstance())
                    .load(url)
                    .into(imgView);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
