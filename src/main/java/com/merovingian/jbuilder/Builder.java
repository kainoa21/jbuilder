/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder;


/**
 *
 * @author jasonr
 */
public interface Builder<T> {
    
    public ObjectBuilder<T> CreateNew();
    
    public ListBuilder<T> CreateListOfSize(int size);
    
    public ListBuilder<T> CreateListOfSize(int size, AutoNamer propertyNamer);
 
}
