/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.AutoNamer;
import com.merovingian.jbuilder.TestBase;
import com.merovingian.jbuilder.propertynaming.SequentialPropertyNamer;
import com.merovingian.jbuilder.util.ReflectionUtilImpl;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jasonr
 */
public class BuilderImplTest extends TestBase {
    
    public BuilderImplTest() {
    }

    /**
     * Test of CreateNew method, of class BuilderImpl.
     */
    @Test
    public void testCreateNew() {
        System.out.println("CreateNew");
        BuilderImpl instance = new BuilderImpl<String>(String.class);
        ObjectBuilder result = instance.CreateNew();
    }

    /**
     * Test of CreateListOfSize method, of class BuilderImpl.
     */
    @Test
    public void testCreateListOfSize_int() {
        System.out.println("CreateListOfSize");
        int size = 10;
        ListBuilder result = new BuilderImpl<String>(String.class).CreateListOfSize(size);
        assertEquals(size, result.size());
    }

    /**
     * Test of CreateListOfSize method, of class BuilderImpl.
     */
    @Test
    public void testCreateListOfSize_int_PropertyNamer() {
        System.out.println("CreateListOfSize");
        int size = 10;
        AutoNamer propertyNamer = new SequentialPropertyNamer(new ReflectionUtilImpl());
        ListBuilder result = new BuilderImpl<String>(String.class).CreateListOfSize(size, propertyNamer);
        assertEquals(size, result.size());
    }
}
