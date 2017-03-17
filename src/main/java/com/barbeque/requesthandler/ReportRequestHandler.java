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
import com.barbeque.util.DateUtil;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by System-3 on 2/13/2017.
 */
public class ReportRequestHandler {

    public List<CountResponse> getcountById(int id) throws SQLException, QuestionNotFoundException {
        List<CountResponse> countList = new ArrayList<CountResponse>();
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        Date date1 = new Date();
        Timestamp t1 = new Timestamp(date1.getTime());
        String to = DateUtil.getDateStringFromTimeStamp(t1);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        Timestamp t2 = new Timestamp(date.getTime());
        String from = DateUtil.getDateStringFromTimeStamp(t2);

        List<CountDTO> countDTOList = feedbackDAO.getcountById(id,from,to);

        for (com.barbeque.dto.request.CountDTO countDTO : countDTOList) {
            CountResponse countResponse = new CountResponse();
            countResponse.setQuestionDesc(countDTO.getQuestionDesc());
            countResponse.setAnswerDesc(countDTO.getAnswerDesc());
            countResponse.setQuestionType(countDTO.getQuestionType());
            countResponse.setCount(countDTO.getCount());
            countResponse.setRating(countDTO.getRating());
            countList.add(countResponse);
        }

        return countList;
    }


    public List<AverageResponse> getaverageById(int id) throws SQLException, QuestionNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<AverageResponse> averageList = new ArrayList<AverageResponse>();

        Date date1 = new Date();
        Timestamp t1 = new Timestamp(date1.getTime());
        String to = DateUtil.getDateStringFromTimeStamp(t1);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        Timestamp t2 = new Timestamp(date.getTime());
        String from = DateUtil.getDateStringFromTimeStamp(t2);


        List<AverageDTO> averageDTOList = feedbackDAO.getaverageById(id,from,to);

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

        Date date1 = new Date();
        Timestamp t1 = new Timestamp(date1.getTime());
        String to = DateUtil.getDateStringFromTimeStamp(t1);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        Timestamp t2 = new Timestamp(date.getTime());
        String from = DateUtil.getDateStringFromTimeStamp(t2);

        CustomerReportResponseList customerReportResponseList = buildFeedbackCustomerFromDTO(feedbackDAO.getcustomerByPhoneNo(phoneNo,from,to));
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



