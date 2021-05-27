/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egabi.jwtexample.service;

import org.springframework.stereotype.Service;

/**
 *
 * @author MagedMamdouh
 */
@Service
public class RedisService {
    
    public static final String USER_TOKENS = "user_tokens";
    
    public void updateUserToken(String user, String token){
        
    }
    
    public boolean validateUserToken(String user, String token){
        return true;
    }
    
    public void removeUserToken(String user){
        
    }
}
