package com.barbeque.response.user;

import com.barbeque.response.util.GenericResponse;

/**
 * Created by System-3 on 2/8/2017.
 */
public class UserdetailsByIdResponse implements GenericResponse {

    private int id;
    private String userName;
    private String email;
    private String status;
    private String password;
    private String sessionId;
    private int roleId;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

        UserdetailsByIdResponse that = (UserdetailsByIdResponse) o;

        if (id != that.id) return false;
        if (roleId != that.roleId) return false;
        if (!userName.equals(that.userName)) return false;
        if (!email.equals(that.email)) return false;
        if (!status.equals(that.status)) return false;
        if (!password.equals(that.password)) return false;
        return sessionId.equals(that.sessionId);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + sessionId.hashCode();
        result = 31 * result + roleId;
        return result;
    }

    @Override
    public String toString() {
        return "UserdetailsByIdResponse{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", password='" + password + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", roleId=" + roleId +
                '}';
    }

    @Override
    public void setMessageType(String message) {

    }

    @Override
    public void setMessage(String message) {

    }
}
