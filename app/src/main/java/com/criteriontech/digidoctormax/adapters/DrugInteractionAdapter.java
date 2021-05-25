package com.criteriontech.digidoctormax.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.databinding.DrugInteractionLayoutBinding;
import com.criteriontech.digidoctormax.databinding.DrugInteractionViewBinding;
import com.criteriontech.digidoctormax.databinding.InnerviewPatientOtherConsultationBinding;
import com.criteriontech.digidoctormax.model.DrugInteractionModel;
import com.criteriontech.digidoctormax.model.DrugIntrcationModel;
import com.criteriontech.digidoctormax.model.PatientVitalDetail;

import java.util.List;

public class DrugInteractionAdapter extends RecyclerView.Adapter<DrugInteractionAdapter.ViewHolderVH> {

    private List<DrugIntrcationModel> drugIntrcationModels;
    private Activity ctx;

    DrugInteractionViewBinding drugInteractionLayoutBinding;

    public DrugInteractionAdapter(List<DrugIntrcationModel> drugIntrcationModels, Activity ctx) {
        this.drugIntrcationModels = drugIntrcationModels;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public DrugInteractionAdapter.ViewHolderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        drugInteractionLayoutBinding = DrugInteractionViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DrugInteractionAdapter.ViewHolderVH(drugInteractionLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull DrugInteractionAdapter.ViewHolderVH holder, int position) {

        DrugIntrcationModel drugIntrcationModel = drugIntrcationModels.get(position);

        holder.drugInteractionViewBinding.textView54.setText(drugIntrcationModel.getInteraction());
        holder.drugInteractionViewBinding.textView84.setText(drugIntrcationModel.getInteractionEffect());

    }

    @Override
    public int getItemCount() {
        return drugIntrcationModels.size();
    }

    public class ViewHolderVH extends RecyclerView.ViewHolder {

        DrugInteractionViewBinding drugInteractionViewBinding;

        public ViewHolderVH(@NonNull DrugInteractionViewBinding itemView) {
            super(itemView.getRoot());

            this.drugInteractionViewBinding = itemView;
        }
    }
}
