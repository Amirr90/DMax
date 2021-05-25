package com.criteriontech.digidoctormax.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.InnerviewPatientListBinding;
import com.criteriontech.digidoctormax.databinding.QuarantinePatientListBinding;
import com.criteriontech.digidoctormax.model.QuarantineModel;
import com.criteriontech.digidoctormax.model.QuarantinePatientListRes;
import com.criteriontech.digidoctormax.utils.AdapterInterface;

import java.util.List;


public class QuarantinePatientListAdapter extends RecyclerView.Adapter<QuarantinePatientListAdapter.QuarantineVH> {

    private static final String TAG = "QuarantinePatientListAd";
    List<QuarantineModel> listRes;
    AdapterInterface adapterInterface;

    public QuarantinePatientListAdapter(List<QuarantineModel> listRes, AdapterInterface adapterInterface) {
        this.listRes = listRes;
        this.adapterInterface = adapterInterface;
    }


    @NonNull
    @Override
    public QuarantinePatientListAdapter.QuarantineVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        QuarantinePatientListBinding binding = QuarantinePatientListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.setListener(adapterInterface);
        return new QuarantineVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuarantinePatientListAdapter.QuarantineVH holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        holder.binding.setPatient(listRes.get(position));
        QuarantineModel quarantineModel = listRes.get(position);

        if (quarantineModel.getIsPrescribed() == 1) {
            holder.binding.clMain.setBackgroundResource(R.drawable.patient_list_prescribed);
        } else holder.binding.clMain.setBackgroundResource(R.drawable.patient_list);

    }

    @Override
    public int getItemCount() {
        return listRes == null ? 0 : listRes.size();
    }

    public static class QuarantineVH extends RecyclerView.ViewHolder {
        QuarantinePatientListBinding binding;

        public QuarantineVH(@NonNull QuarantinePatientListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
