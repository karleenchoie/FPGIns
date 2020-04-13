package com.example.fpgins.DataModel;

public class FAQData {

    private String id;
    private String title;
    private String content;
    private String link;

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

    public FAQData(String id, String title, String content, String link) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.link = link;
    }


}
