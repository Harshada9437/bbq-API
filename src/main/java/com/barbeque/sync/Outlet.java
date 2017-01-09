package com.barbeque.sync;

/**
 * Created by System-2 on 1/9/2017.
 */
public class Outlet {
    private int id;
    private String outletDesc;
    private String shortDesc;
    private int clusterId;
    private int regionId;
    private int companyId;
    private int  groupId;
    private String posStoreId;

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

    public String getPosStoreId() {
        return posStoreId;
    }

    public void setPosStoreId(String posStoreId) {
        this.posStoreId = posStoreId;
    }

    @Override
    public String toString() {
        return "Outlet{" +
                "id=" + id +
                ", outletDesc='" + outletDesc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", clusterId=" + clusterId +
                ", regionId=" + regionId +
                ", companyId=" + companyId +
                ", groupId=" + groupId +
                ", posStoreId='" + posStoreId + '\'' +
                '}';
    }
}
