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
public class UpToConstraint extends ConstraintImpl {
    
    private final RandomGenerator randomGenerator;
    private final int upper;

    public UpToConstraint(RandomGenerator uniqueRandomGenerator, int upper) {
        this.randomGenerator = uniqueRandomGenerator;
        this.upper = upper;
    }

    @Override
    public int getEnd() {
        return randomGenerator.Next(0, upper);
    }

}
