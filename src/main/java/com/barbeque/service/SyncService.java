package com.barbeque.service;

import com.barbeque.bo.SettingRequestBO;
import com.barbeque.bo.SmsSettingRequestBO;
import com.barbeque.bo.UpdateSettingRequestBO;
import com.barbeque.dao.Sync.SmsDAO;
import com.barbeque.request.user.SettingRequest;
import com.barbeque.request.user.SmsSettingRequest;
import com.barbeque.request.user.UpdateSettingRequest;
import com.barbeque.requesthandler.SyncRequestHandler;
import com.barbeque.response.user.SettingResponse;
import com.barbeque.response.user.SmsSettingResponseList;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.util.ResponseGenerator;
import com.barbeque.response.util.VersionInfoResponse;
import com.barbeque.sync.*;

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
    public Response addFeedback() {
        MessageResponse messageResponse = new MessageResponse();
        try {
            Data data = Synchronize.callURL("http://crm.bnhl.in/CRMProfile/Service1.svc/GetGSIHierarchy/");
            SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
            syncRequestHandler.syncData(data);
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Data synchronized successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Synchronization failed.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/androidVersion")
    public Response getOutletById() {
        SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            VersionInfoResponse versionInfoResponse = syncRequestHandler.getAndroidVersion();
            return ResponseGenerator.generateSuccessResponse(versionInfoResponse, "Latest Version");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving details.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response saveSetting(SettingRequest settingRequest) {
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
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in saving settings.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/fetch")
    public Response fetchSettings() {
        SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            SettingResponse settingResponse = syncRequestHandler.fetchSettings();
            return ResponseGenerator.generateSuccessResponse(settingResponse, "Message template");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving details.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveSmsSettings")
    public Response saveSmsSettings(SmsSettingRequest settingRequest) {
        SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
        SmsSettingRequestBO settingRequestBO = new SmsSettingRequestBO();
        MessageResponse messageResponse = new MessageResponse();
        try {
            settingRequestBO.setApi(settingRequest.getApi());
            settingRequestBO.setSenderId(settingRequest.getSenderId());
            settingRequestBO.setCampaign(settingRequest.getCampaign());
            settingRequestBO.setCountryCode(settingRequest.getCountryCode());
            settingRequestBO.setName(settingRequest.getName());
            if(!SmsDAO.getSettingsByName(settingRequestBO.getName())) {
                int id = syncRequestHandler.saveSmsSettings(settingRequestBO);
                return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(id));
            }else{
                return ResponseGenerator.generateFailureResponse(messageResponse, "Name already exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in saving settings.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateSmsSettings")
    public Response updateSmsSettings(UpdateSettingRequest settingRequest) {
        SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
        UpdateSettingRequestBO settingRequestBO = new UpdateSettingRequestBO();
        MessageResponse messageResponse = new MessageResponse();
        try {
            settingRequestBO.setApi(settingRequest.getApi());
            settingRequestBO.setId(settingRequest.getId());
            settingRequestBO.setSenderId(settingRequest.getSenderId());
            settingRequestBO.setCampaign(settingRequest.getCampaign());
            settingRequestBO.setCountryCode(settingRequest.getCountryCode());
            settingRequestBO.setName(settingRequest.getName());
            if(!SmsDAO.getSettingsByUpdateName(settingRequestBO.getName(),settingRequestBO.getId())) {
                syncRequestHandler.updateSmsSettings(settingRequestBO);
                return ResponseGenerator.generateSuccessResponse(messageResponse, "settings are updated.");
            }else{
                return ResponseGenerator.generateFailureResponse(messageResponse, "Name already exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in saving settings.");
        }
    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/fetchSmsSettings")
    public Response fetchSmsSettings() {
        SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
        SmsSettingResponseList settingResponse = new SmsSettingResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            settingResponse.setSmsSettings(syncRequestHandler.fetchSmsSettings());
            return ResponseGenerator.generateSuccessResponse(settingResponse, "Message settings");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving details.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createArchive")
    public Response archiveFeedback() {
        SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            syncRequestHandler.archiveFeedback();
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Data is archived.");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in creation.");
        }
    }
}
