/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egabi.jwtexample.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author m.mamdouh
 */
@Component
public class RedisConnection {
    
    private static final Logger log = Logger.getLogger(RedisConnection.class.getPackage().getName());

    private static StatefulRedisConnection<String, String> connection;
    
    public StatefulRedisConnection getRedisConnection(){
        if (connection == null) {
            log.log(Level.INFO, "New Redis Connection Created = {0}", connection);
            getRedisConnectionInner();
        }
        return connection;
    }

    public void getRedisConnectionInner(){
        try{
            RedisClient redisClient = RedisClient.create("redis://" + Redis.REDIS_IP + ":" + Redis.REDIS_PORT + ""); // change to reflect your environment
            this.connection = redisClient.connect();
        }catch(Exception ex){
            Logger.getLogger(RedisConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
