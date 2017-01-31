package com.barbeque.request.feedback;

/**
 * Created by Sandeep on 1/4/2017.
 */
public class FeedbackDetails {
    private int questionId;
    private char questionType;
    private int answerId;
    private String answerText;
    private String answerDesc;
    private String questionDesc;
    private int rating;
    private String feedbackDate;

    public char getQuestionType() {
        return questionType;
    }

    public void setQuestionType(char questionType) {
        this.questionType = questionType;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getAnswerDesc() {
        return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "FeedbackDetails{" +
                "questionId=" + questionId +
                ", questionType='" + questionType + '\'' +
                ", answerId=" + answerId +
                ", answerText='" + answerText + '\'' +
                ", answerDesc='" + answerDesc + '\'' +
                ", questionDesc='" + questionDesc + '\'' +
                ", rating=" + rating +
                ", feedbackDate='" + feedbackDate + '\'' +
                '}';
    }
}
