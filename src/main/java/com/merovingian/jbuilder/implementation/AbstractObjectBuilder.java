/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.merovingian.jbuilder.ObjectBuilder;
import com.google.common.base.Function;
import com.merovingian.jbuilder.functions.Function2;
import com.merovingian.jbuilder.AutoNamer;
import java.util.List;

/**
 *
 * @author jasonr
 */
public abstract class AbstractObjectBuilder<T> extends BaseOperable<T> implements ObjectBuilder<T> {

    protected final Class<T> type;
    protected AutoNamer autoNamer;
    protected Object[] constructorArgs;

    protected AbstractObjectBuilder(Class<T> c) {
        this.type = c;
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
