package com.barbeque.request.report;

import com.barbeque.request.feedback.FeedbackDetails;

import java.util.List;

/**
 * Created by System-3 on 2/15/2017.
 */
public class Feedback {
    private int id;
    private String outletDesc;
    private String feedbackDate;
    private String tableNo;
    private List<FeedbackDetails> feedbackDetail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOutletDesc() {
        return outletDesc;
    }

    public void setOutletDesc(String outletDesc) {
        this.outletDesc = outletDesc;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public List<FeedbackDetails> getFeedbackDetail() {
        return feedbackDetail;
    }

    public void setFeedbackDetail(List<FeedbackDetails> feedbackDetail) {
        this.feedbackDetail = feedbackDetail;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", outletDesc='" + outletDesc + '\'' +
                ", feedbackDate='" + feedbackDate + '\'' +
                ", tableNo='" + tableNo + '\'' +
                ", feedbackDetail=" + feedbackDetail +
                '}';
    }
}




