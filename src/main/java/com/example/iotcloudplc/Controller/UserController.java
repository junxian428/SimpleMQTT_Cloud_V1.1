package com.example.iotcloudplc.Controller;

import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.iotcloudplc.Entity.IoTDevice;
import com.example.iotcloudplc.Entity.SubscribeRequest;
import com.example.iotcloudplc.Mapper.UserMapper;

@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserMapper usermapper;

    @Autowired
    private MqttClient mqttClient; // Add this line


    public UserController(UserMapper usermapper){
        this.usermapper = usermapper;

    }

   @GetMapping("/welcome")
    public String helloWorld(@RequestParam("name") String name) {
        //System.out.println(usermapper.getToken(name));
        return usermapper.getToken(name);
    }
       
    @PostMapping("/subscribe")
    public String subscribe(@RequestParam String topic) throws MqttException {
        try{
            mqttClient.subscribe(topic);
            return "Subscribed to " + topic;
        }catch(Exception e){
            return e + "";
        }
   
    } 



    @GetMapping("/topics")
    public List<String> getTopicsByToken(@RequestParam String token) {
        return usermapper.returnAllTopics(token);
    }


    @DeleteMapping("/deleteByCredentialToken/{credentialToken}")
    public ResponseEntity<String> deleteIoTDeviceByCredentialToken(@PathVariable String credentialToken) {
        try {
            usermapper.deleteIoTDeviceByCredentialToken(credentialToken);
            return new ResponseEntity<>("Device deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting device: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


       @PostMapping("/unsubscribe")
    public String unsubscribe(@RequestParam String topic) throws MqttException {
        try{
            mqttClient.unsubscribe(topic);
            return "Unscribed " + topic;
        }catch(Exception e){
            return e + "";
        }
   
    } 


@PostMapping("/subscribeMQTT")
public String subscribeMQTT(@RequestBody SubscribeRequest request) {
    System.out.println("Operation: " + request.getOperation());
    System.out.println("Content: " + request.getContent());
    System.out.println("User: " + request.getUser());
    System.out.println("Topic: " + request.getTopic());
    System.out.println("Session: " + request.getSession());
    try{
            mqttClient.subscribe(request.getTopic());
                return "Request processed successfully";
    }catch(Exception e){
        return e  + "";

    }
}


 @DeleteMapping("/delete/device/{id}")
    public ResponseEntity<String> deleteIoTDevice(@PathVariable int id) {
        try{
            
            usermapper.deleteIoTDeviceById(id);
                    return ResponseEntity.ok("IoT device deleted successfully");

        }catch(Exception ex){
            return ResponseEntity.ok("Error message: " + ex);

        }
    }



    @GetMapping("/dataTable/{variable}")
    public List<IoTDevice> data(@PathVariable String variable , @RequestParam String token){
        List<IoTDevice> deviceList;


        if(Integer.parseInt(variable) >=2){
            int VARIABLE_MINUS = Integer.parseInt(variable) -1 ;
            List<IoTDevice> deviceList2 = usermapper.getDevicesWithLimitAndOffset(token, 10, Integer.parseInt(Integer.toString(VARIABLE_MINUS)+ "0"));
                    System.out.println("Pagination Display________________");
                    for (IoTDevice device : deviceList2) {
                        System.out.println("Device Name: " + device.getName());
                        // Add more properties as needed
                    }
                                        deviceList = deviceList2;

        }else{


            List<IoTDevice> deviceList2 = usermapper.getDevicesWithLimitAndOffset(token, 10, Integer.parseInt(variable)-1);
                    System.out.println("Pagination Display________________");
                    int Index= 0;
                    for (IoTDevice device : deviceList2) {
                        System.out.println(Index + "Device Name: " + device.getName());
                        // Add more properties as needed
                        Index +=1;
                    }
                    deviceList = deviceList2;
        }
     

        return deviceList;
    }
    


}
