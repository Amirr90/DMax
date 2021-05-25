package com.criteriontech.digidoctormax.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.FragmentDrHomeBinding;
import com.criteriontech.digidoctormax.model.IncomeList;
import com.criteriontech.digidoctormax.request.ClinicDashboard;
import com.criteriontech.digidoctormax.response.DrDashboardResp;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.highsoft.highcharts.common.hichartsclasses.HIColumn;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HILine;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DrHome extends Fragment {
    FragmentDrHomeBinding binding;
    NavController navController;
    ClinicDashboard clinicDashboard;
    Bundle bundle;
    Date date;
    String sysValue;
    Number[] sysData;
    HIColumn series1;
    HIXAxis xAxis;
    HIOptions options;
    HIPlotOptions plotOptions;
    HIExporting hiExporting;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDrHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        clinicDashboard = new ClinicDashboard();
        bundle = new Bundle();
        date = new Date();
        binding.tvDate.setText(" " + new SimpleDateFormat("MMM dd, yyyy").format(date));
        clinicDashboard.setId(SharedPrefManager.getInstance(requireActivity()).getUser().getId());
        clinicDashboard.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());

        initGraph();

        if (AppUtils.isNetworkConnected(requireActivity())) {
            AppUtils.showRequestDialog(requireActivity());
            ApiUtils.doctorDashboard(clinicDashboard, new ApiCallbackInterface() {
                @Override
                public void onSuccess(Object obj) {
                    binding.setDashboard(((DrDashboardResp) (obj)).getResponseValue().get(0));
                    if (((DrDashboardResp) (obj)).getResponseValue().get(0).getIncomeList().size() > 0)
                        setVitalChartData(((DrDashboardResp) (obj)).getResponseValue().get(0).getIncomeList());
                    AppUtils.hideDialog();


                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show();
                    AppUtils.hideDialog();
                    SharedPrefManager.getInstance(requireActivity()).clear();
                    navController.navigate(R.id.action_drHome_to_chooseLoginType2);
                }
            });
        } else
            Toast.makeText(requireActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
        binding.ivImg.setOnClickListener(view1 -> DrHomeActivity.getInstance().openDrawer());

        binding.tvDoctor.setOnClickListener(view1 -> {
            bundle.putString("type", "noOfpatients");
            if (binding.getDashboard().getNoOfpatients() > 0)
                navController.navigate(R.id.action_drHome_to_patientListActivity, bundle);
            else Toast.makeText(requireActivity(), "No data found!", Toast.LENGTH_SHORT).show();
        });

        binding.imageView8.setOnClickListener(view1 -> {
            bundle.putString("type", "noOfpatients");
            if (binding.getDashboard().getNoOfpatients() > 0)
                navController.navigate(R.id.action_drHome_to_patientListActivity, bundle);
            else Toast.makeText(requireActivity(), "No data found!", Toast.LENGTH_SHORT).show();
        });

        binding.tvPrescribed.setOnClickListener(view1 -> {
            bundle.putString("type", "prescribedPatients");
            if (binding.getDashboard().getPrescribedPatients() > 0)
                navController.navigate(R.id.action_drHome_to_patientListActivity, bundle);
            else Toast.makeText(requireActivity(), "No data found!", Toast.LENGTH_SHORT).show();
        });
        binding.ivArrow4.setOnClickListener(view1 -> {
            bundle.putString("type", "prescribedPatients");
            if (binding.getDashboard().getPrescribedPatients() > 0)
                navController.navigate(R.id.action_drHome_to_patientListActivity, bundle);
            else Toast.makeText(requireActivity(), "No data found!", Toast.LENGTH_SHORT).show();
        });

        binding.tvPending.setOnClickListener(view1 -> {
            bundle.putString("type", "pendingPatients");
            if (binding.getDashboard().getPendingPatients() > 0)
                navController.navigate(R.id.action_drHome_to_patientListActivity, bundle);
            else Toast.makeText(requireActivity(), "No data found!", Toast.LENGTH_SHORT).show();
        });
        binding.ivArrow5.setOnClickListener(view1 -> {
            bundle.putString("type", "pendingPatients");
            if (binding.getDashboard().getPendingPatients() > 0)
                navController.navigate(R.id.action_drHome_to_patientListActivity, bundle);
            else Toast.makeText(requireActivity(), "No data found!", Toast.LENGTH_SHORT).show();
        });


        binding.tvCollectedFee.setOnClickListener(view1 -> {
            bundle.putString("type", "totalCollectedFees");
            if (binding.getDashboard().getTotalCollectedFees() > 0)
                navController.navigate(R.id.action_drHome_to_patientListActivity, bundle);
            else Toast.makeText(requireActivity(), "No data found!", Toast.LENGTH_SHORT).show();
        });
        binding.ivArrow.setOnClickListener(view1 -> {
            bundle.putString("type", "totalCollectedFees");
            if (binding.getDashboard().getTotalCollectedFees() > 0)
                navController.navigate(R.id.action_drHome_to_patientListActivity, bundle);
            else Toast.makeText(requireActivity(), "No data found!", Toast.LENGTH_SHORT).show();
        });
        binding.tvTodayApp.setOnClickListener(view1 -> {
            bundle.putString("type", "todaysAppointments");
            if (binding.getDashboard().getTodaysAppointments() > 0)
                navController.navigate(R.id.action_drHome_to_patientListActivity, bundle);
            else Toast.makeText(requireActivity(), "No data found!", Toast.LENGTH_SHORT).show();
        });
        binding.ivArrow1.setOnClickListener(view1 -> {
            bundle.putString("type", "todaysAppointments");
            if (binding.getDashboard().getTodaysAppointments() > 0)
                navController.navigate(R.id.action_drHome_to_patientListActivity, bundle);
            else Toast.makeText(requireActivity(), "No data found!", Toast.LENGTH_SHORT).show();
        });
    }

    private void initGraph() {
        binding.hcIncome.theme = "sand-signika";
        options = new HIOptions();

        HITitle hiTitle = new HITitle();
        hiTitle.setText("Income");
        options.setTitle(hiTitle);

        final HIYAxis yAxis = new HIYAxis();
        yAxis.setTitle(new HITitle());
        yAxis.getTitle().setText("Income");
        options.setYAxis(new ArrayList<HIYAxis>() {{
            add(yAxis);
        }});

        plotOptions = new HIPlotOptions();
        plotOptions.setLine(new HILine());

        plotOptions.getLine().setEnableMouseTracking(true);
        options.setPlotOptions(plotOptions);

        hiExporting = new HIExporting();
        hiExporting.setEnabled(false);
        options.setExporting(hiExporting);
        binding.hcIncome.setOptions(options);
    }

    private void setVitalChartData(List<IncomeList> vitalResponse) {
        xAxis = new HIXAxis();
        String[] categoriesList = new String[vitalResponse.size()];
        for (int a = 0; a < vitalResponse.size(); a++) {
            categoriesList[a] = vitalResponse.get(a).getMonthName();
        }

        xAxis.setCategories(new ArrayList<>(Arrays.asList(categoriesList)));
        options.setXAxis(new ArrayList<HIXAxis>() {{
            add(xAxis);
        }});

        series1 = new HIColumn();
        series1.setName("Income");
        sysData = new Number[vitalResponse.size()];

        for (int a = 0; a < vitalResponse.size(); a++) {
            sysValue = String.valueOf(vitalResponse.get(a).getTotalAmount());
            sysData[a] = Double.parseDouble(sysValue);
        }
        series1.setData(new ArrayList<>(Arrays.asList(sysData)));
        options.setSeries(new ArrayList<>(Arrays.asList(series1)));

        binding.hcIncome.reload();
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }
}