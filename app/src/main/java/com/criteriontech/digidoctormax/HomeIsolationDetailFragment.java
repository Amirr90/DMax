package com.criteriontech.digidoctormax;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.databinding.FragmentHomeIsolationDetailBinding;
import com.criteriontech.digidoctormax.databinding.VitalsViewBinding;
import com.criteriontech.digidoctormax.model.HomeIsolationReqModel;
import com.criteriontech.digidoctormax.model.IsolationVitalModel;
import com.criteriontech.digidoctormax.model.LoginValue;
import com.criteriontech.digidoctormax.model.UpdateIsolationModel;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeIsolationDetailFragment extends Fragment {
    private static final String TAG = "HomeIsolationDetailFrag";


    FragmentHomeIsolationDetailBinding binding;
    NavController navController;

    String id;
    HomeIsolationReqModel homeIsolationReqModel;
    int color;
    public static final String Approved = "Approved";
    public static final String Declined = "Declined";
    VitalsAdapter vitalsAdapter;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeIsolationDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        if (null == getArguments()) {
            Toast.makeText(requireActivity(), "invalid request id", Toast.LENGTH_SHORT).show();
            return;
        }
        id = HomeIsolationDetailFragmentArgs.fromBundle(getArguments()).getId();


        getIsolationData();

        binding.btnApproved.setOnClickListener(v -> showRemarkDialog(Approved));
        binding.btnReject.setOnClickListener(v -> showRemarkDialog(Declined));
    }

    private void showRemarkDialog(String approved) {
        // get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View commentView = inflater.inflate(R.layout.comment,
                null, false);
        EditText editText = (EditText) commentView
                .findViewById(R.id.remark);

        new AlertDialog.Builder(requireActivity())
                .setTitle(approved)
                .setMessage("Add Remark(if any)")
                .setView(commentView)
                .setPositiveButton("Proceed", (dialog, which) -> {
                    String remark = editText.getText().toString();
                    if (TextUtils.isEmpty(remark)) {
                        Toast.makeText(requireActivity(), "Add Remark !!", Toast.LENGTH_SHORT).show();
                    } else
                        updateStatus(approved, remark);
                })
                .setNeutralButton("Proceed,without remark", (dialog, which) -> updateStatus(approved, "")).show();
    }

    private void updateStatus(String status, String remark) {
        UpdateIsolationModel isolationModel = new UpdateIsolationModel();
        isolationModel.setId(Integer.valueOf(id));
        if (null != remark)
            isolationModel.setRemark(remark);
        isolationModel.setStatus(status);
        isolationModel.setServiceProviderDetailsId(SharedPrefManager.getInstance(requireActivity()).getUser().getId());


        AppUtils.showRequestDialog(requireActivity());
        ApiUtils.approveDeclineRequest(isolationModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), (String) obj, Toast.LENGTH_SHORT).show();
                navController.navigateUp();
            }

            @Override
            public void onFailure(String msg) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIsolationData() {

        AppUtils.showRequestDialog(requireActivity());
        LoginValue loginValue = new LoginValue();
        loginValue.setId(Integer.valueOf(id));
        ApiUtils.isolationData(loginValue, new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                AppUtils.hideDialog();
                List<HomeIsolationReqModel> homeIsolationReqModels = (List<HomeIsolationReqModel>) obj;
                if (null != homeIsolationReqModels && homeIsolationReqModels.size() > 0) {
                    homeIsolationReqModel = homeIsolationReqModels.get(0);
                    binding.setIsolation(homeIsolationReqModel);
                    updateRequestStatus(homeIsolationReqModel);


                    if (null != homeIsolationReqModels.get(0).getVitalDetails()) {
                        try {
                            JSONArray jsonarray = new JSONArray(homeIsolationReqModels.get(0).getVitalDetails());
                            List<IsolationVitalModel> isolationVitalModels = new ArrayList<>();
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String name = jsonobject.getString("vitalName");
                                String value = jsonobject.getString("vitalValue");
                                Log.d(TAG, "onSuccess: name" + name + " value " + value);
                                isolationVitalModels.add(new IsolationVitalModel(name, value));
                            }
                            binding.recVital.setAdapter(new VitalsAdapter(isolationVitalModels));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "error: " + e.getLocalizedMessage());
                        }
                    } else {
                        Log.d(TAG, "onSuccess: no vital value");
                    }


                }

            }

            @Override
            public void onFailure(String msg) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    private void updateRequestStatus(HomeIsolationReqModel tuitionModel) {

        if (tuitionModel.getHomeIsolationStatus().equalsIgnoreCase("Pending")) {
            binding.btLoading.setAnimation(R.raw.waiting);
            color = getResources().getColor(R.color.yellow700);
        } else if (tuitionModel.getHomeIsolationStatus().equalsIgnoreCase("Approved")) {
            binding.btLoading.setAnimation(R.raw.accepted);
            color = getResources().getColor(R.color.green700);
        } else if (tuitionModel.getHomeIsolationStatus().equalsIgnoreCase("Declined")) {
            binding.btLoading.setAnimation(R.raw.rejected);
            color = getResources().getColor(R.color.red);
        }

        binding.tvStatus.setTextColor(color);
        binding.btLoading.playAnimation();

    }

    private class VitalsAdapter extends RecyclerView.Adapter<VitalsAdapter.VitalVH> {

        public VitalsAdapter(List<IsolationVitalModel> models) {
            this.models = models;
        }

        List<IsolationVitalModel> models;

        @NonNull
        @Override
        public VitalVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            VitalsViewBinding viewBinding = VitalsViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new VitalVH(viewBinding);

        }

        @Override
        public void onBindViewHolder(@NonNull VitalVH holder, int position) {
            holder.viewBinding.setVitals(models.get(position));
        }

        @Override
        public int getItemCount() {
            return null == models ? 0 : models.size();
        }

        public class VitalVH extends RecyclerView.ViewHolder {
            VitalsViewBinding viewBinding;

            public VitalVH(@NonNull VitalsViewBinding viewBinding) {
                super(viewBinding.getRoot());
                this.viewBinding = viewBinding;
            }
        }
    }
}