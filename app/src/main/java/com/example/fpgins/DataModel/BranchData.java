package com.example.fpgins.DataModel;

public class BranchData {
    private String id;
    private String name;
    private String address;
    private String contact_no;
    private String email;
    private String fax_no;
    private String office_country_name;
    private String office_type_name;

    public BranchData(String id,String name, String address, String contact_no, String email, String fax_no, String office_country_name, String office_type_name) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact_no = contact_no;
        this.email = email;
        this.fax_no = fax_no;
        this.office_country_name = office_country_name;
        this.office_type_name = office_type_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax_no() {
        return fax_no;
    }

    public void setFax_no(String fax_no) {
        this.fax_no = fax_no;
    }

    public String getOffice_country_name() {
        return office_country_name;
    }

    public void setOffice_country_name(String office_country_name) {
        this.office_country_name = office_country_name;
    }

    public String getOffice_type_name() {
        return office_type_name;
    }

    public void setOffice_type_name(String office_type_name) {
        this.office_type_name = office_type_name;
    }
}
