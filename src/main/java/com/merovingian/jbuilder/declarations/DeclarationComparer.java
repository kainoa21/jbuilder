/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.declarations;

import java.util.Comparator;

/**
 *
 * @author jasonr
 */
public class DeclarationComparer<T> implements Comparator<Declaration<T>> {

    public int compare(Declaration<T> t, Declaration<T> t1) {
        
        // Compare their start values
        return Integer.valueOf(t.getStart()).compareTo(Integer.valueOf(t1.getStart()));
    }
    
}
