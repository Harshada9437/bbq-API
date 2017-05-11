package com.barbeque.dao.user;

import com.barbeque.bo.ChangePasswordBO;
import com.barbeque.bo.ResetPasswordRequestBO;
import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.CreateRoleDTO;
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
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT u.*,r.menu_access,r.outlet_access FROM user_details u\n" +
                            "left join role r\n" +
                            "on r.role_id=u.role_id\n" +
                            " where u.user_name = \"")
                    .append(name).append("\"");
            ResultSet resultSet = statement.executeQuery(query.toString());
            int rowCount = 0;
            while (resultSet.next()) {
                loginResponseDTO.setUserName(name);
                loginResponseDTO.setId(resultSet.getInt("id"));
                loginResponseDTO.setEmail(resultSet.getString("email"));
                loginResponseDTO.setName(resultSet.getString("name"));
                loginResponseDTO.setStatus(resultSet.getString("status"));
                loginResponseDTO.setRoleId(resultSet.getInt("role_id"));
                loginResponseDTO.setMenuAccess(resultSet.getString("menu_access"));
                loginResponseDTO.setPassword(resultSet.getString("password"));
                loginResponseDTO.setOutletAccess(resultSet.getString("outlet_access"));
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

    public Boolean updateAccess(String outlets) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE role SET outlet_access=? WHERE isAll=1");

            preparedStatement.setString(parameterIndex++,outlets);

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

    public Boolean getValidUserBySessionIdPasswordUsername(String userName, String password, String sessionId)
            throws SQLException {
        Boolean isVerify = Boolean.FALSE;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT * FROM user_details where user_name =\"" + userName + "\" and password=\"" + password + "\" and session_id LIKE \"%|" +
                            sessionId + "|%\"");
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                isVerify = Boolean.TRUE;
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
        return isVerify;
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
            ResultSet resultSet = statement.executeQuery(query.toString());
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

            String query = "SELECT u.*,r.menu_access,r.outlet_access FROM user_details u\n" +
                    "left join role r\n" +
                    "on r.role_id=u.role_id\n" +
                    " where u.id=" + id;

            ResultSet resultSet = statement.executeQuery(query.toString());
            int rowCount = 0;

            while (resultSet.next()) {
                loginResponseDTO.setId(id);
                loginResponseDTO.setUserName(resultSet.getString("user_name"));
                loginResponseDTO.setName(resultSet.getString("name"));
                loginResponseDTO.setEmail(resultSet.getString("email"));
                loginResponseDTO.setOutletAccess(resultSet.getString("outlet_access"));
                loginResponseDTO.setStatus(resultSet.getString("status"));
                loginResponseDTO.setMenuAccess(resultSet.getString("menu_access"));
                loginResponseDTO.setRoleId(resultSet.getInt("role_id"));
                loginResponseDTO.setNotifyEmail(resultSet.getInt("notify_email"));
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
            String query = "SELECT * FROM menu\n order by sequence_id*1";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
                menuRequestDTO.setId(resultSet.getInt("id"));
                menuRequestDTO.setParentId(resultSet.getInt("parent_id"));
                menuRequestDTO.setName(resultSet.getString("name"));
                menuRequestDTO.setSequenceId(resultSet.getString("sequence_id"));
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
                roleRequestDTO.setIsAll(resultSet.getInt("isAll"));
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

    public Integer createUser(LoginResponseDTO createUserDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO user_details(name,user_name,email,password,role_id,notify_email) values (?,?,?,?,?,?)");
        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setString(parameterIndex++,
                    createUserDTO.getName());
            preparedStatement.setString(parameterIndex++,
                    createUserDTO.getUserName());
            preparedStatement.setString(parameterIndex++,
                    createUserDTO.getEmail());
            preparedStatement.setString(parameterIndex++,
                    createUserDTO.getPassword());
            preparedStatement.setInt(parameterIndex++,
                    createUserDTO.getRoleId());
            preparedStatement.setInt(parameterIndex++,
                    createUserDTO.getNotifyEmail());


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

    public static Boolean getuser(String userName, String email, String name)
            throws SQLException {

        boolean isCreated = false;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "select * from user_details where user_name = \"").append(userName)
                    .append("\" or email =\"").append(email).append("\" or name=\"").append(name + "\"");

            ResultSet resultSet = statement.executeQuery(query.toString());
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


    public Integer createRoll(CreateRoleDTO createRollDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO role(name,menu_access,outlet_access,isAll) values (?,?,?,?)");
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
            preparedStatement.setInt(parameterIndex++,
                    createRollDTO.getIsAll());


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

    public static Boolean getRole(String name) throws SQLException {

        boolean isCreated = false;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "select * from role where name = \"").append(name).append("\"");

            ResultSet resultSet = statement.executeQuery(query.toString());
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


    public static Boolean updateUser(LoginResponseDTO loginResponseDTO) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE user_details SET name=?, email =?," +
                            "role_id = ?,status=?,notify_email=? WHERE id =?");

            preparedStatement.setString(parameterIndex++, loginResponseDTO.getName());

            preparedStatement.setString(parameterIndex++, loginResponseDTO.getEmail());

            preparedStatement.setInt(parameterIndex++, loginResponseDTO.getRoleId());

            preparedStatement.setString(parameterIndex++, loginResponseDTO.getStatus());

            preparedStatement.setInt(parameterIndex++, loginResponseDTO.getNotifyEmail());

            preparedStatement.setInt(parameterIndex++, loginResponseDTO.getId());

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

    public Boolean changePassword(ChangePasswordBO changePwdBO) throws SQLException {
        Boolean isProcessed = Boolean.FALSE;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String query = "SELECT password FROM user_details WHERE id="
                    + changePwdBO.getId();

            ResultSet resultSet = statement.executeQuery(query);
            String oldDBpassword = null;
            while (resultSet.next()) {
                oldDBpassword = resultSet.getString("password");
            }

            if (oldDBpassword != null && changePwdBO.getOldPassword() != null
                    && oldDBpassword.equals(changePwdBO.getOldPassword())) {
                if (updatePassword(changePwdBO.getNewPassword(),
                        changePwdBO.getId(), connection)) {
                    connection.commit();
                    isProcessed = Boolean.TRUE;
                }
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

    private boolean updatePassword(String newPassword, int userId, Connection connection) throws SQLException {
        boolean isUpdated = false;
        connection.setAutoCommit(false);
        String query = "UPDATE user_details SET password=\"" + newPassword
                + "\" WHERE id=" + userId;
        PreparedStatement preparedStatement = connection
                .prepareStatement(query);
        int i = preparedStatement.executeUpdate();
        if (i > 0) {
            connection.commit();
            isUpdated = Boolean.TRUE;
        } else {
            connection.rollback();
        }
        return isUpdated;

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
                    .prepareStatement("UPDATE role SET isAll=?,name=?,menu_access=?,outlet_access=? WHERE role_id =?");


            preparedStatement.setInt(parameterIndex++, roleRequestDTO.getIsAll());

            preparedStatement.setString(parameterIndex++, roleRequestDTO.getName());

            preparedStatement.setString(parameterIndex++, roleRequestDTO.getMenuAccess());

            preparedStatement.setString(parameterIndex++, roleRequestDTO.getOutletAccess());

            preparedStatement.setInt(parameterIndex++, roleRequestDTO.getRoleId());


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


    public List<LoginResponseDTO> getUserList() throws SQLException {
        List<LoginResponseDTO> userList = new ArrayList<LoginResponseDTO>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String query = "SELECT u.*,r.menu_access,r.outlet_access FROM user_details u\n" +
                    "left join role r\n" +
                    "on r.role_id=u.role_id";

            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
                loginResponseDTO.setId(resultSet.getInt("id"));
                loginResponseDTO.setUserName(resultSet.getString("user_name"));
                loginResponseDTO.setName(resultSet.getString("name"));
                loginResponseDTO.setEmail(resultSet.getString("email"));
                loginResponseDTO.setOutletAccess(resultSet.getString("outlet_access"));
                loginResponseDTO.setStatus(resultSet.getString("status"));
                loginResponseDTO.setMenuAccess(resultSet.getString("menu_access"));
                loginResponseDTO.setRoleId(resultSet.getInt("role_id"));
                loginResponseDTO.setNotifyEmail(resultSet.getInt("notify_email"));
                userList.add(loginResponseDTO);
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
        return userList;
    }

    public Boolean resetPassword(ResetPasswordRequestBO resetPwdBO) throws SQLException {
        Boolean isProcessed = Boolean.FALSE;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("UPDATE user_details SET password=?  WHERE id=?");

            statement.setString(parameterIndex++, resetPwdBO.getNewPassword());

            statement.setInt(parameterIndex++, resetPwdBO.getId());

            int i = statement.executeUpdate();
            if (i > 0) {
                connection.commit();
                isProcessed = Boolean.TRUE;
            } else {
                connection.rollback();
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

    public List<String> getMenus(String menuAccess) throws SQLException {
        List<String> menus = new ArrayList<String>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String query = "SELECT name from menu where id in("+menuAccess+")";

            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                menus.add(name);
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
        return menus;
    }
}
