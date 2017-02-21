package com.barbeque.response.report;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-3 on 2/14/2017.
 */
public class AverageResponseList implements GenericResponse{
    private List<AverageResponse> average;
    private String message;
    private String messageType;

    public List<AverageResponse> getAverage() {
        return average;
    }

    public void setAverage(List<AverageResponse> average) {
        this.average = average;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "AverageResponseList{" +
                "average=" + average +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
