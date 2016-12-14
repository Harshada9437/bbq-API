package com.barbeque.response.template;

import com.barbeque.response.util.GenericResponse;

/**
 * Created by System1 on 9/27/2016.
 */
public class TempQueResponse implements GenericResponse {
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
        return "TempQueResponse{" +
                "message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}

