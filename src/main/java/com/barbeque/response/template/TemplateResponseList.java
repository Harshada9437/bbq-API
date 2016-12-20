package com.barbeque.response.template;

/**
 * Created by System-2 on 12/19/2016.
 */
public class TemplateResponseList
{
    private int templateId;
    private int outletId;
    private String templateDesc;
    private String status;
    private String outletDesc;
    private String shortDesc;


    public TemplateResponseList(int templateId, int outletId, String templateDesc, String status, String outletDesc, String shortDesc) {
        this.templateId = templateId;
        this.outletId = outletId;
        this.templateDesc = templateDesc;
        this.status = status;
        this.outletDesc = outletDesc;
        this.shortDesc = shortDesc;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
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
    public String toString() {
        return "TemplateResponseList{" +
                "templateId=" + templateId +
                ", outletId=" + outletId +
                ", templateDesc='" + templateDesc + '\'' +
                ", status='" + status + '\'' +
                ", outletDesc='" + outletDesc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                '}';
    }
}

