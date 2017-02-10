package com.barbeque.response.user;

import com.barbeque.response.util.GenericResponse;

/**
 * Created by System-3 on 2/9/2017.
 */
public class CreateUserResponse implements GenericResponse {
    private String userName;
    private String email;
    private String password;
    private String isActive;
    private int roleId;
    private String message;
    private String messageType;

    public CreateUserResponse(String userName, String email, String password, String isActive, int roleId, String message, String messageType) {

        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.roleId = roleId;
        this.message = message;
        this.messageType = messageType;
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

    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateUserResponse that = (CreateUserResponse) o;

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
        result = 31 * result + message.hashCode();
        result = 31 * result + messageType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CreateUserResponse{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive='" + isActive + '\'' +
                ", roleId=" + roleId +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
