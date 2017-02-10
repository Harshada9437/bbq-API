package com.barbeque.response.user;

/**
 * Created by System-2 on 2/10/2017.
 */
public class UsersResp {
    private int id;
    private String userName;
    private String name;
    private String email;
    private String status;
    private String outletAccess;
    private String menuAccess;
    private int roleId;

    public UsersResp(int id, String userName,String name, String email, String status, String outletAccess, String menuAccess, int roleId) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.status = status;
        this.outletAccess = outletAccess;
        this.menuAccess = menuAccess;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public String getOutletAccess() {
        return outletAccess;
    }

    public String getMenuAccess() {
        return menuAccess;
    }

    public int getRoleId() {
        return roleId;
    }

    @Override
    public String toString() {
        return "UsersResp{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", outletAccess='" + outletAccess + '\'' +
                ", menuAccess='" + menuAccess + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
