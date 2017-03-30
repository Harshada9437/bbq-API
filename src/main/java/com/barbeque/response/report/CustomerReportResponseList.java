package com.barbeque.response.report;

import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.request.report.Feedback;
import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-3 on 2/15/2017.
 */
public class CustomerReportResponseList implements GenericResponse {
    private String name;
    private String emailId;
    private String mobile;
    private String dob;
    private String doa;
    private String locality;
    private List<Feedback> feedback;
    private String messageType;
    private String message;

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

    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CustomerReportResponseList{" +
                "name='" + name + '\'' +
                ", emailId='" + emailId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", dob='" + dob + '\'' +
                ", doa='" + doa + '\'' +
                ", locality='" + locality + '\'' +
                ", feedback=" + feedback +
                ", messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
