package com.barbeque.dao.outlet;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.request.OutletDTO;
import com.barbeque.dto.request.OutletListDTO;
import com.barbeque.exceptions.TemplateNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletDAO {
    public Boolean assignoutlet(OutletDTO outletDTO, int outletId) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO outlet_template_link(outlet_id, template_id,from_date,to_date");
        query.append(")values (?,?,?,?)");
        Boolean isCreated = Boolean.FALSE;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    outletId);
            preparedStatement.setInt(parameterIndex++, outletDTO.getTemplateId());
            preparedStatement.setString(parameterIndex++, outletDTO.getFromDate());
            preparedStatement.setString(parameterIndex++, outletDTO.getToDate());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                isCreated = Boolean.TRUE;
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
        return isCreated;


    }

    public List<OutletListDTO> getOutlate() throws SQLException, TemplateNotFoundException {
        Connection connection = null;
        Statement statement = null;
        List<OutletListDTO> outletListDTOs = new ArrayList<OutletListDTO>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("Select * From outlet");
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            int index = 1;
            while (resultSet.next()) {
                OutletListDTO outletListDTO = new OutletListDTO();
                outletListDTO.setId(resultSet.getInt("id"));
                outletListDTO.setOutletDesc(resultSet.getString("outlet_desc"));
                outletListDTO.setShortDesc(resultSet.getString("short_desc"));
                outletListDTO.setClusterId(resultSet.getInt("cluster_id"));
                outletListDTO.setRegionId(resultSet.getInt("region_id"));
                outletListDTO.setCompanyId(resultSet.getInt("company_id"));
                outletListDTO.setGroupId(resultSet.getInt("group_id"));
                outletListDTO.setPosStoreId(resultSet.getInt("pos_store_id"));
                index++;
                outletListDTOs.add(outletListDTO);
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
        return outletListDTOs;
    }
}
