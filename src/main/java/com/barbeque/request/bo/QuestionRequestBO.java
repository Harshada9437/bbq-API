package com.barbeque.request.bo;

/**
 * Created by System1 on 9/21/2016.
 */
public class QuestionRequestBO {
    private String questionDesc;
    private char questionType;
    private int parentAnswerId;
    private int parentQuestionId;
    private int answerSymbol;

    public String getQuestionDesc() {return questionDesc;}

    public void setQuestionDesc(String questionDesc) {this.questionDesc = questionDesc;}

    public char getQuestionType() {return questionType;}

    public void setQuestionType(char questionType) {this.questionType = questionType;}

    public int getParentAnswerId() {return parentAnswerId;}

    public void setParentAnswerId(int parentAnswerId) {this.parentAnswerId = parentAnswerId;}

    public int getParentQuestionId() {return parentQuestionId;}

    public void setParentQuestionId(int parentQuestionId) {this.parentQuestionId = parentQuestionId;}

    public int getAnswerSymbol() {return answerSymbol;}

    public void setAnswerSymbol(int answerSymbol) {this.answerSymbol = answerSymbol;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionRequestBO that = (QuestionRequestBO) o;

        if (questionType != that.questionType) return false;
        if (parentAnswerId != that.parentAnswerId) return false;
        if (parentQuestionId != that.parentQuestionId) return false;
        if (answerSymbol != that.answerSymbol) return false;
        return questionDesc != null ? questionDesc.equals(that.questionDesc) : that.questionDesc == null;

    }

    @Override
    public int hashCode() {
        int result = questionDesc != null ? questionDesc.hashCode() : 0;
        result = 31 * result + (int) questionType;
        result = 31 * result + parentAnswerId;
        result = 31 * result + parentQuestionId;
        result = 31 * result + answerSymbol;
        return result;
    }

    @Override
    public String toString() {
        return "QuestionRequestBO{" +
                "questionDesc='" + questionDesc + '\'' +
                ", questionType='" + questionType + '\'' +
                ", parentAnswerId='" + parentAnswerId + '\'' +
                ", parentQuestionId='" + parentQuestionId + '\'' +
                ", answerSymbol='" + answerSymbol + '\'' +
                '}';
    }
}