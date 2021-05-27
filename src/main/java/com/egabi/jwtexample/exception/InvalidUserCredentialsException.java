/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egabi.jwtexample.exception;

/**
 *
 * @author MagedMamdouh
 */
public class InvalidUserCredentialsException extends RuntimeException{

    public InvalidUserCredentialsException(String message) {
        super(message);
    }
    
}

