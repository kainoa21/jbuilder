/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.declarations;

import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.exceptions.BuilderException;
import java.util.List;

/**
 *
 * @author jasonr
 */
public class RangeDeclaration<T> extends AbstractDeclaration<T> {

    private final int start;
    private final int end;

    public RangeDeclaration(ListBuilder<T> listBuilder, ObjectBuilder<T> objectBuilder, int start, int end) {
        super(listBuilder, objectBuilder);
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getNumberOfAffectedItems() {
        return (end + 1) - start;
    }
}
