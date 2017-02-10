package com.barbeque.response.user;

import com.barbeque.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-2 on 2/10/2017.
 */
public class UserResponseList implements GenericResponse {
    private List<UsersResp> users;
    private String message;
    private String messageType;

    public List<UsersResp> getUsers() {
        return users;
    }

    public void setUsers(List<UsersResp> users) {
        this.users = users;
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
    public String toString() {
        return "UserResponseList{" +
                "users=" + users +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
