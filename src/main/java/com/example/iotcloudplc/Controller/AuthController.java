package com.example.iotcloudplc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.iotcloudplc.JWT.DeviceJwtUtil;
import com.example.iotcloudplc.JWT.JwtUtil;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private DeviceJwtUtil anotherJwtUtil;

    @GetMapping("/plc")
    public String generateAnotherToken(@RequestParam String username) {
        return anotherJwtUtil.generateAnotherToken(username);
    }


    @PostMapping("/login4")
    public String generateToken(@RequestParam String username) {
        System.out.println(jwtUtil.generateToken(username));
        return jwtUtil.generateToken(username);
    }
}
