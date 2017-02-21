package com.barbeque.response.feedback;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-3 on 2/17/2017.
 */
public class FeedbackTrackingResponseList implements GenericResponse {
    private List<FeedbackTrackingResponse> feedbackTrackingDetails;
    private String messageType;
    private String message;


    public List<FeedbackTrackingResponse> getFeedbackTrackingDetails() {
        return feedbackTrackingDetails;
    }

    public void setFeedbackTrackingDetails(List<FeedbackTrackingResponse> feedbackTrackingDetails) {
        this.feedbackTrackingDetails = feedbackTrackingDetails;
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
        return "FeedbackTrackingResponseList{" +
                "feedbackTrackingDetails=" + feedbackTrackingDetails +
                ", messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
