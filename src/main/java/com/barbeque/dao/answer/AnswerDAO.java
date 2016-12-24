package com.barbeque.dao.answer;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.AnswerDTO;
import com.barbeque.exceptions.AnswerNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 12/15/2016.
 */
public class AnswerDAO {
    public Integer createAnswer(int queId,String ans, int rating) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO question_answer_link(question_id , answer_desc, rating) values (?,?,?)");
        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    queId);
            preparedStatement.setString(parameterIndex++,
                    ans);
            preparedStatement.setInt(parameterIndex++,
                    rating);


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
                            "Creating answer failed, no ID obtained.");
                }
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                throw e;
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

    public List<AnswerDTO> getAnswer(int questionId) throws SQLException, AnswerNotFoundException {
        Connection connection = null;
        Statement statement = null;
        List<AnswerDTO> answerDTOs = new ArrayList<AnswerDTO>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT qa.question_id,qb.question_desc as description,qa.answer_id,qa.answer_desc,qa.rating\n" +
                    " FROM question_answer_link qa\n" +
                    " INNER JOIN question_bank qb\n" +
                    " ON\n" +
                    " qa.question_id=qb.id\n" +
                    " where question_id="+questionId);
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            int index = 1;
            while (resultSet.next()) {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setQuestionId(resultSet.getInt("question_id"));
                answerDTO.setDescription(resultSet.getString("description"));
                answerDTO.setId(resultSet.getInt("answer_id"));
                answerDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                answerDTO.setRating(resultSet.getInt("rating"));
                index++;
                answerDTOs.add(answerDTO);

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
        return answerDTOs;
    }

    public Boolean updateAnswer(int id, String label, int rating) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE question_answer_link SET answer_desc =?, rating =? WHERE answer_id =?");

            preparedStatement.setString(parameterIndex++, label);

            preparedStatement.setInt(parameterIndex++, rating);

            preparedStatement.setInt(parameterIndex++, id);

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
}
