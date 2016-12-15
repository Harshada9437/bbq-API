package com.barbeque.response.feedback;

/**
 * Created by System-2 on 12/14/2016.
 */
public class FeedbackResponse {
    private int id;
    private int outletId;
    private String date;
    private int questionId;
    private int answerId;
    private String answerText;
    private int rating;
    private String tableNo;
    private String billNo;
    private String createdOn;
    private String modifiedOn;

    public int getId() {return id;}

    public int getOutletId() {return outletId;}

    public String getDate() {return date;}

    public int getQuestionId() {return questionId;}

    public int getAnswerId() {return answerId;}

    public String getAnswerText() {return answerText;}

    public int getRating() {return rating;}

    public String getTableNo() {return tableNo;}

    public String getBillNo() {return billNo;}

    public String getCreatedOn() {return createdOn;}

    public String getModifiedOn() {return modifiedOn;}

    public FeedbackResponse(int id, int outletId, String date, int questionId, int answerId, String answerText, int rating, String tableNo, String billNo, String createdOn, String modifiedOn) {
        this.id = id;
        this.outletId = outletId;
        this.date = date;
        this.questionId = questionId;
        this.answerId = answerId;
        this.answerText = answerText;
        this.rating = rating;
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
                ", questionId=" + questionId +
                ", answerId=" + answerId +
                ", answerText='" + answerText + '\'' +
                ", rating=" + rating +
                ", tableNo='" + tableNo + '\'' +
                ", billNo='" + billNo + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", modifiedOn='" + modifiedOn + '\'' +
                '}';
    }
}
