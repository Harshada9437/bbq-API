package com.barbeque.bo;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceStatusRequestBO {
    private String androidDeviceId;
    private String status;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceStatusRequestBO that = (DeviceStatusRequestBO) o;

        if (androidDeviceId != null ? !androidDeviceId.equals(that.androidDeviceId) : that.androidDeviceId != null)
            return false;
        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        int result = androidDeviceId != null ? androidDeviceId.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeviceStatusRequestBO{" +
                "androidDeviceId='" + androidDeviceId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
