package com.barbeque.bo;

import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.response.feedback.CreateCustomer;

import java.util.List;

/**
 * Created by user on 10/18/2016.
 */
public class FeedbackRequestBO {
    private int outletId;
    private int deviceId;
    private String date;
    private List<FeedbackDetails> feedbacks;
    private String tableNo;
    private String billNo;
    public CreateCustomer customer;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getOutletId() {return outletId;}

    public void setOutletId(int outletId) {this.outletId = outletId;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getTableNo() {return tableNo;}

    public void setTableNo(String tableNo) {this.tableNo = tableNo;}

    public String getBillNo() {return billNo;}

    public void setBillNo(String billNo) {this.billNo = billNo;}

    public List<FeedbackDetails> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackDetails> feedbacks) {
        this.feedbacks = feedbacks;
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

        FeedbackRequestBO that = (FeedbackRequestBO) o;

        if (outletId != that.outletId) return false;
        if (deviceId != that.deviceId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (feedbacks != null ? !feedbacks.equals(that.feedbacks) : that.feedbacks != null) return false;
        if (tableNo != null ? !tableNo.equals(that.tableNo) : that.tableNo != null) return false;
        if (billNo != null ? !billNo.equals(that.billNo) : that.billNo != null) return false;
        return customer != null ? customer.equals(that.customer) : that.customer == null;
    }

    @Override
    public int hashCode() {
        int result = outletId;
        result = 31 * result + deviceId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (feedbacks != null ? feedbacks.hashCode() : 0);
        result = 31 * result + (tableNo != null ? tableNo.hashCode() : 0);
        result = 31 * result + (billNo != null ? billNo.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackRequestBO{" +
                "outletId=" + outletId +
                ", date='" + date + '\'' +
                ", feedbacks=" + feedbacks +
                ", tableNo='" + tableNo + '\'' +
                ", billNo='" + billNo + '\'' +
                ", deviceId=" + deviceId +
                ", customer=" + customer +
                '}';
    }
}
