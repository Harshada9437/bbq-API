package com.barbeque.requesthandler;

import com.barbeque.dao.FeedbackDAO;
import com.barbeque.dao.question.QuestionDAO;
import com.barbeque.dto.request.FeedbackRequestDTO;
import com.barbeque.request.bo.FeedbackRequestBO;
import com.barbeque.request.bo.UpdateFeedbackRequestBO;
import com.barbeque.response.feedback.FeedbackResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Boolean updateFeedback(UpdateFeedbackRequestBO updateFeedbackRequestBO) throws SQLException {
        Boolean isProcessed = Boolean.FALSE;
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        try {
            isProcessed = feedbackDAO.updateQuestion(buildDTOFromBO(updateFeedbackRequestBO));
        } catch (SQLException sq) {
            isProcessed = false;
        }
        return isProcessed;
    }

    private FeedbackRequestDTO buildDTOFromBO(UpdateFeedbackRequestBO updateFeedbackRequestBO) {
        FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();
        feedbackRequestDTO.setId(updateFeedbackRequestBO.getId());
        feedbackRequestDTO.setQuestionId(updateFeedbackRequestBO.getQuestionId());
        feedbackRequestDTO.setAnswerId(updateFeedbackRequestBO.getAnswerId());
        feedbackRequestDTO.setAnswerText(updateFeedbackRequestBO.getAnswerText());
        feedbackRequestDTO.setDate(updateFeedbackRequestBO.getDate());
        feedbackRequestDTO.setTableNo(updateFeedbackRequestBO.getTableNo());
        feedbackRequestDTO.setRating(updateFeedbackRequestBO.getRating());
        feedbackRequestDTO.setBillNo(updateFeedbackRequestBO.getBillNo());
        feedbackRequestDTO.setModifiedOn(updateFeedbackRequestBO.getAnswerText());

        return feedbackRequestDTO;
    }

    public List<FeedbackResponse> getfeedbackList() {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<FeedbackResponse> feedbackList = new ArrayList<FeedbackResponse>();
        try {
            List<FeedbackRequestDTO> questionRequestDTOList = feedbackDAO.getfeedbackList();

            for (FeedbackRequestDTO feedbackRequestDTO : questionRequestDTOList) {
                FeedbackResponse feedbackResponse = new FeedbackResponse(feedbackRequestDTO.getId(),
                        feedbackRequestDTO.getOutletId(),
                        feedbackRequestDTO.getDate(),
                        feedbackRequestDTO.getQuestionId(),
                        feedbackRequestDTO.getAnswerId(),
                        feedbackRequestDTO.getAnswerText(),
                        feedbackRequestDTO.getRating(),
                        feedbackRequestDTO.getTableNo(),
                        feedbackRequestDTO.getBillNo(),
                        feedbackRequestDTO.getCreatedOn(),
                        feedbackRequestDTO.getModifiedOn());

                feedbackList.add(feedbackResponse);
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return feedbackList;
    }
}
