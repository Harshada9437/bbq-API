package com.barbeque.dto.request;

/**
 * Created by System-3 on 2/15/2017.
 */
public class FeedbackDTO {
    private int id;
    private String outletDesc;
    private String feedbackDate;
    private String tableNo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOutletDesc() {
        return outletDesc;
    }

    public void setOutletDesc(String outletDesc) {
        this.outletDesc = outletDesc;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackDTO that = (FeedbackDTO) o;

        if (id != that.id) return false;
        if (!outletDesc.equals(that.outletDesc)) return false;
        if (!feedbackDate.equals(that.feedbackDate)) return false;
        return tableNo.equals(that.tableNo);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + outletDesc.hashCode();
        result = 31 * result + feedbackDate.hashCode();
        result = 31 * result + tableNo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackDTO{" +
                "id=" + id +
                ", outletDesc='" + outletDesc + '\'' +
                ", feedbackDate='" + feedbackDate + '\'' +
                ", tableNo='" + tableNo + '\'' +
                '}';
    }
}
