package com.criteriontech.digidoctormax;

import android.app.TimePickerDialog;
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

import com.criteriontech.digidoctormax.databinding.FragmentAddTimeSlotFoNewlyAddedDoctorBinding;
import com.criteriontech.digidoctormax.databinding.InnerSlotTimeBinding;
import com.criteriontech.digidoctormax.model.DoctorProfileValue;
import com.criteriontech.digidoctormax.model.TimeSlotDataTable;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.criteriontech.digidoctormax.utils.AppUtils.parseDateIn;


public class AddTimeSlotFoNewlyAddedDoctorFragment extends Fragment {

    FragmentAddTimeSlotFoNewlyAddedDoctorBinding binding;
    NavController navController;

    private int hourTo = 0, minutesTo = 0, hourFrom = 0, minutesFrom = 0;
    private Calendar c;
    TimeSlotAdp timeSlotAdp;
    List<TimeSlotDataTable> timeSlotList;
    public static List<TimeSlotDataTable> timeSlotAddedList;
    Gson gson;
    Bundle bundle;

    DoctorProfileValue profileValue;
    private static final String TAG = "AddTimeSlotFoNewlyAdded";


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTimeSlotFoNewlyAddedDoctorBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        timeSlotList = new ArrayList<>();
        timeSlotAddedList = new ArrayList<>();
        gson = new Gson();
        bundle = new Bundle();
        c = Calendar.getInstance();
        timeSlotAdp = new TimeSlotAdp();
        bundle.putBoolean("add", getArguments().getBoolean("add"));
        binding.rvTimeSlot.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvTimeSlot.setAdapter(timeSlotAdp);
        hourTo = c.get(Calendar.HOUR_OF_DAY);
        minutesTo = c.get(Calendar.MINUTE);
        hourFrom = c.get(Calendar.HOUR_OF_DAY);
        minutesFrom = c.get(Calendar.MINUTE);

        binding.tvFromTime.setText(AppUtils.formatTime(hourFrom, minutesFrom));
        binding.tvToTime.setText(AppUtils.formatTime(hourTo, minutesTo));

        if (getArguments() == null)
            return;

        String jsonString = getArguments().getString("model");

        profileValue = new DoctorProfileValue();

        Gson gson = new Gson();
        profileValue = gson.fromJson(jsonString, DoctorProfileValue.class);
        profileValue.setGender(profileValue.getGender().equalsIgnoreCase("male") ? "1" : "2");
        profileValue.setDrFee("" + (int) (Double.parseDouble(profileValue.getDrFee())));
        profileValue.setDob(parseDateIn(profileValue.getDob(), "yyyy-MM-dd"), "");


        //   profileValue.setId(profileValue.getId());
        Log.d(TAG, "getServiceProviderDetailsIdBefore: " + profileValue.getServiceProviderDetailsId());

        profileValue.setId(profileValue.getServiceProviderDetailsId());
        Log.d(TAG, "getServiceProviderDetailsIdAfter: " + profileValue.getId());


