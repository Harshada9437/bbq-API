package com.barbeque.dto.request;

/**
 * Created by System-3 on 2/8/2017.
 */
public class MenuRequestDTO {
    private int id;
    private int parentId;
    private String sequenceId;
    private String name;
    private String hyperlink;
    private String isActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
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

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuRequestDTO that = (MenuRequestDTO) o;

        if (id != that.id) return false;
        if (parentId != that.parentId) return false;
        if (sequenceId != null ? !sequenceId.equals(that.sequenceId) : that.sequenceId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (hyperlink != null ? !hyperlink.equals(that.hyperlink) : that.hyperlink != null) return false;
        return isActive != null ? isActive.equals(that.isActive) : that.isActive == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + parentId;
        result = 31 * result + (sequenceId != null ? sequenceId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (hyperlink != null ? hyperlink.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MenuRequestDTO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", sequenceId='" + sequenceId + '\'' +
                ", name='" + name + '\'' +
                ", hyperlink='" + hyperlink + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
