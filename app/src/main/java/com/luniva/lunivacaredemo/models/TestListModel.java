package com.luniva.lunivacaredemo.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestListModel {

@SerializedName("PatientBillDetails")
@Expose
private List<PatientBillDetail> patientBillDetails = null;

public List<PatientBillDetail> getPatientBillDetails() {
return patientBillDetails;
}

public void setPatientBillDetails(List<PatientBillDetail> patientBillDetails) {
this.patientBillDetails = patientBillDetails;
}
    public class PatientBillDetail {

        @SerializedName("BillID")
        @Expose
        private Integer billID;
        @SerializedName("BillNo")
        @Expose
        private String billNo;
        @SerializedName("billTestName")
        @Expose
        private String billTestName;
        @SerializedName("billPrice")
        @Expose
        private Integer billPrice;
        @SerializedName("IsPaid")
        @Expose
        private Boolean isPaid;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("Nrl_Reg_No")
        @Expose
        private String nrlRegNo;
        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("billOutGoing")
        @Expose
        private Boolean billOutGoing;
        @SerializedName("RefName")
        @Expose
        private String refName;
        @SerializedName("ContactNo")
        @Expose
        private String contactNo;
        @SerializedName("BillDiscount")
        @Expose
        private Integer billDiscount;
        @SerializedName("BillDiscountAmount")
        @Expose
        private Integer billDiscountAmount;
        @SerializedName("BillPriceFinal")
        @Expose
        private Integer billPriceFinal;
        @SerializedName("RoundAmount")
        @Expose
        private Integer roundAmount;
        @SerializedName("Remarks")
        @Expose
        private Object remarks;
        @SerializedName("TestId")
        @Expose
        private Integer testId;
        @SerializedName("TestType")
        @Expose
        private Integer testType;

        public Integer getBillID() {
            return billID;
        }

        public void setBillID(Integer billID) {
            this.billID = billID;
        }

        public String getBillNo() {
            return billNo;
        }

        public void setBillNo(String billNo) {
            this.billNo = billNo;
        }

        public String getBillTestName() {
            return billTestName;
        }

        public void setBillTestName(String billTestName) {
            this.billTestName = billTestName;
        }

        public Integer getBillPrice() {
            return billPrice;
        }

        public void setBillPrice(Integer billPrice) {
            this.billPrice = billPrice;
        }

        public Boolean getIsPaid() {
            return isPaid;
        }

        public void setIsPaid(Boolean isPaid) {
            this.isPaid = isPaid;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getNrlRegNo() {
            return nrlRegNo;
        }

        public void setNrlRegNo(String nrlRegNo) {
            this.nrlRegNo = nrlRegNo;
        }

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public Boolean getBillOutGoing() {
            return billOutGoing;
        }

        public void setBillOutGoing(Boolean billOutGoing) {
            this.billOutGoing = billOutGoing;
        }

        public String getRefName() {
            return refName;
        }

        public void setRefName(String refName) {
            this.refName = refName;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public Integer getBillDiscount() {
            return billDiscount;
        }

        public void setBillDiscount(Integer billDiscount) {
            this.billDiscount = billDiscount;
        }

        public Integer getBillDiscountAmount() {
            return billDiscountAmount;
        }

        public void setBillDiscountAmount(Integer billDiscountAmount) {
            this.billDiscountAmount = billDiscountAmount;
        }

        public Integer getBillPriceFinal() {
            return billPriceFinal;
        }

        public void setBillPriceFinal(Integer billPriceFinal) {
            this.billPriceFinal = billPriceFinal;
        }

        public Integer getRoundAmount() {
            return roundAmount;
        }

        public void setRoundAmount(Integer roundAmount) {
            this.roundAmount = roundAmount;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public Integer getTestId() {
            return testId;
        }

        public void setTestId(Integer testId) {
            this.testId = testId;
        }

        public Integer getTestType() {
            return testType;
        }

        public void setTestType(Integer testType) {
            this.testType = testType;
        }

    }
}