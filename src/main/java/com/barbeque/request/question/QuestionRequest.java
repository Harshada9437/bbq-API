package com.barbeque.request.question;


import java.util.List;

/**
 * Created by System1 on 9/21/2016.
 */
public class QuestionRequest {
    private String questionDesc;
    private char questionType;
    private int parentAnswerId;
    private int parentQuestionId;
    private int answerSymbol;
    public List<String> asnwerLists;
    public List<Integer>rating ;

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

    public List<String> getAsnwerLists() {
        return asnwerLists;
    }

    public void setAsnwerLists(List<String> asnwerLists) {
        this.asnwerLists = asnwerLists;
    }

    public List<Integer> getRating() {
        return rating;
    }

    public void setRating(List<Integer> rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionRequest that = (QuestionRequest) o;

        if (questionType != that.questionType) return false;
        if (parentAnswerId != that.parentAnswerId) return false;
        if (parentQuestionId != that.parentQuestionId) return false;
        if (answerSymbol != that.answerSymbol) return false;
        if (questionDesc != null ? !questionDesc.equals(that.questionDesc) : that.questionDesc != null) return false;
        if (asnwerLists != null ? !asnwerLists.equals(that.asnwerLists) : that.asnwerLists != null) return false;
        return rating != null ? rating.equals(that.rating) : that.rating == null;
    }

    @Override
    public int hashCode() {
        int result = questionDesc != null ? questionDesc.hashCode() : 0;
        result = 31 * result + (int) questionType;
        result = 31 * result + parentAnswerId;
        result = 31 * result + parentQuestionId;
        result = 31 * result + answerSymbol;
        result = 31 * result + (asnwerLists != null ? asnwerLists.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QuestionRequest{" +
                "questionDesc='" + questionDesc + '\'' +
                ", questionType=" + questionType +
                ", parentAnswerId=" + parentAnswerId +
                ", parentQuestionId=" + parentQuestionId +
                ", answerSymbol=" + answerSymbol +
                ", asnwerLists=" + asnwerLists +
                ", rating=" + rating +
                '}';
    }
}
