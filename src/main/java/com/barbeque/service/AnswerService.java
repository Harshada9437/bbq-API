package com.barbeque.service;

import com.barbeque.requesthandler.AnswerRequestHandler;
import com.barbeque.response.Answer.AnswerResponse;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.util.ResponseGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/answer")
public class AnswerService {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/{question_id}")
    public Response getAnswerList(@PathParam("question_id") int questionId){
        AnswerRequestHandler answerRequestHandler = new AnswerRequestHandler();
        AnswerResponse answerResponse = new AnswerResponse();
        MessageResponse messageResponse = new MessageResponse();
        try {
            answerResponse.setAnswerResponseList(answerRequestHandler.getAnswer(questionId));
            return ResponseGenerator.generateSuccessResponse(answerResponse, "Answers are available");
        }  catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "No answers are assigned. ");
        }
    }
}