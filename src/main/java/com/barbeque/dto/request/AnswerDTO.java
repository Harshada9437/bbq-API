package com.barbeque.dto.request;

/**
 * Created by System-2 on 12/15/2016.
 */
public class AnswerDTO {
    private int id;
    private int questionId;
    private String answerText;
    private String answerDesc;
    private String questionDesc;
    private String threshold;
    private int rating;
    private int weightage;

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

        AnswerDTO answerDTO = (AnswerDTO) o;

        if (id != answerDTO.id) return false;
        if (questionId != answerDTO.questionId) return false;
        if (rating != answerDTO.rating) return false;
        if (weightage != answerDTO.weightage) return false;
        if (answerText != null ? !answerText.equals(answerDTO.answerText) : answerDTO.answerText != null) return false;
        if (threshold != null ? !threshold.equals(answerDTO.threshold) : answerDTO.threshold != null) return false;
        if (answerDesc != null ? !answerDesc.equals(answerDTO.answerDesc) : answerDTO.answerDesc != null) return false;
        return questionDesc != null ? questionDesc.equals(answerDTO.questionDesc) : answerDTO.questionDesc == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + questionId;
        result = 31 * result + (answerText != null ? answerText.hashCode() : 0);
        result = 31 * result + (answerDesc != null ? answerDesc.hashCode() : 0);
        result = 31 * result + (questionDesc != null ? questionDesc.hashCode() : 0);
        result = 31 * result + (threshold != null ? threshold.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + weightage;
        return result;
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", answerText='" + answerText + '\'' +
                ", answerDesc='" + answerDesc + '\'' +
                ", questionDesc='" + questionDesc + '\'' +
                ", threshold='" + threshold + '\'' +
                ", rating=" + rating +
                ", weightage=" + weightage +
                '}';
    }
}
