/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.picking;

import com.merovingian.jbuilder.generators.RandomGeneratorImpl;

/**
 *
 * @author jasonr
 */
public final class With {
    
    private With() {}

    public static UpToConstraint UpTo(int count) {
        return new UpToConstraint(new RandomGeneratorImpl(), count);
    }

    public static BetweenConstraint Between(int lower) {
        return new BetweenConstraint(new RandomGeneratorImpl(), lower);
    }

    public static BetweenConstraint Between(int lower, int upper) {
        return new BetweenConstraint(new RandomGeneratorImpl(), lower, upper);
    }

    public static ExactlyConstraint Exactly(int count) {
        return new ExactlyConstraint(count);
    }
}
