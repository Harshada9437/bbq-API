package com.barbeque.request.feedback;

/**
 * Created by Sandeep on 1/4/2017.
 */
public class FeedbackDetails {
    private int questionId;
    private int answerId;
    private String answerText;
    private int rating;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackDetails that = (FeedbackDetails) o;

        if (questionId != that.questionId) return false;
        if (answerId != that.answerId) return false;
        if (rating != that.rating) return false;
        return answerText.equals(that.answerText);
    }

    @Override
    public int hashCode() {
        int result = questionId;
        result = 31 * result + answerId;
        result = 31 * result + answerText.hashCode();
        result = 31 * result + rating;
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackDetails{" +
                "questionId=" + questionId +
                ", answerId=" + answerId +
                ", answerText='" + answerText + '\'' +
                ", rating=" + rating +
                '}';
    }
}
