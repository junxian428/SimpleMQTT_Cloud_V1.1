package com.example.iotcloudplc.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    
    //@Insert("INSERT INTO  mqtt_hello (message) VALUES (#{message})")
    //void insertDeviceData(@Param("message") String message);
    @Select("SELECT token from user_table  WHERE username = (#{username})")
    String getToken(@Param("username") String username);

    @Select("SELECT token from user_table  WHERE username = (#{username}) AND  password = (#{password}) ")
    String validateLogin(@Param("username") String username,@Param("password") String password);

    @Select("SELECT COUNT(*) FROM user_table WHERE token = #{token}")
    int checkToken(@Param("token") String token);
}
 