package com.barbeque.dto.response;

public class LoginResponseDTO {
    private int id;
    private String userName;
    private String email;
    private String status;
    private String password;
    private String sessionId;
    private String isActive;
    private int roll_id;

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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public int getRoll_id() {
        return roll_id;
    }

    public void setRoll_id(int roll_id) {
        this.roll_id = roll_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginResponseDTO that = (LoginResponseDTO) o;

        if (id != that.id) return false;
        if (roll_id != that.roll_id) return false;
        if (!userName.equals(that.userName)) return false;
        if (!email.equals(that.email)) return false;
        if (!status.equals(that.status)) return false;
        if (!password.equals(that.password)) return false;
        if (!sessionId.equals(that.sessionId)) return false;
        return isActive.equals(that.isActive);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + sessionId.hashCode();
        result = 31 * result + isActive.hashCode();
        result = 31 * result + roll_id;
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
                ", isActive='" + isActive + '\'' +
                ", roll_id='" + roll_id + '\'' +
                '}';
    }
}
