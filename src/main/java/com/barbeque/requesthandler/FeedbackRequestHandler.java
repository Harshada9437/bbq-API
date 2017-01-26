package com.barbeque.requesthandler;

import com.barbeque.bo.FeedbackListRequestBO;
import com.barbeque.bo.UpdateCustomerRequestBO;
import com.barbeque.dao.FeedbackDAO;
import com.barbeque.dao.customer.CustomerDAO;
import com.barbeque.dto.request.AnswerDTO;
import com.barbeque.dto.request.FeedbackListDTO;
import com.barbeque.dto.request.FeedbackRequestDTO;
import com.barbeque.bo.FeedbackRequestBO;
import com.barbeque.bo.UpdateFeedbackRequestBO;
import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.response.feedback.CreateCustomer;
import com.barbeque.response.feedback.FeedbackResponse;
import com.barbeque.util.DateUtil;
import com.barbeque.xlxFiles.ExcelCreator;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

/**
 * Created by user on 10/18/2016.
 */
public class FeedbackRequestHandler {
    public Integer addFeedback(FeedbackRequestBO feedbackRequestBO) throws SQLException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        int customerId = 0;

        String mobile = feedbackRequestBO.getCustomer().getPhoneNo();
        customerId = CustomerDAO.getValidationForPhoneNumber(mobile);
        if (customerId == 0) {
            customerId = createCustomer(feedbackRequestBO.getCustomer());
        } else {
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
        return id;
    }

    private FeedbackRequestDTO buildFeedbackRequestDTOFromBO(FeedbackRequestBO feedbackRequestBO) {
        FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();

        feedbackRequestDTO.setOutletId(feedbackRequestBO.getOutletId());
        feedbackRequestDTO.setDeviceId(feedbackRequestBO.getDeviceId());
        feedbackRequestDTO.setFeedbacks(feedbackRequestBO.getFeedbacks());
        feedbackRequestDTO.setTableNo(feedbackRequestBO.getTableNo());
        feedbackRequestDTO.setBillNo(feedbackRequestBO.getBillNo());
        feedbackRequestDTO.setDate(feedbackRequestBO.getDate());
        feedbackRequestDTO.setCustomer(feedbackRequestBO.getCustomer());

        return feedbackRequestDTO;
    }

    public int createCustomer(CreateCustomer createCustomer) throws SQLException {

        CustomerDAO customerDAO = new CustomerDAO();
        int id = customerDAO.addCustomer(createCustomer.getLocality(), createCustomer.getName(), createCustomer.getPhoneNo(), createCustomer.getEmailId(), createCustomer.getDob(), createCustomer.getDoa());
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

    public Boolean getfeedbackList(FeedbackListRequestBO feedbackListRequestBO) throws SQLException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        Boolean isCreated = Boolean.FALSE;
        List<FeedbackResponse> feedbackList = new ArrayList<FeedbackResponse>();

        List<FeedbackRequestDTO> feedbackRequestDTOS = feedbackDAO.getfeedbackList(buildFeedbackDTO(feedbackListRequestBO));

        for (FeedbackRequestDTO feedbackRequestDTO : feedbackRequestDTOS) {
            Timestamp r = feedbackRequestDTO.getCreatedOn();
            Calendar now = Calendar.getInstance();
            now.setTime(r);
            TimeZone timeZoneR = now.getTimeZone();
            TimeZone tz1 = TimeZone.getTimeZone("GMT");
            long timeDifference = tz1.getRawOffset() - timeZoneR.getRawOffset() + tz1.getDSTSavings() - timeZoneR.getDSTSavings();
            r.setTime(r.getTime() + timeDifference);
            Timestamp from = DateUtil.getTimeStampFromString(feedbackListRequestBO.getFromDate());
            Timestamp to = DateUtil.getTimeStampFromString(feedbackListRequestBO.getToDate());
            if (r.after(from) && r.before(to)) {
                String createDate = DateUtil.getDateStringFromTimeStamp(r);
                FeedbackResponse feedbackResponse = new FeedbackResponse(feedbackRequestDTO.getId(),
                        feedbackRequestDTO.getCustomerId(),
                        createDate,
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
        }
        if (feedbackList.size() > 0) {
            ExcelCreator.getExcelSheet(feedbackList);
            isCreated = Boolean.TRUE;
        }

        return isCreated;
    }

    private FeedbackListDTO buildFeedbackDTO(FeedbackListRequestBO feedbackListRequestBO) {
        FeedbackListDTO feedbackListDTO = new FeedbackListDTO();

        feedbackListDTO.setFromDate(feedbackListRequestBO.getFromDate());
        feedbackListDTO.setToDate(feedbackListRequestBO.getToDate());

        return feedbackListDTO;
    }
}
