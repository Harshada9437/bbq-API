package com.barbeque.request.question;

import java.util.List;

/**
 * Created by System1 on 9/30/2016.
 */
public class UpdateQueRequest {
    private int id;
    private String questionDesc;
    private char questionType;
    private int parentAnswerId;
    private int parentQuestionId;
    private int answerSymbol;
    public List<UpdateOptionsList> answerOption;

    public List<UpdateOptionsList> getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(List<UpdateOptionsList> answerOption) {
        this.answerOption = answerOption;
    }

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

        UpdateQueRequest that = (UpdateQueRequest) o;

        if (id != that.id) return false;
        if (questionType != that.questionType) return false;
        if (parentAnswerId != that.parentAnswerId) return false;
        if (parentQuestionId != that.parentQuestionId) return false;
        if (answerSymbol != that.answerSymbol) return false;
        if (questionDesc != null ? !questionDesc.equals(that.questionDesc) : that.questionDesc != null) return false;
        return answerOption != null ? answerOption.equals(that.answerOption) : that.answerOption == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (questionDesc != null ? questionDesc.hashCode() : 0);
        result = 31 * result + (int) questionType;
        result = 31 * result + parentAnswerId;
        result = 31 * result + parentQuestionId;
        result = 31 * result + answerSymbol;
        result = 31 * result + (answerOption != null ? answerOption.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateQueRequest{" +
                "id=" + id +
                ", questionDesc='" + questionDesc + '\'' +
                ", questionType=" + questionType +
                ", parentAnswerId=" + parentAnswerId +
                ", parentQuestionId=" + parentQuestionId +
                ", answerSymbol=" + answerSymbol +
                ", answerOption=" + answerOption +
                '}';
    }
}
