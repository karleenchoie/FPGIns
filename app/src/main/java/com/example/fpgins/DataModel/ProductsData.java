package com.example.fpgins.DataModel;

public class ProductsData {

    private String id;
    private String title;
    private String content;
    private String link;
    private String office_country_name;
    private String companyPic;

    public ProductsData(String id, String title, String content, String link, String office_country_name, String companyPic) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.link = link;
        this.office_country_name = office_country_name;
        this.companyPic = companyPic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOffice_country_name() {
        return office_country_name;
    }

    public void setOffice_country_name(String office_country_name) {
        this.office_country_name = office_country_name;
    }

    public String getCompanyPic() {
        return companyPic;
    }

    public void setCompanyPic(String companyPic) {
        this.companyPic = companyPic;
    }
}
