/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.picking;

/**
 *
 * @author jasonr
 */
public class ExactlyConstraint extends ConstraintImpl {
 
    private final int count;

    public ExactlyConstraint(int count) {
        super();
        this.count = count;
    }

    @Override
    public int getEnd() {
        return count;
    }
}
