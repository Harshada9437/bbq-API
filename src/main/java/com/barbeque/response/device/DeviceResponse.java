package com.barbeque.response.device;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceResponse {
    private int id;
    private int feedbackId;
    private String installationId;
    private String fingerprint;
    private String androidDeviceId;
    private String installationDate;
    private String storeId;
    private String outletName;
    private String regionName;
    private String status;
    private String feedbackDate;

    public DeviceResponse(String storeId,String outletName,String regionName,int id, int feedbackId, String installationId, String fingerprint, String androidDeviceId, String installationDate, String status, String feedbackDate) {
        this.storeId = storeId;
        this.outletName = outletName;
        this.regionName = regionName;
        this.id = id;
        this.feedbackId = feedbackId;
        this.installationId = installationId;
        this.fingerprint = fingerprint;
        this.androidDeviceId = androidDeviceId;
        this.installationDate = installationDate;
        this.status = status;
        this.feedbackDate = feedbackDate;
    }

    public String getOutletName() {
        return outletName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
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

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFeedbackId() {return feedbackId;}

    public void setFeedbackId(int feedbackId) {this.feedbackId = feedbackId;}

    public String getFeedbackDate() {return feedbackDate;}

    public void setFeedbackDate(String feedbackDate) {this.feedbackDate = feedbackDate;}

    @Override
    public String toString() {
        return "DeviceResponse{" +
                "id=" + id +
                ", feedbackId=" + feedbackId +
                ", storeId='" + storeId + '\'' +
                ", outletName='" + outletName + '\'' +
                ", regionName='" + regionName + '\'' +
                ", installationId='" + installationId + '\'' +
                ", fingerprint='" + fingerprint + '\'' +
                ", androidDeviceId='" + androidDeviceId + '\'' +
                ", installationDate='" + installationDate + '\'' +
                ", status='" + status + '\'' +
                ", feedbackDate='" + feedbackDate + '\'' +
                '}';
    }
}
