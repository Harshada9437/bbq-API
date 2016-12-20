package com.barbeque.response.Answer;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-2 on 12/19/2016.
 */
public class AnswerResponse implements GenericResponse
{
    List<AnswerResponseList> answerResponseList;
    private String message;
    private String messageType;


    public List<AnswerResponseList> getAnswerResponseList() {
        return answerResponseList;
    }

    public void setAnswerResponseList(List<AnswerResponseList> answerResponseList) {
        this.answerResponseList = answerResponseList;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageType() {
        return messageType;
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
        return "AnswerResponse{" +
                "answerResponseList=" + answerResponseList +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}

