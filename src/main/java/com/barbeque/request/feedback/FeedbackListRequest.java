package com.barbeque.request.feedback;

import java.util.List;

/**
 * Created by System-2 on 1/25/2017.
 */
public class FeedbackListRequest {
    private String fromDate;
    private String toDate;
    private List<Integer> outletId;
    private String tableNo;

    public List<Integer> getOutletId() {
        return outletId;
    }

    public void setOutletId(List<Integer> outletId) {
        this.outletId = outletId;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackListRequest that = (FeedbackListRequest) o;

        if (outletId != that.outletId) return false;
        if (fromDate != null ? !fromDate.equals(that.fromDate) : that.fromDate != null) return false;
        if (toDate != null ? !toDate.equals(that.toDate) : that.toDate != null) return false;
        return tableNo != null ? tableNo.equals(that.tableNo) : that.tableNo == null;
    }

    @Override
    public int hashCode() {

        int result = fromDate != null ? fromDate.hashCode() : 0;
        result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
        result = 31 * result + (outletId != null ? outletId.hashCode() : 0);
        result = 31 * result + (tableNo != null ? tableNo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackListRequest{" +
                "fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", outletId=" + outletId +
                ", tableNo='" + tableNo + '\'' +
                '}';
    }
}
