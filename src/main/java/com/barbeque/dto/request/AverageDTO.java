package com.barbeque.dto.request;

/**
 * Created by System-3 on 2/14/2017.
 */
public class AverageDTO {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AverageDTO that = (AverageDTO) o;

        if (Float.compare(that.average, average) != 0) return false;
        if (!questionDesc.equals(that.questionDesc)) return false;
        return answerDesc.equals(that.answerDesc);
    }

    @Override
    public int hashCode() {
        int result = questionDesc.hashCode();
        result = 31 * result + answerDesc.hashCode();
        result = 31 * result + (average != +0.0f ? Float.floatToIntBits(average) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AverageDTO{" +
                "questionDesc='" + questionDesc + '\'' +
                ", answerDesc='" + answerDesc + '\'' +
                ", average=" + average +
                '}';
    }
}
