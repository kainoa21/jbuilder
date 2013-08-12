/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.google.common.base.Function;
import com.merovingian.jbuilder.util.DistinctAffectedItemCalculator;
import com.merovingian.jbuilder.RangeBuilder;
import com.google.common.base.Preconditions;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.util.ReflectionUtil;
import com.merovingian.jbuilder.exceptions.BuilderException;
import com.merovingian.jbuilder.ListOperable;
import com.merovingian.jbuilder.functions.Function2;
import com.merovingian.jbuilder.AutoNamer;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author jasonr
 */
public class ListBuilderImpl<T> implements ListBuilder<T> {

    private final int size;
    private final PriorityQueue<RangeBuilder<T>> rangeBuilders;
    private final ObjectBuilder<T> objectBuilder;
    
    private RangeBuilder<T> lastRangeBuilder;
    private RangeBuilder<T> globalRangeBuilder;
    
    // Constructors
    
    public ListBuilderImpl(Class<T> c, int size, ReflectionUtil reflectionUtil) {
        this(new ObjectBuilderImpl<T>(c, reflectionUtil), size);
    }

    public ListBuilderImpl(Class<T> c, int size, ReflectionUtil reflectionUtil, AutoNamer autoNamer) {
        this(c, size, reflectionUtil);
        globalRangeBuilder.WithAutoNamer(autoNamer);
    }
    
    public ListBuilderImpl(ObjectBuilder<T> objBuilder, int size) {
        this.size = size;
       
        rangeBuilders = new PriorityQueue<RangeBuilder<T>>(1, new RangeComparer());
        objectBuilder = objBuilder;
        globalRangeBuilder = new BaseRangeBuilder<T>(this);
       
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
 
        List<T> list = new ArrayList<T>(this.size);
        
        // Use our global builder to get a list of all potential items
        // We will use this list to grab any items that are otherwise unspecified
        List<T> globalList = this.BuildRange();

        int index = 0;
        RangeBuilder d = rangeBuilders.poll();
        while (d != null) {
         
            // Fill in from the global list
            list.addAll(globalList.subList(index, d.getStart()));

            // Add from the range builder
            list.addAll(d.BuildRange());
            index = d.getEnd() + 1;
            
            d = rangeBuilders.poll();
        }

        // Fill in the end from the global list
        list.addAll(globalList.subList(index, this.size));

        return list;
    }
    
    
    // Range Builder implementation
    @Override
    public int getNumberOfAffectedItems() {
        return this.size - this.getDistinctAffectedItemCount();
    }

    @Override
    public int getStart() {
        return 0;
    }

    @Override
    public int getEnd() {
        return this.size - 1;
    }
    
    @Override
    public List<T> BuildRange() throws BuilderException {
        return this.globalRangeBuilder.BuildRange();
    }
    
    // ListOperable<T>
    @Override
    public RangeBuilder<T> TheFirst(int amount) {
        Preconditions.checkArgument(amount > 0, "TheFirst amount must be 1 or greater: %s", amount);
        Preconditions.checkArgument(amount <= size, "TheFirst amount must be less than the size of the list that is being generated %s", amount);

        RangeBuilder declaration = new BaseRangeBuilder<T>(this, this.getObjectBuilder(), 0, amount - 1);
        this.addRangeBuilder(declaration);
        return declaration;
    }

    @Override
    public RangeBuilder<T> TheLast(int amount) {

        Preconditions.checkArgument(amount > 0, "TheLast amount must be 1 or greater: %s", amount);
        Preconditions.checkArgument(amount <= size, "TheLast amount must be less than the size of the list that is being generated %s", amount);


        int start = size - amount;
        RangeBuilder declaration = new BaseRangeBuilder<T>(this, this.getObjectBuilder(), start, this.getEnd());
        this.addRangeBuilder(declaration);
        return declaration;
    }
 
