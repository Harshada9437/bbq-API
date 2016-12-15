package com.barbeque.request.bo;

import com.barbeque.request.answer.AnswerRequest;

/**
 * Created by System-2 on 12/15/2016.
 */
public class AnswerRequestBO {
    private int questionId;
    private String answerDesc;
    private int rating;

    public int getQuestionId() {return questionId;}

    public void setQuestionId(int questionId) {this.questionId = questionId;}

    public String getAnswerDesc() {return answerDesc;}

    public void setAnswerDesc(String answerDesc) {this.answerDesc = answerDesc;}

    public int getRating() {return rating;}

    public void setRating(int rating) {this.rating = rating;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerRequestBO that = (AnswerRequestBO) o;

        if (questionId != that.questionId) return false;
        if (rating != that.rating) return false;
        return answerDesc != null ? answerDesc.equals(that.answerDesc) : that.answerDesc == null;

    }

    @Override
    public int hashCode() {
        int result = questionId;
        result = 31 * result + (answerDesc != null ? answerDesc.hashCode() : 0);
        result = 31 * result + rating;
        return result;
    }

    @Override
    public String toString() {
        return "AnswerRequestBO{" +
                "questionId=" + questionId +
                ", answerDesc='" + answerDesc + '\'' +
                ", rating=" + rating +
                '}';
    }
}
