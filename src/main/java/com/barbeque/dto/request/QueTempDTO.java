package com.barbeque.dto.request;

/**
 * Created by System1 on 9/27/2016.
 */
public class QueTempDTO {
    private int id;
    private int questionId;
    private int priority;
    private String questionText;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueTempDTO that = (QueTempDTO) o;

        if (id != that.id) return false;
        if (questionId != that.questionId) return false;
        if (priority != that.priority) return false;
        return questionText != null ? questionText.equals(that.questionText) : that.questionText == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + questionId;
        result = 31 * result + priority;
        result = 31 * result + (questionText != null ? questionText.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QueTempDTO{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", priority=" + priority +
                ", questionText='" + questionText + '\'' +
                '}';
    }
}
