package com.barbeque.requesthandler;

import com.barbeque.dao.answer.AnswerDAO;
import com.barbeque.dao.question.QuestionDAO;
import com.barbeque.dto.request.AnswerDTO;
import com.barbeque.dto.request.QuestionRequestDTO;
import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.bo.QuestionRequestBO;
import com.barbeque.bo.UpdateQueRequestBO;
import com.barbeque.request.question.OptionsList;
import com.barbeque.request.question.UpdateOptionsList;
import com.barbeque.response.Answer.AnswerResponseList;
import com.barbeque.response.question.GetQuestionResponse;
import com.barbeque.response.question.QuestionResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionRequestHandler {
    public Integer addQuestion(QuestionRequestBO questionRequestBO) throws SQLException {
        QuestionDAO questionDAO = new QuestionDAO();
        int id = questionDAO.addQuestion(buildRequestDTOFromBO(questionRequestBO));
        List<Integer> ansIds = getAssignAnswer(id, questionRequestBO.getAnswerOption());

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


    public List<QuestionResponse> getQuestionList() throws SQLException {
        QuestionDAO questionDAO = new QuestionDAO();
        List<QuestionResponse> questionList = new ArrayList<QuestionResponse>();

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

        return questionList;
    }

    public boolean updateQuestion(UpdateQueRequestBO updateQueRequestBO) throws SQLException, QuestionNotFoundException {

        QuestionDAO questionDAO = new QuestionDAO();

        Boolean isProcessed = questionDAO.updateQuestion(buildDTOFromBO(updateQueRequestBO));
        updateAnswer(updateQueRequestBO.getAnswerOption(), updateQueRequestBO.getId());
        return isProcessed;
    }

    private Boolean updateAnswer(List<UpdateOptionsList> answerOption, int queId) throws SQLException {
        Boolean isCreated = Boolean.FALSE;
        List<AnswerResponseList> savedList = getAnswer(queId);
        AnswerDAO answerDAO = new AnswerDAO();

        for (int i = 0; i < answerOption.size(); i++) {
            UpdateOptionsList optionItem = answerOption.get(i);
            if (!isAnswerInDB(optionItem.getAnswer_id(), savedList)) {
                answerDAO.createAnswer(queId, optionItem.getAnswerDesc(), optionItem.getRating(), optionItem.getWeightage(), optionItem.getThreshold());
            } else {
                answerDAO.updateAnswer(optionItem.getAnswer_id(), optionItem.getAnswerDesc(), optionItem.getRating(), optionItem.getWeightage(), optionItem.getThreshold());
            }
            isCreated = true;
        }

        //DELETE AFTER PROCESSING
        for (int j = 0; j < savedList.size(); j++) {
            AnswerResponseList savedItem = savedList.get(j);
            if (!isAnswerInPayload(savedItem.getAnswer_id(), answerOption)) {
                answerDAO.deleteAnswer(savedItem.getAnswer_id());
            }
        }

        return isCreated;
    }

    private boolean isAnswerInDB(int ansId, List<AnswerResponseList> ansList) {
        for (int i = 0; i < ansList.size(); i++) {
            if (ansList.get(i).getAnswer_id() == ansId) {
                return true;
            }
        }
        return false;
    }

    private boolean isAnswerInPayload(int ansId, List<UpdateOptionsList> ansList) {
        for (int i = 0; i < ansList.size(); i++) {
            if (ansList.get(i).getAnswer_id() == ansId) {
                return true;
            }
        }
        return false;
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

    public GetQuestionResponse getQuestionById(int id) throws SQLException, QuestionNotFoundException {
        QuestionDAO questionDAO = new QuestionDAO();
        GetQuestionResponse getQuestionResponse = buildQuestionInfoDTOFromBO(questionDAO.getQuestionById(id));
        return getQuestionResponse;
    }

    public GetQuestionResponse buildQuestionInfoDTOFromBO(QuestionRequestDTO questionRequestDTO) throws SQLException {
        GetQuestionResponse getQuestionResponse = new GetQuestionResponse();
        getQuestionResponse.setId(questionRequestDTO.getId());
        getQuestionResponse.setAnswerSymbol(questionRequestDTO.getAnswerSymbol());
        getQuestionResponse.setParentAnswerId(questionRequestDTO.getParentAnswerId());
        getQuestionResponse.setParentQuestionId(questionRequestDTO.getParentQuestionId());
        getQuestionResponse.setQuestionDesc(questionRequestDTO.getQuestionDesc());
        getQuestionResponse.setParentQuestionDesc(questionRequestDTO.getParentQuestionDesc());
        getQuestionResponse.setParentAnswerDesc(questionRequestDTO.getParentAnswerDesc());
        getQuestionResponse.setQuestionType(questionRequestDTO.getQuestionType());
        getQuestionResponse.setOptions(getAnswer(questionRequestDTO.getId()));

        return getQuestionResponse;

    }

    public List<Integer> getAssignAnswer(int id, List<OptionsList> answerOption) throws SQLException {

        int ansId = 0;
        Iterator<OptionsList> asnwerListIterator = answerOption.iterator();
        List<Integer> ansIds = new ArrayList<Integer>();

        AnswerDAO answerDAO = new AnswerDAO();
        while (asnwerListIterator.hasNext()) {
            OptionsList optionsList = new OptionsList();
            optionsList = asnwerListIterator.next();

            ansId = answerDAO.createAnswer(id, optionsList.getLabel(), optionsList.getRating(), optionsList.getWeightage(), optionsList.getThreshold());
            ansIds.add(ansId);
        }

        return ansIds;
    }

    public static List<AnswerResponseList> getAnswer(int questionId) throws SQLException {
        AnswerDAO answerDAO = new AnswerDAO();
        return getAnswerListDTOFromBO(answerDAO.getAnswer(questionId));
    }

    public static List<AnswerResponseList> getAnswerListDTOFromBO(List<AnswerDTO> answerDTOs) throws SQLException {
        List<AnswerResponseList> answerResponseLists = new ArrayList<AnswerResponseList>();
        for (AnswerDTO answerDTO : answerDTOs) {
            AnswerResponseList answerResponseList = new AnswerResponseList(answerDTO.getAnswerText(),
                    answerDTO.getRating(), answerDTO.getId(), answerDTO.getWeightage(), answerDTO.getThreshold());
            answerResponseLists.add(answerResponseList);
        }
        return answerResponseLists;
    }
}