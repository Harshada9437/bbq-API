package com.barbeque.dao.device;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.DeviceDTO;
import com.barbeque.dto.request.TableDTO;
import com.barbeque.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceDAO {
    public Integer addDevice(DeviceDTO deviceDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO devices(serial_no, android_version, model, installation_date) values (?,?,?,?)");
        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query.toString());

            preparedStatement.setString(parameterIndex++, deviceDTO.getSerialNo());
            preparedStatement.setString(parameterIndex++, deviceDTO.getAndroidVersion());
            preparedStatement.setString(parameterIndex++, deviceDTO.getModel());
            preparedStatement.setString(parameterIndex++, deviceDTO.getInstallationDate());

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
                deviceDTO.setAndroidVersion(resultSet.getString("android_version"));
                deviceDTO.setInstallationDate(resultSet.getString("installation_date"));
                deviceDTO.setModel(resultSet.getString("model"));
                deviceDTO.setSerialNo(resultSet.getString("serial_no"));
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

    public Integer getValidDevice(String serialNo) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        int id = 0;
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT id FROM devices where serial_no=\"" + serialNo + "\" and status=\"" + "A" + "\"");
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());

            while (resultSet.next()) {
               id = resultSet.getInt("id");
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
        return id;
    }

    public Boolean installDevice(DeviceDTO deviceDTO) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE devices SET android_version =?, " +
                            "model=?, installation_date = ? WHERE id =?");

            preparedStatement.setString(parameterIndex++, deviceDTO.getAndroidVersion());

            preparedStatement.setString(parameterIndex++, deviceDTO.getModel());

            preparedStatement.setString(parameterIndex++, deviceDTO.getInstallationDate());

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

    public Boolean updateDevice(DeviceDTO deviceDTO) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE devices SET status =? WHERE id =?");

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
}
