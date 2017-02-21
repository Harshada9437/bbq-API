package com.barbeque.requesthandler;

import com.barbeque.bo.*;
import com.barbeque.dao.user.UsersDAO;
import com.barbeque.dto.request.CreateUserDTO;
import com.barbeque.dto.request.CreateRollDTO;
import com.barbeque.dto.request.MenuRequestDTO;
import com.barbeque.dto.request.RoleRequestDTO;
import com.barbeque.dto.response.LoginResponseDTO;
import com.barbeque.exceptions.RoleNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.response.user.*;
import com.barbeque.util.MD5Encode;


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
        String encodedPassword = MD5Encode.Encode(loginRequestBO.getPassword());
        String sessionId = usersDAO.getSessionIdForUser(userName, encodedPassword);
        int count = getCount(sessionId);
        Boolean isValidUser = userName.equals(loginResponseDTO.getUserName()) && encodedPassword.equals
                (loginResponseDTO.getPassword());
        if (isValidUser && count < 16) {
            Long newSessionId = new Date().getTime();
            if (loginResponseDTO.getSessionId() == null) {
                usersDAO.updateLogInSessionId(loginResponseDTO.getId(), "|" + String.valueOf(newSessionId) + "|");
            } else {
                usersDAO.updateLogInSessionId(loginResponseDTO.getId(), loginResponseDTO.getSessionId() + "|" +
                        String.valueOf(newSessionId) + "|");
            }
            loginResponseBO.setSessionId(newSessionId + "@" + loginResponseDTO.getId());
            loginResponseBO.setStatus(loginResponseDTO.getStatus());
            loginResponseBO.setId(loginResponseDTO.getId());
            loginResponseBO.setUserName(loginResponseDTO.getUserName());
            loginResponseBO.setName(loginResponseDTO.getName());
            loginResponseBO.setEmail(loginResponseDTO.getEmail());
            loginResponseBO.setRoleId(loginResponseDTO.getRoleId());
            loginResponseBO.setMenuAccess(loginResponseDTO.getMenuAccess());
            loginResponseBO.setOutletAccess(loginResponseDTO.getOutletAccess());

        } else {
            usersDAO.removeSessionId(userName, encodedPassword);
            loginResponseDTO.setSessionId("");
            Long newSessionId = new Date().getTime();
            if (loginResponseDTO.getSessionId() == null) {
                usersDAO.updateLogInSessionId(loginResponseDTO.getId(), String.valueOf(newSessionId));
            } else {
                usersDAO.updateLogInSessionId(loginResponseDTO.getId(), loginResponseDTO.getSessionId() + "|" +
                        String.valueOf(newSessionId) + "|");
            }
            loginResponseBO.setSessionId(newSessionId + "@" + loginResponseDTO.getId());
            loginResponseBO.setStatus(loginResponseDTO.getStatus());
            loginResponseBO.setId(loginResponseDTO.getId());
            loginResponseBO.setUserName(loginResponseDTO.getUserName());
            loginResponseBO.setName(loginResponseDTO.getName());
            loginResponseBO.setEmail(loginResponseDTO.getEmail());
            loginResponseBO.setRoleId(loginResponseDTO.getRoleId());
            loginResponseBO.setMenuAccess(loginResponseDTO.getMenuAccess());
            loginResponseBO.setOutletAccess(loginResponseDTO.getOutletAccess());

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

        UsersDAO usersDAO = new UsersDAO();
        String sessionId = usersDAO.getSessionIdForUserId(userId);
        Boolean isLoggedOut = usersDAO.updateSessionId(sessionId, sessionIdOfUser, userId);
        return isLoggedOut;
    }

    public UserResponse getUserById(int id) throws SQLException, UserNotFoundException {
        UserResponse userResponse = buildUserResponseFromDTO(UsersDAO.getuserById(id));
        return userResponse;
    }

    public UserResponse buildUserResponseFromDTO(LoginResponseDTO loginResponseDTO) throws SQLException, UserNotFoundException {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(loginResponseDTO.getId());
        userResponse.setName(loginResponseDTO.getName());
        userResponse.setUserName(loginResponseDTO.getUserName());
        userResponse.setEmail(loginResponseDTO.getEmail());
        userResponse.setOutletAccess(loginResponseDTO.getOutletAccess());
        userResponse.setStatus(loginResponseDTO.getStatus());
        userResponse.setMenuAccess(loginResponseDTO.getMenuAccess());
        userResponse.setRoleId(loginResponseDTO.getRoleId());

        return userResponse;
    }

    public List<MenuResponse> getMenuList() throws SQLException {
        UsersDAO usersDAO = new UsersDAO();
        List<MenuResponse> menuRequestDTOList = new ArrayList<MenuResponse>();
        List<MenuRequestDTO> menuList = usersDAO.getMenuList();

        for (MenuRequestDTO menuRequestDTO : menuList) {
            MenuResponse menuResponse = new MenuResponse(menuRequestDTO.getId(),
                    menuRequestDTO.getSequenceId(),
                    menuRequestDTO.getName(),
                    menuRequestDTO.getHyperlink(),
                    menuRequestDTO.getIsActive(),
                    menuRequestDTO.getParentId()
            );
            menuRequestDTOList.add(menuResponse);
        }
        return menuRequestDTOList;
    }


    public RoleByIdResponse getroleById(int id) throws SQLException, RoleNotFoundException {
        RoleByIdResponse roleByIdResponse = buildRoleRespFromDTO(UsersDAO.getroleById(id));
        return roleByIdResponse;
    }

    public RoleByIdResponse buildRoleRespFromDTO(RoleRequestDTO roleRequestDTO) throws SQLException, RoleNotFoundException {
        RoleByIdResponse roleByIdResponse = new RoleByIdResponse();
        roleByIdResponse.setRoleId(roleRequestDTO.getRoleId());
        roleByIdResponse.setName(roleRequestDTO.getName());
        roleByIdResponse.setMenuAccess(roleRequestDTO.getMenuAccess());
        roleByIdResponse.setOutletAccess(roleRequestDTO.getOutletAccess());


        return roleByIdResponse;
    }

    public Boolean updateUser(UpdateUserRequestBO updateUserRequestBO) throws SQLException {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        loginResponseDTO.setId(updateUserRequestBO.getId());
        loginResponseDTO.setName(updateUserRequestBO.getName());
        loginResponseDTO.setEmail(updateUserRequestBO.getEmail());
        loginResponseDTO.setRoleId(Integer.parseInt(updateUserRequestBO.getRole()));
        loginResponseDTO.setStatus(updateUserRequestBO.getStatus());

        Boolean isCreate = UsersDAO.updateUser(loginResponseDTO);

        return isCreate;
    }

    public boolean changePassword(ChangePasswordBO changePwdBO) throws Exception {
        UsersDAO appUserDAO = new UsersDAO();

        String encodedOldPassword = MD5Encode.Encode(changePwdBO.getOldPassword());
        String encodedNewPassword = MD5Encode.Encode(changePwdBO.getNewPassword());
        changePwdBO.setOldPassword(encodedOldPassword);
        changePwdBO.setNewPassword(encodedNewPassword);
        Boolean isProcessed = appUserDAO.changePassword(changePwdBO);

        return isProcessed;
    }

    public List<RoleResponse> getRoleList() throws SQLException {
        UsersDAO usersDAO = new UsersDAO();
        List<RoleResponse> roleRequestDTOList = new ArrayList<RoleResponse>();
        List<RoleRequestDTO> roleList = usersDAO.getRoleList();

        for (RoleRequestDTO roleRequestDTO : roleList) {
            RoleResponse roleResponse = new RoleResponse(roleRequestDTO.getRoleId(),
                    roleRequestDTO.getName(),
                    roleRequestDTO.getMenuAccess(),
                    roleRequestDTO.getOutletAccess()
            );
            roleRequestDTOList.add(roleResponse);
        }
        return roleRequestDTOList;
    }

    public Integer createUser(UserRequestBO userRequestBO) throws Exception {
        UsersDAO usersDAO = new UsersDAO();
        int id = usersDAO.createUser(buildUser1DTOFromBO(userRequestBO));
        return id;
    }

    private LoginResponseDTO buildUser1DTOFromBO(UserRequestBO userRequestBO) throws Exception {
        LoginResponseDTO createUserDTO = new LoginResponseDTO();
        createUserDTO.setUserName(userRequestBO.getUserName());
        createUserDTO.setName(userRequestBO.getName());
        createUserDTO.setEmail(userRequestBO.getEmail());
        createUserDTO.setPassword(MD5Encode.Encode(userRequestBO.getPassword()));
        createUserDTO.setRoleId(userRequestBO.getRoleId());

        return createUserDTO;
    }


    public Integer createRoll(RollRequestBO rollRequestBO) throws SQLException {
        UsersDAO usersDAO = new UsersDAO();
        int id = usersDAO.createRoll(buildRollDTOFromBO(rollRequestBO));
        return id;
    }

    private CreateRollDTO buildRollDTOFromBO(RollRequestBO rollRequestBO) {
        CreateRollDTO createUserDTO = new CreateRollDTO();
        createUserDTO.setName(rollRequestBO.getName());
        createUserDTO.setMenuAccess(rollRequestBO.getMenuAccess());
        createUserDTO.setOutletAccess(rollRequestBO.getOutletAccess());
        return createUserDTO;
    }


    public Boolean updateRoll(UpdateRollRequestBO updateFeedbackRequestBO) throws SQLException {
        UsersDAO usersDAO = new UsersDAO();
        Boolean isProcessed = usersDAO.updateRoll(buildDTOFromBO(updateFeedbackRequestBO));
        return isProcessed;
    }

    private RoleRequestDTO buildDTOFromBO(UpdateRollRequestBO updateRollRequestBO) {
        RoleRequestDTO roleRequestDTO = new RoleRequestDTO();
        roleRequestDTO.setRoleId(updateRollRequestBO.getRoleId());
        roleRequestDTO.setName(updateRollRequestBO.getName());
        roleRequestDTO.setMenuAccess(updateRollRequestBO.getMenuAccess());
        roleRequestDTO.setOutletAccess(updateRollRequestBO.getOutletAccess());
        return roleRequestDTO;
    }

    public List<UsersResp> getUserList() throws SQLException {
        UsersDAO usersDAO = new UsersDAO();
        List<UsersResp> userList = new ArrayList<UsersResp>();

        List<LoginResponseDTO> dtos = usersDAO.getUserList();

        for (LoginResponseDTO createUserDTO : dtos) {
            UsersResp user = new UsersResp(createUserDTO.getId(),
                    createUserDTO.getUserName(),
                    createUserDTO.getName(),
                    createUserDTO.getEmail(),
                    createUserDTO.getStatus(),
                    createUserDTO.getOutletAccess(),
                    createUserDTO.getMenuAccess(),
                    createUserDTO.getRoleId());
            userList.add(user);
        }
        return userList;
    }

    public Boolean resetPassword(ResetPasswordRequestBO resetPasswordRequestBO) throws Exception {
        UsersDAO usersDAO = new UsersDAO();

        String encodedNewPassword = MD5Encode.Encode(resetPasswordRequestBO.getNewPassword());
        resetPasswordRequestBO.setNewPassword(encodedNewPassword);
        Boolean isProcessed = usersDAO.resetPassword(resetPasswordRequestBO);

        return isProcessed;
    }

}

