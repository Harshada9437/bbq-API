package com.barbeque.requesthandler;

import com.barbeque.dao.user.UsersDAO;
import com.barbeque.dto.request.CustomerDTO;
import com.barbeque.dto.request.MenuRequestDTO;
import com.barbeque.dto.request.RollRequestDTO;
import com.barbeque.dto.response.LoginResponseDTO;
import com.barbeque.exceptions.RollNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.bo.LoginRequestBO;
import com.barbeque.response.user.LoginResponseBO;
import com.barbeque.response.user.MenuListResponse;
import com.barbeque.response.user.RollByIdResponse;
import com.barbeque.response.user.UserdetailsByIdResponse;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                usersDAO.updateLogInSessionId(loginResponseDTO.getId(), "|" + String.valueOf(newSessionId) + "|");
            } else {
                usersDAO.updateLogInSessionId(loginResponseDTO.getId(), loginResponseDTO.getSessionId() + "|" + String.valueOf(newSessionId) + "|");
            }
            loginResponseBO.setSessionId(newSessionId + "@" + loginResponseDTO.getId());
            loginResponseBO.setStatus(loginResponseDTO.getStatus());

        } else {
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
        if (sessionId != null) {
            for (int i = 0; i < sessionId.length(); i++) {
                if (sessionId.charAt(i) == '|') {
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
            isLoggedOut = usersDAO.updateSessionId(sessionId, sessionIdOfUser, userId);
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return isLoggedOut;
    }

    public UserdetailsByIdResponse getuserById(int id) throws SQLException, UserNotFoundException {
        UsersDAO usersDAO = new UsersDAO();
        UserdetailsByIdResponse userdetailsByIdResponse = new UserdetailsByIdResponse();
        try {
            userdetailsByIdResponse = buildUserDTOFromBO(UsersDAO.getuserById(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userdetailsByIdResponse;
    }

    public UserdetailsByIdResponse buildUserDTOFromBO(LoginResponseDTO loginResponseDTO) throws SQLException, UserNotFoundException {
        UserdetailsByIdResponse userdetailsByIdResponse = new UserdetailsByIdResponse();
        userdetailsByIdResponse.setId(loginResponseDTO.getId());
        userdetailsByIdResponse.setUserName(loginResponseDTO.getUserName());
        userdetailsByIdResponse.setEmail(loginResponseDTO.getEmail());
        userdetailsByIdResponse.setPassword(loginResponseDTO.getPassword());
        userdetailsByIdResponse.setStatus(loginResponseDTO.getStatus());
        userdetailsByIdResponse.setSessionId(loginResponseDTO.getSessionId());
        userdetailsByIdResponse.setIsActive(loginResponseDTO.getIsActive());
        userdetailsByIdResponse.setRoll_id(loginResponseDTO.getRoll_id());

     return userdetailsByIdResponse;
    }

    public List<MenuListResponse> getMenuList() throws SQLException {
        UsersDAO usersDAO = new UsersDAO();
        List<MenuListResponse> menuRequestDTOList = new ArrayList<MenuListResponse>();
            List<MenuRequestDTO> menuList = usersDAO.getMenuList();

            for (MenuRequestDTO menuRequestDTO : menuList) {
                MenuListResponse menuListResponse = new MenuListResponse(menuRequestDTO.getId(),
                        menuRequestDTO.getParent_id(),
                        menuRequestDTO.getName(),
                        menuRequestDTO.getHyperlink(),
                        menuRequestDTO.getIsActive()
                );
                menuRequestDTOList.add(menuListResponse);
            }
        return menuRequestDTOList;
    }


    public RollByIdResponse getrollById(int id) throws SQLException,RollNotFoundException {
       UsersDAO usersDAO = new UsersDAO();
       RollByIdResponse rollByIdResponse = new RollByIdResponse();
        try {
            rollByIdResponse = buildRollDTOFromBO(UsersDAO.getrollById(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rollByIdResponse;
    }

    public RollByIdResponse buildRollDTOFromBO(RollRequestDTO rollRequestDTO) throws SQLException,RollNotFoundException
    {
       RollByIdResponse rollByIdResponse = new RollByIdResponse();
       rollByIdResponse.setRoll_id(rollRequestDTO.getRoll_id());
       rollByIdResponse.setName(rollRequestDTO.getName());
       rollByIdResponse.setMenu_access(rollRequestDTO.getMenu_access());
       rollByIdResponse.setOutlet_access(rollRequestDTO.getOutlet_access());


        return rollByIdResponse;

    }


}

