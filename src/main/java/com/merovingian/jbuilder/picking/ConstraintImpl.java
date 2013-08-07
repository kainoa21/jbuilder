/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.picking;

/**
 *
 * @author jasonr
 */
public abstract class ConstraintImpl implements Constraint {
    
    protected ConstraintImpl() {
    }

    // This allows the code to read better
    // TODO: ???
    public ConstraintImpl Elements() {
        return this;
    }

    @Override
    public abstract int getEnd();

}
