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
                    queTempDTO.getQuestionId());
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
            StringBuilder query = new StringBuilder("SELECT m.id,m.question_id,q.question_desc,m.priority\n" +
                    "FROM template_question_link m\n" +
                    "inner join question_bank q\n" +
                    "on q.id=m.question_id\n" +
                    "where m.template_id=" + templateId + "\n" +
                    "order by m.priority");
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                QueTempDTO queTempDTO = new QueTempDTO();
                queTempDTO.setId(resultSet.getInt("id"));
                queTempDTO.setQuestionId(resultSet.getInt("question_id"));
                queTempDTO.setQuestionText(resultSet.getString("question_desc"));
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
}

