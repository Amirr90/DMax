package com.criteriontech.digidoctormax.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.adapters.CalendarAdapter;
import com.criteriontech.digidoctormax.adapters.TimeSlotsAdapter;
import com.criteriontech.digidoctormax.databinding.FragmentRescheduleBinding;
import com.criteriontech.digidoctormax.databinding.FragmentVitalsBinding;
import com.criteriontech.digidoctormax.fragment.home.DrHomeActivity;
import com.criteriontech.digidoctormax.interfaces.BookAppointmentInterface;
import com.criteriontech.digidoctormax.model.BookAppointment2;
import com.criteriontech.digidoctormax.model.CalendarModel;
import com.criteriontech.digidoctormax.model.CheckSlotAvailabilityDataRes;
import com.criteriontech.digidoctormax.model.GetAppointmentSlotsDataRes;
import com.criteriontech.digidoctormax.model.LoginValue;
import com.criteriontech.digidoctormax.model.NoOfPatientValue;
import com.criteriontech.digidoctormax.model.OnlineAppointmentModel;
import com.criteriontech.digidoctormax.model.OnlineAppointmentRes;
import com.criteriontech.digidoctormax.model.User;
import com.criteriontech.digidoctormax.utils.AdapterInterface;
import com.criteriontech.digidoctormax.utils.Api;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.NewDashboardUtils;
import com.criteriontech.digidoctormax.utils.RetrofitClient;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.criteriontech.digidoctormax.utils.ApiUtils.getDoctorsTimeSlots;
import static com.criteriontech.digidoctormax.utils.AppUtils.*;
import static com.criteriontech.digidoctormax.utils.AppUtils.getCurrencyFormat;
import static com.criteriontech.digidoctormax.utils.AppUtils.getCurrentDateInWeekMonthDayFormat;
import static com.criteriontech.digidoctormax.utils.AppUtils.parseDateToFormatDMY;

public class RescheduleFragment extends Fragment {

    private static final String TAG = "RescheduleFragment";


    FragmentRescheduleBinding binding;
    NavController navController;


    NoOfPatientValue appointmentModel;

    String date = null;

    CalendarAdapter calendarAdapter;
    TimeSlotsAdapter slotsAdapter;

    List<GetAppointmentSlotsDataRes> slotsDataRes = new ArrayList<>();

    LoginValue user;

    List<String> workingDays;

    boolean isRevisit = false;

    String AppointmentID, MemberID, PTname, PtAppointment;
    public static RescheduleFragment instance;

    public static RescheduleFragment getInstance() {
        return instance;
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRescheduleBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Log.d(TAG, "onViewCreated: not null");

        //getting Model
        if (null == getArguments())
            return;
        AppointmentID = String.valueOf(getArguments().getInt("appointmentId"));
        MemberID = String.valueOf(getArguments().getInt("memberId"));
        PTname = getArguments().getString("Pname");
        PtAppointment = getArguments().getString("AppointmentDT");


        Log.d(TAG, "onViewCreated:  null");
        user = SharedPrefManager.getInstance(requireContext()).getUser();

        String jsonString = getArguments().getString("appointmentModel");
        appointmentModel = new NoOfPatientValue();
        Gson gson = new Gson();
        appointmentModel = gson.fromJson(jsonString, NoOfPatientValue.class);
        calendarAdapter = new CalendarAdapter(getNextWeekDays(), new CalendarAdapter.CalenderInterface() {
            @Override
            public void onItemClicked(CalendarModel calendarModel, int position) {


                date = calendarModel.getDateSend();
                getDocTimeSlot(date);


            }
        });
        binding.calRec.setAdapter(calendarAdapter);
        binding.tvCurrentDate.setText(getCurrentDateInWeekMonthDayFormat());
        binding.pname.setText(PTname);
        binding.ApDT.setText("Appointment on: " + PtAppointment);

    }

    private void getDocTimeSlot(String date) {

        getDoctorsTimeSlots(String.valueOf(user.getId()),
                date,
                String.valueOf(user.getIsEraUser()),
                new ApiCallbackInterface() {
                    @Override
                    public void onSuccess(Object obj) {
                        AppUtils.hideDialog();
                        List<GetAppointmentSlotsDataRes> slots = (List<GetAppointmentSlotsDataRes>) obj;

                        if (slots != null) {
                            binding.timingRec.setVisibility(View.VISIBLE);
                        }
                        slotsDataRes.clear();
                        slotsDataRes.addAll(slots);
                        slotsAdapter = new TimeSlotsAdapter(slotsDataRes, new AdapterInterface() {
                            @Override
                            public void onItemClicked(Object obj) {
                                if (date != null) {
                                    showRescheduleDialog(obj);
                                } else
                                    Toast.makeText(DrHomeActivity.getInstance(), "Select Date", Toast.LENGTH_SHORT).show();

                            }
                        });

                        binding.timingRec.setAdapter(slotsAdapter);
                        slotsAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(String msg) {
                        AppUtils.hideDialog();
                        if (null != slotsDataRes)
                            slotsDataRes.clear();
                        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                        if (null != slotsAdapter)
                            slotsAdapter.notifyDataSetChanged();

//                        try {
//                            if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
//                                logout(PatientDashboard.getInstance(), true);
//                                Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                    }


//                    @Override
//                    public void onFailed(Throwable throwable) {
//                        AppUtils.hideDialog();
//                        if (null != slotsDataRes)
//                            slotsDataRes.clear();
//                        Toast.makeText(requireActivity(), "retry", Toast.LENGTH_SHORT).show();
//                        if (null != slotsAdapter)
//                            slotsAdapter.notifyDataSetChanged();
//
//                    }
                });
    }

    private void showRescheduleDialog(Object obj) {
        final String time = (String) obj;
        String msg;
        String title;
        if (isRevisit) {
            msg = "Re-Visit Appointment on " + time + "  " + date;
            title = "Re-visit Appointment";

        } else {
            title = "Re-Schedule Appointment";
            msg = "Re-Scheduling Appointment on " + time + "  " + date;
        }

        new AlertDialog.Builder(requireActivity())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.yes,
                        (dialog, id) -> {
                            dialog.cancel();
                            checkTimeSlot(date, time);

                        })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel()).show();

    }

