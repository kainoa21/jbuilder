/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder;

import com.merovingian.jbuilder.declarations.Declaration;
import com.merovingian.jbuilder.generators.RandomGenerator;
import java.util.Collection;

/**
 *
 * @author jasonr
 */
public interface ListBuilder<T> extends ListOperable<T> {
    
    ListBuilder<T> All();
    ObjectBuilder<T> getObjectBuilder();
    int getCapacity();
    RandomGenerator getRandomGenerator();
    
    Collection<Declaration<T>> getDeclarations();
    Declaration<T> addDeclaration(Declaration<T> declaration);
    
}
