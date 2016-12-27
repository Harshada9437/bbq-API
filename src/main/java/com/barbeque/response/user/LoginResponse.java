package com.barbeque.response.user;



import com.barbeque.response.util.GenericResponse;

public class LoginResponse implements GenericResponse {
    private String messageType;
    private String message;

    public String getMessageType() {
        return messageType;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }


    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
