/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.google.common.base.Function;
import com.merovingian.jbuilder.Declaration;
import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.exceptions.BuilderException;
import com.merovingian.jbuilder.functions.Function2;
import java.util.List;

/**
 *
 * @author jasonr
 */
public abstract class AbstractDeclaration<T> extends BaseOperable<T> implements Declaration<T> {

    private final ListBuilder<T> listBuilder;
    
    protected AbstractDeclaration(ListBuilder<T> listBuilder) {
        this.listBuilder = listBuilder;
    }
    
    @Override
    public int getNumberOfAffectedItems() {
        return this.getIndicesOfAffectedItems().size();
    }

    @Override
    public List<T> CallFunctions(List<T> list) {
        for (Function<T,T> action : actions) {
            for (Integer index : this.getIndicesOfAffectedItems()) {
                list.set(index, action.apply(list.get(index)));
            }
        }

        return list;
    }

    @Override
    public List<T> Build() throws BuilderException {
        return this.listBuilder.Build();
    }

    @Override
    public Declaration<T> TheFirst(int amount) {
        return this.listBuilder.TheFirst(amount);
    }

    @Override
    public Declaration<T> TheNext(int amount) {
        return this.listBuilder.TheNext(amount);
    }

    @Override
    public Declaration<T> TheLast(int amount) {
        return this.listBuilder.TheLast(amount);
    }

    @Override
    public Declaration<T> ThePrevious(int amount) {
        return this.listBuilder.ThePrevious(amount);
    }

    @Override
    public Declaration<T> Section(int start, int end) {
        return this.listBuilder.Section(start, end);
    }

    @Override
    public Declaration<T> With(Function<T, T> func) {
        super.With(func);
        return this;
    }

    @Override
    public <TFunc> Declaration<T> Do(Function2<TFunc, T> func, TFunc arg) {
        super.Do(func, arg);
        return this;
    }

    @Override
    public Declaration<T> And(Function<T, T> func) {
        super.And(func);
        return this;
    }

    @Override
    public <TFunc> Declaration<T> And(Function2<TFunc, T> func, TFunc arg) {
        super.And(func, arg);
        return this;
    }

    @Override
    public <TFunc> Declaration<T> DoForEach(Function2<TFunc, T> func, List<TFunc> list) {
        super.DoForEach(func, list);
        return this;
    }
    
}
