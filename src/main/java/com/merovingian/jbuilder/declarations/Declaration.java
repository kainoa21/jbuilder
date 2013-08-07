/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.declarations;

import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.ListOperable;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.exceptions.BuilderException;
import java.util.List;

/**
 *
 * @author jasonr
 */
public interface Declaration<T> extends ObjectBuilder<List<T>>, ListOperable<T> {

    int getNumberOfAffectedItems();

    List<Integer> getMasterListAffectedIndexes();

    int getStart();

    int getEnd();

    ListBuilder<T> getListBuilder();

    ObjectBuilder<T> getObjectBuilder();
    
    List<T> Build() throws BuilderException;

}
