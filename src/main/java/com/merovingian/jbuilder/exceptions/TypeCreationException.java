/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.exceptions;

/**
 *
 * @author jasonr
 */
public class TypeCreationException extends BuilderException {
        
    public TypeCreationException(String message) {
        super(message);
    }
    
    public TypeCreationException(String message, Throwable innerException) {
        super(message, innerException);
    }
    
}
