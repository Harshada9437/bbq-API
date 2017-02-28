package com.barbeque.requesthandler;

import com.barbeque.bo.SettingRequestBO;
import com.barbeque.bo.SmsSettingRequestBO;
import com.barbeque.bo.UpdateSettingRequestBO;
import com.barbeque.dao.Sync.*;
import com.barbeque.dao.outlet.OutletDAO;
import com.barbeque.dto.request.VersionInfoDTO;
import com.barbeque.dto.request.SettingRequestDTO;
import com.barbeque.dto.request.SmsSettingDTO;
import com.barbeque.response.user.SettingResponse;
import com.barbeque.response.user.SmsSettingResponse;
import com.barbeque.response.util.VersionInfoResponse;
import com.barbeque.sync.*;

import java.sql.SQLException;
import java.util.ArrayList;
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


    public void syncData(Data data) throws Exception {
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
        List<Integer> oldOutlet = OutletDAO.getOutletIds();
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
        Boolean isProcessed = SmsDAO.saveSetting(settingRequestDTO);
        return  isProcessed;
    }

    public SettingResponse fetchSettings() throws SQLException {
        SettingResponse settingResponse = new SettingResponse();
        SettingRequestDTO settingRequestDTO = SmsDAO.fetchSettings();
        settingResponse.setSmsTemplate(settingRequestDTO.getSmsTemplate());
        return  settingResponse;
    }

    public Integer saveSmsSettings(SmsSettingRequestBO settingRequestBO) throws SQLException {
        SmsSettingDTO settingRequestDTO = new SmsSettingDTO();
        settingRequestDTO.setApi(settingRequestBO.getApi());
        settingRequestDTO.setSenderId(settingRequestBO.getSenderId());
        settingRequestDTO.setCampaign(settingRequestBO.getCampaign());
        settingRequestDTO.setCountryCode(settingRequestBO.getCountryCode());
        settingRequestDTO.setName(settingRequestBO.getName());
        Integer id = SmsDAO.saveSmsSettings(settingRequestDTO);
        return  id;
    }

    public List<SmsSettingResponse> fetchSmsSettings() throws SQLException {
        List<SmsSettingResponse> responses = new ArrayList<SmsSettingResponse>();
        List<SmsSettingDTO> smsSettingDTO = SmsDAO.fetchSmsSettings();

        for(SmsSettingDTO dto : smsSettingDTO) {
            SmsSettingResponse response = new SmsSettingResponse(
                    dto.getId(),
                    dto.getApi(),
                    dto.getName(),
                    dto.getSenderId(),
                    dto.getCampaign(),
                    dto.getCountryCode());
            responses.add(response);
        }
        return  responses;
    }

    public void updateSmsSettings(UpdateSettingRequestBO settingRequestBO) throws SQLException{
        SmsSettingDTO settingRequestDTO = new SmsSettingDTO();
        settingRequestDTO.setApi(settingRequestBO.getApi());
        settingRequestDTO.setSenderId(settingRequestBO.getSenderId());
        settingRequestDTO.setCampaign(settingRequestBO.getCampaign());
        settingRequestDTO.setCountryCode(settingRequestBO.getCountryCode());
        settingRequestDTO.setName(settingRequestBO.getName());
        settingRequestDTO.setId(settingRequestBO.getId());
        SmsDAO.updateSmsSettings(settingRequestDTO);
    }
}
