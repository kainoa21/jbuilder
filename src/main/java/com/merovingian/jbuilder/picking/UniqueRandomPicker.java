/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.picking;

import com.merovingian.jbuilder.generators.UniqueRandomGenerator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasonr
 */
public class UniqueRandomPicker<T> {

    private final Constraint constraint;
    private final UniqueRandomGenerator uniqueRandomGenerator;

    public UniqueRandomPicker(Constraint constraint, UniqueRandomGenerator uniqueRandomGenerator) {
        this.constraint = constraint;
        this.uniqueRandomGenerator = uniqueRandomGenerator;
    }

    public List<T> From(List<T> listToPickFrom) {
        uniqueRandomGenerator.reset();

        int capacity = listToPickFrom.size();
        int end = constraint.getEnd();

        List<T> listToReturn = new ArrayList<T>(end);

        for (int i = 0; i < end; i++) {
            int index = uniqueRandomGenerator.Next(0, capacity);
            listToReturn.add(listToPickFrom.get(i));
        }

        return listToReturn;
    }

}
