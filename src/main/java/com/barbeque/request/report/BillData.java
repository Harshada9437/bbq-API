package com.barbeque.request.report;

import java.util.List;

/**
 * Created by System-2 on 3/15/2017.
 */
public class BillData {
    private List<ReportData> outlets;

    public List<ReportData> getOutlets() {
        return outlets;
    }

    public void setOutlets(List<ReportData> outlets) {
        this.outlets = outlets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillData that = (BillData) o;

        return outlets != null ? outlets.equals(that.outlets) : that.outlets == null;
    }

    @Override
    public int hashCode() {
        return outlets != null ? outlets.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BillData{" +
                "outlets=" + outlets +
                '}';
    }
}
