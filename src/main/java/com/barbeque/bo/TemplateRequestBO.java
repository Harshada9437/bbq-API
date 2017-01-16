package com.barbeque.bo;

/**
 * Created by System1 on 9/9/2016.
 */
public class TemplateRequestBO {
    private String templateDesc;

    public String getTemplateDesc() {return templateDesc;}

    public void setTemplateDesc(String templateDesc) {this.templateDesc = templateDesc;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateRequestBO that = (TemplateRequestBO) o;

        return templateDesc != null ? templateDesc.equals(that.templateDesc) : that.templateDesc == null;
    }

    @Override
    public int hashCode() {
        return templateDesc != null ? templateDesc.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TemplateRequestBO{" +
                "templateDesc='" + templateDesc + '\'' +
                '}';
    }
}
