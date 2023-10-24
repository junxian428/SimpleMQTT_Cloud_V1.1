package com.example.iotcloudplc.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.iotcloudplc.Entity.IoTDevice;

@Mapper
public interface UserMapper {
    
    //@Insert("INSERT INTO  mqtt_hello (message) VALUES (#{message})")
    //void insertDeviceData(@Param("message") String message);
    @Select("SELECT token from user_table  WHERE username = (#{username})")
    String getToken(@Param("username") String username);

    @Select("SELECT id from user_table  WHERE username = (#{username})")
    int getUserID(@Param("username") String username);



    @Select("SELECT token from user_table  WHERE username = (#{username}) AND  password = (#{password}) ")
    String validateLogin(@Param("username") String username,@Param("password") String password);

    @Select("SELECT COUNT(*) FROM user_table WHERE token = #{token}")
    int checkToken(@Param("token") String token);



    @Select("SELECT username FROM user_table WHERE token = #{token}")
    String ReturnUsername(@Param("token") String token);

    @Select("SELECT user_plan.plan FROM user_plan INNER JOIN user_table ON user_plan.user_id = user_table.id WHERE user_table.token = #{token}")
    String ReturnUserPlan(@Param("token") String token);
 
    @Select("SELECT iot_device.* FROM iot_device INNER JOIN user_table ON iot_device.userid = user_table.id WHERE user_table.token = #{token}")
    List<IoTDevice> ReturnUserIoT(@Param("token") String token);

    @Insert("INSERT INTO iot_device (name, description, high, low, credential_token, userid) VALUES (#{name}, #{description}, #{high}, #{low}, #{credentialToken}, #{userid})")
    void insertDevice(@Param("name") String name, @Param("description") String description, @Param("high") String high, @Param("low") String low, @Param("credentialToken")  String credentialToken, @Param("userid") int userid);

    // Pagination Frontend
    @Select("SELECT COUNT(*) FROM iot_device INNER JOIN user_table ON iot_device.userid = user_table.id WHERE user_table.token = #{token}")
    int countUserIoTDevices(@Param("token") String token);
    
        @Select("SELECT iot_device.* FROM iot_device INNER JOIN user_table ON iot_device.userid = user_table.id WHERE user_table.token = #{token} LIMIT 10")
    List<IoTDevice> ReturnUserIoTLimited10(@Param("token") String token);
    
    @Select("SELECT iot_device.* FROM iot_device INNER JOIN user_table ON iot_device.userid = user_table.id WHERE user_table.token = #{token} LIMIT #{limit} OFFSET #{offset}")
List<IoTDevice> getDevicesWithLimitAndOffset(@Param("token") String token, @Param("limit") int limit, @Param("offset") int offset);


        @Select("SELECT user_topics.topic_name " +
                "FROM user_table " +
                "INNER JOIN user_topics ON user_table.id = user_topics.user_id " +
                "WHERE user_table.token = #{token}")
        List<String> returnAllTopics(@Param("token") String token);
        
        
        @Select("SELECT user_table.*, user_topics.id AS user_topic_id, user_topics.topic_name " +
        "FROM user_table " +
        "INNER JOIN user_topics ON user_table.id = user_topics.user_id " +
        "WHERE user_table.token = #{token}")
List<Map<String, Object>> returnAllDeviceBasedOnTopics(@Param("token") String token);




        //List<String> getTopicsByToken(String token);



}
 