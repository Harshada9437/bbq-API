package com.barbeque.dao.outlet;

import com.barbeque.dao.ConnectionHandler;
import com.barbeque.dao.user.UsersDAO;
import com.barbeque.dto.request.UpdateSettingsDTO;
import com.barbeque.dto.request.*;
import com.barbeque.dto.response.LoginResponseDTO;
import com.barbeque.exceptions.OutletNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.sync.Outlet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletDAO {
    public Boolean assignTemplate(TempDTO tempDTO, int outletId) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO outlet_template_link(outlet_id, template_id,from_date,to_date");
        query.append(")values (?,?,?,?)");
        Boolean isCreated = Boolean.FALSE;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++, outletId);
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
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isCreated;
    }

    public static List<OutletDTO> getOutlet(String outletId, int userId) throws SQLException, UserNotFoundException {
        Connection connection = null;
        Statement statement = null;
        List<OutletDTO> outletDTOs = new ArrayList<OutletDTO>();
        try {
            String outlet;
            if(outletId == null || outletId.equals("")){
                LoginResponseDTO loginResponseDTO= UsersDAO.getuserById(userId);
                RoleRequestDTO rollRequestDTO = UsersDAO.getroleById(loginResponseDTO.getRoleId());
                outlet = rollRequestDTO.getOutletAccess();
            }else{
                outlet=outletId;
            }

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select o.id,o.outlet_desc,o.short_desc,o.cluster_id,o.region_id,o.company_id\n" +
                    ",o.group_id,o.pos_store_id,c.cluster_desc,r.region_desc,cp.company_desc\n" +
                    ",g.group_desc,m.template_id,t.template_desc from outlet o\n" +
                    "left join outlet_template_link m\n" +
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
                    "where o.id IN (" + outlet + ")");
            ResultSet resultSet = statement.executeQuery(query.toString());
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
                outletDTO.setPosStoreId(resultSet.getString("pos_store_id"));
                outletDTO.setTemplateId(resultSet.getInt("template_id"));
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

    public static List<Integer> getOutletIds() {
        Connection connection = null;
        Statement statement = null;
        List<Integer> outlets = new ArrayList<Integer>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT id FROM outlet");
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                outlets.add(id);
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
        return outlets;
    }

    public static OutletDTO getOutletById(int outletId) throws SQLException, OutletNotFoundException {
        Connection connection = null;
        Statement statement = null;
        OutletDTO outletDTO = new OutletDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select o.id,o.outlet_desc,o.short_desc,o.cluster_id,o.region_id,o.company_id\n" +
                    ",o.group_id,o.pos_store_id,s.table_no_range,c.cluster_desc,r.region_desc,cp.company_desc,s.sms_gateway_id\n" +
                    ",g.group_desc,m.template_id,t.template_desc,s.banner_url,s.mobile_no_length,s.poc_name,s.poc_mobile,s.poc_email,s.mgr_name,s.mgr_mobile,s.mgr_email from outlet o\n" +
                    "left join outlet_template_link m\n" +
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
            ResultSet resultSet = statement.executeQuery(query.toString());
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
                outletDTO.setPosStoreId(resultSet.getString("pos_store_id"));
                outletDTO.setPocEmail(resultSet.getString("poc_email"));
                outletDTO.setPocName(resultSet.getString("poc_name"));
                outletDTO.setPocMobile(resultSet.getString("poc_mobile"));
                outletDTO.setTemplateId(resultSet.getInt("template_id"));
                outletDTO.setMgrEmail(resultSet.getString("mgr_email"));
                outletDTO.setMgrName(resultSet.getString("mgr_name"));
                outletDTO.setMgrMobile(resultSet.getString("mgr_mobile"));
                outletDTO.setSmsGatewayId(resultSet.getInt("sms_gateway_id"));
                index++;
            }

            if (index == 1) {
                throw new OutletNotFoundException("Invalid id");
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

    public static OutletDTO getOutletByStoreId(String storeId) throws SQLException, OutletNotFoundException {
        Connection connection = null;
        Statement statement = null;
        OutletDTO outletDTO = new OutletDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select o.id,o.outlet_desc,o.short_desc,o.cluster_id,o.region_id,o.company_id\n" +
                    ",o.group_id,o.pos_store_id,s.table_no_range,c.cluster_desc,r.region_desc,cp.company_desc,s.mgr_name,s.mgr_mobile,s.mgr_email\n" +
                    ",g.group_desc,m.template_id,t.template_desc,s.banner_url,s.mobile_no_length,s.poc_name,s.poc_email,s.poc_mobile,s.sms_gateway_id from outlet o\n" +
                    "left join outlet_template_link m\n" +
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
                    "where o.pos_store_id=\"").append(storeId).append("\"");
            ResultSet resultSet = statement.executeQuery(query.toString());
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
                outletDTO.setPosStoreId(resultSet.getString("pos_store_id"));
                outletDTO.setTemplateId(resultSet.getInt("template_id"));
                outletDTO.setPocEmail(resultSet.getString("poc_email"));
                outletDTO.setPocName(resultSet.getString("poc_name"));
                outletDTO.setPocMobile(resultSet.getString("poc_mobile"));
                outletDTO.setMgrEmail(resultSet.getString("mgr_email"));
                outletDTO.setMgrName(resultSet.getString("mgr_name"));
                outletDTO.setMgrMobile(resultSet.getString("mgr_mobile"));
                outletDTO.setSmsGatewayId(resultSet.getInt("sms_gateway_id"));
                index++;
            }
            if (index == 1) {
                throw new OutletNotFoundException("Invalid id");
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
                    .prepareStatement("UPDATE outlet_setting SET table_no_range=?, mobile_no_length =?, banner_url =?," +
                            " poc_name=?, poc_mobile=?, poc_email=?,mgr_name=?,mgr_mobile=?,mgr_email=?," +
                            "sms_gateway_id=? WHERE outlet_id =?");

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getTableNoRange());

            preparedStatement.setInt(parameterIndex++, updateSettingsDTO.getMobileNoLength());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getBannerUrl());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getPocName());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getPocMobile());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getPocEmail());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getMgrName());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getMgrMobile());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getMgrEmail());

            preparedStatement.setInt(parameterIndex++, updateSettingsDTO.getSmsGatewayId());

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

    public static void createOutlet(Outlet outlet) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("INSERT INTO outlet(id , outlet_desc, short_desc," +
                            " company_id, cluster_id, region_id, group_id, pos_store_id) " +
                            "values (?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(parameterIndex++,
                    outlet.getId());
            preparedStatement.setString(parameterIndex++,
                    outlet.getOutletDesc());
            preparedStatement.setString(parameterIndex++,
                    outlet.getShortDesc());
            preparedStatement.setInt(parameterIndex++,
                    outlet.getCompanyId());
            preparedStatement.setInt(parameterIndex++,
                    outlet.getClusterId());
            preparedStatement.setInt(parameterIndex++,
                    outlet.getRegionId());
            preparedStatement.setInt(parameterIndex++,
                    outlet.getGroupId());
            preparedStatement.setString(parameterIndex++,
                    outlet.getPosStoreId());


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

    public static void updateOutlet(Outlet outlet) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE outlet SET  outlet_desc=?, short_desc=?," +
                            " company_id=?, cluster_id=?, region_id=?, group_id=?, pos_store_id=? WHERE id=?");

            preparedStatement.setString(parameterIndex++,
                    outlet.getOutletDesc());
            preparedStatement.setString(parameterIndex++,
                    outlet.getShortDesc());
            preparedStatement.setInt(parameterIndex++,
                    outlet.getCompanyId());
            preparedStatement.setInt(parameterIndex++,
                    outlet.getClusterId());
            preparedStatement.setInt(parameterIndex++,
                    outlet.getRegionId());
            preparedStatement.setInt(parameterIndex++,
                    outlet.getGroupId());
            preparedStatement.setString(parameterIndex++,
                    outlet.getPosStoreId());
            preparedStatement.setInt(parameterIndex++,
                    outlet.getId());


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

    public static UpdateSettingsDTO getSetting(int outletId) {
        Connection connection = null;
        Statement statement = null;
        UpdateSettingsDTO updateSettingsDTO = null;
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT * FROM outlet_setting where outlet_id=" + outletId);
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                updateSettingsDTO = new UpdateSettingsDTO();
                updateSettingsDTO.setPocEmail(resultSet.getString("poc_email"));
                updateSettingsDTO.setPocName(resultSet.getString("poc_name"));
                updateSettingsDTO.setPocMobile(resultSet.getString("poc_mobile"));
                updateSettingsDTO.setMgrEmail(resultSet.getString("mgr_email"));
                updateSettingsDTO.setMgrName(resultSet.getString("mgr_name"));
                updateSettingsDTO.setMgrMobile(resultSet.getString("mgr_mobile"));
                updateSettingsDTO.setBannerUrl(resultSet.getString("banner_url"));
                updateSettingsDTO.setMobileNoLength(resultSet.getInt("mobile_no_length"));
                updateSettingsDTO.setSmsGatewayId(resultSet.getInt("sms_gateway_id"));
                updateSettingsDTO.setTableNoRange(resultSet.getString("table_no_range"));

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
        return updateSettingsDTO;
    }

    public Boolean createSettings(UpdateSettingsDTO updateSettingsDTO, int outletId) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("INSERT  INTO outlet_setting (table_no_range, mobile_no_length , banner_url, outlet_id, poc_name, poc_mobile, poc_email,mgr_name,mgr_mobile,mgr_email,sms_gateway_id) VALUES(?,?,?,?,?,?,?,?,?,?,?) ");

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getTableNoRange());

            preparedStatement.setInt(parameterIndex++, updateSettingsDTO.getMobileNoLength());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getBannerUrl());

            preparedStatement.setInt(parameterIndex++, outletId);

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getPocName());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getPocMobile());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getPocEmail());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getMgrName());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getMgrMobile());

            preparedStatement.setString(parameterIndex++, updateSettingsDTO.getMgrEmail());

            preparedStatement.setInt(parameterIndex++, updateSettingsDTO.getSmsGatewayId());

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
