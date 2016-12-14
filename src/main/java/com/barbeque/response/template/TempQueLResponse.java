package com.barbeque.response.template;

/**
 * Created by System1 on 9/27/2016.
 */
public class TempQueLResponse {
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

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

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

    public TempQueLResponse(int id, int questionId, int priority, String questionText) {
        this.id = id;
        this.questionId = questionId;
        this.priority = priority;
        this.questionText = questionText;
    }

    @Override
    public String toString() {
        return "TempQueLResponse{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", priority=" + priority +
                ", questionText='" + questionText + '\'' +
                '}';
    }
}