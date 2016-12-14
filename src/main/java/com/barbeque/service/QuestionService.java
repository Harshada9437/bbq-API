package com.barbeque.service;

import com.barbeque.request.bo.UpdateQueRequestBO;
import com.barbeque.request.question.QuestionRequest;
import com.barbeque.request.bo.QuestionRequestBO;
import com.barbeque.request.question.UpdateQueRequest;
import com.barbeque.requesthandler.QuestionRequestHandler;
import com.barbeque.response.util.SuccessResponse;
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

        SuccessResponse successResponse = new SuccessResponse();
        QuestionRequestHandler questionRequestHandler = new QuestionRequestHandler();
        try {
            int questionId = questionRequestHandler.addQuestion(questionRequestBO);
            if (questionId > 0) {
                return ResponseGenerator.generateSuccessResponse(successResponse, String.valueOf(questionId));
            } else {
                return ResponseGenerator.generateSuccessResponse(successResponse, "Question creation failed.");
            }
        } catch (SQLException sqlException) {
            SuccessResponse SuccessResponse = new SuccessResponse();
            return ResponseGenerator.generateFailureResponse(SuccessResponse, "Question creation failed.");
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

        QuestionRequestHandler questionRequestHandler = new QuestionRequestHandler();
        SuccessResponse successResponse = new SuccessResponse();
        if (questionRequestHandler.updateQuestion(updateQueRequestBO)) {
            return ResponseGenerator.generateSuccessResponse(successResponse, "Question updated successfully");
        } else {
            return ResponseGenerator.generateFailureResponse(successResponse, "Unable to update the question.");
        }
    }
}
