package com.barbeque.service;

import com.barbeque.bo.DeviceRequestBO;
import com.barbeque.bo.DeviceStatusRequestBO;
import com.barbeque.bo.UpdateDeviceRequestBO;
import com.barbeque.dao.device.DeviceDAO;
import com.barbeque.request.device.DeviceRequest;
import com.barbeque.request.device.DeviceStatusRequest;
import com.barbeque.request.device.UpdateDeviceRequest;
import com.barbeque.requesthandler.DeviceRequestHandler;
import com.barbeque.response.device.DeviceResponseList;
import com.barbeque.response.util.MessageResponse;
import com.barbeque.response.util.ResponseGenerator;

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
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDevice(DeviceRequest deviceRequest) {
        DeviceRequestBO deviceRequestBO = new DeviceRequestBO();
        MessageResponse messageResponse = new MessageResponse();

        deviceRequestBO.setAndroidVersion(deviceRequest.getAndroidVersion());
        deviceRequestBO.setInstallationDate(deviceRequest.getInstallationDate());
        deviceRequestBO.setModel(deviceRequest.getModel());
        deviceRequestBO.setSerialNo(deviceRequest.getSerialNo());

        try {
            if(!DeviceDAO.getDeviceBySerialNo( deviceRequestBO.getSerialNo())) {
                DeviceRequestHandler deviceRequestHandler = new DeviceRequestHandler();
                int deviceId = deviceRequestHandler.addDevice(deviceRequestBO);

                if (deviceId > 0) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(deviceId));

                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to add the device.");
                }
            }else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Device already exist with same serial number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to add the device.");
        }
    }

    @POST
    @Path("/install")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDevice(UpdateDeviceRequest deviceRequest) {
        UpdateDeviceRequestBO deviceRequestBO = new UpdateDeviceRequestBO();
        MessageResponse messageResponse = new MessageResponse();

        deviceRequestBO.setAndroidVersion(deviceRequest.getAndroidVersion());
        deviceRequestBO.setId(deviceRequest.getId());
        deviceRequestBO.setInstallationDate(deviceRequest.getInstallationDate());
        deviceRequestBO.setModel(deviceRequest.getModel());

        try {
            DeviceRequestHandler deviceRequestHandler = new DeviceRequestHandler();
            Boolean isCreate = deviceRequestHandler.installDevice(deviceRequestBO);
            if (isCreate) {
                return ResponseGenerator.generateSuccessResponse(messageResponse, "Device updated successfully.");

            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to add the device.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to add the device.");
        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response installDevice(DeviceStatusRequest deviceRequest) {
        DeviceStatusRequestBO deviceRequestBO = new DeviceStatusRequestBO();
        MessageResponse messageResponse = new MessageResponse();

        deviceRequestBO.setId(deviceRequest.getId());
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
        }
    }

    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeviceList() throws Exception {
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


    @GET
    @Path("/validate/{serial_no}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeviceList(@PathParam("serial_no") String serialNo) {
        DeviceRequestHandler deviceRequestHandler = new DeviceRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            int id = deviceRequestHandler.getValidDevice(serialNo);
            if(id > 0) {
                return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(id));
            }else{
                return ResponseGenerator.generateFailureResponse(messageResponse,"This device doesn't support this app.");
            }
        }catch (SQLException e){
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse,"Unable to validate the device.");
        }
    }

}
