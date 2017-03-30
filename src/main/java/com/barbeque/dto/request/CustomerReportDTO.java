package com.barbeque.dto.request;

import com.barbeque.request.report.Feedback;

import java.util.List;

/**
 * Created by System-3 on 2/15/2017.
 */
public class CustomerReportDTO {
    private String mobile;
    private String name;
    private String emailId;
    private String dob;
    private String doa;
    private String locality;
    private List<Feedback> feedback;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoa() {
        return doa;
    }

    public void setDoa(String doa) {
        this.doa = doa;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public List<Feedback> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerReportDTO that = (CustomerReportDTO) o;

        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (emailId != null ? !emailId.equals(that.emailId) : that.emailId != null) return false;
        if (dob != null ? !dob.equals(that.dob) : that.dob != null) return false;
        if (doa != null ? !doa.equals(that.doa) : that.doa != null) return false;
        if (locality != null ? !locality.equals(that.locality) : that.locality != null) return false;
        return feedback != null ? feedback.equals(that.feedback) : that.feedback == null;
    }

    @Override
    public int hashCode() {
        int result = mobile != null ? mobile.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (emailId != null ? emailId.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (doa != null ? doa.hashCode() : 0);
        result = 31 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (feedback != null ? feedback.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CustomerReportDTO{" +
                "mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", emailId='" + emailId + '\'' +
                ", dob='" + dob + '\'' +
                ", doa='" + doa + '\'' +
                ", locality='" + locality + '\'' +
                ", feedback=" + feedback +
                '}';
    }
}
