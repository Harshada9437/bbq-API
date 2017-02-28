package com.barbeque.dao.Sync;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.sync.Cluster;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 1/9/2017.
 */
public class ClusterDAO {
    public static void createCluster(Cluster cluster) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO cluster(id , cluster_desc, clusters_desc, region_id) values (?,?,?,?)");
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    cluster.getId());
            preparedStatement.setString(parameterIndex++,
                    cluster.getDesc());
            preparedStatement.setString(parameterIndex++,
                    cluster.getShortDesc());
            preparedStatement.setInt(parameterIndex++,
                    cluster.getRegionId());

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

    public static List<Integer> getClusters() {
        Connection connection = null;
        Statement statement = null;
        List<Integer> clusters = new ArrayList<Integer>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT id FROM cluster ");
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
               int id = resultSet.getInt("id");
                clusters.add(id);
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
        return clusters;
    }

    public static void updateCluster(Cluster cluster) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE cluster SET  cluster_desc=?, clusters_desc=?, region_id=? WHERE id=?");

            preparedStatement.setString(parameterIndex++,
                    cluster.getDesc());
            preparedStatement.setString(parameterIndex++,
                    cluster.getShortDesc());
            preparedStatement.setInt(parameterIndex++,
                    cluster.getRegionId());
            preparedStatement.setInt(parameterIndex++,
                    cluster.getId());


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
