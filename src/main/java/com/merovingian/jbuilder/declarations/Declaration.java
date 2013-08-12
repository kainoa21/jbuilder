/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.declarations;

import com.google.common.base.Function;
import com.merovingian.jbuilder.Buildable;
import com.merovingian.jbuilder.ListOperable;
import com.merovingian.jbuilder.exceptions.BuilderException;
import com.merovingian.jbuilder.functions.Function2;
import com.merovingian.jbuilder.propertynaming.PropertyNamer;
import java.util.List;

/**
 *
 * @author jasonr
 */
public interface Declaration<T> extends ListOperable<T>, Buildable<List<T>> {

    int getNumberOfAffectedItems();

//    List<Integer> getMasterListAffectedIndexes();

    int getStart();

    int getEnd();

//    ListBuilder<T> getListBuilder();
//
//    ObjectBuilder<T> getObjectBuilder();
    
    public Declaration<T> WithConstructorArgs(Object[] args);
    
    public Declaration<T> WithPropertyNamer(PropertyNamer propertyNamer);
    
    @Override
    public Declaration<T> With(Function<T, T> func);

    @Override
    public <TFunc> Declaration<T> Do(Function2<TFunc, T> func, TFunc arg);
    
    @Override
    public Declaration<T> And(Function<T, T> func);
    
    @Override
    public <TFunc> Declaration<T> And(Function2<TFunc, T> func, TFunc arg);
    
    @Override
    public <TFunc> Declaration<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list);
    
    @Override
    public List<T> Build() throws BuilderException;

}
