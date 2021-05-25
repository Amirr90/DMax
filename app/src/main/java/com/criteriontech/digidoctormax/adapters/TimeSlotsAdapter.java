package com.criteriontech.digidoctormax.adapters;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.TimingViewPrimaryNewBinding;
import com.criteriontech.digidoctormax.databinding.TimingViewSecondaryNewBinding;
import com.criteriontech.digidoctormax.fragment.home.DrHomeActivity;
import com.criteriontech.digidoctormax.model.GetAppointmentSlotsDataRes;
import com.criteriontech.digidoctormax.model.GetAppointmentSlotsModel;
import com.criteriontech.digidoctormax.utils.AdapterInterface;

import java.util.List;

import static com.criteriontech.digidoctormax.utils.AppUtils.fadeIn;

public class TimeSlotsAdapter extends RecyclerView.Adapter<TimeSlotsAdapter.SlotVH> {
    List<GetAppointmentSlotsDataRes> timeSlotsModelList;
    TimeSlotsAdapterSecondary adapterSecondary;
    AdapterInterface adapterInterface;

    public TimeSlotsAdapter(List<GetAppointmentSlotsDataRes> timeSlotsModelList, AdapterInterface adapterInterface) {
        this.timeSlotsModelList = timeSlotsModelList;
        this.adapterInterface = adapterInterface;
    }


    @NonNull
    @Override
    public TimeSlotsAdapter.SlotVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TimingViewPrimaryNewBinding primaryNewBinding = TimingViewPrimaryNewBinding.inflate(inflater, parent, false);
        return new SlotVH(primaryNewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotsAdapter.SlotVH holder, int position) {
        GetAppointmentSlotsDataRes timeSlotsModel = timeSlotsModelList.get(position);

        Log.d("TAG", "onBindViewHolder1: " + timeSlotsModel);



        holder.primaryNewBinding.setTiming(timeSlotsModel);
        if (null != timeSlotsModel.getSlotDetails()) {
            adapterSecondary = new TimeSlotsAdapterSecondary(timeSlotsModel.getSlotDetails(), position, new AdapterInterface() {
                @Override
                public void onItemClicked(Object o) {
             adapterInterface.onItemClicked(o);
                }
            });

            holder.primaryNewBinding.Rec.setAdapter(adapterSecondary);
            holder.primaryNewBinding.getRoot().setAnimation(fadeIn(DrHomeActivity.getInstance()));
        }
    }

    @Override
    public int getItemCount() {
        return timeSlotsModelList.size();
    }

    public class SlotVH extends RecyclerView.ViewHolder {
        TimingViewPrimaryNewBinding primaryNewBinding;

        public SlotVH(@NonNull TimingViewPrimaryNewBinding itemView) {
            super(itemView.getRoot());

            this.primaryNewBinding = itemView;
        }
    }

    public class TimeSlotsAdapterSecondary extends RecyclerView.Adapter<TimeSlotsAdapterSecondary.SlotsSecondaryVH> {

        List<GetAppointmentSlotsModel> timeDetailsModelList;
        int subSelectedPosition = -1;
        AdapterInterface adapterInterface;

        public TimeSlotsAdapterSecondary(List<GetAppointmentSlotsModel> timeDetailsModelList, int subSelectedPosition, AdapterInterface adapterInterface) {
            this.timeDetailsModelList = timeDetailsModelList;
            this.subSelectedPosition = subSelectedPosition;
            this.adapterInterface = adapterInterface;
        }

        @NonNull
        @Override
        public SlotsSecondaryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            TimingViewSecondaryNewBinding viewSecondaryNewBinding = TimingViewSecondaryNewBinding.inflate(inflater, parent, false);
            return new SlotsSecondaryVH(viewSecondaryNewBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull SlotsSecondaryVH holder, int position) {

            final GetAppointmentSlotsModel timeDetailsModel = timeDetailsModelList.get(position);
            holder.viewSecondaryNewBinding.setTimeDetailsModel(timeDetailsModel);

            if (subSelectedPosition == position) {
                changeLayoutColor(holder, DrHomeActivity.getInstance().getResources().getDrawable(R.drawable.round_green),
                        DrHomeActivity.getInstance().getResources().getColor(R.color.white)
                );
            } else {
                changeLayoutColor(holder, DrHomeActivity.getInstance().getResources().getDrawable(R.drawable.round_for_search),
                        DrHomeActivity.getInstance().getResources().getColor(R.color.colorPrimary)
                );
            }

            holder.viewSecondaryNewBinding.timingText.setOnClickListener(v -> {
                subSelectedPosition = position;
                notifyDataSetChanged();
                adapterInterface.onItemClicked(timeDetailsModel.getSlotTime());
            });

            changeSlotsColorState(timeDetailsModelList, holder);

        }

        private void changeLayoutColor(SlotsSecondaryVH holder, Drawable drawable, int color) {
            holder.viewSecondaryNewBinding.timingText.setBackground(drawable);
            holder.viewSecondaryNewBinding.timingText.setTextColor(color);
        }

        @Override
        public int getItemCount() {
            return timeDetailsModelList.size();
        }

        public class SlotsSecondaryVH extends RecyclerView.ViewHolder {
            TimingViewSecondaryNewBinding viewSecondaryNewBinding;

            public SlotsSecondaryVH(@NonNull TimingViewSecondaryNewBinding itemView) {
                super(itemView.getRoot());
                this.viewSecondaryNewBinding = itemView;
            }
        }
    }

    private void changeSlotsColorState(List<GetAppointmentSlotsModel> timeDetailsModelList, TimeSlotsAdapterSecondary.SlotsSecondaryVH holder) {

    }
}
