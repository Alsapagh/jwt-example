/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egabi.jwtexample.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
/**
 *
 * @author MagedMamdouh
 */
public class RedisConnection {
    
    private StatefulRedisConnection connection = null;
    
    public StatefulRedisConnection getRedisConnectionInner(){
        RedisClient redisClient = RedisClient.create("redis://" + Redis.REDIS_IP + ":" + Redis.REDIS_PORT);
        StatefulRedisConnection<String, String> redisConnection = redisClient.connect();
        return redisConnection;
    }
    
    public StatefulRedisConnection getRedisConnection(){
        if(connection == null){
            return getRedisConnectionInner();
        }
        else return connection;
    }
    
}
