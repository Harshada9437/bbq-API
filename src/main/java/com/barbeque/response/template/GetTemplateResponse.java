package com.barbeque.response.template;

import com.barbeque.response.util.GenericResponse;

/**
 * Created by System-2 on 1/7/2017.
 */
public class GetTemplateResponse implements GenericResponse {
    private int id;
    private String desc;
    private String status;
    private String fromDate;
    private String toDate;
    private String message;
    private String messageType;
    private int outletId;

    public GetTemplateResponse(int id, String desc, String status, String fromDate, String toDate, int outletId) {
        this.id = id;
        this.desc = desc;
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.outletId = outletId;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getStatus() {
        return status;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public int getOutletId() {
        return outletId;
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
        return "GetTemplateResponse{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                ", outletId=" + outletId +
                '}';
    }
}

