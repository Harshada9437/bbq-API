package com.barbeque.service;

import com.barbeque.bo.*;
import com.barbeque.dao.user.UsersDAO;
import com.barbeque.exceptions.RoleNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.request.user.*;
import com.barbeque.requesthandler.UserRequestHandler;
import com.barbeque.response.user.*;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.util.ResponseGenerator;
import com.barbeque.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by System-2 on 12/24/2016.
 */
@Path("/user")
public class UserService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(LoginRequest loginRequest) {
        LoginRequestBO loginRequestBO = new LoginRequestBO();
        loginRequestBO.setUserName(loginRequest.getUserName());
        loginRequestBO.setPassword(loginRequest.getPassword());
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        LoginResponse loginResponse = new LoginResponse();
        MessageResponse messageResponse = new MessageResponse();
        try {
            LoginResponseBO loginResponseBO = userRequestHandler.login(loginRequestBO);
            loginResponse.setRoleId(loginResponseBO.getRoleId());
            loginResponse.setUserName(loginResponseBO.getUserName());
            loginResponse.setName(loginResponseBO.getName());
            loginResponse.setEmail(loginResponseBO.getEmail());
            loginResponse.setStatus(loginResponseBO.getStatus());
            loginResponse.setSessionId(loginResponseBO.getSessionId());
            loginResponse.setId(loginResponseBO.getId());
            loginResponse.setMenuAccess(loginResponseBO.getMenuAccess());
            loginResponse.setOutletAccess(loginResponseBO.getOutletAccess());
            if (loginResponseBO.getSessionId() != null && loginResponseBO.getStatus().equals("A")) {
                return ResponseGenerator.generateSuccessResponse(loginResponse, String.valueOf(loginResponseBO.getSessionId()));
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid password or inactive user.");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid Log in");
        }

    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@HeaderParam("sessionId") String sessionId) {
        LoginResponse loginResponse = new LoginResponse();
        MessageResponse messageResponse = new MessageResponse();
        try {
            UserRequestHandler userRequestHandler = new UserRequestHandler();
            String[] sessionIdParts = sessionId.split("@");
            Boolean isLoggedOut = userRequestHandler.logout(Integer.parseInt(sessionIdParts[1]), sessionIdParts
                    [0]);
            if (isLoggedOut) {
                return ResponseGenerator.generateSuccessResponse(loginResponse, "Log out successfully.");
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to log out the current user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to log out the current user.");
        } catch (UserNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid user id");
        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(UpdateUserRequest updateUserRequest) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            UserRequestHandler userRequestHandler = new UserRequestHandler();
            UpdateUserRequestBO updateUserRequestBO = new UpdateUserRequestBO();
            updateUserRequestBO.setEmail(updateUserRequest.getEmail());
            updateUserRequestBO.setName(updateUserRequest.getName());
            updateUserRequestBO.setRole(updateUserRequest.getRole());
            updateUserRequestBO.setStatus(updateUserRequest.getStatus());
            updateUserRequestBO.setId(updateUserRequest.getId());
            updateUserRequestBO.setNotifyEmail(updateUserRequest.getNotifyEmail());
            Boolean isLoggedOut = userRequestHandler.updateUser(updateUserRequestBO);

            if (isLoggedOut) {
                return ResponseGenerator.generateSuccessResponse(messageResponse, "updated successfully.");

            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update user.");
        }
    }

    @GET
    @Path("/userInfo/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getuserById(@PathParam("id") int id) {

        UserRequestHandler userRequestHandler = new UserRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            UserResponse userResponse = userRequestHandler.getUserById(id);
            return ResponseGenerator.generateSuccessResponse(userResponse, "UserInfo");
        } catch (UserNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID UserId ");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "failed to retrieve. ");
        }
    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/menuList")

    public Response getMenuList() {
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        MenuResponseList menuResponseList = new MenuResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            menuResponseList.setMenus(userRequestHandler.getMenuList());
            return ResponseGenerator.generateSuccessResponse(menuResponseList, "List of menus.");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }

    @GET
    @Path("/roleDetail/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getroleById(@PathParam("id") int id) {
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            RoleByIdResponse roleByIdResponse = userRequestHandler.getroleById(id);
            return ResponseGenerator.generateSuccessResponse(roleByIdResponse, "SUCCESS");
        } catch (RoleNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID RollId ");

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }


    /*@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/roleList")
    public Response getRoleList() {
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        RoleResponseList roleResponseList = new RoleResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            roleResponseList.setRoles(userRequestHandler.getRoleList());
            return ResponseGenerator.generateSuccessResponse(roleResponseList, "List of roles.");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }*/

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/roleList")
    public Response getRoleReportList() {
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        RoleResponseList roleResponseList = new RoleResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            roleResponseList.setRoles(userRequestHandler.getRoleReportList());
            return ResponseGenerator.generateSuccessResponse(roleResponseList, "List of roles.");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getUserList() {
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        UserResponseList userResponseList = new UserResponseList();
        try {
            userResponseList.setUsers(userRequestHandler.getUserList());
            return ResponseGenerator.generateSuccessResponse(userResponseList, "List of users.");
        } catch (SQLException e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createUser(UserRequest userRequest) {
        UserRequestBO userRequestBO = new UserRequestBO();
        userRequestBO.setUserName(userRequest.getUserName());
        userRequestBO.setName(userRequest.getName());
        userRequestBO.setEmail(userRequest.getEmail());
        userRequestBO.setPassword(userRequest.getPassword());
        userRequestBO.setRoleId(userRequest.getRoleId());
        userRequestBO.setNotifyEmail(userRequest.getNotifyEmail());

        MessageResponse createUserResponse = new MessageResponse();
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        try {
            if (!UsersDAO.getuser(userRequest.getUserName(), userRequest.getEmail(), userRequest.getName())) {
                int userId = userRequestHandler.createUser(userRequestBO);
                return ResponseGenerator.generateSuccessResponse(createUserResponse, String.valueOf(userId));
            } else {
                return ResponseGenerator.generateFailureResponse(createUserResponse, "Username or email address already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(createUserResponse, "User creation Failed");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createRole")
    public Response createRoll(RollRequest rollRequest) {
        RoleRequestBO roleRequestBO = new RoleRequestBO();
        roleRequestBO.setName(rollRequest.getName());
        roleRequestBO.setMenuAccess(rollRequest.getMenuAccess());
        roleRequestBO.setOutletAccess(rollRequest.getOutletAccess());
        roleRequestBO.setIsAll(rollRequest.getIsAll());

        MessageResponse createUserResponse = new MessageResponse();
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        try {
            if (!UsersDAO.getRole(rollRequest.getName())) {
                int userId = userRequestHandler.createRoll(roleRequestBO);
                return ResponseGenerator.generateSuccessResponse(createUserResponse, String.valueOf(userId));
            } else {
                return ResponseGenerator.generateFailureResponse(createUserResponse, "Role already exists.");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return ResponseGenerator.generateFailureResponse(createUserResponse, "Role creation failed");
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateRole")
    public Response updateRoll(UpdateRollRequest updateRollRequest) {

        UpdateRollRequestBO updateFeedbackRequestBO = new UpdateRollRequestBO();
        updateFeedbackRequestBO.setRoleId(updateRollRequest.getRoleId());
        updateFeedbackRequestBO.setIsAll(updateRollRequest.getIsAll());
        updateFeedbackRequestBO.setName(updateRollRequest.getName());
        updateFeedbackRequestBO.setMenuAccess(updateRollRequest.getMenuAccess());
        updateFeedbackRequestBO.setOutletAccess(updateRollRequest.getOutletAccess());

        UserRequestHandler userRequestHandler = new UserRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (userRequestHandler.updateRoll(updateFeedbackRequestBO)) {
                return ResponseGenerator.generateSuccessResponse(messageResponse, "Role updated successfully");
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the role.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the role.");
        }
    }

    @POST
    @Path("/changePassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changePassword(ChangePasswordRequest changePwdReq) {
        ChangePasswordBO changePwdBO = new ChangePasswordBO(
                changePwdReq.getUserId(), changePwdReq.getOldPassword(),
                changePwdReq.getNewPassword());

        UserRequestHandler appUserRequestHandler = new UserRequestHandler();
        MessageResponse loginResponse = new MessageResponse();
        try {
            if (appUserRequestHandler.changePassword(changePwdBO)) {
                return ResponseGenerator.generateSuccessResponse(loginResponse, "Password updated.");
            } else {
                return ResponseGenerator.generateFailureResponse(loginResponse, "Password update failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(loginResponse, "Password update failed.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/resetPassword")
    public Response changePassword(ResetPasswordRequest resetPasswordRequest,@HeaderParam("Auth") String auth) throws Exception {
        if (auth != null && UserRequestValidation.isRequestValid(auth)) {
        ResetPasswordRequestBO resetPasswordRequestBO = new ResetPasswordRequestBO(
                resetPasswordRequest.getId(),
                resetPasswordRequest.getNewPassword());
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        if (userRequestHandler.resetPassword(resetPasswordRequestBO)) {
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Password has been reset successfully.");
        } else {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Reset password failed.");
       }
        } else {
            return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
        }
    }
}
