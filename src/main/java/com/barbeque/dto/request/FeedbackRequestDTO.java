package com.barbeque.dto.request;

/**
 * Created by user on 10/18/2016.
 */
public class FeedbackRequestDTO {
    private int id;
    private int customerId;
    private String createdOn;
    private String modifiedOn;
    private int outletId;
    private String date;
    private int questionId;
    private int answerId;
    private String answerText;
    private int rating;
    private String tableNo;
    private String billNo;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getCustomerId() {return customerId;}

    public void setCustomerId(int customerId) {this.customerId = customerId;}

    public String getCreatedOn() {return createdOn;}

    public void setCreatedOn(String createdOn) {this.createdOn = createdOn;}

    public String getModifiedOn() {return modifiedOn;}

    public void setModifiedOn(String modifiedOn) {this.modifiedOn = modifiedOn;}

    public int getOutletId() {return outletId;}

    public void setOutletId(int outletId) {this.outletId = outletId;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public int getQuestionId() {return questionId;}

    public void setQuestionId(int questionId) {this.questionId = questionId;}

    public int getAnswerId() {return answerId;}

    public void setAnswerId(int answerId) {this.answerId = answerId;}

    public String getAnswerText() {return answerText;}

    public void setAnswerText(String answerText) {this.answerText = answerText;}

    public int getRating() {return rating;}

    public void setRating(int rating) {this.rating = rating;}

    public String getTableNo() {return tableNo;}

    public void setTableNo(String tableNo) {this.tableNo = tableNo;}

    public String getBillNo() {return billNo;}

    public void setBillNo(String billNo) {this.billNo = billNo;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackRequestDTO that = (FeedbackRequestDTO) o;

        if (id != that.id) return false;
        if (customerId != that.customerId) return false;
        if (outletId != that.outletId) return false;
        if (questionId != that.questionId) return false;
        if (answerId != that.answerId) return false;
        if (rating != that.rating) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (modifiedOn != null ? !modifiedOn.equals(that.modifiedOn) : that.modifiedOn != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (answerText != null ? !answerText.equals(that.answerText) : that.answerText != null) return false;
        if (tableNo != null ? !tableNo.equals(that.tableNo) : that.tableNo != null) return false;
        return billNo != null ? billNo.equals(that.billNo) : that.billNo == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + customerId;
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (modifiedOn != null ? modifiedOn.hashCode() : 0);
        result = 31 * result + outletId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + questionId;
        result = 31 * result + answerId;
        result = 31 * result + (answerText != null ? answerText.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + (tableNo != null ? tableNo.hashCode() : 0);
        result = 31 * result + (billNo != null ? billNo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackRequestDTO{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", createdOn='" + createdOn + '\'' +
                ", modifiedOn='" + modifiedOn + '\'' +
                ", outletId=" + outletId +
                ", date='" + date + '\'' +
                ", questionId=" + questionId +
                ", answerId=" + answerId +
                ", answerText='" + answerText + '\'' +
                ", rating=" + rating +
                ", tableNo='" + tableNo + '\'' +
                ", billNo='" + billNo + '\'' +
                '}';
    }
}
