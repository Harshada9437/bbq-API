package com.barbeque.bo;

/**
 * Created by System-2 on 12/15/2016.
 */
public class UpdateCustomerRequestBO {
    private int id;
    private String name;
    private String locality;
    private String phoneNo;
    private String emailId;
    private String dob;
    private String doa;

    public String getLocality() {return locality;}

    public void setLocality(String locality) {this.locality = locality;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getPhoneNo() {return phoneNo;}

    public void setPhoneNo(String phoneNo) {this.phoneNo = phoneNo;}

    public String getEmailId() {return emailId;}

    public void setEmailId(String emailId) {this.emailId = emailId;}

    public String getDob() {return dob;}

    public void setDob(String dob) {this.dob = dob;}

    public String getDoa() {return doa;}

    public void setDoa(String doa) {this.doa = doa;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateCustomerRequestBO that = (UpdateCustomerRequestBO) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (locality != null ? !locality.equals(that.locality) : that.locality != null) return false;
        if (phoneNo != null ? !phoneNo.equals(that.phoneNo) : that.phoneNo != null) return false;
        if (emailId != null ? !emailId.equals(that.emailId) : that.emailId != null) return false;
        if (dob != null ? !dob.equals(that.dob) : that.dob != null) return false;
        return doa != null ? doa.equals(that.doa) : that.doa == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (phoneNo != null ? phoneNo.hashCode() : 0);
        result = 31 * result + (emailId != null ? emailId.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (doa != null ? doa.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateCustomerRequestBO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", locality='" + locality + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", emailId='" + emailId + '\'' +
                ", dob='" + dob + '\'' +
                ", doa='" + doa + '\'' +
                '}';
    }
}
