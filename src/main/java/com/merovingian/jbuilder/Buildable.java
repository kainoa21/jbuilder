/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder;

import com.merovingian.jbuilder.exceptions.BuilderException;

/**
 *
 * @author jasonr
 */
public interface Buildable<T> {
    
    T Build() throws BuilderException;
    
    public Buildable<T> WithConstructorArgs(Object[] args);
    
    public Buildable<T> WithAutoNamer(AutoNamer autoNamer);
    
}
