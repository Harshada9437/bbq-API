package com.barbeque.response.report;

/**
 * Created by System-3 on 2/14/2017.
 */
public class AverageResponse {
    private String questionDesc;
    private String answerDesc;
    private float average;

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public String getAnswerDesc() {
        return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    @Override
    public String toString() {
        return "AverageResponse{" +
                "questionDesc='" + questionDesc + '\'' +
                ", answerDesc='" + answerDesc + '\'' +
                ", average=" + average +
                '}';
    }
}
