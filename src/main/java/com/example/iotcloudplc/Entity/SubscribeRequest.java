package com.example.iotcloudplc.Entity;

public class SubscribeRequest {
    private String operation;
    private String content;
    private String user;
    private String topic;
    private String session;
    public SubscribeRequest(){

    }
    
    public SubscribeRequest(String operation, String content, String user, String topic, String session) {
        this.operation = operation;
        this.content = content;
        this.user = user;
        this.topic = topic;
        this.session = session;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
    public String getSession() {
        return session;
    }
    public void setSession(String session) {
        this.session = session;
    }
}
