/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.google.common.base.Function;
import com.merovingian.jbuilder.Declaration;
import com.google.common.base.Preconditions;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.util.ReflectionUtil;
import com.merovingian.jbuilder.exceptions.BuilderException;
import com.merovingian.jbuilder.functions.Function2;
import com.merovingian.jbuilder.AutoNamer;
import com.merovingian.jbuilder.BuilderSetup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jasonr
 */
public class ListBuilderImpl<T> implements ListBuilder<T> {

    private final int size;
    private final List<Declaration<T>> declarations;
    private final ObjectBuilder<T> objectBuilder;
    private AutoNamer autoNamer;
    
    private RangeDeclaration<T> lastRangeDeclaration;
    private Declaration<T> globalRangeDeclaration;
    
    // Constructors
    
    public ListBuilderImpl(Class<T> c, int size, ReflectionUtil reflectionUtil) {
        this(new ObjectBuilderImpl<T>(c, reflectionUtil), size);
    }

    public ListBuilderImpl(Class<T> c, int size, ReflectionUtil reflectionUtil, AutoNamer autoNamer) {
        this(c, size, reflectionUtil);
        this.autoNamer = autoNamer;
    }
    
    public ListBuilderImpl(ObjectBuilder<T> objBuilder, int size) {
        this.size = size;
       
        declarations = new ArrayList<Declaration<T>>();
        objectBuilder = objBuilder;
        globalRangeDeclaration = new RangeDeclaration<T>(this, 0, size-1);
       
    }
    
    // ListBuilder Implementation

    @Override
    public ListBuilder<T> All() {
        return this;
    }
    
    @Override
    public ObjectBuilder<T> getObjectBuilder() {
        return objectBuilder;
    }
    
    @Override
    public int size() {
        return this.size;
    }

    // Buildable List<T>
    @Override
    public List<T> Build() throws BuilderException {
       
        // Get a list of our base objects
        List<T> list = new ArrayList<T>(this.size);
        for (int i=0; i < this.size; i++) {
            list.add(this.getObjectBuilder().Build());
        }
        
        // Apply AutoNamer
        list = Name(list);
        
        // Apply any global actions
        list = this.globalRangeDeclaration.CallFunctions(list);
        
        // Apply declarations
        for (Declaration<T> d : declarations) {
            list = d.CallFunctions(list);
        }

        return list;
    }
    
