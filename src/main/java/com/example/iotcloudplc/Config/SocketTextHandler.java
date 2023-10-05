package com.example.iotcloudplc.Config;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public List<WebSocketSession> getSessions() {
        return sessions;
    }

    
    public List<String> getSessionIds() {
        List<String> sessionIds = new ArrayList<>();
        for (WebSocketSession session : sessions) {
            sessionIds.add(session.getId());
        }
        return sessionIds;
    }
    ///
        public List<WebSocketSession> getSessionsFromIds(List<String> sessionIds) {
        List<WebSocketSession> sessionList = new ArrayList<>();
        for (String sessionId : sessionIds) {
            for (WebSocketSession session : sessions) {
                if (session.getId().equals(sessionId)) {
                    sessionList.add(session);
                    break;
                }
            }
        }
        return sessionList;
    }



    ///

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {

		String payload = message.getPayload();
		JSONObject jsonObject = new JSONObject(payload);
        System.out.println(payload);
		//session.sendMessage(new TextMessage("Hi " + jsonObject.get("user") + " how may we help you?"));


        //
        // Print the session ID that sent the message
        System.out.println("Session ID: " + session.getId() + " sends the message");
        System.out.println(jsonObject.get("user"));
        System.out.println(jsonObject.get("operation"));
        System.out.println(jsonObject.get("session"));
        System.out.println(jsonObject.get("content"));
        if(jsonObject.get("operation").equals("Search")){
            System.out.println("Search Function is called");
        }else if(jsonObject.get("operation").equals("Update")){
            System.out.println("Update function is called");
        }else if(jsonObject.get("operation").equals("Register")){
            // Register session
            System.out.println("Register session function is called");
        }else{
            System.out.println("Invalid");
        }
        //System.out.println(message);
            /* 
        System.out.println("Send Messages to all sessions....");
        for (WebSocketSession eachsession : sessions) {
            if (eachsession.isOpen()) {
		        eachsession.sendMessage(new TextMessage(jsonObject.get("user") + "\n"));
            } else{
                System.out.println("not sending to all sessions");
            }
        }

        */
	}

    


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
         // Print information about the session
            System.out.println("WebSocket Session ID: " + session.getId());
            // Send a message to the client
            TextMessage message = new TextMessage(session.getId());
            session.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }


    
}