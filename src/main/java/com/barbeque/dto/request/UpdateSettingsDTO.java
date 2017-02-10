package com.barbeque.dto.request;

/**
 * Created by System-2 on 12/27/2016.
 */
public class UpdateSettingsDTO {
    private int mobileNoLength;
    private Integer smsGatewayId;
    private String bannerUrl;
    private String tableNoRange;
    private String pocName;
    private String pocMobile;
    private String pocEmail;
    private String mgrName;
    private String mgrMobile;
    private String mgrEmail;

    public Integer getSmsGatewayId() {
        return smsGatewayId;
    }

    public void setSmsGatewayId(Integer smsGatewayId) {
        this.smsGatewayId = smsGatewayId;
    }

    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    public String getMgrMobile() {
        return mgrMobile;
    }

    public void setMgrMobile(String mgrMobile) {
        this.mgrMobile = mgrMobile;
    }

    public String getMgrEmail() {
        return mgrEmail;
    }

    public void setMgrEmail(String mgrEmail) {
        this.mgrEmail = mgrEmail;
    }

    public String getPocName() {
        return pocName;
    }

    public void setPocName(String pocName) {
        this.pocName = pocName;
    }

    public String getPocMobile() {
        return pocMobile;
    }

    public void setPocMobile(String pocMobile) {
        this.pocMobile = pocMobile;
    }

    public String getPocEmail() {
        return pocEmail;
    }

    public void setPocEmail(String pocEmail) {
        this.pocEmail = pocEmail;
    }

    public String getTableNoRange() {return tableNoRange;}

    public void setTableNoRange(String tableNoRange) {this.tableNoRange = tableNoRange;}

    public int getMobileNoLength() {
        return mobileNoLength;
    }

    public void setMobileNoLength(int mobileNoLength) {
        this.mobileNoLength = mobileNoLength;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateSettingsDTO that = (UpdateSettingsDTO) o;

        if (mobileNoLength != that.mobileNoLength) return false;
        if (smsGatewayId != that.smsGatewayId) return false;
        if (bannerUrl != null ? !bannerUrl.equals(that.bannerUrl) : that.bannerUrl != null) return false;
        if (tableNoRange != null ? !tableNoRange.equals(that.tableNoRange) : that.tableNoRange != null) return false;
        if (pocName != null ? !pocName.equals(that.pocName) : that.pocName != null) return false;
        if (pocMobile != null ? !pocMobile.equals(that.pocMobile) : that.pocMobile != null) return false;
        if (pocEmail != null ? !pocEmail.equals(that.pocEmail) : that.pocEmail != null) return false;
        if (mgrName != null ? !mgrName.equals(that.mgrName) : that.mgrName != null) return false;
        if (mgrMobile != null ? !mgrMobile.equals(that.mgrMobile) : that.mgrMobile != null) return false;
        return mgrEmail != null ? mgrEmail.equals(that.mgrEmail) : that.mgrEmail == null;
    }

    @Override
    public int hashCode() {
        int result = mobileNoLength;
        result = 31 * result + smsGatewayId;
        result = 31 * result + (bannerUrl != null ? bannerUrl.hashCode() : 0);
        result = 31 * result + (tableNoRange != null ? tableNoRange.hashCode() : 0);
        result = 31 * result + (pocName != null ? pocName.hashCode() : 0);
        result = 31 * result + (pocMobile != null ? pocMobile.hashCode() : 0);
        result = 31 * result + (pocEmail != null ? pocEmail.hashCode() : 0);
        result = 31 * result + (mgrName != null ? mgrName.hashCode() : 0);
        result = 31 * result + (mgrMobile != null ? mgrMobile.hashCode() : 0);
        result = 31 * result + (mgrEmail != null ? mgrEmail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateSettingsDTO{" +
                "mobileNoLength=" + mobileNoLength +
                ", smsGatewayId=" + smsGatewayId +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", tableNoRange='" + tableNoRange + '\'' +
                ", pocName='" + pocName + '\'' +
                ", pocMobile='" + pocMobile + '\'' +
                ", pocEmail='" + pocEmail + '\'' +
                ", mgrName='" + mgrName + '\'' +
                ", mgrMobile='" + mgrMobile + '\'' +
                ", mgrEmail='" + mgrEmail + '\'' +
                '}';
    }
}
