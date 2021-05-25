package com.criteriontech.digidoctormax;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.criteriontech.digidoctormax.adapters.PrescriptionAdapter;
import com.criteriontech.digidoctormax.databinding.FragmentPrescriptionsBinding;
import com.criteriontech.digidoctormax.model.GetPatientMedicationMainModel;
import com.criteriontech.digidoctormax.model.User;
import com.criteriontech.digidoctormax.utils.AdapterInterface;
import com.criteriontech.digidoctormax.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.criteriontech.digidoctormax.utils.AppUtils.fadeIn;
import static com.criteriontech.digidoctormax.utils.AppUtils.getJSONFromModel;


public class PrescriptionsFragment extends Fragment implements AdapterInterface {


    FragmentPrescriptionsBinding prescriptionHistoryBinding;
    NavController navController;
    PrescriptionAdapter prescriptionAdapter;

    PatientViewModel viewModel;

    User user;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prescriptionHistoryBinding = FragmentPrescriptionsBinding.inflate(getLayoutInflater());
        return prescriptionHistoryBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prescriptionHistoryBinding.getRoot().setAnimation(fadeIn(requireActivity()));
        navController = Navigation.findNavController(view);

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);


        prescriptionAdapter = new PrescriptionAdapter(this);


        prescriptionHistoryBinding.prescriptionRec.setAdapter(prescriptionAdapter);
        viewModel.getPrescriptionData(String.valueOf(getArguments().getInt("id")), requireActivity()).observe(getViewLifecycleOwner(), getPatientMedicationMainModels -> {
            if (getPatientMedicationMainModels != null) {
                prescriptionAdapter.submitList(getPatientMedicationMainModels);
            }else Toast.makeText(requireActivity(), "No data found!", Toast.LENGTH_SHORT).show();
        });

       // prescriptionHistoryBinding.tvAddManually.setOnClickListener(view1 -> navController.navigate(R.id.action_prescriptionHistoryFragment_to_addPrescriptionManuallyFragment));


    }

    @Override
    public void onItemClicked(Object o) {

        try {
            GetPatientMedicationMainModel getPatientMedicationMainModels = (GetPatientMedicationMainModel) o;
            Bundle bundle = new Bundle();
            bundle.putString("presModel", getJSONFromModel(getPatientMedicationMainModels));
            Log.d("TAG", "onItemClickedString: " + getJSONFromModel(getPatientMedicationMainModels));
            navController.navigate(R.id.action_prescriptionsFragment_to_visitFragment, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}