package com.luniva.lunivacaredemo.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientListModel {

@SerializedName("PatientList")
@Expose
private List<PatientList> patientList = null;

public List<PatientList> getPatientList() {
return patientList;
}

public void setPatientList(List<PatientList> patientList) {
this.patientList = patientList;
}

    public class PatientList {

        @SerializedName("rownum")
        @Expose
        private Integer rownum;
        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("MemberID")
        @Expose
        private Object memberID;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("MiddleName")
        @Expose
        private String middleName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("Sex")
        @Expose
        private String sex;
        @SerializedName("Age")
        @Expose
        private String age;
        @SerializedName("ContactNo")
        @Expose
        private String contactNo;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("ReportDone")
        @Expose
        private Boolean reportDone;
        @SerializedName("IsTaken")
        @Expose
        private Boolean isTaken;
        @SerializedName("IsPaid")
        @Expose
        private Boolean isPaid;
        @SerializedName("DueAmount")
        @Expose
        private Double dueAmount;
        @SerializedName("ReportType")
        @Expose
        private String reportType;
        @SerializedName("BillIsVoid")
        @Expose
        private Boolean billIsVoid;
        @SerializedName("TotalCount")
        @Expose
        private Integer totalCount;
        @SerializedName("RequestAccepted")
        @Expose
        private Boolean isAccepted;
        @SerializedName("RequestRejected")
        @Expose
        private Boolean isRejected;

        public Integer getRownum() {
            return rownum;
        }

        public void setRownum(Integer rownum) {
            this.rownum = rownum;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Object getMemberID() {
            return memberID;
        }

        public void setMemberID(Object memberID) {
            this.memberID = memberID;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Boolean getReportDone() {
            return reportDone;
        }

        public void setReportDone(Boolean reportDone) {
            this.reportDone = reportDone;
        }

        public Boolean getIsTaken() {
            return isTaken;
        }

        public void setIsTaken(Boolean isTaken) {
            this.isTaken = isTaken;
        }

        public Boolean getIsPaid() {
            return isPaid;
        }

        public void setIsPaid(Boolean isPaid) {
            this.isPaid = isPaid;
        }

        public Double getDueAmount() {
            return dueAmount;
        }

        public void setDueAmount(Double dueAmount) {
            this.dueAmount = dueAmount;
        }

        public String getReportType() {
            return reportType;
        }

        public void setReportType(String reportType) {
            this.reportType = reportType;
        }

        public Boolean getBillIsVoid() {
            return billIsVoid;
        }

        public void setBillIsVoid(Boolean billIsVoid) {
            this.billIsVoid = billIsVoid;
        }

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public Boolean getAccepted() {
            return isAccepted;
        }

        public void setAccepted(Boolean accepted) {
            isAccepted = accepted;
        }

        public Boolean getRejected() {
            return isRejected;
        }

        public void setRejected(Boolean rejected) {
            isRejected = rejected;
        }
    }
}