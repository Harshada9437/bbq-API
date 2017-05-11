package com.barbeque.dao.Sync;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.FeedbackDTO;
import com.barbeque.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 3/21/2017.
 */
public class SyncDAO {
    public String createArchive(String date) throws SQLException {
        Statement statement = null;
        Connection connection = null;
        String table = "feedbacks_" + date;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `" + table + "` (\n" +
                    "`id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                    "`feedback_id` INT(11) NULL DEFAULT NULL,\n" +
                    "`outlet_id` INT(11) NULL DEFAULT NULL,\n" +
                    "`answer_id` INT(11) NULL DEFAULT NULL,\n" +
                    "`question_id` INT(11) NULL DEFAULT NULL,\n" +
                    "`date` VARCHAR(50) NULL DEFAULT NULL,\n" +
                    "`table_no` VARCHAR(50) NULL DEFAULT NULL,\n" +
                    "`outlet_name` VARCHAR(100) NULL DEFAULT NULL,\n" +
                    "`customer_name` VARCHAR(80) NULL DEFAULT NULL,\n" +
                    "`customer_email` VARCHAR(150) NULL DEFAULT NULL,\n" +
                    "`customer_no` VARCHAR(50) NULL DEFAULT NULL,\n" +
                    "`dob` VARCHAR(50) NULL DEFAULT NULL,\n" +
                    "`doa` VARCHAR(50) NULL DEFAULT NULL,\n" +
                    "`locality` VARCHAR(150) NULL DEFAULT NULL,\n" +
                    "`isNegative` INT(11) NULL DEFAULT NULL,\n" +
                    "`question_desc` VARCHAR(150) NULL DEFAULT NULL,\n" +
                    "`question_type` CHAR(50) NULL DEFAULT NULL,\n" +
                    "`answer_desc` VARCHAR(50) NULL DEFAULT NULL,\n" +
                    "`rating` INT(11) NULL DEFAULT NULL,\n" +
                    "`is_poor` INT(11) NULL DEFAULT NULL,\n" +
                    "`answer_text` VARCHAR(800) NULL DEFAULT NULL,\n" +
                    "PRIMARY KEY (`id`)\n" +
                    ");";

            int i = statement.executeUpdate(sql);
            connection.commit();

        } catch (SQLException sqlException) {
            connection.rollback();
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

        return table;
    }

    public void addArchiveData(String table, List<FeedbackDTO> feedbackDTOS) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);

            for (FeedbackDTO feedbackRequestDTO : feedbackDTOS) {
                int parameterIndex = 1;

                StringBuilder query = new StringBuilder("INSERT INTO `" + table + "` ( feedback_id,outlet_id,date, table_no, outlet_name, customer_name, customer_email," +
                        "customer_no,dob,doa,locality,isNegative,question_desc,question_type,answer_desc,rating,answer_text,is_poor,question_id,answer_id)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
                        ",?,?,?,?)");

                preparedStatement = connection.prepareStatement(query.toString());

                preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getId());

                preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getOutletId());

                preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getFeedbackDate());

                preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getTableNo());

                preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getOutletDesc());

                preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getCustomerName());

                preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getEmail());

                preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getMobileNo());

                preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getDob());

                preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getDoa());

                preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getLocality());

                preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getIsNegative());

                preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getQuestionDesc());

                preparedStatement.setString(parameterIndex++, String.valueOf(feedbackRequestDTO.getQuestionType()));

                preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getAnswerDesc());

                preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getRating());

                preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getAnswerText());

                preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getIsPoor());

                preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getQuestionId());

                preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getAnswerId());

                int i = preparedStatement.executeUpdate();

                if (i == 1) {
                    connection.commit();
                }
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        }
    }

    public int getMaxId(String date) throws SQLException {
        int id = 0;
        String table = "feedbacks_" + date;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "select coalesce(max(feedback_id),0) as id from `" + table + "`");

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

    public int getCount(String where1, String date, String from, String to) throws SQLException {
        int id = 0;
        String table = "feedbacks_" + date;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "select coalesce(count(f.feedback_id),0) as id from `" + table + "` f\n" +
                            "where f.date>='" + from + "' and f.date<='" + to + "'" + where1);

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
