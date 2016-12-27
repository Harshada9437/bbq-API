package com.barbeque.service;

import com.barbeque.exceptions.TemplateNotFoundException;
import com.barbeque.request.bo.AssignQuestionRequestBO;
import com.barbeque.request.bo.TemplateRequestBO;
import com.barbeque.request.bo.UpdateTemplateRequestBO;
import com.barbeque.request.template.AssignQuestionRequest;
import com.barbeque.request.template.TemplateRequest;
import com.barbeque.request.template.UpdateTemplateRequest;
import com.barbeque.requesthandler.TemplateRequestHandler;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.template.*;
import com.barbeque.response.util.ResponseGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/template")
public class TemplateService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createTemplate(TemplateRequest templateRequest) {
        TemplateRequestBO templateRequestBO = new TemplateRequestBO();
        templateRequestBO.setTemplateDesc(templateRequest.getTemplateDesc());
        templateRequestBO.setStatus(templateRequest.getStatus());

        MessageResponse messageResponse = new MessageResponse();
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        try {
            int templateId = templateRequestHandler.createTemplate(templateRequestBO);
            if (templateId > 0) {
                return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(templateId));
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Template Creation Failed");
            }
        } catch (SQLException sqlException) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Template Creation Failed");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateTemplate(UpdateTemplateRequest updateTemplateRequest, @HeaderParam("Auth") String auth) throws Exception {
        UpdateTemplateRequestBO updateTemplateRequestBO = new UpdateTemplateRequestBO();
        updateTemplateRequestBO.setId(updateTemplateRequest.getId());
        updateTemplateRequestBO.setTemplateDesc(updateTemplateRequest.getTemplateDesc());
        updateTemplateRequestBO.setStatus(updateTemplateRequest.getStatus());

        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        MessageResponse messageResponse = new MessageResponse();

        if (templateRequestHandler.updateTemplate(updateTemplateRequestBO)) {
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Template updated successfully");
        } else {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the template.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/assignQuestion/{template_id}")
    public Response assignQuestion(AssignQuestionRequest assignQuestionRequest, @PathParam("template_id") int templateId) throws Exception {
        AssignQuestionRequestBO assignQuestionRequestBO = new AssignQuestionRequestBO();
        assignQuestionRequestBO.setQuestionId(assignQuestionRequest.getQuestionId());
        assignQuestionRequestBO.setPriority(assignQuestionRequest.getPriority());

        TempQueResponse tempQueResponse = new TempQueResponse();
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        try {
            if (templateRequestHandler.assignQuestion(assignQuestionRequestBO, templateId)) {
                return ResponseGenerator.generateSuccessResponse(tempQueResponse, "Questions are assigned");
            } else {
                return ResponseGenerator.generateFailureResponse(tempQueResponse, "Assign question Failed");
            }
        } catch (SQLException sqlException) {
            return ResponseGenerator.generateFailureResponse(tempQueResponse, "Assign question Failed");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listQuestions/{template_id}")
    public Response getAssignedQuestions(@PathParam("template_id") int templateId) throws Exception {
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        TempQueListResponse response = new TempQueListResponse();
        try {
            response.setQuestions(templateRequestHandler.getAssignedQuestions(templateId));
            return ResponseGenerator.generateSuccessResponse(response, "list of questions.");

        } catch (TemplateNotFoundException e) {
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid template id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseGenerator.generateResponse(response);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteAssignQuestion/{template_id}/{question_id}")
    public Response removeBatchDetails(@PathParam("template_id") int templateId, @PathParam("question_id") int queId) throws Exception {
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            templateRequestHandler.removeQuestionDetails(templateId, queId);
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Question has been removed.");

        } catch (SQLException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Please first remove the details assigned to question.");

        } catch (TemplateNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid template.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getTemplateList() throws Exception {
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        TemplateResponse templateResponse = new TemplateResponse();
        try {
            templateResponse.setTemplateResponseList(templateRequestHandler.getTemplate());
            return ResponseGenerator.generateSuccessResponse(templateResponse, "Template are available");
        } catch (TemplateNotFoundException e) {
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Template State ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseGenerator.generateResponse(templateResponse);

    }
}
