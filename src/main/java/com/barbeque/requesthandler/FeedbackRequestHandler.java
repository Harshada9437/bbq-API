package com.barbeque.requesthandler;

import com.barbeque.dao.FeedbackDAO;
import com.barbeque.dto.request.FeedbackRequestDTO;
import com.barbeque.request.bo.FeedbackRequestBO;

import java.sql.SQLException;

/**
 * Created by user on 10/18/2016.
 */
public class FeedbackRequestHandler {
    public Integer addFeedback(FeedbackRequestBO feedbackRequestBO, int customerId) throws SQLException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        int id= feedbackDAO.addFeedback(buildFeedbackRequestDTOFromBO(feedbackRequestBO),customerId);
        return id;
    }

    private FeedbackRequestDTO buildFeedbackRequestDTOFromBO(FeedbackRequestBO feedbackRequestBO) {
        FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();

        feedbackRequestDTO.setOutletId(feedbackRequestBO.getOutletId());
        feedbackRequestDTO.setQuestionId(feedbackRequestBO.getQuestionId());
        feedbackRequestDTO.setAnswerId(feedbackRequestBO.getAnswerId());
        feedbackRequestDTO.setAnswerText(feedbackRequestBO.getAnswerText());
        feedbackRequestDTO.setTableNo(feedbackRequestBO.getTableNo());
        feedbackRequestDTO.setBillNo(feedbackRequestBO.getBillNo());
        feedbackRequestDTO.setRating(feedbackRequestBO.getRating());
        feedbackRequestDTO.setDate(feedbackRequestBO.getDate());

        return feedbackRequestDTO;
    }
}
