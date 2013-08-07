/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.util;

import com.merovingian.jbuilder.exceptions.TypeCreationException;
import java.lang.reflect.Type;

/**
 *
 * @author jasonr
 */
public interface ReflectionUtil {
    
    // TODO: Should this class and methods all be static?

    <T> T CreateInstanceOf(Class<T> c) throws TypeCreationException;

    <T> T CreateInstanceOf(Class<T> c, Object[] args) throws TypeCreationException;

    boolean RequiresConstructorArgs(Class<?> c);

    boolean IsDefaultValue(Object value) throws TypeCreationException;
}
