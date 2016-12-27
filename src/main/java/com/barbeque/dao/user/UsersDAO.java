package com.barbeque.dao.user;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.dto.response.LoginResponseDTO;
import java.sql.*;

public class UsersDAO {
    public LoginResponseDTO getUserDetailsWithName(String name) throws UserNotFoundException, SQLException{
        Connection connection = null;
        Statement statement = null;
        LoginResponseDTO loginResponseDTO = null;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT id, user_name, email, password, status,session_id FROM user_details where user_name = \"")
                    .append(name).append("\"");
            ResultSet resultSet = statement.executeQuery(query.toString());
            int rowCount = 0;
            loginResponseDTO = new LoginResponseDTO();
            while (resultSet.next()) {
                loginResponseDTO.setUserName(name);
                loginResponseDTO.setId(resultSet.getInt("id"));
                loginResponseDTO.setEmail(resultSet.getString("email"));
                loginResponseDTO.setPassword(resultSet.getString("password"));
                loginResponseDTO.setStatus(resultSet.getString("status"));
                loginResponseDTO.setSessionId(resultSet.getString("session_id"));
                rowCount++;
            }
            if (rowCount == 0) {
                throw new UserNotFoundException("User name invalid");
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
        return loginResponseDTO;
    }

    public Boolean updateSessionId(String sessionIdL,String sessionId,int userId)
            throws SQLException {
        boolean isUpdated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            StringBuffer query = new StringBuffer(
                    "UPDATE user_details SET session_id=replace(\'" + sessionIdL + "\',\'|" + sessionId
                    + "|\','') WHERE id = " +userId);
            preparedStatement = connection.prepareStatement(query.toString());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
                isUpdated = Boolean.TRUE;
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
        return isUpdated;
    }

    public Boolean updateLogInSessionId(int userId, String sessionId)
            throws SQLException {
        boolean isUpdated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            StringBuffer query = new StringBuffer(
                    "UPDATE user_details SET session_id = '").append(sessionId)
                    .append("' WHERE id = ").append(userId);
            preparedStatement = connection.prepareStatement(query.toString());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
                isUpdated = Boolean.TRUE;
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
        return isUpdated;
    }

    public String getSessionIdForUserId(
            int userId) throws SQLException, UserNotFoundException {
        Connection connection = null;
        Statement statement = null;
        String sessionId = "";
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT session_id FROM user_details where id = ")
                    .append(userId);
            ResultSet resultSet = statement.executeQuery(query.toString());
            int rowCount = 0;
            while (resultSet.next()) {
                sessionId = resultSet.getString("session_id");
                rowCount++;
            }
            if (rowCount == 0) {
                throw new UserNotFoundException("User name invalid");
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
        return sessionId;
    }

    public String getSessionIdForUser(String userName, String password) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        String sessionId = null;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT session_id FROM user_details where user_name =\"" + userName + "\" and password=\"" + password + "\"");
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            while (resultSet.next()) {
                sessionId = resultSet.getString(1);
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
        return sessionId;
    }

    public void removeSessionId(String userName, String encodedPassword) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE user_details SET session_id =\""+"\""+" WHERE user_name =\"" + userName + "\"" + " and password=\"" + encodedPassword + "\"");

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
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
    }

}
