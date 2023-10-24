package com.example.iotcloudplc.Config;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class CustomMqttCallback implements MqttCallback {




    @Override
    public void connectionLost(Throwable cause) {
        // This method is called when the connection to the MQTT broker is lost.
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // This method is called when a message is received on the subscribed topic.
        System.out.println("Received message on topic: " + topic);
        System.out.println("Message: " + new String(message.getPayload()));
        SocketTextHandler webSocketHandler = new SocketTextHandler();

         // Publish the message to the WebSocket topic
          for (WebSocketSession session : webSocketHandler.getSessionsFromIds(webSocketHandler.getSessionIds())) {
            session.sendMessage(new TextMessage(message.getPayload()));
        }

    }



    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deliveryComplete'");
    }
}
