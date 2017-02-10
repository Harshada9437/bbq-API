package com.barbeque.service;

   import com.barbeque.bo.RollRequestBO;
   import com.barbeque.bo.UpdateRollRequestBO;
   import com.barbeque.bo.UserRequestBO;
import com.barbeque.dao.user.UsersDAO;
import com.barbeque.dto.response.LoginResponseDTO;
import com.barbeque.exceptions.RoleNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.bo.LoginRequestBO;
import com.barbeque.request.user.LoginRequest;
import com.barbeque.request.user.RollRequest;
import com.barbeque.request.user.UserRequest;
import com.barbeque.requesthandler.UserRequestHandler;
import com.barbeque.response.user.*;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.util.ResponseGenerator;

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
        try {
            LoginResponseBO loginResponseBO = userRequestHandler
                    .login(loginRequestBO);
            if (loginResponseBO.getSessionId() != null && loginResponseBO.getStatus().equals("A")) {
                return ResponseGenerator.generateSuccessResponse(loginResponse, String.valueOf(loginResponseBO
                        .getSessionId()));

            } else {
                return ResponseGenerator.generateFailureResponse(loginResponse, "Invalid password or inactive user.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(loginResponse, "Invalid Log in");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseGenerator.generateResponse(loginResponse);
    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@HeaderParam("sessionId") String sessionId) throws Exception {
            LoginResponse loginResponse = new LoginResponse();
            try {
                UserRequestHandler userRequestHandler = new UserRequestHandler();
                String[] sessionIdParts = sessionId.split("@");
                Boolean isLoggedOut = userRequestHandler.logout(Integer.parseInt(sessionIdParts[1]), sessionIdParts[0]);

                if (isLoggedOut) {
                    return ResponseGenerator.generateSuccessResponse(loginResponse, "Log out successfully.");

                } else {
                    return ResponseGenerator.generateFailureResponse(loginResponse, "Unable to log out the current user.");

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (UserNotFoundException e) {
                return ResponseGenerator.generateFailureResponse(loginResponse, "Invalid user id");

            }
            return ResponseGenerator.generateResponse(loginResponse);
    }

    @GET
    @Path("/UserDetail/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getuserById(@PathParam("id")int id)throws Exception {

        UserRequestHandler userRequestHandler=new UserRequestHandler();
        Object response = null;
        try{
           UserdetailsByIdResponse userdetailsByIdResponse=userRequestHandler.getuserById(id);
            return ResponseGenerator.generateSuccessResponse(userdetailsByIdResponse, "SUCCESS");
        }catch (UserNotFoundException e) {
            MessageResponse messageResponse=new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID UserId ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseGenerator.generateResponse(response);


    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/menuList")

    public Response getMenuList() throws Exception {
       UserRequestHandler userRequestHandler = new UserRequestHandler();
       MenuResponseList menuResponseList= new MenuResponseList();
       try {
           menuResponseList.setMenus(userRequestHandler.getMenuList());
           return ResponseGenerator.generateSuccessResponse(menuResponseList, "List of menus.");
       }
       catch (SQLException e){
           e.printStackTrace();
           return ResponseGenerator.generateFailureResponse(menuResponseList, "Failure.");
       }
    }

    @GET
    @Path("/roleDetail/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getroleById(@PathParam("id")int id)throws Exception
    {
       UserRequestHandler userRequestHandler=new UserRequestHandler();
        Object response = null;
        try{
            RoleByIdResponse roleByIdResponse =userRequestHandler.getroleById(id);
            return ResponseGenerator.generateSuccessResponse(roleByIdResponse, "SUCCESS");
        }catch (RoleNotFoundException e) {
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID RollId ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseGenerator.generateResponse(response);
    }



    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/roleList")


    public Response getRoleList() throws Exception {
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        RoleResponseList roleResponseList = new RoleResponseList();
        try {
          roleResponseList.setRoles(userRequestHandler.getRoleList());
            return ResponseGenerator.generateSuccessResponse(roleResponseList, "List of roles.");
        }
        catch (SQLException e){
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(roleResponseList, "Failure.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createUser")
    public Response createUser(UserRequest userRequest) throws Exception {
            UserRequestBO userRequestBO = new UserRequestBO();
            userRequestBO.setUserName(userRequest.getUserName());
            userRequestBO.setEmail(userRequest.getEmail());
            userRequestBO.setPassword(userRequest.getPassword());
            userRequestBO.setRoleId(userRequest.getRoleId());

           MessageResponse createUserResponse= new MessageResponse();
            UserRequestHandler userRequestHandler = new UserRequestHandler();
            try {

                if (!UsersDAO.getuserById(userRequest.getUserName(),userRequest.getEmail())) {
                    int userId = userRequestHandler.createUser(userRequestBO);
                    return ResponseGenerator.generateSuccessResponse(createUserResponse, String.valueOf(userId));

                } else {
                    return ResponseGenerator.generateFailureResponse(createUserResponse, "User already exists.");

                }
            } catch (SQLException sqlException)
            {
                return ResponseGenerator.generateFailureResponse(createUserResponse, "User creation Failed");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseGenerator.generateResponse(createUserResponse);

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createRoll")
    public Response createRoll(RollRequest rollRequest) throws Exception {
        RollRequestBO rollRequestBO = new RollRequestBO();
        rollRequestBO.setName(rollRequest.getName());
        rollRequestBO.setMenuAccess(rollRequest.getMenuAccess());
        rollRequestBO.setOutletAccess(rollRequest.getOutletAccess());


        MessageResponse createUserResponse= new MessageResponse();
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        try {

            if (!UsersDAO.getuserById(rollRequest.getName())) {
                int userId = userRequestHandler.createRoll(rollRequestBO);
                return ResponseGenerator.generateSuccessResponse(createUserResponse, String.valueOf(userId));

            } else {
                return ResponseGenerator.generateFailureResponse(createUserResponse, "User already exists.");

            }
        } catch (SQLException sqlException)
        {
            return ResponseGenerator.generateFailureResponse(createUserResponse, "User creation Failed");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseGenerator.generateResponse(createUserResponse);

    }



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateRoll")
    public Response updateRoll(UpdateRollRequest updateRollRequest) throws SQLException {

        UpdateRollRequestBO updateFeedbackRequestBO= new UpdateRollRequestBO();
        updateFeedbackRequestBO.setRoleId(updateRollRequest.getRoleId());
        updateFeedbackRequestBO.setName(updateRollRequest.getName());
        updateFeedbackRequestBO.setMenuAccess(updateRollRequest.getMenuAccess());
        updateFeedbackRequestBO.setOutletAccess(updateRollRequest.getOutletAccess());

      UserRequestHandler userRequestHandler = new UserRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        if (userRequestHandler.updateRoll(updateFeedbackRequestBO)) {
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Roll updated successfully");
        } else {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the roll.");
        }
    }



}
