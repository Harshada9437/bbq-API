package com.barbeque.response.report;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-3 on 2/13/2017.
 */
public class CountResponseList implements GenericResponse{
    private List<CountResponse> count;
    private String message;
    private String messageType;

    public CountResponseList() {
        this.count = count;
        this.message = message;
        this.messageType = messageType;
    }

    public List<CountResponse> getCount() {
        return count;
    }

    public void setCount(List<CountResponse> count) {
        this.count = count;
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
        return "CountResponseList{" +
                "count=" + count +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
