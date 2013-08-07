/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.persistance;

import com.google.common.base.Function;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jasonr
 */
public class PersistenceServiceImpl implements PersistenceService {
    
    public Map<Class<?>, Function> createMethods;
    public Map<Class<?>, Function> updateMethods;

    public PersistenceServiceImpl() {
        createMethods = new HashMap<Class<?>, Function>();
        updateMethods = new HashMap<Class<?>, Function>();
    }

    public <T> void Create(T obj) throws PersistenceMethodNotFoundException {
        
        if (createMethods.isEmpty() || !createMethods.containsKey(obj.getClass())) {
            throw new PersistenceMethodNotFoundException("No persistence create method set up for " + obj.getClass() + ". Add one using BuilderSetup.SetPersistenceCreateMethod()");
        }
        
        createMethods.get(obj.getClass()).apply(obj);

    }

    public <T> void Create(List<T> obj) throws PersistenceMethodNotFoundException {
        
        if (createMethods.isEmpty() || !createMethods.containsKey(obj.getClass())) {
            throw new PersistenceMethodNotFoundException("No persistence create method set up for " + obj.getClass() + ". Add one using BuilderSetup.SetPersistenceCreateMethod()");
        }

        createMethods.get(obj.getClass()).apply(obj);

    }
    
    public <T> void Update(T obj) throws PersistenceMethodNotFoundException {
        if (updateMethods.isEmpty() || !updateMethods.containsKey(obj.getClass())) {
            throw new PersistenceMethodNotFoundException("No persistence create method set up for " + obj.getClass() + ". Add one using BuilderSetup.SetPersistenceCreateMethod()");
        }

        updateMethods.get(obj.getClass()).apply(obj);
    }
    
    public <T> void Update(List<T> obj) throws PersistenceMethodNotFoundException {
        
        if (updateMethods.isEmpty() || !updateMethods.containsKey(obj.getClass())) {
            throw new PersistenceMethodNotFoundException("No persistence create method set up for " + obj.getClass() + ". Add one using BuilderSetup.SetPersistenceCreateMethod()");
        }

        updateMethods.get(obj.getClass()).apply(obj);
    }

    public <F, T> void SetPersistenceCreateMethod(Class<T> c, Function<F, T> createMethod) {
        createMethods.put(c, createMethod);
    }

    public <F, T> void SetPersistenceUpdateMethod(Class<T> c, Function<F, T> updateMethod) {
        updateMethods.put(c, updateMethod);

    }

}
