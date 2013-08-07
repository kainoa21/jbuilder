/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.picking;

import com.merovingian.jbuilder.generators.RandomGeneratorImpl;
import com.merovingian.jbuilder.generators.UniqueRandomGeneratorImpl;
import java.util.List;

/**
 *
 * @author jasonr
 */
public final class Pick<T> {
    
    public Pick() {}
    
    public UniqueRandomPicker<T> UniqueRandomList(int count) {
        return new UniqueRandomPicker<T>(With.Exactly(count).Elements(), new UniqueRandomGeneratorImpl());
    }

    public UniqueRandomPicker<T> UniqueRandomList(Constraint constraint) {
        return new UniqueRandomPicker<T>(constraint, new UniqueRandomGeneratorImpl());
    }

    public T RandomItemFrom(List<T> list) {
        return new RandomItemPicker<T>(list, new RandomGeneratorImpl()).Pick();
    }
}
