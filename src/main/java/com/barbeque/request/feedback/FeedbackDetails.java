package com.barbeque.request.feedback;

/**
 * Created by Sandeep on 1/4/2017.
 */
public class FeedbackDetails {
    private int questionId;
    private int id;
    private int weightage;
    private char questionType;
    private int answerId;
    private String answerText;
    private String threshold;
    private String answerDesc;
    private String questionDesc;
    private int rating;
    private int isNegative;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeightage() {
        return weightage;
    }

    public void setWeightage(int weightage) {
        this.weightage = weightage;
    }

    public char getQuestionType() {
        return questionType;
    }

    public void setQuestionType(char questionType) {
        this.questionType = questionType;
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

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getIsNegative() {
        return isNegative;
    }

    public void setIsNegative(int isNegative) {
        this.isNegative = isNegative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackDetails that = (FeedbackDetails) o;

        if (questionId != that.questionId) return false;
        if (id != that.id) return false;
        if (weightage != that.weightage) return false;
        if (questionType != that.questionType) return false;
        if (answerId != that.answerId) return false;
        if (rating != that.rating) return false;
        if (isNegative != that.isNegative) return false;
        if (!answerText.equals(that.answerText)) return false;
        if (!threshold.equals(that.threshold)) return false;
        if (!answerDesc.equals(that.answerDesc)) return false;
        return questionDesc.equals(that.questionDesc);
    }

    @Override
    public int hashCode() {
        int result = questionId;
        result = 31 * result + id;
        result = 31 * result + weightage;
        result = 31 * result + (int) questionType;
        result = 31 * result + answerId;
        result = 31 * result + answerText.hashCode();
        result = 31 * result + threshold.hashCode();
        result = 31 * result + answerDesc.hashCode();
        result = 31 * result + questionDesc.hashCode();
        result = 31 * result + rating;
        result = 31 * result + isNegative;
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackDetails{" +
                "questionId=" + questionId +
                ", id=" + id +
                ", weightage=" + weightage +
                ", questionType=" + questionType +
                ", answerId=" + answerId +
                ", answerText='" + answerText + '\'' +
                ", threshold='" + threshold + '\'' +
                ", answerDesc='" + answerDesc + '\'' +
                ", questionDesc='" + questionDesc + '\'' +
                ", rating=" + rating +
                ", isNegative=" + isNegative +
                '}';
    }
}
