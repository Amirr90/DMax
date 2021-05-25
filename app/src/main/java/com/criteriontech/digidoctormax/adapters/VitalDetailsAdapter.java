package com.criteriontech.digidoctormax.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.databinding.InnerviewPatientOtherConsultationBinding;
import com.criteriontech.digidoctormax.model.PatientVitalDetail;

import java.util.List;

public class VitalDetailsAdapter extends RecyclerView.Adapter<VitalDetailsAdapter.VitalDetailsVH> {

    private List<PatientVitalDetail> getvital;
    private Activity ctx;
    InnerviewPatientOtherConsultationBinding innerviewPatientOtherConsultationBinding;

    public VitalDetailsAdapter(List<PatientVitalDetail> getvital, Activity ctx) {
        this.ctx = ctx;
        this.getvital = getvital;
    }

    @NonNull
    @Override
    public VitalDetailsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        innerviewPatientOtherConsultationBinding = InnerviewPatientOtherConsultationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VitalDetailsVH(innerviewPatientOtherConsultationBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull VitalDetailsVH holder, int position) {
        PatientVitalDetail patientVitalDetail = getvital.get(position);
        holder.innerviewPatientOtherConsultationBinding.setVital(patientVitalDetail);
//        holder.innerviewPatientOtherConsultationBinding.tvName.setText(patientVitalDetail.getVitalName());
//        holder.innerviewPatientOtherConsultationBinding.textView17.setText(patientVitalDetail.getVitalTime());
//        holder.innerviewPatientOtherConsultationBinding.tvDate.setText(patientVitalDetail.getVitalValue());
        Log.v("vitalNamee", patientVitalDetail.getVitalName());
        Log.v("vitalNamee", patientVitalDetail.getVitalTime());
        Log.v("vitalNamee", patientVitalDetail.getVitalValue());
    }

    @Override
    public int getItemCount() {
        return getvital.size();
    }

    public class VitalDetailsVH extends RecyclerView.ViewHolder {

        InnerviewPatientOtherConsultationBinding innerviewPatientOtherConsultationBinding;

        public VitalDetailsVH(@NonNull InnerviewPatientOtherConsultationBinding innerviewPatientOtherConsultationBinding) {
            super(innerviewPatientOtherConsultationBinding.getRoot());
            this.innerviewPatientOtherConsultationBinding = innerviewPatientOtherConsultationBinding;
        }
    }
}
