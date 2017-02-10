package com.barbeque.dto;

/**
 * Created by System-3 on 2/9/2017.
 */
public class CreateUserDTO {
    private String userName;
    private String email;
    private String password;
    private String isActive;
    private int roleId;

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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateUserDTO that = (CreateUserDTO) o;

        if (roleId != that.roleId) return false;
        if (!userName.equals(that.userName)) return false;
        if (!email.equals(that.email)) return false;
        if (!password.equals(that.password)) return false;
        return isActive.equals(that.isActive);
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + isActive.hashCode();
        result = 31 * result + roleId;
        return result;
    }

    @Override
    public String toString() {
        return "CreateUserDTO{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive='" + isActive + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
