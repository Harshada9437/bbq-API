package com.barbeque.dto.response;

public class LoginResponseDTO {
    private int id;
    private String userName;
    private String email;
    private String status;
    private String password;
    private String sessionId;


    public String getSessionId() {return sessionId;}

    public void setSessionId(String sessionId) {this.sessionId = sessionId;}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginResponseDTO that = (LoginResponseDTO) o;

        if (id != that.id) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return sessionId != null ? sessionId.equals(that.sessionId) : that.sessionId == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", password='" + password + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
