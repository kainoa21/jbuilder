/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder;

import com.google.common.base.Function;
import com.merovingian.jbuilder.exceptions.TypeCreationException;
import com.merovingian.jbuilder.functions.Function2;
import com.merovingian.jbuilder.util.ReflectionUtil;
import com.merovingian.jbuilder.util.ReflectionUtilImpl;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author jasonr
 */
public abstract class TestBase {

    protected static final String TEST_FIELD = "myTestField";
    protected static final String TEST_VALUE = "myTestFieldValue";
    
    protected static final ReflectionUtil reflecUtil = new ReflectionUtilImpl();
    
    protected static final AutoNamer autoNamer = new AutoNamer() {
        @Override
        public <T> void setValuesOf(T obj) throws InvocationTargetException, IllegalAccessException, TypeCreationException {
            try {
                Field f = obj.getClass().getField(TEST_FIELD);
                if (!BuilderSetup.ShouldIgnoreProperty(f)) {
                    f.set(obj, TEST_VALUE);
                }
            } catch (Exception e) {
            }
        }

        @Override
        public <T> void setValuesOfAllIn(List<T> list) throws InvocationTargetException, IllegalAccessException, TypeCreationException {
            for (T obj : list) {
                setValuesOf(obj);
            }
        }
    };
    
    protected static final Function<String, String> setString = new Function<String, String>() {
        @Override
        public String apply(String obj) {
            obj = TEST_VALUE;
            return obj;
        }
    };
    
    protected static final Function2<String, String> setString2 = new Function2<String, String>() {
        @Override
        public String apply(String f, String obj) {
            obj = f;
            return obj;
        }
    };
    
    protected static final Function2<String, String> stringConcat = new Function2<String, String>() {
        @Override
        public String apply(String f, String obj) {
            obj += f;
            return obj;
        }
    };
    
    protected static final Function2<String, NoEmptyConstructorTestClass> setProperty = new Function2<String, NoEmptyConstructorTestClass>() {
        @Override
        public NoEmptyConstructorTestClass apply(String f, NoEmptyConstructorTestClass obj) {
            obj.setMyPrivateString(f);
            return obj;
        }
    };

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        BuilderSetup.ResetToDefaults();
    }

    @After
    public void tearDown() throws Exception {
    }
}
