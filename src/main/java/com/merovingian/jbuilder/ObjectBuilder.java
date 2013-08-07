/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder;

import com.merovingian.jbuilder.exceptions.BuilderException;
import com.merovingian.jbuilder.propertynaming.PropertyNamer;

/**
 *
 * @author jasonr
 */
public interface ObjectBuilder<T> extends Operable<T> {
    
    ObjectBuilder<T> WithConstructorArgs(Object[] args);
    
    ObjectBuilder<T> WithPropertyNamer(PropertyNamer propertyNamer);
    
    PropertyNamer getPropertyNamer();
    
    public T CallFunctions(T obj);
    
    public T Construct() throws BuilderException;
}
