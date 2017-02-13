package com.barbeque.dao.device;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.DeviceDTO;
import com.barbeque.dto.request.RegisterDTO;
import com.barbeque.exceptions.DeviceNotFoundException;
import com.barbeque.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceDAO {
    public Integer verifyDevice(DeviceDTO deviceDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO devices(installation_id, android_device_id, store_id, fingerprint, installation_date) values (?,?,?,?,?)");
        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
                expireDeviceOtp(deviceDTO.getOtp(),deviceDTO.getInstallationId(),0,connection);
                preparedStatement = connection.prepareStatement(query.toString());

                preparedStatement.setString(parameterIndex++, deviceDTO.getInstallationId());
                preparedStatement.setString(parameterIndex++, deviceDTO.getAndroidDeviceId());
                preparedStatement.setString(parameterIndex++, deviceDTO.getStoreId());
                preparedStatement.setString(parameterIndex++, deviceDTO.getFingerprint());
                preparedStatement.setString(parameterIndex++, DateUtil.getCurrentServerTime());

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
                                "Creating device failed, no ID obtained.");
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

    public DeviceDTO getDeviceByInstallationId(String installId) throws SQLException {

        DeviceDTO deviceDTO = new DeviceDTO();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String query = "select * from devices where installation_id='" + installId + "'";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                deviceDTO.setId(resultSet.getInt("id"));
                deviceDTO.setAndroidDeviceId(resultSet.getString("android_device_id"));
                deviceDTO.setInstallationDate(resultSet.getString("installation_date"));
                deviceDTO.setFingerprint(resultSet.getString("fingerprint"));
                deviceDTO.setInstallationId(resultSet.getString("installation_id"));
                deviceDTO.setStoreId(resultSet.getString("store_id"));
                deviceDTO.setStatus(resultSet.getString("status"));
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
        return deviceDTO;
    }

    public List<DeviceDTO> getDeviceList() throws SQLException {
        List<DeviceDTO> deviceList = new ArrayList<DeviceDTO>();
        Connection connection = null;
        Statement statement = null;
        String date = "";
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String query = "select d.*, f.id as feedback_id,f.created_on\n" +
                    "from devices d\n" +
                    "left join feedback_head f\n" +
                    "on f.device_id=d.id and f.id = (select max(id) from feedback_head where device_id=d.id)" ;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                DeviceDTO deviceDTO = new DeviceDTO();
                date = "";
                deviceDTO.setId(resultSet.getInt("id"));
                deviceDTO.setAndroidDeviceId(resultSet.getString("android_device_id"));
                deviceDTO.setInstallationDate(resultSet.getString("installation_date"));
                deviceDTO.setFingerprint(resultSet.getString("fingerprint"));
                deviceDTO.setInstallationId(resultSet.getString("installation_id"));
                deviceDTO.setStoreId(resultSet.getString("store_id"));
                deviceDTO.setStatus(resultSet.getString("status"));
                if(resultSet.getTimestamp("created_on") != null){
                     date = DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("created_on"));
                }
                deviceDTO.setFeedbackDate(date);
                deviceDTO.setFeedbackId(resultSet.getInt("feedback_id"));
                deviceList.add(deviceDTO);
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
        return deviceList;
    }

    public Boolean getValidDevice(RegisterDTO registerDTO,int otp) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Boolean isCreate = Boolean.FALSE;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement("INSERT INTO device_otp_map(android_device_id,installation_id,store_id,otp,isExpired) values(?,?,?,?,?)");

            statement.setString(parameterIndex++, registerDTO.getAndroidDeviceId());
            statement.setString(parameterIndex++, registerDTO.getInstallationId());
            statement.setString(parameterIndex++, registerDTO.getStoreId());
            statement.setInt(parameterIndex++, otp);
            statement.setString(parameterIndex++, "NO");

            int i = statement.executeUpdate();

            if (i > 0) {
                connection.commit();
                expireDeviceOtp(otp,registerDTO.getInstallationId(),1,connection);
                isCreate = Boolean.TRUE;
            } else {
                connection.rollback();
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isCreate;
    }

    private Boolean expireDeviceOtp(int otp, String installationId,int time,Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        String where = "";
        Boolean isCreate = Boolean.FALSE;
        try {

            if(time > 0){
                where = " and timest < (NOW() + INTERVAL 30 MINUTE)";
            }
            preparedStatement = connection
                    .prepareStatement("UPDATE device_otp_map SET isExpired=\"" + "YES" + "\" WHERE otp=" + otp + " and installation_id=\""
                                     + installationId +  "\"" + where);


            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                isCreate = Boolean.TRUE;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        }
        return isCreate;
    }

    public Boolean updateDevice(DeviceDTO deviceDTO) throws SQLException, DeviceNotFoundException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            getDevice(deviceDTO.getId());
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE devices SET installation_date=?, status =? WHERE id =?");

            preparedStatement.setString(parameterIndex++, deviceDTO.getInstallationDate());
            preparedStatement.setString(parameterIndex++, deviceDTO.getStatus());

            preparedStatement.setInt(parameterIndex++, deviceDTO.getId());

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

    public static int getValidOtp(String installationId,int otp) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        int otp1=0;
        String where="";
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            if(otp>0){
                where = " and otp=" + otp;
            }
            StringBuilder query = new StringBuilder(
                    "SELECT isExpired,otp FROM device_otp_map where installation_id =\"").append(installationId).append("\"").append(where);
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            while (resultSet.next()) {
                if(resultSet.getString("isExpired").equals("NO")) {
                    otp1 = resultSet.getInt("otp");
                }
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
        return otp1;
    }

    public static DeviceDTO getDevice(int deviceId) throws SQLException, DeviceNotFoundException {
        Connection connection = null;
        Statement statement = null;
        DeviceDTO deviceDTO = new DeviceDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT * FROM devices where id =" + deviceId);
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());

            int i = 1;
            while (resultSet.next()) {
               deviceDTO.setStoreId(resultSet.getString("store_id"));
               deviceDTO.setInstallationId(resultSet.getString("installation_id"));
               deviceDTO.setStatus(resultSet.getString("status"));
               deviceDTO.setAndroidDeviceId(resultSet.getString("android_device_id"));
               deviceDTO.setAndroidDeviceId(resultSet.getString("installation_date"));
               i++;
            }

            if(i==1){
                throw new DeviceNotFoundException("invalid device id");
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
        return deviceDTO;
    }
}
