package com.barbeque.request.feedback;

/**
 * Created by System-2 on 12/14/2016.
 */
public class UpdateFeedbackRequest {
    private int id;
    private String date;
    private int questionId;
    private int answerId;
    private String answerText;
    private int rating;
    private String tableNo;
    private String billNo;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

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

        UpdateFeedbackRequest that = (UpdateFeedbackRequest) o;

        if (id != that.id) return false;
        if (questionId != that.questionId) return false;
        if (answerId != that.answerId) return false;
        if (rating != that.rating) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (answerText != null ? !answerText.equals(that.answerText) : that.answerText != null) return false;
        if (tableNo != null ? !tableNo.equals(that.tableNo) : that.tableNo != null) return false;
        return billNo != null ? billNo.equals(that.billNo) : that.billNo == null;

    }

    @Override
    public int hashCode() {
        int result = id;
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
        return "UpdateFeedbackRequest{" +
                "id=" + id +
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
