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

import com.criteriontech.digidoctormax.databinding.FragmentVitalsBinding;
import com.criteriontech.digidoctormax.databinding.SelectVitalVategoryListViewBinding;
import com.criteriontech.digidoctormax.model.SelectVitalCategoryModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.criteriontech.digidoctormax.utils.AppUtils.BLOOD_SUGAR_ID;
import static com.criteriontech.digidoctormax.utils.AppUtils.BP_ID;
import static com.criteriontech.digidoctormax.utils.AppUtils.PULSE_RATE_ID;
import static com.criteriontech.digidoctormax.utils.AppUtils.RESPIRATORY_ID;
import static com.criteriontech.digidoctormax.utils.AppUtils.SPO2_ID;
import static com.criteriontech.digidoctormax.utils.AppUtils.TEMPERATURE_ID;
import static com.criteriontech.digidoctormax.utils.AppUtils.VITAL_ID;
import static com.criteriontech.digidoctormax.utils.AppUtils.VITAL_IMAGE;
import static com.criteriontech.digidoctormax.utils.AppUtils.VITAL_NAME;


public class VitalsFragment extends Fragment {
    private static final String TAG = "VitalsFragment";


    FragmentVitalsBinding binding;
    NavController navController;


    String memberId = null;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVitalsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        if (getArguments() == null)
            return;
        memberId = String.valueOf(getArguments().getInt("id"));
        Log.d(TAG, "onViewCreated: MemberId" + memberId);
        List<SelectVitalCategoryModel> selectTypeList = getSelectTypData();


        binding.recSelectType.setAdapter(new SelectVitalCategoryListAdapter(selectTypeList));

    }


    private List<SelectVitalCategoryModel> getSelectTypData() {
        List<SelectVitalCategoryModel> selectTypeList = new ArrayList<>();
        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.blood_pressure),
                R.drawable.bp_chart_icon,
                false, "#FFEDD4", BP_ID));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.pulse_rate),
                R.drawable.bp_chart_icon,
                false, "#EAE2FF", PULSE_RATE_ID));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.random_blood_sugar),
                R.drawable.bp_chart_icon,
                false, "#E5FFEF", BLOOD_SUGAR_ID));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.spo2),
                R.drawable.bp_chart_icon,
                false, "#E2EEFF", SPO2_ID));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.respiratory_rate),
                R.drawable.bp_chart_icon,
                false, "#E3D9FF", RESPIRATORY_ID));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.temperature),
                R.drawable.thermometer,
                false, "#FFE4E8", TEMPERATURE_ID));


        return selectTypeList;
    }

    private class SelectVitalCategoryListAdapter extends RecyclerView.Adapter<SelectVitalCategoryListAdapter.CategoryVH> {
        List<SelectVitalCategoryModel> selectTypeList;

        public SelectVitalCategoryListAdapter(List<SelectVitalCategoryModel> selectTypeList) {
            this.selectTypeList = selectTypeList;
        }

        @NonNull
        @Override
        public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            SelectVitalVategoryListViewBinding listViewBinding = SelectVitalVategoryListViewBinding.inflate(inflater, parent, false);
            return new CategoryVH(listViewBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryVH holder, final int position) {

            try {
                SelectVitalCategoryModel model = selectTypeList.get(position);
                holder.listViewBinding.setModel(model);
                holder.listViewBinding.view.setVisibility(selectTypeList.size() == (position + 1) ? View.GONE : View.VISIBLE);
                holder.listViewBinding.constraintsMain.setOnClickListener(view -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(VITAL_ID, selectTypeList.get(position).getVitalId());
                    bundle.putString(VITAL_NAME, selectTypeList.get(position).getTitle());
                    bundle.putInt(VITAL_IMAGE, selectTypeList.get(position).getImage());
                    bundle.putString("id", memberId);

                    navController.navigate(R.id.action_vitalsFragment_to_vitalChartFragment, bundle);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return selectTypeList.size();
        }

        public class CategoryVH extends RecyclerView.ViewHolder {
            SelectVitalVategoryListViewBinding listViewBinding;

            public CategoryVH(SelectVitalVategoryListViewBinding listViewBinding) {
                super(listViewBinding.getRoot());
                this.listViewBinding = listViewBinding;
            }
        }
    }

}