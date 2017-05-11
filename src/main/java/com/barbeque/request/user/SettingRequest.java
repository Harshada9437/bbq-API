package com.barbeque.request.user;

import java.sql.Time;

/**
 * Created by System-2 on 2/6/2017.
 */

public class SettingRequest {
    private String negativeSmsTemplate;
    private String positiveSmsTemplate;
    private Time archiveTime;
    private Time reportTime;

    public String getNegativeSmsTemplate() {
        return negativeSmsTemplate;
    }

    public void setNegativeSmsTemplate(String negativeSmsTemplate) {
        this.negativeSmsTemplate = negativeSmsTemplate;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingRequest that = (SettingRequest) o;

        if (negativeSmsTemplate != null ? !negativeSmsTemplate.equals(that.negativeSmsTemplate) : that.negativeSmsTemplate != null)
            return false;
        if (positiveSmsTemplate != null ? !positiveSmsTemplate.equals(that.positiveSmsTemplate) : that.positiveSmsTemplate != null)
            return false;
        if (archiveTime != null ? !archiveTime.equals(that.archiveTime) : that.archiveTime != null) return false;
        return reportTime != null ? reportTime.equals(that.reportTime) : that.reportTime == null;
    }

    @Override
    public int hashCode() {
        int result = negativeSmsTemplate != null ? negativeSmsTemplate.hashCode() : 0;
        result = 31 * result + (positiveSmsTemplate != null ? positiveSmsTemplate.hashCode() : 0);
        result = 31 * result + (archiveTime != null ? archiveTime.hashCode() : 0);
        result = 31 * result + (reportTime != null ? reportTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SettingRequest{" +
                "negativeSmsTemplate='" + negativeSmsTemplate + '\'' +
                ", positiveSmsTemplate='" + positiveSmsTemplate + '\'' +
                ", archiveTime='" + archiveTime + '\'' +
                ", reportTime='" + reportTime + '\'' +
                '}';
    }
}
