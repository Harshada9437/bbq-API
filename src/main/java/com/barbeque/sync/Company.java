package com.barbeque.sync;

/**
 * Created by System-2 on 1/9/2017.
 */
public class Company {
    private int id;
    private String desc;
    private String shortDesc;
    private int groupId;

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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", groupId=" + groupId +
                '}';
    }
}
