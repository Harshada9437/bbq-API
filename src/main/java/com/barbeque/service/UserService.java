package com.barbeque.service;

import com.barbeque.exceptions.RollNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.bo.LoginRequestBO;
import com.barbeque.request.user.LoginRequest;
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
    @Path("/rollDetail/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getrollById(@PathParam("id")int id)throws Exception
    {
       UserRequestHandler userRequestHandler=new UserRequestHandler();
        Object response = null;
        try{
            RollByIdResponse rollByIdResponse=userRequestHandler.getrollById(id);
            return ResponseGenerator.generateSuccessResponse(rollByIdResponse, "SUCCESS");
        }catch (RollNotFoundException e) {
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID RollId ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseGenerator.generateResponse(response);
    }
}
