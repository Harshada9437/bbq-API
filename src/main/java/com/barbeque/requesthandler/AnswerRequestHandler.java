package com.barbeque.requesthandler;

import com.barbeque.dao.answer.AnswerDAO;
import com.barbeque.dto.request.AnswerDTO;
import com.barbeque.exceptions.AnswerNotFoundException;
import com.barbeque.request.bo.AnswerRequestBO;
import com.barbeque.response.Answer.AnswerResponseList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by System-2 on 12/15/2016.
 */
public class AnswerRequestHandler {
   /* public Integer createAnswer(AnswerRequestBO answerRequestBO) throws SQLException {
        AnswerDAO answerDAO = new AnswerDAO();
        int id = answerDAO.createAnswer(buildRequestDTOFromBO(answerRequestBO));
        return id;
    }*/

    /*private AnswerDTO buildRequestDTOFromBO(AnswerRequestBO answerRequestBO) {
        AnswerDTO answerDTO = new AnswerDTO()

        return  answerDTO;
    }*/

    public List<AnswerResponseList>getAnswer(int questionId)throws SQLException,AnswerNotFoundException
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
    public List<AnswerResponseList>getAnswerListDTOFromBO(List<AnswerDTO>answerDTOs)throws SQLException
    {
        List<AnswerResponseList> answerResponseLists=new ArrayList<AnswerResponseList>();
        Iterator<AnswerDTO>answerDTOIterator=answerDTOs.iterator();
        while (answerDTOIterator.hasNext())
        {
            AnswerDTO answerDTO=answerDTOIterator.next();
            AnswerResponseList answerResponseList=new AnswerResponseList(answerDTO.getQuestionId(),answerDTO.getAnswerDesc(),
                                                                     answerDTO.getRating(),answerDTO.getDescription(),answerDTO.getId());
            answerResponseLists.add(answerResponseList);
        }
        return answerResponseLists;
    }
}


