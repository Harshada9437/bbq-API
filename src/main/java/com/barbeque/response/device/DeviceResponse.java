package com.barbeque.response.device;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceResponse {
    private int id;
    private int feedbackId;
    private String serialNo;
    private String model;
    private String androidVersion;
    private String installationDate;
    private String status;
    private String feedbackDate;

    public DeviceResponse(int id, int feedbackId, String serialNo, String model, String androidVersion, String installationDate, String status, String feedbackDate) {
        this.id = id;
        this.feedbackId = feedbackId;
        this.serialNo = serialNo;
        this.model = model;
        this.androidVersion = androidVersion;
        this.installationDate = installationDate;
        this.status = status;
        this.feedbackDate = feedbackDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
                ", serialNo='" + serialNo + '\'' +
                ", model='" + model + '\'' +
                ", androidVersion='" + androidVersion + '\'' +
                ", installationDate='" + installationDate + '\'' +
                ", status='" + status + '\'' +
                ", feedbackDate='" + feedbackDate + '\'' +
                '}';
    }
}
