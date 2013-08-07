/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.util;

import com.google.common.base.Preconditions;

/**
 *
 * @author jasonr
 */
public class DistinctAffectedItemCalculator {

    public boolean[] map;

    public DistinctAffectedItemCalculator(int capacity) {
        map = new boolean[capacity];
    }

    public void AddRange(int start, int end, int numberOfItems) {
        
        Preconditions.checkArgument((end - start) > (numberOfItems - 1), "The number of items cannot be greater than the range");

        int index = start;
        int endIndex = start + numberOfItems;

        for (; index < endIndex; index++) {
            map[index] = true;
        }
    }

    public int GetTotal() {
        int total = 0;
        for (int i = 0; i < map.length; i++) {
            total += map[i] ? 1 : 0;
        }

        return total;
    }
}
