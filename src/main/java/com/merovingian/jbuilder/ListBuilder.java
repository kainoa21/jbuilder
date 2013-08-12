/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder;

import com.google.common.base.Function;
import com.merovingian.jbuilder.declarations.Declaration;
import com.merovingian.jbuilder.functions.Function2;
import com.merovingian.jbuilder.generators.RandomGenerator;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author jasonr
 */
public interface ListBuilder<T> extends ListOperable<T>, Buildable<List<T>> {
    
    ListBuilder<T> All();
    ObjectBuilder<T> getObjectBuilder();
    int getCapacity();
    RandomGenerator getRandomGenerator();
    
    Collection<Declaration<T>> getDeclarations();
    Declaration<T> addDeclaration(Declaration<T> declaration);
    
    @Override
    public ListBuilder<T> With(Function<T, T> func);

    @Override
    public <TFunc> ListBuilder<T> Do(Function2<TFunc, T> func, TFunc arg);
    
    @Override
    public ListBuilder<T> And(Function<T, T> func);
    
    @Override
    public <TFunc> ListBuilder<T> And(Function2<TFunc, T> func, TFunc arg);
    
    @Override
    public <TFunc> ListBuilder<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list);
    
}
