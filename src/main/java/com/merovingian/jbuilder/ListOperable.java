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
public interface ListOperable<T> extends Operable<T> {
    
    ListOperable<T> TheFirst(int amount);
    ListOperable<T> TheNext(int amount);
    ListOperable<T> TheLast(int amount);
    ListOperable<T> ThePrevious(int amount);
    ListOperable<T> Section(int start, int end);
    
    // Operable - override return type for fluidity
    @Override
    ListOperable<T> With(Function<T, T> func);

    @Override
    <TFunc> ListOperable<T> Do(Function2<TFunc, T> func, TFunc arg);
    
    @Override
    ListOperable<T> And(Function<T, T> func);
    
    @Override
    <TFunc> ListOperable<T> And(Function2<TFunc, T> func, TFunc arg);
    
    @Override
    <TFunc> ListOperable<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list);
    
}
