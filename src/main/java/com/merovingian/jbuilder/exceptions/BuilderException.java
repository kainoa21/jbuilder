/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.exceptions;

/**
 *
 * @author jasonr
 */
public class BuilderException extends Throwable {
    
    public BuilderException(String message) {
        super(message);
    }
    
    public BuilderException(String message, Throwable innerException) {
        super(message, innerException);
    }
    
}
