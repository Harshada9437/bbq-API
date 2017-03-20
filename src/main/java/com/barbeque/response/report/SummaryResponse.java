package com.barbeque.response.report;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-2 on 3/20/2017.
 */
public class SummaryResponse implements GenericResponse {
    private int dailyFeedback;
    private int monthlyFeedback;
    private float avgDailyFeedback;
    private float avgMonthlyFeedback;
    private int dailyNegativeCount;
    private int monthlyNegativeCount;
    private int dailyUnAddressedCount;
    private int monthlyUnAddressedCount;
    private float avgDailyUnaddressed;
    private float avgMonthlyUnaddressed;
    private int dailyBillCount;
    private int monthlyBillCount;
    private List<OutletSummery> outlets;
    private String message;
    private String messageType;

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

    public int getDailyFeedback() {
        return dailyFeedback;
    }

    public void setDailyFeedback(int dailyFeedback) {
        this.dailyFeedback = dailyFeedback;
    }

    public int getMonthlyFeedback() {
        return monthlyFeedback;
    }

    public void setMonthlyFeedback(int monthlyFeedback) {
        this.monthlyFeedback = monthlyFeedback;
    }

    public float getAvgDailyFeedback() {
        return avgDailyFeedback;
    }

    public void setAvgDailyFeedback(float avgDailyFeedback) {
        this.avgDailyFeedback = avgDailyFeedback;
    }

    public float getAvgMonthlyFeedback() {
        return avgMonthlyFeedback;
    }

    public void setAvgMonthlyFeedback(float avgMonthlyFeedback) {
        this.avgMonthlyFeedback = avgMonthlyFeedback;
    }

    public int getDailyNegativeCount() {
        return dailyNegativeCount;
    }

    public void setDailyNegativeCount(int dailyNegativeCount) {
        this.dailyNegativeCount = dailyNegativeCount;
    }

    public int getMonthlyNegativeCount() {
        return monthlyNegativeCount;
    }

    public void setMonthlyNegativeCount(int monthlyNegativeCount) {
        this.monthlyNegativeCount = monthlyNegativeCount;
    }

    public int getDailyUnAddressedCount() {
        return dailyUnAddressedCount;
    }

    public void setDailyUnAddressedCount(int dailyUnAddressedCount) {
        this.dailyUnAddressedCount = dailyUnAddressedCount;
    }

    public int getMonthlyUnAddressedCount() {
        return monthlyUnAddressedCount;
    }

    public void setMonthlyUnAddressedCount(int monthlyUnAddressedCount) {
        this.monthlyUnAddressedCount = monthlyUnAddressedCount;
    }

    public float getAvgDailyUnaddressed() {
        return avgDailyUnaddressed;
    }

    public void setAvgDailyUnaddressed(float avgDailyUnaddressed) {
        this.avgDailyUnaddressed = avgDailyUnaddressed;
    }

    public float getAvgMonthlyUnaddressed() {
        return avgMonthlyUnaddressed;
    }

    public void setAvgMonthlyUnaddressed(float avgMonthlyUnaddressed) {
        this.avgMonthlyUnaddressed = avgMonthlyUnaddressed;
    }

    public int getDailyBillCount() {
        return dailyBillCount;
    }

    public void setDailyBillCount(int dailyBillCount) {
        this.dailyBillCount = dailyBillCount;
    }

    public int getMonthlyBillCount() {
        return monthlyBillCount;
    }

    public void setMonthlyBillCount(int monthlyBillCount) {
        this.monthlyBillCount = monthlyBillCount;
    }

    public List<OutletSummery> getOutlets() {
        return outlets;
    }

    public void setOutlets(List<OutletSummery> outlets) {
        this.outlets = outlets;
    }

    @Override
    public String toString() {
        return "SummaryResponse{" +
                "dailyFeedback=" + dailyFeedback +
                ", monthlyFeedback=" + monthlyFeedback +
                ", avgDailyFeedback=" + avgDailyFeedback +
                ", avgMonthlyFeedback=" + avgMonthlyFeedback +
                ", dailyNegativeCount=" + dailyNegativeCount +
                ", monthlyNegativeCount=" + monthlyNegativeCount +
                ", dailyUnAddressedCount=" + dailyUnAddressedCount +
                ", monthlyUnAddressedCount=" + monthlyUnAddressedCount +
                ", avgDailyUnaddressed=" + avgDailyUnaddressed +
                ", avgMonthlyUnaddressed=" + avgMonthlyUnaddressed +
                ", dailyBillCount=" + dailyBillCount +
                ", monthlyBillCount=" + monthlyBillCount +
                ", outlets=" + outlets +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
