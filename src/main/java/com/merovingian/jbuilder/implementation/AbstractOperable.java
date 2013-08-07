/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.merovingian.jbuilder.functions.Function2;
import com.merovingian.jbuilder.functions.MultiFunction;
import com.google.common.base.Function;
import com.merovingian.jbuilder.Operable;
import com.merovingian.jbuilder.functions.FunctionWithParam;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasonr
 */
public abstract class AbstractOperable<T> implements Operable<T> {
    
    protected final List<Function<T,T>> actions = new ArrayList<Function<T,T>>();
    
    public Operable<T> With(Function<T, T> func) {
        actions.add(func);
        return this;
    }

    public <TFunc> Operable<T> Do(Function2<TFunc, T> func, TFunc arg) {
        actions.add(new FunctionWithParam<TFunc, T>(func, arg));
        return this;
    }

    public Operable<T> And(Function<T, T> func) {
        return With(func);
    }

    public <TFunc> Operable<T> And(Function2<TFunc, T> func, TFunc arg) {
        return Do(func, arg);
    }
    
    public <TFunc> Operable<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list) {
        actions.add(new MultiFunction<TFunc, T>(func, list));
        return this;
    }
    
}
