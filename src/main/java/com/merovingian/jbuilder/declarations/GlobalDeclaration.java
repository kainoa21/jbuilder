/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.declarations;

import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.exceptions.BuilderException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasonr
 */
public class GlobalDeclaration<T> extends AbstractDeclaration<T> {
    
    public GlobalDeclaration(ListBuilder<T> listBuilder, ObjectBuilder<T> objectBuilder) {
        super(listBuilder, objectBuilder);
    }

    public int getStart() {
        return 0;
    }

    public int getEnd() {
        return listBuilder.getCapacity();
    }
    
    public List<T> Build(int range) throws BuilderException { 
        List<T> list = new ArrayList<T>(range);
        
        for (int i=0; i < range; i++) {
            T obj = objectBuilder.Build();
            list.add(obj);
        }
        
        return list;
    }

}
