/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.util;

import com.merovingian.jbuilder.exceptions.TypeCreationException;
import java.lang.reflect.Constructor;

/**
 *
 * @author jasonr
 */
public class ReflectionUtilImpl implements ReflectionUtil {

    public <T> T CreateInstanceOf(Class<T> c) throws TypeCreationException {
        try {
            return c.getConstructor().newInstance();
        } catch (IllegalAccessException e) {
            throw new TypeCreationException(c.getName() + " parameterless instantiation failed.  The class or its nullary constructor is inaccessible.", e);
        } catch (InstantiationException e) {
            throw new TypeCreationException(c.getName() + " parameterless instantiation failed.  It does noe have a nullary constructor (maybe an abstract class or an interface).", e);
        } catch (NoSuchMethodException e) {
            throw new TypeCreationException(c.getName() + " parameterless instantiation failed.  It does noe have a nullary constructor.", e);
        } catch (ExceptionInInitializerError e) {
            throw new TypeCreationException(c.getName() + " parameterless instantiation failed.  Constructor threw an exception.", e.getCause());
        } catch (SecurityException e) {
            throw new TypeCreationException(c.getName() + " parameterless instantiation threw a security exception.", e);
        } catch (Throwable e) {
            throw new TypeCreationException(c.getName() + " parameterless instantiation failed.", e);
        }
    }

    public <T> T CreateInstanceOf(Class<T> c, Object... args) throws TypeCreationException {
        try {
            
            Class<?>[] types = new Class<?>[args.length];
            for (int i=0; i < args.length; i++) {
                types[i] = args[i].getClass();
            }
            
            return c.getConstructor(types).newInstance(args);
            
        } catch (IllegalAccessException e) {
            throw new TypeCreationException(c.getName() + " parameterless instantiation failed.  The class or its nullary constructor is inaccessible.", e);
        } catch (InstantiationException e) {
            throw new TypeCreationException(c.getName() + " parameterless instantiation failed.  It does noe have a nullary constructor (maybe an abstract class or an interface).", e);
        } catch (NoSuchMethodException e) {
            throw new TypeCreationException(c.getName() + " parameterless instantiation failed.  It does noe have a nullary constructor.", e);
        } catch (ExceptionInInitializerError e) {
            throw new TypeCreationException(c.getName() + " parameterless instantiation failed.  Constructor threw an exception.", e.getCause());
        } catch (SecurityException e) {
            throw new TypeCreationException(c.getName() + " parameterless instantiation threw a security exception.", e);
        } catch (Throwable e) {
            throw new TypeCreationException(c.getName() + " parameterless instantiation failed.", e);
        }
    }

    public boolean RequiresConstructorArgs(Class<?> c) {
        
        // TODO: this isn't right, the .NET version is checking for ValueType?
        if (c.isPrimitive()) {
            return false;
        }

        Constructor[] constructors = c.getConstructors();

        for (Constructor constructorInfo : constructors) {
            if (constructorInfo.getParameterTypes().length == 0) {
                return false;
            }
        }

        return true;
    }

    public boolean IsDefaultValue(Object value) throws TypeCreationException {

        // TODO: this isn't right, the .NET version is checking for ValueType?
        if (value.getClass().isPrimitive()) {
            Class c = value.getClass();
            Object defaultValue = CreateInstanceOf(c);

            if (value.equals(defaultValue)) {
                return true;
            } else {
                return false;
            }
        } else {
            return value == null;
        }

    }

}
