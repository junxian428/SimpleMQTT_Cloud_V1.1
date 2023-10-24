package com.example.iotcloudplc.Entity;

public class Device {
    private String deviceName;
    private String description;
    private String triggerSelection;
    private String high;
    private String low;
    private String credentialToken;
    private String status;

    // Constructors
    public Device() {
        // Default constructor
    }

    public Device(String deviceName, String description, String triggerSelection, String high, String low, String credentialToken, String status) {
        this.deviceName = deviceName;
        this.description = description;
        this.triggerSelection = triggerSelection;
        this.high = high;
        this.low = low;
        this.credentialToken = credentialToken;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getters and Setters
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTriggerSelection() {
        return triggerSelection;
    }

    public void setTriggerSelection(String triggerSelection) {
        this.triggerSelection = triggerSelection;
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

    public String getCredentialToken() {
        return credentialToken;
    }

    public void setCredentialToken(String credentialToken) {
        this.credentialToken = credentialToken;
    }
}
