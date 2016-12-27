package com.barbeque.response.table;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-3 on 12/10/2016.
 */
public class TableResponseList implements GenericResponse
{
    List<TableResponse> tableResponse;
    private String message;
    private String messageType;


    public List<TableResponse> getTableResponse() {
        return tableResponse;
    }

    public void setTableResponse(List<TableResponse> tableResponse) {
        this.tableResponse = tableResponse;
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
        return "TableResponseList{" +
                "tableResponse=" + tableResponse +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
