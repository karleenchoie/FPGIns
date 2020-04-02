package com.example.fpgins.DataModel;

public class ClientNameData {

    private String clientName, clientAddress, clientPolicy;

    public ClientNameData(String clientName, String clientAddress, String clientPolicy) {
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.clientPolicy = clientPolicy;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientPolicy() {
        return clientPolicy;
    }

    public void setClientPolicy(String clientPolicy) {
        this.clientPolicy = clientPolicy;
    }
}
