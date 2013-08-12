/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder;

import com.merovingian.jbuilder.exceptions.TypeCreationException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author jasonr
 */
public interface AutoNamer {
    
    <T> void setValuesOf(T obj) throws InvocationTargetException, IllegalAccessException, TypeCreationException;
    
    <T> void setValuesOfAllIn(List<T> obj) throws InvocationTargetException, IllegalAccessException, TypeCreationException;
    
}
