package com.barbeque.requesthandler;

import com.barbeque.dao.answer.AnswerDAO;
import com.barbeque.dto.request.AnswerDTO;
import com.barbeque.request.bo.AnswerRequestBO;

import java.sql.SQLException;

/**
 * Created by System-2 on 12/15/2016.
 */
public class AnswerRequestHandler {
    public Integer createAnswer(AnswerRequestBO answerRequestBO) throws SQLException {
        AnswerDAO answerDAO = new AnswerDAO();
        int id = answerDAO.createAnswer(buildRequestDTOFromBO(answerRequestBO));
        return id;
    }

    private AnswerDTO buildRequestDTOFromBO(AnswerRequestBO answerRequestBO) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setQuestionId(answerRequestBO.getQuestionId());
        answerDTO.setAnswerDesc(answerRequestBO.getAnswerDesc());
        answerDTO.setRating(answerRequestBO.getRating());

        return  answerDTO;
    }
}


