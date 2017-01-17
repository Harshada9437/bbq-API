package com.barbeque.bo;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceRequestBO {
    private String serialNo;
    private String model;
    private String androidVersion;
    private String installationDate;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceRequestBO that = (DeviceRequestBO) o;

        if (serialNo != null ? !serialNo.equals(that.serialNo) : that.serialNo != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (androidVersion != null ? !androidVersion.equals(that.androidVersion) : that.androidVersion != null)
            return false;
        return installationDate != null ? installationDate.equals(that.installationDate) : that.installationDate == null;
    }

    @Override
    public int hashCode() {
        int result = serialNo != null ? serialNo.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (androidVersion != null ? androidVersion.hashCode() : 0);
        result = 31 * result + (installationDate != null ? installationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeviceRequestBO{" +
                "serialNo='" + serialNo + '\'' +
                ", model='" + model + '\'' +
                ", androidVersion='" + androidVersion + '\'' +
                ", installationDate='" + installationDate + '\'' +
                '}';
    }
}
