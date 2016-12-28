package com.barbeque.dto.request;

/**
 * Created by System-2 on 12/20/2016.
 */
public class TempDTO
{

    private int templateId;
    private String fromDate;
    private String toDate;

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
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

        TempDTO tempDTO = (TempDTO) o;

        if (templateId != tempDTO.templateId) return false;
        if (fromDate != null ? !fromDate.equals(tempDTO.fromDate) : tempDTO.fromDate != null) return false;
        return toDate != null ? toDate.equals(tempDTO.toDate) : tempDTO.toDate == null;

    }

    @Override
    public int hashCode() {
        int result = templateId;
        result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TempDTO{" +
                "templateId=" + templateId +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                '}';
    }
}
