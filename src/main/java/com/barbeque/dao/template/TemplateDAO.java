package com.barbeque.dao.template;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.QuestionRequestDTO;
import com.barbeque.dto.request.TemplateDTO;
import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.exceptions.TemplateNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<TemplateDTO> getTemplate() throws SQLException, TemplateNotFoundException {
        Connection connection = null;
        Statement statement = null;
        List<TemplateDTO> templateDTOs = new ArrayList<TemplateDTO>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT t.template_id,ol.outlet_id,t.template_desc,t.status,o.outlet_desc, o.short_desc\n" +
                    "from template t\n" +
                    "left JOIN outlet_template_link ol ON\n" +
                    "t.template_id=ol.outlet_id\n" +
                    "left join outlet o\n" +
                    "on ol.outlet_id=o.id; ");
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            int index = 1;
            while (resultSet.next()) {
                TemplateDTO templateDTO = new TemplateDTO();
                templateDTO.setId(resultSet.getInt("template_id"));
                templateDTO.setOutletId(resultSet.getInt("outlet_id"));
                templateDTO.setTemplateDesc(resultSet.getString("template_desc"));
                templateDTO.setStatus(resultSet.getString("status"));
                templateDTO.setOutletDesc(resultSet.getString("outlet_desc"));
                templateDTO.setShortDesc(resultSet.getString("short_desc"));
                index++;
                templateDTOs.add(templateDTO);

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
        return templateDTOs;
    }

    public static Boolean getTemplateByOutletId(int outletId,Connection connection) throws SQLException {
        Statement statement = null;
        Boolean isExist = Boolean.FALSE;
        try {
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT template_id FROM outlet_template_link where outlet_id = ").append(outletId);
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            while (resultSet.next()) {
                isExist = Boolean.TRUE;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return isExist;
    }
}
