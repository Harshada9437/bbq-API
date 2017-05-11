package com.barbeque.dto.response;

public class LoginResponseDTO {
    private int id;
    private String userName;
    private String name;
    private String password;
    private String email;
    private String status;
    private String outletAccess;
    private String sessionId;
    private String menuAccess;
    private int roleId;
    private int notifyEmail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOutletAccess() {
        return outletAccess;
    }

    public void setOutletAccess(String outletAccess) {
        this.outletAccess = outletAccess;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMenuAccess() {
        return menuAccess;
    }

    public void setMenuAccess(String menuAccess) {
        this.menuAccess = menuAccess;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getNotifyEmail() {
        return notifyEmail;
    }

    public void setNotifyEmail(int notifyEmail) {
        this.notifyEmail = notifyEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginResponseDTO that = (LoginResponseDTO) o;

        if (id != that.id) return false;
        if (roleId != that.roleId) return false;
        if (notifyEmail != that.notifyEmail) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (outletAccess != null ? !outletAccess.equals(that.outletAccess) : that.outletAccess != null) return false;
        if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;
        return menuAccess != null ? menuAccess.equals(that.menuAccess) : that.menuAccess == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (outletAccess != null ? outletAccess.hashCode() : 0);
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        result = 31 * result + (menuAccess != null ? menuAccess.hashCode() : 0);
        result = 31 * result + roleId;
        result = 31 * result + notifyEmail;
        return result;
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", outletAccess='" + outletAccess + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", menuAccess='" + menuAccess + '\'' +
                ", roleId=" + roleId +
                ", notifyEmail=" + notifyEmail +
                '}';
    }
}
