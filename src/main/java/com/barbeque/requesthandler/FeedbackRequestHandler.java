package com.barbeque.requesthandler;

import com.barbeque.bo.*;
import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dao.FeedbackDAO;
import com.barbeque.dao.Sync.SmsDAO;
import com.barbeque.dao.Sync.SyncDAO;
import com.barbeque.dao.answer.AnswerDAO;
import com.barbeque.dao.customer.CustomerDAO;
import com.barbeque.dao.outlet.OutletDAO;
import com.barbeque.dao.question.QuestionDAO;
import com.barbeque.dao.user.UsersDAO;
import com.barbeque.dto.request.*;
import com.barbeque.dto.response.LoginResponseDTO;
import com.barbeque.exceptions.FeedbackNotFoundException;
import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.request.report.BillData;
import com.barbeque.request.report.ReportData;
import com.barbeque.response.feedback.CreateCustomer;
import com.barbeque.response.feedback.FeedbackByIdResponse;
import com.barbeque.response.feedback.FeedbackResponse;
import com.barbeque.response.feedback.FeedbackTrackingResponse;

import java.io.FileWriter;
import java.sql.Connection;
import java.util.Date;

import com.barbeque.util.*;
import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.codehaus.jettison.json.JSONObject;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by user on 10/18/2016.
 */
public class FeedbackRequestHandler {

