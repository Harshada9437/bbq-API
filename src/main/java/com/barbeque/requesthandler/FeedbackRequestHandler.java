package com.barbeque.requesthandler;

import com.barbeque.bo.UpdateCustomerRequestBO;
import com.barbeque.dao.FeedbackDAO;
import com.barbeque.dao.customer.CustomerDAO;
import com.barbeque.dto.request.AnswerDTO;
import com.barbeque.dto.request.FeedbackRequestDTO;
import com.barbeque.bo.FeedbackRequestBO;
import com.barbeque.bo.UpdateFeedbackRequestBO;
import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.response.feedback.CreateCustomer;
import com.barbeque.response.feedback.FeedbackResponse;
import com.barbeque.xlxFiles.ExcelCreator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 10/18/2016.
 */
public class FeedbackRequestHandler {
    public Integer addFeedback(FeedbackRequestBO feedbackRequestBO) throws SQLException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        int customerId = 0;



        String mobile=feedbackRequestBO.getCustomer().getPhoneNo();
        customerId = CustomerDAO.getValidationForPhoneNumber(mobile);
        if (customerId == 0)
        {
            customerId = createCustomer(feedbackRequestBO.getCustomer());
        }
        else {
            UpdateCustomerRequestBO updateCustomerRequestBO = new UpdateCustomerRequestBO();
            updateCustomerRequestBO.setId(customerId);
            updateCustomerRequestBO.setName(feedbackRequestBO.getCustomer().getName());
            updateCustomerRequestBO.setLocality(feedbackRequestBO.getCustomer().getLocality());
            updateCustomerRequestBO.setPhoneNo(feedbackRequestBO.getCustomer().getPhoneNo());
            updateCustomerRequestBO.setEmailId(feedbackRequestBO.getCustomer().getEmailId());
            updateCustomerRequestBO.setDob(feedbackRequestBO.getCustomer().getDob());
            updateCustomerRequestBO.setDoa(feedbackRequestBO.getCustomer().getDoa());
            CustomerRequestHandler customerRequestHandler = new CustomerRequestHandler();
            customerRequestHandler.updateCustomer(updateCustomerRequestBO);
        }

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
        feedbackRequestDTO.setCustomer(feedbackRequestBO.getCustomer());

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
    public int createCustomer(CreateCustomer createCustomer)throws SQLException
    {

        CustomerDAO customerDAO=new CustomerDAO();
        int id = customerDAO.addCustomer(createCustomer.getLocality(),createCustomer.getName(),createCustomer.getPhoneNo(),createCustomer.getEmailId(),createCustomer.getDob(),createCustomer.getDoa());
        return id;

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
        feedbackRequestDTO.setDate(updateFeedbackRequestBO.getDate());
        feedbackRequestDTO.setTableNo(updateFeedbackRequestBO.getTableNo());
        feedbackRequestDTO.setBillNo(updateFeedbackRequestBO.getBillNo());
        feedbackRequestDTO.setModifiedOn(updateFeedbackRequestBO.getAnswerText());

        return feedbackRequestDTO;
    }

    public Boolean getfeedbackList() {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        Boolean isCreated = Boolean.FALSE;
        List<FeedbackResponse> feedbackList = new ArrayList<FeedbackResponse>();
        try {
            List<FeedbackRequestDTO> feedbackRequestDTOS = feedbackDAO.getfeedbackList();

            for (FeedbackRequestDTO feedbackRequestDTO : feedbackRequestDTOS) {
                FeedbackResponse feedbackResponse = new FeedbackResponse(feedbackRequestDTO.getId(),
                        feedbackRequestDTO.getCustomerId(),
                        feedbackRequestDTO.getCreatedOn(),
                        feedbackRequestDTO.getModifiedOn(),
                        feedbackRequestDTO.getOutletId(),
                        feedbackRequestDTO.getDate(),
                        feedbackRequestDTO.getAnswerDesc(),
                        feedbackRequestDTO.getAnswerText(),
                        feedbackRequestDTO.getQuestionDesc(),
                        feedbackRequestDTO.getRating(),
                        feedbackRequestDTO.getAnswerId(),
                        feedbackRequestDTO.getQuestionId(),
                        feedbackRequestDTO.getTableNo(),
                        feedbackRequestDTO.getBillNo(),
                        feedbackRequestDTO.getCustomerName(),
                        feedbackRequestDTO.getOutletDesc(),
                        feedbackRequestDTO.getMobileNo());

                feedbackList.add(feedbackResponse);
            }
            ExcelCreator.getExcelSheet(feedbackList);
            isCreated = Boolean.TRUE;
        } catch (SQLException sq) {
            sq.printStackTrace();
        }

        return isCreated;
    }

    public List<FeedbackDetails> getfeedback() throws SQLException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        List<FeedbackDetails> feedbackList = getFeedbackResponseListFromDTO(feedbackDAO.getfeedback());

        return feedbackList;
    }
    public static List<FeedbackDetails>getFeedbackResponseListFromDTO(List<AnswerDTO> answerDTOs)throws SQLException {
        List<FeedbackDetails> feedbackDetailss = new ArrayList<FeedbackDetails>();
        Iterator<AnswerDTO> feedbackRequestDTOIterator = answerDTOs.iterator();
        while (feedbackRequestDTOIterator.hasNext())
        {
            AnswerDTO answerDTO=feedbackRequestDTOIterator.next();
            FeedbackDetails feedbackResponse=new FeedbackDetails();
            feedbackResponse.setQuestionId(answerDTO.getQuestionId());
            feedbackResponse.setAnswerId(answerDTO.getId());
            feedbackResponse.setAnswerText(answerDTO.getAnswerText());
            feedbackResponse.setAnswerDesc(answerDTO.getAnswerDesc());
            feedbackResponse.setQuestionDesc(answerDTO.getQuestionDesc());
            feedbackResponse.setRating(answerDTO.getRating());
            feedbackDetailss.add(feedbackResponse);
        }
        return  feedbackDetailss;
    }
}
