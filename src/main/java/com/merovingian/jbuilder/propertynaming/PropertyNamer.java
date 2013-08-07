/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.propertynaming;

import com.merovingian.jbuilder.exceptions.TypeCreationException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author jasonr
 */
public interface PropertyNamer {
    
    <T> void setValuesOfAllIn(List<T> obj) throws InvocationTargetException, IllegalAccessException, TypeCreationException;
    <T> void setValuesOf(T obj) throws InvocationTargetException, IllegalAccessException, TypeCreationException;
    
}
