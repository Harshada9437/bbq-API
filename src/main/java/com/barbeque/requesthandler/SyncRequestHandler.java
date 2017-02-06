package com.barbeque.requesthandler;

import com.barbeque.bo.SettingRequestBO;
import com.barbeque.dao.Sync.*;
import com.barbeque.dao.outlet.OutletDAO;
import com.barbeque.dto.VersionInfoDTO;
import com.barbeque.dto.request.SettingRequestDTO;
import com.barbeque.response.user.SettingResponse;
import com.barbeque.response.util.VersionInfoResponse;
import com.barbeque.sync.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by System-2 on 1/9/2017.
 */
public class SyncRequestHandler {

    public VersionInfoResponse getAndroidVersion() throws SQLException{
        VersionInfoResponse versionInfoResponse = new VersionInfoResponse();
        VersionDAO versionDAO = new VersionDAO();
        VersionInfoDTO versionInfoDTO = versionDAO.getAndroidVersion();
        versionInfoResponse.setVersionCode(versionInfoDTO.getVersionCode());
        versionInfoResponse.setVersionNumber(versionInfoDTO.getVersionNumber());
        return  versionInfoResponse;
    }


    public void syncData(Data data) throws SQLException {
        List<Group> groups = data.getGroups();
        List<Integer> oldGroup = GroupDAO.getGroups();
            for (Group group : groups) {
                if (oldGroup.contains(group.getId())) {
                    GroupDAO.updateGroup(group);
                } else {
                    GroupDAO.createGroup(group);
                }
        }

        List<Company> companies = data.getCompanies();
        List<Integer> oldCompany = CompanyDAO.getCompany();
            for (Company company : companies) {
                    if (oldCompany.contains(company.getId())) {
                        CompanyDAO.updateCompany(company);
                    } else {
                        CompanyDAO.createCompany(company);
                    }
        }

        List<Regions> regions = data.getRegions();
        List<Integer> oldRegions = RegionDAO.getRegions();
            for (Regions region : regions) {
                    if (oldRegions.contains(region.getId())) {
                        RegionDAO.updateRegions(region);
                    } else {
                        RegionDAO.createRegion(region);
                    }
        }

        List<Cluster> clusters = data.getClusters();
        List<Integer> oldCluster = ClusterDAO.getClusters();
            for (Cluster cluster : clusters) {
                    if (oldCluster.contains(cluster.getId())) {
                        ClusterDAO.updateCluster(cluster);
                    } else {
                        ClusterDAO.createCluster(cluster);
                    }
        }

        List<Outlet> outlets = data.getOutlets();
        List<Integer> oldOutlet = OutletDAO.getOutlates();
            for (Outlet outlet : outlets) {
                    if (oldOutlet.contains(outlet.getId())) {
                        OutletDAO.updateOutlet(outlet);
                    } else {
                        OutletDAO.createOutlet(outlet);
                    }
            }
    }

    public Boolean saveSetting(SettingRequestBO settingRequestBO) throws SQLException {
        SettingRequestDTO settingRequestDTO = new SettingRequestDTO();
        settingRequestDTO.setSmsTemplate(settingRequestBO.getSmsTemplate());
        Boolean isProcessed = VersionDAO.saveSetting(settingRequestDTO);
        return  isProcessed;
    }

    public SettingResponse fetchSettings() throws SQLException {
        SettingResponse settingResponse = new SettingResponse();
        VersionDAO versionDAO = new VersionDAO();
        SettingRequestDTO settingRequestDTO = versionDAO.fetchSettings();
        settingResponse.setSmsTemplate(settingRequestDTO.getSmsTemplate());
        return  settingResponse;
    }
}
