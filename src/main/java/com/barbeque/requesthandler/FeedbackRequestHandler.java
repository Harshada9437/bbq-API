package com.barbeque.requesthandler;

import com.barbeque.bo.FeedbackListRequestBO;
import com.barbeque.bo.UpdateCustomerRequestBO;
import com.barbeque.dao.FeedbackDAO;
import com.barbeque.dao.Sync.SmsDAO;
import com.barbeque.dao.answer.AnswerDAO;
import com.barbeque.dao.customer.CustomerDAO;
import com.barbeque.dao.outlet.OutletDAO;
import com.barbeque.dao.question.QuestionDAO;
import com.barbeque.dto.request.UpdateSettingsDTO;
import com.barbeque.dto.request.*;
import com.barbeque.bo.FeedbackRequestBO;
import com.barbeque.bo.UpdateFeedbackRequestBO;
import com.barbeque.exceptions.FeedbackNotFoundException;
import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.response.feedback.CreateCustomer;
import com.barbeque.response.feedback.FeedbackByIdResponse;
import com.barbeque.response.feedback.FeedbackResponse;
import com.barbeque.util.DateUtil;
import com.barbeque.util.SendSms;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by user on 10/18/2016.
 */
public class FeedbackRequestHandler {
    public Integer addFeedback(FeedbackRequestBO feedbackRequestBO) throws SQLException,
            QuestionNotFoundException, FeedbackNotFoundException {
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
        List<FeedbackDetails> feedbacks = FeedbackDAO.getfeedback(id);
        Boolean isExist = Boolean.FALSE;
        UpdateSettingsDTO setting = OutletDAO.getSetting(feedbackRequestBO.getOutletId());
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
                        break;
                    }
                }
                if ((questionRequestDTO.getQuestionType() == '1' || questionRequestDTO.getQuestionType() == '5' ||
                        questionRequestDTO.getQuestionType() == '6') && answerDTO.getThreshold().equals("1")) {
                    isExist = Boolean.TRUE;
                    break;
                }
            }
        }
        if (isExist) {
            SettingRequestDTO settingRequestDTO = SmsDAO.fetchSettings();
            SmsSettingDTO smsSettingDTO = SmsDAO.fetchSmsSettingsById(setting.getSmsGatewayId());
            SendSms.sendThresholdSms(id, settingRequestDTO.getSmsTemplate(), smsSettingDTO);
        }
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
        int id = customerDAO.addCustomer(createCustomer.getLocality(), createCustomer.getName(),
                createCustomer.getPhoneNo(), createCustomer.getEmailId(), createCustomer.getDob(), createCustomer.getDoa());
        return id;

    }

    public List<FeedbackResponse> getfeedbackList1(FeedbackListRequestBO feedbackListRequestBO) throws
            SQLException, UserNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        List<FeedbackRequestDTO> feedbackRequestDTOS = feedbackDAO.getfeedbackList1(buildFeedbackDTO
                (feedbackListRequestBO));
        List<FeedbackResponse> uniqueList = new ArrayList<FeedbackResponse>();
        List<Integer> uniqueIds = new ArrayList<Integer>();
        for (FeedbackRequestDTO feedbackRequestDTO : feedbackRequestDTOS) {
            if (!uniqueIds.contains(feedbackRequestDTO.getId())) {
                FeedbackResponse feedbackResp = new FeedbackResponse(feedbackRequestDTO.getId(),
                        DateUtil.getDateStringFromTimeStamp(feedbackRequestDTO.getFeedbackDate()),
                        feedbackRequestDTO.getOutletId(),
                        feedbackRequestDTO.getTableNo(),
                        feedbackRequestDTO.getBillNo(),
                        feedbackRequestDTO.getOutletDesc(),
                        feedbackRequestDTO.getCustomerId(),
                        feedbackRequestDTO.getCustomerName(),
                        feedbackRequestDTO.getMobileNo(),
                        feedbackRequestDTO.getEmail(),
                        feedbackRequestDTO.getDob(),
                        feedbackRequestDTO.getDoa(),
                        feedbackRequestDTO.getLocality());
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
                newAnswerList.add(answer);
                feedbackResp.setFeedbacks(newAnswerList);

                uniqueIds.add(feedbackRequestDTO.getId());
                uniqueList.add(feedbackResp);
            } else {
                FeedbackResponse existingResp = getResponseFromList(uniqueList, feedbackRequestDTO.getId());
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
                    curAnswerList.add(answer);
                }
            }
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

    public FeedbackByIdResponse getfeedbackById(int id) throws SQLException, FeedbackNotFoundException,
            QuestionNotFoundException {

        FeedbackByIdResponse feedbackByIdResponse = buildResponseFromDTO(FeedbackDAO.getFeedbacksList(id));
        return feedbackByIdResponse;
    }

    public FeedbackByIdResponse buildResponseFromDTO(List<FeedbackRequestDTO> feedbackRequestDTOs) throws
            SQLException, FeedbackNotFoundException, QuestionNotFoundException {
        FeedbackByIdResponse feedbackResp = null;
        List<FeedbackByIdResponse> uniqueList = new ArrayList<FeedbackByIdResponse>();
        List<Integer> uniqueIds = new ArrayList<Integer>();
        for (FeedbackRequestDTO feedbackRequestDTO : feedbackRequestDTOs) {
            if (!uniqueIds.contains(feedbackRequestDTO.getId())) {
                feedbackResp = new FeedbackByIdResponse(feedbackRequestDTO.getQuestionType(),
                        feedbackRequestDTO.getId(),
                        feedbackRequestDTO.getDeviceId(),
                        DateUtil.getDateStringFromTimeStamp(feedbackRequestDTO.getFeedbackDate()),
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
        for (int i = 0; i < list.size(); i++) {
            if (id == list.get(i).getId()) {
                return list.get(i);
            }
        }
        return null;
    }
}







