/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.declarations;

import com.google.common.base.Function;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.ListOperable;
import com.merovingian.jbuilder.exceptions.BuilderException;
import com.merovingian.jbuilder.functions.Function2;
import com.merovingian.jbuilder.implementation.AbstractOperable;
import com.merovingian.jbuilder.propertynaming.PropertyNamer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasonr
 */
public abstract class AbstractDeclaration<T> extends AbstractOperable<T> implements Declaration<T> {

    protected final ListBuilder<T> listBuilder;
    protected final ObjectBuilder<T> objectBuilder;
//    private List<Integer> masterListAffectedIndexes;

    protected AbstractDeclaration(ListBuilder<T> listBuilder) {
        this(listBuilder, listBuilder.getObjectBuilder());
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="Declaration&lt;T&gt;"/> class.
    /// </summary>
    /// <param name="listBuilderImpl">The list builder.</param>
    /// <param name="objectBuilder">The object builder.</param>
    protected AbstractDeclaration(ListBuilder<T> listBuilder, ObjectBuilder<T> objectBuilder) {
        this.listBuilder = listBuilder;
        this.objectBuilder = objectBuilder;
//        masterListAffectedIndexes = new ArrayList<Integer>();
    }
    
//    public ListBuilder<T> getListBuilder() {
//        return listBuilder;
//    }
//
//    public ObjectBuilder<T> getObjectBuilder() {
//        return objectBuilder;
//    }
//
//    public List<Integer> getMasterListAffectedIndexes() {
//        return masterListAffectedIndexes;
//    }
//
//    public void setMasterListAffectedIndexes(List<Integer> value) {
//        masterListAffectedIndexes = value;
//    }

    /// <summary>
    /// Gets the start index (relating to the master list) that this declaration will affect.
    /// </summary>
    @Override
    public abstract int getStart();

    /// <summary>
    /// Gets the end index (relating to the master list) that this declaration will affect.
    /// </summary>
    @Override
    public abstract int getEnd();

    @Override
    public int getNumberOfAffectedItems() {
        return getEnd() - getStart() + 1;
    }
    
//    public List<T> Construct() throws BuilderException {
//        List<T> list = new ArrayList<T>(getNumberOfAffectedItems());
//        
//        for (int i=0; i < getNumberOfAffectedItems(); i++) {
//            T obj = objectBuilder.Construct();
//            list.add(obj);
//        }
//        
//        return list;
//    }
//    
//    public List<T> CallFunctions(List<T> list) {
//
//        for (Function action : actions) {
//            list = Lists.transform(list, action);
//        }
//
//        return list;
//    }
//    
//    public List<T> Name(List<T> list) throws BuilderException {
//        if (!BuilderSetup.AutoNameProperties) {
//            return list;
//        }
//
//        PropertyNamer propName = this.objectBuilder.getPropertyNamer();
//        
//        try {
//            propName.setValuesOfAllIn(list);
//        } catch (Exception e) {
//            throw new BuilderException("Error while trying to autoname properties.", e);
//        }
//
//        return list;
//    }
    
    @Override
    public List<T> Build() throws BuilderException {
        
        List<T> list = new ArrayList<T>(getNumberOfAffectedItems());
        
        for (int i=0; i < getNumberOfAffectedItems(); i++) {
            T obj = objectBuilder.Build();
            list.add(obj);
        }
        
        return list;

    }
    
    @Override
    public Declaration<T> WithConstructorArgs(Object[] args) {
        this.objectBuilder.WithConstructorArgs(args);
        return this;
    }
    
    @Override
    public Declaration<T> WithPropertyNamer(PropertyNamer propertyNamer) {
        this.objectBuilder.WithPropertyNamer(propertyNamer);
        return this;
    }
    
//    public PropertyNamer getPropertyNamer() {
//        return this.objectBuilder.getPropertyNamer();
//    }
    
    @Override
    public ListOperable<T> TheFirst(int amount) {
        return this.listBuilder.TheFirst(amount);
    }
    
    @Override
    public ListOperable<T> TheNext(int amount) {
        return this.listBuilder.TheNext(amount);
    }
    @Override
    public ListOperable<T> TheLast(int amount) {
        return this.listBuilder.TheLast(amount);
    }
    
    @Override
    public ListOperable<T> ThePrevious(int amount) {
        return this.listBuilder.ThePrevious(amount);
    }
    
    @Override
    public ListOperable<T> Section(int start, int end) {
        return this.listBuilder.Section(start, end);
    }
    
    @Override
    public Declaration<T> With(Function<T, T> func) {
        this.objectBuilder.With(func);
        return this;
    }

    @Override
    public <TFunc> Declaration<T> Do(Function2<TFunc, T> func, TFunc arg) {
        this.objectBuilder.Do(func, arg);
        return this;
    }

    @Override
    public Declaration<T> And(Function<T, T> func) {
        this.objectBuilder.And(func);
        return this;
    }

    @Override
    public <TFunc> Declaration<T> And(Function2<TFunc, T> func, TFunc arg) {
        this.objectBuilder.And(func, arg);
        return this;
    }

    @Override
    public <TFunc> Declaration<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list) {
        this.objectBuilder.DoForEach(func, list);
        return this;
    }
    
}
