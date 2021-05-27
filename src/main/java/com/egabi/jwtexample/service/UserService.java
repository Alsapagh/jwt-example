/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egabi.jwtexample.service;

import com.egabi.jwtexample.entity.LoginResponse;
import com.egabi.jwtexample.entity.LoginStatus;
import com.egabi.jwtexample.entity.User;
import com.egabi.jwtexample.exception.InvalidUserCredentialsException;
import com.egabi.jwtexample.util.JwtUtil;
import java.sql.ResultSet;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author MagedMamdouh
 */
@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private RedisService redisService;
    
    public User getUserByUsername(String username) {
        User user = new User(1, "Magued Mamdouh", "magued@gmail.com", 
                "$2a$10$Lq8EOVEWmapxMX2zCgYj3O/8X68gvzTZZDdA4Jyg4bSxM1H0SKLIi", 1, username);
        return user;
    }

    public LoginResponse login(User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setCode(LoginStatus.Exception);
        
        User userDB = getUserByUsername("magued.mamdouh");
        
        if(!userDB.getUsername().equals(user.getUsername())) {
            loginResponse.setMessage("Invalid Username/Password");
            loginResponse.setCode(LoginStatus.InvalidCredentials);
            throw new InvalidUserCredentialsException("Invalid Username/Password");
        } else{
            //Correct Credentials
            if(passwordEncoder.matches(user.getPassword(), userDB.getPassword())){
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
                );

                loginResponse.setToken(jwtUtil.generateToken(user.getUsername()));
                loginResponse.setCode(LoginStatus.Success);
                loginResponse.setMessage("Login Successful");
                loginResponse.setName(userDB.getName());
                loginResponse.setUsername(userDB.getUsername());
                loginResponse.setRoleId(userDB.getRoleId());
                redisService.updateUserToken(loginResponse.getUsername(), loginResponse.getToken());
            }
        }
        return loginResponse;
    }
    
}
