package com.barbeque.service;


import com.barbeque.requesthandler.SyncRequestHandler;
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
}
