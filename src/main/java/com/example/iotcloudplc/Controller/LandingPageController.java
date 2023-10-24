package com.example.iotcloudplc.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.iotcloudplc.Entity.Device;
import com.example.iotcloudplc.Entity.IoTDevice;
import com.example.iotcloudplc.Mapper.UserMapper;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@SuppressWarnings("deprecation")
public class LandingPageController  implements ErrorController {
    private static final String[] imageNames = {"403.png", "404.png","405.png","406.png"}; 

 @Autowired
    private UserMapper usermapper;

    public LandingPageController (UserMapper usermapper){
        this.usermapper = usermapper;

    }

    @GetMapping("/")
    public String homePage(){
        return "homepage";
    }


    @PostMapping("/login")
    public ResponseEntity<String> processLogin(String username, String password) {
        // Validate the username and password
        // If valid, perform authentication logic

        // Return a view based on authentication success or failure
  // If everything is successful, return HTTP 200 (OK) along with a success message
        System.out.println(username);
            System.out.println(password);
            try{
                //System.out.println(usermapper.validateLogin(username, password));
                String token = usermapper.validateLogin(username, password);
                System.out.println(token);
                if(token != null){
                    return ResponseEntity.ok(token);

                }else{
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");

                }

            }catch(Exception e){
                System.out.println("Error " + e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
            }

     
    

        }

        @GetMapping("/dashboard")
        public String adminPage(@RequestParam("id") String id, @RequestParam("name") String name) {
            // Your code here
            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
    
            return "mainadmin";
        }



    @GetMapping("/admin")
    public String adminMainPage(@RequestParam("id") String id, Model model){
        // Use the 'id' parameter in your logic
        // For example, you can pass it to a service method to fetch data associated with that id.
        // Example: someService.getDataById(id);
            // Check if 'id' is not null and has a valid format
            if (!id.equals("")) {
                // Use the 'id' parameter in your logic
                // Example: someService.getDataById(id);
                //String token = usermapper.getToken(id);
                //int countToken = usermapper.checkToken(id);
                //System.out.println(countToken);
                     System.out.println(id);
                     int countToken = usermapper.checkToken(id);
                     System.out.println(countToken);
                     int PageInDevicesTotal = usermapper.countUserIoTDevices(id);
                     int TotalPage = PageInDevicesTotal / 10 ;
                     int LastPageTotal = PageInDevicesTotal % 10 ;


                     System.out.println("Total Devices" + Integer.toString(PageInDevicesTotal));
                     System.out.println("Total Page " + Integer.toString(TotalPage));
                     System.out.println("Last Page " + Integer.toString(LastPageTotal));

                   
               
                     if(countToken != 0){
                        if(TotalPage >= 1){

                                 /*
                                 * 
                                 * 
                                 * 
                                 * 
                                 * More Than 10 devices
                                 */




                            System.out.println("Display Pagination");
                            String username = usermapper.ReturnUsername(id);
                    // System.out.println(username);
                        model.addAttribute("HelloWorld", username); // Add a flag to indicate admin status
                        String userPlan = usermapper.ReturnUserPlan(id);
                        model.addAttribute("FreePlan", userPlan); // Add a flag to indicate admin status
                        model.addAttribute("MaximumConnection", "100"); // Add a flag to indicate admin status
                        model.addAttribute("CredentialToken", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"); // Add a flag to indicate admin status
                        model.addAttribute("CurrentConnection", "0"); // Add a flag to indicate admin status
                        // Create a list of devices
                        List<Device> Thymeleafdevices = new ArrayList<>();
                             /*
                                 * 
                                 * 
                                 * 
                                 * 
                                 * More Than 10 devices
                                 */



                        List<IoTDevice> deviceList = usermapper.ReturnUserIoTLimited10(id);
                        //System.out.println("Total Page " + Integer.toString(TotalPage));

                             /*
                                 * 
                                 * 
                                 * 
                                 * 
                                 * More Than 10 devices
                                 */

                        for (IoTDevice device : deviceList) {
                            //System.out.println("Device ID: " + device.getId());
                            //System.out.println("Description: " + device.getDescription());
                            //System.out.println("High: " + device.getHigh());
                            //System.out.println("Low: " + device.getLow());
                            //System.out.println("Credential Token: " + device.getCredential_token());
                            //System.out.println("User ID: " + device.getId());
                            Thymeleafdevices.add(new Device(device.getName(),device.getDescription(), "", device.getHigh(),device.getLow(),device.getCredential_token(),"Ok"));
                            System.out.println("---------------------");
                        }

                        //devices.add(new Device("Device2", "Description2", "Selection2", "High2", "Low2", "Token2","Bad"));
                        //devices.add(new Device("Device3", "Description3", "Selection3", "High3", "Low3", "Token3","Bad"));

                        // Add the list to the model
                        model.addAttribute("devices", Thymeleafdevices);
                        model.addAttribute("PageTotal", TotalPage);

                        return "mainadmin";


                        } else{
                                /*
                                 * 
                                 * 
                                 * 
                                 * 
                                 * Less Than 10 devices
                                 */

                        String username = usermapper.ReturnUsername(id);
                    // System.out.println(username);
                        model.addAttribute("HelloWorld", username); // Add a flag to indicate admin status
                        String userPlan = usermapper.ReturnUserPlan(id);
                        model.addAttribute("FreePlan", userPlan); // Add a flag to indicate admin status
                        model.addAttribute("MaximumConnection", "100"); // Add a flag to indicate admin status
                        model.addAttribute("CredentialToken", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"); // Add a flag to indicate admin status
                        model.addAttribute("CurrentConnection", "0"); // Add a flag to indicate admin status
                        // Create a list of devices
                        List<Device> Thymeleafdevices = new ArrayList<>();
                        List<IoTDevice> deviceList = usermapper.ReturnUserIoT(id);
                        for (IoTDevice device : deviceList) {
                            //System.out.println("Device ID: " + device.getId());
                            //System.out.println("Description: " + device.getDescription());
                            //System.out.println("High: " + device.getHigh());
                            //System.out.println("Low: " + device.getLow());
                            //System.out.println("Credential Token: " + device.getCredential_token());
                            //System.out.println("User ID: " + device.getId());
                            Thymeleafdevices.add(new Device(device.getName(),device.getDescription(), "", device.getHigh(),device.getLow(),device.getCredential_token(),"Ok"));
                            System.out.println("---------------------");
                        }

                        //devices.add(new Device("Device2", "Description2", "Selection2", "High2", "Low2", "Token2","Bad"));
                        //devices.add(new Device("Device3", "Description3", "Selection3", "High3", "Low3", "Token3","Bad"));

                        // Add the list to the model
                        List<String> Manydevices = usermapper.returnAllTopics(id);
                        for (String device : Manydevices) {
                            System.out.println(device);
                        }

                    List<Map<String, Object>> results = usermapper.returnAllDeviceBasedOnTopics(id);

                        for (Map<String, Object> row : results) {
                            System.out.println("______________________");
                            String userTopicId = row.get("user_topic_id").toString();
                            String topicName = row.get("topic_name").toString();

                            System.out.println(userTopicId + " : "  + topicName);
                            // Use userTopicId as needed
                        }

                       // model.addAttribute("manyDevices", Manydevices);
                       //model.addAttribute("manyDevices",Manydevices );
                       model.addAttribute("results",results );

                        model.addAttribute("devices", Thymeleafdevices);
                        model.addAttribute("PageTotal", TotalPage);

                        return "mainadmin";
                        }
                              
                     }else{
                        return "redirect:/error";
                     }
               
               
            } else {
                // Handle invalid 'id'
                return "redirect:/error";
            }
    }
    
    
    @RequestMapping("/error")
    public String handleError(Model model) {
        //do something like logging

                Random random = new Random();
        int randomIndex = random.nextInt(imageNames.length);
        String randomImageName = imageNames[randomIndex];

        model.addAttribute("imageName", randomImageName);

        return "error";
    }
 

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingServletRequestParameterException(Model model) {
         Random random = new Random();
        int randomIndex = random.nextInt(imageNames.length);
        String randomImageName = imageNames[randomIndex];

        model.addAttribute("imageName", randomImageName);
        return new ModelAndView("errorPage"); // Set the name of your error page
    }







    
}
