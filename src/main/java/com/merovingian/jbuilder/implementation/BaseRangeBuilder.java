/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.merovingian.jbuilder.RangeBuilder;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.exceptions.BuilderException;
import com.merovingian.jbuilder.functions.Function2;
import com.merovingian.jbuilder.AutoNamer;
import com.merovingian.jbuilder.BuilderSetup;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasonr
 */
public class BaseRangeBuilder<T> extends BaseOperable<T> implements RangeBuilder<T> {

    protected final ListBuilder<T> listBuilder;
    protected final ObjectBuilder<T> objectBuilder;
    
    protected AutoNamer autoNamer;
    
    private final int start;
    private final int end;

    public BaseRangeBuilder(ListBuilder<T> listBuilder) {
        this(listBuilder, listBuilder.getObjectBuilder(), 0, listBuilder.getEnd());
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="RangeBuilder&lt;T&gt;"/> class.
    /// </summary>
    /// <param name="listBuilderImpl">The list builder.</param>
    /// <param name="objectBuilder">The object builder.</param>
    public BaseRangeBuilder(ListBuilder<T> listBuilder, ObjectBuilder<T> objectBuilder, int start, int end) {
        this.listBuilder = listBuilder;
        this.objectBuilder = objectBuilder;
        this.start = start;
        this.end = end;
    }
    

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getEnd() {
        return end;
    }

    @Override
    public int getNumberOfAffectedItems() {
        return (end + 1) - start;
    }
    
    @Override
    public List<T> BuildRange() throws BuilderException {
        
        List<T> list = Construct(this.getNumberOfAffectedItems());
        list = Name(list);
        list = CallFunctions(list);
        
        return list;
        
    }
    
    public List<T> Construct(int size) throws BuilderException {
        List<T> list = new ArrayList<T>(getNumberOfAffectedItems());
        
        for (int i=0; i < size; i++) {
            T obj = objectBuilder.Build();
            list.add(obj);
        }
        
        return list;
    }
    
    public List<T> CallFunctions(List<T> list) {

        // These actions come from our parent BaseOperable class
        for (Function action : actions) {
            list = Lists.transform(list, action);
        }

        return list;
    }
    
    public List<T> Name(List<T> list) throws BuilderException {
        if (!BuilderSetup.AutoNameProperties || autoNamer == null) {
            return list;
        }
        
        try {
            autoNamer.setValuesOfAllIn(list);
        } catch (Exception e) {
            throw new BuilderException("Error while trying to autoname properties.", e);
        }

        return list;
    }
    
    // Buidlable<List<T>>
    @Override
    public List<T> Build() throws BuilderException {
        return this.listBuilder.Build();
    }
    
    @Override
    public RangeBuilder<T> WithConstructorArgs(Object[] args) {
        this.objectBuilder.WithConstructorArgs(args);
        return this;
    }
    
    @Override
    public RangeBuilder<T> WithAutoNamer(AutoNamer autoNamer) {
        this.autoNamer = autoNamer;
        return this;
    }
    
    // ListOperable<T> - defer to our parent list builder
    // Note that these calls effectively change our context to a newly created Range Builder
    @Override
    public RangeBuilder<T> TheFirst(int amount) {
        return this.listBuilder.TheFirst(amount);
    }
    
    @Override
    public RangeBuilder<T> TheNext(int amount) {
        return this.listBuilder.TheNext(amount);
    }
    @Override
    public RangeBuilder<T> TheLast(int amount) {
        return this.listBuilder.TheLast(amount);
    }
    
    @Override
    public RangeBuilder<T> ThePrevious(int amount) {
        return this.listBuilder.ThePrevious(amount);
    }
    
    @Override
    public RangeBuilder<T> Section(int start, int end) {
        return this.listBuilder.Section(start, end);
    }
    
    
    // Operable<List<T>> - Operable<T> with overriden return type for "Fluidity"
    @Override
    public RangeBuilder<T> With(Function<T, T> func) {
        super.With(func);
        return this;
    }

    @Override
    public <TFunc> RangeBuilder<T> Do(Function2<TFunc, T> func, TFunc arg) {
        super.Do(func, arg);
        return this;
    }

    @Override
    public RangeBuilder<T> And(Function<T, T> func) {
        super.And(func);
        return this;
    }

    @Override
    public <TFunc> RangeBuilder<T> And(Function2<TFunc, T> func, TFunc arg) {
        super.And(func, arg);
        return this;
    }

    @Override
    public <TFunc> RangeBuilder<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list) {
        super.DoForEach(func, list);
        return this;
    }
    
}
