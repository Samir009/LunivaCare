package com.luniva.lunivacaredemo.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientListByIdModel {

@SerializedName("PatientInfo")
@Expose
private List<PatientInfo> patientInfo = null;

public List<PatientInfo> getPatientInfo() {
return patientInfo;
}

public void setPatientInfo(List<PatientInfo> patientInfo) {
this.patientInfo = patientInfo;
}

    public class PatientInfo {

        @SerializedName("ReportType")
        @Expose
        private String reportType;
        @SerializedName("Nrl_Reg_No")
        @Expose
        private String nrlRegNo;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("Age")
        @Expose
        private String age;
        @SerializedName("ResultDate")
        @Expose
        private String resultDate;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Sex")
        @Expose
        private String sex;
        @SerializedName("InvoiceNumber")
        @Expose
        private String invoiceNumber;
        @SerializedName("Requestor")
        @Expose
        private String requestor;
        @SerializedName("Age1")
        @Expose
        private String age1;
        @SerializedName("Sex1")
        @Expose
        private String sex1;
        @SerializedName("ContactNo")
        @Expose
        private String contactNo;
        @SerializedName("BillPaymentType")
        @Expose
        private String billPaymentType;
        @SerializedName("BillRemainingAmt")
        @Expose
        private Integer billRemainingAmt;
        @SerializedName("MemberCode")
        @Expose
        private String memberCode;
        @SerializedName("BillIsVoid")
        @Expose
        private Boolean billIsVoid;

        public String getReportType() {
            return reportType;
        }

        public void setReportType(String reportType) {
            this.reportType = reportType;
        }

        public String getNrlRegNo() {
            return nrlRegNo;
        }

        public void setNrlRegNo(String nrlRegNo) {
            this.nrlRegNo = nrlRegNo;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getResultDate() {
            return resultDate;
        }

        public void setResultDate(String resultDate) {
            this.resultDate = resultDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getInvoiceNumber() {
            return invoiceNumber;
        }

        public void setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
        }

        public String getRequestor() {
            return requestor;
        }

        public void setRequestor(String requestor) {
            this.requestor = requestor;
        }

        public String getAge1() {
            return age1;
        }

        public void setAge1(String age1) {
            this.age1 = age1;
        }

        public String getSex1() {
            return sex1;
        }

        public void setSex1(String sex1) {
            this.sex1 = sex1;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getBillPaymentType() {
            return billPaymentType;
        }

        public void setBillPaymentType(String billPaymentType) {
            this.billPaymentType = billPaymentType;
        }

        public Integer getBillRemainingAmt() {
            return billRemainingAmt;
        }

        public void setBillRemainingAmt(Integer billRemainingAmt) {
            this.billRemainingAmt = billRemainingAmt;
        }

        public String getMemberCode() {
            return memberCode;
        }

        public void setMemberCode(String memberCode) {
            this.memberCode = memberCode;
        }

        public Boolean getBillIsVoid() {
            return billIsVoid;
        }

        public void setBillIsVoid(Boolean billIsVoid) {
            this.billIsVoid = billIsVoid;
        }

    }
}

