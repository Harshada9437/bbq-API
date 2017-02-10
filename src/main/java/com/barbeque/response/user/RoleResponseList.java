package com.barbeque.response.user;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-3 on 2/9/2017.
 */
public class RoleResponseList implements GenericResponse {
    private List<RoleResponse> roles;
    private String messageType;
    private String message;

    public List<RoleResponse> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleResponse> roles) {
        this.roles = roles;
    }

    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RoleResponseList{" +
                "roles=" + roles +
                ", messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
