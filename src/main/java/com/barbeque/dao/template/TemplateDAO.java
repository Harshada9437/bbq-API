package com.barbeque.dao.template;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.TemplateDTO;
import com.barbeque.exceptions.TemplateNotFoundException;

import java.sql.*;

/**
 * Created by System1 on 9/9/2016.
 */
public class TemplateDAO {
    public Integer createTemplate(TemplateDTO templateDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO template(template_desc , status) values (?,?)");
        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setString(parameterIndex++,
                    templateDTO.getTemplateDesc());
            preparedStatement.setString(parameterIndex++,
                    templateDTO.getStatus());

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
                            "Creating template failed, no ID obtained.");
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

    public static void getTemplateById(int templateId) throws SQLException, TemplateNotFoundException {
        Connection connection = null;
        Statement statement = null;
        int id = 0;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT template_id FROM template where template_id = ").append(templateId);
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            while (resultSet.next()) {
                id = resultSet.getInt("template_id");
            }
            if (id == 0) {
                throw new TemplateNotFoundException("Invalid template id");
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
    }

    public Boolean updateTemplate(TemplateDTO templateDTO) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE template SET template_desc =?, status =? WHERE template_id =?;");

            preparedStatement.setString(parameterIndex++, templateDTO.getTemplateDesc());

            preparedStatement.setString(parameterIndex++, templateDTO.getStatus());

            preparedStatement.setInt(parameterIndex++, templateDTO.getId());

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
