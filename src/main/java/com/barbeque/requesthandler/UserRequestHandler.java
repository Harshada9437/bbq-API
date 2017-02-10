package com.barbeque.requesthandler;

import com.barbeque.bo.RollRequestBO;
import com.barbeque.bo.UpdateRollRequestBO;
import com.barbeque.bo.UserRequestBO;
import com.barbeque.dao.user.UsersDAO;
import com.barbeque.dto.CreateUserDTO;
import com.barbeque.dto.request.CreateRollDTO;
import com.barbeque.dto.request.MenuRequestDTO;
import com.barbeque.dto.request.RoleRequestDTO;
import com.barbeque.dto.response.LoginResponseDTO;
import com.barbeque.exceptions.RoleNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.bo.LoginRequestBO;
import com.barbeque.response.user.*;


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
        userdetailsByIdResponse.setRoleId(loginResponseDTO.getRoleId());

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


    public RoleByIdResponse getroleById(int id) throws SQLException,RoleNotFoundException {
       UsersDAO usersDAO = new UsersDAO();
       RoleByIdResponse roleByIdResponse = new RoleByIdResponse();
        try {
            roleByIdResponse = buildRoleDTOFromBO(UsersDAO.getroleById(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleByIdResponse;
    }

    public RoleByIdResponse buildRoleDTOFromBO(RoleRequestDTO roleRequestDTO) throws SQLException,RoleNotFoundException
    {
       RoleByIdResponse roleByIdResponse = new RoleByIdResponse();
       roleByIdResponse.setRoleId(roleRequestDTO.getRoleId());
       roleByIdResponse.setName(roleRequestDTO.getName());
       roleByIdResponse.setMenuAccess(roleRequestDTO.getMenuAccess());
       roleByIdResponse.setOutletAccess(roleRequestDTO.getOutletAccess());


        return roleByIdResponse;
    }

    public List<RoleListResponse> getRoleList() throws SQLException {
        UsersDAO usersDAO = new UsersDAO();
        List<RoleListResponse> roleRequestDTOList = new ArrayList<RoleListResponse>();
        List<RoleRequestDTO> roleList = usersDAO.getRoleList();

        for (RoleRequestDTO roleRequestDTO : roleList) {
           RoleListResponse roleListResponse = new RoleListResponse(roleRequestDTO.getRoleId(),
                  roleRequestDTO.getName(),
                   roleRequestDTO.getMenuAccess(),
                   roleRequestDTO.getOutletAccess()

            );
           roleRequestDTOList.add(roleListResponse);
        }
        return roleRequestDTOList;
    }
        public Integer createUser(UserRequestBO userRequestBO) throws SQLException {
          UsersDAO usersDAO = new UsersDAO();
            int id = usersDAO.createUser(buildUser1DTOFromBO(userRequestBO));
            return id;
        }

        private CreateUserDTO buildUser1DTOFromBO(UserRequestBO userRequestBO) {
        CreateUserDTO createUserDTO= new CreateUserDTO();
           createUserDTO.setUserName(userRequestBO.getUserName());
           createUserDTO.setEmail(userRequestBO.getEmail());
           createUserDTO.setPassword(userRequestBO.getPassword());
           createUserDTO.setRoleId(userRequestBO.getRoleId());

            return createUserDTO;
        }



    public Integer createRoll(RollRequestBO rollRequestBO) throws SQLException {
        UsersDAO usersDAO = new UsersDAO();
        int id = usersDAO.createRoll(buildRollDTOFromBO(rollRequestBO));
        return id;
    }

    private CreateRollDTO buildRollDTOFromBO(RollRequestBO rollRequestBO) {
      CreateRollDTO createUserDTO= new CreateRollDTO();
      createUserDTO.setName(rollRequestBO.getName());
      createUserDTO.setMenuAccess(rollRequestBO.getMenuAccess());
      createUserDTO.setOutletAccess(rollRequestBO.getOutletAccess());


        return createUserDTO;
    }




    public Boolean updateRoll(UpdateRollRequestBO updateFeedbackRequestBO) throws SQLException {
        Boolean isProcessed;
      UsersDAO usersDAO= new UsersDAO();
        try {
            isProcessed = usersDAO.updateRoll(buildDTOFromBO(updateFeedbackRequestBO));
        } catch (SQLException sq) {
            isProcessed = false;
        }
        return isProcessed;
    }

    private RoleRequestDTO buildDTOFromBO(UpdateRollRequestBO updateRollRequestBO) {
        RoleRequestDTO roleRequestDTO= new RoleRequestDTO();
        roleRequestDTO.setRoleId(updateRollRequestBO.getRoleId());
        roleRequestDTO.setName(updateRollRequestBO.getName());
        roleRequestDTO.setMenuAccess(updateRollRequestBO.getMenuAccess());
        roleRequestDTO.setOutletAccess(updateRollRequestBO.getOutletAccess());


        return  roleRequestDTO;
    }



}

