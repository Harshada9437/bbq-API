package com.barbeque.response.report;

/**
 * Created by System-2 on 3/20/2017.
 */
public class OutletSummery {
    private String city;
    private String outletName;
    private int dailyFeedback;
    private int monthlyFeedback;
    private int dailyNegativeCount;
    private int monthlyNegativeCount;
    private int dailyUnAddressedCount;
    private int monthlyUnAddressedCount;
    private int dailyBillCount;
    private int monthlyBillCount;
    private float avgCount;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
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

    public float getAvgCount() {
        return avgCount;
    }

    public void setAvgCount(float avgCount) {
        this.avgCount = avgCount;
    }

    @Override
    public String toString() {
        return "OutletSummery{" +
                "city='" + city + '\'' +
                ", outletName='" + outletName + '\'' +
                ", dailyFeedback=" + dailyFeedback +
                ", monthlyFeedback=" + monthlyFeedback +
                ", dailyNegativeCount=" + dailyNegativeCount +
                ", monthlyNegativeCount=" + monthlyNegativeCount +
                ", dailyUnAddressedCount=" + dailyUnAddressedCount +
                ", monthlyUnAddressedCount=" + monthlyUnAddressedCount +
                ", dailyBillCount=" + dailyBillCount +
                ", monthlyBillCount=" + monthlyBillCount +
                ", avgCount=" + avgCount +
                '}';
    }
}
