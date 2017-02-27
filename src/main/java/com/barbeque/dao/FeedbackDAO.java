package com.barbeque.dao;

import com.barbeque.bo.FeedbackListRequestBO;
import com.barbeque.dao.customer.CustomerDAO;
import com.barbeque.dao.user.UsersDAO;
import com.barbeque.dto.request.*;
import com.barbeque.dto.response.LoginResponseDTO;
import com.barbeque.exceptions.CustomerNotFoundException;
import com.barbeque.exceptions.FeedbackNotFoundException;

import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.request.report.Feedback;
import com.barbeque.util.DateUtil;

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
                    id = 0;
                    connection.rollback();
                    throw new SQLException(
                            "Creating feedback failed, no ID obtained.");
                }
            } catch (SQLException e) {
                id = 0;
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException sqlException) {
            id = 0;
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

    public List<FeedbackRequestDTO> getfeedbackList1(FeedbackListDTO feedbackListDTO) throws SQLException, UserNotFoundException {
        List<FeedbackRequestDTO> feedbackList = new ArrayList<FeedbackRequestDTO>();
        Connection connection = null;
        Statement statement = null;
        String where1 = "", ids = "";
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            if (feedbackListDTO.getTableNo() != null && !feedbackListDTO.getTableNo().equals("")) {
                where1 = " and fh.table_no=\"" + feedbackListDTO.getTableNo() + "\"";
            }
            if (feedbackListDTO.getOutletId() != null && feedbackListDTO.getOutletId().size() > 0) {
                int i = 1;
                for (Integer id : feedbackListDTO.getOutletId()) {
                    if (i == 1) {
                        ids = String.valueOf(id);
                    } else {
                        ids = ids + "," + String.valueOf(id);
                    }
                    i++;
                }
                where1 += " and fh.outlet_id IN(" + ids + ")";
            }
            if (feedbackListDTO.getOutletId() == null || feedbackListDTO.getOutletId().size() == 0) {
                LoginResponseDTO loginResponseDTO = UsersDAO.getuserById(feedbackListDTO.getUserId());
                RoleRequestDTO rollRequestDTO = UsersDAO.getroleById(loginResponseDTO.getRoleId());
                where1 += " and fh.outlet_id IN(" + rollRequestDTO.getOutletAccess() + ")";
            }
            String query = "select f.*,fh.date as feedback_date,a.weightage,q.question_type, q.question_desc, a.answer_desc,fh.outlet_id,o.outlet_desc ,fh.customer_id,c.name,c.phone_no,c.email_id,c.dob,c.doa,c.locality, fh.table_no,fh.bill_no\n" +
                    "from feedback f\n" +
                    "left join feedback_head fh on fh.id=f.feedback_id\n" +
                    "left join outlet o on fh.outlet_id = o.id\n" +
                    "left join question_bank q on q.id = f.question_id\n" +
                    "left join customer c on c.id = fh.customer_id\n" +
                    "left join question_answer_link a on a.answer_id = f.answer_id\n" +
                    "where fh.date >= '" + feedbackListDTO.getFromDate() + "' AND fh.date <='" +
                    feedbackListDTO.getToDate() + "'" + where1;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();
                feedbackRequestDTO.setId(resultSet.getInt("feedback_id"));
                feedbackRequestDTO.setFeedbackDate(resultSet.getTimestamp("feedback_date"));
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
                feedbackList.add(feedbackRequestDTO);
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
        return feedbackList;
    }

    public static List<FeedbackDetails> getfeedback(int id) throws SQLException {
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
                answerDTO.setFeedbackDate(DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("created_on")));
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

    public static FeedbackRequestDTO getfeedbackById(int id) throws FeedbackNotFoundException {
        FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String query = "select f.*, fh.date as feedback_date,a.weightage,q.question_type, q.question_desc, a.answer_desc,fh.outlet_id,o.outlet_desc ,fh.customer_id,c.name,c.phone_no,c.email_id,c.dob,c.doa,c.locality, fh.table_no,fh.bill_no\n" +
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
                feedbackRequestDTO.setFeedbackDate(resultSet.getTimestamp("feedback_date"));
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

    public static List<FeedbackRequestDTO> getFeedbacksList(int id) throws SQLException, FeedbackNotFoundException {
        List<FeedbackRequestDTO> feedbackRequestDTOs = new ArrayList<FeedbackRequestDTO>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String query = "select f.*,fh.device_id,a.threshold, fh.date as feedback_date,a.weightage,q.question_type, q.question_desc, a.answer_desc,fh.outlet_id,o.outlet_desc ,fh.customer_id,c.name,c.phone_no,c.email_id,c.dob,c.doa,c.locality, fh.table_no,fh.bill_no\n" +
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
                FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();
                feedbackRequestDTO.setId(resultSet.getInt("feedback_id"));
                feedbackRequestDTO.setFeedbackDate(resultSet.getTimestamp("feedback_date"));
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
                rowCount++;
                feedbackRequestDTOs.add(feedbackRequestDTO);
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
        return feedbackRequestDTOs;
    }

    public static List<CountDTO> getcountById(int id) throws SQLException, QuestionNotFoundException {

        Connection connection = null;
        Statement statement = null;
        List<CountDTO> countDTOs = new ArrayList<CountDTO>();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "select q.question_desc,q.question_type,a.weightage,f.rating,a.answer_desc,f.question_id,f.answer_id,coalesce(count(f.rating),0) as count from feedback f\n" +
                            "join question_bank q\n" +
                            "on f.question_id=q.id\n" +
                            "join question_answer_link a\n" +
                            "on f.answer_id=a.answer_id\n" +
                            "where f.question_id=" + id + " and f.rating<>0\n" +
                            "group by f.answer_id,f.rating ");
            ResultSet resultSet = statement.executeQuery(query.toString());
            int rowCount = 0;
            while (resultSet.next()) {
                CountDTO countDTO = new CountDTO();
                countDTO.setQuestionDesc(resultSet.getString("question_desc"));
                countDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                countDTO.setQuestionType(resultSet.getString("question_type"));
                countDTO.setCount(resultSet.getInt("count"));
                countDTOs.add(countDTO);
                rowCount++;
            }
            if (rowCount == 0) {
                throw new QuestionNotFoundException("Question id invalid");
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


    public List<AverageDTO> getaverageById(int id) throws SQLException, QuestionNotFoundException {
        Connection connection = null;
        Statement statement = null;
        List<AverageDTO> averageDTOs = new ArrayList<AverageDTO>();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "select q.question_desc,q.question_type,a.weightage,a.answer_desc,f.question_id,f.answer_id,coalesce(ROUND(avg(f.rating),0),0) as average from feedback f\n" +
                            "join question_bank q\n" +
                            "on f.question_id=q.id\n" +
                            "join question_answer_link a\n" +
                            "on f.answer_id=a.answer_id\n" +
                            "where f.question_id=" + id + " and f.rating<>0\n" +
                            "group by f.answer_id;"
            );
            ResultSet resultSet = statement.executeQuery(query.toString());
            int rowCount = 0;
            while (resultSet.next()) {
                AverageDTO averageDTO = new AverageDTO();
                averageDTO.setQuestionDesc(resultSet.getString("question_desc"));
                averageDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                averageDTO.setAverage(resultSet.getFloat("average"));
                averageDTOs.add(averageDTO);
                rowCount++;
            }

            if (rowCount == 0) {
                throw new QuestionNotFoundException("Question id invalid");
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


    public static CustomerReportDTO getcustomerByPhoneNo(String phoneNo) throws SQLException, CustomerNotFoundException {
        CustomerReportDTO customerReportDTO = new CustomerReportDTO();

        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            CustomerDAO.getValidationForPhoneNumber(phoneNo);
            StringBuilder query = new StringBuilder(
                    "select c.id,c.name,c.email_id,c.dob,c.doa,c.locality,f.id as feedback_id,fh.customer_id from customer c\n" +
                            "left join feedback_head fh on fh.customer_id = c.id\n" +
                            "left join feedback f on f.feedback_id=fh.id\n" +
                            "where c.phone_no=\"" + phoneNo + "\"\n" +
                            "group by f.feedback_id"
            );
            ResultSet resultSet = statement.executeQuery(query.toString());
            int rowCount = 0;
            while (resultSet.next()) {
                customerReportDTO.setId(resultSet.getInt("id"));
                customerReportDTO.setName(resultSet.getString("name"));
                customerReportDTO.setEmailId(resultSet.getString("email_id"));
                customerReportDTO.setDob(resultSet.getString("dob"));
                customerReportDTO.setDoa(resultSet.getString("doa"));
                customerReportDTO.setLocality(resultSet.getString("locality"));

                rowCount++;
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

    public static List<FeedbackDetails> getCustomerFeedbackDetail(int id) throws SQLException {
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
                answerDTO.setFeedbackDate(DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("created_on")));
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

    public static List<Feedback> getcustomerFeedback(int id) throws SQLException {
        Statement statement = null;
        Connection connection = null;
        List<Feedback> customerFeedbackDTOS = new ArrayList<Feedback>();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            String query = "select f.id,o.outlet_desc,f.date,f.table_no from feedback_head f\n" +
                    "join outlet o on f.outlet_id =o.id\n" +
                    "where f.id=" + id;

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Feedback customerFeedbackDTO = new Feedback();
                customerFeedbackDTO.setId(resultSet.getInt("id"));
                customerFeedbackDTO.setOutletDesc(resultSet.getString("outlet_desc"));
                customerFeedbackDTO.setTableNo(resultSet.getString("table_no"));
                customerFeedbackDTO.setFeedbackDate(DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("date")));
                customerFeedbackDTO.setFeedbackDetail(FeedbackDAO.getCustomerFeedbackDetail(customerFeedbackDTO.getId()));

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


    public static Boolean createFeedbackTracking(FeedbackTrackingDTO feedbackTrackingDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO feedback_view_tracking(feedback_id,manager_mobile,manager_name,manager_email) values (?,?,?,?)");
        Boolean isCreate = Boolean.FALSE;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    feedbackTrackingDTO.getFeedbackId());
            preparedStatement.setString(parameterIndex++,
                    feedbackTrackingDTO.getManagerMobile());
            preparedStatement.setString(parameterIndex++,
                    feedbackTrackingDTO.getManagerName());
            preparedStatement.setString(parameterIndex++,
                    feedbackTrackingDTO.getManagerEmail());


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

    public static FeedbackTrackingDTO getcustomer(String managerMobile, int feedbackId)
            throws SQLException {

        FeedbackTrackingDTO feedbackTrackingDTO = new FeedbackTrackingDTO();
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "select * from feedback_view_tracking where manager_mobile = \"").append(managerMobile)
                    .append("\" and feedback_id=").append(feedbackId);

            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
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


    public static Boolean updateFeedbackTracking(FeedbackTrackingDTO feedbackTrackingDTO) throws SQLException {
        boolean isUpdate = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE feedback_view_tracking SET view_count =? " +
                            "WHERE feedback_id =? and manager_mobile=?");

            FeedbackTrackingDTO feedbackTrackingDTO1 = getcustomer(feedbackTrackingDTO.getManagerMobile(), feedbackTrackingDTO.getFeedbackId());

            int count = feedbackTrackingDTO1.getViewCount();
            preparedStatement.setInt(parameterIndex++, count + 1);


            preparedStatement.setInt(parameterIndex++, feedbackTrackingDTO.getFeedbackId());
            preparedStatement.setString(parameterIndex++, feedbackTrackingDTO.getManagerMobile());


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


    public List<FeedbackTrackingResponseDTO> getFeedbackTrackingList(FeedbackListRequestBO feedbackListRequestBO) throws SQLException, UserNotFoundException {
        List<FeedbackTrackingResponseDTO> trackingDTOList = new ArrayList<FeedbackTrackingResponseDTO>();
        Connection connection = null;
        Statement statement = null;
        String where1 = "", ids = "";
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            if (feedbackListRequestBO.getTableNo() != null && !feedbackListRequestBO.getTableNo().equals("")) {
                where1 = " and f.table_no=\"" + feedbackListRequestBO.getTableNo() + "\"";
            }
            if (feedbackListRequestBO.getOutletId() != null && feedbackListRequestBO.getOutletId().size() > 0) {
                int i = 1;
                for (Integer id : feedbackListRequestBO.getOutletId()) {
                    if (i == 1) {
                        ids = String.valueOf(id);
                    } else {
                        ids = ids + "," + String.valueOf(id);
                    }
                    i++;
                }
                where1 += " and f.outlet_id IN(" + ids + ")";
            }
            if (feedbackListRequestBO.getOutletId() == null || feedbackListRequestBO.getOutletId().size() == 0) {
                LoginResponseDTO loginResponseDTO = UsersDAO.getuserById(feedbackListRequestBO.getUserId());
                RoleRequestDTO rollRequestDTO = UsersDAO.getroleById(loginResponseDTO.getRoleId());
                where1 += " and f.outlet_id IN(" + rollRequestDTO.getOutletAccess() + ")";
            }

            String query = "select f.id,ft.feedback_id,f.outlet_id,f.date,f.table_no,f.customer_id,os.mgr_name,os.mgr_mobile,os.mgr_email,ft.first_view_date,COALESCE(ft.view_count,0) as view_count,\n" +
                    "c.name as custoner_name,c.phone_no,o.outlet_desc,\n" +
                    "(select count(feedback_id) from feedback_view_tracking t where t.feedback_id=f.id) as isAddressed\n" +
                    "from feedback_head f\n" +
                    "left join feedback_view_tracking ft on ft.feedback_id = f.id\n" +
                    "left join customer c on f.customer_id = c.id\n" +
                    "left join outlet o on f.outlet_id = o.id\n" +
                    "left join outlet_setting os on f.outlet_id = os.outlet_id\n" +
                    "where f.isNegative=1 and f.date >= '" + feedbackListRequestBO.getFromDate() + "' AND f.date <='" +
                    feedbackListRequestBO.getToDate() + "'" + where1;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                FeedbackTrackingResponseDTO feedbackTrackingResponseDTO = new FeedbackTrackingResponseDTO();
                feedbackTrackingResponseDTO.setFeedbackId(resultSet.getInt("id"));
                feedbackTrackingResponseDTO.setOutletId(resultSet.getInt("outlet_id"));
                feedbackTrackingResponseDTO.setOutletName(resultSet.getString("outlet_desc"));
                feedbackTrackingResponseDTO.setDate(DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("date")));
                feedbackTrackingResponseDTO.setTableNo(resultSet.getString("table_no"));
                feedbackTrackingResponseDTO.setCustomerId(resultSet.getInt("customer_id"));
                feedbackTrackingResponseDTO.setCustomerName(resultSet.getString("custoner_name"));
                feedbackTrackingResponseDTO.setPhoneNo(resultSet.getString("phone_no"));
                feedbackTrackingResponseDTO.setMgrName(resultSet.getString("mgr_name"));
                feedbackTrackingResponseDTO.setMgrMobileNo(resultSet.getString("mgr_mobile"));
                feedbackTrackingResponseDTO.setMgrEmail(resultSet.getString("mgr_email"));
                if(resultSet.getTimestamp("first_view_date") == null){
                    feedbackTrackingResponseDTO.setFistViewDate("");
                }else {
                    feedbackTrackingResponseDTO.setFistViewDate(DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("first_view_date")));
                }
                feedbackTrackingResponseDTO.setViewCount(resultSet.getInt("view_count"));
                feedbackTrackingResponseDTO.setIsAddressed(resultSet.getInt("isAddressed"));


                trackingDTOList.add(feedbackTrackingResponseDTO);
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
        return trackingDTOList;
    }

    public static void updateFeedback(int id)  throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE feedback_head SET isNegative =1 WHERE id =?");


            preparedStatement.setInt(parameterIndex++,id);


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

}
