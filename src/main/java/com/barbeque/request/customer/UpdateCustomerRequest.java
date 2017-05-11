package com.barbeque.request.customer;

/**
 * Created by System-2 on 12/15/2016.
 */
public class UpdateCustomerRequest {
    private int id;
    private String name;
    private String phoneNo;
    private String emailId;
    private String dob;
    private String doa;
    private int clientId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoa() {
        return doa;
    }

    public void setDoa(String doa) {
        this.doa = doa;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateCustomerRequest that = (UpdateCustomerRequest) o;

        if (id != that.id) return false;
        if (clientId != that.clientId) return false;
        if (!name.equals(that.name)) return false;
        if (!phoneNo.equals(that.phoneNo)) return false;
        if (!emailId.equals(that.emailId)) return false;
        if (!dob.equals(that.dob)) return false;
        return doa.equals(that.doa);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + phoneNo.hashCode();
        result = 31 * result + emailId.hashCode();
        result = 31 * result + dob.hashCode();
        result = 31 * result + doa.hashCode();
        result = 31 * result + clientId;
        return result;
    }

    @Override
    public String toString() {
        return "UpdateCustomerRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", emailId='" + emailId + '\'' +
                ", dob='" + dob + '\'' +
                ", doa='" + doa + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
