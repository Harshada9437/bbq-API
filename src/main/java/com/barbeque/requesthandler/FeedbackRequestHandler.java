package com.barbeque.requesthandler;

import com.barbeque.dao.FeedbackDAO;
import com.barbeque.dto.request.FeedbackRequestDTO;
import com.barbeque.bo.FeedbackRequestBO;
import com.barbeque.bo.UpdateFeedbackRequestBO;
import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.response.feedback.FeedbackResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 10/18/2016.
 */
public class FeedbackRequestHandler {
    public Integer addFeedback(FeedbackRequestBO feedbackRequestBO, int customerId) throws SQLException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        int id = feedbackDAO.addFeedback(buildFeedbackRequestDTOFromBO(feedbackRequestBO), customerId);
        List<Integer> ansIds = assignFeedbacks(id,feedbackRequestBO.getFeedbacks());
        return id;
    }

    private FeedbackRequestDTO buildFeedbackRequestDTOFromBO(FeedbackRequestBO feedbackRequestBO) {
        FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();

        feedbackRequestDTO.setOutletId(feedbackRequestBO.getOutletId());
        feedbackRequestDTO.setFeedbacks(feedbackRequestBO.getFeedbacks());
        feedbackRequestDTO.setTableNo(feedbackRequestBO.getTableNo());
        feedbackRequestDTO.setBillNo(feedbackRequestBO.getBillNo());
        feedbackRequestDTO.setDate(feedbackRequestBO.getDate());

        return feedbackRequestDTO;
    }

    public List<Integer> assignFeedbacks(int id, List<FeedbackDetails> feedbackDetails) throws SQLException {

        int feedId = 0;
        Iterator<FeedbackDetails> feedbackDetailsIterator = feedbackDetails.iterator();
        List<Integer> feedIds = new ArrayList<Integer>();

        FeedbackDAO feedbackDAO = new FeedbackDAO();
        while (feedbackDetailsIterator.hasNext()) {
            FeedbackDetails feedbackDetailsObj = feedbackDetailsIterator.next();

            feedId = feedbackDAO.createFeedbackDetail(id, feedbackDetailsObj.getQuestionId(), feedbackDetailsObj.getAnswerId(), feedbackDetailsObj.getAnswerText(),  feedbackDetailsObj.getRating() );
            feedIds.add(feedId);
        }

        return feedIds;
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
        //feedbackRequestDTO.setFeedbacks(updateFeedbackRequestBO.getFeedbacks());
        feedbackRequestDTO.setDate(updateFeedbackRequestBO.getDate());
        feedbackRequestDTO.setTableNo(updateFeedbackRequestBO.getTableNo());
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
                        feedbackRequestDTO.getFeedbacks(),
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
