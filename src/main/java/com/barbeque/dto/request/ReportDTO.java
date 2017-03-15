package com.barbeque.dto.request;

import com.barbeque.request.report.ReportData;

import java.util.List;

/**
 * Created by System-2 on 3/14/2017.
 */
public class ReportDTO {
    private int totalCount;
    private int negativeCount;
    private int unAddressedCount;
    private int dailyBillCount;
    private int monthlyBillCount;
    private String userName;
    private List<ReportData> outlets;

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

    public List<ReportData> getOutlets() {
        return outlets;
    }

    public void setOutlets(List<ReportData> outlets) {
        this.outlets = outlets;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportDTO reportDTO = (ReportDTO) o;

        if (totalCount != reportDTO.totalCount) return false;
        if (negativeCount != reportDTO.negativeCount) return false;
        if (unAddressedCount != reportDTO.unAddressedCount) return false;
        if (dailyBillCount != reportDTO.dailyBillCount) return false;
        if (monthlyBillCount != reportDTO.monthlyBillCount) return false;
        if (userName != null ? !userName.equals(reportDTO.userName) : reportDTO.userName != null) return false;
        return outlets != null ? outlets.equals(reportDTO.outlets) : reportDTO.outlets == null;
    }

    @Override
    public int hashCode() {
        int result = totalCount;
        result = 31 * result + negativeCount;
        result = 31 * result + monthlyBillCount;
        result = 31 * result + dailyBillCount;
        result = 31 * result + unAddressedCount;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (outlets != null ? outlets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "totalCount=" + totalCount +
                ", negativeCount=" + negativeCount +
                ", monthlyBillCount=" + monthlyBillCount +
                ", dailyBillCount=" + dailyBillCount +
                ", unAddressedCount=" + unAddressedCount +
                ", userName='" + userName + '\'' +
                ", outlets=" + outlets +
                '}';
    }
}
