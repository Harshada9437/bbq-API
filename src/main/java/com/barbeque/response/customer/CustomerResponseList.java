package com.barbeque.response.customer;

import com.barbeque.response.util.GenericResponse;
import java.util.List;

/**
 * Created by System-2 on 12/15/2016.
 */
public class CustomerResponseList implements GenericResponse {
    private List<CustomerResponse> customers;
    private String messageType;
    private String message;

    public List<CustomerResponse> getCustomers() {return customers;}

    public void setCustomers(List<CustomerResponse> customers) {this.customers = customers;}

    public String getMessageType() {return messageType;}

    public void setMessageType(String messageType) {this.messageType = messageType;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    @Override
    public String toString() {
        return "CustomerResponseList{" +
                "customers=" + customers +
                ", messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
