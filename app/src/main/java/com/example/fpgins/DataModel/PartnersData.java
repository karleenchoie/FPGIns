package com.example.fpgins.DataModel;

public class PartnersData {

    private String id;
    private String company;
    private String description;
    private String companyPic;
    private String link;
    private String startDate;
    private String expiryDate;
    private String createdBy;
    private String createdWhen;
    private String updatedBy;
    private String updatedWhen;

    public PartnersData(String id, String company, String desc, String pic, String link, String startDate, String expDate,
                        String createdBy, String createdWhen, String updatedBy, String updatedWhen) {
        this.id = id;
        this.company = company;
        this.description = desc;
        this.companyPic = pic;
        this.link = link;
        this.startDate = startDate;
        this.expiryDate = expDate;
        this.createdBy = createdBy;
        this.createdWhen = createdWhen;
        this.updatedBy = updatedBy;
        this.updatedWhen = updatedWhen;
    }

    public String getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }

    public String getCompanyPic() {
        return companyPic;
    }

    public String getLink() {
        return link;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedWhen() {
        return createdWhen;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getUpdatedWhen() {
        return updatedWhen;
    }
}
