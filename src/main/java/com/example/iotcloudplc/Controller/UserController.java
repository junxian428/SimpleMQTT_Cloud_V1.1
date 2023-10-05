package com.example.iotcloudplc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.iotcloudplc.Mapper.UserMapper;

@RestController
public class UserController {
    @Autowired
    private UserMapper usermapper;

    public UserController(UserMapper usermapper){
        this.usermapper = usermapper;

    }

   @GetMapping("/welcome")
    public String helloWorld(@RequestParam("name") String name) {
        //System.out.println(usermapper.getToken(name));
        return usermapper.getToken(name);
    }
        
}
