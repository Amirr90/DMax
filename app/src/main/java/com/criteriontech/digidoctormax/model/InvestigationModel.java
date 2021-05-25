package com.criteriontech.digidoctormax.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.criteriontech.digidoctormax.utils.AppUtils;

import java.util.List;
import java.util.Objects;

public class InvestigationModel {

    private String type;
    private String receiptNo;
    private String pathologyName;
    private String testDate;
    private String filePath;
    private List<Investigation> investigation;
    public String dateFormat;

    public String getDateFormat() {
        return AppUtils.parseDate(testDate, "dd MMMM yyyy");
    }

    public String getType() {
        return type;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public String getPathologyName() {
        return pathologyName;
    }

    public String getTestDate() {
        return AppUtils.parseDate(testDate, "dd MMMM yyyy");
    }

    public String getFilePath() {
        return filePath;
    }

    public List<Investigation> getInvestigation() {
        return investigation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvestigationModel)) return false;
        InvestigationModel that = (InvestigationModel) o;
        return Objects.equals(getType(), that.getType()) &&
                Objects.equals(getReceiptNo(), that.getReceiptNo()) &&
                Objects.equals(getPathologyName(), that.getPathologyName()) &&
                Objects.equals(getTestDate(), that.getTestDate()) &&
                Objects.equals(getFilePath(), that.getFilePath()) &&
                Objects.equals(getInvestigation(), that.getInvestigation());
    }

    @Override
    public String toString() {
        return "{" +
                "type='" + type + '\'' +
                ", receiptNo='" + receiptNo + '\'' +
                ", pathologyName='" + pathologyName + '\'' +
                ", testDate='" + testDate + '\'' +
                ", filePath='" + filePath + '\'' +
                ", investigation=" + investigation +
                '}';
    }


    public static DiffUtil.ItemCallback<InvestigationModel> itemCallback = new DiffUtil.ItemCallback<InvestigationModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull InvestigationModel oldItem, @NonNull InvestigationModel newItem) {
            return oldItem.getPathologyName().equalsIgnoreCase(newItem.getPathologyName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull InvestigationModel oldItem, @NonNull InvestigationModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getReceiptNo(), getPathologyName(), getTestDate(), getFilePath(), getInvestigation());
    }

    public class Investigation {


        private String testName;


        private List<TestDetailsModel> testDetails;

        public List<TestDetailsModel> getTestDetails() {
            return testDetails;
        }


        public class TestDetailsModel {
            private String subTestName;
            private String testValue;
            private String range;
            private String unitName;
            private String testRemarks;
            private String subTestId;

            public String getSubTestId() {
                return subTestId;
            }

            public String getSubTestName() {
                return subTestName;
            }

            public String getTestValue() {
                return testValue;
            }

            public String getRange() {
                return range;
            }

            public String getUnitName() {
                return unitName;
            }

            public String getTestRemarks() {
                return testRemarks;
            }

            @Override
            public String toString() {
                return "{" +
                        "subTestName='" + subTestName + '\'' +
                        ", testValue='" + testValue + '\'' +
                        ", range='" + range + '\'' +
                        ", unitName='" + unitName + '\'' +
                        ", testRemarks='" + testRemarks + '\'' +
                        ", subTestId='" + subTestId + '\'' +
                        '}';
            }
        }

        public String getTestName() {
            return testName;
        }


        @Override
        public String toString() {
            return "{" +
                    "testName='" + testName + '\'' +
                    ", testDetails=" + testDetails +
                    '}';
        }
    }
}
