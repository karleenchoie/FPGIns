package com.example.fpgins.DataModel;

public class SOSData {
    private String file_url_local;
    private String file_url_server;
    private String api_url_care;
    private String facebook;
    private String twitter;
    private String youtube;
    private String linkedin;

    public SOSData(String file_url_local, String file_url_server, String api_url_care, String facebook,
                   String twitter, String youtube, String linkedin) {
        this.file_url_local = file_url_local;
        this.file_url_server = file_url_server;
        this.api_url_care = api_url_care;
        this.facebook = facebook;
        this.twitter = twitter;
        this.youtube = youtube;
        this.linkedin = linkedin;
    }

    public String getFile_url_local() {
        return file_url_local;
    }

    public void setFile_url_local(String file_url_local) {
        this.file_url_local = file_url_local;
    }

    public String getFile_url_server() {
        return file_url_server;
    }

    public void setFile_url_server(String file_url_server) {
        this.file_url_server = file_url_server;
    }

    public String getApi_url_care() {
        return api_url_care;
    }

    public void setApi_url_care(String api_url_care) {
        this.api_url_care = api_url_care;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
}
