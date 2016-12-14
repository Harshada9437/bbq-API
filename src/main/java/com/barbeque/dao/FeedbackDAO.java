package com.barbeque.dao;

import com.barbeque.dto.request.FeedbackRequestDTO;
import com.barbeque.util.DateUtil;

import java.sql.*;

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
            StringBuilder query = new StringBuilder("INSERT INTO feedback( outlet_id, date, customer_id, question_id, answer_id, answer_text, rating, table_no, bill_no, modified_on");
            query.append(")values (?,?,?,?,?,?,?,?,?,?)");

            preparedStatement = connection.prepareStatement(query.toString());

            preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getOutletId());

            Timestamp date = DateUtil.getTimeStampFromString(feedbackRequestDTO.getDate());
            preparedStatement.setTimestamp(parameterIndex++, date);

            preparedStatement.setInt(parameterIndex++, customerId);

            preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getQuestionId());

            preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getAnswerId());

            preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getAnswerText());

            preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getRating());

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
}
