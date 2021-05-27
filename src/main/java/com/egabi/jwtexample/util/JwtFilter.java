/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egabi.jwtexample.util;

import com.egabi.jwtexample.exception.InvalidUserCredentialsException;
import com.egabi.jwtexample.service.RedisService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author MagedMamdouh
 */
@Component
public class JwtFilter extends OncePerRequestFilter{
    
    @Autowired
    private JwtUtil jwt;
    
    @Autowired
    private RedisService redisService;
    
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest hsr, HttpServletResponse hsr1, FilterChain fc) throws ServletException, IOException {
        String authorizedHeader = hsr.getHeader("Authorization");
        Logger.getLogger(JwtFilter.class.getName()).log(Level.SEVERE, authorizedHeader);
        String token = null;
        String username = null;
        try{
            if(authorizedHeader != null && authorizedHeader.startsWith("Bearer ")){
                token = authorizedHeader.substring(7);
                username = jwt.extractUsername(token);
                Logger.getLogger(JwtFilter.class.getName()).log(Level.SEVERE, username);
            }
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                Logger.getLogger(JwtFilter.class.getName()).log(Level.SEVERE, userDetails.toString());
                if(jwt.validateToken(token, userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(hsr));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
                if(!redisService.validateUserToken(username, token)) 
                    throw new InvalidUserCredentialsException("Login Session Expired");
            }
            fc.doFilter(hsr, hsr1);
        }
        catch(Exception ex){
             StringBuilder sb = new StringBuilder();
                sb.append("{ ");
                sb.append("\"error\": \"Unauthorized\",");
                sb.append("\"message\": \"Unauthorized\",");
                sb.append("\"path\": \"")
                  .append(hsr.getRequestURL())
                  .append("\"");
                sb.append("} ");

                hsr1.setContentType("application/json");
                hsr1.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  
                hsr1.getWriter().write(sb.toString());
                return;
        }
        
            
    }
}
