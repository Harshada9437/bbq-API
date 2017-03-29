package com.barbeque.response.user;

/**
 * Created by System-3 on 2/8/2017.
 */
public class RoleResponse {
    private int roleId;
    private int isAll;
    private String name;
    private String menuAccess;
    private String outletAccess;

    public RoleResponse(int roleId,int isAll, String name, String menuAccess, String outletAccess) {
        this.roleId = roleId;
        this.isAll = isAll;
        this.name = name;
        this.menuAccess = menuAccess;
        this.outletAccess = outletAccess;
    }

    public int getIsAll() {
        return isAll;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public String getMenuAccess() {
        return menuAccess;
    }

    public String getOutletAccess() {
        return outletAccess;
    }

    @Override
    public String toString() {
        return "RoleResponse{" +
                "roleId=" + roleId +
                ", isAll=" + isAll +
                ", name='" + name + '\'' +
                ", menuAccess='" + menuAccess + '\'' +
                ", outletAccess='" + outletAccess + '\'' +
                '}';
    }


}
