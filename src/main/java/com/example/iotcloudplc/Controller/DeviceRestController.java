package com.example.iotcloudplc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.iotcloudplc.Entity.IoTDevice;
import com.example.iotcloudplc.Entity.IoTDeviceUser;
import com.example.iotcloudplc.Mapper.UserMapper;

@RestController
@RequestMapping("/api/devices")
public class DeviceRestController {



    
 @Autowired
    private UserMapper usermapper;


    public DeviceRestController(UserMapper usermapper){
        this.usermapper = usermapper;

    }



     @PostMapping("/create")
    public String createDevice(@RequestBody IoTDeviceUser deviceRequest) {
        // Assuming you have a service to handle device creation
        // You can use these values to create a new IoT device
        // For example:
        // Device newDevice = new Device(deviceName, description, high, low, credentialToken);
        // deviceService.createDevice(newDevice);
        System.out.println("Received Form Data:");
        System.out.println("Device Name: " + deviceRequest.getName());
        System.out.println("Description: " + deviceRequest.getDescription());
        System.out.println("High: " + deviceRequest.getHigh());
        System.out.println("Low: " + deviceRequest.getLow());
        System.out.println("User Token:" + deviceRequest.getUser());
        System.out.println("Credential Token: " + deviceRequest.getCredential_token());
        String username = usermapper.ReturnUsername(deviceRequest.getUser());
        int ID = usermapper.getUserID(username);
        System.out.println("User ID : " + Integer.valueOf(ID));
        try{
            usermapper.insertDevice(deviceRequest.getName(), deviceRequest.getDescription(), deviceRequest.getHigh(), deviceRequest.getLow(), deviceRequest.getCredential_token(), ID);
        }catch(Exception e){
            System.out.println("" + e);
        }
        //System.out.println(username);
        // Return a success message or appropriate response
        //System.out.println("Endpoint received");
        return "Device created successfully!";
    }
}
