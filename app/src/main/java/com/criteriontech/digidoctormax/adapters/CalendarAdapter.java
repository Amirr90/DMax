package com.criteriontech.digidoctormax.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.CalenderViewBinding;
import com.criteriontech.digidoctormax.fragment.home.DrHomeActivity;
import com.criteriontech.digidoctormax.model.CalendarModel;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalenderVH> {


    List<CalendarModel> calendarModelList;
    CalenderInterface calenderInterface;
    public static int selectedPosition;

    public CalendarAdapter(List<CalendarModel> calendarModelList, CalenderInterface calenderInterface) {
        this.calendarModelList = calendarModelList;
        this.calenderInterface = calenderInterface;

        selectedPosition = 0;
        for (int a = 0; a < calendarModelList.size(); a++) {
            if (calendarModelList.get(a).isAvailable()) {
                selectedPosition = a;
                return;
            }
        }
    }

    @NonNull
    @Override
    public CalendarAdapter.CalenderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CalenderViewBinding calenderViewBinding = CalenderViewBinding.inflate(inflater, parent, false);
        calenderViewBinding.setCalenderInterface(calenderInterface);
        return new CalenderVH(calenderViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.CalenderVH holder, int position) {

        final CalendarModel calendarModel = calendarModelList.get(position);

        Log.d("TAG", "onBindViewHolder: " + calendarModel.isAvailable());

        holder.calenderViewBinding.setCalender(calendarModel);

        //  holder.calenderViewBinding.rlCalenderRoot.setEnabled(calendarModel.isAvailable());
        holder.calenderViewBinding.rlCalenderRoot.setOnClickListener(v -> {
            calenderInterface.onItemClicked(calendarModel, position);
            holder.calenderViewBinding.rlCalenderRoot.setBackground(DrHomeActivity.getInstance().getResources().getDrawable(R.drawable.rectangle_outline_new_ui_color_yellow));
            selectedPosition = position;
            setTextColor(holder, DrHomeActivity.getInstance().getResources().getColor(R.color.white),
                    DrHomeActivity.getInstance().getResources().getColor(R.color.white));
            notifyDataSetChanged();


        });


        if (calendarModel.isAvailable() == false) {
            if (selectedPosition == position) {
                setTextColor(holder, DrHomeActivity.getInstance().getResources().getColor(R.color.white),
                        DrHomeActivity.getInstance().getResources().getColor(R.color.white));
                holder.calenderViewBinding.getRoot().setBackground(DrHomeActivity.getInstance().getResources().getDrawable(R.drawable.rectangle_outline_new_ui_color_yellow));
            } else {
                setTextColor(holder, DrHomeActivity.getInstance().getResources().getColor(R.color.colorPrimary),
                        DrHomeActivity.getInstance().getResources().getColor(R.color.TextGrayColo));
                holder.calenderViewBinding.getRoot().setBackground(DrHomeActivity.getInstance().getResources().getDrawable(R.drawable.rectangle_outline_new_ui_color));
            }

        } else {
            setTextColor(holder, DrHomeActivity.getInstance().getResources().getColor(R.color.TextGrayColo),
                    DrHomeActivity.getInstance().getResources().getColor(R.color.TextGrayColo));
            holder.calenderViewBinding.getRoot().setBackground(DrHomeActivity.getInstance().getResources().getDrawable(R.drawable.disable_rectangle_outline_new_ui_color));

        }

    }

    public CalendarModel getItem() {
        CalendarModel calendarModel;
        if (null == calendarModelList || calendarModelList.isEmpty())
            return null;
        else {
            for (CalendarModel calendarModel1 : calendarModelList) {
                if (calendarModel1.isAvailable()) {
                    calendarModel = calendarModel1;
                    return calendarModel;
                }
            }
            return null;
        }

    }

    private void setTextColor(CalenderVH holder, int color, int color2) {
        holder.calenderViewBinding.textView81.setTextColor(color2);
        holder.calenderViewBinding.textView85.setTextColor(color);
        holder.calenderViewBinding.textView86.setTextColor(color2);
    }

    @Override
    public int getItemCount() {
        return calendarModelList.size();
    }

    public class CalenderVH extends RecyclerView.ViewHolder {
        CalenderViewBinding calenderViewBinding;

        public CalenderVH(@NonNull CalenderViewBinding calenderViewBinding) {
            super(calenderViewBinding.getRoot());
            this.calenderViewBinding = calenderViewBinding;
        }
    }

    public interface CalenderInterface {
        void onItemClicked(CalendarModel calendarModel, int position);
    }
}
