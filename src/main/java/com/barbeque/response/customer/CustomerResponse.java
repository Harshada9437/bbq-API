package com.barbeque.response.customer;

/**
 * Created by System-2 on 12/15/2016.
 */
public class CustomerResponse {
    private int id;
    private String name;
    private String locality;
    private String phoneNo;
    private String emailId;
    private String dob;
    private String doa;
    private String createdOn;
    private String modifiedOn;

    public String getName() {return name;}

    public String getPhoneNo() {return phoneNo;}

    public String getEmailId() {return emailId;}

    public String getDob() {return dob;}

    public String getDoa() {return doa;}

    public int getId() {return id;}

    public String getCreatedOn() {return createdOn;}

    public String getModifiedOn() {return modifiedOn;}

    public String getLocality() {return locality;}

    public CustomerResponse(int id, String name, String locality, String phoneNo, String emailId, String dob, String doa, String createdOn, String modifiedOn) {
        this.id = id;
        this.name = name;
        this.locality = locality;
        this.phoneNo = phoneNo;
        this.emailId = emailId;
        this.dob = dob;
        this.doa = doa;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }

    @Override
    public String toString() {
        return "CustomerResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", locality='" + locality + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", emailId='" + emailId + '\'' +
                ", dob='" + dob + '\'' +
                ", doa='" + doa + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", modifiedOn='" + modifiedOn + '\'' +
                '}';
    }
}




