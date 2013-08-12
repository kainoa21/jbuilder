/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder;

import com.google.common.base.Function;
import com.merovingian.jbuilder.functions.Function2;
import java.util.List;

/**
 *
 * @author jasonr
 */
public interface ObjectBuilder<T> extends Operable<T>, Buildable<T> {
    
    // From Buildable
    @Override
    public ObjectBuilder<T> WithConstructorArgs(Object[] args);
    
    @Override
    public ObjectBuilder<T> WithAutoNamer(AutoNamer propertyNamer);
    
    
    //From Operable    
    @Override
    public ObjectBuilder<T> With(Function<T, T> func);

    @Override
    public <TFunc> ObjectBuilder<T> Do(Function2<TFunc, T> func, TFunc arg);
    
    @Override
    public ObjectBuilder<T> And(Function<T, T> func);
    
    @Override
    public <TFunc> ObjectBuilder<T> And(Function2<TFunc, T> func, TFunc arg);
    
    @Override
    public <TFunc> ObjectBuilder<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list);
    
}
