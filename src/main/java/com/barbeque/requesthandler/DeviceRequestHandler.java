package com.barbeque.requesthandler;

import com.barbeque.bo.DeviceRequestBO;
import com.barbeque.bo.DeviceStatusRequestBO;
import com.barbeque.bo.UpdateDeviceRequestBO;
import com.barbeque.dao.device.DeviceDAO;
import com.barbeque.dto.request.DeviceDTO;
import com.barbeque.response.device.DeviceResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceRequestHandler {
    public Integer addDevice(DeviceRequestBO deviceRequestBO) throws SQLException {
        DeviceDAO deviceDAO=new DeviceDAO();
        int id = deviceDAO.addDevice(buildDtofromBo(deviceRequestBO));
        return id;
    }

    private DeviceDTO buildDtofromBo(DeviceRequestBO deviceRequestBO) {
        DeviceDTO deviceDTO = new DeviceDTO();

        deviceDTO.setSerialNo(deviceRequestBO.getSerialNo());
        deviceDTO.setModel(deviceRequestBO.getModel());
        deviceDTO.setInstallationDate(deviceRequestBO.getInstallationDate());
        deviceDTO.setAndroidVersion(deviceRequestBO.getAndroidVersion());

        return deviceDTO;
    }

    public List<DeviceResponse> getDeviceList() throws SQLException {
        DeviceDAO deviceDAO = new DeviceDAO();
        List<DeviceDTO> deviceDTOs = deviceDAO.getDeviceList();
        List<DeviceResponse> deviceList = new ArrayList<DeviceResponse>();
        for (DeviceDTO deviceDTO : deviceDTOs){
            DeviceResponse deviceResponse = new DeviceResponse(
                    deviceDTO.getId(),
                    deviceDTO.getFeedbackId(),
                    deviceDTO.getSerialNo(),
                    deviceDTO.getModel(),
                    deviceDTO.getAndroidVersion(),
                    deviceDTO.getInstallationDate(),
                    deviceDTO.getStatus(),
                    deviceDTO.getFeedbackDate());
            deviceList.add(deviceResponse);
        }
        return deviceList;
    }

    public Integer getValidDevice(String serialNo) throws SQLException {
        DeviceDAO deviceDAO = new DeviceDAO();
        int id = deviceDAO.getValidDevice(serialNo);
        return id;
    }

    public Boolean installDevice(UpdateDeviceRequestBO deviceRequestBO) throws SQLException {
        DeviceDAO deviceDAO=new DeviceDAO();
        Boolean isCreate = deviceDAO.installDevice(buildUpdateDtofromBo(deviceRequestBO));
        return isCreate;
    }

    private DeviceDTO buildUpdateDtofromBo(UpdateDeviceRequestBO deviceRequestBO) {
        DeviceDTO deviceDTO = new DeviceDTO();

        deviceDTO.setId(deviceRequestBO.getId());
        deviceDTO.setModel(deviceRequestBO.getModel());
        deviceDTO.setInstallationDate(deviceRequestBO.getInstallationDate());
        deviceDTO.setAndroidVersion(deviceRequestBO.getAndroidVersion());

        return deviceDTO;
    }

    public Boolean updateDevice(DeviceStatusRequestBO deviceRequestBO) throws SQLException {
        DeviceDAO deviceDAO=new DeviceDAO();
        Boolean isCreate = deviceDAO.updateDevice(buildStatusDtofromBo(deviceRequestBO));
        return isCreate;
    }

    private DeviceDTO buildStatusDtofromBo(DeviceStatusRequestBO deviceRequestBO) {
        DeviceDTO deviceDTO = new DeviceDTO();

        deviceDTO.setId(deviceRequestBO.getId());
        deviceDTO.setStatus(deviceRequestBO.getStatus());
        return deviceDTO;
    }
}
