package com.criteriontech.digidoctormax.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.ActivityPatientListBinding;
import com.criteriontech.digidoctormax.databinding.InnerviewPatientListBinding;
import com.criteriontech.digidoctormax.drViewModel.DrViewModel;
import com.criteriontech.digidoctormax.model.NoOfPatientValue;
import com.criteriontech.digidoctormax.request.ClinicDashboardDetails;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import java.util.Objects;

import static com.criteriontech.digidoctormax.model.NoOfPatientValue.itemCallback;
import static com.criteriontech.digidoctormax.utils.AppUtils.KEY_APPOINTMENT_ID;

public class PatientListActivity extends Fragment {
    ActivityPatientListBinding binding;
    NavController navController;
    PatientListAdp patientListAdp;
    DrViewModel drViewModel;
    ClinicDashboardDetails clinicDashboardDetails;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityPatientListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        patientListAdp = new PatientListAdp();
        bundle = new Bundle();
        clinicDashboardDetails = new ClinicDashboardDetails();
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireActivity()));
        drViewModel = new ViewModelProvider(requireActivity()).get(DrViewModel.class);
        binding.recyclerview.setAdapter(patientListAdp);
        binding.imageView11.setOnClickListener(view1 -> navController.navigateUp());

        clinicDashboardDetails.setId(SharedPrefManager.getInstance(requireActivity()).getUser().getId());


        clinicDashboardDetails.setType(getArguments().getString("type"));
        clinicDashboardDetails.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
        try {
            drViewModel.drDashboardNumOfPatients(clinicDashboardDetails, requireActivity()).observe(getViewLifecycleOwner(),
                    dNoOfPatientValues -> {
                        try {
                            patientListAdp.submitList(dNoOfPatientValues);
                            binding.recyclerview.post(() -> patientListAdp.notifyDataSetChanged());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class PatientListAdp extends ListAdapter<NoOfPatientValue, PatientListAdp.ViewHolder> {
        protected PatientListAdp() {
            super(itemCallback);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            InnerviewPatientListBinding innerviewPatientListBinding = InnerviewPatientListBinding.inflate(layoutInflater, parent, false);
            return new ViewHolder(innerviewPatientListBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            try {
                NoOfPatientValue noOfPatientValue = getItem(position);
                if (getArguments().getString("type").equals("todaysAppointments")) {
                    holder.innerviewPatientListBinding.view.setVisibility(View.VISIBLE);
                    holder.innerviewPatientListBinding.tvTime.setVisibility(View.VISIBLE);
                    holder.innerviewPatientListBinding.chatIV.setVisibility(View.VISIBLE);
                    if (noOfPatientValue.getPrescribed())
                        holder.innerviewPatientListBinding.clMain.setBackground(requireActivity().getDrawable(R.drawable.list_selected));
                } else {
                    holder.innerviewPatientListBinding.view.setVisibility(View.GONE);
                    holder.innerviewPatientListBinding.tvTime.setVisibility(View.VISIBLE);
                }

                holder.innerviewPatientListBinding.imageView13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AppUtils.showImagePreview(requireActivity(), noOfPatientValue.getProfilePhotoPath());
                    }
                });


                holder.innerviewPatientListBinding.chatIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle bundle = new Bundle();
                        bundle.putString(KEY_APPOINTMENT_ID, String.valueOf(noOfPatientValue.getAppointmentId()));
                        bundle.putInt("memberId", noOfPatientValue.getMemberId());
//                        bundle.putString("docId", String.valueOf(noOfPatientValue.getDoctorId()));
//                        bundle.putString("docName", String.valueOf(noOfPatientValue.getName()));
//                        bundle.putString("firstAppointmentId", String.valueOf(noOfPatientValue.getFirstAppointmentId()));

                        navController.navigate(R.id.action_patientListActivity_to_chatFragment, bundle);
                    }
                });
                holder.innerviewPatientListBinding.ivReschedule.setVisibility(getArguments().getString("type").equals("pendingPatients") || getArguments().getString("type").equals("todaysAppointments") ? View.VISIBLE : View.GONE);
                holder.innerviewPatientListBinding.chatIV.setVisibility(getArguments().getString("type").equals("pendingPatients") || getArguments().getString("type").equals("todaysAppointments") ? View.VISIBLE : View.GONE);
                holder.innerviewPatientListBinding.setPatient(noOfPatientValue);
                holder.innerviewPatientListBinding.tvPhone.setText(noOfPatientValue.getDetail());
                holder.innerviewPatientListBinding.clMain.setOnClickListener(view -> {
                    try {
                        if (!getArguments().getString("type").equals("totalCollectedFees")) {
                            if (getArguments().getString("type").equals("todaysAppointments") || getArguments().getString("type").equals("pendingPatients")) {
                                bundle.putInt("appointmentId", noOfPatientValue.getAppointmentId());
                                bundle.putInt("memberId", noOfPatientValue.getMemberId());
                                bundle.putString("phone", noOfPatientValue.getUserMobileNo());
                                bundle.putString("type", getArguments().getString("type"));
                                if (noOfPatientValue.getPrescribed())
                                    navController.navigate(R.id.action_patientListActivity_to_patientDetailsActivity, bundle);
                                else
                                    navController.navigate(R.id.action_patientListActivity_to_writePrescriptionFragment, bundle);
                            } else {

                                bundle.putInt("appointmentId", noOfPatientValue.getAppointmentId());
                                bundle.putInt("memberId", noOfPatientValue.getMemberId());
                                bundle.putString("type", getArguments().getString("type"));
                                navController.navigate(R.id.action_patientListActivity_to_patientDetailsActivity, bundle);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                holder.innerviewPatientListBinding.ivReschedule.setOnClickListener(view -> {

                    Bundle bundle = new Bundle();
                    bundle.putString("Pname", noOfPatientValue.getName());
                    bundle.putString("AppointmentDT", noOfPatientValue.appointmentTime + " " + noOfPatientValue.getAppointmentDate());
                    bundle.putInt("appointmentId", noOfPatientValue.getAppointmentId());
                    bundle.putInt("memberId", noOfPatientValue.getMemberId());
                    navController.navigate(R.id.rescheduleFragment, bundle);
                });
            } catch (
                    Exception ex) {
                ex.printStackTrace();
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            InnerviewPatientListBinding innerviewPatientListBinding;

            public ViewHolder(InnerviewPatientListBinding innerviewPatientListBinding) {
                super(innerviewPatientListBinding.getRoot());
                this.innerviewPatientListBinding = innerviewPatientListBinding;
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();

    }

}