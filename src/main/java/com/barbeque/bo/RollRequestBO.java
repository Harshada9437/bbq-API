package com.barbeque.bo;

/**
 * Created by System-3 on 2/9/2017.
 */
public class RollRequestBO {
    private int roleId;
    private String name;
    private String menuAccess;
    private String outletAccess;

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

        RollRequestBO that = (RollRequestBO) o;

        if (roleId != that.roleId) return false;
        if (!name.equals(that.name)) return false;
        if (!menuAccess.equals(that.menuAccess)) return false;
        return outletAccess.equals(that.outletAccess);
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + name.hashCode();
        result = 31 * result + menuAccess.hashCode();
        result = 31 * result + outletAccess.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RollRequestBO{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                ", menuAccess='" + menuAccess + '\'' +
                ", outletAccess='" + outletAccess + '\'' +
                '}';
    }
}
