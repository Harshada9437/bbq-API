package com.barbeque.dto;

/**
 * Created by System-2 on 12/27/2016.
 */
public class UpdateSettingsDTO {
    private int mobileNoLength;
    private String bannerUrl;
    private String tableNoRange;

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
        if (bannerUrl != null ? !bannerUrl.equals(that.bannerUrl) : that.bannerUrl != null) return false;
        return tableNoRange != null ? tableNoRange.equals(that.tableNoRange) : that.tableNoRange == null;

    }

    @Override
    public int hashCode() {
        int result = mobileNoLength;
        result = 31 * result + (bannerUrl != null ? bannerUrl.hashCode() : 0);
        result = 31 * result + (tableNoRange != null ? tableNoRange.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateSettingsDTO{" +
                "mobileNoLength=" + mobileNoLength +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", tableNoRange='" + tableNoRange + '\'' +
                '}';
    }
}
