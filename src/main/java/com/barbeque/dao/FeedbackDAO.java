package com.barbeque.dao;

import com.barbeque.dto.request.AnswerDTO;
import com.barbeque.dto.request.FeedbackRequestDTO;
import com.barbeque.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            StringBuilder query = new StringBuilder("INSERT INTO feedback_head( outlet_id, date, customer_id, table_no, bill_no, modified_on");
            query.append(")values (?,?,?,?,?,?)");

            preparedStatement = connection.prepareStatement(query.toString());

            preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getOutletId());

            Timestamp date = DateUtil.getTimeStampFromString(feedbackRequestDTO.getDate());
            preparedStatement.setTimestamp(parameterIndex++, date);

            preparedStatement.setInt(parameterIndex++, customerId);

            preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getTableNo());

            preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getBillNo());

            java.util.Date date1 = new java.util.Date();
            Timestamp t1 = new Timestamp(date1.getTime());
            String updated_date = DateUtil.getDateStringFromTimeStamp(t1);
            preparedStatement.setString(parameterIndex++, updated_date);


            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }

            try {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException(
                            "Creating feedback failed, no ID obtained.");
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


    public int createFeedbackDetail(int feedback_id, int question_id, int answer_id, String answer_text, int rating) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO feedback(feedback_id , modifiede_on, question_id, answer_id, answer_text, rating) values (?,?,?,?,?,?)");
        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    feedback_id);
            java.util.Date date1 = new java.util.Date();
            Timestamp t1 = new Timestamp(date1.getTime());
            String updated_date = DateUtil.getDateStringFromTimeStamp(t1);
            preparedStatement.setString(parameterIndex++, updated_date);
            preparedStatement.setInt(parameterIndex++,
                    question_id);
            preparedStatement.setInt(parameterIndex++,
                    answer_id);
            preparedStatement.setString(parameterIndex++,
                    answer_text);
            preparedStatement.setInt(parameterIndex++,
                    rating);

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
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
        return id;
    }

    public Boolean updateQuestion(FeedbackRequestDTO feedbackRequestDTO)throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE feedback_head SET date=?, customer_id=?, table_no=?, bill_no=?, modified_on=? WHERE feedback_id =?");

            preparedStatement.setString(parameterIndex++, String.valueOf(feedbackRequestDTO.getDate()));

            preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getCustomerId());

            preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getTableNo());

            preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getBillNo());

            java.util.Date date1 = new java.util.Date();
            Timestamp t1 = new Timestamp(date1.getTime());
            String updated_date = DateUtil.getDateStringFromTimeStamp(t1);
            preparedStatement.setString(parameterIndex++, updated_date);

            preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getId());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
                isCreated = Boolean.TRUE;
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
        return isCreated;
    }

    public List<FeedbackRequestDTO> getfeedbackList() throws SQLException {
        List<FeedbackRequestDTO> feedbackList = new ArrayList<FeedbackRequestDTO>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String query = "select f.*, q.question_desc, a.answer_desc,fh.outlet_id,o.outlet_desc,fh.created_on as createDate,fh.modified_on as modifyDate,fh.customer_id,c.name,c.phone_no,fh.table_no,fh.bill_no\n" +
                    "from feedback f\n" +
                    "left join feedback_head fh on fh.id=f.feedback_id\n" +
                    "left join outlet o on fh.outlet_id = o.id\n" +
                    "left join question_bank q on q.id = f.question_id\n" +
                    "left join customer c on c.id = fh.customer_id\n" +
                    "left join question_answer_link a on a.answer_id = f.answer_id\n" ;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();
                feedbackRequestDTO.setId(resultSet.getInt("feedback_id"));
                feedbackRequestDTO.setCustomerId(resultSet.getInt("customer_id"));
                String createDate = DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("createDate"));
                feedbackRequestDTO.setCreatedOn(createDate);
                String modifyDate = DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("modifyDate"));
                feedbackRequestDTO.setOutletId(resultSet.getInt("outlet_id"));
                feedbackRequestDTO.setTableNo(resultSet.getString("table_no"));
                feedbackRequestDTO.setBillNo(resultSet.getString("bill_no"));
                feedbackRequestDTO.setCustomerName(resultSet.getString("name"));
                feedbackRequestDTO.setMobileNo(resultSet.getString("phone_no"));
                feedbackRequestDTO.setModifiedOn(modifyDate);
                feedbackRequestDTO.setOutletDesc(resultSet.getString("outlet_desc"));
                feedbackRequestDTO.setAnswerId(resultSet.getInt("answer_id"));
                feedbackRequestDTO.setRating(resultSet.getInt("rating"));
                feedbackRequestDTO.setQuestionId(resultSet.getInt("question_id"));
                feedbackRequestDTO.setAnswerText(resultSet.getString("answer_text"));
                feedbackRequestDTO.setQuestionDesc(resultSet.getString("question_desc"));
                feedbackRequestDTO.setAnswerDesc(resultSet.getString("answer_desc"));
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

    public List<AnswerDTO>getfeedback()throws SQLException {
        Connection connection = null;
        Statement statement = null;
        List<AnswerDTO> answerDTOS = new ArrayList<AnswerDTO>();
        try {

            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String query = "select f.question_id,f.answer_id,f.answer_text,f.rating,q.question_desc,a.answer_desc\n" +
                    " from feedback f\n" +
                    "left join feedback_head fh\n" +
                    "on f.feedback_id = fh.id\n" +
                    "left join question_bank q\n" +
                    "on f.question_id = q.id\n" +
                    "left join question_answer_link a\n" +
                    "on f.answer_id = a.answer_id";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                AnswerDTO answerDTO = new AnswerDTO();

                answerDTO.setRating(resultSet.getInt("rating"));
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
        return  answerDTOS;
    }
}
