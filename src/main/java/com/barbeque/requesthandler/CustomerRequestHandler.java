package com.barbeque.requesthandler;

import com.barbeque.dao.customer.CustomerDAO;
import com.barbeque.dto.request.CustomerDTO;
import com.barbeque.bo.UpdateCustomerRequestBO;
import com.barbeque.response.customer.CustomerResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 12/15/2016.
 */
public class CustomerRequestHandler {

    public boolean updateCustomer(UpdateCustomerRequestBO updateCustomerRequestBO) throws SQLException {

        CustomerDAO customerDAO = new CustomerDAO();
        Boolean isProcessed = customerDAO.updateCustomer(buildDTOFromBO(updateCustomerRequestBO));
        return isProcessed;
    }

    private CustomerDTO buildDTOFromBO(UpdateCustomerRequestBO updateCustomerRequestBO) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(updateCustomerRequestBO.getId());
        customerDTO.setName(updateCustomerRequestBO.getName());
        customerDTO.setLocality(updateCustomerRequestBO.getLocality());
        customerDTO.setPhoneNo(updateCustomerRequestBO.getPhoneNo());
        customerDTO.setEmailId(updateCustomerRequestBO.getEmailId());
        customerDTO.setDob(updateCustomerRequestBO.getDob());
        customerDTO.setDoa(updateCustomerRequestBO.getDoa());
        return customerDTO;
    }

    public List<CustomerResponse> getCustomerList() throws SQLException {
        CustomerDAO customerDAO = new CustomerDAO();
        List<CustomerResponse> customerList = new ArrayList<CustomerResponse>();
            List<CustomerDTO> customerRequestDTOList = customerDAO.getCustomerList();

            for (CustomerDTO customerDTO : customerRequestDTOList) {
                CustomerResponse customerResponse = new CustomerResponse(customerDTO.getId(),
                        customerDTO.getName(),
                        customerDTO.getLocality(),
                        customerDTO.getPhoneNo(),
                        customerDTO.getEmailId(),
                        customerDTO.getDob(),
                        customerDTO.getDoa(),
                        customerDTO.getCreatedOn(),
                        customerDTO.getModifiedOn()
                );
                customerList.add(customerResponse);
            }
        return customerList;
    }
}
