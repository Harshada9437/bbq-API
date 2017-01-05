package com.barbeque.dao.template;


import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.*;
import com.barbeque.exceptions.TemplateNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System1 on 9/27/2016.
 */
public class QueTempDAO {
    public Boolean assignQuestion(QueTempDTO queTempDTO,int templateId) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO template_question_link(template_id, question_id,priority");
        query.append(")values (?,?,?)");
        Boolean isCreated =Boolean.FALSE;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    templateId);
            preparedStatement.setInt(parameterIndex++,
                    queTempDTO.getQueId());
            preparedStatement.setFloat(parameterIndex++,
                    queTempDTO.getPriority());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                isCreated = Boolean.TRUE;
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
        return isCreated;
    }

    public List<QueTempDTO> getAssignedQuestions(int templateId) throws SQLException , TemplateNotFoundException {
        Connection connection = null;
        Statement statement = null;
        List<QueTempDTO> queList = new ArrayList<QueTempDTO>();
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            TemplateDAO.getTemplateById(templateId);
            StringBuilder query = new StringBuilder(" SELECT q.id,q.question_desc,q.question_type,q.parent_answer_id, a.answer_desc as parent_answer_desc,\n" +
                    " q.parent_question_id,(select question_desc from question_bank where id = q.parent_question_id) as\n" +
                    "parent_question_desc, q.answer_symbol,m.priority FROM \n" +
                    "template_question_link m\n" +
                    "left join question_bank q\n" +
                    "on q.id = m.question_id\n" +
                    "left join question_answer_link a\n" +
                    "on q.parent_answer_id = a.answer_id\n" +
                    "where m.template_id=" + templateId + "\n" +
                    "order by m.priority");
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                QueTempDTO queTempDTO = new QueTempDTO();
                queTempDTO.setQueId(resultSet.getInt("id"));
                queTempDTO.setAnswerSymbol(resultSet.getInt("answer_symbol"));
                queTempDTO.setParentAnswerId(resultSet.getInt("parent_answer_id"));
                queTempDTO.setParentQuestionId(resultSet.getInt("parent_question_id"));
                queTempDTO.setQuestionDesc(resultSet.getString("question_desc"));
                queTempDTO.setParentQuestionDesc(resultSet.getString("parent_question_desc"));
                queTempDTO.setParentAnswerDesc(resultSet.getString("parent_answer_desc"));
                queTempDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                queTempDTO.setPriority(resultSet.getInt("priority"));
                queList.add(queTempDTO);
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
            return queList;
        }
    }

    public void removeQuestionDetails(int templateId, int queId)throws SQLException,
            TemplateNotFoundException {
        Connection connection = new ConnectionHandler().getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        StringBuilder query = new StringBuilder("delete from template_question_link where template_id =" + templateId + " and question_id=" + queId);
        int i = statement.executeUpdate(query.toString().trim());

        if (i < 1) {
            throw new TemplateNotFoundException("Invalid template id.");
        }

        connection.commit();
        statement.close();
        connection.close();
    }



    public static boolean isAlreadyAssigned(int questionId, int templateId) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        boolean isExist = false;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT template_id FROM template_question_link where template_id = ").append(templateId).append(" and question_id=").append(questionId);
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            while (resultSet.next()) {
                isExist = true;
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
        return isExist;
    }

    public static boolean isAssigned(int priority) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        boolean isExist = false;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT priority FROM template_question_link where priority = ").append(priority);
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            while (resultSet.next()) {
                isExist = true;
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
        return isExist;
    }

    public Boolean updateupdateAssignQuestion (UpdateAssignQuestionDTO updateAssignQuestionDTO) throws SQLException
    {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE template_question_link SET priority=? WHERE template_id =? and question_id =?;");

            preparedStatement.setInt(parameterIndex++, updateAssignQuestionDTO.getPriority());
            preparedStatement.setInt(parameterIndex++, updateAssignQuestionDTO.getTemplateId());
            preparedStatement.setInt(parameterIndex++, updateAssignQuestionDTO.getQuestionId());




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

