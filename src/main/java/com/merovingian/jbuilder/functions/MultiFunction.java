/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.functions;

import com.google.common.base.Function;
import java.util.List;

/**
 *
 * @author jasonr
 */
public class MultiFunction<F,T> implements Function<T,T> {
    
    private final Function2<F,T> func;
    private final List<F> list;
    
    public MultiFunction(Function2<F,T> func, List<F> list) {
        this.func = func;
        this.list = list;
    }
    
    public T apply(T obj) {
        
        for (F item : list) {
           obj = func.apply(item, obj);
        }
        
        return obj;

    }
    
}
