package com.barbeque.dto.request;

/**
 * Created by System-3 on 2/9/2017.
 */
public class CreateRoleDTO {
    private String name;
    private String menuAccess;
    private String outletAccess;
    private int isAll;

    public int getIsAll() {
        return isAll;
    }

    public void setIsAll(int isAll) {
        this.isAll = isAll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuAccess() {
        return menuAccess;
    }

    public void setMenuAccess(String menuAccess) {
        this.menuAccess = menuAccess;
    }

    public String getOutletAccess() {
        return outletAccess;
    }

    public void setOutletAccess(String outletAccess) {
        this.outletAccess = outletAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateRoleDTO that = (CreateRoleDTO) o;

        if (isAll != that.isAll) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (menuAccess != null ? !menuAccess.equals(that.menuAccess) : that.menuAccess != null) return false;
        return outletAccess != null ? outletAccess.equals(that.outletAccess) : that.outletAccess == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (menuAccess != null ? menuAccess.hashCode() : 0);
        result = 31 * result + (outletAccess != null ? outletAccess.hashCode() : 0);
        result = 31 * result + isAll;
        return result;
    }

    @Override
    public String toString() {
        return "CreateRoleDTO{" +
                "name='" + name + '\'' +
                ", menuAccess='" + menuAccess + '\'' +
                ", outletAccess='" + outletAccess + '\'' +
                ", isAll=" + isAll +
                '}';
    }
}


