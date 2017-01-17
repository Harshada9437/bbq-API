package com.barbeque.request.device;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceStatusRequest {
    private int id;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        DeviceStatusRequest that = (DeviceStatusRequest) o;

        if (id != that.id) return false;
        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeviceStatusRequest{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
