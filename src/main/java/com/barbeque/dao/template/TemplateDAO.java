package com.barbeque.dao.template;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.QuestionRequestDTO;
import com.barbeque.dto.request.TempDTO;
import com.barbeque.dto.request.TemplateDTO;
import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.exceptions.TemplateNotFoundException;
import com.barbeque.util.DateUtil;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by System1 on 9/9/2016.
 */
public class TemplateDAO {
    public Integer createTemplate(TemplateDTO templateDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO template(template_desc) values (?)");
        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setString(parameterIndex++,
                    templateDTO.getTemplateDesc());

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

    public static TemplateDTO getTemplateById(int templateId) throws SQLException, TemplateNotFoundException {
        Connection connection = null;
        Statement statement = null;
        TemplateDTO templateDTO = new TemplateDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT template_id,template_desc FROM template where template_id = ").append(templateId);
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            while (resultSet.next()) {
                templateDTO.setId(resultSet.getInt("template_id"));
                templateDTO.setTemplateDesc(resultSet.getString("template_desc"));
            }
            if (templateDTO.getId() == 0) {
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
        return templateDTO;
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
            StringBuilder query = new StringBuilder("SELECT  t.template_id,ol.outlet_id,t.template_desc,t.status,o.outlet_desc, o.short_desc\n" +
                    "from template t\n" +
                    "left JOIN outlet_template_link ol ON\n" +
                    "t.template_id=ol.template_id\n" +
                    "left join outlet o\n" +
                    "on ol.outlet_id=o.id;");
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

    public static List<TempDTO> getTemplateByOutletId(int outletId) throws SQLException {
        Statement statement = null;
        List<TempDTO> tempDTOs = new ArrayList<TempDTO>();
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT * FROM outlet_template_link where outlet_id = ").append(outletId);
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            while (resultSet.next()) {
                TempDTO tempDTO = new TempDTO();
                String toDate = DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("to_date"));
                tempDTO.setToDate(toDate);
                String fromDate = DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("from_date"));
                tempDTO.setFromDate(fromDate);
                tempDTO.setTemplateId(resultSet.getInt("template_id"));
                tempDTOs.add(tempDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return tempDTOs;
    }

    public TempDTO getTemplateInfo(int templateId,int outletId) throws SQLException, TemplateNotFoundException {
        Connection connection = null;
        Statement statement = null;
        TempDTO tempDTO = new TempDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select m.outlet_id,m.template_id,m.from_date,m.to_date,t.status,t.template_desc\n" +
                    " from outlet_template_link m\n" +
                    " left join template t\n" +
                    " on t.template_id=m.template_id\n" +
                    " where m.template_id="+ templateId + " and m.outlet_id=" + outletId);
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            int index = 1;
            while (resultSet.next()) {

                tempDTO.setTemplateId(resultSet.getInt("template_id"));
                tempDTO.setDesc(resultSet.getString("template_desc"));
                tempDTO.setStatus(resultSet.getString("status"));
                tempDTO.setOutletId(resultSet.getInt("outlet_id"));
                String fDate = DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("from_date"));
                tempDTO.setFromDate(fDate);
                String tDate = DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("to_date"));
                tempDTO.setToDate(tDate);
                index++;
            }

            if(index == 1){
                throw new TemplateNotFoundException("Invalid id");
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
        return tempDTO;
    }

    public static Boolean getTemplateByName(String name) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        Boolean isExist = Boolean.FALSE;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT * FROM template where template_desc =\"").append(name).append("\"");
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            while (resultSet.next()) {
               isExist = Boolean.TRUE;
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


}
