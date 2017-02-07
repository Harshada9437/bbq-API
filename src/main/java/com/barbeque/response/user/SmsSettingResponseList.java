package com.barbeque.response.user;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-2 on 2/7/2017.
 */
public class SmsSettingResponseList implements GenericResponse {
    private List<SmsSettingResponse> smsSettings;
    private String message;
    private String messageType;

    public List<SmsSettingResponse> getSmsSettings() {
        return smsSettings;
    }

    public void setSmsSettings(List<SmsSettingResponse> smsSettings) {
        this.smsSettings = smsSettings;
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
        return "SmsSettingResponseList{" +
                "smsSettings=" + smsSettings +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
