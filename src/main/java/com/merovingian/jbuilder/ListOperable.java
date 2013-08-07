/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder;

import java.util.List;

/**
 *
 * @author jasonr
 */
public interface ListOperable<T>  extends Operable<List<T>> {
    
    ListOperable<T> TheFirst(int amount);
    ListOperable<T> TheNext(int amount);
    ListOperable<T> TheLast(int amount);
    ListOperable<T> ThePrevious(int amount);
//    ListOperable<T> Random(int amount);
//    ListOperable<T> Random(int amount, int start, int end);
    ListOperable<T> Section(int start, int end);
    
}
