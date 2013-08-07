/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jasonr
 */
public class UniqueRandomGeneratorImpl extends RandomGeneratorImpl implements UniqueRandomGenerator {
    
    private Map<Class<?>, List<Object>> trackedValues;
    
    public UniqueRandomGeneratorImpl() {
        reset();
    }
    
    public void reset() {
        trackedValues = new HashMap<Class<?>, List<Object>>();
        trackedValues.put(short.class, new ArrayList<Object>());
        trackedValues.put(int.class, new ArrayList<Object>());
        trackedValues.put(long.class, new ArrayList<Object>());
        trackedValues.put(float.class, new ArrayList<Object>());
        trackedValues.put(double.class, new ArrayList<Object>());
        trackedValues.put(byte.class, new ArrayList<Object>());
        trackedValues.put(char.class, new ArrayList<Object>());
//            trackedValues.put(ushort.class, new ArrayList<Object>());
//            trackedValues.put(uint.class, new ArrayList<Object>());
//            trackedValues.put(ulong.class, new ArrayList<Object>());
//            trackedValues.put(decimal.class, new ArrayList<Object>());
//            trackedValues.put(sbyte.class, new ArrayList<Object>());
    }
    
    //TODO: Come back and implement the rest of this class
    
    
}
