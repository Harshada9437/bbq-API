package com.barbeque.dto.request;

import java.util.List;

/**
 * Created by System1 on 9/9/2016.
 */
public class TemplateDTO {
    private int id;
    private List<Outlets> outlets;
    private String templateDesc;
    private String status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Outlets> getOutlets() {
        return outlets;
    }

    public void setOutlets(List<Outlets> outlets) {
        this.outlets = outlets;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateDTO that = (TemplateDTO) o;

        if (id != that.id) return false;
        if (outlets != null ? !outlets.equals(that.outlets) : that.outlets != null) return false;
        if (templateDesc != null ? !templateDesc.equals(that.templateDesc) : that.templateDesc != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (outlets != null ? outlets.hashCode() : 0);
        result = 31 * result + (templateDesc != null ? templateDesc.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TemplateDTO{" +
                "id=" + id +
                ", outlets=" + outlets +
                ", templateDesc='" + templateDesc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
