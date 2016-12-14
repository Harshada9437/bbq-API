package com.barbeque.response.util;

import com.barbeque.response.util.GenericResponse;

/**
 * Created by System1 on 9/29/2016.
 */
public class SuccessResponse implements GenericResponse{
    private String message;
    private String messageType;

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
        return "SuccessResponse{" +
                "message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