        binding.btnDone.setOnClickListener(view1 -> {

            if (!binding.etDuration.getText().toString().trim().equals("")) {
                try {
                    profileValue.setDuration(Integer.parseInt(binding.etDuration.getText().toString().trim()));
                    if (!timeSlotAddedList.isEmpty()) {
                        if (getArguments().getBoolean("login")) {
                            if (!timeSlotAddedList.isEmpty()) {

                                profileValue.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());

                                try {
                                    JSONArray array = new JSONArray();
                                    for (int i = 0; i < timeSlotAddedList.size(); i++) {
                                        JSONObject object = new JSONObject();
                                        object.put("dayId", timeSlotAddedList.get(i).getDayId());
                                        object.put("timeFrom", timeSlotAddedList.get(i).getTimeFrom());
                                        object.put("timeTo", timeSlotAddedList.get(i).getTimeTo());
                                        array.put(object);
                                    }
                                    profileValue.setDtDataTable(String.valueOf(array));
                                    Log.v("updateDrProfile", String.valueOf(profileValue));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    AppUtils.hideDialog();
                                }
                                if (AppUtils.isNetworkConnected(requireActivity())) {
                                    ApiUtils.updateDoctorProfile(profileValue, new ApiCallbackInterface() {
                                        @Override
                                        public void onSuccess(Object obj) {
                                            AppUtils.hideDialog();
                                            Toast.makeText(requireActivity(), "Doctor Added Successfully !!", Toast.LENGTH_SHORT).show();
                                            navController.navigate(R.id.action_addTimeSlotFoNewlyAddedDoctorFragment_to_addedDoctorCongratulationFragment);
                                        }

                                        @Override
                                        public void onFailure(String msg) {
                                            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                                            AppUtils.hideDialog();
                                        }
                                    });
                                } else
                                    Toast.makeText(requireActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
                            }

                        } else if (!timeSlotAddedList.isEmpty()) {
                            bundle.putBoolean("login", false);
                            navController.navigate(R.id.action_selectSlotFragment_to_yourslotfragment, bundle);
                        }
                    } else
                        Toast.makeText(requireActivity(), "Please add a time slot to proceed!", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(requireActivity(), "Please enter duration!", Toast.LENGTH_SHORT).show();
                binding.etDuration.setError("Please enter duration");
            }

        });


        TimeSlotDataTable timeSlotDataTable1 = new TimeSlotDataTable();
        timeSlotDataTable1.setDayId(1);
        timeSlotDataTable1.setDayName("Mon");
        timeSlotDataTable1.setTimeFrom("");
        timeSlotDataTable1.setTimeTo("");
        TimeSlotDataTable timeSlotDataTable2 = new TimeSlotDataTable();
        timeSlotDataTable2.setDayId(2);
        timeSlotDataTable2.setDayName("Tue");
        timeSlotDataTable2.setTimeFrom("");
        timeSlotDataTable2.setTimeTo("");
        TimeSlotDataTable timeSlotDataTable3 = new TimeSlotDataTable();
        timeSlotDataTable3.setDayId(3);
        timeSlotDataTable3.setDayName("Wed");
        timeSlotDataTable3.setTimeFrom("");
        timeSlotDataTable3.setTimeTo("");
        TimeSlotDataTable timeSlotDataTable4 = new TimeSlotDataTable();
        timeSlotDataTable4.setDayId(4);
        timeSlotDataTable4.setDayName("Thu");
        timeSlotDataTable4.setTimeFrom("");
        timeSlotDataTable4.setTimeTo("");
        TimeSlotDataTable timeSlotDataTable5 = new TimeSlotDataTable();
        timeSlotDataTable5.setDayId(5);
        timeSlotDataTable5.setDayName("Fri");
        timeSlotDataTable5.setTimeFrom("");
        timeSlotDataTable5.setTimeTo("");
        TimeSlotDataTable timeSlotDataTable6 = new TimeSlotDataTable();
        timeSlotDataTable6.setDayId(6);
        timeSlotDataTable6.setDayName("Sat");
        timeSlotDataTable6.setTimeFrom("");
        timeSlotDataTable6.setTimeTo("");
        TimeSlotDataTable timeSlotDataTable7 = new TimeSlotDataTable();
        timeSlotDataTable7.setDayId(7);
        timeSlotDataTable7.setDayName("Sun");
        timeSlotDataTable7.setTimeFrom("");
        timeSlotDataTable7.setTimeTo("");
        timeSlotList.add(timeSlotDataTable1);
        timeSlotList.add(timeSlotDataTable2);
        timeSlotList.add(timeSlotDataTable3);
        timeSlotList.add(timeSlotDataTable4);
        timeSlotList.add(timeSlotDataTable5);
        timeSlotList.add(timeSlotDataTable6);
        timeSlotList.add(timeSlotDataTable7);
        binding.tvMon.setOnClickListener(view1 -> {
            try {
                for (int i = 0; i < timeSlotList.size(); i++) {
                    if (timeSlotList.get(i).getDayId() == 1) {
                        if (timeSlotList.get(i).getSelected()) {
                            binding.tvMon.setTextColor(Color.BLACK);
                            binding.tvMon.setBackgroundResource(R.drawable.slot_week);
                            timeSlotList.get(i).setSelected(false);
                        } else {
                            binding.tvMon.setTextColor(Color.WHITE);
                            binding.tvMon.setBackgroundResource(R.drawable.slot_week_selected);
                            timeSlotList.get(i).setSelected(true);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        binding.tvTue.setOnClickListener(view1 -> {
            try {
                for (int i = 0; i < timeSlotList.size(); i++) {
                    if (timeSlotList.get(i).getDayId() == 2) {
                        if (timeSlotList.get(i).getSelected()) {
                            binding.tvTue.setTextColor(Color.BLACK);
                            binding.tvTue.setBackgroundResource(R.drawable.slot_week);
                            timeSlotList.get(i).setSelected(false);
                        } else {
                            binding.tvTue.setTextColor(Color.WHITE);
                            binding.tvTue.setBackgroundResource(R.drawable.slot_week_selected);
                            timeSlotList.get(i).setSelected(true);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        binding.tvWed.setOnClickListener(view1 -> {
            try {
                for (int i = 0; i < timeSlotList.size(); i++) {
                    if (timeSlotList.get(i).getDayId() == 3) {
                        if (timeSlotList.get(i).getSelected()) {
                            binding.tvWed.setTextColor(Color.BLACK);
                            binding.tvWed.setBackgroundResource(R.drawable.slot_week);
                            timeSlotList.get(i).setSelected(false);
                        } else {
                            binding.tvWed.setTextColor(Color.WHITE);
                            binding.tvWed.setBackgroundResource(R.drawable.slot_week_selected);
                            timeSlotList.get(i).setSelected(true);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        binding.tvThu.setOnClickListener(view1 -> {
            try {
                for (int i = 0; i < timeSlotList.size(); i++) {
                    if (timeSlotList.get(i).getDayId() == 4) {
                        if (timeSlotList.get(i).getSelected()) {
                            binding.tvThu.setTextColor(Color.BLACK);
                            binding.tvThu.setBackgroundResource(R.drawable.slot_week);
                            timeSlotList.get(i).setSelected(false);
                        } else {
                            binding.tvThu.setTextColor(Color.WHITE);
                            binding.tvThu.setBackgroundResource(R.drawable.slot_week_selected);
                            timeSlotList.get(i).setSelected(true);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        binding.tvFri.setOnClickListener(view1 -> {
            try {
                for (int i = 0; i < timeSlotList.size(); i++) {
                    if (timeSlotList.get(i).getDayId() == 5) {
                        if (timeSlotList.get(i).getSelected()) {
                            binding.tvFri.setTextColor(Color.BLACK);
                            binding.tvFri.setBackgroundResource(R.drawable.slot_week);
                            timeSlotList.get(i).setSelected(false);
                        } else {
                            binding.tvFri.setTextColor(Color.WHITE);
                            binding.tvFri.setBackgroundResource(R.drawable.slot_week_selected);
                            timeSlotList.get(i).setSelected(true);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        binding.tvSat.setOnClickListener(view1 -> {
            try {
                for (int i = 0; i < timeSlotList.size(); i++) {
                    if (timeSlotList.get(i).getDayId() == 6) {
                        if (timeSlotList.get(i).getSelected()) {
                            binding.tvSat.setTextColor(Color.BLACK);
                            binding.tvSat.setBackgroundResource(R.drawable.slot_week);
                            timeSlotList.get(i).setSelected(false);
                        } else {
                            binding.tvSat.setTextColor(Color.WHITE);
                            binding.tvSat.setBackgroundResource(R.drawable.slot_week_selected);
                            timeSlotList.get(i).setSelected(true);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        binding.tvSun.setOnClickListener(view1 -> {
            try {
                for (int i = 0; i < timeSlotList.size(); i++) {
                    if (timeSlotList.get(i).getDayId() == 7) {
                        if (timeSlotList.get(i).getSelected()) {
                            binding.tvSun.setTextColor(Color.BLACK);
                            binding.tvSun.setBackgroundResource(R.drawable.slot_week);
                            timeSlotList.get(i).setSelected(false);
                        } else {
                            binding.tvSun.setTextColor(Color.WHITE);
                            binding.tvSun.setBackgroundResource(R.drawable.slot_week_selected);
                            timeSlotList.get(i).setSelected(true);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        binding.ClMobile.setOnClickListener(view1 -> {
            TimePickerDialog mTimePicker = new TimePickerDialog(requireActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    (timePicker, selectedHour, selectedMinute) -> {
                        hourFrom = selectedHour;
                        minutesFrom = selectedMinute;
                        binding.tvFromTime.setText(AppUtils.formatTime(selectedHour, selectedMinute));
                        c.set(Calendar.HOUR, selectedHour);
                        c.set(Calendar.MINUTE, selectedMinute);
                    }, hourFrom, minutesFrom, false);
            mTimePicker.setTitle("Select From Time");
            mTimePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            mTimePicker.show();
        });
        binding.to.setOnClickListener(view1 -> {
            TimePickerDialog mTimePicker = new TimePickerDialog(requireActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    (timePicker, selectedHour, selectedMinute) -> {
                        hourTo = selectedHour;
                        minutesTo = selectedMinute;
                        binding.tvToTime.setText(AppUtils.formatTime(selectedHour, selectedMinute));
                        c.set(Calendar.HOUR, selectedHour);
                        c.set(Calendar.MINUTE, selectedMinute);
                    }, hourTo, minutesTo, false);
            mTimePicker.setTitle("Select To Time");
            mTimePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            mTimePicker.show();
        });
        binding.imgAdd.setOnClickListener(view1 -> {
            if (binding.tvFromTime.getText().toString().equalsIgnoreCase(binding.tvToTime.getText().toString())) {
                Toast.makeText(requireActivity(), "Select valid time slots", Toast.LENGTH_SHORT).show();
            } else {
                if (null != timeSlotAddedList)
                    for (int i = 0; i < timeSlotList.size(); i++) {
                        if (timeSlotList.get(i).getSelected()) {
                            timeSlotList.get(i).setTimeFrom(binding.tvFromTime.getText().toString());
                            timeSlotList.get(i).setTimeTo(binding.tvToTime.getText().toString());
                            timeSlotAddedList.add(timeSlotList.get(i));
                        }
                    }
                binding.tvMon.setTextColor(Color.BLACK);
                binding.tvMon.setBackgroundResource(R.drawable.slot_week);
                binding.tvTue.setTextColor(Color.BLACK);
                binding.tvTue.setBackgroundResource(R.drawable.slot_week);
                binding.tvWed.setTextColor(Color.BLACK);
                binding.tvWed.setBackgroundResource(R.drawable.slot_week);
                binding.tvThu.setTextColor(Color.BLACK);
                binding.tvThu.setBackgroundResource(R.drawable.slot_week);
                binding.tvFri.setTextColor(Color.BLACK);
                binding.tvFri.setBackgroundResource(R.drawable.slot_week);
                binding.tvSat.setTextColor(Color.BLACK);
                binding.tvSat.setBackgroundResource(R.drawable.slot_week);
                binding.tvSun.setTextColor(Color.BLACK);
                binding.tvSun.setBackgroundResource(R.drawable.slot_week);
                timeSlotList.removeAll(timeSlotList);
                TimeSlotDataTable timeSlotDataTable8 = new TimeSlotDataTable();
                timeSlotDataTable8.setDayId(1);
                timeSlotDataTable8.setDayName("Mon");
                timeSlotDataTable8.setTimeFrom("");
                timeSlotDataTable8.setTimeTo("");
                TimeSlotDataTable timeSlotDataTable9 = new TimeSlotDataTable();
                timeSlotDataTable9.setDayId(2);
                timeSlotDataTable9.setDayName("Tue");
                timeSlotDataTable9.setTimeFrom("");
                timeSlotDataTable9.setTimeTo("");
                TimeSlotDataTable timeSlotDataTable10 = new TimeSlotDataTable();
                timeSlotDataTable10.setDayId(3);
                timeSlotDataTable10.setDayName("Wed");
                timeSlotDataTable10.setTimeFrom("");
                timeSlotDataTable10.setTimeTo("");
                TimeSlotDataTable timeSlotDataTable11 = new TimeSlotDataTable();
                timeSlotDataTable11.setDayId(4);
                timeSlotDataTable11.setDayName("Thu");
                timeSlotDataTable11.setTimeFrom("");
                timeSlotDataTable11.setTimeTo("");
                TimeSlotDataTable timeSlotDataTable12 = new TimeSlotDataTable();
                timeSlotDataTable12.setDayId(5);
                timeSlotDataTable12.setDayName("Fri");
                timeSlotDataTable12.setTimeFrom("");
                timeSlotDataTable12.setTimeTo("");
                TimeSlotDataTable timeSlotDataTable13 = new TimeSlotDataTable();
                timeSlotDataTable13.setDayId(6);
                timeSlotDataTable13.setDayName("Sat");
                timeSlotDataTable13.setTimeFrom("");
                timeSlotDataTable13.setTimeTo("");
                TimeSlotDataTable timeSlotDataTable14 = new TimeSlotDataTable();
                timeSlotDataTable14.setDayId(7);
                timeSlotDataTable14.setDayName("Sun");
                timeSlotDataTable14.setTimeFrom("");
                timeSlotDataTable14.setTimeTo("");
                timeSlotList.add(timeSlotDataTable8);
                timeSlotList.add(timeSlotDataTable9);
                timeSlotList.add(timeSlotDataTable10);
                timeSlotList.add(timeSlotDataTable11);
                timeSlotList.add(timeSlotDataTable12);
                timeSlotList.add(timeSlotDataTable13);
                timeSlotList.add(timeSlotDataTable14);
                timeSlotAdp.submitList(timeSlotAddedList);
                timeSlotAdp.notifyDataSetChanged();
            }
        });
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