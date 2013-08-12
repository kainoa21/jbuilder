package com.merovingian.jbuilder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author jasonr
 */
public class NoEmptyConstructorTestClass {
    
    // Public field
    public String myTestField;
    
    // Private member variable to set with a method
    private String myPrivateString;
    
    // Final private member set via constructor
    private final String myFinalString;
    
    
        
    public NoEmptyConstructorTestClass(String value) {
        myFinalString = value;
    }
    
    public String getMyPrivateString() {
        return this.myPrivateString;
    }
    
    public void setMyPrivateString(String value) {
        this.myPrivateString = value;
    }
    
    public String getMyFinalString() {
        return this.myFinalString;
    }
    
}
