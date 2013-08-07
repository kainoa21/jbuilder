/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.merovingian.jbuilder.util.DistinctAffectedItemCalculator;
import com.merovingian.jbuilder.declarations.Declaration;
import com.google.common.base.Preconditions;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.util.ReflectionUtil;
import com.merovingian.jbuilder.exceptions.BuilderException;
import com.merovingian.jbuilder.BuilderSetup;
import com.merovingian.jbuilder.ListOperable;
import com.merovingian.jbuilder.declarations.DeclarationComparer;
import com.merovingian.jbuilder.declarations.GlobalDeclaration;
import com.merovingian.jbuilder.declarations.RangeDeclaration;
import com.merovingian.jbuilder.generators.UniqueRandomGenerator;
import com.merovingian.jbuilder.generators.UniqueRandomGeneratorImpl;
import com.merovingian.jbuilder.propertynaming.PropertyNamer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author jasonr
 */
public class ListBuilderImpl<T> extends AbstractOperable<List<T>> implements ListBuilder<T> {

    private final Class<T> type;
    private final int size;
    private final PropertyNamer propertyNamer;
    private final ReflectionUtil reflectionUtil;
    private final PriorityQueue<Declaration<T>> declarations;
    private final ObjectBuilder<T> objectBuilder;
    private GlobalDeclaration<T> globalDeclaration;
    private RangeDeclaration<T> lastDeclaration;
    private UniqueRandomGenerator scopeUniqueRandomGenerator;

    public ListBuilderImpl(Class<T> c, int size, PropertyNamer propertyNamer, ReflectionUtil reflectionUtil) {
        this.type = c;
        this.size = size;
        this.propertyNamer = propertyNamer;
        this.reflectionUtil = reflectionUtil;

        declarations = new PriorityQueue<Declaration<T>>(1, new DeclarationComparer());

        scopeUniqueRandomGenerator = new UniqueRandomGeneratorImpl();
        
        objectBuilder = new ObjectBuilderImpl<T>(c, reflectionUtil);
       
    }

    public int getCapacity() {
        return size;
    }

    public Collection<Declaration<T>> getDeclarations() {
        return declarations;
    }

    public ObjectBuilder<T> getObjectBuilder() {
        return objectBuilder;
    }

    public ListBuilder<T> All() {
        this.globalDeclaration = new GlobalDeclaration<T>(this, getObjectBuilder());
        return this;
    }

    public void Construct() throws BuilderException {
        
        if (getDistinctAffectedItemCount() < getCapacity()
                && globalDeclaration == null
                && reflectionUtil.RequiresConstructorArgs(type)) {
            throw new BuilderException("The type requires constructor args but they have not be supplied for all the elements of the list");
        }

        // Add a global declaration to construct default items for this list for any ranges which have not been explicitly declared
        if (getDistinctAffectedItemCount() < getCapacity() && globalDeclaration == null) {
            this.globalDeclaration = new GlobalDeclaration<T>(this, getObjectBuilder());
        }
        
    }

    public List<T> Name(List<T> list) throws BuilderException {

        if (!BuilderSetup.AutoNameProperties) {
            return list;
        }

        try {
            propertyNamer.setValuesOfAllIn(list);
        } catch (Exception e) {
            throw new BuilderException("Error while trying to autoname properties.", e);
        }
        
        return list;
    }

    public List<T> Build() throws BuilderException {
        
        List<T> list = new ArrayList<T>(getCapacity());
        
        Construct();
        
        int index=0;
        Declaration d = declarations.poll();
        while (d != null) {
            if (index < d.getStart()) {
                // then use the global declaration to contruct until we get to the next declaration
                list.addAll(this.globalDeclaration.Build(d.getStart() - index));
            }
            
            list.addAll(d.Build());
            index = d.getEnd() + 1;
        }

        return list;
    }

    public Declaration<T> addDeclaration(Declaration<T> declaration) {
        
        Preconditions.checkArgument(declaration.getStart() > 0, "A declaration was added which had a start index less than zero: %s", declaration.getEnd());
        Preconditions.checkArgument(declaration.getEnd() < this.getCapacity(), "A declaration was added which had an end index greater than the capacity of the list being generated: %s", declaration.getEnd());
        
        this.declarations.add(declaration);    
        
        if (declaration instanceof RangeDeclaration) {
            this.lastDeclaration = (RangeDeclaration<T>)declaration;
        }
        
        return declaration;
    }

