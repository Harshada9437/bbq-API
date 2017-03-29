package com.barbeque.dto.request;

/**
 * Created by System-3 on 2/8/2017.
 */
public class RoleRequestDTO {
    private int roleId;
    private int isAll;
    private String name;
    private String menuAccess;
    private String outletAccess;

    public int getIsAll() {
        return isAll;
    }

    public void setIsAll(int isAll) {
        this.isAll = isAll;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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

        RoleRequestDTO that = (RoleRequestDTO) o;

        if (roleId != that.roleId) return false;
        if (isAll != that.isAll) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (menuAccess != null ? !menuAccess.equals(that.menuAccess) : that.menuAccess != null) return false;
        return outletAccess != null ? outletAccess.equals(that.outletAccess) : that.outletAccess == null;
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + isAll;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (menuAccess != null ? menuAccess.hashCode() : 0);
        result = 31 * result + (outletAccess != null ? outletAccess.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoleRequestDTO{" +
                "roleId=" + roleId +
                ", isAll=" + isAll +
                ", name='" + name + '\'' +
                ", menuAccess='" + menuAccess + '\'' +
                ", outletAccess='" + outletAccess + '\'' +
                '}';
    }
}
