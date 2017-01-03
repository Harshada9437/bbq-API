package com.barbeque.response.feedback;

import com.barbeque.request.feedback.FeedbackDetails;

import java.util.List;

/**
 * Created by System-2 on 12/14/2016.
 */
public class FeedbackResponse {
    private int id;
    private int outletId;
    private String date;
    private List<FeedbackDetails> feedbacks;
    private String tableNo;
    private String billNo;
    private String createdOn;
    private String modifiedOn;

    public int getId() {return id;}

    public int getOutletId() {return outletId;}

    public String getDate() {return date;}

    public List<FeedbackDetails> getFeedbacks() {
        return feedbacks;
    }

    public String getTableNo() {return tableNo;}

    public String getBillNo() {return billNo;}

    public String getCreatedOn() {return createdOn;}

    public String getModifiedOn() {return modifiedOn;}

    public FeedbackResponse(int id, int outletId, String date, List<FeedbackDetails> feedbacks, String tableNo, String billNo, String createdOn, String modifiedOn) {
        this.id = id;
        this.outletId = outletId;
        this.date = date;
        this.feedbacks = feedbacks;
        this.tableNo = tableNo;
        this.billNo = billNo;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }

    @Override
    public String toString() {
        return "FeedbackResponse{" +
                "id=" + id +
                ", outletId=" + outletId +
                ", date='" + date + '\'' +
                ", feedbacks=" + feedbacks +
                ", tableNo='" + tableNo + '\'' +
                ", billNo='" + billNo + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", modifiedOn='" + modifiedOn + '\'' +
                '}';
    }
}
