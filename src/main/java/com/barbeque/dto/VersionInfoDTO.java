package com.barbeque.dto;

/**
 * Created by Sandeep on 1/20/2017.
 */
public class VersionInfoDTO {
    private int versionCode;
    private String versionNumber;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VersionInfoDTO that = (VersionInfoDTO) o;

        if (versionCode != that.versionCode) return false;
        return versionNumber != null ? versionNumber.equals(that.versionNumber) : that.versionNumber == null;
    }

    @Override
    public int hashCode() {
        int result = versionCode;
        result = 31 * result + (versionNumber != null ? versionNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VersionInfoDTO{" +
                "versionCode=" + versionCode +
                ", versionNumber='" + versionNumber + '\'' +
                '}';
    }
}
