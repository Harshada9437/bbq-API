package com.barbeque.service;

import com.barbeque.bo.CustomerRequestBO;
import com.barbeque.bo.UpdateCustomerRequestBO;
import com.barbeque.dao.customer.CustomerDAO;
import com.barbeque.request.customer.CustomerRequest;
import com.barbeque.request.customer.UpdateCustomerRequest;
import com.barbeque.requesthandler.CustomerRequestHandler;
import com.barbeque.response.customer.CustomerResponseList;
import com.barbeque.response.util.ResponseGenerator;
import com.barbeque.response.util.MessageResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by System-2 on 12/14/2016.
 */

@Path("/customer")
public class CustomerService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateCustomer(UpdateCustomerRequest updateCustomerRequest){

        UpdateCustomerRequestBO updateCustomerRequestBO = new UpdateCustomerRequestBO();
        updateCustomerRequestBO.setId(updateCustomerRequest.getId());
        updateCustomerRequestBO.setName(updateCustomerRequest.getName());
        updateCustomerRequestBO.setPhoneNo(updateCustomerRequest.getPhoneNo());
        updateCustomerRequestBO.setEmailId(updateCustomerRequest.getEmailId());
        updateCustomerRequestBO.setDob(updateCustomerRequest.getDob());
        updateCustomerRequestBO.setDoa(updateCustomerRequest.getDoa());

        CustomerRequestHandler customerRequestHandler = new CustomerRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (customerRequestHandler.updateCustomer(updateCustomerRequestBO)) {
                return ResponseGenerator.generateSuccessResponse(messageResponse, "Customer updated successfully");
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the customer.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getCustomerList(){
        CustomerRequestHandler customerRequestHandler = new CustomerRequestHandler();
        CustomerResponseList customerResponseList = new CustomerResponseList();
        try {
            customerResponseList.setCustomers(customerRequestHandler.getCustomerList());
            return ResponseGenerator.generateSuccessResponse(customerResponseList, "List of customers.");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(customerResponseList, "failed to retrieve.");
        }
    }
}
