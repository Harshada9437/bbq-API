package com.barbeque.response.Answer;

/**
 * Created by System-2 on 12/19/2016.
 */
public class AnswerResponseList
{
    private int questionId;
    private String answerDesc;
    private int rating;
    private String description;
    private int answer_id;

    public AnswerResponseList(int questionId, String answerDesc, int rating, String description, int answer_id) {
        this.questionId = questionId;
        this.answerDesc = answerDesc;
        this.rating = rating;
        this.description = description;
        this.answer_id = answer_id;
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

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerResponseList that = (AnswerResponseList) o;

        if (questionId != that.questionId) return false;
        if (rating != that.rating) return false;
        if (answer_id != that.answer_id) return false;
        if (answerDesc != null ? !answerDesc.equals(that.answerDesc) : that.answerDesc != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = questionId;
        result = 31 * result + (answerDesc != null ? answerDesc.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + answer_id;
        return result;
    }

    @Override
    public String toString() {
        return "AnswerResponseList{" +
                "questionId=" + questionId +
                ", answerDesc='" + answerDesc + '\'' +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", answer_id=" + answer_id +
                '}';
    }
}
