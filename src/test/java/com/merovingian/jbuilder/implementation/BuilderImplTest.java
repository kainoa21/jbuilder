/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.merovingian.jbuilder.BuilderSetup;
import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.propertynaming.PropertyNamer;
import com.merovingian.jbuilder.propertynaming.SequentialPropertyNamer;
import com.merovingian.jbuilder.util.ReflectionUtilImpl;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 *
 * @author jasonr
 */
public class BuilderImplTest extends TestCase {
    
    public BuilderImplTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp(); 
        BuilderSetup.ResetToDefaults();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of CreateNew method, of class BuilderImpl.
     */
    public void testCreateNew() {
        System.out.println("CreateNew");
        BuilderImpl instance = new BuilderImpl<String>(String.class);
        ObjectBuilder result = instance.CreateNew();
    }

    /**
     * Test of CreateListOfSize method, of class BuilderImpl.
     */
    public void testCreateListOfSize_int() {
        System.out.println("CreateListOfSize");
        int size = 10;
        ListBuilder result = new BuilderImpl<String>(String.class).CreateListOfSize(size);
        assertEquals(size, result.getCapacity());
    }

    /**
     * Test of CreateListOfSize method, of class BuilderImpl.
     */
    public void testCreateListOfSize_int_PropertyNamer() {
        System.out.println("CreateListOfSize");
        int size = 10;
        PropertyNamer propertyNamer = new SequentialPropertyNamer(new ReflectionUtilImpl());
        ListBuilder result = new BuilderImpl<String>(String.class).CreateListOfSize(size, propertyNamer);
        assertEquals(size, result.getCapacity());
    }
}
