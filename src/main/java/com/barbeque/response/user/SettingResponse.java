package com.barbeque.response.user;

import com.barbeque.response.util.GenericResponse;

/**
 * Created by System-2 on 2/6/2017.
 */
public class SettingResponse implements GenericResponse{
    private String smsTemplate;
    private String message;
    private String messageType;

    public String getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
        this.smsTemplate = smsTemplate;
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
        return "SettingResponse{" +
                "smsTemplate='" + smsTemplate + '\'' +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
