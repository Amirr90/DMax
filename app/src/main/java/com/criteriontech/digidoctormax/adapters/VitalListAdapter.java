package com.criteriontech.digidoctormax.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.databinding.VitalViewBinding;
import com.criteriontech.digidoctormax.model.VitalResponse;

import java.util.List;

public class VitalListAdapter extends ListAdapter<VitalResponse.VitalDateVise, VitalListAdapter.VitalVH> {
    public VitalListAdapter() {
        super(VitalResponse.VitalDateVise.itemCallback);
    }

    @NonNull
    @Override
    public VitalVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        VitalViewBinding vitalViewBinding = VitalViewBinding.inflate(inflater, parent, false);
        return new VitalVH(vitalViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VitalVH holder, int position) {

        try {
            VitalResponse.VitalDateVise dateVise = getItem(position);
            List<VitalResponse.VitalDateVise.VitalValueModel> vitalValueModel = dateVise.getVitalDetails();

            StringBuilder vitalValue = new StringBuilder();

            if (vitalValueModel.size() > 1) {
                vitalValue.append(vitalValueModel.get(0).getVitalValue());
                vitalValue.append("/" + vitalValueModel.get(1).getVitalValue());
            } else {
                vitalValue.append(vitalValueModel.get(0).getVitalValue());
            }

            holder.vitalViewBinding.setVital(vitalValue.toString());


            holder.vitalViewBinding.setVitalDate(dateVise);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class VitalVH extends RecyclerView.ViewHolder {
        VitalViewBinding vitalViewBinding;

        public VitalVH(@NonNull VitalViewBinding vitalViewBinding) {
            super(vitalViewBinding.getRoot());
            this.vitalViewBinding = vitalViewBinding;
        }
    }
}
