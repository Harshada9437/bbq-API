package com.barbeque.response.question;

import com.barbeque.response.util.GenericResponse;

/**
 * Created by System-2 on 12/20/2016.
 */
public class GetQuestionResponse implements GenericResponse
{
    private int id;
    private String questionDesc;
    private char questionType;
    private int parentAnswerId;
    private int parentQuestionId;
    private int answerSymbol;
    private String messageType;
    private Object message;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMessageType() {
        return messageType;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType=messageType;
    }

    @Override
    public void setMessage(String message) {
        this.message=message;
    }

    @Override
    public String toString() {
        return "GetQuestionResponse{" +
                "id=" + id +
                ", questionDesc='" + questionDesc + '\'' +
                ", questionType=" + questionType +
                ", parentAnswerId=" + parentAnswerId +
                ", parentQuestionId=" + parentQuestionId +
                ", answerSymbol=" + answerSymbol +
                ", messageType='" + messageType + '\'' +
                ", message=" + message +
                '}';
    }
}
