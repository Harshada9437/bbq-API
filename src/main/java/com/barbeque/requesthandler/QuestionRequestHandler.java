package com.barbeque.requesthandler;

import com.barbeque.dao.question.QuestionDAO;
import com.barbeque.dto.request.QuestionRequestDTO;
import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.request.bo.QuestionRequestBO;
import com.barbeque.request.bo.UpdateQueRequestBO;
import com.barbeque.response.question.GetQuestionResponse;
import com.barbeque.response.question.QuestionResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionRequestHandler {
    public Integer addQuestion(QuestionRequestBO questionRequestBO) throws SQLException {
        QuestionDAO questionDAO = new QuestionDAO();
        int id = questionDAO.addQuestion(buildRequestDTOFromBO(questionRequestBO));
        return id;
    }

    private QuestionRequestDTO buildRequestDTOFromBO(QuestionRequestBO questionRequestBO) {
        QuestionRequestDTO questionRequestDTO = new QuestionRequestDTO();

        questionRequestDTO.setQuestionDesc(questionRequestBO.getQuestionDesc());
        questionRequestDTO.setQuestionType(questionRequestBO.getQuestionType());
        questionRequestDTO.setParentQuestionId(questionRequestBO.getParentQuestionId());
        questionRequestDTO.setParentAnswerId(questionRequestBO.getParentAnswerId());
        questionRequestDTO.setAnswerSymbol(questionRequestBO.getAnswerSymbol());


        return questionRequestDTO;
    }

    public List<QuestionResponse> getQuestionList() {
        QuestionDAO questionDAO = new QuestionDAO();
        List<QuestionResponse> questionList = new ArrayList<QuestionResponse>();
        try {
            List<QuestionRequestDTO> questionRequestDTOList = questionDAO.getAllQuestions();

            for (QuestionRequestDTO questionRequestDTO : questionRequestDTOList) {
                QuestionResponse questionResponse = new QuestionResponse();
                questionResponse.setId(questionRequestDTO.getId());
                questionResponse.setQuestionDesc(questionRequestDTO.getQuestionDesc());
                questionResponse.setParentAnswerDesc(questionRequestDTO.getParentAnswerDesc());
                questionResponse.setParentQuestionDesc(questionRequestDTO.getParentQuestionDesc());
                questionResponse.setQuestionType(questionRequestDTO.getQuestionType());
                questionResponse.setParentQuestionId(questionRequestDTO.getParentQuestionId());
                questionResponse.setParentAnswerId(questionRequestDTO.getParentAnswerId());
                questionResponse.setAnswerSymbol(questionRequestDTO.getAnswerSymbol());
                questionList.add(questionResponse);
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return questionList;
    }

    public boolean updateQuestion(UpdateQueRequestBO updateQueRequestBO) throws SQLException {
        Boolean isProcessed = Boolean.FALSE;
        QuestionDAO questionDAO = new QuestionDAO();
        try {
            isProcessed = questionDAO.updateQuestion(buildDTOFromBO(updateQueRequestBO));
        } catch (SQLException sq) {
            isProcessed = false;
        }
        return isProcessed;
    }

    private QuestionRequestDTO buildDTOFromBO(UpdateQueRequestBO updateQueRequestBO) {
        QuestionRequestDTO questionRequestDTO = new QuestionRequestDTO();
        questionRequestDTO.setId(updateQueRequestBO.getId());
        questionRequestDTO.setQuestionDesc(updateQueRequestBO.getQuestionDesc());
        questionRequestDTO.setQuestionType(updateQueRequestBO.getQuestionType());
        questionRequestDTO.setParentQuestionId(updateQueRequestBO.getParentQuestionId());
        questionRequestDTO.setParentAnswerId(updateQueRequestBO.getParentAnswerId());
        questionRequestDTO.setAnswerSymbol(updateQueRequestBO.getAnswerSymbol());
        return questionRequestDTO;
    }

    public GetQuestionResponse getQuestionById(int id) throws SQLException, QuestionNotFoundException
    {
        QuestionDAO questionDAO=new QuestionDAO();
        GetQuestionResponse getQuestionResponse=new GetQuestionResponse();
         try
         {
             getQuestionResponse=buildQuestionInfoDTOFromBO(questionDAO.getQuestionById(id));
         }catch (SQLException e){
             e.printStackTrace();
         }
         return getQuestionResponse;
    }

    public GetQuestionResponse buildQuestionInfoDTOFromBO (QuestionRequestDTO questionRequestDTO)
    {
        GetQuestionResponse getQuestionResponse=new GetQuestionResponse();
        getQuestionResponse.setId(questionRequestDTO.getId());
        getQuestionResponse.setAnswerSymbol(questionRequestDTO.getAnswerSymbol());
        getQuestionResponse.setParentAnswerId(questionRequestDTO.getParentAnswerId());
        getQuestionResponse.setParentQuestionId(questionRequestDTO.getParentQuestionId());
        getQuestionResponse.setQuestionDesc(questionRequestDTO.getQuestionDesc());
        getQuestionResponse.setParentQuestionDesc(questionRequestDTO.getParentQuestionDesc());
        getQuestionResponse.setParentAnswerDesc(questionRequestDTO.getParentAnswerDesc());
        getQuestionResponse.setQuestionType(questionRequestDTO.getQuestionType());

        return getQuestionResponse;

    }


}
