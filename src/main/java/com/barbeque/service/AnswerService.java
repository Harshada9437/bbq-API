package com.barbeque.service;

import com.barbeque.request.answer.AnswerRequest;
import com.barbeque.request.bo.AnswerRequestBO;
import com.barbeque.requesthandler.AnswerRequestHandler;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.util.ResponseGenerator;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by System-2 on 12/15/2016.
 */
@Path("/answer")
public class AnswerService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createAnswer(AnswerRequest answerRequest)
    {
        AnswerRequestBO answerRequestBO = new AnswerRequestBO();
        answerRequestBO.setQuestionId(answerRequest.getQuestionId());
        answerRequestBO.setAnswerDesc(answerRequest.getAnswerDesc());
        answerRequestBO.setRating(answerRequest.getRating());

        MessageResponse messageResponse = new MessageResponse();
        AnswerRequestHandler answerRequestHandler = new AnswerRequestHandler();
        try
        {
            int answer = answerRequestHandler.createAnswer(answerRequestBO);
            if (answer > 0)
            {
                return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(answer));
            } else
            {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Answer Creation Failed");
            }
        } catch (SQLException sqlException) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Answer Creation Failed");
        }
    }
}
