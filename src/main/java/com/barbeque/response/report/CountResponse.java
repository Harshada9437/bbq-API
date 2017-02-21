package com.barbeque.response.report;

/**
 * Created by System-3 on 2/13/2017.
 */
public class CountResponse  {
    private String questionDesc;
    private String answerDesc;
    private String  questionType;
    private int count;

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

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    public String toString() {
        return "CountResponse{" +
                "questionDesc='" + questionDesc + '\'' +
                ", answerDesc='" + answerDesc + '\'' +
                ", questionType='" + questionType + '\'' +
                ", count=" + count +
                '}';
    }
}



