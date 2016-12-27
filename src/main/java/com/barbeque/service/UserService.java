package com.barbeque.service;

import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.request.bo.LoginRequestBO;
import com.barbeque.request.user.LoginRequest;
import com.barbeque.requesthandler.UserRequestHandler;
import com.barbeque.response.user.LoginResponse;
import com.barbeque.response.user.LoginResponseBO;
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
    public Response logout(@HeaderParam("sessionId") String sessionId/*, @HeaderParam("Auth") String auth*/) throws Exception {
       /* if (auth != null && RequestValidation.isRequestValid(auth)) {*/
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
      /*  } else {
            return ResponseGenerator.generateResponse(RequestValidator.getUnautheticatedResponse());
        }*/
    }
}
