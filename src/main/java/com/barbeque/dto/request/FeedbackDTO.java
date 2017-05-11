package com.barbeque.dto.request;

import java.sql.Timestamp;

/**
 * Created by System-3 on 2/15/2017.
 */
public class FeedbackDTO {
    private char questionType;
    private int id;
    private int isNegative;
    private int answerId;
    private int questionId;
    private int outletId;
    private String feedbackDate;
    private String viewDate;
    private int viewCount;
    private String tableNo;
    private String customerName;
    private String outletDesc;
    private String mobileNo;
    private String answerText;
    private String answerDesc;
    private String questionDesc;
    private int ansRating;
    private int weightage;
    private int rating;
    private int isAddressed;
    private int isPoor;
    private String email;
    private String threshold;
    private String dob;
    private String doa;
    private String locality;
    private String mgrName;
    private String mgrEmail;
    private String mgrMobile;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }

    public int getAnsRating() {
        return ansRating;
    }

    public void setAnsRating(int ansRating) {
        this.ansRating = ansRating;
    }

    public int getWeightage() {
        return weightage;
    }

    public void setWeightage(int weightage) {
        this.weightage = weightage;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public char getQuestionType() {
        return questionType;
    }

    public void setQuestionType(char questionType) {
        this.questionType = questionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsNegative() {
        return isNegative;
    }

    public void setIsNegative(int isNegative) {
        this.isNegative = isNegative;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getViewDate() {
        return viewDate;
    }

    public void setViewDate(String viewDate) {
        this.viewDate = viewDate;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOutletDesc() {
        return outletDesc;
    }

    public void setOutletDesc(String outletDesc) {
        this.outletDesc = outletDesc;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerDesc() {
        return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getIsAddressed() {
        return isAddressed;
    }

    public void setIsAddressed(int isAddressed) {
        this.isAddressed = isAddressed;
    }

    public int getIsPoor() {
        return isPoor;
    }

    public void setIsPoor(int isPoor) {
        this.isPoor = isPoor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    public String getMgrEmail() {
        return mgrEmail;
    }

    public void setMgrEmail(String mgrEmail) {
        this.mgrEmail = mgrEmail;
    }

    public String getMgrMobile() {
        return mgrMobile;
    }

    public void setMgrMobile(String mgrMobile) {
        this.mgrMobile = mgrMobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackDTO that = (FeedbackDTO) o;

        if (questionType != that.questionType) return false;
        if (id != that.id) return false;
        if (questionId != that.questionId) return false;
        if (isNegative != that.isNegative) return false;
        if (answerId != that.answerId) return false;
        if (outletId != that.outletId) return false;
        if (weightage != that.weightage) return false;
        if (ansRating != that.ansRating) return false;
        if (viewCount != that.viewCount) return false;
        if (rating != that.rating) return false;
        if (isAddressed != that.isAddressed) return false;
        if (isPoor != that.isPoor) return false;
        if (feedbackDate != null ? !feedbackDate.equals(that.feedbackDate) : that.feedbackDate != null) return false;
        if (threshold != null ? !threshold.equals(that.threshold) : that.threshold != null) return false;
        if (viewDate != null ? !viewDate.equals(that.viewDate) : that.viewDate != null) return false;
        if (tableNo != null ? !tableNo.equals(that.tableNo) : that.tableNo != null) return false;
        if (customerName != null ? !customerName.equals(that.customerName) : that.customerName != null) return false;
        if (outletDesc != null ? !outletDesc.equals(that.outletDesc) : that.outletDesc != null) return false;
        if (mobileNo != null ? !mobileNo.equals(that.mobileNo) : that.mobileNo != null) return false;
        if (answerText != null ? !answerText.equals(that.answerText) : that.answerText != null) return false;
        if (answerDesc != null ? !answerDesc.equals(that.answerDesc) : that.answerDesc != null) return false;
        if (questionDesc != null ? !questionDesc.equals(that.questionDesc) : that.questionDesc != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (dob != null ? !dob.equals(that.dob) : that.dob != null) return false;
        if (doa != null ? !doa.equals(that.doa) : that.doa != null) return false;
        if (locality != null ? !locality.equals(that.locality) : that.locality != null) return false;
        if (mgrName != null ? !mgrName.equals(that.mgrName) : that.mgrName != null) return false;
        if (mgrEmail != null ? !mgrEmail.equals(that.mgrEmail) : that.mgrEmail != null) return false;
        return mgrMobile != null ? mgrMobile.equals(that.mgrMobile) : that.mgrMobile == null;
    }

    @Override
    public int hashCode() {
        int result = (int) questionType;
        result = 31 * result + id;
        result = 31 * result + questionId;
        result = 31 * result + isNegative;
        result = 31 * result + answerId;
        result = 31 * result + outletId;
        result = 31 * result + weightage;
        result = 31 * result + ansRating;
        result = 31 * result + (feedbackDate != null ? feedbackDate.hashCode() : 0);
        result = 31 * result + (threshold != null ? threshold.hashCode() : 0);
        result = 31 * result + (viewDate != null ? viewDate.hashCode() : 0);
        result = 31 * result + viewCount;
        result = 31 * result + (tableNo != null ? tableNo.hashCode() : 0);
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (outletDesc != null ? outletDesc.hashCode() : 0);
        result = 31 * result + (mobileNo != null ? mobileNo.hashCode() : 0);
        result = 31 * result + (answerText != null ? answerText.hashCode() : 0);
        result = 31 * result + (answerDesc != null ? answerDesc.hashCode() : 0);
        result = 31 * result + (questionDesc != null ? questionDesc.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + isAddressed;
        result = 31 * result + isPoor;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (doa != null ? doa.hashCode() : 0);
        result = 31 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (mgrName != null ? mgrName.hashCode() : 0);
        result = 31 * result + (mgrEmail != null ? mgrEmail.hashCode() : 0);
        result = 31 * result + (mgrMobile != null ? mgrMobile.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackDTO{" +
                "questionType=" + questionType +
                ", id=" + id +
                ", isNegative=" + isNegative +
                ", questionId=" + questionId +
                ", answerId=" + answerId +
                ", answerId=" + outletId +
                ", ansRating=" + ansRating +
                ", weightage=" + weightage +
                ", feedbackDate='" + feedbackDate + '\'' +
                ", viewDate='" + viewDate + '\'' +
                ", viewCount=" + viewCount +
                ", tableNo='" + tableNo + '\'' +
                ", customerName='" + customerName + '\'' +
                ", threshold='" + threshold + '\'' +
                ", outletDesc='" + outletDesc + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", answerText='" + answerText + '\'' +
                ", answerDesc='" + answerDesc + '\'' +
                ", questionDesc='" + questionDesc + '\'' +
                ", rating=" + rating +
                ", isAddressed=" + isAddressed +
                ", isPoor=" + isPoor +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", doa='" + doa + '\'' +
                ", locality='" + locality + '\'' +
                ", mgrName='" + mgrName + '\'' +
                ", mgrEmail='" + mgrEmail + '\'' +
                ", mgrMobile='" + mgrMobile + '\'' +
                '}';
    }
}
