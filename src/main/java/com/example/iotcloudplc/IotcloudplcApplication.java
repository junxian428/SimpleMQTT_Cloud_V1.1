package com.example.iotcloudplc;



import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.iotcloudplc.Config.SocketTextHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class IotcloudplcApplication {



    public static void main(String[] args) {
        Map<String, String> idTopicsMap = new HashMap<>();
        idTopicsMap.put("User1", "Topic1");
        idTopicsMap.put("User2", "Topic2");
        
        // Add usernames and passwords for each user
        Map<String, String> userCredentialsMap = new HashMap<>();
        userCredentialsMap.put("User1", "password1");
        userCredentialsMap.put("User2", "password2");
    
        for (Map.Entry<String, String> entry : idTopicsMap.entrySet()) {
            String id = entry.getKey();
            String topic = entry.getValue();
            String password = userCredentialsMap.get(id);
    
            try {
                MQTTSubscriber subscriber = new MQTTSubscriber(id, topic, "User1", "password1");
                subscriber.start();
    
                // Create a publisher instance with username and password
                MqttPublisher publisher = new MqttPublisher("tcp://0.0.0.0:1883", "PublisherClientID", id, password);
                publisher.connect();
    
                // Publish using the publisher instance
                publisher.publish(topic, "Hello from " + id);
    
                // Disconnect when done
                publisher.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        SpringApplication.run(IotcloudplcApplication.class, args);
    }
}

class MqttPublisher {

      private String brokerUrl = "tcp://0.0.0.0:1883";
    private String clientId = "1";
    private String username; // Add username
    private String password; // Add password 
    private IMqttClient mqttClient;
    public MqttPublisher() {

    }

     public MqttPublisher(String brokerUrl, String clientId, String username, String password) {
        this.brokerUrl = brokerUrl;
        this.clientId = clientId;
        this.username = username;
        this.password = password;
    }

    public void connect() throws MqttException {
        mqttClient = new MqttClient(brokerUrl, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);

        // Add username and password
        if (username != null && password != null) {
            options.setUserName(username);
            options.setPassword(password.toCharArray());
        }

        mqttClient.connect(options);
    }

    public void publish(String topic, String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        mqttClient.publish(topic, message);
    }

    public void disconnect() throws MqttException {
        mqttClient.disconnect();
    }
}

class MQTTSubscriber {
    private static final String BROKER_URL = "tcp://0.0.0.0:1883";
 private String id;
    private String topic;
    private String username; // Add username
    private String password; // Add password


    private WebSocketSession webSocketSession;
    
    public void setWebSocketSession(WebSocketSession session) {
        this.webSocketSession = session;
    }


   public MQTTSubscriber(String id, String topic, String username, String password) {
        this.id = id;
        this.topic = topic;
        this.username = username;
        this.password = password;
    }

     public void start() throws MqttException {
        MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        System.out.println("MQTT Subscriber started for ID " + id + " on topic " + topic);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);

        // Add username and password if available
        if (username != null && password != null) {
            options.setUserName(username);
            options.setPassword(password.toCharArray());
        }

        client.connect(options);

        client.subscribeWithResponse(topic, (IMqttMessageListener) (topic, message) -> {
            System.out.println(message.toString()); 
            SocketTextHandler socketTextHandler = new SocketTextHandler();
            List<WebSocketSession> sessions = socketTextHandler.getSessions();

            for (WebSocketSession eachsession : sessions ) {
                if (eachsession.isOpen()) {
                    eachsession.sendMessage(new TextMessage(message.toString()));
                } else{
                    System.out.println("not sending to all sessions");
                }
            }
        });
    }
}
