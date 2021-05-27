/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egabi.jwtexample.util;

import com.egabi.jwtexample.entity.User;
import com.egabi.jwtexample.exception.UserNotFoundException;
import com.egabi.jwtexample.service.UserService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 *
 * @author MagedMamdouh
 */
@Service
public class JwtUserDetailsService implements UserDetailsService{
    
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException{
        User user = userService.getUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), 
                user.getPassword(), new ArrayList<>());
    }
    
}
