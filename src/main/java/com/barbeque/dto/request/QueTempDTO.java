package com.barbeque.dto.request;

import com.barbeque.response.Answer.AnswerResponseList;

import java.util.List;

/**
 * Created by System1 on 9/27/2016.
 */
public class QueTempDTO {
    private int tempId;
    private int queId;
    private String parentAnswerDesc;
    private String parentQuestionDesc;
    private String questionDesc;
    private char questionType;
    private int parentAnswerId;
    private String threshold;
    private int parentQuestionId;
    private int answerSymbol;
    private List<AnswerResponseList> options;
    private int priority;

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public int getTempId() {
        return tempId;
    }

    public void setTempId(int tempId) {
        this.tempId = tempId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<AnswerResponseList> getOptions() {
        return options;
    }

    public void setOptions(List<AnswerResponseList> options) {
        this.options = options;
    }

    public String getParentAnswerDesc() {return parentAnswerDesc;}

    public void setParentAnswerDesc(String parentAnswerDesc) {this.parentAnswerDesc = parentAnswerDesc;}

    public String getParentQuestionDesc() {return parentQuestionDesc;}

    public void setParentQuestionDesc(String parentQuestionDesc) {this.parentQuestionDesc = parentQuestionDesc;}

    public int getQueId() {
        return queId;
    }

    public void setQueId(int queId) {
        this.queId = queId;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public char getQuestionType() {
        return questionType;
    }

    public void setQuestionType(char questionType) {
        this.questionType = questionType;
    }

    public int getParentAnswerId() {
        return parentAnswerId;
    }

    public void setParentAnswerId(int parentAnswerId) {
        this.parentAnswerId = parentAnswerId;
    }

    public int getParentQuestionId() {
        return parentQuestionId;
    }

    public void setParentQuestionId(int parentQuestionId) {
        this.parentQuestionId = parentQuestionId;
    }

    public int getAnswerSymbol() {
        return answerSymbol;
    }

    public void setAnswerSymbol(int answerSymbol) {
        this.answerSymbol = answerSymbol;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueTempDTO that = (QueTempDTO) o;

        if (tempId != that.tempId) return false;
        if (queId != that.queId) return false;
        if (questionType != that.questionType) return false;
        if (parentAnswerId != that.parentAnswerId) return false;
        if (parentQuestionId != that.parentQuestionId) return false;
        if (answerSymbol != that.answerSymbol) return false;
        if (priority != that.priority) return false;
        if (parentAnswerDesc != null ? !parentAnswerDesc.equals(that.parentAnswerDesc) : that.parentAnswerDesc != null)
            return false;
        if (parentQuestionDesc != null ? !parentQuestionDesc.equals(that.parentQuestionDesc) : that.parentQuestionDesc != null)
            return false;
        if (questionDesc != null ? !questionDesc.equals(that.questionDesc) : that.questionDesc != null) return false;
        if (threshold != null ? !threshold.equals(that.threshold) : that.threshold != null) return false;
        return options != null ? options.equals(that.options) : that.options == null;
    }

    @Override
    public int hashCode() {
        int result = tempId;
        result = 31 * result + queId;
        result = 31 * result + (parentAnswerDesc != null ? parentAnswerDesc.hashCode() : 0);
        result = 31 * result + (parentQuestionDesc != null ? parentQuestionDesc.hashCode() : 0);
        result = 31 * result + (questionDesc != null ? questionDesc.hashCode() : 0);
        result = 31 * result + (int) questionType;
        result = 31 * result + parentAnswerId;
        result = 31 * result + (threshold != null ? threshold.hashCode() : 0);
        result = 31 * result + parentQuestionId;
        result = 31 * result + answerSymbol;
        result = 31 * result + (options != null ? options.hashCode() : 0);
        result = 31 * result + priority;
        return result;
    }

    @Override
    public String toString() {
        return "QueTempDTO{" +
                "tempId=" + tempId +
                ", queId=" + queId +
                ", parentAnswerDesc='" + parentAnswerDesc + '\'' +
                ", parentQuestionDesc='" + parentQuestionDesc + '\'' +
                ", questionDesc='" + questionDesc + '\'' +
                ", questionType=" + questionType +
                ", parentAnswerId=" + parentAnswerId +
                ", threshold='" + threshold + '\'' +
                ", parentQuestionId=" + parentQuestionId +
                ", answerSymbol=" + answerSymbol +
                ", options=" + options +
                ", priority=" + priority +
                '}';
    }
}
