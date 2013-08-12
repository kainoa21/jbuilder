/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder;

import com.google.common.base.Function;
import com.merovingian.jbuilder.exceptions.BuilderException;
import com.merovingian.jbuilder.functions.Function2;
import java.util.List;

/**
 *
 * @author jasonr
 */
public interface RangeBuilder<T> extends ListOperable<T>, Buildable<List<T>> {

    int getStart();
    int getEnd();
    int getNumberOfAffectedItems();
    public List<T> BuildRange() throws BuilderException;
    
    // From ListOperable - override return type to match
    RangeBuilder<T> TheFirst(int amount);
    RangeBuilder<T> TheNext(int amount);
    RangeBuilder<T> TheLast(int amount);
    RangeBuilder<T> ThePrevious(int amount);
    RangeBuilder<T> Section(int start, int end);

    // From Operable - override return type to match
    public RangeBuilder<T> With(Function<T, T> func);
    public <TFunc> RangeBuilder<T> Do(Function2<TFunc, T> func, TFunc arg);
    public RangeBuilder<T> And(Function<T, T> func);
    public <TFunc> RangeBuilder<T> And(Function2<TFunc, T> func, TFunc arg);
    public <TFunc> RangeBuilder<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list);
       
    // From Buildable - override return type to match
    public RangeBuilder<T> WithConstructorArgs(Object[] args);
    public RangeBuilder<T> WithAutoNamer(AutoNamer autoNamer);

}
