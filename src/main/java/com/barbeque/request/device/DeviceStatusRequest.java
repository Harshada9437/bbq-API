package com.barbeque.request.device;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceStatusRequest {
    private String androidDeviceId;
    private String status;
    private int clientId;

    public String getAndroidDeviceId() {
        return androidDeviceId;
    }

    public void setAndroidDeviceId(String androidDeviceId) {
        this.androidDeviceId = androidDeviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

        DeviceStatusRequest that = (DeviceStatusRequest) o;

        if (clientId != that.clientId) return false;
        if (!androidDeviceId.equals(that.androidDeviceId)) return false;
        return status.equals(that.status);
    }

    @Override
    public int hashCode() {
        int result = androidDeviceId.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + clientId;
        return result;
    }

    @Override
    public String toString() {
        return "DeviceStatusRequest{" +
                "androidDeviceId='" + androidDeviceId + '\'' +
                ", status='" + status + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
