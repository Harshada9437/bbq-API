package com.barbeque.service;

import com.barbeque.bo.FeedbackListRequestBO;
import com.barbeque.bo.FeedbackRequestBO;
import com.barbeque.bo.UpdateFeedbackRequestBO;
import com.barbeque.dao.device.DeviceDAO;
import com.barbeque.dto.request.DeviceDTO;
import com.barbeque.exceptions.FeedbackNotFoundException;
import com.barbeque.request.feedback.FeedbackListRequest;
import com.barbeque.request.feedback.FeedbackRequest;
import com.barbeque.request.feedback.UpdateFeedbackRequest;
import com.barbeque.requesthandler.FeedbackRequestHandler;
import com.barbeque.response.feedback.FeedbackByIdResponse;
import com.barbeque.response.feedback.FeedbackResponseList;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.util.ResponseGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by user on 10/18/2016.
 */
@Path("/feedback")
public class FeedbackService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response addFeedback(FeedbackRequest feedbackRequest) throws Exception {
        FeedbackRequestBO feedbackRequestBO = new FeedbackRequestBO();
        feedbackRequestBO.setOutletId(feedbackRequest.getOutletId());
        feedbackRequestBO.setDeviceId(feedbackRequest.getDeviceId());
        feedbackRequestBO.setFeedbacks(feedbackRequest.getFeedbacks());
        feedbackRequestBO.setTableNo(feedbackRequest.getTableNo());
        feedbackRequestBO.setBillNo(feedbackRequest.getBillNo());
        feedbackRequestBO.setDate(feedbackRequest.getDate());
        feedbackRequestBO.setCustomer(feedbackRequest.getCustomer());

        MessageResponse messageResponse = new MessageResponse();
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        try {
            DeviceDTO deviceDTO = DeviceDAO.getDevice(feedbackRequestBO.getDeviceId());
            if (deviceDTO.getStatus().equals("A")) {
                int feedbackId = feedbackRequestHandler.addFeedback(feedbackRequestBO);
                return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(feedbackId));
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Inactive device.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "feedback creation failed.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateQuestion(UpdateFeedbackRequest updateFeedbackRequest) throws SQLException {

        UpdateFeedbackRequestBO updateFeedbackRequestBO = new UpdateFeedbackRequestBO();
        updateFeedbackRequestBO.setId(updateFeedbackRequest.getId());
        updateFeedbackRequestBO.setQuestionId(updateFeedbackRequest.getQuestionId());
        updateFeedbackRequestBO.setAnswerId(updateFeedbackRequest.getAnswerId());
        updateFeedbackRequestBO.setAnswerText(updateFeedbackRequest.getAnswerText());
        updateFeedbackRequestBO.setDate(updateFeedbackRequest.getDate());
        updateFeedbackRequestBO.setTableNo(updateFeedbackRequest.getTableNo());
        updateFeedbackRequestBO.setRating(updateFeedbackRequest.getRating());
        updateFeedbackRequestBO.setBillNo(updateFeedbackRequest.getBillNo());
        updateFeedbackRequestBO.setModifiedOn(updateFeedbackRequest.getAnswerText());

        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        if (feedbackRequestHandler.updateFeedback(updateFeedbackRequestBO)) {
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Feedback updated successfully");
        } else {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the feedback.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getfeedbackList(FeedbackListRequest feedbackListRequest) throws SQLException {
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        FeedbackListRequestBO feedbackListRequestBO = new FeedbackListRequestBO();
        try {
            feedbackListRequestBO.setFromDate(feedbackListRequest.getFromDate());
            feedbackListRequestBO.setToDate(feedbackListRequest.getToDate());
            feedbackListRequestBO.setOutletId(feedbackListRequest.getOutletId());
            feedbackListRequestBO.setTableNo(feedbackListRequest.getTableNo());
            FeedbackResponseList feedbackResponse = new FeedbackResponseList();
            feedbackResponse.setFeedbacks(feedbackRequestHandler.getfeedbackList1(feedbackListRequestBO));
            return ResponseGenerator.generateSuccessResponse(feedbackResponse, "Successfully retrieved.");
        } catch (SQLException e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve the list");
        }
    }

    @GET
    @Path("/feedbackDetail/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getfeedbackById(@PathParam("id")int id)throws Exception
    {
        FeedbackRequestHandler feedbackRequestHandler=new FeedbackRequestHandler();
        Object response = null;
        MessageResponse messageResponse = new MessageResponse();
        try{
            FeedbackByIdResponse feedbackByIdResponse=feedbackRequestHandler.getfeedbackById(id);
            return ResponseGenerator.generateSuccessResponse(feedbackByIdResponse, "SUCCESS");
        }catch (FeedbackNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID feedback id. ");

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID feedback id. ");
        }
    }
}

