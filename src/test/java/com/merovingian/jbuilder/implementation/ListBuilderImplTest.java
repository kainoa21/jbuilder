/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.merovingian.jbuilder.BuilderSetup;
import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.ListOperable;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.TestBase;
import com.merovingian.jbuilder.declarations.Declaration;
import com.merovingian.jbuilder.declarations.RangeDeclaration;
import com.merovingian.jbuilder.exceptions.BuilderException;
import com.merovingian.jbuilder.generators.UniqueRandomGenerator;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jasonr
 */
public class ListBuilderImplTest extends TestBase {
    
    protected static final int INITIAL_CAPACITY = 10;
    
    protected ListBuilder<String> stringBuilder;    
    
    public ListBuilderImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        BuilderSetup.ResetToDefaults();
        stringBuilder = new ListBuilderImpl<String>(String.class, INITIAL_CAPACITY, propNamer, reflecUtil);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCapacity method, of class ListBuilderImpl.
     */
    @Test
    public void testGetCapacity() {
        System.out.println("getCapacity");
        ListBuilder<String> instance = stringBuilder;
        int expResult = INITIAL_CAPACITY;
        int result = instance.getCapacity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDeclarations method, of class ListBuilderImpl.
     */
    @Test
    public void testGetDeclarations() {
        System.out.println("getDeclarations");
        ListBuilder<String> instance = stringBuilder;
        
        // Should start out as null
        assertEquals(0, instance.getDeclarations().size());
        
        // Global Declarations don't go in this list
        assertEquals(0, instance.All().getDeclarations().size());
        
        // now lets add one
        Declaration<String> dec = new RangeDeclaration<String>(instance, instance.getObjectBuilder(), 0, INITIAL_CAPACITY - 1);
        instance.addDeclaration(dec);
        Collection expResult = Arrays.asList(dec);
        Collection result = instance.getDeclarations();
        assertEquals(expResult.size(), result.size());
    }

    /**
     * Test of getObjectBuilder method, of class ListBuilderImpl.
     */
    @Test
    public void testGetObjectBuilder() {
        System.out.println("getObjectBuilder");
        ObjectBuilder<String> result = stringBuilder.getObjectBuilder();
        ObjectBuilder<String> exp = new ObjectBuilderImpl<String>(String.class, reflecUtil);
        assertNotNull(result);
        assertEquals(exp.getClass(), result.getClass());
    }

    /**
     * Test of All method, of class ListBuilderImpl.
     */
    @Test
    public void testAll() throws BuilderException {
        System.out.println("All");
        List<String> result = stringBuilder
                .All()
                .With(setString)
                .Build();
        assertNotNull(result);
        assertEquals(INITIAL_CAPACITY, result.size());
        
        for (String s : result) {
            assertEquals(TEST_VALUE, s);
        }
    }

    /**
     * Test of Construct method, of class ListBuilderImpl.
     */
    @Test
    public void testConstruct() throws BuilderException {
        System.out.println("Construct");
        ListBuilderImpl instance = null;
        instance.Construct();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Name method, of class ListBuilderImpl.
     */
    @Test
    public void testName() throws BuilderException {
        System.out.println("Name");
        ListBuilderImpl instance = null;
        List expResult = null;
        List result = instance.Name(null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Build method, of class ListBuilderImpl.
     */
    @Test
    public void testBuild() throws BuilderException {
        System.out.println("Build");
        ListBuilderImpl instance = null;
        List expResult = null;
        List result = instance.Build();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addDeclaration method, of class ListBuilderImpl.
     */
    @Test
    public void testAddDeclaration() {
        System.out.println("addDeclaration");
        ListBuilderImpl instance = null;
        Declaration expResult = null;
        Declaration result = instance.addDeclaration(null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRandomGenerator method, of class ListBuilderImpl.
     */
    @Test
    public void testGetRandomGenerator() {
        System.out.println("getRandomGenerator");
        ListBuilderImpl instance = null;
        UniqueRandomGenerator expResult = null;
        UniqueRandomGenerator result = instance.getRandomGenerator();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of TheFirst method, of class ListBuilderImpl.
     */
    @Test
    public void testTheFirst() {
        System.out.println("TheFirst");
        int amount = 0;
        ListBuilderImpl instance = null;
        ListOperable expResult = null;
        ListOperable result = instance.TheFirst(amount);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of TheLast method, of class ListBuilderImpl.
     */
    @Test
    public void testTheLast() {
        System.out.println("TheLast");
        int amount = 0;
        ListBuilderImpl instance = null;
        ListOperable expResult = null;
        ListOperable result = instance.TheLast(amount);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Section method, of class ListBuilderImpl.
     */
    @Test
    public void testSection() {
        System.out.println("Section");
        int start = 0;
        int end = 0;
        ListBuilderImpl instance = null;
        ListOperable expResult = null;
        ListOperable result = instance.Section(start, end);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of TheNext method, of class ListBuilderImpl.
     */
    @Test
    public void testTheNext() {
        System.out.println("TheNext");
        int amount = 0;
        ListBuilderImpl instance = null;
        ListOperable expResult = null;
        ListOperable result = instance.TheNext(amount);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ThePrevious method, of class ListBuilderImpl.
     */
    @Test
    public void testThePrevious() {
        System.out.println("ThePrevious");
        int amount = 0;
        ListBuilderImpl instance = null;
        ListOperable expResult = null;
        ListOperable result = instance.ThePrevious(amount);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}