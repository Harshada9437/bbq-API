package com.barbeque.service;

import com.barbeque.bo.FeedbackListRequestBO;
import com.barbeque.bo.FeedbackRequestBO;
import com.barbeque.bo.FeedbackTrackingRequestBO;
import com.barbeque.dao.FeedbackDAO;
import com.barbeque.dao.device.DeviceDAO;
import com.barbeque.dto.request.DeviceDTO;
import com.barbeque.exceptions.FeedbackNotFoundException;
import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.request.feedback.FeedbackListRequest;
import com.barbeque.request.feedback.FeedbackRequest;
import com.barbeque.request.feedback.FeedbackTrackingRequest;
import com.barbeque.requesthandler.FeedbackRequestHandler;
import com.barbeque.response.feedback.FeedbackByIdResponse;
import com.barbeque.response.feedback.FeedbackResponseList;
import com.barbeque.response.feedback.FeedbackTrackingResponseList;
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
    public Response addFeedback(FeedbackRequest feedbackRequest) {
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
    @Path("/list")
    public Response getfeedbackList(FeedbackListRequest feedbackListRequest ) {
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        FeedbackListRequestBO feedbackListRequestBO = new FeedbackListRequestBO();
        try {
            feedbackListRequestBO.setFromDate(feedbackListRequest.getFromDate());
            feedbackListRequestBO.setToDate(feedbackListRequest.getToDate());
            feedbackListRequestBO.setOutletId(feedbackListRequest.getOutletId());
            feedbackListRequestBO.setTableNo(feedbackListRequest.getTableNo());
            feedbackListRequestBO.setUserId(feedbackListRequest.getUserId());
            FeedbackResponseList feedbackResponse = new FeedbackResponseList();
            feedbackResponse.setFeedbacks(feedbackRequestHandler.getfeedbackList1(feedbackListRequestBO));
            return ResponseGenerator.generateSuccessResponse(feedbackResponse, "Successfully retrieved.");
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve the list");
        }
    }

    @GET
    @Path("/feedbackDetail/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getfeedbackById(@PathParam("id") int id) {
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            FeedbackByIdResponse feedbackByIdResponse = feedbackRequestHandler.getfeedbackById(id);
            return ResponseGenerator.generateSuccessResponse(feedbackByIdResponse, "SUCCESS");
        } catch (FeedbackNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID feedback id. ");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve. ");
        } catch (QuestionNotFoundException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid question id.");
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createfeedbackTracking")
    public Response createFeedbackTracking(FeedbackTrackingRequest feedbackTrackingRequest) {
        FeedbackTrackingRequestBO feedbackTrackingRequestBO = new FeedbackTrackingRequestBO();
        feedbackTrackingRequestBO.setFeedbackId(feedbackTrackingRequest.getFeedbackId());
        MessageResponse createUserResponse = new MessageResponse();
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        try {
            Boolean isCreate = feedbackRequestHandler.createFeedbackTracking(feedbackTrackingRequestBO);
            if(isCreate) {
                return ResponseGenerator.generateSuccessResponse(createUserResponse, "Negative feedback url tracked.");
            }else{
                return ResponseGenerator.generateFailureResponse(createUserResponse, "Feedback tracking creation Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(createUserResponse, "Feedback tracking creation Failed");
        }
    }




    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/negativeReport")

    public Response getNegativeReport(FeedbackListRequest feedbackListRequest){
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
      FeedbackTrackingResponseList feedbackTrackingResponseList = new FeedbackTrackingResponseList();
        FeedbackListRequestBO feedbackListRequestBO = new FeedbackListRequestBO();
        try {
            feedbackListRequestBO.setFromDate(feedbackListRequest.getFromDate());
            feedbackListRequestBO.setToDate(feedbackListRequest.getToDate());
            feedbackListRequestBO.setOutletId(feedbackListRequest.getOutletId());
            feedbackListRequestBO.setTableNo(feedbackListRequest.getTableNo());
            feedbackListRequestBO.setUserId(feedbackListRequest.getUserId());
           feedbackTrackingResponseList.setFeedbackTrackingDetails(feedbackRequestHandler.getFeedbackTrackingList(feedbackListRequestBO));
            return ResponseGenerator.generateSuccessResponse(feedbackTrackingResponseList, "List of Negative feedbacks.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(feedbackTrackingResponseList, "Failed to retrieve the feedbacks.");
        }
    }



}

