package com.barbeque.requesthandler;

import com.barbeque.dao.FeedbackDAO;
import com.barbeque.dto.request.CustomerReportDTO;
import com.barbeque.dto.request.AverageDTO;
import com.barbeque.dto.request.CountDTO;
import com.barbeque.exceptions.CustomerNotFoundException;
import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.response.report.AverageResponse;
import com.barbeque.response.report.CountResponse;
import com.barbeque.response.report.CustomerReportResponseList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-3 on 2/13/2017.
 */
public class ReportRequestHandler {

    public List<CountResponse> getcountById(int id) throws SQLException, QuestionNotFoundException {
        List<CountResponse> countList = new ArrayList<CountResponse>();
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<CountDTO> countDTOList = feedbackDAO.getcountById(id);

        for (com.barbeque.dto.request.CountDTO countDTO : countDTOList) {
            CountResponse countResponse = new CountResponse();
            countResponse.setQuestionDesc(countDTO.getQuestionDesc());
            countResponse.setAnswerDesc(countDTO.getAnswerDesc());
            countResponse.setQuestionType(countDTO.getQuestionType());
            countResponse.setCount(countDTO.getCount());

            countList.add(countResponse);
        }

        return countList;
    }


    public List<AverageResponse> getaverageById(int id) throws SQLException, QuestionNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<AverageResponse> averageList = new ArrayList<AverageResponse>();

        List<AverageDTO> averageDTOList = feedbackDAO
                .getaverageById(id);

        for (com.barbeque.dto.request.AverageDTO averageDTO : averageDTOList) {
            AverageResponse averageResponse = new AverageResponse();
            averageResponse.setQuestionDesc(averageDTO.getQuestionDesc());
            averageResponse.setAnswerDesc(averageDTO.getAnswerDesc());
            averageResponse.setAverage(averageDTO.getAverage());

            averageList.add(averageResponse);
        }

        return averageList;
    }


    public CustomerReportResponseList getcustomerByPhoneNo(String phoneNo) throws SQLException, CustomerNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        CustomerReportResponseList customerReportResponseList = buildFeedbackCustomerFromDTO(feedbackDAO.getcustomerByPhoneNo(phoneNo));
        return customerReportResponseList;
    }

    public CustomerReportResponseList buildFeedbackCustomerFromDTO(CustomerReportDTO customerReportDTO) throws SQLException, CustomerNotFoundException {
        CustomerReportResponseList customerReportResponseList = new CustomerReportResponseList();
        customerReportResponseList.setId(customerReportDTO.getId());
        customerReportResponseList.setName(customerReportDTO.getName());
        customerReportResponseList.setEmailId(customerReportDTO.getEmailId());
        customerReportResponseList.setDob(customerReportDTO.getDob());
        customerReportResponseList.setDoa(customerReportDTO.getDoa());
        customerReportResponseList.setLocality(customerReportDTO.getLocality());
        customerReportResponseList.setFeedback(FeedbackDAO.getcustomerFeedback(customerReportResponseList.getId()));
        return customerReportResponseList;
    }

}



