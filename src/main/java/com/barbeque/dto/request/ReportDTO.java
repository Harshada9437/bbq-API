package com.barbeque.dto.request;

/**
 * Created by System-2 on 3/14/2017.
 */
public class ReportDTO {
    private int totalCount;
    private int negativeCount;
    private int addressedCount;
    private String userName;

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

    public int getAddressedCount() {
        return addressedCount;
    }

    public void setAddressedCount(int addressedCount) {
        this.addressedCount = addressedCount;
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
        if (addressedCount != reportDTO.addressedCount) return false;
        return userName != null ? userName.equals(reportDTO.userName) : reportDTO.userName == null;
    }

    @Override
    public int hashCode() {
        int result = totalCount;
        result = 31 * result + negativeCount;
        result = 31 * result + addressedCount;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "totalCount=" + totalCount +
                ", negativeCount=" + negativeCount +
                ", addressedCount=" + addressedCount +
                ", userName='" + userName + '\'' +
                '}';
    }
}
