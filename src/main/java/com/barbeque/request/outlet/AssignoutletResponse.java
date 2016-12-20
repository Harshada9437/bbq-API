package com.barbeque.request.outlet;

import com.barbeque.response.util.GenericResponse;

/**
 * Created by System-2 on 12/20/2016.
 */
public class AssignoutletResponse implements GenericResponse
{
    private String message;
    private String messageType;

    public String getMessage() {
        return message;
    }

    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType=messageType;
    }

    @Override
    public void setMessage(String message) {
        this.message=message;
    }

    @Override
    public String toString() {
        return "AssignoutletResponse{" +
                "message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
