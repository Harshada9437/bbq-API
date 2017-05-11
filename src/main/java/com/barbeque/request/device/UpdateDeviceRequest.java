package com.barbeque.request.device;

/**
 * Created by System-2 on 1/17/2017.
 */
public class UpdateDeviceRequest {
    private int otp;
    private String fingerprint;
    private String androidDeviceId;
    private String installationId;
    private String storeId;
    private int clientId;


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

        UpdateDeviceRequest that = (UpdateDeviceRequest) o;

        if (otp != that.otp) return false;
        if (clientId != that.clientId) return false;
        if (!fingerprint.equals(that.fingerprint)) return false;
        if (!androidDeviceId.equals(that.androidDeviceId)) return false;
        if (!installationId.equals(that.installationId)) return false;
        return storeId.equals(that.storeId);
    }

    @Override
    public int hashCode() {
        int result = otp;
        result = 31 * result + fingerprint.hashCode();
        result = 31 * result + androidDeviceId.hashCode();
        result = 31 * result + installationId.hashCode();
        result = 31 * result + storeId.hashCode();
        result = 31 * result + clientId;
        return result;
    }

    @Override
    public String toString() {
        return "UpdateDeviceRequest{" +
                "otp=" + otp +
                ", fingerprint='" + fingerprint + '\'' +
                ", androidDeviceId='" + androidDeviceId + '\'' +
                ", installationId='" + installationId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
