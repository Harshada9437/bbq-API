package com.barbeque.dto.request;

import java.util.List;

/**
 * Created by System-2 on 12/15/2016.
 */
public class AnswerDTO {
    private int id;
    private int questionId;
    private String answerDesc;
    private int rating;
    private int weightage;

    public int getWeightage() {
        return weightage;
    }

    public void setWeightage(int weightage) {
        this.weightage = weightage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswerDesc() {
        return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
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

        AnswerDTO answerDTO = (AnswerDTO) o;

        if (id != answerDTO.id) return false;
        if (questionId != answerDTO.questionId) return false;
        if (rating != answerDTO.rating) return false;
        if (weightage != answerDTO.weightage) return false;
        return answerDesc != null ? answerDesc.equals(answerDTO.answerDesc) : answerDTO.answerDesc == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + questionId;
        result = 31 * result + (answerDesc != null ? answerDesc.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + weightage;
        return result;
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", answerDesc='" + answerDesc + '\'' +
                ", rating=" + rating +
                ", weightage=" + weightage +
                '}';
    }
}
