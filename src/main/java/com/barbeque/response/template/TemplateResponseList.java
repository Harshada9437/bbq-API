package com.barbeque.response.template;

import com.barbeque.dto.request.Outlets;

import java.util.List;


/**
 * Created by System-2 on 12/19/2016.
 */
public class TemplateResponseList
{
    private int templateId;
    private List<Outlets> outlets;
    private String templateDesc;
    private String status;



    public TemplateResponseList(int templateId, List<Outlets> outlets, String templateDesc, String status) {
        this.templateId = templateId;
        this.outlets=outlets;
        this.templateDesc = templateDesc;
        this.status = status;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
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

    public List<Outlets> getOutlets() {
        return outlets;
    }

    @Override
    public String toString() {
        return "TemplateResponseList{" +
                "templateId=" + templateId +
                ", outlets=" + outlets +
                ", templateDesc='" + templateDesc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

