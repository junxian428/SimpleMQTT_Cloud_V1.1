package com.example.iotcloudplc.Controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

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
//@RequestHeader("Authorization") String authorizationHeader

    @GetMapping("/admin")
    public String adminMainPage(@RequestParam("id") String id){
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
                     if(countToken != 0){
                        return "mainadmin";
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
