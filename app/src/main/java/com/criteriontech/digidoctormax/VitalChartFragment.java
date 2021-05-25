package com.criteriontech.digidoctormax;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.criteriontech.digidoctormax.adapters.VitalListAdapter;
import com.criteriontech.digidoctormax.databinding.FragmentVitalChartBinding;
import com.criteriontech.digidoctormax.fragment.home.DrHomeActivity;
import com.criteriontech.digidoctormax.model.VitalResponse;
import com.criteriontech.digidoctormax.request.VitalModel;
import com.criteriontech.digidoctormax.viewHolder.PatientViewModel;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HILine;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.criteriontech.digidoctormax.utils.AppUtils.VITAL_ID;
import static com.criteriontech.digidoctormax.utils.AppUtils.VITAL_IMAGE;
import static com.criteriontech.digidoctormax.utils.AppUtils.VITAL_NAME;
import static com.criteriontech.digidoctormax.utils.AppUtils.getVitalMaxValue;
import static com.criteriontech.digidoctormax.utils.AppUtils.getVitalMinValue;


public class VitalChartFragment extends Fragment {


    private static final String TAG = "VitalChartFragment";

    FragmentVitalChartBinding chartBinding;
    NavController navController;
    PatientViewModel viewModel;

    String VitalId = null;
    String VitalName = null;
    VitalListAdapter adapter;
    int ImagePath;

    //graph Variables
    HIOptions options;
    HIPlotOptions plotOptions;
    HIExporting hiExporting;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        chartBinding = FragmentVitalChartBinding.inflate(getLayoutInflater());
        return chartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        if (getArguments() == null)
            return;

        VitalId = getArguments().getString(VITAL_ID);
        Log.d(TAG, "VitalId: " + VitalId);
        VitalName = getArguments().getString(VITAL_NAME);
        ImagePath = getArguments().getInt(VITAL_IMAGE);

        chartBinding.tvVitalName.setText(VitalName);
        chartBinding.ivVital.setImageResource(ImagePath);

        chartBinding.tvVitalMaxValue.setText(getVitalMaxValue(VitalId));
        chartBinding.tvVitalMinValue.setText(getVitalMinValue(VitalId));

        initGraph();
        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        final VitalModel vitalModel = new VitalModel();

        vitalModel.setMemberId(getArguments().getString("id"));
        Log.d(TAG, "onViewCreated: MemberId " + getArguments().getString("id"));
        //vitalModel.setMemberId("23337");
        vitalModel.setVitalId(VitalId);


        adapter = new VitalListAdapter();
        chartBinding.recVitalList.setAdapter(adapter);
        chartBinding.recVitalList.addItemDecoration(new
                DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));


        viewModel.getVitals(vitalModel, requireActivity()).observe(getViewLifecycleOwner(), vitalResponse -> {

            if (null == vitalResponse || vitalResponse.isEmpty()) {
                Toast.makeText(requireActivity(), "No data found", Toast.LENGTH_SHORT).show();
                DrHomeActivity.getInstance().onSupportNavigateUp();
                return;
            }
            adapter.submitList(vitalResponse);

            setVitalChartData(vitalResponse);


        });
    }


    private void initGraph() {
        chartBinding.chartView.theme = "sand-signika";
        options = new HIOptions();
        final HIYAxis yAxis = new HIYAxis();
        yAxis.setTitle(new HITitle());
        yAxis.getTitle().setText(VitalName);
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
    }

    private void setVitalChartData(List<VitalResponse.VitalDateVise> vitalResponse) {
        final HIXAxis xAxis = new HIXAxis();
        String[] categoriesList = new String[vitalResponse.size()];
        for (int a = 0; a < vitalResponse.size(); a++) {
            categoriesList[a] = vitalResponse.get(a).getVitalDateForGraph();
        }
        xAxis.setCategories(new ArrayList<>(Arrays.asList(categoriesList)));
        options.setXAxis(new ArrayList<HIXAxis>() {{
            add(xAxis);
        }});

        if (VitalId.equalsIgnoreCase("-1")) {

            HISeries series1 = null, series2 = null;
            try {
                series1 = new HISeries();
                series1.setName(getString(R.string.systolic));
                Number[] sysData = new Number[vitalResponse.size()];

                for (int a = 0; a < vitalResponse.size(); a++) {
                    String sysValue = vitalResponse.get(a).getVitalDetails().get(0).getVitalValue();
                    sysData[a] = Double.parseDouble(sysValue);
                }
                series1.setData(new ArrayList<>(Arrays.asList(sysData)));

                series2 = new HISeries();
                series2.setName(getString(R.string.diastolic));

                Number[] diasData = new Number[vitalResponse.size()];
                for (int a = 0; a < vitalResponse.size(); a++) {
                    if (vitalResponse.get(a).getVitalDetails().size() > 1) {
                        String diasValue = vitalResponse.get(a).getVitalDetails().get(1).getVitalValue();
                        diasData[a] = Integer.parseInt(diasValue);
                    }

                }
                series2.setData(new ArrayList<>(Arrays.asList(diasData)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            options.setSeries(new ArrayList<>(Arrays.asList(series1, series2)));
        } else {
            HISeries series1 = new HISeries();
            series1.setName(VitalName);
            Number[] sysData = new Number[vitalResponse.size()];

            for (int a = 0; a < vitalResponse.size(); a++) {
                String sysValue = vitalResponse.get(a).getVitalDetails().get(0).getVitalValue();
                sysData[a] = Double.parseDouble(sysValue);
            }
            series1.setData(new ArrayList<>(Arrays.asList(sysData)));
            options.setSeries(new ArrayList<>(Arrays.asList(series1)));
        }

        chartBinding.chartView.setOptions(options);
        chartBinding.progressBar4.setVisibility(View.GONE);
    }

}