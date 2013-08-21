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
public interface Declaration<T> extends ListOperable<T> {

    int getNumberOfAffectedItems();
    List<Integer> getIndicesOfAffectedItems();
    public List<T> CallFunctions(List<T> list);
    public List<T> Build() throws BuilderException;
    
    // From ListOperable - override return type to match
    Declaration<T> TheFirst(int amount);
    Declaration<T> TheNext(int amount);
    Declaration<T> TheLast(int amount);
    Declaration<T> ThePrevious(int amount);
    Declaration<T> Section(int start, int end);

    // From Operable - override return type to match
    public Declaration<T> With(Function<T, T> func);
    public <TFunc> Declaration<T> Do(Function2<TFunc, T> func, TFunc arg);
    public Declaration<T> And(Function<T, T> func);
    public <TFunc> Declaration<T> And(Function2<TFunc, T> func, TFunc arg);
    public <TFunc> Declaration<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list);

}
