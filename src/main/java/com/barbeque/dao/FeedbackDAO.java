package com.barbeque.dao;

import com.barbeque.bo.FeedbackListRequestBO;
import com.barbeque.dao.customer.CustomerDAO;
import com.barbeque.dao.question.QuestionDAO;
import com.barbeque.dto.request.*;
import com.barbeque.exceptions.CustomerNotFoundException;
import com.barbeque.exceptions.FeedbackNotFoundException;

import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.request.report.Feedback;
import com.barbeque.request.report.ReportData;
import com.barbeque.dto.RequestDto;
import com.barbeque.util.CommaSeparatedString;
import com.barbeque.util.DateUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.sql.*;
import java.util.*;

/**
 * Created by System-2 on 12/13/2016.
 */
public class FeedbackDAO {
    public Integer addFeedback(FeedbackRequestDTO feedbackRequestDTO, int customerId) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            StringBuilder query = new StringBuilder("INSERT INTO feedback_head( device_id, outlet_id, date, customer_id, table_no, bill_no");
            query.append(")values (?,?,?,?,?,?)");

            preparedStatement = connection.prepareStatement(query.toString());

            preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getDeviceId());

            preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getOutletId());

            Timestamp date = DateUtil.getTimeStampFromString(feedbackRequestDTO.getDate());
            preparedStatement.setTimestamp(parameterIndex++, date);

            preparedStatement.setInt(parameterIndex++, customerId);

            preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getTableNo());

            preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getBillNo());

            int i = preparedStatement.executeUpdate();

            try {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    for (FeedbackDetails feedbackDetailsObj : feedbackRequestDTO.getFeedbacks()) {
                        createFeedbackDetail(id, feedbackDetailsObj.getQuestionId(), feedbackDetailsObj.getAnswerId(), feedbackDetailsObj.getAnswerText(), feedbackDetailsObj.getRating(), connection);
                    }
                    connection.commit();
                } else {
                    connection.rollback();
                    throw new SQLException("Creating feedback failed, no ID obtained.");
                }
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException sqlException) {
            connection.rollback();
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }


    public void createFeedbackDetail(int feedback_id, int question_id, int answer_id, String answer_text, int rating, Connection connection) throws SQLException {
        PreparedStatement preparedStatement;
        StringBuilder query = new StringBuilder("INSERT INTO feedback(feedback_id, question_id, answer_id, answer_text, rating) values (?,?,?,?,?)");
        try {
            int parameterIndex = 1;
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    feedback_id);
            preparedStatement.setInt(parameterIndex++,
                    question_id);
            preparedStatement.setInt(parameterIndex++,
                    answer_id);
            preparedStatement.setString(parameterIndex++,
                    answer_text);
            preparedStatement.setInt(parameterIndex++,
                    rating);

            int i = preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        }
    }

    public List<FeedbackDTO> getfeedbackList1(String where1,Connection connection, FeedbackListDTO feedbackListDTO, String date, String limit) throws Exception {
        List<FeedbackDTO> feedbackList;
        Statement statement = null;
        //Connection connection = null;
        String table = "feedbacks_" + date;

        try {
           // connection = new ConnectionHandler().getConnection();
            ///connection.setAutoCommit(false);
            statement = connection.createStatement();

            feedbackList = new ArrayList<FeedbackDTO>();

            String query = "select f.answer_id,f.feedback_id,f.is_poor,f.date,f.customer_name,f.customer_no,f.customer_email" +
                    ",f.dob,f.doa,f.locality,f.table_no,f.outlet_name,f.rating,f.answer_text,f.question_desc,f.question_type" +
                    ",f.question_id,f.answer_desc,f.isNegative,t.feedback_id as isAddressed,t.first_view_date,t.view_count,t.manager_name,t.manager_mobile" +
                    ",t.manager_email from `" + table + "` f\n" +
                    "left join feedback_view_tracking t on f.feedback_id = t.feedback_id\n" +
                    "where f.date>='" + feedbackListDTO.getFromDate() + "' and f.date<='" + feedbackListDTO.getToDate()
                    + "'" + where1 + limit;

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                FeedbackDTO feedbackRequestDTO = new FeedbackDTO();
                feedbackRequestDTO.setId(resultSet.getInt("feedback_id"));
                feedbackRequestDTO.setQuestionId(resultSet.getInt("question_id"));
                feedbackRequestDTO.setAnswerId(resultSet.getInt("answer_id"));
                feedbackRequestDTO.setViewCount(resultSet.getInt("view_count"));
                feedbackRequestDTO.setIsPoor(resultSet.getInt("is_poor"));
                feedbackRequestDTO.setFeedbackDate(resultSet.getString("date"));
                feedbackRequestDTO.setCustomerName(resultSet.getString("customer_name"));
                feedbackRequestDTO.setMobileNo(resultSet.getString("customer_no"));
                feedbackRequestDTO.setEmail(resultSet.getString("customer_email"));
                feedbackRequestDTO.setDob(resultSet.getString("dob"));
                feedbackRequestDTO.setDoa(resultSet.getString("doa"));
                feedbackRequestDTO.setLocality(resultSet.getString("locality"));
                feedbackRequestDTO.setTableNo(resultSet.getString("table_no"));
                feedbackRequestDTO.setOutletDesc(resultSet.getString("outlet_name"));
                feedbackRequestDTO.setMgrName(resultSet.getString("manager_name"));
                feedbackRequestDTO.setRating(resultSet.getInt("rating"));
                feedbackRequestDTO.setAnswerText(resultSet.getString("answer_text"));
                feedbackRequestDTO.setQuestionDesc(resultSet.getString("question_desc"));
                feedbackRequestDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                feedbackRequestDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                feedbackRequestDTO.setMgrMobile(resultSet.getString("manager_mobile"));
                feedbackRequestDTO.setMgrEmail(resultSet.getString("manager_email"));
                if (resultSet.getTimestamp("first_view_date") == null) {
                    feedbackRequestDTO.setViewDate("");
                } else {
                    feedbackRequestDTO.setViewDate(DateUtil.getCurrentServerTimeByRemoteTimestamp(resultSet.getTimestamp("first_view_date")));
                }
                feedbackRequestDTO.setIsNegative(resultSet.getInt("isNegative"));
                feedbackRequestDTO.setIsAddressed(resultSet.getInt("isAddressed"));
                feedbackList.add(feedbackRequestDTO);

            }

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
               // connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return feedbackList;
    }

    public List<FeedbackDetails> getfeedback(int id) throws SQLException {
        Statement statement = null;
        Connection connection = null;
        List<FeedbackDetails> answerDTOS = new ArrayList<FeedbackDetails>();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            String query = "select f.created_on,f.question_id,f.answer_id,f.answer_text,f.rating,q.question_type,q.question_desc,a.answer_desc\n" +
                    " from feedback f\n" +
                    "left join feedback_head fh\n" +
                    "on f.feedback_id = fh.id\n" +
                    "left join question_bank q\n" +
                    "on f.question_id = q.id\n" +
                    "left join question_answer_link a\n" +
                    "on f.answer_id = a.answer_id\n" +
                    "where f.feedback_id=" + id;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                FeedbackDetails answerDTO = new FeedbackDetails();
                answerDTO.setAnswerId(resultSet.getInt("answer_id"));
                answerDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                answerDTO.setRating(resultSet.getInt("rating"));
                answerDTO.setQuestionId(resultSet.getInt("question_id"));
                answerDTO.setAnswerText(resultSet.getString("answer_text"));
                answerDTO.setQuestionDesc(resultSet.getString("question_desc"));
                answerDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                answerDTOS.add(answerDTO);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return answerDTOS;
    }

    public FeedbackRequestDTO getfeedbackById(int id) throws FeedbackNotFoundException {
        FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String query = "select f.feedback_id,f.question_id,f.rating,f.answer_text,f.answer_id, fh.date as feedback_date,a.weightage,q.question_type, q.question_desc, a.answer_desc,fh.outlet_id,o.outlet_desc ,fh.customer_id,c.name,c.phone_no,c.email_id,c.dob,c.doa,c.locality, fh.table_no,fh.bill_no\n" +
                    "from feedback f\n" +
                    "left join feedback_head fh on fh.id=f.feedback_id\n" +
                    "left join outlet o on fh.outlet_id = o.id\n" +
                    "left join question_bank q on q.id = f.question_id\n" +
                    "left join customer c on c.id = fh.customer_id\n" +
                    "left join question_answer_link a on a.answer_id = f.answer_id\n" +
                    "where fh.id=" + id;
            ResultSet resultSet = statement.executeQuery(query);
            int rowCount = 0;
            while (resultSet.next()) {

                feedbackRequestDTO.setId(resultSet.getInt("feedback_id"));
                feedbackRequestDTO.setFeedbackDate(DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("feedback_date")));
                feedbackRequestDTO.setCustomerId(resultSet.getInt("customer_id"));
                feedbackRequestDTO.setCustomerName(resultSet.getString("name"));
                feedbackRequestDTO.setMobileNo(resultSet.getString("phone_no"));
                feedbackRequestDTO.setEmail(resultSet.getString("email_id"));
                feedbackRequestDTO.setDob(resultSet.getString("dob"));
                feedbackRequestDTO.setDoa(resultSet.getString("doa"));
                feedbackRequestDTO.setLocality(resultSet.getString("locality"));
                feedbackRequestDTO.setOutletId(resultSet.getInt("outlet_id"));
                feedbackRequestDTO.setTableNo(resultSet.getString("table_no"));
                feedbackRequestDTO.setBillNo(resultSet.getString("bill_no"));
                feedbackRequestDTO.setOutletDesc(resultSet.getString("outlet_desc"));
                feedbackRequestDTO.setAnswerId(resultSet.getInt("answer_id"));
                feedbackRequestDTO.setRating(resultSet.getInt("rating"));
                feedbackRequestDTO.setQuestionId(resultSet.getInt("question_id"));
                feedbackRequestDTO.setAnswerText(resultSet.getString("answer_text"));
                feedbackRequestDTO.setQuestionDesc(resultSet.getString("question_desc"));
                feedbackRequestDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                feedbackRequestDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                feedbackRequestDTO.setWeightage(resultSet.getInt("weightage"));
                rowCount++;
            }
            if (rowCount == 0) {
                throw new FeedbackNotFoundException("Feedback id invalid");
            }


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return feedbackRequestDTO;
    }

    public List<FeedbackRequestDTO> getFeedbacksList(int id) throws SQLException, FeedbackNotFoundException {
        List<FeedbackRequestDTO> feedbackRequestDTOs = new ArrayList<FeedbackRequestDTO>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String query = "select f.feedback_id,f.question_id,f.rating,f.answer_text,f.answer_id,fh.device_id,a.threshold, fh.date as feedback_date,a.weightage,q.question_type, q.question_desc, a.answer_desc,fh.outlet_id,o.outlet_desc ,fh.customer_id,c.name,c.phone_no,c.email_id,c.dob,c.doa,c.locality, fh.table_no,fh.bill_no\n" +
                    "from feedback f\n" +
                    "left join feedback_head fh on fh.id=f.feedback_id\n" +
                    "left join outlet o on fh.outlet_id = o.id\n" +
                    "left join question_bank q on q.id = f.question_id\n" +
                    "left join customer c on c.id = fh.customer_id\n" +
                    "left join question_answer_link a on a.answer_id = f.answer_id\n" +
                    "where fh.id=" + id;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();
                feedbackRequestDTO.setId(resultSet.getInt("feedback_id"));
                feedbackRequestDTO.setFeedbackDate(DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("feedback_date")));
                feedbackRequestDTO.setCustomerId(resultSet.getInt("customer_id"));
                feedbackRequestDTO.setCustomerName(resultSet.getString("name"));
                feedbackRequestDTO.setMobileNo(resultSet.getString("phone_no"));
                feedbackRequestDTO.setEmail(resultSet.getString("email_id"));
                feedbackRequestDTO.setDob(resultSet.getString("dob"));
                feedbackRequestDTO.setDoa(resultSet.getString("doa"));
                feedbackRequestDTO.setLocality(resultSet.getString("locality"));
                feedbackRequestDTO.setOutletId(resultSet.getInt("outlet_id"));
                feedbackRequestDTO.setTableNo(resultSet.getString("table_no"));
                feedbackRequestDTO.setBillNo(resultSet.getString("bill_no"));
                feedbackRequestDTO.setOutletDesc(resultSet.getString("outlet_desc"));
                feedbackRequestDTO.setAnswerId(resultSet.getInt("answer_id"));
                feedbackRequestDTO.setRating(resultSet.getInt("rating"));
                feedbackRequestDTO.setQuestionId(resultSet.getInt("question_id"));
                feedbackRequestDTO.setAnswerText(resultSet.getString("answer_text"));
                feedbackRequestDTO.setQuestionDesc(resultSet.getString("question_desc"));
                feedbackRequestDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                feedbackRequestDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                feedbackRequestDTO.setWeightage(resultSet.getInt("weightage"));
                feedbackRequestDTO.setThreshold(resultSet.getString("threshold"));
                feedbackRequestDTO.setDeviceId(resultSet.getInt("device_id"));

                feedbackRequestDTOs.add(feedbackRequestDTO);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return feedbackRequestDTOs;
    }

    public List<CountDTO> getcountById(String date, int id) throws SQLException, QuestionNotFoundException {
        Connection connection = null;
        Statement statement = null;
        List<CountDTO> countDTOs = new ArrayList<CountDTO>();
        String table = "feedbacks_" + date;
        try {
            connection = new ConnectionHandler().getConnection();
            QuestionDAO.getQuestionById(id);
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "select coalesce(count(rating),0) as count,question_desc,question_type,rating,answer_desc from `" + table + "` \n" +
                            "where question_id=" + id + " and rating<>0\n" +
                            "group by answer_id,rating ");
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                CountDTO countDTO = new CountDTO();
                countDTO.setQuestionDesc(resultSet.getString("question_desc"));
                countDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                countDTO.setQuestionType(resultSet.getString("question_type"));
                countDTO.setRating(resultSet.getInt("rating"));
                countDTO.setCount(resultSet.getInt("count"));
                countDTOs.add(countDTO);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return countDTOs;
    }


    public List<AverageDTO> getaverageById(String date, int id) throws SQLException, QuestionNotFoundException {
        Connection connection = null;
        Statement statement = null;
        List<AverageDTO> averageDTOs = new ArrayList<AverageDTO>();
        String table = "feedbacks_" + date;
        try {
            connection = new ConnectionHandler().getConnection();
            QuestionDAO.getQuestionById(id);
            statement = connection.createStatement();

            StringBuilder query = new StringBuilder(
                    "select question_desc,question_type,rating,answer_desc,question_id,answer_id,coalesce(ROUND(avg(rating),0),0) as average from `" + table + "` \n" +
                            "where question_id=" + id + " and rating<>0\n" +
                            "group by answer_id ");
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                AverageDTO averageDTO = new AverageDTO();
                averageDTO.setQuestionDesc(resultSet.getString("question_desc"));
                averageDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                averageDTO.setAverage(resultSet.getFloat("average"));
                averageDTOs.add(averageDTO);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return averageDTOs;
    }


    public CustomerReportDTO getcustomerByPhoneNo(String date, String phoneNo) throws SQLException, CustomerNotFoundException {
        CustomerReportDTO customerReportDTO = new CustomerReportDTO();
        String table = "feedbacks_" + date;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            CustomerDAO.getValidationForPhoneNumber(phoneNo);
            StringBuilder query = new StringBuilder(
                    "select customer_name,customer_email,customer_no,dob,doa,locality from `" + table + "` \n" +
                            "where customer_no=\"" + phoneNo + "\"\n" +
                            "group by customer_no"
            );
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                customerReportDTO.setMobile(resultSet.getString("customer_no"));
                customerReportDTO.setName(resultSet.getString("customer_name"));
                customerReportDTO.setEmailId(resultSet.getString("customer_email"));
                customerReportDTO.setDob(resultSet.getString("dob"));
                customerReportDTO.setDoa(resultSet.getString("doa"));
                customerReportDTO.setLocality(resultSet.getString("locality"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customerReportDTO;
    }

    public static List<FeedbackDetails> getCustomerFeedbackDetail(String table, int id) throws SQLException {
        Statement statement = null;
        Connection connection = null;
        List<FeedbackDetails> answerDTOS = new ArrayList<FeedbackDetails>();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            String query = "select question_id,answer_id,answer_text,rating,question_type,question_desc,answer_desc\n" +
                    " from `" + table + "`\n" +
                    "where feedback_id=" + id;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                FeedbackDetails answerDTO = new FeedbackDetails();
                answerDTO.setAnswerId(resultSet.getInt("answer_id"));
                answerDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                answerDTO.setRating(resultSet.getInt("rating"));
                answerDTO.setQuestionId(resultSet.getInt("question_id"));
                answerDTO.setAnswerText(resultSet.getString("answer_text"));
                answerDTO.setQuestionDesc(resultSet.getString("question_desc"));
                answerDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                answerDTOS.add(answerDTO);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return answerDTOS;
    }

    public static List<Feedback> getcustomerFeedback(String date, String name) throws SQLException {
        Statement statement = null;
        Connection connection = null;
        List<Feedback> customerFeedbackDTOS = new ArrayList<Feedback>();
        String table = "feedbacks_" + date;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            String query = "select feedback_id,outlet_name,date,table_no  from `" + table + "` \n" +
                    "where customer_no='" + name + "'" + "group by feedback_id\n";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Feedback customerFeedbackDTO = new Feedback();
                customerFeedbackDTO.setId(resultSet.getInt("feedback_id"));
                customerFeedbackDTO.setOutletDesc(resultSet.getString("outlet_name"));
                customerFeedbackDTO.setTableNo(resultSet.getString("table_no"));
                customerFeedbackDTO.setFeedbackDate(resultSet.getString("date"));
                customerFeedbackDTO.setFeedbackDetail(FeedbackDAO.getCustomerFeedbackDetail(table, customerFeedbackDTO.getId()));
                customerFeedbackDTOS.add(customerFeedbackDTO);

            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customerFeedbackDTOS;
    }


    public Boolean createFeedbackTracking(FeedbackTrackingDTO feedbackTrackingDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO feedback_view_tracking(feedback_id,manager_mobile,manager_name,manager_email) values (?,?,?,?)");
        Boolean isCreate = Boolean.FALSE;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++, feedbackTrackingDTO.getFeedbackId());
            preparedStatement.setString(parameterIndex++, feedbackTrackingDTO.getManagerMobile());
            preparedStatement.setString(parameterIndex++, feedbackTrackingDTO.getManagerName());
            preparedStatement.setString(parameterIndex++, feedbackTrackingDTO.getManagerEmail());


            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                isCreate = Boolean.TRUE;
                connection.commit();
            } else {
                connection.rollback();
            }

        } catch (SQLException sqlException) {
            connection.rollback();
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isCreate;
    }

    public FeedbackTrackingDTO getcustomer(int feedbackId) throws SQLException {

        FeedbackTrackingDTO feedbackTrackingDTO = new FeedbackTrackingDTO();
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "select * from feedback_view_tracking where feedback_id=").append(feedbackId);

            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                feedbackTrackingDTO.setFeedbackId(resultSet.getInt("feedback_id"));
                feedbackTrackingDTO.setManagerName(resultSet.getString("manager_name"));
                feedbackTrackingDTO.setManagerMobile(resultSet.getString("manager_mobile"));
                feedbackTrackingDTO.setManagerEmail(resultSet.getString("manager_email"));
                feedbackTrackingDTO.setFirstViewDate(DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("first_view_date")));
                feedbackTrackingDTO.setViewCount(resultSet.getInt("view_count"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return feedbackTrackingDTO;
    }


    public Boolean updateFeedbackTracking(FeedbackTrackingDTO feedbackTrackingDTO) throws SQLException {
        boolean isUpdate = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE feedback_view_tracking SET manager_name=?,manager_email=?,manager_mobile=?,view_count =? WHERE feedback_id =? ");

            FeedbackTrackingDTO feedbackTrackingDTO1 = getcustomer(feedbackTrackingDTO.getFeedbackId());

            preparedStatement.setString(parameterIndex++, feedbackTrackingDTO.getManagerName());
            preparedStatement.setString(parameterIndex++, feedbackTrackingDTO.getManagerEmail());
            preparedStatement.setString(parameterIndex++, feedbackTrackingDTO.getManagerMobile());
            int count = feedbackTrackingDTO1.getViewCount();
            preparedStatement.setInt(parameterIndex++, count + 1);
            preparedStatement.setInt(parameterIndex++, feedbackTrackingDTO.getFeedbackId());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
                isUpdate = Boolean.TRUE;
            } else {
                connection.rollback();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isUpdate;
    }


    public List<FeedbackTrackingResponseDTO> getFeedbackTrackingList(Connection connection, String where1, String limit, FeedbackListRequestBO feedbackListRequestBO, int isNegative, String date) throws SQLException, UserNotFoundException {
        List<FeedbackTrackingResponseDTO> trackingDTOList = new ArrayList<FeedbackTrackingResponseDTO>();
        Statement statement = null;
        String where = "", table = "feedbacks_" + date;
        try {

            statement = connection.createStatement();

            if (isNegative == 1) {
                where += " and isNegative=1\n";
            }

            String query = "select f.feedback_id,f.outlet_id,f.outlet_name,f.date,f.table_no,f.customer_name,f.isNegative" +
                    ",f.customer_no,f.customer_email,t.manager_name,t.manager_mobile,t.manager_email,t.feedback_id as isAddressed" +
                    ",t.view_count,t.first_view_date from `" + table + "` f\n" +
                    "left join feedback_view_tracking t on f.feedback_id = t.feedback_id\n" +
                    "where f.date>='" + feedbackListRequestBO.getFromDate() + "' and f.date<='"
                    + feedbackListRequestBO.getToDate() + "'" + where1 + where + "group by f.feedback_id\n" + limit;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                FeedbackTrackingResponseDTO feedbackTrackingResponseDTO = new FeedbackTrackingResponseDTO();
                feedbackTrackingResponseDTO.setFeedbackId(resultSet.getInt("feedback_id"));
                feedbackTrackingResponseDTO.setOutletId(resultSet.getInt("outlet_id"));
                feedbackTrackingResponseDTO.setOutletName(resultSet.getString("outlet_name"));
                feedbackTrackingResponseDTO.setDate(resultSet.getString("date"));
                feedbackTrackingResponseDTO.setTableNo(resultSet.getString("table_no"));
                feedbackTrackingResponseDTO.setCustomerName(resultSet.getString("customer_name"));
                feedbackTrackingResponseDTO.setEmail(resultSet.getString("customer_email"));
                feedbackTrackingResponseDTO.setPhoneNo(resultSet.getString("customer_no"));
                feedbackTrackingResponseDTO.setMgrName(resultSet.getString("manager_name"));
                feedbackTrackingResponseDTO.setMgrMobileNo(resultSet.getString("manager_mobile"));
                feedbackTrackingResponseDTO.setMgrEmail(resultSet.getString("manager_email"));
                if (resultSet.getTimestamp("first_view_date") == null) {
                    feedbackTrackingResponseDTO.setFistViewDate("");
                } else {
                    feedbackTrackingResponseDTO.setFistViewDate(DateUtil.getCurrentServerTimeByRemoteTimestamp(resultSet.getTimestamp("first_view_date")));
                }
                feedbackTrackingResponseDTO.setViewCount(resultSet.getInt("view_count"));
                feedbackTrackingResponseDTO.setIsAddressed(resultSet.getInt("isAddressed"));
                feedbackTrackingResponseDTO.setIsNegative(resultSet.getInt("isNegative"));
                trackingDTOList.add(feedbackTrackingResponseDTO);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return trackingDTOList;
    }

    public static void updateFeedback(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("UPDATE feedback_head SET isNegative =1 WHERE id =?");

            preparedStatement.setInt(parameterIndex++, id);


            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ReportDTO getDailyReport(String date, String outlets, String from, String to) throws SQLException {
        ReportDTO reportDTO = new ReportDTO();
        String table = "feedbacks_" + date;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection
                    ();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "select  sum(tab.total_count)as total_count,sum(tab.negative_count)as negative_count,\n" +
                            " sum(tab.addressed_count)as addressed_count,sum(tab.daily_bill_count)as daily_bill,sum(tab.monthly_bill_count)as monthly_bill from \n" +
                            "(SELECT o.id,COUNT(fm.feedback_id) as total_count,coalesce(sum(fm.isNegative=1),0) as negative_count,\n" +
                            "count(t.feedback_id) as addressed_count\n" +
                            ",o.outlet_desc,o.daily_bill_count, o.monthly_bill_count from (select cluster_id,outlet_desc,daily_bill_count, monthly_bill_count,id from outlet where id in(" + outlets + ")) as o\n" +
                            "left join (select f.feedback_id, f.isNegative, f.outlet_id from `" + table + "` f  where f.date >='" + from + "' and f.date <= '" + to + "' group by f.feedback_id) fm on o.id = fm.outlet_id\n" +
                            "left join feedback_view_tracking t on fm.feedback_id = t.feedback_id\n" +
                            "group by o.id) tab\n");
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                reportDTO.setTotalCount(resultSet.getInt("total_count"));
                reportDTO.setNegativeCount(resultSet.getInt("negative_count"));
                reportDTO.setUnAddressedCount(reportDTO.getNegativeCount() - resultSet.getInt("addressed_count"));
                reportDTO.setDailyBillCount(resultSet.getInt("daily_bill"));
                reportDTO.setMonthlyBillCount(resultSet.getInt("monthly_bill"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reportDTO;
    }

    public List<ReportData> getOutletReport(String date, String outlets, String from, String to) throws SQLException {
        List<ReportData> reports = new ArrayList<ReportData>();
        String table = "feedbacks_" + date;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "SELECT o.id,COUNT(fm.feedback_id) as total_count,coalesce(sum(fm.isNegative=1),0) as negative_count,\n" +
                            "count(t.feedback_id) as addressed_count\n" +
                            ",c.cluster_desc,o.outlet_desc,o.daily_bill_count, o.monthly_bill_count from (select cluster_id,outlet_desc,daily_bill_count, monthly_bill_count,id from outlet where id in(" + outlets + ")) as o\n" +
                            "join cluster c on c.id=o.cluster_id\n" +
                            "left join (select f.feedback_id, f.isNegative, f.outlet_id from `" + table + "` f  where f.date >='" + from + "' and f.date <= '" + to + "' group by f.feedback_id) fm on o.id = fm.outlet_id\n" +
                            "left join feedback_view_tracking t on fm.feedback_id = t.feedback_id\n" +
                            "group by o.id\n" +
                            "order by negative_count desc");
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                ReportData report = new ReportData();
                report.setTotalCount(resultSet.getInt("total_count"));
                report.setStoreId(resultSet.getString("outlet_desc"));
                report.setCity(resultSet.getString("cluster_desc"));
                report.setNegativeCount(resultSet.getInt("negative_count"));
                report.setUnAddressedCount(report.getNegativeCount() - resultSet.getInt("addressed_count"));
                report.setDailyBillCount(resultSet.getInt("daily_bill_count"));
                report.setMonthlyBillCount(resultSet.getInt("monthly_bill_count"));
                reports.add(report);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reports;
    }

    public List<FeedbackDTO> getArchiveData(int id) throws SQLException {

        List<FeedbackDTO> feedbackList = new ArrayList<FeedbackDTO>();
        Connection connection = null;
        Statement statement = null;

        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String query = "select o.id,f.answer_id,f.feedback_id,f.rating,f.answer_text,fh.date," +
                    "q.id as question_id,q.question_type, q.question_desc,a.weightage,a.rating as answer_rating,a.threshold, a.answer_desc,o.outlet_desc," +
                    "c.name,c.phone_no,c.email_id,c.dob,c.doa,c.locality, fh.table_no\n" +
                    ",fh.isNegative\n" +
                    "from feedback f\n" +
                    "left join feedback_head fh on fh.id=f.feedback_id\n" +
                    "left join outlet o on fh.outlet_id = o.id\n" +
                    "left join question_bank q on q.id = f.question_id\n" +
                    "left join customer c on c.id = fh.customer_id\n" +
                    "left join question_answer_link a on a.answer_id = f.answer_id\n" +
                    "where fh.id > " + id;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                FeedbackDTO feedbackRequestDTO = new FeedbackDTO();
                feedbackRequestDTO.setId(resultSet.getInt("feedback_id"));
                feedbackRequestDTO.setOutletId(resultSet.getInt("id"));
                feedbackRequestDTO.setAnswerId(resultSet.getInt("answer_id"));
                feedbackRequestDTO.setQuestionId(resultSet.getInt("question_id"));
                feedbackRequestDTO.setWeightage(resultSet.getInt("weightage"));
                feedbackRequestDTO.setAnsRating(resultSet.getInt("answer_rating"));
                feedbackRequestDTO.setFeedbackDate(DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("date")));
                feedbackRequestDTO.setCustomerName(resultSet.getString("name"));
                feedbackRequestDTO.setMobileNo(resultSet.getString("phone_no"));
                feedbackRequestDTO.setEmail(resultSet.getString("email_id"));
                feedbackRequestDTO.setDob(resultSet.getString("dob"));
                feedbackRequestDTO.setDoa(resultSet.getString("doa"));
                feedbackRequestDTO.setLocality(resultSet.getString("locality"));
                feedbackRequestDTO.setThreshold(resultSet.getString("threshold"));
                feedbackRequestDTO.setTableNo(resultSet.getString("table_no"));
                feedbackRequestDTO.setOutletDesc(resultSet.getString("outlet_desc"));
                feedbackRequestDTO.setRating(resultSet.getInt("rating"));
                feedbackRequestDTO.setAnswerText(resultSet.getString("answer_text"));
                feedbackRequestDTO.setQuestionDesc(resultSet.getString("question_desc"));
                feedbackRequestDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                feedbackRequestDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                feedbackRequestDTO.setIsNegative(resultSet.getInt("isNegative"));
                feedbackList.add(feedbackRequestDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return feedbackList;
    }

    public static Boolean getRequest(String param) throws SQLException {
        Boolean isExist = Boolean.FALSE;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer("select * from request where status='A' and json='" + param + "'");

            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                isExist = Boolean.TRUE;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isExist;
    }

    public List<RequestDto> getRequests() throws Exception {
        List<RequestDto> requests = new ArrayList<RequestDto>();
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer("select * from request where status='A'");

            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                JSONObject jsonObject = new JSONObject(resultSet.getString("json"));
                RequestDto requestDto = new RequestDto();
                requestDto.setId(resultSet.getInt("id"));
                requestDto.setFromDate(jsonObject.getString("fromDate"));
                requestDto.setToDate(jsonObject.getString("toDate"));
                JSONArray array = jsonObject.getJSONArray("outletId");
                List<Integer> nums = new ArrayList<Integer>();
                for (int i = 0; i < array.length(); i++) {
                    nums.add(array.getInt(i));
                }
                requestDto.setOutlets(CommaSeparatedString.generate(nums));
                requestDto.setTable(jsonObject.getString("tableNo"));
                requestDto.setUserId(jsonObject.getInt("userId"));
                requestDto.setToken(resultSet.getString("token"));
                requests.add(requestDto);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return requests;
    }


    public Boolean createRequest(JSONObject json,String token) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO request(json,token) values (?,?)");
        Boolean isCreate = Boolean.FALSE;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setString(parameterIndex++, json.toString());
            preparedStatement.setString(parameterIndex++, token);

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                isCreate = Boolean.TRUE;
                connection.commit();
            } else {
                connection.rollback();
            }

        } catch (SQLException sqlException) {
            connection.rollback();
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isCreate;
    }

    public void updateRequest(int id,String date) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("UPDATE request SET status ='I',expire_date=? WHERE id =?");


            preparedStatement.setString(parameterIndex++,date);
            preparedStatement.setInt(parameterIndex++, id);


            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Integer> getExpiredRequests(String currentDate) throws SQLException {
        List<Integer> ids = new ArrayList<Integer>();
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer("select id from request where expire_date='"+currentDate+"'");

            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                ids.add(id);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ids;
    }

    public int getReqByToken(String token) throws SQLException {
        int id=0;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer("select id from request where token='"+token+"'");

            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                 id = resultSet.getInt("id");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }
}