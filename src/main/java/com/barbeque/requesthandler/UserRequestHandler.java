package com.barbeque.requesthandler;

import com.barbeque.dao.user.UsersDAO;
import com.barbeque.dto.response.LoginResponseDTO;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.request.bo.LoginRequestBO;
import com.barbeque.response.user.LoginResponseBO;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by System-2 on 12/24/2016.
 */
public class UserRequestHandler {
    public LoginResponseBO login(LoginRequestBO loginRequestBO)
            throws Exception {
        UsersDAO usersDAO = new UsersDAO();
        String userName = loginRequestBO.getUserName();
        LoginResponseDTO loginResponseDTO = usersDAO
                .getUserDetailsWithName(userName);
        LoginResponseBO loginResponseBO = new LoginResponseBO();
        String sessionId = usersDAO.getSessionIdForUser(userName, loginRequestBO.getPassword());
        int count = getCount(sessionId);
        Boolean isValidUser = userName.equals(loginResponseDTO.getUserName()) && loginRequestBO.getPassword().equals(loginResponseDTO.getPassword());
        if (isValidUser && count < 16) {
            Long newSessionId = new Date().getTime();
            if (loginResponseDTO.getSessionId() == null) {
                usersDAO.updateLogInSessionId(loginResponseDTO.getId(), "|" + String.valueOf(newSessionId) + "|") ;
            } else {
                usersDAO.updateLogInSessionId(loginResponseDTO.getId(), loginResponseDTO.getSessionId() + "|" + String.valueOf(newSessionId) + "|");
            }
            loginResponseBO.setSessionId(newSessionId + "@" + loginResponseDTO.getId());
            loginResponseBO.setStatus(loginResponseDTO.getStatus());

        }else{
            usersDAO.removeSessionId(userName, loginRequestBO.getPassword());
            loginResponseDTO.setSessionId("");
            Long newSessionId = new Date().getTime();
            if (loginResponseDTO.getSessionId() == null) {
                usersDAO.updateLogInSessionId(loginResponseDTO.getId(), String.valueOf(newSessionId));
            } else {
                usersDAO.updateLogInSessionId(loginResponseDTO.getId(), loginResponseDTO.getSessionId() + "|" + String.valueOf(newSessionId) + "|");
            }
            loginResponseBO.setSessionId(newSessionId + "@" + loginResponseDTO.getId());
            loginResponseBO.setStatus(loginResponseDTO.getStatus());
        }
        return loginResponseBO;
    }

    private Integer getCount(String sessionId) {
        int counter = 0;
        if(sessionId != null){
            for( int i=0; i<sessionId.length(); i++ ) {
                if( sessionId.charAt(i) == '|' ) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public Boolean logout(int userId, String sessionIdOfUser) throws SQLException, UserNotFoundException {
        Boolean isLoggedOut = Boolean.FALSE;
        try {
            UsersDAO usersDAO = new UsersDAO();
            String sessionId = usersDAO.getSessionIdForUserId(userId);
            isLoggedOut = usersDAO.updateSessionId(sessionId,sessionIdOfUser,userId);
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return isLoggedOut;
    }
}
