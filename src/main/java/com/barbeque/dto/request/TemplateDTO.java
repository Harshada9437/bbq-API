package com.barbeque.dto.request;

/**
 * Created by System1 on 9/9/2016.
 */
public class TemplateDTO {
    private int id;
    private int outletId;
    private String templateDesc;
    private String status;
    private String outletDesc;
    private String shortDesc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOutletDesc() {
        return outletDesc;
    }

    public void setOutletDesc(String outletDesc) {
        this.outletDesc = outletDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateDTO that = (TemplateDTO) o;

        if (id != that.id) return false;
        if (outletId != that.outletId) return false;
        if (templateDesc != null ? !templateDesc.equals(that.templateDesc) : that.templateDesc != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (outletDesc != null ? !outletDesc.equals(that.outletDesc) : that.outletDesc != null) return false;
        return shortDesc != null ? shortDesc.equals(that.shortDesc) : that.shortDesc == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + outletId;
        result = 31 * result + (templateDesc != null ? templateDesc.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (outletDesc != null ? outletDesc.hashCode() : 0);
        result = 31 * result + (shortDesc != null ? shortDesc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TemplateDTO{" +
                "id=" + id +
                ", outletId=" + outletId +
                ", templateDesc='" + templateDesc + '\'' +
                ", status='" + status + '\'' +
                ", outletDesc='" + outletDesc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                '}';
    }
}
