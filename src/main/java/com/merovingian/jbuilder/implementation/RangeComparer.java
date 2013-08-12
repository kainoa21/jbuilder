/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.merovingian.jbuilder.RangeBuilder;
import java.util.Comparator;

/**
 *
 * @author jasonr
 */
public class RangeComparer<T> implements Comparator<RangeBuilder<T>> {

    public int compare(RangeBuilder<T> t, RangeBuilder<T> t1) {
        
        // Compare their start values
        return Integer.valueOf(t.getStart()).compareTo(Integer.valueOf(t1.getStart()));
    }
    
}
