/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.functions;

/**
 *
 * @author jasonr
 */
public interface Function2<F,T> {
    
    T apply(F arg1,T arg2);
}
