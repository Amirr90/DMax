package com.criteriontech.digidoctormax.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.databinding.PrescriptionViewBinding;
import com.criteriontech.digidoctormax.model.GetPatientMedicationMainModel;
import com.criteriontech.digidoctormax.utils.AdapterInterface;

public class PrescriptionAdapter extends ListAdapter<GetPatientMedicationMainModel, PrescriptionAdapter.PrescriptionVH> {
    AdapterInterface adapterInterface;

    public PrescriptionAdapter(AdapterInterface adapterInterface) {
        super(GetPatientMedicationMainModel.itemCallback);
        this.adapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public PrescriptionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PrescriptionViewBinding prescriptionViewBinding = PrescriptionViewBinding.inflate(inflater, parent, false);

        prescriptionViewBinding.setAdapterInterface(adapterInterface);
        return new PrescriptionVH(prescriptionViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionVH holder, int position) {

        GetPatientMedicationMainModel mainModel = getItem(position);
        holder.prescriptionViewBinding.setGetPatientMedicationMainModel(mainModel);

    }

    public class PrescriptionVH extends RecyclerView.ViewHolder {
        PrescriptionViewBinding prescriptionViewBinding;

        public PrescriptionVH(PrescriptionViewBinding prescriptionViewBinding) {
            super(prescriptionViewBinding.getRoot());
            this.prescriptionViewBinding = prescriptionViewBinding;
        }
    }
}
