package com.barbeque.service;

import com.barbeque.request.bo.FeedbackRequestBO;
import com.barbeque.request.feedback.FeedbackRequest;
import com.barbeque.requesthandler.FeedbackRequestHandler;
import com.barbeque.response.util.SuccessResponse;
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

        SuccessResponse successResponse = new SuccessResponse();
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        try {
            int feedbackId = feedbackRequestHandler.addFeedback(feedbackRequestBO, customerId);
            if (feedbackId > 0) {
                return ResponseGenerator.generateSuccessResponse(successResponse, String.valueOf(feedbackId));
            } else {
                return ResponseGenerator.generateSuccessResponse(successResponse, "feedback creation failed.");

            }
        } catch (SQLException sqlException) {
            SuccessResponse SuccessResponse = new SuccessResponse();
            return ResponseGenerator.generateSuccessResponse(SuccessResponse, "feedback creation failed.");
        }
    }

    /*@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateQuestion(UpdateQueRequest updateQueRequest) throws SQLException {

        UpdateQueRequestBO updateQueRequestBO = new UpdateQueRequestBO();
        updateQueRequestBO.setId(updateQueRequest.getId());
        updateQueRequestBO.setQuestionDesc(updateQueRequest.getQuestionDesc());
        updateQueRequestBO.setQuestionType(updateQueRequest.getQuestionType());
        updateQueRequestBO.setParentQuestionId(updateQueRequest.getParentQuestionId());
        updateQueRequestBO.setParentAnswerId(updateQueRequest.getParentAnswerId());
        updateQueRequestBO.setAnswerSymbol(updateQueRequest.getAnswerSymbol());

        QuestionRequestHandler questionRequestHandler = new QuestionRequestHandler();
        SuccessResponse successResponse = new SuccessResponse();
        if (questionRequestHandler.updateQuestion(updateQueRequestBO)) {
            return ResponseGenerator.generateSuccessResponse(successResponse, "Question updated successfully");
        } else {
            return ResponseGenerator.generateFailureResponse(successResponse, "Unable to update the question.");
        }
    }*/
}