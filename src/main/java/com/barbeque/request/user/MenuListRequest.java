package com.barbeque.request.user;

/**
 * Created by System-3 on 2/8/2017.
 */
public class MenuListRequest {
    private int id;
    private int parent_id;
    private String name;
    private String hyperlink;
    private String isActive;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
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

        MenuListRequest that = (MenuListRequest) o;

        if (id != that.id) return false;
        if (parent_id != that.parent_id) return false;
        if (!name.equals(that.name)) return false;
        if (!hyperlink.equals(that.hyperlink)) return false;
        return isActive.equals(that.isActive);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + parent_id;
        result = 31 * result + name.hashCode();
        result = 31 * result + hyperlink.hashCode();
        result = 31 * result + isActive.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "menuListRequest{" +
                "id=" + id +
                ", parent_id=" + parent_id +
                ", name='" + name + '\'' +
                ", hyperlink='" + hyperlink + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
