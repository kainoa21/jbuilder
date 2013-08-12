/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.merovingian.jbuilder.BuilderSetup;
import com.merovingian.jbuilder.NoEmptyConstructorTestClass;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.SimpleTestClass;
import com.merovingian.jbuilder.TestBase;
import com.merovingian.jbuilder.exceptions.BuilderException;
import java.util.Arrays;
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
public class ObjectBuilderImplTest  extends TestBase {
    
    private ObjectBuilder<String> stringBuilder;
    private ObjectBuilder<SimpleTestClass> simpleBuilder;
    private ObjectBuilder<NoEmptyConstructorTestClass> constructBuilder;
   
    public ObjectBuilderImplTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        stringBuilder = new ObjectBuilderImpl<String>(String.class, reflecUtil);
        simpleBuilder = new ObjectBuilderImpl<SimpleTestClass>(SimpleTestClass.class, reflecUtil);
        constructBuilder = new ObjectBuilderImpl<NoEmptyConstructorTestClass>(NoEmptyConstructorTestClass.class, reflecUtil);
    }
    
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of WithConstructorArgs method, of class ObjectBuilderImpl.
     */
    @Test
    public void testWithConstructorArgs() throws BuilderException {
        System.out.println("WithConstructorArgs");
        BuilderSetup.AutoNameProperties = false;
       
        String[] args = new String[]{TEST_VALUE}; 
        ObjectBuilder<String> instance = stringBuilder.WithConstructorArgs(args);

        assertNotNull(instance);
        assertEquals(TEST_VALUE, instance.Build());
        
    }

    /**
     * Test of WithAutoNamer method, of class ObjectBuilderImpl.
     */
    @Test
    public void testWithPropertyNamer() throws BuilderException {
        System.out.println("WithPropertyNamer");
        BuilderSetup.AutoNameProperties = true;
        
        SimpleTestClass instance = simpleBuilder.WithAutoNamer(autoNamer).Build();
        
        assertNotNull(instance);
        assertEquals(TEST_VALUE, instance.myTestField);
        
    }

    /**
     * Test of CallFunctions method, of class ObjectBuilderImpl.
     */
    @Test
    public void testCallFunctions() throws BuilderException {
        System.out.println("CallFunctions");
        BuilderSetup.AutoNameProperties = false;
        
        // Simple Function
        ObjectBuilder<String> instance = stringBuilder.With(setString);
    
        assertNotNull(instance);
        assertEquals(TEST_VALUE, instance.Build());
        
        instance.Do(setString2, "newValue");
        assertEquals("newValue", instance.Build());
        
        instance.DoForEach(stringConcat, Arrays.asList("another","value"));
        assertEquals("newValue" + "anothervalue", instance.Build());

    }

//    /**
//     * Test of Construct method, of class ObjectBuilderImpl.
//     */
//    @Test
//    public void testConstruct() throws BuilderException {
//        System.out.println("Construct");
//        
//        String result = stringBuilder.Construct();
//        assertNotNull(result);
//        assertEquals("", result);
//        
//        result = stringBuilder.WithConstructorArgs(null).Construct();
//        assertNotNull(result);
//        assertEquals("", result);
//        
//        String[] args = new String[]{TEST_VALUE}; 
//        result = stringBuilder.WithConstructorArgs(args).Construct();
//        assertNotNull(result);
//        assertEquals(TEST_VALUE, result);
//    }
//    
//    @Test(expected = TypeCreationException.class)
//    public void testConstructMissingConstructorArgs() throws BuilderException {
//        System.out.println("Construct");
//        
//        NoEmptyConstructorTestClass result = constructBuilder.Construct();
//        fail("Should have thrown a TypeCreationException.");
//    }
//    
//    /**
//     * Test of Construct method, of class ObjectBuilderImpl.
//     */
//    @Test(expected = TypeCreationException.class)
//    public void testConstructInterface() throws BuilderException {
//        System.out.println("Construct");
//        
//        ObjectBuilder<Collection> instance = new ObjectBuilderImpl<Collection>(Collection.class, reflecUtil);
//        
//        Collection result = instance.Construct();
//        fail("Should have thrown a TypeCreationException.");
//        
//    }
//    
//    /**
//     * Test of Construct method, of class ObjectBuilderImpl.
//     */
//    @Test(expected = TypeCreationException.class)
//    public void testConstructAbstract() throws BuilderException {
//        System.out.println("Construct");
//        
//        ObjectBuilder<AbstractCollection> instance = new ObjectBuilderImpl<AbstractCollection>(AbstractCollection.class, reflecUtil);
//        
//        AbstractCollection result = instance.Construct();
//        fail("Should have thrown a TypeCreationException.");
//        
//    }

//    /**
//     * Test of Name method, of class ObjectBuilderImpl.
//     */
//    @Test
//    public void testName() throws BuilderException, NoSuchFieldException {
//        
//        SimpleTestClass instance = new SimpleTestClass();
//        
//        // With AutoNameProperties = false
//        BuilderSetup.AutoNameProperties = false;
//        instance = simpleBuilder.WithAutoNamer(autoNamer).Name(instance);
//        assertNotNull(instance);
//        assertEquals("original", instance.myTestField);
//        
//        // With AutoNameProperties = true
//        BuilderSetup.AutoNameProperties = true;
//        instance = simpleBuilder.Name(instance);
//        assertNotNull(instance);
//        assertEquals(TEST_VALUE, instance.myTestField);
//        
//        // Disable a property
//        BuilderSetup.DisablePropertyNamingFor(instance.getClass().getField(TEST_FIELD));
//        instance = new SimpleTestClass();
//        instance = simpleBuilder.Name(instance);
//        assertNotNull(instance);
//        assertEquals("original", instance.myTestField);
//         
//    }

    /**
     * Test of Build method, of class ObjectBuilderImpl.
     */
    @Test
    public void testBuild() throws BuilderException, NoSuchFieldException  {
        System.out.println("Build");
        
        String[] args = new String[]{TEST_VALUE};
        NoEmptyConstructorTestClass instance = constructBuilder
                .WithConstructorArgs(args)
                .WithAutoNamer(autoNamer)
                .Do(setProperty, TEST_VALUE)
                .Build();
        assertNotNull(instance);
        assertEquals(TEST_VALUE, instance.getMyFinalString());
        assertEquals(TEST_VALUE, instance.myTestField);
        assertEquals(TEST_VALUE, instance.getMyPrivateString());
        
        BuilderSetup.DisablePropertyNamingFor(instance.getClass().getField(TEST_FIELD));
        instance = constructBuilder.Build();
        assertNotNull(instance);
        assertEquals(TEST_VALUE, instance.getMyFinalString());
        assertNull(instance.myTestField);
        assertEquals(TEST_VALUE, instance.getMyPrivateString());
        
        
    }

}
