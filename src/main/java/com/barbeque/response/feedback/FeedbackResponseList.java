package com.barbeque.response.feedback;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-2 on 12/14/2016.
 */
public class FeedbackResponseList implements GenericResponse {
    private List<FeedbackResponse> feedbacks;
    private String messageType;
    private String message;

    public List<FeedbackResponse> getFeedbacks() {return feedbacks;}

    public void setFeedbacks(List<FeedbackResponse> feedbacks) {this.feedbacks = feedbacks;}

    public String getMessageType() {return messageType;}

    public void setMessageType(String messageType) {this.messageType = messageType;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    @Override
    public String toString() {
        return "FeedbackResponseList{" +
                "feedbacks=" + feedbacks +
                ", messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
