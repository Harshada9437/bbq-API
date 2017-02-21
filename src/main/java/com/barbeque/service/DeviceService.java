package com.barbeque.service;

import com.barbeque.bo.DeviceStatusRequestBO;
import com.barbeque.bo.RegisterRequestBO;
import com.barbeque.bo.UpdateDeviceRequestBO;
import com.barbeque.dao.device.DeviceDAO;
import com.barbeque.dao.outlet.OutletDAO;
import com.barbeque.dto.request.UpdateSettingsDTO;
import com.barbeque.dto.request.OutletDTO;
import com.barbeque.exceptions.DeviceNotFoundException;
import com.barbeque.exceptions.OutletNotFoundException;
import com.barbeque.request.device.DeviceStatusRequest;
import com.barbeque.request.device.RegisterRequest;
import com.barbeque.request.device.UpdateDeviceRequest;
import com.barbeque.requesthandler.DeviceRequestHandler;
import com.barbeque.response.device.DeviceResponseList;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.util.ResponseGenerator;
import com.barbeque.util.EmailService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by System-2 on 1/17/2017.
 */
@Path("/device")
public class DeviceService {
    @POST
    @Path("/verify")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyDevice(UpdateDeviceRequest deviceRequest) {
        UpdateDeviceRequestBO deviceRequestBO = new UpdateDeviceRequestBO();
        MessageResponse messageResponse = new MessageResponse();

        deviceRequestBO.setAndroidDeviceId(deviceRequest.getAndroidDeviceId());
        deviceRequestBO.setInstallationId(deviceRequest.getInstallationId());
        deviceRequestBO.setOtp(deviceRequest.getOtp());
        deviceRequestBO.setStoreId(deviceRequest.getStoreId());
        deviceRequestBO.setFingerprint(deviceRequest.getFingerprint());

        try {
            DeviceRequestHandler deviceRequestHandler = new DeviceRequestHandler();

            if(DeviceDAO.getValidOtp(deviceRequestBO.getInstallationId(),deviceRequestBO.getOtp())>0) {
                int id = deviceRequestHandler.verifyDevice(deviceRequestBO);
                return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(id));
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid otp.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to verify the device.");
        } catch (DeviceNotFoundException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid device id");
        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response installDevice(DeviceStatusRequest deviceRequest) {
        DeviceStatusRequestBO deviceRequestBO = new DeviceStatusRequestBO();
        MessageResponse messageResponse = new MessageResponse();

        deviceRequestBO.setAndroidDeviceId(deviceRequest.getAndroidDeviceId());
        deviceRequestBO.setStatus(deviceRequest.getStatus());

        try {
            DeviceRequestHandler deviceRequestHandler = new DeviceRequestHandler();
            Boolean isCreate = deviceRequestHandler.updateDevice(deviceRequestBO);
            if (isCreate) {
                return ResponseGenerator.generateSuccessResponse(messageResponse, "Device updated successfully.");
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to add the device.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to add the device.");
        } catch (DeviceNotFoundException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid device id");
        }
    }

    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeviceList() {
        DeviceRequestHandler deviceRequestHandler = new DeviceRequestHandler();
        DeviceResponseList deviceResponseList = new DeviceResponseList();
        try {
            deviceResponseList.setDevices(deviceRequestHandler.getDeviceList());
            return ResponseGenerator.generateSuccessResponse(deviceResponseList, "device list.");
        }catch (SQLException e){
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse,"Unable to retrieve the device list.");
        }
    }


    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerDevice(RegisterRequest registerRequest) {
        DeviceRequestHandler deviceRequestHandler = new DeviceRequestHandler();
        MessageResponse messageResponse = new MessageResponse();

        RegisterRequestBO registerRequestBO = new RegisterRequestBO();
        registerRequestBO.setAndroidDeviceId(registerRequest.getAndroidDeviceId());
        registerRequestBO.setInstallationId(registerRequest.getInstallationId());
        registerRequestBO.setStoreId(registerRequest.getStoreId());

        OutletDTO outletDTO = null;
        try {
            int otp = DeviceDAO.getValidOtp(registerRequestBO.getInstallationId(),0);
            outletDTO = OutletDAO.getOutletByStoreId(registerRequestBO.getStoreId());
            if(otp == 0) {
                if (outletDTO != null) {
                    deviceRequestHandler.getValidDevice(registerRequestBO, outletDTO.getId());
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Otp is send to registered email id");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid store id.");
                }
            }else {
                UpdateSettingsDTO updateSettingsDTO = OutletDAO.getSetting(outletDTO.getId());
                EmailService.sendOtp(updateSettingsDTO.getPocEmail(),updateSettingsDTO.getPocName(),otp);
                return ResponseGenerator.generateSuccessResponse(messageResponse, "Otp is send to registered email id");
            }
        }catch (SQLException e){
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse,"Unable to validate the device.");
        } catch (OutletNotFoundException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse,"Invalid store id.");
        }
    }

}
