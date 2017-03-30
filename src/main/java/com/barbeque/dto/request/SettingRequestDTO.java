package com.barbeque.dto.request;

import java.sql.Time;

/**
 * Created by System-2 on 2/6/2017.
 */
public class SettingRequestDTO {
    private String smsTemplate;
    private Time archiveTime;
    private Time reportTime;

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

    public String getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingRequestDTO that = (SettingRequestDTO) o;

        if (smsTemplate != null ? !smsTemplate.equals(that.smsTemplate) : that.smsTemplate != null) return false;
        if (archiveTime != null ? !archiveTime.equals(that.archiveTime) : that.archiveTime != null) return false;
        return reportTime != null ? reportTime.equals(that.reportTime) : that.reportTime == null;
    }

    @Override
    public int hashCode() {
        int result = smsTemplate != null ? smsTemplate.hashCode() : 0;
        result = 31 * result + (archiveTime != null ? archiveTime.hashCode() : 0);
        result = 31 * result + (reportTime != null ? reportTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SettingRequestDTO{" +
                "smsTemplate='" + smsTemplate + '\'' +
                ", archiveTime=" + archiveTime +
                ", reportTime=" + reportTime +
                '}';
    }
}
