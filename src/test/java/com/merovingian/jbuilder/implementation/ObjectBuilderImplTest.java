/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.implementation;

import com.google.common.base.Function;
import com.merovingian.jbuilder.BuilderSetup;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.Operable;
import com.merovingian.jbuilder.exceptions.BuilderException;
import com.merovingian.jbuilder.exceptions.TypeCreationException;
import com.merovingian.jbuilder.functions.Function2;
import com.merovingian.jbuilder.propertynaming.PropertyNamer;
import com.merovingian.jbuilder.util.ReflectionUtilImpl;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import junit.framework.TestCase;

/**
 *
 * @author jasonr
 */
public class ObjectBuilderImplTest extends TestCase {
    
    private static final String TEST_FIELD = "myTestField";
    private static final String TEST_VALUE = "myTestValue";
    
    private static final PropertyNamer propNamer = new PropertyNamer() {
        public <T> void setValuesOf(T obj) throws InvocationTargetException, IllegalAccessException, TypeCreationException {
            try {
                obj.getClass().getField(TEST_FIELD).set(obj, TEST_VALUE);
            } catch (Exception e) {
            }
        }

        public <T> void setValuesOfAllIn(List<T> list) throws InvocationTargetException, IllegalAccessException, TypeCreationException {
            for (T obj : list) {
                setValuesOf(obj);
            }
        }
    };
    
    private static final Function<String, String> setString = new Function<String,String>() {
        public String apply(String obj) {
            obj = TEST_VALUE;
            return obj;
        }
    };
    
    private static final Function2<String, String> setString2 = new Function2<String,String>() {
        public String apply(String f, String obj) {
            obj = f;
            return obj;
        }
    };
    
    private static final Function2<String, String> stringConcat = new Function2<String,String>() {
        public String apply(String f, String obj) {
            obj += f;
            return obj;
        }
    };

    public ObjectBuilderImplTest(String testName) {
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
     * Test of WithConstructorArgs method, of class ObjectBuilderImpl.
     */
    public void testWithConstructorArgs() throws BuilderException {
        System.out.println("WithConstructorArgs");
        BuilderSetup.AutoNameProperties = false;
        
        String arg = "newString";
       
        String[] args = new String[]{arg}; 
        ObjectBuilder<String> instance = new ObjectBuilderImpl<String>(String.class, new ReflectionUtilImpl())
                .WithConstructorArgs(args);

        assertNotNull(instance);
        assertEquals(arg, instance.Build());
        
    }

    /**
     * Test of WithPropertyNamer method, of class ObjectBuilderImpl.
     */
    public void testWithPropertyNamer() throws BuilderException {
        System.out.println("WithPropertyNamer");
        BuilderSetup.AutoNameProperties = true;
        
        ObjectBuilder<SimpleTestClass> instance = new ObjectBuilderImpl<SimpleTestClass>(SimpleTestClass.class, new ReflectionUtilImpl())
                .WithPropertyNamer(propNamer);
        
        assertNotNull(instance);
        assertEquals(TEST_VALUE, instance.Build().myTestField);
        
    }

    /**
     * Test of CallFunctions method, of class ObjectBuilderImpl.
     */
    public void testCallFunctions() throws BuilderException {
        System.out.println("CallFunctions");
        BuilderSetup.AutoNameProperties = false;
        
        // Simple Function
        Operable<String> instance = new ObjectBuilderImpl<String>(String.class, new ReflectionUtilImpl()).With(setString);
    
        assertNotNull(instance);
        assertEquals(TEST_VALUE, instance.Build());
        
        instance.Do(setString2, "newValue");
        assertEquals("newValue", instance.Build());
        
        instance.DoForEach(stringConcat, Arrays.asList("another","value"));
        assertEquals("newValue" + "anothervalue", instance.Build());

    }

    /**
     * Test of Construct method, of class ObjectBuilderImpl.
     */
    public void testConstruct() throws BuilderException {
        System.out.println("Construct");
        ObjectBuilderImpl instance = null;
        Object expResult = null;
        Object result = instance.Construct();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Name method, of class ObjectBuilderImpl.
     */
    public void testName() throws BuilderException {
        System.out.println("Name");
        Object obj = null;
        ObjectBuilderImpl instance = null;
        Object expResult = null;
        Object result = instance.Name(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Build method, of class ObjectBuilderImpl.
     */
    public void testBuild() throws BuilderException  {
        System.out.println("Build");
        ObjectBuilderImpl instance = null;
        Object expResult = null;
        Object result = instance.Build();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPropertyNamer method, of class ObjectBuilderImpl.
     */
    public void testGetPropertyNamer() {
        System.out.println("getPropertyNamer");
        ObjectBuilderImpl instance = null;
        PropertyNamer expResult = null;
        PropertyNamer result = instance.getPropertyNamer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
