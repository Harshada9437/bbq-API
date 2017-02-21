package com.barbeque.response.feedback;

import com.barbeque.request.feedback.FeedbackDetails;

import java.util.List;

/**
 * Created by System-2 on 12/14/2016.
 */
public class FeedbackResponse {
    private int id;
    private int customerId;
    private String feedbackDate;
    private int outletId;
    private List<FeedbackDetails> feedbacks;
    private String tableNo;
    private String billNo;
    private String customerName;
    private String outletDesc;
    private String mobileNo;
    private String email;
    private String dob;
    private String doa;
    private String locality;
    private int isAddressed;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }

    public List<FeedbackDetails> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackDetails> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOutletDesc() {
        return outletDesc;
    }

    public void setOutletDesc(String outletDesc) {
        this.outletDesc = outletDesc;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoa() {
        return doa;
    }

    public void setDoa(String doa) {
        this.doa = doa;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public int getIsAddressed() {
        return isAddressed;
    }

    public void setIsAddressed(int isAddressed) {
        this.isAddressed = isAddressed;
    }

    public FeedbackResponse(int id, int customerId, String feedbackDate, int outletId, String tableNo, String billNo, String customerName, String outletDesc, String mobileNo, String email, String dob, String doa, String locality, int isAddressed) {
        this.id = id;
        this.customerId = customerId;
        this.feedbackDate = feedbackDate;
        this.outletId = outletId;
        this.tableNo = tableNo;
        this.billNo = billNo;
        this.customerName = customerName;
        this.outletDesc = outletDesc;
        this.mobileNo = mobileNo;
        this.email = email;
        this.dob = dob;
        this.doa = doa;
        this.locality = locality;
        this.isAddressed = isAddressed;
    }

    @Override
    public String toString() {
        return "FeedbackResponse{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", feedbackDate='" + feedbackDate + '\'' +
                ", outletId=" + outletId +
                ", feedbacks=" + feedbacks +
                ", tableNo='" + tableNo + '\'' +
                ", billNo='" + billNo + '\'' +
                ", customerName='" + customerName + '\'' +
                ", outletDesc='" + outletDesc + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", doa='" + doa + '\'' +
                ", locality='" + locality + '\'' +
                ", isAddressed=" + isAddressed +
                '}';
    }
}
