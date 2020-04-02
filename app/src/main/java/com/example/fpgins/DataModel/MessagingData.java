package com.example.fpgins.DataModel;

public class MessagingData {

    private String createdBy;
    private String id;
    private String message;
    private String time;

    public MessagingData(String id, String createdBy, String message, String timeReceived) {
        this.id = id;
        this.createdBy = createdBy;
        this.message = message;
        this.time = timeReceived;
    }

    public String getId() {
        return id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

}
