package com.barbeque.dto.request;

import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.response.feedback.CreateCustomer;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by user on 10/18/2016.
 */
public class FeedbackRequestDTO {
    private char questionType;
    private int id;
    private int isNegative;
    private int customerId;
    private int deviceId;
    private Timestamp feedbackDate;
    private String viewDate;
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
    private int isAddressed;
    private int weightage;
    private String email;
    private String threshold;
    private String dob;
    private String doa;
    private String locality;

    public int getIsNegative() {
        return isNegative;
    }

    public void setIsNegative(int isNegative) {
        this.isNegative = isNegative;
    }

    public int getIsAddressed() {
        return isAddressed;
    }

    public void setIsAddressed(int isAddressed) {
        this.isAddressed = isAddressed;
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

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
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

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
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

    public int getWeightage() {
        return weightage;
    }

    public void setWeightage(int weightage) {
        this.weightage = weightage;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public CreateCustomer customer;

    public String getOutletDesc() {
        return outletDesc;
    }

    public void setOutletDesc(String outletDesc) {
        this.outletDesc = outletDesc;
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

    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getViewDate() {
        return viewDate;
    }

    public void setViewDate(String viewDate) {
        this.viewDate = viewDate;
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

    public List<FeedbackDetails> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackDetails> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public CreateCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(CreateCustomer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackRequestDTO that = (FeedbackRequestDTO) o;

        if (questionType != that.questionType) return false;
        if (id != that.id) return false;
        if (customerId != that.customerId) return false;
        if (isAddressed != that.isAddressed) return false;
        if (deviceId != that.deviceId) return false;
        if (outletId != that.outletId) return false;
        if (isNegative != that.isNegative) return false;
        if (answerId != that.answerId) return false;
        if (questionId != that.questionId) return false;
        if (rating != that.rating) return false;
        if (weightage != that.weightage) return false;
        if (feedbackDate != null ? !feedbackDate.equals(that.feedbackDate) : that.feedbackDate != null) return false;
        if (viewDate != null ? !viewDate.equals(that.viewDate) : that.viewDate != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (feedbacks != null ? !feedbacks.equals(that.feedbacks) : that.feedbacks != null) return false;
        if (tableNo != null ? !tableNo.equals(that.tableNo) : that.tableNo != null) return false;
        if (billNo != null ? !billNo.equals(that.billNo) : that.billNo != null) return false;
        if (customerName != null ? !customerName.equals(that.customerName) : that.customerName != null) return false;
        if (outletDesc != null ? !outletDesc.equals(that.outletDesc) : that.outletDesc != null) return false;
        if (mobileNo != null ? !mobileNo.equals(that.mobileNo) : that.mobileNo != null) return false;
        if (answerText != null ? !answerText.equals(that.answerText) : that.answerText != null) return false;
        if (answerDesc != null ? !answerDesc.equals(that.answerDesc) : that.answerDesc != null) return false;
        if (questionDesc != null ? !questionDesc.equals(that.questionDesc) : that.questionDesc != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (threshold != null ? !threshold.equals(that.threshold) : that.threshold != null) return false;
        if (dob != null ? !dob.equals(that.dob) : that.dob != null) return false;
        if (doa != null ? !doa.equals(that.doa) : that.doa != null) return false;
        if (locality != null ? !locality.equals(that.locality) : that.locality != null) return false;
        return customer != null ? customer.equals(that.customer) : that.customer == null;
    }

    @Override
    public int hashCode() {
        int result = (int) questionType;
        result = 31 * result + id;
        result = 31 * result + customerId;
        result = 31 * result + isAddressed;
        result = 31 * result + deviceId;
        result = 31 * result + isNegative;
        result = 31 * result + (feedbackDate != null ? feedbackDate.hashCode() : 0);
        result = 31 * result + (viewDate != null ? viewDate.hashCode() : 0);
        result = 31 * result + outletId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (feedbacks != null ? feedbacks.hashCode() : 0);
        result = 31 * result + (tableNo != null ? tableNo.hashCode() : 0);
        result = 31 * result + (billNo != null ? billNo.hashCode() : 0);
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (outletDesc != null ? outletDesc.hashCode() : 0);
        result = 31 * result + (mobileNo != null ? mobileNo.hashCode() : 0);
        result = 31 * result + answerId;
        result = 31 * result + questionId;
        result = 31 * result + (answerText != null ? answerText.hashCode() : 0);
        result = 31 * result + (answerDesc != null ? answerDesc.hashCode() : 0);
        result = 31 * result + (questionDesc != null ? questionDesc.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + weightage;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (threshold != null ? threshold.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (doa != null ? doa.hashCode() : 0);
        result = 31 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackRequestDTO{" +
                "questionType=" + questionType +
                ", id=" + id +
                ", customerId=" + customerId +
                ", deviceId=" + deviceId +
                ", isAddressed=" + isAddressed +
                ", feedbackDate=" + feedbackDate +
                ", viewDate='" + viewDate + '\'' +
                ", outletId=" + outletId +
                ", isNegative=" + isNegative +
                ", date='" + date + '\'' +
                ", feedbacks=" + feedbacks +
                ", tableNo='" + tableNo + '\'' +
                ", billNo='" + billNo + '\'' +
                ", customerName='" + customerName + '\'' +
                ", threshold='" + threshold + '\'' +
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
                ", customer=" + customer +
                '}';
    }
}
