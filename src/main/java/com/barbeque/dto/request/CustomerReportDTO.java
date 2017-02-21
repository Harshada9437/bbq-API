package com.barbeque.dto.request;

import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.request.report.Feedback;

import java.util.List;

/**
 * Created by System-3 on 2/15/2017.
 */
public class CustomerReportDTO {
    private int id;
    private String name;
    private String emailId;
    private String dob;
    private String doa;
    private String locality;
    private List<Feedback> feedback;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;
        if (!emailId.equals(that.emailId)) return false;
        if (!dob.equals(that.dob)) return false;
        if (!doa.equals(that.doa)) return false;
        if (!locality.equals(that.locality)) return false;
        return feedback.equals(that.feedback);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + emailId.hashCode();
        result = 31 * result + dob.hashCode();
        result = 31 * result + doa.hashCode();
        result = 31 * result + locality.hashCode();
        result = 31 * result + feedback.hashCode();
        return result;
    }


    @Override
    public String toString() {
        return "CustomerReportDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emailId='" + emailId + '\'' +
                ", dob='" + dob + '\'' +
                ", doa='" + doa + '\'' +
                ", locality='" + locality + '\'' +
                ", feedback=" + feedback +
                '}';
    }
}
