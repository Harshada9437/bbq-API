package com.barbeque.response.outlet;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletResponse implements GenericResponse
{
    List<OutletResponseList> outletResponseList;
    private String message;
    private String messageType;

    public List<OutletResponseList> getOutletResponseList() {
        return outletResponseList;
    }

    public void setOutletResponseList(List<OutletResponseList> outletResponseList) {
        this.outletResponseList = outletResponseList;
    }

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
        return "OutletResponse{" +
                "outletResponseList=" + outletResponseList +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
