package com.barbeque.service;

import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.request.bo.UpdateQueRequestBO;
import com.barbeque.request.question.QuestionRequest;
import com.barbeque.request.bo.QuestionRequestBO;
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
    public Response addQuestion(QuestionRequest questionRequest) throws SQLException {
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
    public Response getQuestionList() throws Exception {

        QuestionRequestHandler questionRequestHandler = new QuestionRequestHandler();
        QuestionResponseList subjectResponseList = new QuestionResponseList();
        subjectResponseList.setQuestions(questionRequestHandler.getQuestionList());
        return ResponseGenerator.generateSuccessResponse(subjectResponseList, "List of questions.");
    }

    @POST
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
        updateQueRequestBO.setAnswerOption(updateQueRequest.getAnswerOption());


        QuestionRequestHandler questionRequestHandler = new QuestionRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        if (questionRequestHandler.updateQuestion(updateQueRequestBO)) {
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Question updated successfully");
        } else {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the question.");
        }
    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/questionInfo/{id}")
    public Response getQuestionList(@PathParam("id")int id)throws Exception
    {
        QuestionRequestHandler questionRequestHandler=new QuestionRequestHandler();
        Object response = null;
        try{
            GetQuestionResponse questionResponse=questionRequestHandler.getQuestionById(id);
            return ResponseGenerator.generateSuccessResponse(questionResponse, "SUCCESS");
        }catch (QuestionNotFoundException e) {
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID QuestionId ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseGenerator.generateResponse(response);
    }



}
