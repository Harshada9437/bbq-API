package com.barbeque.dto.request;

import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.response.feedback.CreateCustomer;

import java.util.List;

/**
 * Created by user on 10/18/2016.
 */
public class FeedbackRequestDTO {
    private int id;
    private int customerId;
    private String createdOn;
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

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
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

        if (id != that.id) return false;
        if (answerId != that.answerId) return false;
        if (questionId != that.questionId) return false;
        if (rating != that.rating) return false;
        if (weightage != that.weightage) return false;
        if (answerText != null ? !answerText.equals(that.answerText) : that.answerText != null) return false;
        if (answerDesc != null ? !answerDesc.equals(that.answerDesc) : that.answerDesc != null) return false;
        if (questionDesc != null ? !questionDesc.equals(that.questionDesc) : that.questionDesc != null) return false;
        if (customerId != that.customerId) return false;
        if (outletId != that.outletId) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (modifiedOn != null ? !modifiedOn.equals(that.modifiedOn) : that.modifiedOn != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (feedbacks != null ? !feedbacks.equals(that.feedbacks) : that.feedbacks != null) return false;
        if (tableNo != null ? !tableNo.equals(that.tableNo) : that.tableNo != null) return false;
        if (billNo != null ? !billNo.equals(that.billNo) : that.billNo != null) return false;
        if (customerName != null ? !customerName.equals(that.customerName) : that.customerName != null) return false;
        if (outletDesc != null ? !outletDesc.equals(that.outletDesc) : that.outletDesc != null) return false;
        if (mobileNo != null ? !mobileNo.equals(that.mobileNo) : that.mobileNo != null) return false;
        return customer != null ? customer.equals(that.customer) : that.customer == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + customerId;
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (modifiedOn != null ? modifiedOn.hashCode() : 0);
        result = 31 * result + outletId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (feedbacks != null ? feedbacks.hashCode() : 0);
        result = 31 * result + (tableNo != null ? tableNo.hashCode() : 0);
        result = 31 * result + (billNo != null ? billNo.hashCode() : 0);
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (outletDesc != null ? outletDesc.hashCode() : 0);
        result = 31 * result + (mobileNo != null ? mobileNo.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + questionId;
        result = 31 * result + (answerText != null ? answerText.hashCode() : 0);
        result = 31 * result + (answerDesc != null ? answerDesc.hashCode() : 0);
        result = 31 * result + (questionDesc != null ? questionDesc.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + weightage;
        result = 31 * result + answerId;
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackRequestDTO{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", createdOn='" + createdOn + '\'' +
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
                ", customer=" + customer +
                '}';
    }
}