    public Integer addFeedback(FeedbackRequestBO feedbackRequestBO) throws SQLException, QuestionNotFoundException, FeedbackNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        int customerId;

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
        List<FeedbackDetails> feedbacks = feedbackDAO.getfeedback(id);
        Boolean isExist = Boolean.FALSE;
        UpdateSettingsDTO setting = OutletDAO.getSetting(feedbackRequestBO.getOutletId());
        try {
            for (FeedbackDetails feedbackDetails : feedbacks) {
                AnswerDTO answerDTO = AnswerDAO.getAnswerById(feedbackDetails.getAnswerId());
                QuestionRequestDTO questionRequestDTO = QuestionDAO.getQuestionById(feedbackDetails.getQuestionId());
                if (setting.getSmsGatewayId() != null && !setting.getSmsGatewayId().equals("") &&
                        answerDTO.getThreshold() != null && !answerDTO.getThreshold().equals("")) {
                    if ((questionRequestDTO.getQuestionType() == '2' || questionRequestDTO.getQuestionType() == '3')
                            && feedbackDetails.getRating() != 0) {
                        int ans = answerDTO.getRating() / answerDTO.getWeightage();
                        int weightage = feedbackDetails.getRating() / ans;
                        if (weightage <= Integer.parseInt(answerDTO.getThreshold())) {
                            isExist = Boolean.TRUE;
                            FeedbackDAO.updateFeedback(id);
                            break;
                        }
                    }
                    if ((questionRequestDTO.getQuestionType() == '1' || questionRequestDTO.getQuestionType() == '5' ||
                            questionRequestDTO.getQuestionType() == '6') && answerDTO.getThreshold().equals("1")) {
                        isExist = Boolean.TRUE;
                        FeedbackDAO.updateFeedback(id);
                        break;
                    }
                }
            }

            if (isExist) {
                SettingRequestDTO settingRequestDTO = SmsDAO.fetchSettings();
                SmsSettingDTO smsSettingDTO = SmsDAO.fetchSmsSettingsById(setting.getSmsGatewayId());
                SendSms sendSms = new SendSms();
                sendSms.sendThresholdSms(id, settingRequestDTO.getNegativeSmsTemplate(), smsSettingDTO);
            }

            if (setting.getSmsGatewayId() != null && !setting.getSmsGatewayId().equals("") && setting.getReferType() == 1) {
                if (!isExist) {
                    SettingRequestDTO settingRequestDTO = SmsDAO.fetchSettings();
                    SmsSettingDTO smsSettingDTO = SmsDAO.fetchSmsSettingsById(setting.getSmsGatewayId());
                    SendSms sendSms = new SendSms();
                    sendSms.sendReferSms(id, settingRequestDTO.getPositiveSmsTemplate(), smsSettingDTO);
                }
            } else if (setting.getSmsGatewayId() != null && !setting.getSmsGatewayId().equals("") && setting.getReferType() == 2) {
                SettingRequestDTO settingRequestDTO = SmsDAO.fetchSettings();
                SmsSettingDTO smsSettingDTO = SmsDAO.fetchSmsSettingsById(setting.getSmsGatewayId());
                SendSms sendSms = new SendSms();
                sendSms.sendReferSms(id, settingRequestDTO.getPositiveSmsTemplate(), smsSettingDTO);
            }
        } finally {
            return id;
        }
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
        int id = customerDAO.addCustomer(createCustomer.getLocality(), createCustomer.getName(),
                createCustomer.getPhoneNo(), createCustomer.getEmailId(), createCustomer.getDob(),
                createCustomer.getDoa());
        return id;

    }

    public List<FeedbackResponse> getfeedbackList1(FeedbackListRequestBO feedbackListRequestBO) throws Exception {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<FeedbackDTO> feedbackRequestDTOS;
        String limit, where1 = "", ids;
        int threshold = 100000, base = 100000, length;

        Timestamp t1 = DateUtil.getTimeStampFromString(feedbackListRequestBO.getFromDate());
        String date = DateUtil.format(t1, "MM-yyyy");

        if (feedbackListRequestBO.getTableNo() != null && !feedbackListRequestBO.getTableNo().equals("")) {
            where1 = " and f.table_no=\"" + feedbackListRequestBO.getTableNo() + "\"\n";
        }
        if (feedbackListRequestBO.getOutletId() != null && feedbackListRequestBO.getOutletId().size() > 0) {
            ids = CommaSeparatedString.generate(feedbackListRequestBO.getOutletId());
            where1 += " and f.outlet_id IN(" + ids + ")\n";
        }
        if (feedbackListRequestBO.getOutletId() == null || feedbackListRequestBO.getOutletId().size() == 0) {
            LoginResponseDTO loginResponseDTO = UsersDAO.getuserById(feedbackListRequestBO.getUserId());
            RoleRequestDTO rollRequestDTO = UsersDAO.getroleById(loginResponseDTO.getRoleId());
            where1 += " and f.outlet_id IN(" + rollRequestDTO.getOutletAccess() + ")\n";
        }

        SyncDAO syncDAO = new SyncDAO();
        int max = syncDAO.getCount(where1, date, feedbackListRequestBO.getFromDate(), feedbackListRequestBO.getToDate());

        if (max < threshold) {
            length = 1;
        } else {
            length = max / threshold;
            if (max % threshold > 0) {
                length = length + 1;
            }
        }

        List<FeedbackResponse> uniqueList = new ArrayList<FeedbackResponse>(max);

        try {

            for (int i = 0; i < length; i++) {

                if (i == 0) {
                    limit = "limit 100000";
                } else {
                    limit = "limit " + base + "," + threshold;
                }

                if (i == length - 1) {
                    feedbackRequestDTOS = feedbackDAO.getfeedbackList1(where1, buildFeedbackDTO
                            (feedbackListRequestBO), date, limit);
                } else {
                    feedbackRequestDTOS = feedbackDAO.getfeedbackList1(where1, buildFeedbackDTO
                            (feedbackListRequestBO), date, limit);
                }

                List<Integer> uniqueIds = new ArrayList<Integer>();
                for (int j = 0; j < feedbackRequestDTOS.size(); j++) {
                    if (feedbackRequestDTOS.get(j).getIsAddressed() > 0) {
                        feedbackRequestDTOS.get(j).setIsAddressed(1);
                    }
                    if (!uniqueIds.contains(feedbackRequestDTOS.get(j).getId())) {
                        FeedbackResponse feedbackResp = new FeedbackResponse(feedbackRequestDTOS.get(j).getId(),
                                feedbackRequestDTOS.get(j).getViewCount(),
                                feedbackRequestDTOS.get(j).getFeedbackDate(),
                                feedbackRequestDTOS.get(j).getTableNo(),
                                feedbackRequestDTOS.get(j).getCustomerName(),
                                feedbackRequestDTOS.get(j).getOutletDesc(),
                                feedbackRequestDTOS.get(j).getMobileNo(),
                                feedbackRequestDTOS.get(j).getEmail(),
                                feedbackRequestDTOS.get(j).getDob(),
                                feedbackRequestDTOS.get(j).getDoa(),
                                feedbackRequestDTOS.get(j).getLocality(),
                                feedbackRequestDTOS.get(j).getIsAddressed(),
                                feedbackRequestDTOS.get(j).getViewDate(),
                                feedbackRequestDTOS.get(j).getIsNegative());
                        List<FeedbackDetails> newAnswerList = new ArrayList<FeedbackDetails>();
                        FeedbackDetails answer = new FeedbackDetails();
                        answer.setAnswerDesc(feedbackRequestDTOS.get(j).getAnswerDesc());
                        answer.setAnswerText(feedbackRequestDTOS.get(j).getAnswerText());
                        answer.setQuestionDesc(feedbackRequestDTOS.get(j).getQuestionDesc());
                        answer.setRating(feedbackRequestDTOS.get(j).getRating());
                        answer.setIsNegative(feedbackRequestDTOS.get(j).getIsPoor());
                        answer.setQuestionType(feedbackRequestDTOS.get(j).getQuestionType());
                        answer.setQuestionId(feedbackRequestDTOS.get(j).getQuestionId());
                        newAnswerList.add(answer);
                        feedbackResp.setFeedbacks(newAnswerList);
                        uniqueIds.add(feedbackRequestDTOS.get(j).getId());
                        uniqueList.add(feedbackResp);
                    } else {
                        FeedbackResponse existingResp = getResponseFromList(uniqueList, feedbackRequestDTOS.get(j).getId());
                        if (existingResp != null) {
                            List<FeedbackDetails> curAnswerList = existingResp.getFeedbacks();
                            FeedbackDetails answer = new FeedbackDetails();
                            answer.setAnswerDesc(feedbackRequestDTOS.get(j).getAnswerDesc());
                            answer.setAnswerText(feedbackRequestDTOS.get(j).getAnswerText());
                            answer.setQuestionDesc(feedbackRequestDTOS.get(j).getQuestionDesc());
                            answer.setRating(feedbackRequestDTOS.get(j).getRating());
                            answer.setIsNegative(feedbackRequestDTOS.get(j).getIsPoor());
                            answer.setQuestionType(feedbackRequestDTOS.get(j).getQuestionType());
                            answer.setQuestionId(feedbackRequestDTOS.get(j).getQuestionId());
                            curAnswerList.add(answer);
                        }
                    }
                }
                if (i > 0) {
                    base = base + threshold;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uniqueList;
    }

    private FeedbackResponse getResponseFromList(List<FeedbackResponse> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            if (id == list.get(i).getId()) {
                return list.get(i);
            }
        }
        return null;
    }

    private FeedbackListDTO buildFeedbackDTO(FeedbackListRequestBO feedbackListRequestBO) {
        FeedbackListDTO feedbackListDTO = new FeedbackListDTO();

        feedbackListDTO.setFromDate(feedbackListRequestBO.getFromDate());
        feedbackListDTO.setToDate(feedbackListRequestBO.getToDate());
        feedbackListDTO.setTableNo(feedbackListRequestBO.getTableNo());
        feedbackListDTO.setOutletId(feedbackListRequestBO.getOutletId());
        feedbackListDTO.setUserId(feedbackListRequestBO.getUserId());

        return feedbackListDTO;
    }

    public FeedbackByIdResponse getfeedbackById(int id) throws SQLException, FeedbackNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        FeedbackByIdResponse feedbackByIdResponse = buildResponseFromDTO(feedbackDAO.getFeedbacksList(id));
        return feedbackByIdResponse;
    }

    public FeedbackByIdResponse buildResponseFromDTO(List<FeedbackRequestDTO> feedbackRequestDTOs) throws SQLException, FeedbackNotFoundException {
        FeedbackByIdResponse feedbackResp = null;
        List<FeedbackByIdResponse> uniqueList = new ArrayList<FeedbackByIdResponse>();
        List<Integer> uniqueIds = new ArrayList<Integer>();
        for (FeedbackRequestDTO feedbackRequestDTO : feedbackRequestDTOs) {
            if (!uniqueIds.contains(feedbackRequestDTO.getId())) {
                feedbackResp = new FeedbackByIdResponse(feedbackRequestDTO.getQuestionType(),
                        feedbackRequestDTO.getId(),
                        feedbackRequestDTO.getDeviceId(),
                        feedbackRequestDTO.getFeedbackDate(),
                        feedbackRequestDTO.getOutletId(),
                        feedbackRequestDTO.getTableNo(),
                        feedbackRequestDTO.getBillNo(),
                        feedbackRequestDTO.getCustomerId(),
                        feedbackRequestDTO.getCustomerName(),
                        feedbackRequestDTO.getMobileNo(),
                        feedbackRequestDTO.getEmail(),
                        feedbackRequestDTO.getDob(),
                        feedbackRequestDTO.getDoa(),
                        feedbackRequestDTO.getLocality(),
                        feedbackRequestDTO.getOutletDesc());
                List<FeedbackDetails> newAnswerList = new ArrayList<FeedbackDetails>();
                FeedbackDetails answer = new FeedbackDetails();
                answer.setAnswerDesc(feedbackRequestDTO.getAnswerDesc());
                answer.setAnswerText(feedbackRequestDTO.getAnswerText());
                answer.setQuestionDesc(feedbackRequestDTO.getQuestionDesc());
                answer.setRating(feedbackRequestDTO.getRating());
                answer.setAnswerId(feedbackRequestDTO.getAnswerId());
                answer.setQuestionId(feedbackRequestDTO.getQuestionId());
                answer.setQuestionType(feedbackRequestDTO.getQuestionType());
                answer.setWeightage(feedbackRequestDTO.getWeightage());
                answer.setThreshold(feedbackRequestDTO.getThreshold());
                AnswerDTO answerDTO = AnswerDAO.getAnswerById(answer.getAnswerId());
                if (answer.getThreshold() != null && !answer.getThreshold().equals("")) {
                    if ((answer.getQuestionType() == '2' || answer.getQuestionType() == '3') && answer.getRating()
                            != 0) {
                        int ans = answerDTO.getRating() / answerDTO.getWeightage();
                        int weightage = answer.getRating() / ans;
                        if (weightage <= Integer.parseInt(answerDTO.getThreshold())) {
                            answer.setIsNegative(1);
                        } else {
                            answer.setIsNegative(0);
                        }
                    }
                    if ((answer.getQuestionType() == '1' || answer.getQuestionType() == '5' ||
                            answer.getQuestionType() == '6')) {
                        if (answerDTO.getThreshold().equals("1")) {
                            answer.setIsNegative(1);
                        } else {
                            answer.setIsNegative(0);
                        }
                    }
                } else {
                    answer.setIsNegative(0);
                }
                newAnswerList.add(answer);
                feedbackResp.setFeedbacks(newAnswerList);

                uniqueIds.add(feedbackRequestDTO.getId());
                uniqueList.add(feedbackResp);
            } else {
                FeedbackByIdResponse existingResp = getResponseFromList1(uniqueList, feedbackRequestDTO.getId());
                if (existingResp != null) {
                    List<FeedbackDetails> curAnswerList = existingResp.getFeedbacks();
                    FeedbackDetails answer = new FeedbackDetails();
                    answer.setAnswerDesc(feedbackRequestDTO.getAnswerDesc());
                    answer.setAnswerText(feedbackRequestDTO.getAnswerText());
                    answer.setQuestionDesc(feedbackRequestDTO.getQuestionDesc());
                    answer.setRating(feedbackRequestDTO.getRating());
                    answer.setAnswerId(feedbackRequestDTO.getAnswerId());
                    answer.setQuestionId(feedbackRequestDTO.getQuestionId());
                    answer.setQuestionType(feedbackRequestDTO.getQuestionType());
                    answer.setWeightage(feedbackRequestDTO.getWeightage());
                    answer.setThreshold(feedbackRequestDTO.getThreshold());
                    AnswerDTO answerDTO = AnswerDAO.getAnswerById(answer.getAnswerId());
                    if (answer.getThreshold() != null && !answer.getThreshold().equals("")) {
                        if ((answer.getQuestionType() == '2' || answer.getQuestionType() == '3') &&
                                answer.getRating() != 0) {
                            int ans = answerDTO.getRating() / answerDTO.getWeightage();
                            int weightage = answer.getRating() / ans;
                            if (weightage <= Integer.parseInt(answerDTO.getThreshold())) {
                                answer.setIsNegative(1);
                            } else {
                                answer.setIsNegative(0);
                            }
                        }
                        if ((answer.getQuestionType() == '1' || answer.getQuestionType() == '5' ||
                                answer.getQuestionType() == '6')) {
                            if (answerDTO.getThreshold().equals("1")) {
                                answer.setIsNegative(1);
                            } else {
                                answer.setIsNegative(0);
                            }
                        }
                    } else {
                        answer.setIsNegative(0);
                    }
                    curAnswerList.add(answer);
                }
            }
        }

        return feedbackResp;
    }


    private FeedbackByIdResponse getResponseFromList1(List<FeedbackByIdResponse> list, int id) {
        for (FeedbackByIdResponse aList : list) {
            if (id == aList.getId()) {
                return aList;
            }
        }
        return null;
    }


    public Boolean createFeedbackTracking(FeedbackTrackingRequestBO feedbackTrackingRequestBO) throws SQLException, FeedbackNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        Boolean isUpdate;
        FeedbackTrackingDTO feedbackTrackingDTO = buildFeedbackTrackingDTOFromBO(feedbackTrackingRequestBO);
        FeedbackTrackingDTO feedbackTrackingDTO1 = feedbackDAO.getcustomer(feedbackTrackingDTO.getFeedbackId());
        if (feedbackTrackingDTO1.getFeedbackId() == 0) {
            isUpdate = feedbackDAO.createFeedbackTracking(feedbackTrackingDTO);
        } else {
            isUpdate = feedbackDAO.updateFeedbackTracking(feedbackTrackingDTO);
        }
        return isUpdate;
    }


    private FeedbackTrackingDTO buildFeedbackTrackingDTOFromBO(FeedbackTrackingRequestBO feedbackTrackingRequestBO) throws FeedbackNotFoundException {
        FeedbackTrackingDTO feedbackTrackingDTO = new FeedbackTrackingDTO();

        feedbackTrackingDTO.setFeedbackId(feedbackTrackingRequestBO.getFeedbackId());
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        FeedbackRequestDTO feedbackRequestDTO = feedbackDAO.getfeedbackById(feedbackTrackingRequestBO.getFeedbackId());
        UpdateSettingsDTO updateSettingsDTO = OutletDAO.getSetting(feedbackRequestDTO.getOutletId());
        if (updateSettingsDTO.getMgrEmail() == null && updateSettingsDTO.getMgrMobile() == null && updateSettingsDTO.getMgrName() == null) {
            feedbackTrackingDTO.setManagerEmail("");
            feedbackTrackingDTO.setManagerMobile("");
            feedbackTrackingDTO.setManagerName("");
        }
        feedbackTrackingDTO.setManagerMobile(updateSettingsDTO.getMgrMobile());
        feedbackTrackingDTO.setManagerName(updateSettingsDTO.getMgrName());
        feedbackTrackingDTO.setManagerEmail(updateSettingsDTO.getMgrEmail());
        return feedbackTrackingDTO;
    }


    public List<FeedbackTrackingResponse> getFeedbackTrackingList(FeedbackListRequestBO feedbackListRequestBO, int isNegative) throws SQLException, UserNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<FeedbackTrackingResponseDTO> trackingList;
        String limit, where1 = "", ids;
        int threshold = 100000, base = 100000, length;

        Timestamp t1 = DateUtil.getTimeStampFromString(feedbackListRequestBO.getFromDate());
        String date = DateUtil.format(t1, "MM-yyyy");

        Connection connection = null;

        if (feedbackListRequestBO.getTableNo() != null && !feedbackListRequestBO.getTableNo().equals("")) {
            where1 = " and f.table_no=\"" + feedbackListRequestBO.getTableNo() + "\"\n";
        }
        if (feedbackListRequestBO.getOutletId() != null && feedbackListRequestBO.getOutletId().size() > 0) {
            ids = CommaSeparatedString.generate(feedbackListRequestBO.getOutletId());
            where1 += " and f.outlet_id IN(" + ids + ")\n";
        }
        if (feedbackListRequestBO.getOutletId() == null || feedbackListRequestBO.getOutletId().size() == 0) {
            LoginResponseDTO loginResponseDTO = UsersDAO.getuserById(feedbackListRequestBO.getUserId());
            RoleRequestDTO rollRequestDTO = UsersDAO.getroleById(loginResponseDTO.getRoleId());
            where1 += " and f.outlet_id IN(" + rollRequestDTO.getOutletAccess() + ")\n";
        }

        SyncDAO syncDAO = new SyncDAO();
        int max = syncDAO.getCount(where1, date, feedbackListRequestBO.getFromDate(), feedbackListRequestBO.getToDate());

        if (max < threshold) {
            length = 1;
        } else {
            length = max / threshold;
            if (max % threshold > 0) {
                length = length + 1;
            }
        }

        List<FeedbackTrackingResponse> feedbackTrackingDTOList = new ArrayList<FeedbackTrackingResponse>(max);

        try {
            connection = new ConnectionHandler().getConnection();

            for (int i = 0; i < length; i++) {

                if (i == 0) {
                    limit = "limit 100000";
                } else {
                    limit = "limit " + base + "," + threshold;
                }

                if (i == length - 1) {
                    trackingList = feedbackDAO.getFeedbackTrackingList(connection, where1, limit, feedbackListRequestBO, isNegative, date);
                } else {
                    trackingList = feedbackDAO.getFeedbackTrackingList(connection, where1, limit, feedbackListRequestBO, isNegative, date);
                }

                for (FeedbackTrackingResponseDTO feedbackTrackingResponseDTO : trackingList) {
                    if (feedbackTrackingResponseDTO.getIsAddressed() > 0) {
                        feedbackTrackingResponseDTO.setIsAddressed(1);
                    }
                    FeedbackTrackingResponse feedbackTrackingResponse = new FeedbackTrackingResponse(feedbackTrackingResponseDTO.getFeedbackId(),
                            feedbackTrackingResponseDTO.getOutletId(),
                            feedbackTrackingResponseDTO.getOutletName(),
                            feedbackTrackingResponseDTO.getEmail(),
                            feedbackTrackingResponseDTO.getDate(),
                            feedbackTrackingResponseDTO.getTableNo(),
                            feedbackTrackingResponseDTO.getCustomerId(),
                            feedbackTrackingResponseDTO.getCustomerName(),
                            feedbackTrackingResponseDTO.getPhoneNo(),
                            feedbackTrackingResponseDTO.getMgrName(),
                            feedbackTrackingResponseDTO.getMgrMobileNo(),
                            feedbackTrackingResponseDTO.getMgrEmail(),
                            feedbackTrackingResponseDTO.getFistViewDate(),
                            feedbackTrackingResponseDTO.getViewCount(),
                            feedbackTrackingResponseDTO.getIsAddressed(),
                            feedbackTrackingResponseDTO.getIsNegative());

                    feedbackTrackingDTOList.add(feedbackTrackingResponse);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return feedbackTrackingDTOList;
    }

    public Boolean getDailyReport(BillData data) throws SQLException, UserNotFoundException {
        Boolean isSent = Boolean.FALSE;
        UsersDAO usersDAO = new UsersDAO();
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        OutletDAO outletDAO = new OutletDAO();

        for (ReportData reportData : data.getOutlets()) {
            outletDAO.updateBillCount(reportData);
        }

        Date date1 = new Date();
        Timestamp t1 = new Timestamp(date1.getTime());
        String currentDate = DateUtil.getCurrentServerTimeByRemoteTimestamp(t1);
        String date = DateUtil.format(t1, "MM-yyyy");

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DATE, -1);
        Date date2 = cal.getTime();
        Timestamp t2 = new Timestamp(date2.getTime());
        String previousDate = DateUtil.getCurrentServerTimeByRemoteTimestamp(t2);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date3 = cal.getTime();
        Timestamp t3 = new Timestamp(date3.getTime());
        String previousMonth = DateUtil.getCurrentServerTimeByRemoteTimestamp(t3);

        List<LoginResponseDTO> users = usersDAO.getUserList();
        for (LoginResponseDTO user : users) {
            if (!user.getOutletAccess().equals("") && user.getNotifyEmail() == 1) {
                String outlets = user.getOutletAccess();
                ReportDTO dailyReportDTO = feedbackDAO.getDailyReport(date, outlets, previousDate, currentDate);
                List<ReportData> dailyOutletReport = feedbackDAO.getOutletReport(date, outlets, previousDate, currentDate);
                dailyReportDTO.setOutlets(dailyOutletReport);
                ReportDTO monthlyReportDTO = feedbackDAO.getDailyReport(date, outlets, previousMonth, currentDate);
                List<ReportData> monthlyOutletReport = feedbackDAO.getOutletReport(date, outlets, previousMonth, currentDate);
                monthlyReportDTO.setOutlets(monthlyOutletReport);
                dailyReportDTO.setUserName(user.getName());
                isSent = EmailService.sendReport(currentDate, user.getEmail(), dailyReportDTO, monthlyReportDTO);
            }
        }
        return isSent;
    }

    public Boolean getHeavyReport() throws Exception {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        Boolean isProcessed;
        List<RequestDto> requestDtos = feedbackDAO.getRequests();
        for (RequestDto request : requestDtos) {
            if (!UsersDAO.getuserById(request.getUserId()).getOutletAccess().equals("")) {
                System.out.println("data" + DateUtil.getDateStringFromTimeStamp(new Timestamp(System.currentTimeMillis())));
                String uniqueList = getfeedbackList12(request);
                System.out.println("sheet" + DateUtil.getDateStringFromTimeStamp(new Timestamp(System.currentTimeMillis())));
                // String filename = ExcelCreator.getExcelSheet(uniqueList, String.valueOf(request.getId()));
                System.out.println("end" + DateUtil.getDateStringFromTimeStamp(new Timestamp(System.currentTimeMillis())));
                EmailService.sendEmail(uniqueList, request.getUserId());
            }
        }
        isProcessed = Boolean.TRUE;
        return isProcessed;
    }

    public String getfeedbackList12(RequestDto feedbackListRequestBO) throws Exception {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<FeedbackDTO> feedbackRequestDTOS;
        String limit, where1 = "";
        int threshold = 100000, base = 100000, length, i = 2, l = 0;

        Timestamp t1 = DateUtil.getTimeStampFromString(feedbackListRequestBO.getFromDate());
        String date = DateUtil.format(t1, "MM-yyyy");

        if (feedbackListRequestBO.getTable() != null && !feedbackListRequestBO.getTable().equals("")) {
            where1 = " and f.table_no=\"" + feedbackListRequestBO.getTable() + "\"\n";
        }
        if (feedbackListRequestBO.getOutlets() != null && !feedbackListRequestBO.getOutlets().equals("")) {
            where1 += " and f.outlet_id IN(" + feedbackListRequestBO.getOutlets() + ")\n";
        }
        if (feedbackListRequestBO.getOutlets() == null || feedbackListRequestBO.getOutlets().equals("")) {
            LoginResponseDTO loginResponseDTO = UsersDAO.getuserById(feedbackListRequestBO.getUserId());
            RoleRequestDTO rollRequestDTO = UsersDAO.getroleById(loginResponseDTO.getRoleId());
            where1 += " and f.outlet_id IN(" + rollRequestDTO.getOutletAccess() + ")\n";
        }

        SyncDAO syncDAO = new SyncDAO();
        int max = syncDAO.getCount(where1, date, feedbackListRequestBO.getFromDate(), feedbackListRequestBO.getToDate());

        if (max < threshold) {
            length = 1;
        } else {
            length = max / threshold;
            if (max % threshold > 0) {
                length = length + 1;
            }
        }

        List<FeedbackResponse> uniqueList = new ArrayList<FeedbackResponse>(max);

        FeedbackListDTO dto = new FeedbackListDTO();
        dto.setFromDate(feedbackListRequestBO.getFromDate());
        dto.setToDate(feedbackListRequestBO.getToDate());

        /////////////////////////

        String filename = "D:/Feedbacks_" + date + ".xls";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FirstSheet");
        sheet.addMergedRegion(CellRangeAddress.valueOf("S1:X1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("Z1:AA1"));

        HSSFFont font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Verdana");

        HSSFRow row = null;


        HSSFCellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFont(font);

        HSSFCellStyle style1 = workbook.createCellStyle();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style1.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style1.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style1.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style1.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style1.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style1.setFont(font);

        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);

        HSSFCellStyle style3 = workbook.createCellStyle();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
        style3.setFillForegroundColor(IndexedColors.RED.getIndex());
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style3.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style3.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style3.setFont(font);

        HSSFRow rowhead = sheet.createRow((short) 0);

        rowhead.createCell(0).setCellValue("Response No.");
        rowhead.createCell(1).setCellValue("Date");
        rowhead.createCell(2).setCellValue("Outlet");
        rowhead.createCell(3).setCellValue("Table No");
        rowhead.createCell(4).setCellValue("Customer");
        rowhead.createCell(5).setCellValue("");
        rowhead.createCell(6).setCellValue("");
        rowhead.createCell(7).setCellValue("");
        rowhead.createCell(8).setCellValue("");
        rowhead.createCell(9).setCellValue("");
        rowhead.createCell(10).setCellValue("isNegative");
        rowhead.createCell(11).setCellValue("isAddressed Details");
        rowhead.createCell(12).setCellValue("");

        for (Cell cell : rowhead) {
            cell.setCellStyle(style);
        }


        HSSFRow rowhead1 = sheet.createRow((short) 1);

        rowhead1.createCell(0).setCellValue("");
        rowhead1.createCell(1).setCellValue("");
        rowhead1.createCell(2).setCellValue("");
        rowhead1.createCell(3).setCellValue("");
        rowhead1.createCell(4).setCellValue("Name");
        rowhead1.createCell(5).setCellValue("Email");
        rowhead1.createCell(6).setCellValue("Phone");
        rowhead1.createCell(7).setCellValue("Date of Birth");
        rowhead1.createCell(8).setCellValue("Date of Anniversary");
        rowhead1.createCell(9).setCellValue("Locality");
        rowhead1.createCell(10).setCellValue("");
        rowhead1.createCell(11).setCellValue("isAddressed?");
        rowhead1.createCell(12).setCellValue("View Date");

        for (Cell cell : rowhead1) {
            cell.setCellStyle(style1);
            workbook.getSheetAt(0).autoSizeColumn(cell.getColumnIndex());
        }

        rowhead1.getCell(0).setCellStyle(style);

        /////////////////////////

        try {
            for (int o = 0; o < length; o++) {

                if (o == 0) {
                    limit = "limit 100000";
                } else {
                    limit = "limit " + base + "," + threshold;
                }

                if (o == length - 1) {
                    feedbackRequestDTOS = feedbackDAO.getfeedbackList1(where1, dto, date, limit);
                } else {
                    feedbackRequestDTOS = feedbackDAO.getfeedbackList1(where1, dto, date, limit);
                }

                List<Integer> uniqueIds = new ArrayList<Integer>();
                for (int j = 0; j < feedbackRequestDTOS.size(); j++) {
                    if (feedbackRequestDTOS.get(j).getIsAddressed() > 0) {
                        feedbackRequestDTOS.get(j).setIsAddressed(1);
                    }
                    if (!uniqueIds.contains(feedbackRequestDTOS.get(j).getId())) {
                        FeedbackResponse feedbackResp = new FeedbackResponse(feedbackRequestDTOS.get(j).getId(),
                                feedbackRequestDTOS.get(j).getViewCount(),
                                feedbackRequestDTOS.get(j).getFeedbackDate(),
                                feedbackRequestDTOS.get(j).getTableNo(),
                                feedbackRequestDTOS.get(j).getCustomerName(),
                                feedbackRequestDTOS.get(j).getOutletDesc(),
                                feedbackRequestDTOS.get(j).getMobileNo(),
                                feedbackRequestDTOS.get(j).getEmail(),
                                feedbackRequestDTOS.get(j).getDob(),
                                feedbackRequestDTOS.get(j).getDoa(),
                                feedbackRequestDTOS.get(j).getLocality(),
                                feedbackRequestDTOS.get(j).getIsAddressed(),
                                feedbackRequestDTOS.get(j).getViewDate(),
                                feedbackRequestDTOS.get(j).getIsNegative());
                        List<FeedbackDetails> newAnswerList = new ArrayList<FeedbackDetails>();
                        FeedbackDetails answer = new FeedbackDetails();
                        answer.setAnswerDesc(feedbackRequestDTOS.get(j).getAnswerDesc());
                        answer.setAnswerText(feedbackRequestDTOS.get(j).getAnswerText());
                        answer.setQuestionDesc(feedbackRequestDTOS.get(j).getQuestionDesc());
                        answer.setRating(feedbackRequestDTOS.get(j).getRating());
                        answer.setIsNegative(feedbackRequestDTOS.get(j).getIsPoor());
                        answer.setQuestionType(feedbackRequestDTOS.get(j).getQuestionType());
                        answer.setQuestionId(feedbackRequestDTOS.get(j).getQuestionId());
                        newAnswerList.add(answer);
                        feedbackResp.setFeedbacks(newAnswerList);
                        uniqueIds.add(feedbackRequestDTOS.get(j).getId());
                        /*uniqueList.add(feedbackResp);*/
                        row = sheet.createRow((short) i);
                        row.createCell(0).setCellValue(feedbackResp.getId());
                        row.getCell(0).setCellStyle(style);
                        row.createCell(1).setCellValue(feedbackResp.getFeedbackDate());
                        workbook.getSheetAt(0).autoSizeColumn(1);
                        row.createCell(2).setCellValue(feedbackResp.getOutletDesc());
                        workbook.getSheetAt(0).autoSizeColumn(2);
                        row.createCell(3).setCellValue(feedbackResp.getTableNo());
                        if (feedbackResp.getCustomerName() == null || feedbackResp.getCustomerName().equals("")) {
                            row.createCell(4).setCellValue("-");
                        } else {
                            row.createCell(4).setCellValue(feedbackResp.getCustomerName());
                            workbook.getSheetAt(0).autoSizeColumn(4);
                        }
                        if (feedbackResp.getEmail() == null || feedbackResp.getEmail().equals("")) {
                            row.createCell(5).setCellValue("-");
                        } else {
                            row.createCell(5).setCellValue(feedbackResp.getEmail());
                            workbook.getSheetAt(0).autoSizeColumn(5);
                        }
                        if (feedbackResp.getMobileNo() == null || feedbackResp.getMobileNo().equals("")) {
                            row.createCell(6).setCellValue("-");
                        } else {
                            row.createCell(6).setCellValue(feedbackResp.getMobileNo());
                            workbook.getSheetAt(0).autoSizeColumn(6);
                        }
                        if (feedbackResp.getDob() == null || feedbackResp.getDob().equals("")) {
                            row.createCell(7).setCellValue("-");
                        } else {
                            Timestamp dob = DateUtil.getTimeStampFromString(feedbackResp.getDob());
                            row.createCell(7).setCellValue(DateUtil.format(dob, "dd-MMM"));
                        }
                        if (feedbackResp.getDoa() == null || feedbackResp.getDoa().equals("")) {
                            row.createCell(8).setCellValue("-");
                        } else {
                            Timestamp doa = DateUtil.getTimeStampFromString(feedbackResp.getDoa());
                            row.createCell(8).setCellValue(DateUtil.format(doa, "dd-MMM"));
                        }
                        if (feedbackResp.getLocality() == null || feedbackResp.getLocality().equals("")) {
                            row.createCell(9).setCellValue("-");
                        } else {
                            row.createCell(9).setCellValue(feedbackResp.getLocality());
                            workbook.getSheetAt(0).autoSizeColumn(9);
                        }
                        if (feedbackResp.getIsNegative() == 0) {
                            row.createCell(10).setCellValue("NO");
                        } else {
                            row.createCell(10).setCellValue("YES");
                        }
                        if (feedbackResp.getViewDate() == null || feedbackResp.getViewDate().equals("")) {
                            row.createCell(11).setCellValue("NO");
                            row.createCell(12).setCellValue("-");
                        } else {
                            row.createCell(11).setCellValue("YES");
                            row.createCell(12).setCellValue(feedbackResp.getViewDate());
                            workbook.getSheetAt(0).autoSizeColumn(12);
                        }
                        l = 13;
                        if (answer.getQuestionType() == '3' || answer.getQuestionType() == '2') {
                            if (answer.getRating() == 0) {
                                row.createCell(l).setCellValue("Skipped");
                            } else {
                                row.createCell(l).setCellValue(getReview(answer.getRating()));
                            }
                            if (answer.getIsNegative() == 1) {
                                row.getCell(l).setCellStyle(style3);
                            }
                            l++;
                        } else if (answer.getQuestionType() == '4') {
                            if (answer.getAnswerText() == null || answer.getAnswerText().equals("")) {
                                row.createCell(l).setCellValue("Skipped");
                            } else {
                                row.createCell(l).setCellValue(answer.getAnswerText());
                                row.getCell(l).setCellStyle(style2);
                            }
                            l++;
                        } else if (answer.getQuestionType() == '1') {
                            if (answer.getAnswerDesc() == null || answer.getAnswerDesc().equals("")) {
                                row.createCell(l).setCellValue("Skipped");
                            } else {
                                row.createCell(l).setCellValue(answer.getAnswerDesc());
                                row.getCell(l).setCellStyle(style2);
                            }
                            l++;
                        } else if (answer.getQuestionType() == '5') {
                            if (answer.getAnswerDesc() == null || answer.getAnswerDesc().equals("")) {
                                row.createCell(l).setCellValue("Skipped");
                            } else {
                                row.createCell(l).setCellValue(answer.getAnswerDesc());
                            }
                            row.getCell(l).setCellStyle(style2);
                            i++;

                            ////////////////////////////////////
                        }
                    } else {
                        FeedbackResponse existingResp = getResponseFromList(uniqueList, feedbackRequestDTOS.get(j).getId());
                        if (existingResp != null) {
                            List<FeedbackDetails> curAnswerList = existingResp.getFeedbacks();
                            FeedbackDetails answer1 = new FeedbackDetails();
                            answer1.setAnswerDesc(feedbackRequestDTOS.get(j).getAnswerDesc());
                            answer1.setAnswerText(feedbackRequestDTOS.get(j).getAnswerText());
                            answer1.setQuestionDesc(feedbackRequestDTOS.get(j).getQuestionDesc());
                            answer1.setRating(feedbackRequestDTOS.get(j).getRating());
                            answer1.setIsNegative(feedbackRequestDTOS.get(j).getIsPoor());
                            answer1.setQuestionType(feedbackRequestDTOS.get(j).getQuestionType());
                            answer1.setQuestionId(feedbackRequestDTOS.get(j).getQuestionId());
                            curAnswerList.add(answer1);

                            //////////////////////////////////////

                            if (answer1.getQuestionType() == '3' || answer1.getQuestionType() == '2') {
                                if (answer1.getRating() == 0) {
                                    row.createCell(l).setCellValue("Skipped");
                                } else {
                                    row.createCell(l).setCellValue(getReview(answer1.getRating()));
                                }
                                if (answer1.getIsNegative() == 1) {
                                    row.getCell(l).setCellStyle(style3);
                                }
                                workbook.getSheetAt(0).autoSizeColumn(l);
                                l++;
                            } else if (answer1.getQuestionType() == '4') {
                                if (answer1.getAnswerText() == null || answer1.getAnswerText().equals("")) {
                                    row.createCell(l).setCellValue("Skipped");
                                } else {
                                    row.createCell(l).setCellValue(answer1.getAnswerText());
                                    row.getCell(l).setCellStyle(style2);
                                }
                                l++;
                            } else if (answer1.getQuestionType() == '1') {
                                if (answer1.getAnswerDesc() == null || answer1.getAnswerDesc().equals("")) {
                                    row.createCell(l).setCellValue("Skipped");
                                } else {
                                    row.createCell(l).setCellValue(answer1.getAnswerDesc());
                                    row.getCell(l).setCellStyle(style2);
                                }
                                l++;
                            } else if (answer1.getQuestionType() == '5') {
                                if (answer1.getAnswerDesc() == null || answer1.getAnswerDesc().equals("")) {
                                    row.createCell(l).setCellValue("Skipped");
                                } else {
                                    row.createCell(l).setCellValue(answer1.getAnswerDesc());
                                }
                                row.getCell(l).setCellStyle(style2);

                                //////////////////////////////////////////
                            }
                        }
                    }
                    if (o > 0) {
                        base = base + threshold;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public Boolean getRequest(FeedbackListRequestBO feedbackListRequestBO) throws Exception {
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        Gson gson = new Gson();
        // gson.toJson(feedbackListRequestBO, new FileWriter("D:\\file1.json"));
        String jsonInString = gson.toJson(feedbackListRequestBO);
        JSONObject jsonObject = new JSONObject(jsonInString);
        Boolean isCreate = feedbackDAO.createRequest(jsonObject);
        return isCreate;
    }

    public static String getReview(int rating) {
        String review = "";
        if (rating <= 20) {
            review = "Poor";
        } else if (rating <= 40) {
            review = "Average";
        } else if (rating <= 60) {
            review = "Good";
        } else if (rating <= 80) {
            review = "Very Good";
        } else if (rating <= 100) {
            review = "Excellent";
        }
        return review;
    }

    public static AnswerDTO getAnswer(FeedbackDetails feedbackDetails) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setAnswerDesc(feedbackDetails.getAnswerDesc());
        answerDTO.setRating(feedbackDetails.getRating());
        answerDTO.setIsPoor(feedbackDetails.getIsNegative());
        return answerDTO;
    }

}