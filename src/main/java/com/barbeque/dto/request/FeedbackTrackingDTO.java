package com.barbeque.dto.request;

/**
 * Created by System-3 on 2/16/2017.
 */
public class FeedbackTrackingDTO {
    private int feedbackId;
    private String managerMobile;
    private String managerEmail;
    private String managerName;
    private String firstViewDate;
    private int viewCount;

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getManagerMobile() {
        return managerMobile;
    }

    public void setManagerMobile(String managerMobile) {
        this.managerMobile = managerMobile;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getFirstViewDate() {
        return firstViewDate;
    }

    public void setFirstViewDate(String firstViewDate) {
        this.firstViewDate = firstViewDate;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackTrackingDTO that = (FeedbackTrackingDTO) o;

        if (feedbackId != that.feedbackId) return false;
        if (viewCount != that.viewCount) return false;
        if (!managerMobile.equals(that.managerMobile)) return false;
        if (!managerEmail.equals(that.managerEmail)) return false;
        if (!managerName.equals(that.managerName)) return false;
        return firstViewDate.equals(that.firstViewDate);
    }

    @Override
    public int hashCode() {
        int result = feedbackId;
        result = 31 * result + managerMobile.hashCode();
        result = 31 * result + managerEmail.hashCode();
        result = 31 * result + managerName.hashCode();
        result = 31 * result + firstViewDate.hashCode();
        result = 31 * result + viewCount;
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackTrackingDTO{" +
                "feedbackId=" + feedbackId +
                ", managerMobile='" + managerMobile + '\'' +
                ", managerEmail='" + managerEmail + '\'' +
                ", managerName='" + managerName + '\'' +
                ", firstViewDate='" + firstViewDate + '\'' +
                ", viewCount=" + viewCount +
                '}';
    }
}
