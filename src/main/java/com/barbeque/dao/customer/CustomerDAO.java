package com.barbeque.dao.customer;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.CustomerDTO;
import com.barbeque.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 12/15/2016.
 */
public class CustomerDAO {
    public Integer addCustomer(String name,String PhoneNo,String emailId,String dob,String doa ) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO customer(name, phone_no, email_id, dob, doa, modified_on) values (?,?,?,?,?,?)");
        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query.toString());

            preparedStatement.setString(parameterIndex++, name);
            preparedStatement.setString(parameterIndex++, PhoneNo);
            preparedStatement.setString(parameterIndex++, emailId);
            preparedStatement.setString(parameterIndex++, dob);
            preparedStatement.setString(parameterIndex++, doa);

            java.util.Date date1 = new java.util.Date();
            Timestamp t1 = new Timestamp(date1.getTime());
            String updated_date = DateUtil.getDateStringFromTimeStamp(t1);
            preparedStatement.setString(parameterIndex++,updated_date);

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
                            "Creating customer failed, no ID obtained.");
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
    
    public List<CustomerDTO> getCustomerList() throws SQLException {
        List<CustomerDTO> customerList = new ArrayList<CustomerDTO>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String query = "SELECT * FROM customer";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setId(resultSet.getInt("id"));
                customerDTO.setName(resultSet.getString("name"));
                customerDTO.setPhoneNo(resultSet.getString("phone_no"));
                customerDTO.setEmailId(resultSet.getString("email_id"));
                customerDTO.setDob(resultSet.getString("dob"));
                customerDTO.setDoa(resultSet.getString("doa"));
                customerDTO.setCreatedOn(resultSet.getString("created_on"));
                customerDTO.setModifiedOn(resultSet.getString("modified_on"));
                customerList.add(customerDTO);
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
        return customerList;
    }

    public Boolean updateQuestion(CustomerDTO customerDTO) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE customer SET name =?, phone_no =?, " +
                            "email_id=?, dob = ?, doa=?, modified_on=? WHERE id =?");

            preparedStatement.setString(parameterIndex++, customerDTO.getName());

            preparedStatement.setString(parameterIndex++, String.valueOf(customerDTO.getPhoneNo()));

            preparedStatement.setString(parameterIndex++, customerDTO.getEmailId());

            preparedStatement.setString(parameterIndex++, customerDTO.getDob());

            preparedStatement.setString(parameterIndex++, customerDTO.getDoa());

            java.util.Date date1 = new java.util.Date();
            Timestamp t1 = new Timestamp(date1.getTime());
            String updated_date = DateUtil.getDateStringFromTimeStamp(t1);
            preparedStatement.setString(parameterIndex++,updated_date);

            preparedStatement.setInt(parameterIndex++, customerDTO.getId());

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

    public static int getValidationForPhoneNumber(String mobile) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        int isProcessed = 0;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT id, phone_no,email_id FROM customer where phone_no = \"" + mobile + "\"");
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                isProcessed = resultSet.getInt("id");
                connection.commit();
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
        return isProcessed;
    }
}
