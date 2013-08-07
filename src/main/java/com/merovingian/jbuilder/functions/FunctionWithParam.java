/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.functions;

import com.google.common.base.Function;

/**
 *
 * @author jasonr
 */
public class FunctionWithParam<F,T> implements Function<T,T> {
    
    private final Function2<F,T> func;
    private final F arg;
    
    public FunctionWithParam(Function2<F,T> func, F arg) {
        this.func = func;
        this.arg = arg;
    }
  
    public T apply(T obj) {
        obj = func.apply(arg, obj);
        return obj;
    }
}
