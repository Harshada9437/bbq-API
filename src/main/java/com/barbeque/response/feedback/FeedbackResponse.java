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

    public String getLocality() {
        return locality;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getDoa() {
        return doa;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getOutletDesc() {
        return outletDesc;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public int getId() {
        return id;
    }

    public int getOutletId() {
        return outletId;
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

    public String getBillNo() {
        return billNo;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public FeedbackResponse(int id, String feedbackDate, int outletId, String tableNo, String billNo, String outletDesc,int customerId, String customerName, String mobileNo, String email, String dob, String doa, String locality) {
        this.id = id;
        this.feedbackDate = feedbackDate;
        this.outletId = outletId;
        this.tableNo = tableNo;
        this.billNo = billNo;
        this.outletDesc = outletDesc;
        this.customerId = customerId;
        this.customerName = customerName;
        this.mobileNo = mobileNo;
        this.email = email;
        this.dob = dob;
        this.doa = doa;
        this.locality = locality;
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
                '}';
    }
}
