package com.barbeque.service;

import com.barbeque.exceptions.TemplateNotFoundException;
import com.barbeque.request.bo.AssignOutletRequestBO;
import com.barbeque.request.outlet.AssignOutletRequest;
import com.barbeque.request.outlet.AssignoutletResponse;
import com.barbeque.requesthandler.AssignOutletRequesthandler;
import com.barbeque.response.FailureResponse;
import com.barbeque.response.outlet.OutletResponse;
import com.barbeque.response.util.ResponseGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by System-2 on 12/20/2016.
 */
@Path("/outlet")
public class OutLetService
{
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/assignOutlet/{outlet_id}")
    public Response assignQuestion (AssignOutletRequest assignOutletRequest, @PathParam("outlet_id") int outletId)throws Exception
    {
        AssignOutletRequestBO assignOutletRequestBO=new AssignOutletRequestBO();
        assignOutletRequestBO.setTemplateId(assignOutletRequest.getTemplateId());
        assignOutletRequestBO.setFromDate(assignOutletRequest.getFromDate());
        assignOutletRequestBO.setToDate(assignOutletRequest.getToDate());


        AssignoutletResponse assignoutletResponse=new AssignoutletResponse();
        AssignOutletRequesthandler assignOutletRequesthandler=new  AssignOutletRequesthandler();
        try {
            if(assignOutletRequesthandler.assignoutlet(assignOutletRequestBO,outletId))
            {
                return ResponseGenerator.generateSuccessResponse(assignoutletResponse, "Outlet are assigned");
            }
            else
            {
                return ResponseGenerator.generateFailureResponse(assignoutletResponse, "Assign outlet Failed");
            }
        }catch (SQLException sqlException) {
        return ResponseGenerator.generateFailureResponse(assignoutletResponse, "Assign outlet Failed");
    }



    }
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getOutletList ()throws Exception
    {
  AssignOutletRequesthandler assignOutletRequesthandler=new AssignOutletRequesthandler();
        OutletResponse outletResponse=new OutletResponse();
        try
        {
            outletResponse.setOutletResponseList(assignOutletRequesthandler.getOutlate());
            return ResponseGenerator.generateSuccessResponse(outletResponse, "Template are available");
        } catch (TemplateNotFoundException e)
        {
            FailureResponse failureResponse = new FailureResponse();
            return ResponseGenerator.generateFailureResponse(failureResponse, "Template State ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseGenerator.generateResponse(outletResponse);
        }
    }


