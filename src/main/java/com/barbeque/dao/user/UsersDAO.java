package com.barbeque.dao.user;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.CreateUserDTO;
import com.barbeque.dto.request.CreateRollDTO;
import com.barbeque.dto.request.MenuRequestDTO;
import com.barbeque.dto.request.RoleRequestDTO;
import com.barbeque.exceptions.RoleNotFoundException;
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
                    "SELECT id, user_name, email, password, status,session_id,role_id FROM user_details where user_name = \"")
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
                loginResponseDTO.setRoleId(resultSet.getInt("role_id"));
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

            String query = "SELECT id,user_name,email,password,status,session_id,role_id FROM user_details where id=" + id;

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
                loginResponseDTO.setRoleId(resultSet.getInt("role_id"));
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


    public static RoleRequestDTO getroleById(int id) throws SQLException {
        RoleRequestDTO roleRequestDTO = new RoleRequestDTO();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String query = "select * from role where role_id =" + id;
            ResultSet resultSet = statement.executeQuery(query);
            int rowCount = 0;
            while (resultSet.next()) {
                RoleRequestDTO roleRequestDTO1 = new RoleRequestDTO();
                roleRequestDTO.setRoleId(resultSet.getInt("role_id"));
                roleRequestDTO.setName(resultSet.getString("name"));
                roleRequestDTO.setMenuAccess(resultSet.getString("menu_access"));
                roleRequestDTO.setOutletAccess(resultSet.getString("outlet_access"));
                rowCount++;
            }
            if (rowCount == 0) {
                try {
                    throw new RoleNotFoundException("Role_id invalid");
                } catch (RoleNotFoundException e) {
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
        return roleRequestDTO;
    }

    public List<RoleRequestDTO> getRoleList() throws SQLException {
        List<RoleRequestDTO> roleRequestDTOList = new ArrayList<RoleRequestDTO>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String query = "SELECT * FROM role";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                RoleRequestDTO roleRequestDTO = new RoleRequestDTO();
                roleRequestDTO.setRoleId(resultSet.getInt("role_id"));
                roleRequestDTO.setName(resultSet.getString("name"));
                roleRequestDTO.setMenuAccess(resultSet.getString("menu_access"));
                roleRequestDTO.setOutletAccess(resultSet.getString("outlet_access"));
                roleRequestDTOList.add(roleRequestDTO);
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
        return roleRequestDTOList;
    }

    public Integer createUser(CreateUserDTO createUserDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO user_details(user_name,email,password,role_id) values (?,?,?,?)");
        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setString(parameterIndex++,
                    createUserDTO.getUserName());
            preparedStatement.setString(parameterIndex++,
                    createUserDTO.getEmail());
            preparedStatement.setString(parameterIndex++,
                    createUserDTO.getPassword());
            preparedStatement.setInt(parameterIndex++,
                    createUserDTO.getRoleId());


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
                            "Creating user failed.");
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

    public static Boolean getuserById(String userName, String email)
            throws SQLException {

        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "select * from user_details where user_name = \"").append(userName)
                    .append("\" or email =\"").append(email).append("\"");

            ResultSet resultSet = statement.executeQuery(query.toString()
                        .trim());
                while (resultSet.next()) {
                  isCreated = true;
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
            return isCreated;
        }




    public Integer createRoll(CreateRollDTO createRollDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO role(name,menu_access,outlet_access) values (?,?,?)");
        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setString(parameterIndex++,
                    createRollDTO.getName());
            preparedStatement.setString(parameterIndex++,
                    createRollDTO.getMenuAccess());
            preparedStatement.setString(parameterIndex++,
                   createRollDTO.getOutletAccess());


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
                            "Creation of roll failed.");
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

    public static Boolean getuserById(String name)
            throws SQLException {

        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "select * from role where name = \"").append(name).append("\"");

            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            while (resultSet.next()) {
                isCreated = true;
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
        return isCreated;
    }


    public Boolean updateRoll(RoleRequestDTO roleRequestDTO) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE role SET name=?,menu_access=?,outlet_access=? WHERE role_id =?");


            preparedStatement.setString(parameterIndex++,roleRequestDTO.getName());

            preparedStatement.setString(parameterIndex++,roleRequestDTO.getMenuAccess());

            preparedStatement.setString(parameterIndex++,roleRequestDTO.getOutletAccess());

            preparedStatement.setInt(parameterIndex++,roleRequestDTO.getRoleId());



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
