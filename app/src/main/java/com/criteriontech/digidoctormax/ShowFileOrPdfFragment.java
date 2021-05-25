package com.criteriontech.digidoctormax;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.criteriontech.digidoctormax.databinding.FragmentShowFileOrPdfBinding;
import com.criteriontech.digidoctormax.fragment.home.DrHomeActivity;

import org.jetbrains.annotations.NotNull;


public class ShowFileOrPdfFragment extends Fragment {

    private static final String TAG = "ShowFileOrPdfFragment";

    FragmentShowFileOrPdfBinding showFileOrPdfBinding;

    String filePath;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        showFileOrPdfBinding = FragmentShowFileOrPdfBinding.inflate(getLayoutInflater());
        return showFileOrPdfBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (null == getArguments())
            return;
        filePath = getArguments().getString("filePath");
        Log.d(TAG, "onViewCreated: filePath " + filePath);

        showFileOrPdfBinding.btnClose.setOnClickListener(view12 -> {
            DrHomeActivity.getInstance().onSupportNavigateUp();
        });


        String extension = filePath.substring(filePath.lastIndexOf("."));

        if (extension.equalsIgnoreCase(".png") ||
                extension.equalsIgnoreCase(".jpeg") ||
                extension.equalsIgnoreCase(".jpg")) {

            loadImage();
        } else if (extension.equalsIgnoreCase(".pdf")) {
            showPdf(filePath);
        } else
            DrHomeActivity.getInstance().onSupportNavigateUp();

        showFileOrPdfBinding.btnClose.setOnClickListener(view1 -> DrHomeActivity.getInstance().onSupportNavigateUp());
    }

    private void showPdf(String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(filePath), "application/pdf");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "showPdf: " + e.getLocalizedMessage());
        }
    }

    private void loadImage() {
        try {
            Glide.with(DrHomeActivity.getInstance())
                    .load(filePath)
                    .centerCrop()
                    .into(showFileOrPdfBinding.imageView11);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "loadSpeImage: ");
        }
    }
}