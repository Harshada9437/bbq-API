package com.barbeque.bo;

import com.barbeque.request.feedback.FeedbackDetails;

import java.util.List;

/**
 * Created by user on 10/18/2016.
 */
public class FeedbackRequestBO {
    private int outletId;
    private String date;
    private List<FeedbackDetails> feedbacks;
    private String tableNo;
    private String billNo;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackRequestBO that = (FeedbackRequestBO) o;

        if (outletId != that.outletId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (feedbacks != null ? !feedbacks.equals(that.feedbacks) : that.feedbacks != null) return false;
        if (tableNo != null ? !tableNo.equals(that.tableNo) : that.tableNo != null) return false;
        return billNo != null ? billNo.equals(that.billNo) : that.billNo == null;
    }

    @Override
    public int hashCode() {
        int result = outletId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (feedbacks != null ? feedbacks.hashCode() : 0);
        result = 31 * result + (tableNo != null ? tableNo.hashCode() : 0);
        result = 31 * result + (billNo != null ? billNo.hashCode() : 0);
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
                '}';
    }
}
