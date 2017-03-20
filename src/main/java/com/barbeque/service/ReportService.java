package com.barbeque.service;

/**
 * Created by System-3 on 2/13/2017.
 */

import com.barbeque.exceptions.CustomerNotFoundException;

import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.request.report.BillData;
import com.barbeque.requesthandler.FeedbackRequestHandler;
import com.barbeque.requesthandler.ReportRequestHandler;
import com.barbeque.response.report.AverageResponseList;
import com.barbeque.response.report.CountResponseList;
import com.barbeque.response.report.CustomerReportResponseList;
import com.barbeque.response.report.SummaryResponse;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.util.ResponseGenerator;
import com.barbeque.sync.Synchronize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;


@Path("/report")
public class ReportService {
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/questionRating/count/{id}")
    public Response getcountById(@PathParam("id") int id) throws Exception {
        ReportRequestHandler reportRequestHandler = new ReportRequestHandler();
        CountResponseList countResponseList = new CountResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            countResponseList.setCount(reportRequestHandler.getcountById(id));
            return ResponseGenerator.generateSuccessResponse(countResponseList, "List of counts.");
        } catch (QuestionNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID Question Id ");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failure.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/questionRating/average/{id}")

    public Response getaverageById(@PathParam("id") int id) throws Exception {
        ReportRequestHandler reportRequestHandler = new ReportRequestHandler();
        AverageResponseList averageResponseList = new AverageResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            averageResponseList.setAverage(reportRequestHandler.getaverageById(id));
            return ResponseGenerator.generateSuccessResponse(averageResponseList, "List of average rating.");
        } catch (QuestionNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID Question Id ");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failure.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/customer/{phoneNo}")

    public Response getcustomerByPhoneNo(@PathParam("phoneNo") String phoneNo) throws Exception {
        ReportRequestHandler reportRequestHandler = new ReportRequestHandler();
        CustomerReportResponseList customerReportResponseList = new CustomerReportResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            customerReportResponseList = reportRequestHandler.getcustomerByPhoneNo(phoneNo);
            return ResponseGenerator.generateSuccessResponse(customerReportResponseList, "Personal Details.");
        } catch (CustomerNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID Phone No. ");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failure.");
        }
    }

    @GET
    @Path("/dailyReport/{user_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getDailyReport(@PathParam("user_id") int userId) {
        ReportRequestHandler reportRequestHandler = new ReportRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            SummaryResponse isProcessed = reportRequestHandler.getReport(userId);
            return ResponseGenerator.generateSuccessResponse(isProcessed, "Mails are sent to all users.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve. ");
        }
    }
}





