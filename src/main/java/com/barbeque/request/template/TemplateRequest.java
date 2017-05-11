package com.barbeque.request.template;

/**
 * Created by System1 on 9/9/2016.
 */
public class TemplateRequest {
    private String templateDesc;
    private int clientId;

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateRequest that = (TemplateRequest) o;

        if (clientId != that.clientId) return false;
        return templateDesc != null ? templateDesc.equals(that.templateDesc) : that.templateDesc == null;
    }

    @Override
    public int hashCode() {
        int result = templateDesc != null ? templateDesc.hashCode() : 0;
        result = 31 * result + clientId;
        return result;
    }

    @Override
    public String toString() {
        return "TemplateRequest{" +
                "templateDesc='" + templateDesc + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
