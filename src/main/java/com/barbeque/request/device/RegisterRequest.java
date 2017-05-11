package com.barbeque.request.device;

/**
 * Created by System-2 on 1/19/2017.
 */
public class RegisterRequest {
    private String storeId;
    private String installationId;
    private String androidDeviceId;
    private int clientId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }

    public String getAndroidDeviceId() {
        return androidDeviceId;
    }

    public void setAndroidDeviceId(String androidDeviceId) {
        this.androidDeviceId = androidDeviceId;
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

        RegisterRequest that = (RegisterRequest) o;

        if (clientId != that.clientId) return false;
        if (!storeId.equals(that.storeId)) return false;
        if (!installationId.equals(that.installationId)) return false;
        return androidDeviceId.equals(that.androidDeviceId);
    }

    @Override
    public int hashCode() {
        int result = storeId.hashCode();
        result = 31 * result + installationId.hashCode();
        result = 31 * result + androidDeviceId.hashCode();
        result = 31 * result + clientId;
        return result;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "storeId='" + storeId + '\'' +
                ", installationId='" + installationId + '\'' +
                ", androidDeviceId='" + androidDeviceId + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
