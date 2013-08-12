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
public interface Operable<T> {

    Operable<T> With(Function<T, T> func);

    <TFunc> Operable<T> Do(Function2<TFunc, T> func, TFunc arg);
    
    Operable<T> And(Function<T, T> func);
    
    <TFunc> Operable<T> And(Function2<TFunc, T> func, TFunc arg);
    
    <TFunc> Operable<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list);
}
