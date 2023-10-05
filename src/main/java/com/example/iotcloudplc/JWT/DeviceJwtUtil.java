package com.example.iotcloudplc.JWT;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class DeviceJwtUtil {

    @Value("${jwt.anotherSecret}")
    private String anotherSecret; // Another secret key used for signing the token

    public String generateAnotherToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Token valid for 24 hours
                .signWith(SignatureAlgorithm.HS512, anotherSecret)
                .compact();
    }

    // Add other methods as needed...
}
