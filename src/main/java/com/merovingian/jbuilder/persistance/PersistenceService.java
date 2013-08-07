/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.persistance;

import com.google.common.base.Function;
import java.util.List;

/**
 *
 * @author jasonr
 */
public interface PersistenceService {
    
    <T> void Create(T obj) throws PersistenceMethodNotFoundException;

    <T> void Create(List<T> obj) throws PersistenceMethodNotFoundException;

    <T> void Update(T obj) throws PersistenceMethodNotFoundException;

    <T> void Update(List<T> obj) throws PersistenceMethodNotFoundException;

    <F,T> void SetPersistenceCreateMethod(Class<T> c, Function<F,T> createMethod);

    <F,T> void SetPersistenceUpdateMethod(Class<T> c, Function<F,T> updateMethod);
    
}
