package com.barbeque.response.template;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-2 on 12/19/2016.
 */
public class TemplateResponse implements GenericResponse
{
    List<TemplateResponseList> templateResponseList;
    private String message;
    private String messageType;

    public List<TemplateResponseList> getTemplateResponseList() {
        return templateResponseList;
    }

    public void setTemplateResponseList(List<TemplateResponseList> templateResponseList) {
        this.templateResponseList = templateResponseList;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType)
    {
        this.messageType=messageType;
    }

    @Override
    public void setMessage(String message)
    {
        this.message=message;
    }

    @Override
    public String toString() {
        return "TemplateResponse{" +
                "templateResponseList=" + templateResponseList +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
