package com.criteriontech.digidoctormax.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.InnerviewPatientPrescriptionDtlBinding;
import com.criteriontech.digidoctormax.model.PatientMedicineDetail;

import java.util.List;

public class MedicineDetailsAdapter extends RecyclerView.Adapter<MedicineDetailsAdapter.ViewHolder> {

    private List<PatientMedicineDetail> getpres;
    InnerviewPatientPrescriptionDtlBinding innerviewPatientPrescriptionDtlBinding;

    public MedicineDetailsAdapter(List<PatientMedicineDetail> getpres) {
        this.getpres = getpres;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        innerviewPatientPrescriptionDtlBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.innerview_patient_prescription_dtl, parent, false);
        return new ViewHolder(innerviewPatientPrescriptionDtlBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PatientMedicineDetail medicineDetailsModel = getpres.get(position);
        holder.innerviewPatientPrescriptionDtlBinding.setMedicine(medicineDetailsModel);
        holder.innerviewPatientPrescriptionDtlBinding.tvMedicineNam.setText(medicineDetailsModel.getMedicineName());
        holder.innerviewPatientPrescriptionDtlBinding.tvMedDtl.setText(medicineDetailsModel.getDosageFormName());
        holder.innerviewPatientPrescriptionDtlBinding.tvFrequency.setText(medicineDetailsModel.getFrequencyName());
        holder.innerviewPatientPrescriptionDtlBinding.textView41.setText(medicineDetailsModel.getDurationInDays());


    }

    @Override
    public int getItemCount() {
        return getpres.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        InnerviewPatientPrescriptionDtlBinding innerviewPatientPrescriptionDtlBinding;

        public ViewHolder(@NonNull InnerviewPatientPrescriptionDtlBinding itemView) {
            super(itemView.getRoot());

            innerviewPatientPrescriptionDtlBinding = itemView;
        }
    }
}
