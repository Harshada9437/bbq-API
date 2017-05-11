package com.barbeque.response.user;

import com.barbeque.response.util.GenericResponse;

import java.sql.Time;

/**
 * Created by System-2 on 2/6/2017.
 */
public class SettingResponse implements GenericResponse{
    private String positiveSmsTemplate;
    private Time archiveTime;
    private Time reportTime;
    private String negativeSmsTemplate;
    private String message;
    private String messageType;

    public String getPositiveSmsTemplate() {
        return positiveSmsTemplate;
    }

    public void setPositiveSmsTemplate(String positiveSmsTemplate) {
        this.positiveSmsTemplate = positiveSmsTemplate;
    }

    public Time getArchiveTime() {
        return archiveTime;
    }

    public void setArchiveTime(Time archiveTime) {
        this.archiveTime = archiveTime;
    }

    public Time getReportTime() {
        return reportTime;
    }

    public void setReportTime(Time reportTime) {
        this.reportTime = reportTime;
    }

    public String getNegativeSmsTemplate() {
        return negativeSmsTemplate;
    }

    public void setNegativeSmsTemplate(String negativeSmsTemplate) {
        this.negativeSmsTemplate = negativeSmsTemplate;
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
                "positiveSmsTemplate='" + positiveSmsTemplate + '\'' +
                ", archiveTime=" + archiveTime +
                ", reportTime=" + reportTime +
                ", negativeSmsTemplate='" + negativeSmsTemplate + '\'' +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
