package com.barbeque.bo;

/**
 * Created by System-2 on 1/17/2017.
 */
public class UpdateDeviceRequestBO {
    private int otp;
    private String fingerprint;
    private String androidDeviceId;
    private String installationId;
    private String storeId;

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getAndroidDeviceId() {
        return androidDeviceId;
    }

    public void setAndroidDeviceId(String androidDeviceId) {
        this.androidDeviceId = androidDeviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateDeviceRequestBO that = (UpdateDeviceRequestBO) o;

        if (otp != that.otp) return false;
        if (fingerprint != null ? !fingerprint.equals(that.fingerprint) : that.fingerprint != null) return false;
        if (androidDeviceId != null ? !androidDeviceId.equals(that.androidDeviceId) : that.androidDeviceId != null)
            return false;
        if (installationId != null ? !installationId.equals(that.installationId) : that.installationId != null)
            return false;
        return storeId != null ? storeId.equals(that.storeId) : that.storeId == null;
    }

    @Override
    public int hashCode() {
        int result = otp;
        result = 31 * result + (fingerprint != null ? fingerprint.hashCode() : 0);
        result = 31 * result + (androidDeviceId != null ? androidDeviceId.hashCode() : 0);
        result = 31 * result + (installationId != null ? installationId.hashCode() : 0);
        result = 31 * result + (storeId != null ? storeId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateDeviceRequestBO{" +
                "otp=" + otp +
                ", fingerprint='" + fingerprint + '\'' +
                ", androidDeviceId='" + androidDeviceId + '\'' +
                ", installationId='" + installationId + '\'' +
                ", storeId='" + storeId + '\'' +
                '}';
    }
}
