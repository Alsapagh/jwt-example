/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egabi.jwtexample.service;

import com.egabi.jwtexample.redis.RedisConnection;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MagedMamdouh
 */
@Service
public class RedisService {
    
    RedisCommands<String, String> syncCommands;
    
    public static final String USER_TOKENS = "user_tokens";

//    public RedisService() {
//        getSyncConn();
//    }
    
    public RedisService(){
        System.out.println("com.egabi.jwtexample.service.RedisService.created");
        RedisConnection redisConnection = new RedisConnection();
        StatefulRedisConnection<String, String> connection = redisConnection.getRedisConnection();
        this.syncCommands = connection.sync();
    }

    public void updateUserToken(String user, String token){
        syncCommands.hset(USER_TOKENS, user, token);
    }
    
    public boolean validateUserToken(String user, String token){
        String oldToken = syncCommands.hget(USER_TOKENS, user);
        return (oldToken.equals(token));
    }
    
    public void removeUserToken(String user){
        syncCommands.hdel(USER_TOKENS, user);
    }
}
