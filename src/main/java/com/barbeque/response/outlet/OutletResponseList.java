package com.barbeque.response.outlet;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletResponseList
{
    private int id;
    private String outletDesc;
    private String shortDesc;
    private int clusterId;
    private int regionId;
    private int companyId;
    private int  groupId;
    private int posStoreId;


    public OutletResponseList(int id, String outletDesc, String shortDesc, int intclusterId, int regionId, int intcompanyId, int groupId, int intposStoreId) {
        this.id = id;
        this.outletDesc = outletDesc;
        this.shortDesc = shortDesc;
        this.clusterId = intclusterId;
        this.regionId = regionId;
        this.companyId = intcompanyId;
        this.groupId = groupId;
        this.posStoreId = intposStoreId;
    }

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

    @Override
    public String toString() {
        return "OutletResponseList{" +
                "id=" + id +
                ", outletDesc='" + outletDesc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", clusterId=" + clusterId +
                ", regionId=" + regionId +
                ", companyId=" + companyId +
                ", groupId=" + groupId +
                ", posStoreId=" + posStoreId +
                '}';
    }
}
