package com.barbeque.dao.Sync;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.sync.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 1/9/2017.
 */
public class CompanyDAO {
    public static void createCompany(Company company) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO company(id , company_desc, company_short_desc, group_id) values (?,?,?,?)");
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    company.getId());
            preparedStatement.setString(parameterIndex++,
                    company.getDesc());
            preparedStatement.setString(parameterIndex++,
                    company.getShortDesc());
            preparedStatement.setInt(parameterIndex++,
                    company.getGroupId());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
            } else {
                connection.rollback();
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
    }

    public static List<Integer> getCompany() {
        Connection connection = null;
        Statement statement = null;
        List<Integer> companies = new ArrayList<Integer>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT id FROM company ");
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                companies.add(id);
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
        return companies;
    }

    public static void updateCompany(Company company) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE company SET  company_desc=?, company_short_desc=?, group_id=? WHERE id=?");

            preparedStatement.setString(parameterIndex++,
                    company.getDesc());
            preparedStatement.setString(parameterIndex++,
                    company.getShortDesc());
            preparedStatement.setInt(parameterIndex++,
                    company.getGroupId());
            preparedStatement.setInt(parameterIndex++,
                    company.getId());


            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
            } else {
                connection.rollback();
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
    }
}