    public UniqueRandomGenerator getRandomGenerator() {
        return scopeUniqueRandomGenerator;
    }

    private int getDistinctAffectedItemCount() {
        
        DistinctAffectedItemCalculator distinctAffectedItemCalculator = new DistinctAffectedItemCalculator(getCapacity());

        for (Declaration d : getDeclarations()) {
            distinctAffectedItemCalculator.AddRange(d.getStart(), d.getEnd(), d.getNumberOfAffectedItems());
        }

        return distinctAffectedItemCalculator.GetTotal();
    }
    
    /**
     * Declarations Section
     */
    
    public ListOperable<T> TheFirst(int amount) {
        Preconditions.checkArgument(amount > 0, "TheFirst amount must be 1 or greater: %s", amount);
        Preconditions.checkArgument(amount < getCapacity(), "TheFirst amount must be less than the size of the list that is being generated %s", amount);

        Declaration declaration = new RangeDeclaration<T>(this, this.getObjectBuilder(), 0, amount - 1);
        this.addDeclaration(declaration);
        return this;
    }

    public ListOperable<T> TheLast(int amount) {

        Preconditions.checkArgument(amount > 0, "TheLast amount must be 1 or greater: %s", amount);
        Preconditions.checkArgument(amount < getCapacity(), "TheLast amount must be less than the size of the list that is being generated %s", amount);


        int start = getCapacity() - amount;
        Declaration declaration = new RangeDeclaration<T>(this, this.getObjectBuilder(), start, getCapacity() - 1);
        this.addDeclaration(declaration);
        return this;
    }

//    public ListOperable<T> Random(int amount) {
//        return Random(amount, 0, getCapacity());
//    }
//
//    public ListOperable<T> Random(int amount, int start, int end) {
//
//        Preconditions.checkArgument(amount > 0, "Random amount must be 1 or greater: %s", amount);
//        Preconditions.checkArgument(amount < getCapacity(), "Random amount must be less than the size of the list that is being generated %s", amount);
//
//        Declaration declaration = new RandomDeclaration(this, this.getObjectBuilder(), getRandomGenerator(), amount, start, end);
//        return this;
//    }
 
    public ListOperable<T> Section(int start, int end) {

        Preconditions.checkArgument(start >= 0, "Section - start must be 0 or greater: %s", start);
        Preconditions.checkArgument(start < getCapacity(), "Section - start must be less than the size of the list that is being generated: %s", start);
        Preconditions.checkArgument(end >= 1, "Section - end must be 1 or greater: %s", start);
        Preconditions.checkArgument(end < getCapacity(), "Section - end must be less than the size of the list that is being generated: %s", start);
        Preconditions.checkArgument(start <= end, "Section - end must be greater than start.");

        Declaration declaration = new RangeDeclaration<T>(this, this.getObjectBuilder(), start, end);
        this.addDeclaration(declaration);
        return this;
    }
    
    public ListOperable<T> TheNext(int amount) {
        Preconditions.checkArgument(amount > 0, "TheNext must be 1 or greater: %s", amount);

        RangeDeclaration<T> rangeDeclaration = this.lastDeclaration;

        Preconditions.checkNotNull(rangeDeclaration, "Before using TheNext you must have just used a RangeDeclaration - i.e. (TheFirst or Section)");
        
        int start = rangeDeclaration.getEnd() + 1;
        int end = start + amount - 1;
        
        Preconditions.checkArgument(end < getCapacity(), "TheNext amount must be less than the remaining capacity of the list being generated: %s", amount);

        Declaration declaration = new RangeDeclaration<T>(this, this.getObjectBuilder(), start, end);
        this.addDeclaration(declaration);

        return this;
    }
    
    public ListOperable<T> ThePrevious(int amount) {
        Preconditions.checkArgument(amount > 0, "ThePrevious must be 1 or greater: %s", amount);

        RangeDeclaration<T> rangeDeclaration = this.lastDeclaration;

        Preconditions.checkNotNull(rangeDeclaration, "Before using ThePrevious you must have just used a RangeDeclaration - i.e. (TheLast or Section)");
        
        int start = rangeDeclaration.getStart() - amount;
        int end = start + amount - 1;
        
        Preconditions.checkArgument(end >= 0, "ThePrevious amount must be less than the remaining capacity of the list being generated: %s", amount);

        Declaration declaration = new RangeDeclaration<T>(this, this.getObjectBuilder(), start, end);
        this.addDeclaration(declaration);

        return this;
    }
}
