package com.barbeque.dao.Sync;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.VersionInfoDTO;
import com.barbeque.dto.request.SettingRequestDTO;

import java.sql.*;

/**
 * Created by Sandeep on 1/20/2017.
 */
public class VersionDAO {
    public VersionInfoDTO getAndroidVersion() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        VersionInfoDTO versionInfoDTO = new VersionInfoDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select * from android_version");
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());

            int index = 1;
            while (resultSet.next()) {

                versionInfoDTO.setVersionCode(resultSet.getInt("versionCode"));
                versionInfoDTO.setVersionNumber(resultSet.getString("versionName"));
                index++;
            }
            if (index == 1) {
                throw new SQLException("Error getting version");
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
        return versionInfoDTO;
    }

    public static Boolean saveSetting(SettingRequestDTO settingRequestDTO) throws SQLException{
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
}
