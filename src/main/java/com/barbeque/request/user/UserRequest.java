package com.barbeque.request.user;

/**
 * Created by System-3 on 2/9/2017.
 */
public class UserRequest {
    private String userName;
    private String name;
    private String email;
    private String password;
    private int roleId;
    private int notifyEmail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

        UserRequest that = (UserRequest) o;

        if (roleId != that.roleId) return false;
        if (notifyEmail != that.notifyEmail) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + roleId;
        result = 31 * result + notifyEmail;
        return result;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ", notifyEmail=" + notifyEmail +
                '}';
    }
}
