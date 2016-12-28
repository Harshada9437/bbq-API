package com.barbeque.service;

import com.barbeque.exceptions.OutletNotFoundException;
import com.barbeque.exceptions.TemplateNotFoundException;
import com.barbeque.request.outlet.AssignTemplateRequest;
import com.barbeque.request.outlet.UpdateSettingsRequest;
import com.barbeque.requesthandler.OutletRequesthandler;
import com.barbeque.response.outlet.OutletResponseList;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.outlet.OutletResponse;
import com.barbeque.response.util.ResponseGenerator;
import com.barbeque.bo.AssignTemplateRequestBO;
import com.barbeque.bo.UpdateSettingsRequestBO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by System-2 on 12/20/2016.
 */
@Path("/outlet")
public class OutLetService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/assignTemplate/{outlet_id}")
    public Response assignTemplate(AssignTemplateRequest assignTemplateRequest, @PathParam("outlet_id") int outletId) throws Exception {
        AssignTemplateRequestBO assignOutletRequestBO = new AssignTemplateRequestBO();
        assignOutletRequestBO.setTemplateId(assignTemplateRequest.getTemplateId());
        assignOutletRequestBO.setFromDate(assignTemplateRequest.getFromDate());
        assignOutletRequestBO.setToDate(assignTemplateRequest.getToDate());


        MessageResponse assignoutletResponse = new MessageResponse();
        OutletRequesthandler outletRequesthandler = new OutletRequesthandler();
        try {
            if (outletRequesthandler.assignoutlet(assignOutletRequestBO, outletId)) {
                return ResponseGenerator.generateSuccessResponse(assignoutletResponse, "Templates are assigned");
            } else {
                return ResponseGenerator.generateFailureResponse(assignoutletResponse, "Template outlet Failed");
            }
        } catch (SQLException sqlException) {
            return ResponseGenerator.generateFailureResponse(assignoutletResponse, "Template outlet Failed");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateSettings/{outlet_id}")
    public Response updateSettings(UpdateSettingsRequest updateSettingsRequest, @PathParam("outlet_id") int outletId) throws Exception {
        UpdateSettingsRequestBO updateSettingsRequestBO = new UpdateSettingsRequestBO();
        updateSettingsRequestBO.setMobileNoLength(updateSettingsRequest.getMobileNoLength());
        updateSettingsRequestBO.setBannerUrl(updateSettingsRequest.getBannerUrl());
        updateSettingsRequestBO.setTableNoRange(updateSettingsRequest.getTableNoRange());
        MessageResponse assignoutletResponse = new MessageResponse();
        OutletRequesthandler outletRequesthandler = new OutletRequesthandler();
        try {
            if (outletRequesthandler.updateSettings(updateSettingsRequestBO, outletId)) {
                return ResponseGenerator.generateSuccessResponse(assignoutletResponse, "Outlet settings are updated");
            } else {
                return ResponseGenerator.generateFailureResponse(assignoutletResponse, "update setting Failed");
            }
        } catch (SQLException sqlException) {
            return ResponseGenerator.generateFailureResponse(assignoutletResponse, "update setting Failed");
        }


    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getOutletList() throws Exception {
        OutletRequesthandler outletRequesthandler = new OutletRequesthandler();
        OutletResponse outletResponse = new OutletResponse();
        try {
            outletResponse.setOutletResponseList(outletRequesthandler.getOutlate());
            return ResponseGenerator.generateSuccessResponse(outletResponse, "outlet are available");
        } catch (TemplateNotFoundException e) {
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "failed to retrieve outlet list");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseGenerator.generateResponse(outletResponse);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/outletInfoById/{outlet_id}")
    public Response getOutletById(@PathParam("outlet_id") int outletId) throws Exception {
        OutletRequesthandler outletRequesthandler = new OutletRequesthandler();
        OutletResponseList outletResponse = new OutletResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            outletResponse = outletRequesthandler.getOutletById(outletId);
            return ResponseGenerator.generateSuccessResponse(outletResponse, "Outlet Information");
        } catch (OutletNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid outlet id ");

        } catch (SQLException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving outlet details. ");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/outletInfo/{outlet_store_id}")
    public Response getOutletByStoreId(@PathParam("outlet_store_id") int storeId) throws Exception {
        OutletRequesthandler outletRequesthandler = new OutletRequesthandler();
        OutletResponseList outletResponse = new OutletResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            outletResponse = outletRequesthandler.getOutletByStoreId(storeId);
            return ResponseGenerator.generateSuccessResponse(outletResponse, "Outlet Information");
        } catch (OutletNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid store id ");

        } catch (SQLException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving outlet details. ");
        }
    }
}


