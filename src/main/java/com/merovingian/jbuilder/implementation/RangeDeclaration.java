/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.merovingian.jbuilder.ListBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasonr
 */
public class RangeDeclaration<T> extends AbstractDeclaration<T> {

    
    private final int start;
    private final int end;
    
    private final List<Integer> indices;

    /// <summary>
    /// Initializes a new instance of the <see cref="Declaration&lt;T&gt;"/> class.
    /// </summary>
    /// <param name="listBuilderImpl">The list builder.</param>
    /// <param name="objectBuilder">The object builder.</param>
    public RangeDeclaration(ListBuilder<T> listBuilder, int start, int end) {
        super(listBuilder);
        this.start = start;
        this.end = end;
        
        indices = new ArrayList<Integer>(end-start+1);
        for (int i=start; i <= end; i++) {
            indices.add(i);
        }
    }
    

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
    
    @Override
    public List<Integer> getIndicesOfAffectedItems() {
        return indices;
    }
    
}
