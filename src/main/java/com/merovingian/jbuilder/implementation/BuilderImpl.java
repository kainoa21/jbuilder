/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.google.common.base.Preconditions;
import com.merovingian.jbuilder.util.ReflectionUtil;
import com.merovingian.jbuilder.util.ReflectionUtilImpl;
import com.merovingian.jbuilder.Builder;
import com.merovingian.jbuilder.BuilderSetup;
import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.propertynaming.PropertyNamer;

/**
 *
 * @author jasonr
 */
public class BuilderImpl<T> implements Builder<T> {
    
    private final Class<T> type;
    
    public BuilderImpl(Class<T> c) {
        type = c;
    }
    
    public ObjectBuilder<T> CreateNew() {
        ReflectionUtil reflectionUtil = new ReflectionUtilImpl();
        PropertyNamer propertyNamer = BuilderSetup.GetPropertyNamerFor(type);
        return new ObjectBuilderImpl<T>(type, reflectionUtil).WithPropertyNamer(propertyNamer);
    }

    public ListBuilder<T> CreateListOfSize(int size) {
        Preconditions.checkArgument(size > 0, "Size of list must be 1 or greater: %s", size);
        PropertyNamer propertyNamer = BuilderSetup.GetPropertyNamerFor(type);
        return CreateListOfSize(size, propertyNamer);
    }

    public ListBuilder<T> CreateListOfSize(int size, PropertyNamer propertyNamer) {
        return new ListBuilderImpl<T>(type, size, propertyNamer, new ReflectionUtilImpl());
    }
    
}
