package com.barbeque.response.user;

/**
 * Created by System-3 on 2/8/2017.
 */
public class MenuResponse {
    private int id;
    private int parentId;
    private String sequenceId;
    private String name;
    private String hyperlink;
    private String isActive;


    public MenuResponse(int id, String sequenceId, String name, String hyperlink, String isActive,int parentId) {
        this.id = id;
        this.sequenceId = sequenceId;
        this.name = name;
        this.hyperlink = hyperlink;
        this.isActive = isActive;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public String getIsActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "MenuResponse{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", sequenceId='" + sequenceId + '\'' +
                ", name='" + name + '\'' +
                ", hyperlink='" + hyperlink + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
