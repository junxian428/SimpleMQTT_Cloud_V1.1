package com.example.iotcloudplc.Entity;

public class IoTDevicesWithIDAndTopic {
    private int id;
    private int user_id;
    private String topic_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public IoTDevicesWithIDAndTopic(){

    }

    
}
