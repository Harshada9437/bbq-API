package com.barbeque.service;

import com.barbeque.bo.FeedbackRequestBO;
import com.barbeque.bo.UpdateFeedbackRequestBO;
import com.barbeque.request.feedback.FeedbackRequest;
import com.barbeque.request.feedback.UpdateFeedbackRequest;
import com.barbeque.requesthandler.FeedbackRequestHandler;
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
    @Path("/create/{customer_id}")
    public Response addFeedback(FeedbackRequest feedbackRequest, @PathParam("customer_id") int customerId) throws SQLException {
        FeedbackRequestBO feedbackRequestBO = new FeedbackRequestBO();
        feedbackRequestBO.setOutletId(feedbackRequest.getOutletId());
        feedbackRequestBO.setQuestionId(feedbackRequest.getQuestionId());
        feedbackRequestBO.setAnswerId(feedbackRequest.getAnswerId());
        feedbackRequestBO.setAnswerText(feedbackRequest.getAnswerText());
        feedbackRequestBO.setTableNo(feedbackRequest.getTableNo());
        feedbackRequestBO.setBillNo(feedbackRequest.getBillNo());
        feedbackRequestBO.setRating(feedbackRequest.getRating());
        feedbackRequestBO.setDate(feedbackRequest.getDate());

        MessageResponse messageResponse = new MessageResponse();
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        try {
            int feedbackId = feedbackRequestHandler.addFeedback(feedbackRequestBO, customerId);
            if (feedbackId > 0) {
                return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(feedbackId));
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "feedback creation failed.");

            }
        } catch (SQLException sqlException) {
            MessageResponse MessageResponse = new MessageResponse();
            return ResponseGenerator.generateSuccessResponse(MessageResponse, "feedback creation failed.");
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

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getfeedbackList() throws Exception {
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        FeedbackResponseList feedbackResponseList = new FeedbackResponseList();
        feedbackResponseList.setFeedbacks(feedbackRequestHandler.getfeedbackList());
        return ResponseGenerator.generateSuccessResponse(feedbackResponseList, "List of feedbacks.");
    }
}