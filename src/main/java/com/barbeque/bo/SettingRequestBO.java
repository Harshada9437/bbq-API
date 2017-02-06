package com.barbeque.bo;

/**
 * Created by System-2 on 2/6/2017.
 */
public class SettingRequestBO {
    private String smsTemplate;

    public String getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingRequestBO that = (SettingRequestBO) o;

        return smsTemplate != null ? smsTemplate.equals(that.smsTemplate) : that.smsTemplate == null;
    }

    @Override
    public int hashCode() {
        return smsTemplate != null ? smsTemplate.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SettingRequestBO{" +
                "smsTemplate='" + smsTemplate + '\'' +
                '}';
    }
}
