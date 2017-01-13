package com.barbeque.response.feedback;

import com.barbeque.request.feedback.FeedbackDetails;

import java.util.List;

/**
 * Created by System-2 on 12/14/2016.
 */
public class FeedbackResponse {
    private int id;
    private int customerId;
    private String createdOn;
    private int outletId;
    private String date;
    private String answerDesc;
    private String answerText;
    private String questionDesc;
    private int rating;
    private int answerId;
    private int questionId;
    private String tableNo;
    private String billNo;
    private String customerName;
    private String outletDesc;
    private String mobileNo;

    public FeedbackResponse(int id, int customerId, String createdOn, int outletId, String date, String answerDesc, String answerText, String questionDesc, int rating, int answerId, int questionId, String tableNo, String billNo, String customerName, String outletDesc, String mobileNo) {
        this.id = id;
        this.customerId = customerId;
        this.createdOn = createdOn;
        this.outletId = outletId;
        this.date = date;
        this.answerDesc = answerDesc;
        this.answerText = answerText;
        this.questionDesc = questionDesc;
        this.rating = rating;
        this.answerId = answerId;
        this.questionId = questionId;
        this.tableNo = tableNo;
        this.billNo = billNo;
        this.customerName = customerName;
        this.outletDesc = outletDesc;
        this.mobileNo = mobileNo;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public int getOutletId() {
        return outletId;
    }

    public String getDate() {
        return date;
    }

    public String getAnswerDesc() {
        return answerDesc;
    }

    public String getAnswerText() {
        return answerText;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public int getRating() {
        return rating;
    }

    public int getAnswerId() {
        return answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getTableNo() {
        return tableNo;
    }

    public String getBillNo() {
        return billNo;
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

    @Override
    public String toString() {
        return "FeedbackResponse{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", createdOn='" + createdOn + '\'' +
                ", outletId=" + outletId +
                ", date='" + date + '\'' +
                ", answerDesc='" + answerDesc + '\'' +
                ", answerText='" + answerText + '\'' +
                ", questionDesc='" + questionDesc + '\'' +
                ", rating=" + rating +
                ", answerId=" + answerId +
                ", questionId=" + questionId +
                ", tableNo='" + tableNo + '\'' +
                ", billNo='" + billNo + '\'' +
                ", customerName='" + customerName + '\'' +
                ", outletDesc='" + outletDesc + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                '}';
    }
}
