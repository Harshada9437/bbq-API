package com.barbeque.response.user;

public class LoginResponseBO {

    private String status;
    private String sessionId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginResponseBO that = (LoginResponseBO) o;

        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        return sessionId != null ? sessionId.equals(that.sessionId) : that.sessionId == null;

    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginResponseBO{" +
                "status='" + status + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
