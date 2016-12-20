package com.barbeque.dto.request;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletListDTO
{

    private int id;
    private String outletDesc;
    private String shortDesc;
    private int clusterId;
    private int regionId;
    private int companyId;
    private int  groupId;
    private int posStoreId;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutletListDTO that = (OutletListDTO) o;

        if (id != that.id) return false;
        if (clusterId != that.clusterId) return false;
        if (regionId != that.regionId) return false;
        if (companyId != that.companyId) return false;
        if (groupId != that.groupId) return false;
        if (posStoreId != that.posStoreId) return false;
        if (outletDesc != null ? !outletDesc.equals(that.outletDesc) : that.outletDesc != null) return false;
        return shortDesc != null ? shortDesc.equals(that.shortDesc) : that.shortDesc == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (outletDesc != null ? outletDesc.hashCode() : 0);
        result = 31 * result + (shortDesc != null ? shortDesc.hashCode() : 0);
        result = 31 * result + clusterId;
        result = 31 * result + regionId;
        result = 31 * result + companyId;
        result = 31 * result + groupId;
        result = 31 * result + posStoreId;
        return result;
    }

    @Override
    public String toString() {
        return "OutletListDTO{" +
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

