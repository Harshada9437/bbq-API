package com.barbeque.dao.Sync;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.VersionInfoDTO;

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
}
