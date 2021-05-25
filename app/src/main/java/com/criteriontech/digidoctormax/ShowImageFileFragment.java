package com.criteriontech.digidoctormax;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.criteriontech.digidoctormax.databinding.FragmentShowImageFileBinding;
import com.criteriontech.digidoctormax.databinding.ImageViewBinding;
import com.criteriontech.digidoctormax.interfaces.OnClickListener;
import com.criteriontech.digidoctormax.model.ImageModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShowImageFileFragment extends Fragment implements OnClickListener {


    private static final String TAG = "ShowImageFileFragment";

    FragmentShowImageFileBinding binding;
    NavController navController;
    String imageList = null;
    ImageModel imageModel;
    ImageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowImageFileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        List<ImageModel> imageLists = new ArrayList<>();
        adapter = new ImageAdapter(imageLists, this);
        if (getArguments() == null)
            return;


        imageList = getArguments().getString("filePath");
        String newStr = imageList.replace("\\", "");


        try {
            JSONArray jsonArray = new JSONArray(newStr);
            Gson gson = new Gson();

            for (int a = 0; a < jsonArray.length(); a++) {
                imageModel = gson.fromJson((jsonArray.get(a).toString()), ImageModel.class);
                Log.d(TAG, "ImageArray: " + imageModel.toString());
                if (!imageModel.getFileType().equalsIgnoreCase("pdf"))
                    imageLists.add(imageModel);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        binding.recImageView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Object object) {

        ImageModel imageModel = (ImageModel) object;
        showImageDialog(imageModel.getFilePath());

    }

    private void showImageDialog(String filePath) {
        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.image_view,
                null, false);
        ImageView imageView = formElementsView.findViewById(R.id.imageView46);
        loadImage(imageView, filePath);
        new AlertDialog.Builder(requireActivity())
                .setView(formElementsView)
                .setPositiveButton(getString(R.string.close), (dialogInterface, i) -> dialogInterface.dismiss()).show();
    }

    private void loadImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(requireActivity())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.diagnosis_demo_image)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadPrescriptionImage: " + e.getLocalizedMessage());
            }
        }


    }

    private class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageVH> {

        List<ImageModel> imageLists;
        OnClickListener clickListener;

        public ImageAdapter(List<ImageModel> imageLists, OnClickListener clickListener) {
            this.imageLists = imageLists;
            this.clickListener = clickListener;
        }


        @NonNull
        @Override
        public ImageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ImageViewBinding imageViewBinding = ImageViewBinding.inflate(inflater, parent, false);
            imageViewBinding.setClickListener(clickListener);
            return new ImageVH(imageViewBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageVH holder, int position) {

            try {
                ImageModel imageModel = imageLists.get(position);
                holder.imageViewBinding.setImage(imageModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return imageLists.size();
        }

        public class ImageVH extends RecyclerView.ViewHolder {
            ImageViewBinding imageViewBinding;

            public ImageVH(@NonNull ImageViewBinding imageViewBinding) {
                super(imageViewBinding.getRoot());
                this.imageViewBinding = imageViewBinding;
            }
        }

    }

}