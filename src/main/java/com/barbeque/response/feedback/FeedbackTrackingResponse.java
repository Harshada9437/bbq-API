package com.barbeque.response.feedback;

/**
 * Created by System-3 on 2/17/2017.
 */
public class FeedbackTrackingResponse {
    private int feedbackId;
    private int outletId;
    private String outletName;
    private String date;
    private String tableNo;
    private int customerId;
    private String customerName;
    private String phoneNo;
    private String email;
    private String mgrName;
    private String mgrMobileNo;
    private String mgrEmail;
    private String fistViewDate;
    private int viewCount;
    private int isAddressed;
    private int isNegative;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIsNegative(int isNegative) {
        this.isNegative = isNegative;
    }

    public int getIsNegative() {
        return isNegative;
    }

    public int getIsAddressed() {
        return isAddressed;
    }

    public void setIsAddressed(int isAddressed) {
        this.isAddressed = isAddressed;
    }

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

    public FeedbackTrackingResponse(int feedbackId, int outletId, String outletName,String email, String date, String tableNo, int customerId, String customerName, String phoneNo, String mgrName, String mgrMobileNo, String mgrEmail, String fistViewDate, int viewCount, int isAddressed, int isNegative) {
        this.feedbackId = feedbackId;
        this.outletId = outletId;
        this.outletName = outletName;
        this.email = email;
        this.date = date;
        this.tableNo = tableNo;
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNo = phoneNo;
        this.mgrName = mgrName;
        this.mgrMobileNo = mgrMobileNo;
        this.mgrEmail = mgrEmail;
        this.fistViewDate = fistViewDate;
        this.viewCount = viewCount;
        this.isAddressed = isAddressed;
        this.isNegative = isNegative;
    }

    @Override
    public String toString() {
        return "FeedbackTrackingResponse{" +
                "feedbackId=" + feedbackId +
                ", outletId=" + outletId +
                ", isAddressed=" + isAddressed +
                ", isNegative=" + isNegative +
                ", outletName='" + outletName + '\'' +
                ", date='" + date + '\'' +
                ", tableNo='" + tableNo + '\'' +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                ", mgrName='" + mgrName + '\'' +
                ", mgrMobileNo='" + mgrMobileNo + '\'' +
                ", mgrEmail='" + mgrEmail + '\'' +
                ", fistViewDate='" + fistViewDate + '\'' +
                ", viewCount=" + viewCount +
                '}';
    }
}

