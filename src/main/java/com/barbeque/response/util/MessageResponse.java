package com.barbeque.response.util;


import com.barbeque.response.util.GenericResponse;

/**
 * Created by System1 on 9/1/2016.
 */
public class MessageResponse implements GenericResponse {
    private String messageType;
    private String message;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
