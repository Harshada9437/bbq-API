package com.barbeque.response.template;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System1 on 9/30/2016.
 */
public class TempQueListResponse implements GenericResponse {
    private List<QueResponse> questions;
    private String message;
    private String messageType;

    public List<QueResponse> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QueResponse> questions) {
        this.questions = questions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "TempQueListResponse{" +
                "questions=" + questions +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
