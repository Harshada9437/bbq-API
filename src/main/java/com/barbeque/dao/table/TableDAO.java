package com.barbeque.dao.table;

import com.barbeque.dto.request.TableDTO;
import com.barbeque.dao.ConnectionHandler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-3 on 12/10/2016.
 */
public class TableDAO {
    public List<TableDTO> getTables()  {
        Connection connection = null;
        Statement statement = null;
        List<TableDTO> tableDTOs = new ArrayList<TableDTO>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT * FROM tables_desc ");
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            int index = 1;
            while (resultSet.next()) {
                TableDTO tableDTO = new TableDTO();
                tableDTO.setId(resultSet.getInt("id"));
                tableDTO.setStatus(resultSet.getString("status"));
                tableDTO.setTableName(resultSet.getString("table_name"));
                index++;
                tableDTOs.add(tableDTO);
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
        return tableDTOs;
    }
}
