package com.barbeque.request.feedback;

import com.barbeque.response.feedback.CreateCustomer;

import java.util.List;

/**
 * Created by user on 10/18/2016.
 */
public class FeedbackRequest {
    private int outletId;
    private int deviceId;
    private String date;
    private List<FeedbackDetails> feedbacks;
    private String tableNo;
    private String billNo;
    public CreateCustomer customer;

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public CreateCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(CreateCustomer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackRequest that = (FeedbackRequest) o;

        if (outletId != that.outletId) return false;
        if (deviceId != that.deviceId) return false;
        if (!date.equals(that.date)) return false;
        if (!feedbacks.equals(that.feedbacks)) return false;
        if (!tableNo.equals(that.tableNo)) return false;
        if (!billNo.equals(that.billNo)) return false;
        return customer.equals(that.customer);
    }

    @Override
    public int hashCode() {
        int result = outletId;
        result = 31 * result + deviceId;
        result = 31 * result + date.hashCode();
        result = 31 * result + feedbacks.hashCode();
        result = 31 * result + tableNo.hashCode();
        result = 31 * result + billNo.hashCode();
        result = 31 * result + customer.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackRequest{" +
                "outletId=" + outletId +
                ", deviceId=" + deviceId +
                ", date='" + date + '\'' +
                ", feedbacks=" + feedbacks +
                ", tableNo='" + tableNo + '\'' +
                ", billNo='" + billNo + '\'' +
                ", customer=" + customer +
                '}';
    }
}
