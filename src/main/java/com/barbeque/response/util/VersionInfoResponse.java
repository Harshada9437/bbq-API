package com.barbeque.response.util;

/**
 * Created by Sandeep on 1/20/2017.
 */
public class VersionInfoResponse implements GenericResponse {

    private int versionCode;
    private String versionNumber;
    private String  message;
    private String  messageType;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
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
}
