package com.barbeque.sync;

/**
 * Created by System-2 on 1/9/2017.
 */
public class Regions {
    private int id;
    private String desc;
    private String shortDesc;
    private int companyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "Regions{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
