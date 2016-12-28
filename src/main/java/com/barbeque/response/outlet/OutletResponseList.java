package com.barbeque.response.outlet;

import com.barbeque.response.util.GenericResponse;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletResponseList implements GenericResponse
{
    private int id;
    private String outletDesc;
    private String shortDesc;
    private int clusterId;
    private String clusterName;
    private int regionId;
    private String regionName;
    private int companyId;
    private String companyName;
    private String tableNoRange;
    private int  groupId;
    private int  mobileNoLength;
    private String  groupName;
    private String  message;
    private String  messageType;
    private String  templateName;
    private String  bannerUrl;
    private int posStoreId;
    private int templateId;

    public String getTableNoRange() {
        return tableNoRange;
    }

    public void setTableNoRange(String tableNoRange) {
        this.tableNoRange = tableNoRange;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setTemplateId(int templateId) {this.templateId = templateId;}

    public int getTemplateId() {return templateId;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOutletDesc() {
        return outletDesc;
    }

    public void setOutletDesc(String outletDesc) {
        this.outletDesc = outletDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getPosStoreId() {
        return posStoreId;
    }

    public void setPosStoreId(int posStoreId) {
        this.posStoreId = posStoreId;
    }

    public String getClusterName() {return clusterName;}

    public void setClusterName(String clusterName) {this.clusterName = clusterName;}

    public String getRegionName() {return regionName;}

    public void setRegionName(String regionName) {this.regionName = regionName;}

    public String getCompanyName() {return companyName;}

    public void setCompanyName(String companyName) {this.companyName = companyName;}

    public int getMobileNoLength() {return mobileNoLength;}

    public void setMobileNoLength(int mobileNoLength) {this.mobileNoLength = mobileNoLength;}

    public String getGroupName() {return groupName;}

    public void setGroupName(String groupName) {this.groupName = groupName;}

    public String getBannerUrl() {return bannerUrl;}

    public void setBannerUrl(String bannerUrl) {this.bannerUrl = bannerUrl;}

    @Override
    public String toString() {
        return "OutletResponseList{" +
                "id=" + id +
                ", outletDesc='" + outletDesc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", clusterId=" + clusterId +
                ", clusterName='" + clusterName + '\'' +
                ", regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", tableNoRange='" + tableNoRange + '\'' +
                ", groupId=" + groupId +
                ", mobileNoLength=" + mobileNoLength +
                ", groupName='" + groupName + '\'' +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                ", templateName='" + templateName + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", posStoreId=" + posStoreId +
                ", templateId=" + templateId +
                '}';
    }
}
