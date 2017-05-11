package com.barbeque.dto.request;

import java.sql.Time;

/**
 * Created by System-2 on 2/6/2017.
 */
public class SettingRequestDTO {
    private String positiveSmsTemplate;
    private String negativeSmsTemplate;
    private Time archiveTime;
    private Time reportTime;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingRequestDTO that = (SettingRequestDTO) o;

        if (positiveSmsTemplate != null ? !positiveSmsTemplate.equals(that.positiveSmsTemplate) : that.positiveSmsTemplate != null)
            return false;
        if (negativeSmsTemplate != null ? !negativeSmsTemplate.equals(that.negativeSmsTemplate) : that.negativeSmsTemplate != null)
            return false;
        if (archiveTime != null ? !archiveTime.equals(that.archiveTime) : that.archiveTime != null) return false;
        return reportTime != null ? reportTime.equals(that.reportTime) : that.reportTime == null;
    }

    @Override
    public int hashCode() {
        int result = positiveSmsTemplate != null ? positiveSmsTemplate.hashCode() : 0;
        result = 31 * result + (negativeSmsTemplate != null ? negativeSmsTemplate.hashCode() : 0);
        result = 31 * result + (archiveTime != null ? archiveTime.hashCode() : 0);
        result = 31 * result + (reportTime != null ? reportTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SettingRequestDTO{" +
                "positiveSmsTemplate='" + positiveSmsTemplate + '\'' +
                ", negativeSmsTemplate='" + negativeSmsTemplate + '\'' +
                ", archiveTime=" + archiveTime +
                ", reportTime=" + reportTime +
                '}';
    }
}
