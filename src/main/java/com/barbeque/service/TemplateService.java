package com.barbeque.service;

import com.barbeque.bo.UpdateAssignQuestionRequestBO;
import com.barbeque.dao.template.QueTempDAO;
import com.barbeque.dao.template.TemplateDAO;
import com.barbeque.exceptions.TemplateNotFoundException;
import com.barbeque.bo.AssignQuestionRequestBO;
import com.barbeque.bo.TemplateRequestBO;
import com.barbeque.bo.UpdateTemplateRequestBO;
import com.barbeque.request.template.AssignQuestionRequest;
import com.barbeque.request.template.TemplateRequest;
import com.barbeque.request.template.UpdateAssignQuestionRequest;
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

        MessageResponse messageResponse = new MessageResponse();
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        try {
            if (!TemplateDAO.getTemplateByName(templateRequest.getTemplateDesc())) {
                int templateId = templateRequestHandler.createTemplate(templateRequestBO);
                return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(templateId));
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Template description already exist");
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Template Creation Failed");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateTemplate(UpdateTemplateRequest updateTemplateRequest) {
        UpdateTemplateRequestBO updateTemplateRequestBO = new UpdateTemplateRequestBO();
        updateTemplateRequestBO.setId(updateTemplateRequest.getId());
        updateTemplateRequestBO.setTemplateDesc(updateTemplateRequest.getTemplateDesc());
        updateTemplateRequestBO.setStatus(updateTemplateRequest.getStatus());

        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        MessageResponse messageResponse = new MessageResponse();

        try {
            if (templateRequestHandler.updateTemplate(updateTemplateRequestBO)) {
                return ResponseGenerator.generateSuccessResponse(messageResponse, "Template updated successfully");
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the template.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the template.");
        } catch (TemplateNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid template id.");
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/assignQuestion/{template_id}")
    public Response assignQuestion(AssignQuestionRequest assignQuestionRequest, @PathParam("template_id") int templateId) {
        AssignQuestionRequestBO assignQuestionRequestBO = new AssignQuestionRequestBO();
        assignQuestionRequestBO.setQuestionId(assignQuestionRequest.getQuestionId());
        assignQuestionRequestBO.setPriority(assignQuestionRequest.getPriority());

        MessageResponse tempQueResponse = new MessageResponse();
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        try {
            if (!QueTempDAO.isAlreadyAssigned(assignQuestionRequest.getQuestionId(), templateId)) {
                templateRequestHandler.assignQuestion(assignQuestionRequestBO, templateId);
                return ResponseGenerator.generateSuccessResponse(tempQueResponse, "Questions are assigned");
            } else {
                return ResponseGenerator.generateFailureResponse(tempQueResponse, "Questiuon is already assigned.");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return ResponseGenerator.generateFailureResponse(tempQueResponse, "Assign question Failed");
        } catch (TemplateNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(tempQueResponse, "Invalid template id");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listQuestions/{template_id}")
    public Response getAssignedQuestions(@PathParam("template_id") int templateId) {
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        TempQueListResponse response = new TempQueListResponse();
        MessageResponse messageResponse = new MessageResponse();
        try {
            response.setQuestions(templateRequestHandler.getAssignedQuestions(templateId));
            return ResponseGenerator.generateSuccessResponse(response, "list of questions.");
        } catch (TemplateNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid template id");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "failed to retrieve.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateteAssignQuestion")
    public Response getUpdatedAssignedQuestions(UpdateAssignQuestionRequest updateAssignQuestionRequest) {

        UpdateAssignQuestionRequestBO updateAssignQuestionRequestBO = new UpdateAssignQuestionRequestBO();
        updateAssignQuestionRequestBO.setQuestionId(updateAssignQuestionRequest.getQuestionId());
        updateAssignQuestionRequestBO.setPriority(updateAssignQuestionRequest.getPriority());
        updateAssignQuestionRequestBO.setTemplateId(updateAssignQuestionRequest.getTemplateId());


        MessageResponse messageResponse = new MessageResponse();
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        try {
            templateRequestHandler.updateAssignQuestion(updateAssignQuestionRequestBO);
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Priority of question updated successfully");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update Priority of question");
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteAssignQuestion/{template_id}/{question_id}")
    public Response deleteAssignQuestion(@PathParam("template_id") int templateId, @PathParam("question_id") int queId) {
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            templateRequestHandler.removeQuestionDetails(templateId, queId);
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Question has been removed.");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Please first remove the details assigned to question.");
        } catch (TemplateNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid template.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getTemplateList() {
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        TemplateResponse templateResponse = new TemplateResponse();
        try {
            templateResponse.setTemplateResponseList(templateRequestHandler.getTemplate());
            return ResponseGenerator.generateSuccessResponse(templateResponse, "Template are available");
        } catch (SQLException e) {
            MessageResponse messageResponse = new MessageResponse();
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/templateInfo/{template_id}/{outlet_id}")
    public Response getOutletByStoreId(@PathParam("template_id") int templateId, @PathParam("outlet_id") int outletId) {
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            GetTemplateResponse response = templateRequestHandler.getTemplateById(templateId, outletId);
            return ResponseGenerator.generateSuccessResponse(response, "Template Information");
        } catch (TemplateNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid template id ");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving outlet details. ");
        }
    }
}
