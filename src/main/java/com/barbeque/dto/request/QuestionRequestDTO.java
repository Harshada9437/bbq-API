package com.barbeque.dto.request;

public class QuestionRequestDTO {
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionRequestDTO that = (QuestionRequestDTO) o;

        if (id != that.id) return false;
        if (questionType != that.questionType) return false;
        if (parentAnswerId != that.parentAnswerId) return false;
        if (parentQuestionId != that.parentQuestionId) return false;
        if (answerSymbol != that.answerSymbol) return false;
        return questionDesc != null ? questionDesc.equals(that.questionDesc) : that.questionDesc == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (questionDesc != null ? questionDesc.hashCode() : 0);
        result = 31 * result + (int) questionType;
        result = 31 * result + parentAnswerId;
        result = 31 * result + parentQuestionId;
        result = 31 * result + answerSymbol;
        return result;
    }

    @Override
    public String toString() {
        return "QuestionRequestDTO{" +
                "id=" + id +
                ", questionDesc='" + questionDesc + '\'' +
                ", questionType='" + questionType + '\'' +
                ", parentAnswerId='" + parentAnswerId + '\'' +
                ", parentQuestionId='" + parentQuestionId + '\'' +
                ", answerSymbol='" + answerSymbol + '\'' +
                '}';
    }
}
