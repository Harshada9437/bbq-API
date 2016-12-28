package com.barbeque.requesthandler;

import com.barbeque.dao.customer.CustomerDAO;
import com.barbeque.dto.request.CustomerDTO;
import com.barbeque.bo.CustomerRequestBO;
import com.barbeque.bo.UpdateCustomerRequestBO;
import com.barbeque.response.customer.CustomerResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 12/15/2016.
 */
public class CustomerRequestHandler {
    public Integer addCustomer(CustomerRequestBO customerRequestBO) throws SQLException {
        CustomerDAO customerDAO = new CustomerDAO();
        int id = customerDAO.addCustomer(buildRequestDTOFromBO(customerRequestBO));
        return id;
    }

    private CustomerDTO buildRequestDTOFromBO(CustomerRequestBO customerRequestBO) {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setName(customerRequestBO.getName());
        customerDTO.setPhoneNo(customerRequestBO.getPhoneNo());
        customerDTO.setEmailId(customerRequestBO.getEmailId());
        customerDTO.setDob(customerRequestBO.getDob());
        customerDTO.setDoa(customerRequestBO.getDoa());

        return customerDTO;
    }

    public boolean updateCustomer(UpdateCustomerRequestBO updateCustomerRequestBO) {
        Boolean isProcessed = Boolean.FALSE;
        CustomerDAO customerDAO = new CustomerDAO();
        try {
            isProcessed = customerDAO.updateQuestion(buildDTOFromBO(updateCustomerRequestBO));
        } catch (SQLException sq) {
            isProcessed = false;
        }
        return isProcessed;
    }

    private CustomerDTO buildDTOFromBO(UpdateCustomerRequestBO updateCustomerRequestBO) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(updateCustomerRequestBO.getId());
        customerDTO.setName(updateCustomerRequestBO.getName());
        customerDTO.setPhoneNo(updateCustomerRequestBO.getPhoneNo());
        customerDTO.setEmailId(updateCustomerRequestBO.getEmailId());
        customerDTO.setDob(updateCustomerRequestBO.getDob());
        customerDTO.setDoa(updateCustomerRequestBO.getDoa());
        return customerDTO;
    }

    public List<CustomerResponse> getCustomerList() {
        CustomerDAO customerDAO = new CustomerDAO();
        List<CustomerResponse> customerList = new ArrayList<CustomerResponse>();
        try {
            List<CustomerDTO> customerRequestDTOList = customerDAO.getCustomerList();

            for (CustomerDTO customerDTO : customerRequestDTOList) {
                CustomerResponse customerResponse = new CustomerResponse(customerDTO.getId(),
                        customerDTO.getName(),
                        customerDTO.getPhoneNo(),
                        customerDTO.getEmailId(),
                        customerDTO.getDob(),
                        customerDTO.getDoa(),
                        customerDTO.getCreatedOn(),
                        customerDTO.getModifiedOn()
                );
                customerList.add(customerResponse);
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return customerList;
    }
}
