package com.barbeque.response.customer;

/**
 * Created by System-2 on 12/15/2016.
 */
public class CustomerResponse {
    private int id;
    private String name;
    private String phoneNo;
    private String emailId;
    private String dob;
    private String doa;
    private String CreatedOn;
    private String ModifiedOn;

    public String getName() {return name;}

    public String getPhoneNo() {return phoneNo;}

    public String getEmailId() {return emailId;}

    public String getDob() {return dob;}

    public String getDoa() {return doa;}

    public int getId() {return id;}

    public String getCreatedOn() {return CreatedOn;}

    public String getModifiedOn() {return ModifiedOn;}

    public CustomerResponse(int id, String name, String phoneNo, String emailId, String dob, String doa, String createdOn, String modifiedOn) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.emailId = emailId;
        this.dob = dob;
        this.doa = doa;
        CreatedOn = createdOn;
        ModifiedOn = modifiedOn;
    }

    @Override
    public String toString() {
        return "CustomerResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", emailId='" + emailId + '\'' +
                ", dob='" + dob + '\'' +
                ", doa='" + doa + '\'' +
                ", CreatedOn='" + CreatedOn + '\'' +
                ", ModifiedOn='" + ModifiedOn + '\'' +
                '}';
    }
}




