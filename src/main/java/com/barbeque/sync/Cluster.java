package com.barbeque.sync;

/**
 * Created by System-2 on 1/9/2017.
 */
public class Cluster {
    private int id;
    private String desc;
    private String shortDesc;
    private int regionId;

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

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", regionId=" + regionId +
                '}';
    }
}
