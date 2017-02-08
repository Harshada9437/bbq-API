package com.barbeque.request.user;

/**
 * Created by System-2 on 2/8/2017.
 */
public class UpdateSettingRequest {
    private int id;
    private String api;
    private String name;
    private String senderId;
    private String campaign;
    private String countryCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateSettingRequest that = (UpdateSettingRequest) o;

        if (id != that.id) return false;
        if (api != null ? !api.equals(that.api) : that.api != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (senderId != null ? !senderId.equals(that.senderId) : that.senderId != null) return false;
        if (campaign != null ? !campaign.equals(that.campaign) : that.campaign != null) return false;
        return countryCode != null ? countryCode.equals(that.countryCode) : that.countryCode == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (api != null ? api.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (senderId != null ? senderId.hashCode() : 0);
        result = 31 * result + (campaign != null ? campaign.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateSettingRequest{" +
                "id=" + id +
                ", api='" + api + '\'' +
                ", name='" + name + '\'' +
                ", senderId='" + senderId + '\'' +
                ", campaign='" + campaign + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
