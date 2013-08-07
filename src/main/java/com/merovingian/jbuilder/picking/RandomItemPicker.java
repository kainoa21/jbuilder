/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.picking;

import com.merovingian.jbuilder.generators.RandomGenerator;
import java.util.List;

/**
 *
 * @author jasonr
 */
public class RandomItemPicker<T> {

    private final List<T> from;
    private final RandomGenerator randomGenerator;
    private final int max;

    public RandomItemPicker(List<T> from, RandomGenerator randomGenerator) {
        this.from = from;
        this.randomGenerator = randomGenerator;
        max = from.size();
    }

    public T Pick() {
        int index = randomGenerator.Next(0, max);
        return from.get(index);
    }
}