    private void checkTimeSlot(String date, String time) {

        Map<String, Object> map = new HashMap<>();
        map.put(MOBILE_NUMBER, user.getMobileNo());
        map.put(MEMBER_ID, MemberID);
        map.put(KEY_DOC_ID, String.valueOf(user.getId()));
        map.put(APPOINTMENT_DATE, date);
        map.put(APPOINTMENT_TIME, time);
        map.put(IS_REVISIT, isRevisit);
        map.put(KEY_IS_ERA_USER, String.valueOf(user.getIsEraUser()));
        map.put(KEY_APPOINTMENT_ID, AppointmentID);


        Log.d(TAG, "checkTimeSlot: MAP:=> " + map.toString());

        ApiUtils.checkTimeSlotAvailability(map, new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                Log.d(TAG, "onSuccess: " + obj);

                List<CheckSlotAvailabilityDataRes> response = (List<CheckSlotAvailabilityDataRes>) obj;
                if (response != null) {

                    if (response.get(0).getIsAvailable() == 1) {
                        reScheduleAppointment(time, new BookAppointmentInterface() {
                            @Override
                            public void onAppointmentBooked(OnlineAppointmentModel appointmentModel) {
                                Toast.makeText(requireContext(), "Reschedule Successfully!", Toast.LENGTH_SHORT).show();
                                DrHomeActivity.getInstance().onSupportNavigateUp();
                                Bundle bundle = new Bundle();
                                //   bundle.putString("key", RE_SCHEDULE);
                                //navController.navigate(R.id.action_reScheduleFragment_to_cancelAppointmentFragment2, bundle);
                            }

                            @Override
                            public void onFailed(String msg) {
                                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(String errorMsg) {
                                Toast.makeText(requireActivity(), errorMsg, Toast.LENGTH_SHORT).show();

                            }
                        });
                    } else {
                        Toast.makeText(requireActivity(), "Slot not Available", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void reScheduleAppointment(String time, BookAppointmentInterface bookAppointmentInterface) {
//        User bookingUser = getUserForBooking(requireActivity());

        BookAppointment2 appointment2 = new BookAppointment2();
        appointment2.setMemberId(MemberID);
        //  appointment2.setMobileNo(bookingUser.getMobileNo());
        appointment2.setServiceProviderDetailsId(String.valueOf(user.getId()));
        appointment2.setAppointDate(parseDateToFormatDMY(date));
        appointment2.setAppointTime(time);
        appointment2.setIsEraUser(String.valueOf(user.getIsEraUser()));
        appointment2.setAppointmentId(AppointmentID);
        AppUtils.showRequestDialog(requireActivity());
        Api iRestInterfaces = RetrofitClient.getInstance().getApi();
        Call<OnlineAppointmentRes> call;
        if (!isRevisit) {
            call = iRestInterfaces.onlineAppointment2(appointment2);
        } else call = iRestInterfaces.revisitAppointment(appointment2);

        if (null != call)
            call.enqueue(new Callback<OnlineAppointmentRes>() {
                @Override
                public void onResponse(@NotNull Call<OnlineAppointmentRes> call, @NotNull Response<OnlineAppointmentRes> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            bookAppointmentInterface.onAppointmentBooked(response.body().getResponseValue().get(0));
                        } else
                            bookAppointmentInterface.onError(response.body().getResponseMessage());
                    } else bookAppointmentInterface.onError(response.message());
                }

                @Override
                public void onFailure(@NotNull Call<OnlineAppointmentRes> call, @NotNull Throwable t) {
                    AppUtils.hideDialog();
                    bookAppointmentInterface.onFailed(t.getLocalizedMessage());
                }
            });
        else
            Toast.makeText(requireActivity(), "Retry", Toast.LENGTH_SHORT).show();

    }

    private List<CalendarModel> getNextWeekDays() {
        List<CalendarModel> calendarModelList = new ArrayList<>();
        ArrayList<HashMap<String, String>> getNextWeekDays = NewDashboardUtils.getNextWeekDays();
        for (int a = 0; a < getNextWeekDays.size(); a++) {
            //  Log.d(TAG, "getNextWeekDays: " + workingDays.contains(getNextWeekDays.get(a).get("day")));
            calendarModelList.add(new CalendarModel(
                    getNextWeekDays.get(a).get("date"),
                    getNextWeekDays.get(a).get("day"),
                    getNextWeekDays.get(a).get("dateSend"),
                    false));


        }

        return calendarModelList;
    }
}