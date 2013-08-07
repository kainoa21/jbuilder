/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.picking;

import com.merovingian.jbuilder.generators.RandomGenerator;

/**
 *
 * @author jasonr
 */
public class BetweenConstraint extends ConstraintImpl {
    
    private final RandomGenerator uniqueRandomGenerator;
    private final int lower;
    private int upper;

    public BetweenConstraint(RandomGenerator uniqueRandomGenerator, int lower) {
        this.uniqueRandomGenerator = uniqueRandomGenerator;
        this.lower = lower;
    }

    public BetweenConstraint(RandomGenerator uniqueRandomGenerator, int lower, int upper) {
        this(uniqueRandomGenerator, lower);
        this.upper = upper;
    }

    @Override
    public int getEnd() {
        return uniqueRandomGenerator.Next(lower, upper);
    }

    public BetweenConstraint And(int end) {
        this.upper = end;
        return this;
    }

}
