package com.example.iotcloudplc.Entity;

public class IoTDevice {
    private int id;
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String description;
    private String high;
    private String low;
    private String credential_token;

    public IoTDevice(int id,String name, String description, String high, String low, String credential_token) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.high = high;
        this.low = low;
        this.credential_token = credential_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getCredential_token() {
        return credential_token;
    }

    public void setCredential_token(String credential_token) {
        this.credential_token = credential_token;
    }

    public IoTDevice(){

    }



    
    
}
