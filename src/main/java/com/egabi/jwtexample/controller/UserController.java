/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egabi.jwtexample.controller;

import com.egabi.jwtexample.entity.LoginResponse;
import com.egabi.jwtexample.entity.User;
import com.egabi.jwtexample.service.UserService;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MagedMamdouh
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, user.toString());
        LoginResponse loginMessage = userService.login(user);
        loginMessage.setTime(new Date());
        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, loginMessage.toString());
        return new ResponseEntity(loginMessage, HttpStatus.valueOf(loginMessage.getCode()));
    }
    
    @GetMapping("/getTestMessage")
    public ResponseEntity getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return new ResponseEntity("Test Message from " + username, HttpStatus.OK);
    }
    
}
