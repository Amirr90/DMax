package com.criteriontech.digidoctormax.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Objects;

public class VitalResponse {

    String responseMessage;
    int responseCode;
    List<VitalDateVise> responseValue;

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public List<VitalDateVise> getResponseValue() {
        return responseValue;
    }

    public static class VitalDateVise {
        String vitalDate;
        String vitalDateForGraph;
        List<VitalValueModel> vitalDetails;

        public String getVitalDateForGraph() {
            return vitalDateForGraph;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof VitalDateVise)) return false;
            VitalDateVise that = (VitalDateVise) o;
            return getVitalDate().equals(that.getVitalDate());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getVitalDate(), getVitalDetails());
        }

        public String getVitalDate() {
            return vitalDate;
        }

        public List<VitalValueModel> getVitalDetails() {
            return vitalDetails;
        }

        public class VitalValueModel {
            String vitalName;
            String vitalValue;

            public String getVitalName() {
                return vitalName;
            }

            public String getVitalValue() {
                return vitalValue;
            }
        }

        public static DiffUtil.ItemCallback<VitalDateVise> itemCallback = new DiffUtil.ItemCallback<VitalDateVise>() {
            @Override
            public boolean areItemsTheSame(@NonNull VitalDateVise oldItem, @NonNull VitalDateVise newItem) {
                return oldItem.getVitalDetails().equals(newItem.getVitalDetails());
            }

            @Override
            public boolean areContentsTheSame(@NonNull VitalDateVise oldItem, @NonNull VitalDateVise newItem) {
                return oldItem.equals(newItem);
            }
        };
    }
}
