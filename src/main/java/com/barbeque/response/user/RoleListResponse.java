package com.barbeque.response.user;

/**
 * Created by System-3 on 2/9/2017.
 */
public class RoleListResponse {
    private int roleId;
    private String name;
    private String menu_access;
    private String outlet_access;


    public RoleListResponse(int roleId, String name, String menu_access, String outlet_access) {
        this.roleId = roleId;
        this.name = name;
        this.menu_access = menu_access;
        this.outlet_access = outlet_access;
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

    public String getMenu_access() {
        return menu_access;
    }

    public void setMenu_access(String menu_access) {
        this.menu_access = menu_access;
    }

    public String getOutlet_access() {
        return outlet_access;
    }

    public void setOutlet_access(String outlet_access) {
        this.outlet_access = outlet_access;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleListResponse that = (RoleListResponse) o;

        if (roleId != that.roleId) return false;
        if (!name.equals(that.name)) return false;
        if (!menu_access.equals(that.menu_access)) return false;
        return outlet_access.equals(that.outlet_access);
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + name.hashCode();
        result = 31 * result + menu_access.hashCode();
        result = 31 * result + outlet_access.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RoleListResponse{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                ", menu_access='" + menu_access + '\'' +
                ", outlet_access='" + outlet_access + '\'' +
                '}';
    }
}
