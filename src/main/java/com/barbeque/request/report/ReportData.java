package com.barbeque.request.report;

/**
 * Created by System-2 on 3/15/2017.
 */
public class ReportData {
    private String storeId;
    private String city;
    private String firstFeedback;
    private String lastFeedback;
    private int dailyBillCount;
    private int monthlyBillCount;
    private int totalCount;
    private int negativeCount;
    private int unAddressedCount;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirstFeedback() {
        return firstFeedback;
    }

    public void setFirstFeedback(String firstFeedback) {
        this.firstFeedback = firstFeedback;
    }

    public String getLastFeedback() {
        return lastFeedback;
    }

    public void setLastFeedback(String lastFeedback) {
        this.lastFeedback = lastFeedback;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getNegativeCount() {
        return negativeCount;
    }

    public void setNegativeCount(int negativeCount) {
        this.negativeCount = negativeCount;
    }

    public int getUnAddressedCount() {
        return unAddressedCount;
    }

    public void setUnAddressedCount(int unAddressedCount) {
        this.unAddressedCount = unAddressedCount;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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


    @Override
    public String toString() {
        return "ReportData{" +
                "storeId='" + storeId + '\'' +
                ", city='" + city + '\'' +
                ", firstFeedback='" + firstFeedback + '\'' +
                ", lastFeedback='" + lastFeedback + '\'' +
                ", dailyBillCount=" + dailyBillCount +
                ", monthlyBillCount=" + monthlyBillCount +
                ", totalCount=" + totalCount +
                ", negativeCount=" + negativeCount +
                ", unAddressedCount=" + unAddressedCount +
                '}';
    }
}
