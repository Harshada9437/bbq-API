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
        List<Integer> ansIds = getAssignAnswer(id,questionRequestBO.getAnswerOption());

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

    public boolean updateQuestion(UpdateQueRequestBO updateQueRequestBO) throws SQLException, QuestionNotFoundException {
        Boolean isProcessed = Boolean.FALSE;
        QuestionDAO questionDAO = new QuestionDAO();
        try {
            isProcessed = questionDAO.updateQuestion(buildDTOFromBO(updateQueRequestBO));
            updateAnswer(updateQueRequestBO.getAnswerOption(),updateQueRequestBO.getId());
        } catch (SQLException sq) {
            isProcessed = false;
        }
        return isProcessed;
    }

    private Boolean updateAnswer(List<UpdateOptionsList> answerOption,int queId) throws SQLException, QuestionNotFoundException {
        Boolean isCreated = Boolean.FALSE;
        Iterator<AnswerResponseList> storedAnsList = getAnswer(queId).iterator();

        AnswerDAO answerDAO=new AnswerDAO();

        while(storedAnsList.hasNext()){
            boolean isExist = false;
            AnswerResponseList prevPptionList = storedAnsList.next();
            for(int i = 0; i<answerOption.size();i++){
                UpdateOptionsList optionsList = answerOption.get(i);
                if(prevPptionList.getAnswer_id() == optionsList.getAnswer_id()){
                    isExist = true;
                    answerDAO.updateAnswer(optionsList.getAnswer_id(),optionsList.getAnswerDesc(),optionsList.getRating());
                    break;
                }
            }
            if(!isExist){
                answerDAO.deleteAnswer(prevPptionList.getAnswer_id());
            }
            isCreated=true;
        }
        return isCreated;
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
        GetQuestionResponse getQuestionResponse = new GetQuestionResponse();
        try {
            getQuestionResponse = buildQuestionInfoDTOFromBO(questionDAO.getQuestionById(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getQuestionResponse;
    }

    public GetQuestionResponse buildQuestionInfoDTOFromBO(QuestionRequestDTO questionRequestDTO) throws SQLException, QuestionNotFoundException {
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

    public List<Integer> getAssignAnswer(int id, List<OptionsList>answerOption) throws SQLException {

        int ansId = 0;
        Iterator<OptionsList> asnwerListIterator = answerOption.iterator();
        List<Integer> ansIds = new ArrayList<Integer>();

        AnswerDAO answerDAO=new AnswerDAO();
        while (asnwerListIterator.hasNext())
        {
            OptionsList optionsList=new OptionsList();
            optionsList=asnwerListIterator.next();

            ansId = answerDAO.createAnswer(id,optionsList.getLabel(),optionsList.getRating());
            ansIds.add(ansId);
        }

        return ansIds;
    }

    public static List<AnswerResponseList>getAnswer(int questionId)throws SQLException,QuestionNotFoundException
    {
        AnswerDAO answerDAO=new AnswerDAO();
        List<AnswerResponseList>answerResponseLists=new ArrayList<AnswerResponseList>();
        try{
            answerResponseLists=getAnswerListDTOFromBO(answerDAO.getAnswer(questionId));
        }catch (SQLException s) {
            s.printStackTrace();
        }
        return answerResponseLists;
    }
    public static List<AnswerResponseList>getAnswerListDTOFromBO(List<AnswerDTO>answerDTOs)throws SQLException
    {
        List<AnswerResponseList> answerResponseLists=new ArrayList<AnswerResponseList>();
        Iterator<AnswerDTO>answerDTOIterator=answerDTOs.iterator();
        while (answerDTOIterator.hasNext())
        {
            AnswerDTO answerDTO=answerDTOIterator.next();
            AnswerResponseList answerResponseList=new AnswerResponseList(answerDTO.getAnswerDesc(),
                    answerDTO.getRating(),answerDTO.getId());
            answerResponseLists.add(answerResponseList);
        }
        return answerResponseLists;
    }
}