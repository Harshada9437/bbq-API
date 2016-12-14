package com.barbeque.dao.question;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.QuestionRequestDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    public Integer addQuestion(QuestionRequestDTO questionRequestDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO question_bank(question_desc, question_type, parent_answer_id, parent_question_id, answer_symbol) values (?,?,?,?,?)");
        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query.toString());

            preparedStatement.setString(parameterIndex++, questionRequestDTO.getQuestionDesc());
            preparedStatement.setString(parameterIndex++, String.valueOf(questionRequestDTO.getQuestionType()));
            preparedStatement.setInt(parameterIndex++, questionRequestDTO.getParentAnswerId());
            preparedStatement.setInt(parameterIndex++, questionRequestDTO.getParentQuestionId());
            preparedStatement.setInt(parameterIndex++, questionRequestDTO.getAnswerSymbol());

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
                            "Creating question failed, no ID obtained.");
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

    public List<QuestionRequestDTO> getAllQuestions() throws SQLException {
        List<QuestionRequestDTO> allQuestions = new ArrayList<QuestionRequestDTO>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String query = "SELECT * FROM question_bank";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                QuestionRequestDTO sujectDTO = new QuestionRequestDTO();
                sujectDTO.setId(resultSet.getInt("id"));
                sujectDTO.setQuestionDesc(resultSet.getString("question_desc"));
                sujectDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                sujectDTO.setParentQuestionId(resultSet.getInt("parent_question_id"));
                sujectDTO.setParentAnswerId(resultSet.getInt("parent_answer_id"));
                sujectDTO.setAnswerSymbol(resultSet.getInt("answer_symbol"));
                allQuestions.add(sujectDTO);
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
        return allQuestions;
    }

    public Boolean updateQuestion(QuestionRequestDTO questionRequestDTO) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE question_bank SET question_desc =?, question_type =?, " +
                            "parent_question_id=?, parent_answer_id = ?,answer_symbol=? WHERE id =?");

            preparedStatement.setString(parameterIndex++, questionRequestDTO.getQuestionDesc());

            preparedStatement.setString(parameterIndex++, String.valueOf(questionRequestDTO.getQuestionType()));

            preparedStatement.setInt(parameterIndex++, questionRequestDTO.getParentQuestionId());

            preparedStatement.setInt(parameterIndex++, questionRequestDTO.getParentAnswerId());

            preparedStatement.setInt(parameterIndex++, questionRequestDTO.getAnswerSymbol());

            preparedStatement.setInt(parameterIndex++, questionRequestDTO.getId());

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
