package com.barbeque.dao.Sync;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.SettingRequestDTO;
import com.barbeque.dto.request.SmsSettingDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 2/7/2017.
 */
public class SmsDAO {
    public static Boolean saveSetting(SettingRequestDTO settingRequestDTO) throws SQLException {
        Boolean isProcessed =Boolean.FALSE;
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            int parameterIndex = 1;

            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("UPDATE global_settings SET  sms_template=? ");

            statement.setString(parameterIndex++,settingRequestDTO.getSmsTemplate());

            int i = statement.executeUpdate();
            if (i > 0) {
                isProcessed = Boolean.TRUE;
                connection.commit();
            } else {
                connection.rollback();
            }
        }catch (SQLException e){
            connection.rollback();
            e.printStackTrace();
            throw e;
        }finally {
            try {
                statement.close();
                connection.close();
            }catch (SQLException sq){
                sq.printStackTrace();
                throw sq;
            }
        }
        return isProcessed;
    }

    public static SettingRequestDTO fetchSettings() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        SettingRequestDTO settingRequestDTO = new SettingRequestDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select * from global_settings");
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());

            int index = 1;
            while (resultSet.next()) {

                settingRequestDTO.setSmsTemplate(resultSet.getString("sms_template"));

                index++;
            }
            if (index == 1) {
                throw new SQLException("Error getting settings");
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
        return settingRequestDTO;
    }

    public static Integer saveSmsSettings(SmsSettingDTO settingRequestDTO) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int id = 0;
        try{
            int parameterIndex = 1;

            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("INSERT INTO sms_gateway_mstr(api,sender_id,campaign,country_code) VALUES(?,?,?,?)");

            statement.setString(parameterIndex++,settingRequestDTO.getApi());
            statement.setString(parameterIndex++,settingRequestDTO.getSenderId());
            statement.setString(parameterIndex++,settingRequestDTO.getCampaign());
            statement.setString(parameterIndex++,settingRequestDTO.getCountryCode());

            int i = statement.executeUpdate();
            if (i > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }

            try {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    connection.rollback();
                    throw new SQLException(
                            "Creating gateway failed, no ID obtained.");
                }
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }

        }catch (SQLException e){
            connection.rollback();
            e.printStackTrace();
            throw e;
        }finally {
            try {
                statement.close();
                connection.close();
            }catch (SQLException sq){
                sq.printStackTrace();
                throw sq;
            }
        }
        return id;
    }

    public static List<SmsSettingDTO> fetchSmsSettings() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        List<SmsSettingDTO> smsSettingDTOS = new ArrayList<SmsSettingDTO>();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select * from sms_gateway_mstr");
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());


            while (resultSet.next()) {
                SmsSettingDTO settingRequestDTO = new SmsSettingDTO();
                settingRequestDTO.setId(resultSet.getInt("id"));
                settingRequestDTO.setApi(resultSet.getString("api"));
                settingRequestDTO.setSenderId(resultSet.getString("sender_id"));
                settingRequestDTO.setCampaign(resultSet.getString("campaign"));
                settingRequestDTO.setCountryCode(resultSet.getString("country_code"));
                smsSettingDTOS.add(settingRequestDTO);
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
        return smsSettingDTOS;
    }

    public static SmsSettingDTO fetchSmsSettingsById(int id) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        SmsSettingDTO smsSettingDTO = new SmsSettingDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select * from sms_gateway_mstr where id=" + id);
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());


            while (resultSet.next()) {
                smsSettingDTO.setId(resultSet.getInt("id"));
                smsSettingDTO.setApi(resultSet.getString("api"));
                smsSettingDTO.setSenderId(resultSet.getString("sender_id"));
                smsSettingDTO.setCampaign(resultSet.getString("campaign"));
                smsSettingDTO.setCountryCode(resultSet.getString("country_code"));
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
        return smsSettingDTO;
    }
}