    protected List<T> Name(List<T> list) throws BuilderException {
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
    
    
    // ListOperable<T>
    @Override
    public Declaration<T> TheFirst(int amount) {
        Preconditions.checkArgument(amount > 0, "TheFirst amount must be 1 or greater: %s", amount);
        Preconditions.checkArgument(amount <= size, "TheFirst amount must be less than the size of the list that is being generated %s", amount);

        RangeDeclaration declaration = new RangeDeclaration<T>(this, 0, amount - 1);
        this.addDeclaration(declaration);
        return declaration;
    }

    @Override
    public Declaration<T> TheLast(int amount) {

        Preconditions.checkArgument(amount > 0, "TheLast amount must be 1 or greater: %s", amount);
        Preconditions.checkArgument(amount <= size, "TheLast amount must be less than the size of the list that is being generated %s", amount);


        int start = size - amount;
        RangeDeclaration declaration = new RangeDeclaration<T>(this, start, this.size - 1);
        this.addDeclaration(declaration);
        return declaration;
    }
 
    @Override
    public Declaration<T> Section(int start, int end) {

        Preconditions.checkArgument(start >= 0, "Section - start must be 0 or greater: %s", start);
        Preconditions.checkArgument(start < size, "Section - start must be less than the size of the list that is being generated: %s", start);
        Preconditions.checkArgument(end >= 0, "Section - end must be 0 or greater: %s", start);
        Preconditions.checkArgument(end < size, "Section - end must be less than the size of the list that is being generated: %s", start);
        Preconditions.checkArgument(start <= end, "Section - end must be greater than start.");

        RangeDeclaration declaration = new RangeDeclaration<T>(this, start, end);
        this.addDeclaration(declaration);
        return declaration;
    }
    
    @Override
    public Declaration<T> TheNext(int amount) {
        Preconditions.checkArgument(amount > 0, "TheNext must be 1 or greater: %s", amount);

        RangeDeclaration<T> rangeDeclaration = this.lastRangeDeclaration;

        Preconditions.checkNotNull(rangeDeclaration, "Before using TheNext you must have just used a RangeDeclaration - i.e. (TheFirst or Section)");
        
        int start = rangeDeclaration.getEnd() + 1;
        int end = start + amount - 1;
        
        Preconditions.checkArgument(end < size, "TheNext amount must be less than the remaining capacity of the list being generated: %s", amount);

        RangeDeclaration declaration = new RangeDeclaration<T>(this, start, end);
        this.addDeclaration(declaration);

        return declaration;
    }
    
    @Override
    public Declaration<T> ThePrevious(int amount) {
        Preconditions.checkArgument(amount > 0, "ThePrevious must be 1 or greater: %s", amount);

        RangeDeclaration<T> rangeDeclaration = this.lastRangeDeclaration;

        Preconditions.checkNotNull(rangeDeclaration, "Before using ThePrevious you must have just used a RangeDeclaration - i.e. (TheLast or Section)");
        
        int start = rangeDeclaration.getStart() - amount;
        int end = start + amount - 1;
        
        Preconditions.checkArgument(start >= 0, "ThePrevious amount must be less than the remaining capacity of the list being generated: %s", amount);

        RangeDeclaration declaration = new RangeDeclaration<T>(this, start, end);
        this.addDeclaration(declaration);

        return declaration;
    }

   
    // Operable<List<T>>
    @Override
    public ListBuilder<T> With(Function<T, T> func) {
        this.globalRangeDeclaration.With(func);
        return this;
    }

    @Override
    public <TFunc> ListBuilder<T> Do(Function2<TFunc, T> func, TFunc arg) {
        this.globalRangeDeclaration.Do(func, arg);
        return this;
    }

    @Override
    public ListBuilder<T> And(Function<T, T> func) {
        this.globalRangeDeclaration.And(func);
        return this;
    }

    @Override
    public <TFunc> ListBuilder<T> And(Function2<TFunc, T> func, TFunc arg) {
        this.globalRangeDeclaration.And(func, arg);
        return this;
    }

    @Override
    public <TFunc> ListBuilder<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list) {
        this.globalRangeDeclaration.DoForEach(func, list);
        return this;
    }

    // Buildable<T>
    @Override
    public ListBuilder<T> WithConstructorArgs(Object[] args) {
        this.getObjectBuilder().WithConstructorArgs(args);
        return this;
    }

    @Override
    public ListBuilder<T> WithAutoNamer(AutoNamer autoNamer) {
        this.autoNamer = autoNamer;
        return this;
    }

    // Helper methods
    protected Declaration<T> addDeclaration(Declaration<T> declaration) {

        Preconditions.checkArgument(declaration.getNumberOfAffectedItems() <= this.size(), "A range was added which had an end index greater than or equal to the capacity of the list being generated: %s", declaration.getNumberOfAffectedItems());

        this.declarations.add(declaration);
        
        if (declaration instanceof RangeDeclaration) {
            this.lastRangeDeclaration = (RangeDeclaration)declaration;
        }
        
        return declaration;
    }

//    protected int getDistinctAffectedItemCount() {
//
//        DistinctAffectedItemCalculator distinctAffectedItemCalculator = new DistinctAffectedItemCalculator(this.size);
//
//        for (Declaration d : this.declarations) {
//            distinctAffectedItemCalculator.AddRange(d.getStart(), d.getEnd(), d.getNumberOfAffectedItems());
//        }
//
//        return distinctAffectedItemCalculator.GetTotal();
//    }

}
