package com.barbeque.requesthandler;

import com.barbeque.dao.FeedbackDAO;
import com.barbeque.dao.user.UsersDAO;
import com.barbeque.dto.request.CustomerReportDTO;
import com.barbeque.dto.request.AverageDTO;
import com.barbeque.dto.request.CountDTO;
import com.barbeque.dto.request.ReportDTO;
import com.barbeque.dto.response.LoginResponseDTO;
import com.barbeque.exceptions.CustomerNotFoundException;
import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.request.report.ReportData;
import com.barbeque.response.report.*;
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
        String to = DateUtil.getCurrentServerTimeByRemoteTimestamp(t1);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        Timestamp t2 = new Timestamp(date.getTime());
        String from = DateUtil.getCurrentServerTimeByRemoteTimestamp(t2);

        List<CountDTO> countDTOList = feedbackDAO.getcountById(id, from, to);

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
        String to = DateUtil.getCurrentServerTimeByRemoteTimestamp(t1);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        Timestamp t2 = new Timestamp(date.getTime());
        String from = DateUtil.getCurrentServerTimeByRemoteTimestamp(t2);


        List<AverageDTO> averageDTOList = feedbackDAO.getaverageById(id, from, to);

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
        String to = DateUtil.getCurrentServerTimeByRemoteTimestamp(t1);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        Timestamp t2 = new Timestamp(date.getTime());
        String from = DateUtil.getCurrentServerTimeByRemoteTimestamp(t2);

        CustomerReportResponseList customerReportResponseList = buildFeedbackCustomerFromDTO(feedbackDAO.getcustomerByPhoneNo(phoneNo, from, to));
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

    public SummaryResponse getReport(int userId) throws SQLException, UserNotFoundException {

        SummaryResponse summaryResponse = new SummaryResponse();
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 10);
        Date date1 = cal.getTime();
        Timestamp t1 = new Timestamp(date1.getTime());
        String currentDate = DateUtil.getCurrentServerTimeByRemoteTimestamp(t1);


        cal.setTime(date1);
        cal.add(Calendar.DATE, -1);
        Date date2 = cal.getTime();
        Timestamp t2 = new Timestamp(date2.getTime());
        String previousDate = DateUtil.getCurrentServerTimeByRemoteTimestamp(t2);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date3 = cal.getTime();
        Timestamp t3 = new Timestamp(date3.getTime());
        String previousMonth = DateUtil.getCurrentServerTimeByRemoteTimestamp(t3);

        LoginResponseDTO user = UsersDAO.getuserById(userId);
        String outlets = user.getOutletAccess();
        ReportDTO dailyReportDTO = feedbackDAO.getDailyReport(outlets, previousDate, currentDate);
        List<ReportData> dailyOutletReport = feedbackDAO.getOutletReport(outlets, previousDate, currentDate);
        dailyReportDTO.setOutlets(dailyOutletReport);
        ReportDTO monthlyReportDTO = feedbackDAO.getDailyReport(outlets, previousMonth, currentDate);
        List<ReportData> monthlyOutletReport = feedbackDAO.getOutletReport(outlets, previousMonth, currentDate);
        monthlyReportDTO.setOutlets(monthlyOutletReport);
        dailyReportDTO.setUserName(user.getName());

        summaryResponse.setMonthlyBillCount(monthlyReportDTO.getMonthlyBillCount());
        summaryResponse.setDailyBillCount(dailyReportDTO.getDailyBillCount());
        summaryResponse.setDailyFeedback(dailyReportDTO.getTotalCount());
        summaryResponse.setAvgDailyFeedback(calAverage(dailyReportDTO.getTotalCount(), dailyReportDTO.getDailyBillCount()));
        summaryResponse.setAvgMonthlyFeedback(calAverage(monthlyReportDTO.getTotalCount(), monthlyReportDTO.getMonthlyBillCount()));
        summaryResponse.setMonthlyFeedback(monthlyReportDTO.getTotalCount());
        summaryResponse.setDailyNegativeCount(dailyReportDTO.getNegativeCount());
        summaryResponse.setMonthlyNegativeCount(monthlyReportDTO.getNegativeCount());
        summaryResponse.setDailyUnAddressedCount(dailyReportDTO.getUnAddressedCount());
        summaryResponse.setMonthlyUnAddressedCount(monthlyReportDTO.getUnAddressedCount());
        summaryResponse.setAvgDailyUnaddressed(calAverage(dailyReportDTO.getUnAddressedCount(), dailyReportDTO.getNegativeCount()));
        summaryResponse.setAvgMonthlyUnaddressed(calAverage(monthlyReportDTO.getUnAddressedCount(), monthlyReportDTO.getNegativeCount()));

        List<OutletSummery> outletsdata = new ArrayList<OutletSummery>();
        for (int i = 0; i < monthlyReportDTO.getOutlets().size(); i++) {
            ReportData dailyData = dailyReportDTO.getOutlets().get(i);
            ReportData monthlyData = monthlyReportDTO.getOutlets().get(i);
            OutletSummery outletSummery = new OutletSummery();
            outletSummery.setCity(dailyData.getCity());
            outletSummery.setOutletName(dailyData.getStoreId());
            outletSummery.setMonthlyBillCount(monthlyData.getMonthlyBillCount());
            outletSummery.setDailyBillCount(dailyData.getDailyBillCount());
            outletSummery.setDailyFeedback(dailyData.getTotalCount());
            outletSummery.setMonthlyFeedback(monthlyData.getTotalCount());
            outletSummery.setAvgCount(calAverage(monthlyData.getTotalCount(), monthlyData.getMonthlyBillCount()));
            outletSummery.setDailyNegativeCount(dailyData.getNegativeCount());
            outletSummery.setMonthlyNegativeCount(monthlyData.getNegativeCount());
            outletSummery.setDailyUnAddressedCount(dailyData.getUnAddressedCount());
            outletSummery.setMonthlyUnAddressedCount(monthlyData.getUnAddressedCount());
            outletsdata.add(outletSummery);
        }
        summaryResponse.setOutlets(outletsdata);
        return summaryResponse;
    }

    public static float calAverage(int a, int b) {

        float result;
        if (b > 0) {
            result = (float) a / b;
            result = result * 100;
            result = Math.round(result * 100) / 100f;
        } else {
            result = 0;
        }
        return result;
    }
}



