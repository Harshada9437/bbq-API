package com.barbeque.dao.question;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.QuestionRequestDTO;
import com.barbeque.exceptions.QuestionNotFoundException;

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
            String query = "SELECT q.id,q.question_desc,q.question_type,q.parent_answer_id, a.answer_desc as parent_answer_desc, \n" +
                    "q.parent_question_id,(select question_desc from question_bank where id = q.parent_question_id) as parent_question_desc, q.answer_symbol FROM question_bank q\n" +
                    "left join question_answer_link a\n" +
                    "on q.parent_answer_id = a.answer_id";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                QuestionRequestDTO questionRequestDTO = new QuestionRequestDTO();
                questionRequestDTO.setId(resultSet.getInt("id"));
                questionRequestDTO.setQuestionDesc(resultSet.getString("question_desc"));
                questionRequestDTO.setParentQuestionDesc(resultSet.getString("parent_question_desc"));
                questionRequestDTO.setParentAnswerDesc(resultSet.getString("parent_answer_desc"));
                questionRequestDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                questionRequestDTO.setParentQuestionId(resultSet.getInt("parent_question_id"));
                questionRequestDTO.setParentAnswerId(resultSet.getInt("parent_answer_id"));
                questionRequestDTO.setAnswerSymbol(resultSet.getInt("answer_symbol"));

                allQuestions.add(questionRequestDTO);
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

    public static QuestionRequestDTO getQuestionById(int id) throws SQLException, QuestionNotFoundException {
        Connection connection = null;
        Statement statement = null;
        QuestionRequestDTO questionRequestDTO = new QuestionRequestDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT q.id,q.question_desc,q.question_type,q.parent_answer_id, a.answer_desc as parent_answer_desc, \n" +
                            "q.parent_question_id,(select question_desc from question_bank where id = q.parent_question_id) as parent_question_desc, q.answer_symbol FROM question_bank q\n" +
                            "left join question_answer_link a\n" +
                            "on q.parent_answer_id = a.answer_id\n" +
                            "where id = ")
                    .append(id);
            ResultSet resultSet = statement.executeQuery(query.toString());
            int rowCount = 0;
            while (resultSet.next()) {
                questionRequestDTO.setId(resultSet.getInt("id"));
                questionRequestDTO.setQuestionDesc(resultSet.getString("question_desc"));
                questionRequestDTO.setParentAnswerDesc(resultSet.getString("parent_answer_desc"));
                questionRequestDTO.setParentQuestionDesc(resultSet.getString("parent_question_desc"));
                questionRequestDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                questionRequestDTO.setParentAnswerId(resultSet.getInt("parent_answer_id"));
                questionRequestDTO.setParentQuestionId(resultSet.getInt("parent_question_id"));
                questionRequestDTO.setAnswerSymbol(resultSet.getInt("answer_symbol"));
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
        return questionRequestDTO;
    }
}
