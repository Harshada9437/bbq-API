package com.barbeque.request.feedback;

/**
 * Created by System-3 on 2/16/2017.
 */
public class FeedbackTrackingRequest {
    private int feedbackId;

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackTrackingRequest that = (FeedbackTrackingRequest) o;

        return feedbackId == that.feedbackId;
    }

    @Override
    public int hashCode() {
        return feedbackId;
    }

    @Override
    public String toString() {
        return "FeedbackTrackingRequest{" +
                "feedbackId=" + feedbackId +
                '}';
    }
}
