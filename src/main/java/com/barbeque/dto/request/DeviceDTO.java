package com.barbeque.dto.request;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceDTO {
    private int id;
    private int feedbackId;
    private String feedbackDate;
    private String serialNo;
    private String model;
    private String androidVersion;
    private String buildNo;
    private String installationDate;
    private String status;

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

    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceDTO deviceDTO = (DeviceDTO) o;

        if (id != deviceDTO.id) return false;
        if (feedbackId != deviceDTO.feedbackId) return false;
        if (feedbackDate != null ? !feedbackDate.equals(deviceDTO.feedbackDate) : deviceDTO.feedbackDate != null)
            return false;
        if (serialNo != null ? !serialNo.equals(deviceDTO.serialNo) : deviceDTO.serialNo != null) return false;
        if (model != null ? !model.equals(deviceDTO.model) : deviceDTO.model != null) return false;
        if (androidVersion != null ? !androidVersion.equals(deviceDTO.androidVersion) : deviceDTO.androidVersion != null)
            return false;
        if (buildNo != null ? !buildNo.equals(deviceDTO.buildNo) : deviceDTO.buildNo != null) return false;
        if (installationDate != null ? !installationDate.equals(deviceDTO.installationDate) : deviceDTO.installationDate != null)
            return false;
        return status != null ? status.equals(deviceDTO.status) : deviceDTO.status == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + feedbackId;
        result = 31 * result + (feedbackDate != null ? feedbackDate.hashCode() : 0);
        result = 31 * result + (serialNo != null ? serialNo.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (androidVersion != null ? androidVersion.hashCode() : 0);
        result = 31 * result + (buildNo != null ? buildNo.hashCode() : 0);
        result = 31 * result + (installationDate != null ? installationDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeviceDTO{" +
                "id=" + id +
                ", feedbackId=" + feedbackId +
                ", feedbackDate='" + feedbackDate + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", model='" + model + '\'' +
                ", androidVersion='" + androidVersion + '\'' +
                ", buildNo='" + buildNo + '\'' +
                ", installationDate='" + installationDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
