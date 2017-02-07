package com.barbeque.service;


import com.barbeque.bo.SettingRequestBO;
import com.barbeque.bo.SmsSettingRequestBO;
import com.barbeque.request.user.SettingRequest;
import com.barbeque.request.user.SmsSettingRequest;
import com.barbeque.requesthandler.SyncRequestHandler;
import com.barbeque.response.user.SettingResponse;
import com.barbeque.response.user.SmsSettingResponse;
import com.barbeque.response.user.SmsSettingResponseList;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.util.ResponseGenerator;
import com.barbeque.response.util.VersionInfoResponse;
import com.barbeque.sync.*;
import org.codehaus.jettison.json.JSONException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;


/**
 * Created by System-2 on 1/9/2017.
 */

@Path("/settings")
public class SyncService {
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sync")
    public Response addFeedback() throws SQLException, JSONException {
        MessageResponse messageResponse = new MessageResponse();
        try {
            Data data = Synchronize.callURL("http://crm.bnhl.in/CRMProfile/Service1.svc/GetGSIHierarchy/");
            SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
            syncRequestHandler.syncData(data);
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Data synchronized successfully");
        } catch (JSONException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Synchronization failed.");
        } catch (SQLException sq) {
            sq.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Synchronization failed.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/androidVersion")
    public Response getOutletById() throws Exception {
        SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
        VersionInfoResponse versionInfoResponse = new VersionInfoResponse();
        MessageResponse messageResponse = new MessageResponse();
        try {
            versionInfoResponse = syncRequestHandler.getAndroidVersion();
            return ResponseGenerator.generateSuccessResponse(versionInfoResponse, "Latest Version");
        } catch (SQLException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving details.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response saveSetting(SettingRequest settingRequest) throws Exception {
        SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
        SettingRequestBO settingRequestBO = new SettingRequestBO();
        MessageResponse messageResponse = new MessageResponse();
        try {
            settingRequestBO.setSmsTemplate(settingRequest.getSmsTemplate());
            if(syncRequestHandler.saveSetting(settingRequestBO)) {
                return ResponseGenerator.generateSuccessResponse(messageResponse, "Saved successfully");
            }else{
                return ResponseGenerator.generateFailureResponse(messageResponse, "Error in saving settings.");
            }
        } catch (SQLException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in saving settings.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/fetch")
    public Response fetchSettings() throws Exception {
        SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
        SettingResponse settingResponse = new SettingResponse();
        MessageResponse messageResponse = new MessageResponse();
        try {
            settingResponse = syncRequestHandler.fetchSettings();
            return ResponseGenerator.generateSuccessResponse(settingResponse, "Message template");
        } catch (SQLException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving details.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveSmsSettings")
    public Response saveSmsSettings(SmsSettingRequest settingRequest) throws Exception {
        SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
        SmsSettingRequestBO settingRequestBO = new SmsSettingRequestBO();
        MessageResponse messageResponse = new MessageResponse();
        try {
            settingRequestBO.setApi(settingRequest.getApi());
            settingRequestBO.setSenderId(settingRequest.getSenderId());
            settingRequestBO.setCampaign(settingRequest.getCampaign());
            settingRequestBO.setCountryCode(settingRequest.getCountryCode());
            int id = syncRequestHandler.saveSmsSettings(settingRequestBO);
            if(id > 0) {
                return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(id));
            }else{
                return ResponseGenerator.generateFailureResponse(messageResponse, "Error in saving settings.");
            }
        } catch (SQLException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in saving settings.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/fetchSmsSettings")
    public Response fetchSmsSettings() throws Exception {
        SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
        SmsSettingResponseList settingResponse = new SmsSettingResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            settingResponse.setSmsSettings(syncRequestHandler.fetchSmsSettings());
            return ResponseGenerator.generateSuccessResponse(settingResponse, "Message settings");
        } catch (SQLException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving details.");
        }
    }
}
