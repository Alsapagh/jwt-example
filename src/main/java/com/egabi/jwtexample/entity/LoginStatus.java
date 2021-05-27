/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egabi.jwtexample.entity;

/**
 *
 * @author MagedMamdouh
 */
public class LoginStatus {
    
    public static final int Success = 200;
    public static final int FirstLogin = 202;
    public static final int UserDeactivated = 300;
    public static final int InvalidCredentials = 301;
    public static final int Exception = 400;
    
}
