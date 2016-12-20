package com.barbeque.service;

import com.barbeque.exceptions.AnswerNotFoundException;
import com.barbeque.request.answer.AnswerRequest;
import com.barbeque.request.bo.AnswerRequestBO;
import com.barbeque.requesthandler.AnswerRequestHandler;
import com.barbeque.response.Answer.AnswerResponse;
import com.barbeque.response.FailureResponse;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.util.ResponseGenerator;

import javax.ws.rs.*;
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

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/{question_id}")
    public Response getAnswerList(@PathParam("question_id") int questionId)throws Exception
    {
      AnswerRequestHandler answerRequestHandler=new AnswerRequestHandler();
      AnswerResponse answerResponse=new AnswerResponse();
        try {
            answerResponse.setAnswerResponseList(answerRequestHandler.getAnswer(questionId));
            return ResponseGenerator.generateSuccessResponse(answerResponse, "Answers are available");
        }
        catch (AnswerNotFoundException e) {
            FailureResponse failureResponse = new FailureResponse();
            return ResponseGenerator.generateFailureResponse(failureResponse, "INVALID Answers ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseGenerator.generateResponse(answerResponse);
    }
}
