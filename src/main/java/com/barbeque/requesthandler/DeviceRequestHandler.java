package com.barbeque.requesthandler;

import com.barbeque.bo.DeviceRequestBO;
import com.barbeque.bo.DeviceStatusRequestBO;
import com.barbeque.bo.RegisterRequestBO;
import com.barbeque.bo.UpdateDeviceRequestBO;
import com.barbeque.dao.device.DeviceDAO;
import com.barbeque.dao.outlet.OutletDAO;
import com.barbeque.dto.UpdateSettingsDTO;
import com.barbeque.dto.request.DeviceDTO;
import com.barbeque.dto.request.RegisterDTO;
import com.barbeque.response.device.DeviceResponse;
import com.barbeque.util.EmailService;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceRequestHandler {

    public List<DeviceResponse> getDeviceList() throws SQLException {
        DeviceDAO deviceDAO = new DeviceDAO();
        List<DeviceDTO> deviceDTOs = deviceDAO.getDeviceList();
        List<DeviceResponse> deviceList = new ArrayList<DeviceResponse>();
        for (DeviceDTO deviceDTO : deviceDTOs){
            DeviceResponse deviceResponse = new DeviceResponse(deviceDTO.getStoreId(),
                    deviceDTO.getId(),
                    deviceDTO.getFeedbackId(),
                    deviceDTO.getInstallationId(),
                    deviceDTO.getFingerprint(),
                    deviceDTO.getAndroidDeviceId(),
                    deviceDTO.getInstallationDate(),
                    deviceDTO.getStatus(),
                    deviceDTO.getFeedbackDate());
            deviceList.add(deviceResponse);
        }
        return deviceList;
    }

    public Boolean getValidDevice(RegisterRequestBO registerRequestBO, int outletId) throws SQLException {
        DeviceDAO deviceDAO = new DeviceDAO();
        int otp = Integer.parseInt(random(6));
        Boolean isCreate = deviceDAO.getValidDevice(biuildDTOFromBO(registerRequestBO),otp);
        if(isCreate){
            UpdateSettingsDTO updateSettingsDTO = OutletDAO.getSetting(outletId);
            EmailService.sendOtp(updateSettingsDTO.getPocEmail(),updateSettingsDTO.getPocName(),otp);
        }
        return isCreate;
    }

    private RegisterDTO biuildDTOFromBO(RegisterRequestBO registerRequestBO) {
        RegisterDTO registerDTO = new RegisterDTO();

        registerDTO.setAndroidDeviceId(registerRequestBO.getAndroidDeviceId());
        registerDTO.setStoreId(registerRequestBO.getStoreId());
        registerDTO.setInstallationId(registerRequestBO.getInstallationId());

        return registerDTO;
    }

    public static String random(int size) {

        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            // Generate 20 integers 0..20
            for (int i = 0; i < size; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedToken.toString();
    }

    public Integer verifyDevice(UpdateDeviceRequestBO deviceRequestBO) throws SQLException {
        DeviceDAO deviceDAO=new DeviceDAO();
        int id = deviceDAO.verifyDevice(buildUpdateDtofromBo(deviceRequestBO));
        return id;
    }

    private DeviceDTO buildUpdateDtofromBo(UpdateDeviceRequestBO deviceRequestBO) {
        DeviceDTO deviceDTO = new DeviceDTO();

        deviceDTO.setOtp(deviceRequestBO.getOtp());
        deviceDTO.setFingerprint(deviceRequestBO.getFingerprint());
        deviceDTO.setInstallationId(deviceRequestBO.getInstallationId());
        deviceDTO.setAndroidDeviceId(deviceRequestBO.getAndroidDeviceId());
        deviceDTO.setStoreId(deviceRequestBO.getStoreId());

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
