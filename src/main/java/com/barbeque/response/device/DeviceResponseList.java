package com.barbeque.response.device;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceResponseList implements GenericResponse{
    private List<DeviceResponse> devices;
    private String message;
    private String messageType;

    public List<DeviceResponse> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceResponse> devices) {
        this.devices = devices;
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
        return "DeviceResponseList{" +
                "devices=" + devices +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
