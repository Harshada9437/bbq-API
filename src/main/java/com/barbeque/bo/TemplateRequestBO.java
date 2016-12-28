package com.barbeque.bo;

/**
 * Created by System1 on 9/9/2016.
 */
public class TemplateRequestBO {
    private String templateDesc;
    private String status;

    public String getTemplateDesc() {return templateDesc;}

    public void setTemplateDesc(String templateDesc) {this.templateDesc = templateDesc;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateRequestBO that = (TemplateRequestBO) o;

        if (templateDesc != null ? !templateDesc.equals(that.templateDesc) : that.templateDesc != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;

    }

    @Override
    public int hashCode() {
        int result = templateDesc != null ? templateDesc.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TemplateRequestBO{" +
                "templateDesc='" + templateDesc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
