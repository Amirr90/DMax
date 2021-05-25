package com.criteriontech.digidoctormax;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.databinding.FragmentInvestigationDetailBinding;
import com.criteriontech.digidoctormax.databinding.IncestigationDetailViewBinding;
import com.criteriontech.digidoctormax.model.InvestigationModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class InvestigationDetailFragment extends Fragment {

    private static final String TAG = "InvestigationDetailFrag";

    List<InvestigationModel.Investigation> models;
    FragmentInvestigationDetailBinding investigationBinding;
    NavController navController;
    InvestigationModel investigationModel;
    InvestigationDetailsAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        investigationBinding = FragmentInvestigationDetailBinding.inflate(getLayoutInflater());
        return investigationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        models = new ArrayList<>();
        adapter = new InvestigationDetailsAdapter(models);
        investigationBinding.recInvestigationView.setAdapter(adapter);
        if (getArguments() != null) {

            Gson gson = new Gson();
            investigationModel = gson.fromJson(getArguments().getString("docModel"), InvestigationModel.class);

            Log.d(TAG, "onViewCreatedinvestigationModel: " + investigationModel.getInvestigation().toString());

            investigationBinding.setInvestigationModel(investigationModel);

            models.clear();

            models.addAll(investigationModel.getInvestigation());
            adapter.notifyDataSetChanged();

            if (null != investigationModel.getInvestigation() && !investigationModel.getInvestigation().isEmpty())
                investigationBinding.textView128.setText(investigationModel.getInvestigation().get(0).getTestName());


        } else {
        }//PatientDashboard.getInstance().onSupportNavigateUp();

    }

    private class InvestigationDetailsAdapter extends RecyclerView.Adapter<InvestigationDetailsAdapter.InvestigationVH> {
        List<InvestigationModel.Investigation> models;

        public InvestigationDetailsAdapter(List<InvestigationModel.Investigation> models) {
            this.models = models;
        }

        @NonNull
        @Override
        public InvestigationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            IncestigationDetailViewBinding binding = IncestigationDetailViewBinding.inflate(inflater, parent, false);
            return new InvestigationVH(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull InvestigationVH holder, int position) {

            InvestigationModel.Investigation.TestDetailsModel testDetails = models.get(position).getTestDetails().get(0);

            holder.binding.setDetailModel(testDetails);


            Log.d(TAG, "onBindViewHolder: " + testDetails.toString());
        }

        @Override
        public int getItemCount() {
            return models.size();
        }

        public class InvestigationVH extends RecyclerView.ViewHolder {
            IncestigationDetailViewBinding binding;

            public InvestigationVH(@NonNull IncestigationDetailViewBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

            }
        }
    }
}