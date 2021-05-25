package com.criteriontech.digidoctormax;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.criteriontech.digidoctormax.adapters.InvestigationAdapter;
import com.criteriontech.digidoctormax.databinding.FragmentInvestigationsBinding;
import com.criteriontech.digidoctormax.interfaces.OnClickListener;
import com.criteriontech.digidoctormax.model.InvestigationModel;
import com.criteriontech.digidoctormax.model.User;
import com.criteriontech.digidoctormax.viewHolder.PatientViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.criteriontech.digidoctormax.utils.AppUtils.getJSONFromModel;


public class InvestigationsFragment extends Fragment implements OnClickListener {

    FragmentInvestigationsBinding investigationBinding;
    NavController navController;

    PatientViewModel viewModel;

    InvestigationAdapter adapter;

    List<InvestigationModel> submitList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        investigationBinding = FragmentInvestigationsBinding.inflate(getLayoutInflater());
        return investigationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        submitList = new ArrayList<>();
        adapter = new InvestigationAdapter(this, submitList);

        investigationBinding.recInvestigation.setAdapter(adapter);

        // AppUtils.showRequestDialog(requireActivity());
        User user = new User();
        user.setMemberId(getArguments().getInt("id"));
        viewModel.getInvestigationData(user).observe(getViewLifecycleOwner(), investigationModels -> {

            submitList.clear();
            submitList.addAll(investigationModels);
            adapter.notifyDataSetChanged();
        });


    }

    @Override
    public void onItemClick(Object object) {

        InvestigationModel investigationModel = (InvestigationModel) object;
        String filePath = investigationModel.getFilePath();
        if (null == filePath || filePath.isEmpty()) {
            if (!investigationModel.getInvestigation().isEmpty()) {
                Bundle bundle = new Bundle();
                bundle.putString("docModel", getJSONFromModel(investigationModel));
                Log.d("TAG", "onItemClickData: " + investigationModel.getInvestigation().get(0).getTestDetails());
                navController.navigate(R.id.action_investigationsFragment_to_investigationDetailFragment, bundle);
            }
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("filePath", filePath);
            Log.d("TAG", "onItemClickData FilePath: " + filePath);
            navController.navigate(R.id.action_investigationsFragment_to_showImageFileFragment, bundle);

        }

    }
}