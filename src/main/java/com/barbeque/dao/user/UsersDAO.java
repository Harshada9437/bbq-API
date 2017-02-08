package com.barbeque.dao.user;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.MenuRequestDTO;
import com.barbeque.dto.request.RollRequestDTO;
import com.barbeque.exceptions.RollNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.dto.response.LoginResponseDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    public LoginResponseDTO getUserDetailsWithName(String name) throws UserNotFoundException, SQLException {
        Connection connection = null;
        Statement statement = null;
        LoginResponseDTO loginResponseDTO = null;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT id, user_name, email, password, status,session_id,isActive,roll_id FROM user_details where user_name = \"")
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
                loginResponseDTO.setIsActive(resultSet.getString("isActive"));
                loginResponseDTO.setRoll_id(resultSet.getInt("roll_id"));
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

    public Boolean updateSessionId(String sessionIdL, String sessionId, int userId)
            throws SQLException {
        boolean isUpdated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            StringBuffer query = new StringBuffer(
                    "UPDATE user_details SET session_id=replace(\'" + sessionIdL + "\',\'|" + sessionId
                            + "|\','') WHERE id = " + userId);
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
                    .prepareStatement("UPDATE user_details SET session_id =\"" + "\"" + " WHERE user_name =\"" + userName + "\"" + " and password=\"" + encodedPassword + "\"");

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

    public static LoginResponseDTO getuserById(int id) throws UserNotFoundException, SQLException {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String query = "SELECT id,user_name,email,password,status,session_id,isActive,roll_id FROM user_details where id=" + id;

            ResultSet resultSet = statement.executeQuery(query.toString());
            int rowCount = 0;
            loginResponseDTO = new LoginResponseDTO();
            while (resultSet.next()) {
                loginResponseDTO.setId(id);
                loginResponseDTO.setUserName(resultSet.getString("user_name"));
                loginResponseDTO.setEmail(resultSet.getString("email"));
                loginResponseDTO.setPassword(resultSet.getString("password"));
                loginResponseDTO.setStatus(resultSet.getString("status"));
                loginResponseDTO.setSessionId(resultSet.getString("session_id"));
                loginResponseDTO.setIsActive(resultSet.getString("isActive"));
                loginResponseDTO.setRoll_id(resultSet.getInt("roll_id"));
                rowCount++;
            }
            if (rowCount == 0) {
                throw new UserNotFoundException("User id invalid");
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

    public List<MenuRequestDTO> getMenuList() throws SQLException {
        List<MenuRequestDTO> menuRequestDTOList = new ArrayList<MenuRequestDTO>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String query = "SELECT * FROM menu";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
                menuRequestDTO.setId(resultSet.getInt("id"));
                menuRequestDTO.setName(resultSet.getString("name"));
                menuRequestDTO.setParent_id(resultSet.getInt("parent_id"));
                menuRequestDTO.setHyperlink(resultSet.getString("hyperlink"));
                menuRequestDTO.setIsActive(resultSet.getString("isActive"));
                menuRequestDTOList.add(menuRequestDTO);
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
        return menuRequestDTOList;
    }


    public static RollRequestDTO getrollById(int id) throws SQLException {
        RollRequestDTO rollRequestDTO = new RollRequestDTO();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String query = "select * from roll where roll_id ="+id;
            ResultSet resultSet = statement.executeQuery(query);
            int rowCount = 0;
            while (resultSet.next()) {
                RollRequestDTO rollRequestDTO1 = new RollRequestDTO();
                rollRequestDTO.setRoll_id(resultSet.getInt("roll_id"));
                rollRequestDTO.setName(resultSet.getString("name"));
                rollRequestDTO.setMenu_access(resultSet.getString("menu_access"));
                rollRequestDTO.setOutlet_access(resultSet.getString("outlet_access"));
                rowCount++;
            }
            if (rowCount == 0) {
                try {
                    throw new RollNotFoundException("Roll_id invalid");
                } catch (RollNotFoundException e) {
                    e.printStackTrace();
                }
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
        return rollRequestDTO;
    }
}
