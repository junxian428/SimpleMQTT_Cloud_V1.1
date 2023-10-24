package com.example.iotcloudplc.Config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Configuration
public class MqttConfig {

       @Autowired
    private CustomMqttCallback ustomMqttCallback; // Inject the custom callback



 



    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.client.id}")
    private String clientId;

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient mqttClient = new MqttClient(brokerUrl, clientId);
        mqttClient.setCallback(new CustomMqttCallback()); // Set the callback
        mqttClient.connect();
       
        return mqttClient;
    }
}
