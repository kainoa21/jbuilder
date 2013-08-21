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
public interface ListBuilder<T> extends ListOperable<T>, Buildable<List<T>> {
    
    // Add a convenience method to clearly indicate that the following actions should apply to all items in this list
    public ListBuilder<T> All();
    
    // Allow public access to the Object Builder so it is easy to share
    public ObjectBuilder<T> getObjectBuilder();
    
    // Simple getter for the size of the list to be built
    public int size();
    
    // From Operable - Override return type to match
    public ListBuilder<T> With(Function<T, T> func);
    public <TFunc> ListBuilder<T> Do(Function2<TFunc, T> func, TFunc arg);
    public ListBuilder<T> And(Function<T, T> func);
    public <TFunc> ListBuilder<T> And(Function2<TFunc, T> func, TFunc arg);
    public <TFunc> ListBuilder<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list);
    
    // From ListOperable - override return type to match
    Declaration<T> TheFirst(int amount);
    Declaration<T> TheNext(int amount);
    Declaration<T> TheLast(int amount);
    Declaration<T> ThePrevious(int amount);
    Declaration<T> Section(int start, int end);
    
    // From Buildable - override return type to match
    public ListBuilder<T> WithConstructorArgs(Object[] args);
    public ListBuilder<T> WithAutoNamer(AutoNamer autoNamer);
    
}
