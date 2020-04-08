package com.example.fpgins.DataModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PartnersData {

//    private String id;
//    private String company;
//    private String description;
//    private String companyPic;
//    private String link;
//    private String startDate;
//    private String expiryDate;
//    private String createdBy;
//    private String createdWhen;
//    private String updatedBy;
//    private String updatedWhen;

    private String id;
    private String title;
    private String content;
    private String link;
    private String postDate;
    private String expiryDate;
    private String partnerClientName;
    private String companyPic;

    public PartnersData(String id, String title, String content, String link, String postDate, String expiryDate, String partnerClientName, String companyPic) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.link = link;
        this.postDate = postDate;
        this.expiryDate = expiryDate;
        this.partnerClientName = partnerClientName;
        this.companyPic = companyPic;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getLink() {
        return link;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getPartnerClientName() {
        return partnerClientName;
    }

    public String getCompanyPic() {
        return companyPic;
    }
}
