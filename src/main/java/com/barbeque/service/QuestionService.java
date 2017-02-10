package com.barbeque.service;

import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.bo.UpdateQueRequestBO;
import com.barbeque.request.question.QuestionRequest;
import com.barbeque.bo.QuestionRequestBO;
import com.barbeque.request.question.UpdateQueRequest;
import com.barbeque.requesthandler.QuestionRequestHandler;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.question.GetQuestionResponse;
import com.barbeque.response.question.QuestionResponseList;
import com.barbeque.response.util.ResponseGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/question")
public class QuestionService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response addQuestion(QuestionRequest questionRequest) {
        QuestionRequestBO questionRequestBO = new QuestionRequestBO();
        questionRequestBO.setQuestionDesc(questionRequest.getQuestionDesc());
        questionRequestBO.setQuestionType(questionRequest.getQuestionType());
        questionRequestBO.setParentQuestionId(questionRequest.getParentQuestionId());
        questionRequestBO.setParentAnswerId(questionRequest.getParentAnswerId());
        questionRequestBO.setAnswerSymbol(questionRequest.getAnswerSymbol());
        questionRequestBO.setAnswerOption(questionRequest.getAnswerOption());

        MessageResponse messageResponse = new MessageResponse();
        QuestionRequestHandler questionRequestHandler = new QuestionRequestHandler();
        try {
            int questionId = questionRequestHandler.addQuestion(questionRequestBO);
            if (questionId > 0) {

                return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(questionId));
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Question creation failed.");
            }
        } catch (SQLException sqlException) {
            MessageResponse MessageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(MessageResponse, "Question creation failed.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getQuestionList() {
        QuestionRequestHandler questionRequestHandler = new QuestionRequestHandler();
        QuestionResponseList questionResponseList = new QuestionResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            questionResponseList.setQuestions(questionRequestHandler.getQuestionList());
            return ResponseGenerator.generateSuccessResponse(questionResponseList, "List of questions.");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateQuestion(UpdateQueRequest updateQueRequest) {

        UpdateQueRequestBO updateQueRequestBO = new UpdateQueRequestBO();
        updateQueRequestBO.setId(updateQueRequest.getId());
        updateQueRequestBO.setQuestionDesc(updateQueRequest.getQuestionDesc());
        updateQueRequestBO.setQuestionType(updateQueRequest.getQuestionType());
        updateQueRequestBO.setParentQuestionId(updateQueRequest.getParentQuestionId());
        updateQueRequestBO.setParentAnswerId(updateQueRequest.getParentAnswerId());
        updateQueRequestBO.setAnswerSymbol(updateQueRequest.getAnswerSymbol());
        updateQueRequestBO.setAnswerOption(updateQueRequest.getAnswerOption());

        QuestionRequestHandler questionRequestHandler = new QuestionRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (questionRequestHandler.updateQuestion(updateQueRequestBO)) {
                return ResponseGenerator.generateSuccessResponse(messageResponse, "Question updated successfully");
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the question.");
            }
        } catch (QuestionNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid question id");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "failed to update.");
        }
    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/questionInfo/{id}")
    public Response getQuestionList(@PathParam("id")int id)
    {
        QuestionRequestHandler questionRequestHandler=new QuestionRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try{
            GetQuestionResponse questionResponse=questionRequestHandler.getQuestionById(id);
            return ResponseGenerator.generateSuccessResponse(questionResponse, "SUCCESS");
        }catch (QuestionNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID QuestionId ");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "failed to retrieve question details. ");
        }
    }
}
