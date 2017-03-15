package com.barbeque.service;

import com.barbeque.bo.OutletListRequestBO;
import com.barbeque.dao.template.TemplateDAO;
import com.barbeque.dto.request.TempDTO;
import com.barbeque.exceptions.OutletNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.request.outlet.AssignTemplateRequest;
import com.barbeque.request.outlet.OutletListRequest;
import com.barbeque.request.outlet.UpdateSettingsRequest;
import com.barbeque.requesthandler.OutletRequestHandler;
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
import java.util.List;

/**
 * Created by System-2 on 12/20/2016.
 */
@Path("/outlet")
public class OutLetService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/assignTemplate/{outlet_id}")
    public Response assignTemplate(AssignTemplateRequest assignTemplateRequest, @PathParam("outlet_id") int outletId) {
        AssignTemplateRequestBO assignTemplateRequestBO = new AssignTemplateRequestBO();
        assignTemplateRequestBO.setTemplateId(assignTemplateRequest.getTemplateId());
        assignTemplateRequestBO.setFromDate(assignTemplateRequest.getFromDate());
        assignTemplateRequestBO.setToDate(assignTemplateRequest.getToDate());

        MessageResponse assignoutletResponse = new MessageResponse();
        OutletRequestHandler outletRequestHandler = new OutletRequestHandler();
        try {
            List<TempDTO> tempDTOs = TemplateDAO.getTemplateByOutletId(outletId);
            if (tempDTOs.size() == 0) {
                outletRequestHandler.assignTemplate(assignTemplateRequestBO, outletId);
                return ResponseGenerator.generateSuccessResponse(assignoutletResponse, "Templates are assigned");
            } else {
                return ResponseGenerator.generateFailureResponse(assignoutletResponse, "Template is already assigned to this outlet.");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return ResponseGenerator.generateFailureResponse(assignoutletResponse, "Template assignment Failed");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateSettings/{outlet_id}")
    public Response updateSettings(UpdateSettingsRequest updateSettingsRequest, @PathParam("outlet_id") int outletId) {
        UpdateSettingsRequestBO updateSettingsRequestBO = new UpdateSettingsRequestBO();

        updateSettingsRequestBO.setMobileNoLength(updateSettingsRequest.getMobileNoLength());
        updateSettingsRequestBO.setBannerUrl(updateSettingsRequest.getBannerUrl());
        updateSettingsRequestBO.setTableNoRange(updateSettingsRequest.getTableNoRange());
        updateSettingsRequestBO.setPocName(updateSettingsRequest.getPocName());
        updateSettingsRequestBO.setPocMobile(updateSettingsRequest.getPocMobile());
        updateSettingsRequestBO.setPocEmail(updateSettingsRequest.getPocEmail());
        updateSettingsRequestBO.setMgrEmail(updateSettingsRequest.getMgrEmail());
        updateSettingsRequestBO.setMgrMobile(updateSettingsRequest.getMgrMobile());
        updateSettingsRequestBO.setMgrName(updateSettingsRequest.getMgrName());
        updateSettingsRequestBO.setSmsGatewayId(updateSettingsRequest.getSmsGatewayId());

        MessageResponse assignoutletResponse = new MessageResponse();
        OutletRequestHandler outletRequestHandler = new OutletRequestHandler();
        try {
            if (outletRequestHandler.updateSettings(updateSettingsRequestBO, outletId)) {
                return ResponseGenerator.generateSuccessResponse(assignoutletResponse, "Outlet settings are updated");
            } else {
                return ResponseGenerator.generateFailureResponse(assignoutletResponse, "update setting Failed");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return ResponseGenerator.generateFailureResponse(assignoutletResponse, "update setting Failed");
        }


    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getOutletList(OutletListRequest outletListRequest) {
        OutletRequestHandler outletRequestHandler = new OutletRequestHandler();
        OutletResponse outletResponse = new OutletResponse();
        MessageResponse messageResponse = new MessageResponse();
        try {
            OutletListRequestBO outletListRequestBO = new OutletListRequestBO();
            outletListRequestBO.setOutletId(outletListRequest.getOutletId());
            outletListRequestBO.setUserId(outletListRequest.getUserId());
            outletResponse.setOutletResponseList(outletRequestHandler.getOutlate(outletListRequestBO));
            return ResponseGenerator.generateSuccessResponse(outletResponse, "outlet are available");
        } catch (UserNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "invalid user id");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "invalid user id");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/outletInfoById/{outlet_id}")
    public Response getOutletById(@PathParam("outlet_id") int outletId) {
        OutletRequestHandler outletRequestHandler = new OutletRequestHandler();
        OutletResponseList outletResponse = new OutletResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            outletResponse = outletRequestHandler.getOutletById(outletId);
            return ResponseGenerator.generateSuccessResponse(outletResponse, "Outlet Information");
        } catch (OutletNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid outlet id ");

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving outlet details. ");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/outletInfo/{outlet_store_id}")
    public Response getOutletByStoreId(@PathParam("outlet_store_id") String storeId) {
        OutletRequestHandler outletRequestHandler = new OutletRequestHandler();
        OutletResponseList outletResponse = new OutletResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            outletResponse = outletRequestHandler.getOutletByStoreId(storeId);
            return ResponseGenerator.generateSuccessResponse(outletResponse, "Outlet Information");
        } catch (OutletNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid store id ");

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving outlet details. ");
        }
    }
}


