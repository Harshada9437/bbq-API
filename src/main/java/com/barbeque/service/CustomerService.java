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
    @Path("/create")
    public Response addCustomer(CustomerRequest customerRequest) throws SQLException {
        CustomerRequestBO customerRequestBO = new CustomerRequestBO();
        customerRequestBO.setName(customerRequest.getName());
        customerRequestBO.setPhoneNo(customerRequest.getPhoneNo());
        customerRequestBO.setEmailId(customerRequest.getEmailId());
        customerRequestBO.setDob(customerRequest.getDob());
        customerRequestBO.setDoa(customerRequest.getDoa());

        MessageResponse messageResponse = new MessageResponse();
        CustomerRequestHandler customerRequestHandler = new CustomerRequestHandler();
        try {
            String mobile = customerRequestBO.getPhoneNo();
            int isExistingCustomer = CustomerDAO.getValidationForPhoneNumber(mobile);
            if (isExistingCustomer == 0) {
                int customerId = customerRequestHandler.addCustomer(customerRequestBO);
                return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(customerId));
            } else {
                UpdateCustomerRequestBO updateCustomerRequestBO = new UpdateCustomerRequestBO();
                updateCustomerRequestBO.setId(isExistingCustomer);
                updateCustomerRequestBO.setName(customerRequestBO.getName());
                updateCustomerRequestBO.setPhoneNo(customerRequestBO.getPhoneNo());
                updateCustomerRequestBO.setEmailId(customerRequestBO.getEmailId());
                updateCustomerRequestBO.setDob(customerRequestBO.getDob());
                updateCustomerRequestBO.setDoa(customerRequestBO.getDoa());
                if (customerRequestHandler.updateCustomer(updateCustomerRequestBO)) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(isExistingCustomer));
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the customer.");
                }
            }
        } catch (SQLException sqlException) {
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Customer creation failed.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateCustomer(UpdateCustomerRequest updateCustomerRequest) throws SQLException {

        UpdateCustomerRequestBO updateCustomerRequestBO = new UpdateCustomerRequestBO();
        updateCustomerRequestBO.setId(updateCustomerRequest.getId());
        updateCustomerRequestBO.setName(updateCustomerRequest.getName());
        updateCustomerRequestBO.setPhoneNo(updateCustomerRequest.getPhoneNo());
        updateCustomerRequestBO.setEmailId(updateCustomerRequest.getEmailId());
        updateCustomerRequestBO.setDob(updateCustomerRequest.getDob());
        updateCustomerRequestBO.setDoa(updateCustomerRequest.getDoa());

        CustomerRequestHandler customerRequestHandler = new CustomerRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        if (customerRequestHandler.updateCustomer(updateCustomerRequestBO)) {
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Customer updated successfully");
        } else {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the customer.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getCustomerList() throws Exception {
        CustomerRequestHandler customerRequestHandler = new CustomerRequestHandler();
        CustomerResponseList customerResponseList = new CustomerResponseList();
        customerResponseList.setCustomers(customerRequestHandler.getCustomerList());
        return ResponseGenerator.generateSuccessResponse(customerResponseList, "List of customers.");
    }
}
