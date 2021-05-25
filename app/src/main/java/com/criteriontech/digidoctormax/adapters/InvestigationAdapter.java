package com.criteriontech.digidoctormax.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.databinding.InvestigationViewBinding;
import com.criteriontech.digidoctormax.databinding.InvestigationViewTwoBinding;
import com.criteriontech.digidoctormax.interfaces.OnClickListener;
import com.criteriontech.digidoctormax.model.InvestigationModel;

import java.util.List;

public class InvestigationAdapter extends RecyclerView.Adapter {

    OnClickListener onClickListener;
    List<InvestigationModel> investigationModels;

    public InvestigationAdapter(OnClickListener onClickListener, List<InvestigationModel> investigationModels) {
        this.onClickListener = onClickListener;
        this.investigationModels = investigationModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            InvestigationViewBinding binding = InvestigationViewBinding.inflate(inflater, parent, false);
            binding.setClickListener(onClickListener);
            return new InvestigationVHOne(binding);
        } else {
            InvestigationViewTwoBinding binding = InvestigationViewTwoBinding.inflate(inflater, parent, false);
            binding.setClickListener(onClickListener);
            return new InvestigationVHTwo(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        InvestigationModel investigationModel = investigationModels.get(position);
        if (null == investigationModel.getFilePath() || investigationModel.getFilePath().isEmpty()) {
            InvestigationVHOne investigationVHOne = (InvestigationVHOne) holder;
            investigationVHOne.binding.setInvestigationModel(investigationModel);
            if (!investigationModel.getInvestigation().isEmpty()) {
                String testName = investigationModel.getInvestigation().get(0).getTestName();
                if (investigationModel.getInvestigation().size() > 1) {
                    investigationVHOne.binding.textView96.setText(testName + "   (+" + (investigationModel.getInvestigation().size() - 1) + ")");
                } else
                    investigationVHOne.binding.textView96.setText(testName);
            }
        } else {
            InvestigationVHTwo investigationVHTwo = (InvestigationVHTwo) holder;
            investigationVHTwo.binding.setInvestigationModel(investigationModel);
            if (!investigationModel.getInvestigation().isEmpty()) {
                String testName = investigationModel.getInvestigation().get(0).getTestName();
                if (investigationModel.getInvestigation().size() > 1) {
                    investigationVHTwo.binding.textView96.setText(testName + "   (+" + (investigationModel.getInvestigation().size() - 1) + ")");
                } else
                    investigationVHTwo.binding.textView96.setText(testName);
            }
        }
    }

    @Override
    public int getItemCount() {
        return investigationModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        InvestigationModel model = investigationModels.get(position);
        if (null == model.getFilePath() || model.getFilePath().isEmpty())
            return 0;
        else return 1;
    }

    public class InvestigationVHOne extends RecyclerView.ViewHolder {

        InvestigationViewBinding binding;

        public InvestigationVHOne(@NonNull InvestigationViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class InvestigationVHTwo extends RecyclerView.ViewHolder {

        InvestigationViewTwoBinding binding;

        public InvestigationVHTwo(@NonNull InvestigationViewTwoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
