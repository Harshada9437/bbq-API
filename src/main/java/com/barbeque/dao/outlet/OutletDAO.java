package com.barbeque.dao.outlet;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dto.UpdateSettingsDTO;
import com.barbeque.dto.request.OutletDTO;
import com.barbeque.dto.request.TempDTO;
import com.barbeque.exceptions.OutletNotFoundException;
import com.barbeque.exceptions.TemplateNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletDAO {
    public Boolean assignoutlet(TempDTO tempDTO, int outletId) throws SQLException {
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
            preparedStatement.setInt(parameterIndex++, tempDTO.getTemplateId());
            preparedStatement.setString(parameterIndex++, tempDTO.getFromDate());
            preparedStatement.setString(parameterIndex++, tempDTO.getToDate());
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

    public List<OutletDTO> getOutlate() throws SQLException, TemplateNotFoundException {
        Connection connection = null;
        Statement statement = null;
        List<OutletDTO> outletDTOs = new ArrayList<OutletDTO>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select o.id,o.outlet_desc,o.short_desc,o.cluster_id,o.region_id,o.company_id\n" +
                    ",o.group_id,o.pos_store_id,c.cluster_desc,r.region_desc,cp.company_desc\n" +
                    ",g.group_desc,m.template_id,t.template_desc from outlet_template_link m\n" +
                    "left join outlet o\n" +
                    "on m.outlet_id=o.id\n" +
                    "left join cluster c\n" +
                    "on c.id=o.cluster_id\n" +
                    "left join region r\n" +
                    "on r.id=o.region_id\n" +
                    "left join company cp\n" +
                    "on cp.id=o.company_id\n" +
                    "left join groups g\n" +
                    "on g.id=o.group_id\n" +
                    "left join template t\n" +
                    "on t.template_id=m.template_id");
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            int index = 1;
            while (resultSet.next()) {
                OutletDTO outletDTO = new OutletDTO();
                outletDTO.setId(resultSet.getInt("id"));
                outletDTO.setOutletDesc(resultSet.getString("outlet_desc"));
                outletDTO.setShortDesc(resultSet.getString("short_desc"));
                outletDTO.setClusterId(resultSet.getInt("cluster_id"));
                outletDTO.setClusterName(resultSet.getString("cluster_desc"));
                outletDTO.setTemplateName(resultSet.getString("template_desc"));
                outletDTO.setRegionId(resultSet.getInt("region_id"));
                outletDTO.setRegionName(resultSet.getString("region_desc"));
                outletDTO.setCompanyId(resultSet.getInt("company_id"));
                outletDTO.setCompanyName(resultSet.getString("company_desc"));
                outletDTO.setGroupName(resultSet.getString("group_desc"));
                outletDTO.setGroupId(resultSet.getInt("group_id"));
                outletDTO.setPosStoreId(resultSet.getInt("pos_store_id"));
                outletDTO.setTemplateId(resultSet.getInt("template_id"));
                index++;
                outletDTOs.add(outletDTO);
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
        return outletDTOs;
    }

    public OutletDTO getOutletById(int outletId) throws SQLException, OutletNotFoundException {
        Connection connection = null;
        Statement statement = null;
        OutletDTO outletDTO = new OutletDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select o.id,o.outlet_desc,o.short_desc,o.cluster_id,o.region_id,o.company_id\n" +
                    ",o.group_id,o.pos_store_id,s.table_no_range,c.cluster_desc,r.region_desc,cp.company_desc\n" +
                    ",g.group_desc,m.template_id,t.template_desc,s.banner_url,s.mobile_no_length from outlet_template_link m\n" +
                    "left join outlet o\n" +
                    "on m.outlet_id=o.id\n" +
                    "left join cluster c\n" +
                    "on c.id=o.cluster_id\n" +
                    "left join region r\n" +
                    "on r.id=o.region_id\n" +
                    "left join company cp\n" +
                    "on cp.id=o.company_id\n" +
                    "left join groups g\n" +
                    "on g.id=o.group_id\n" +
                    "left join template t\n" +
                    "on t.template_id=m.template_id\n" +
                    "left join outlet_setting s\n" +
                    "on s.outlet_id=o.id\n" +
                    "where o.id=").append(outletId);
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            int index = 1;
            while (resultSet.next()) {

                outletDTO.setId(resultSet.getInt("id"));
                outletDTO.setOutletDesc(resultSet.getString("outlet_desc"));
                outletDTO.setShortDesc(resultSet.getString("short_desc"));
                outletDTO.setClusterId(resultSet.getInt("cluster_id"));
                outletDTO.setClusterName(resultSet.getString("cluster_desc"));
                outletDTO.setTemplateName(resultSet.getString("template_desc"));
                outletDTO.setRegionId(resultSet.getInt("region_id"));
                outletDTO.setRegionName(resultSet.getString("region_desc"));
                outletDTO.setCompanyId(resultSet.getInt("company_id"));
                outletDTO.setCompanyName(resultSet.getString("company_desc"));
                outletDTO.setGroupName(resultSet.getString("group_desc"));
                outletDTO.setBannerUrl(resultSet.getString("banner_url"));
                outletDTO.setTableNoRange(resultSet.getString("table_no_range"));
                outletDTO.setGroupId(resultSet.getInt("group_id"));
                outletDTO.setMobileNoLength(resultSet.getInt("mobile_no_length"));
                outletDTO.setPosStoreId(resultSet.getInt("pos_store_id"));
                outletDTO.setTemplateId(resultSet.getInt("template_id"));
                index++;
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
        return outletDTO;
    }

    public OutletDTO getOutletByStoreId(int storeId) throws SQLException, OutletNotFoundException {
        Connection connection = null;
        Statement statement = null;
        OutletDTO outletDTO = new OutletDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select o.id,o.outlet_desc,o.short_desc,o.cluster_id,o.region_id,o.company_id\n" +
                    ",o.group_id,o.pos_store_id,s.table_no_range,c.cluster_desc,r.region_desc,cp.company_desc\n" +
                    ",g.group_desc,m.template_id,t.template_desc,s.banner_url,s.mobile_no_length from outlet_template_link m\n" +
                    "left join outlet o\n" +
                    "on m.outlet_id=o.id\n" +
                    "left join cluster c\n" +
                    "on c.id=o.cluster_id\n" +
                    "left join region r\n" +
                    "on r.id=o.region_id\n" +
                    "left join company cp\n" +
                    "on cp.id=o.company_id\n" +
                    "left join groups g\n" +
                    "on g.id=o.group_id\n" +
                    "left join template t\n" +
                    "on t.template_id=m.template_id\n" +
                    "left join outlet_setting s\n" +
                    "on s.outlet_id=o.id\n" +
                    "where o.pos_store_id=").append(storeId);
            ResultSet resultSet = statement.executeQuery(query.toString()
                    .trim());
            int index = 1;
            while (resultSet.next()) {

                outletDTO.setId(resultSet.getInt("id"));
                outletDTO.setOutletDesc(resultSet.getString("outlet_desc"));
                outletDTO.setShortDesc(resultSet.getString("short_desc"));
                outletDTO.setClusterId(resultSet.getInt("cluster_id"));
                outletDTO.setClusterName(resultSet.getString("cluster_desc"));
                outletDTO.setTemplateName(resultSet.getString("template_desc"));
                outletDTO.setRegionId(resultSet.getInt("region_id"));
                outletDTO.setRegionName(resultSet.getString("region_desc"));
                outletDTO.setCompanyId(resultSet.getInt("company_id"));
                outletDTO.setCompanyName(resultSet.getString("company_desc"));
                outletDTO.setGroupName(resultSet.getString("group_desc"));
                outletDTO.setBannerUrl(resultSet.getString("banner_url"));
                outletDTO.setTableNoRange(resultSet.getString("table_no_range"));
                outletDTO.setGroupId(resultSet.getInt("group_id"));
                outletDTO.setMobileNoLength(resultSet.getInt("mobile_no_length"));
                outletDTO.setPosStoreId(resultSet.getInt("pos_store_id"));
                outletDTO.setTemplateId(resultSet.getInt("template_id"));
                index++;
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
        return outletDTO;
    }

    public Boolean updateSettings(UpdateSettingsDTO updateSettingsDTO, int outletId) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE outlet_setting SET table_no_range=?, mobile_no_length =?, banner_url =? WHERE outlet_id =?");

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getTableNoRange());

            preparedStatement.setInt(parameterIndex++, updateSettingsDTO.getMobileNoLength());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getBannerUrl());

            preparedStatement.setInt(parameterIndex++, outletId);

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
