package com.criteriontech.digidoctormax.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.ActivityYourslotfragmentBinding;
import com.criteriontech.digidoctormax.databinding.InnerSlotTimeBinding;
import com.criteriontech.digidoctormax.model.TimeSlotDataTable;
import com.criteriontech.digidoctormax.response.UpdateDrProfileResp;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.criteriontech.digidoctormax.activity.StartActivity.doctorProfileValue;
import static com.criteriontech.digidoctormax.fragment.SelectSlotFragment.timeSlotAddedList;

public class YourSlotFragment extends Fragment {
    ActivityYourslotfragmentBinding binding;
    NavController navController;
    TimeSlotAdp timeSlotAdp;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        timeSlotAdp = new TimeSlotAdp();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recyclerView.setAdapter(timeSlotAdp);
        timeSlotAdp.submitList(timeSlotAddedList);
        doctorProfileValue.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
        doctorProfileValue.setId(SharedPrefManager.getInstance(requireActivity()).getUser().getId());
        for (int i = 0; i < timeSlotAddedList.size(); i++) {
            if (timeSlotAddedList.get(i).getDayId() == 1) {
                binding.tvMon.setTextColor(Color.WHITE);
                binding.tvMon.setBackgroundResource(R.drawable.slot_week_selected);
            }
            if (timeSlotAddedList.get(i).getDayId() == 2) {
                binding.tvTue.setTextColor(Color.WHITE);
                binding.tvTue.setBackgroundResource(R.drawable.slot_week_selected);
            }
            if (timeSlotAddedList.get(i).getDayId() == 3) {
                binding.tvWed.setTextColor(Color.WHITE);
                binding.tvWed.setBackgroundResource(R.drawable.slot_week_selected);
            }
            if (timeSlotAddedList.get(i).getDayId() == 4) {
                binding.tvThu.setTextColor(Color.WHITE);
                binding.tvThu.setBackgroundResource(R.drawable.slot_week_selected);
            }
            if (timeSlotAddedList.get(i).getDayId() == 5) {
                binding.tvFri.setTextColor(Color.WHITE);
                binding.tvFri.setBackgroundResource(R.drawable.slot_week_selected);
            }
            if (timeSlotAddedList.get(i).getDayId() == 6) {
                binding.tvSat.setTextColor(Color.WHITE);
                binding.tvSat.setBackgroundResource(R.drawable.slot_week_selected);
            }
            if (timeSlotAddedList.get(i).getDayId() == 7) {
                binding.tvSun.setTextColor(Color.WHITE);
                binding.tvSun.setBackgroundResource(R.drawable.slot_week_selected);
            }
        }
        binding.btnConfirm.setOnClickListener(view1 -> {
            AppUtils.showRequestDialog(requireActivity());
            try {
                JSONArray array = new JSONArray();
                for (int i = 0; i < timeSlotAddedList.size(); i++) {
                    JSONObject object = new JSONObject();
                    object.put("dayId", timeSlotAddedList.get(i).getDayId());
                    object.put("timeFrom", timeSlotAddedList.get(i).getTimeFrom());
                    object.put("timeTo", timeSlotAddedList.get(i).getTimeTo());
                    array.put(object);
                }
                doctorProfileValue.setDtDataTable(String.valueOf(array));
                Log.v("updateDrProfile", String.valueOf(doctorProfileValue));
            } catch (JSONException e) {
                e.printStackTrace();
                AppUtils.hideDialog();
            }
            if (AppUtils.isNetworkConnected(requireActivity())) {
                doctorProfileValue.setDrFee("" + (int) (Double.parseDouble(doctorProfileValue.getDrFee())));
                ApiUtils.updateDoctorProfile(doctorProfileValue, new ApiCallbackInterface() {
                    @Override
                    public void onSuccess(Object obj) {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("login", true);
                        Toast.makeText(requireActivity(), ((UpdateDrProfileResp) obj).getResponseMessage(), Toast.LENGTH_SHORT).show();
                        if (SharedPrefManager.getInstance(requireActivity()).getLoginType() == 1) {
                            navController.navigate(R.id.action_yourslotfragment_to_homeFragment2, bundle);

                        } else if (SharedPrefManager.getInstance(requireActivity()).getLoginType() == 2)
                            navController.navigate(R.id.action_yourslotfragment_to_drHomeActivity, bundle);
                        requireActivity().finish();
                        AppUtils.hideDialog();
                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                        AppUtils.hideDialog();
                    }
                });

            } else
                Toast.makeText(requireActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();


        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityYourslotfragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public class TimeSlotAdp extends ListAdapter<TimeSlotDataTable, TimeSlotAdp.ViewHolder> {
        protected TimeSlotAdp() {
            super(TimeSlotDataTable.itemCallback);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            InnerSlotTimeBinding innerSlotTimeBinding = InnerSlotTimeBinding.inflate(layoutInflater, parent, false);
            return new ViewHolder(innerSlotTimeBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TimeSlotDataTable timeSlotDataTable = getItem(position);
            Log.v("drName", String.valueOf(timeSlotDataTable.getDayName()));
            holder.innerSlotTimeBinding.setTimeSlot(timeSlotDataTable);
            holder.innerSlotTimeBinding.imgDel.setOnClickListener(view -> {
                timeSlotAddedList.remove(position);
                timeSlotAdp.submitList(timeSlotAddedList);
                timeSlotAdp.notifyDataSetChanged();
            });
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            InnerSlotTimeBinding innerSlotTimeBinding;

            public ViewHolder(@NonNull InnerSlotTimeBinding innerSlotTimeBinding) {
                super(innerSlotTimeBinding.getRoot());
                this.innerSlotTimeBinding = innerSlotTimeBinding;
            }
        }
    }
}