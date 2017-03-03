package com.barbeque.dto.request;

/**
 * Created by System-3 on 2/17/2017.
 */
public class FeedbackTrackingResponseDTO {

    private int feedbackId;
    private int outletId;
    private String outletName;
    private String date;
    private String tableNo;
    private int customerId;
    private String customerName;
    private String phoneNo;
    private String mgrName;
    private String mgrMobileNo;
    private String mgrEmail;
    private String fistViewDate;
    private int viewCount;
    private int isAddressed;
    private int isNegative;


    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    public String getMgrMobileNo() {
        return mgrMobileNo;
    }

    public void setMgrMobileNo(String mgrMobileNo) {
        this.mgrMobileNo = mgrMobileNo;
    }

    public String getMgrEmail() {
        return mgrEmail;
    }

    public void setMgrEmail(String mgrEmail) {
        this.mgrEmail = mgrEmail;
    }

    public String getFistViewDate() {
        return fistViewDate;
    }

    public void setFistViewDate(String fistViewDate) {
        this.fistViewDate = fistViewDate;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getIsAddressed() {
        return isAddressed;
    }

    public void setIsAddressed(int isAddressed) {
        this.isAddressed = isAddressed;
    }

    public int getIsNegative() {
        return isNegative;
    }

    public void setIsNegative(int isNegative) {
        this.isNegative = isNegative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackTrackingResponseDTO that = (FeedbackTrackingResponseDTO) o;

        if (feedbackId != that.feedbackId) return false;
        if (outletId != that.outletId) return false;
        if (isNegative != that.isNegative) return false;
        if (customerId != that.customerId) return false;
        if (viewCount != that.viewCount) return false;
        if (isAddressed != that.isAddressed) return false;
        if (!outletName.equals(that.outletName)) return false;
        if (!date.equals(that.date)) return false;
        if (!tableNo.equals(that.tableNo)) return false;
        if (!customerName.equals(that.customerName)) return false;
        if (!phoneNo.equals(that.phoneNo)) return false;
        if (!mgrName.equals(that.mgrName)) return false;
        if (!mgrMobileNo.equals(that.mgrMobileNo)) return false;
        if (!mgrEmail.equals(that.mgrEmail)) return false;
        return fistViewDate.equals(that.fistViewDate);
    }

    @Override
    public int hashCode() {
        int result = feedbackId;
        result = 31 * result + outletId;
        result = 31 * result + isNegative;
        result = 31 * result + outletName.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + tableNo.hashCode();
        result = 31 * result + customerId;
        result = 31 * result + customerName.hashCode();
        result = 31 * result + phoneNo.hashCode();
        result = 31 * result + mgrName.hashCode();
        result = 31 * result + mgrMobileNo.hashCode();
        result = 31 * result + mgrEmail.hashCode();
        result = 31 * result + fistViewDate.hashCode();
        result = 31 * result + viewCount;
        result = 31 * result + isAddressed;
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackTrackingResponseDTO{" +
                "feedbackId=" + feedbackId +
                ", outletId=" + outletId +
                ", isNegative=" + isNegative +
                ", outletName='" + outletName + '\'' +
                ", date='" + date + '\'' +
                ", tableNo='" + tableNo + '\'' +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", mgrName='" + mgrName + '\'' +
                ", mgrMobileNo='" + mgrMobileNo + '\'' +
                ", mgrEmail='" + mgrEmail + '\'' +
                ", fistViewDate='" + fistViewDate + '\'' +
                ", viewCount=" + viewCount +
                ", isAddressed=" + isAddressed +
                '}';
    }
}
