package com.criteriontech.digidoctormax;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.criteriontech.digidoctormax.adapters.QuarantinePatientListAdapter;
import com.criteriontech.digidoctormax.databinding.FragmentQuarantinePatientListBinding;
import com.criteriontech.digidoctormax.databinding.InnerviewPatientListBinding;
import com.criteriontech.digidoctormax.databinding.QuarantinePatientListBinding;
import com.criteriontech.digidoctormax.model.QuarantineModel;
import com.criteriontech.digidoctormax.utils.AdapterInterface;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.criteriontech.digidoctormax.utils.AppUtils.getJSONFromModel;

public class QuarantinePatientListFragment extends Fragment {

    FragmentQuarantinePatientListBinding binding;
    NavController navController;
    private static final String TAG = "QuarantinePatientListFr";
    AdapterInterface adapterInterface;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuarantinePatientListBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        loadData();

    }

    private void loadData() {
        adapterInterface = o -> {
            QuarantineModel quarantineModel = (QuarantineModel) o;
            Bundle bundle = new Bundle();
            bundle.putInt("memberId", quarantineModel.getMemberId());

            bundle.putString("quarantineModel", getJSONFromModel(quarantineModel));
            if (quarantineModel.getIsPrescribed() == 0) {
                bundle.putInt("appointmentId", 1122);
                navController.navigate(R.id.action_quarantinePatientListFragment_to_writePrescriptionForQuarantineFragment, bundle);

            } else {
                bundle.putInt("appointmentId", quarantineModel.getAppointmentId());
                navController.navigate(R.id.action_quarantinePatientListFragment_to_patientDetailsActivity, bundle);
            }
        };
        AppUtils.showRequestDialog(requireActivity());
        int docId = SharedPrefManager.getInstance(requireActivity()).getUser().getId();
        ApiUtils.getQuarantinePatientList(docId, new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                AppUtils.hideDialog();
                binding.recQuarantineList.setAdapter(new QuarantinePatientListAdapter((List<QuarantineModel>) obj, adapterInterface));
            }

            @Override
            public void onFailure(String msg) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }


}