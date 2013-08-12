/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.merovingian.jbuilder.ObjectBuilder;
import com.google.common.base.Function;
import com.merovingian.jbuilder.util.ReflectionUtil;
import com.merovingian.jbuilder.exceptions.BuilderException;
import com.merovingian.jbuilder.BuilderSetup;
import com.merovingian.jbuilder.exceptions.TypeCreationException;
import com.merovingian.jbuilder.functions.Function2;
import com.merovingian.jbuilder.AutoNamer;
import java.util.List;

/**
 *
 * @author jasonr
 */
public class ObjectBuilderImpl<T> extends BaseOperable<T> implements ObjectBuilder<T> {

    private final Class<T> type;
    private final ReflectionUtil reflectionUtil;
    private AutoNamer autoNamer;
    private Object[] constructorArgs;

    public ObjectBuilderImpl(Class<T> c, ReflectionUtil reflectionUtil) {
        this.type = c;
        this.reflectionUtil = reflectionUtil;
    }

    /**
     *
     * @param args
     * @return
     */
    @Override
    public ObjectBuilder<T> WithConstructorArgs(Object[] args) {
        constructorArgs = args;
        return this;
    }

    @Override
    public ObjectBuilder<T> WithAutoNamer(AutoNamer autoNamer) {
        this.autoNamer = autoNamer;
        return this;
    }

    public T CallFunctions(T obj) {
        
        // These are functions which take the object as a parameter and return it
        for (Function<T,T> action : actions) {
            obj = action.apply(obj);
        }
        
        return obj;
    }

    public T Construct() throws BuilderException {

        boolean requiresArgs = reflectionUtil.RequiresConstructorArgs(type);

        if (type.isInterface()) {
            throw new TypeCreationException("Cannot build an interface");
        }

        // TODO: find out if this is an abtract class?
//        if (type.isAbstract) {
//            throw new TypeCreationException("Cannot build an abstract class");
//        }

        T obj;

        try {
            if (requiresArgs && constructorArgs != null) {
                obj = reflectionUtil.CreateInstanceOf(type, constructorArgs);
            } else if (constructorArgs != null) {
                obj = reflectionUtil.CreateInstanceOf(type, constructorArgs);
            } else {
                obj = reflectionUtil.CreateInstanceOf(type);
            }
        } catch (TypeCreationException e) {
            throw new TypeCreationException("Could not construct an instance of " + type.getName(), e);
        }

        return obj;
    }

    public T Name(T obj) throws BuilderException {
        if (!BuilderSetup.AutoNameProperties || autoNamer == null) {
            return obj;
        }
        try {
            autoNamer.setValuesOf(obj);
        } catch (Exception e) {
            throw new BuilderException("Error while trying to autoname properties.", e);
        }

        return obj;
    }

    @Override
    public T Build() throws BuilderException {
        T obj = Construct();
        obj = Name(obj);
        obj = CallFunctions(obj);

        return obj;
    }
   
    
    @Override
    public ObjectBuilder<T> With(Function<T,T> func) {
        super.With(func);
        return this;
    }

    @Override
    public <TFunc> ObjectBuilder<T> Do(Function2<TFunc, T> func, TFunc arg) {
        super.Do(func, arg);
        return this;
    }

    @Override
    public ObjectBuilder<T> And(Function<T, T> func) {
        super.And(func);
        return this;
    }

    @Override
    public <TFunc> ObjectBuilder<T> And(Function2<TFunc, T> func, TFunc arg) {
        super.And(func, arg);
        return this;
    }

    @Override
    public <TFunc> ObjectBuilder<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list) {
        super.DoForEach(func, list);
        return this;
    }
    
    
}