    @Override
    public RangeBuilder<T> Section(int start, int end) {

        Preconditions.checkArgument(start >= 0, "Section - start must be 0 or greater: %s", start);
        Preconditions.checkArgument(start < size, "Section - start must be less than the size of the list that is being generated: %s", start);
        Preconditions.checkArgument(end >= 0, "Section - end must be 0 or greater: %s", start);
        Preconditions.checkArgument(end < size, "Section - end must be less than the size of the list that is being generated: %s", start);
        Preconditions.checkArgument(start <= end, "Section - end must be greater than start.");

        RangeBuilder declaration = new BaseRangeBuilder<T>(this, this.getObjectBuilder(), start, end);
        this.addRangeBuilder(declaration);
        return declaration;
    }
    
    @Override
    public RangeBuilder<T> TheNext(int amount) {
        Preconditions.checkArgument(amount > 0, "TheNext must be 1 or greater: %s", amount);

        RangeBuilder<T> rangeDeclaration = this.lastRangeBuilder;

        Preconditions.checkNotNull(rangeDeclaration, "Before using TheNext you must have just used a RangeDeclaration - i.e. (TheFirst or Section)");
        
        int start = rangeDeclaration.getEnd() + 1;
        int end = start + amount - 1;
        
        Preconditions.checkArgument(end < size, "TheNext amount must be less than the remaining capacity of the list being generated: %s", amount);

        RangeBuilder declaration = new BaseRangeBuilder<T>(this, this.getObjectBuilder(), start, end);
        this.addRangeBuilder(declaration);

        return declaration;
    }
    
    @Override
    public RangeBuilder<T> ThePrevious(int amount) {
        Preconditions.checkArgument(amount > 0, "ThePrevious must be 1 or greater: %s", amount);

        RangeBuilder<T> rangeDeclaration = this.lastRangeBuilder;

        Preconditions.checkNotNull(rangeDeclaration, "Before using ThePrevious you must have just used a RangeDeclaration - i.e. (TheLast or Section)");
        
        int start = rangeDeclaration.getStart() - amount;
        int end = start + amount - 1;
        
        Preconditions.checkArgument(end >= 0, "ThePrevious amount must be less than the remaining capacity of the list being generated: %s", amount);

        RangeBuilder declaration = new BaseRangeBuilder<T>(this, this.getObjectBuilder(), start, end);
        this.addRangeBuilder(declaration);

        return declaration;
    }

   
    // Operable<List<T>>
    @Override
    public ListBuilder<T> With(Function<T, T> func) {
        this.globalRangeBuilder.With(func);
        return this;
    }

    @Override
    public <TFunc> ListBuilder<T> Do(Function2<TFunc, T> func, TFunc arg) {
        this.globalRangeBuilder.Do(func, arg);
        return this;
    }

    @Override
    public ListBuilder<T> And(Function<T, T> func) {
        this.globalRangeBuilder.And(func);
        return this;
    }

    @Override
    public <TFunc> ListBuilder<T> And(Function2<TFunc, T> func, TFunc arg) {
        this.globalRangeBuilder.And(func, arg);
        return this;
    }

    @Override
    public <TFunc> ListBuilder<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list) {
        this.globalRangeBuilder.DoForEach(func, list);
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
        this.globalRangeBuilder.WithAutoNamer(autoNamer);
        return this;
    }

    // Helper methods
    protected RangeBuilder<T> addRangeBuilder(RangeBuilder<T> rangeBuilder) {

        Preconditions.checkArgument(rangeBuilder.getStart() >= 0, "A range was added which had a start index less than or equal to zero: %s", rangeBuilder.getStart());
        Preconditions.checkArgument(rangeBuilder.getEnd() < this.size(), "A range was added which had an end index greater than or equal to the capacity of the list being generated: %s", rangeBuilder.getEnd());

        this.rangeBuilders.add(rangeBuilder);
        this.lastRangeBuilder = rangeBuilder;

        return rangeBuilder;
    }

    protected int getDistinctAffectedItemCount() {

        DistinctAffectedItemCalculator distinctAffectedItemCalculator = new DistinctAffectedItemCalculator(this.size);

        for (RangeBuilder d : this.rangeBuilders) {
            distinctAffectedItemCalculator.AddRange(d.getStart(), d.getEnd(), d.getNumberOfAffectedItems());
        }

        return distinctAffectedItemCalculator.GetTotal();
    }

}
