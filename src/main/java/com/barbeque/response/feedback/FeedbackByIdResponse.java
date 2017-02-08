package com.barbeque.response.feedback;

import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.response.util.GenericResponse;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by System-3 on 2/7/2017.
 */
public class FeedbackByIdResponse implements GenericResponse

{
    private char questionType;
    private int id;
    private int customerId;
    private int deviceId;
    private String feedbackDate;
    private String modifiedOn;
    private int outletId;
    private String date;
    private List<FeedbackDetails> feedbacks;
    private String tableNo;
    private String billNo;
    private String customerName;
    private String outletDesc;
    private String mobileNo;
    private int answerId;
    private int questionId;
    private String answerText;
    private String answerDesc;
    private String questionDesc;
    private int rating;
    private int weightage;
    private String email;
    private String dob;
    private String doa;
    private String locality;
    private String message;
    private String messageType;


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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<FeedbackDetails> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackDetails> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
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

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
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

    public int getWeightage() {
        return weightage;
    }

    public void setWeightage(int weightage) {
        this.weightage = weightage;
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

    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackByIdResponse that = (FeedbackByIdResponse) o;

        if (questionType != that.questionType) return false;
        if (id != that.id) return false;
        if (customerId != that.customerId) return false;
        if (deviceId != that.deviceId) return false;
        if (outletId != that.outletId) return false;
        if (answerId != that.answerId) return false;
        if (questionId != that.questionId) return false;
        if (rating != that.rating) return false;
        if (weightage != that.weightage) return false;
        if (!feedbackDate.equals(that.feedbackDate)) return false;
        if (!modifiedOn.equals(that.modifiedOn)) return false;
        if (!date.equals(that.date)) return false;
        if (!feedbacks.equals(that.feedbacks)) return false;
        if (!tableNo.equals(that.tableNo)) return false;
        if (!billNo.equals(that.billNo)) return false;
        if (!customerName.equals(that.customerName)) return false;
        if (!outletDesc.equals(that.outletDesc)) return false;
        if (!mobileNo.equals(that.mobileNo)) return false;
        if (!answerText.equals(that.answerText)) return false;
        if (!answerDesc.equals(that.answerDesc)) return false;
        if (!questionDesc.equals(that.questionDesc)) return false;
        if (!email.equals(that.email)) return false;
        if (!dob.equals(that.dob)) return false;
        if (!doa.equals(that.doa)) return false;
        return locality.equals(that.locality);
    }

    @Override
    public int hashCode() {
        int result = (int) questionType;
        result = 31 * result + id;
        result = 31 * result + customerId;
        result = 31 * result + deviceId;
        result = 31 * result + feedbackDate.hashCode();
        result = 31 * result + modifiedOn.hashCode();
        result = 31 * result + outletId;
        result = 31 * result + date.hashCode();
        result = 31 * result + feedbacks.hashCode();
        result = 31 * result + tableNo.hashCode();
        result = 31 * result + billNo.hashCode();
        result = 31 * result + customerName.hashCode();
        result = 31 * result + outletDesc.hashCode();
        result = 31 * result + mobileNo.hashCode();
        result = 31 * result + answerId;
        result = 31 * result + questionId;
        result = 31 * result + answerText.hashCode();
        result = 31 * result + answerDesc.hashCode();
        result = 31 * result + questionDesc.hashCode();
        result = 31 * result + rating;
        result = 31 * result + weightage;
        result = 31 * result + email.hashCode();
        result = 31 * result + dob.hashCode();
        result = 31 * result + doa.hashCode();
        result = 31 * result + locality.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackByIdResponse{" +
                "questionType=" + questionType +
                ", id=" + id +
                ", customerId=" + customerId +
                ", deviceId=" + deviceId +
                ", feedbackDate=" + feedbackDate +
                ", modifiedOn='" + modifiedOn + '\'' +
                ", outletId=" + outletId +
                ", date='" + date + '\'' +
                ", feedbacks=" + feedbacks +
                ", tableNo='" + tableNo + '\'' +
                ", billNo='" + billNo + '\'' +
                ", customerName='" + customerName + '\'' +
                ", outletDesc='" + outletDesc + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", answerId=" + answerId +
                ", questionId=" + questionId +
                ", answerText='" + answerText + '\'' +
                ", answerDesc='" + answerDesc + '\'' +
                ", questionDesc='" + questionDesc + '\'' +
                ", rating=" + rating +
                ", weightage=" + weightage +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", doa='" + doa + '\'' +
                ", locality='" + locality + '\'' +
                '}';
    }
}
