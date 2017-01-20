package com.barbeque.dto.request;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletDTO
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
    private int  groupId;
    private int  mobileNoLength;
    private String  groupName;
    private String  tableNoRange;
    private String  bannerUrl;
    private String  templateName;
    private String posStoreId;
    private String pocEmail;
    private String pocName;
    private String pocMobile;
    private int templateId;

    public String getPocEmail() {
        return pocEmail;
    }

    public void setPocEmail(String pocEmail) {
        this.pocEmail = pocEmail;
    }

    public String getPocName() {
        return pocName;
    }

    public void setPocName(String pocName) {
        this.pocName = pocName;
    }

    public String getPocMobile() {
        return pocMobile;
    }

    public void setPocMobile(String pocMobile) {
        this.pocMobile = pocMobile;
    }

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

    public int getTemplateId() {return templateId;}

    public void setTemplateId(int templateId) {this.templateId = templateId;}

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutletDTO outletDTO = (OutletDTO) o;

        if (id != outletDTO.id) return false;
        if (clusterId != outletDTO.clusterId) return false;
        if (regionId != outletDTO.regionId) return false;
        if (companyId != outletDTO.companyId) return false;
        if (groupId != outletDTO.groupId) return false;
        if (mobileNoLength != outletDTO.mobileNoLength) return false;
        if (templateId != outletDTO.templateId) return false;
        if (outletDesc != null ? !outletDesc.equals(outletDTO.outletDesc) : outletDTO.outletDesc != null) return false;
        if (shortDesc != null ? !shortDesc.equals(outletDTO.shortDesc) : outletDTO.shortDesc != null) return false;
        if (clusterName != null ? !clusterName.equals(outletDTO.clusterName) : outletDTO.clusterName != null)
            return false;
        if (regionName != null ? !regionName.equals(outletDTO.regionName) : outletDTO.regionName != null) return false;
        if (companyName != null ? !companyName.equals(outletDTO.companyName) : outletDTO.companyName != null)
            return false;
        if (groupName != null ? !groupName.equals(outletDTO.groupName) : outletDTO.groupName != null) return false;
        if (tableNoRange != null ? !tableNoRange.equals(outletDTO.tableNoRange) : outletDTO.tableNoRange != null)
            return false;
        if (bannerUrl != null ? !bannerUrl.equals(outletDTO.bannerUrl) : outletDTO.bannerUrl != null) return false;
        if (templateName != null ? !templateName.equals(outletDTO.templateName) : outletDTO.templateName != null)
            return false;
        if (posStoreId != null ? !posStoreId.equals(outletDTO.posStoreId) : outletDTO.posStoreId != null) return false;
        if (pocEmail != null ? !pocEmail.equals(outletDTO.pocEmail) : outletDTO.pocEmail != null) return false;
        if (pocName != null ? !pocName.equals(outletDTO.pocName) : outletDTO.pocName != null) return false;
        return pocMobile != null ? pocMobile.equals(outletDTO.pocMobile) : outletDTO.pocMobile == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (outletDesc != null ? outletDesc.hashCode() : 0);
        result = 31 * result + (shortDesc != null ? shortDesc.hashCode() : 0);
        result = 31 * result + clusterId;
        result = 31 * result + (clusterName != null ? clusterName.hashCode() : 0);
        result = 31 * result + regionId;
        result = 31 * result + (regionName != null ? regionName.hashCode() : 0);
        result = 31 * result + companyId;
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + groupId;
        result = 31 * result + mobileNoLength;
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (tableNoRange != null ? tableNoRange.hashCode() : 0);
        result = 31 * result + (bannerUrl != null ? bannerUrl.hashCode() : 0);
        result = 31 * result + (templateName != null ? templateName.hashCode() : 0);
        result = 31 * result + (posStoreId != null ? posStoreId.hashCode() : 0);
        result = 31 * result + (pocEmail != null ? pocEmail.hashCode() : 0);
        result = 31 * result + (pocName != null ? pocName.hashCode() : 0);
        result = 31 * result + (pocMobile != null ? pocMobile.hashCode() : 0);
        result = 31 * result + templateId;
        return result;
    }

    @Override
    public String toString() {
        return "OutletDTO{" +
                "id=" + id +
                ", outletDesc='" + outletDesc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", clusterId=" + clusterId +
                ", clusterName='" + clusterName + '\'' +
                ", regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", groupId=" + groupId +
                ", mobileNoLength=" + mobileNoLength +
                ", groupName='" + groupName + '\'' +
                ", tableNoRange='" + tableNoRange + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", templateName='" + templateName + '\'' +
                ", posStoreId='" + posStoreId + '\'' +
                ", pocEmail='" + pocEmail + '\'' +
                ", pocName='" + pocName + '\'' +
                ", pocMobile='" + pocMobile + '\'' +
                ", templateId=" + templateId +
                '}';
    }
}

