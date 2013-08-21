
package com.merovingian.jbuilder.implementation;

import com.merovingian.jbuilder.ListBuilder;
import com.merovingian.jbuilder.NoEmptyConstructorTestClass;
import com.merovingian.jbuilder.ObjectBuilder;
import com.merovingian.jbuilder.Declaration;
import com.merovingian.jbuilder.SimpleTestClass;
import com.merovingian.jbuilder.TestBase;
import com.merovingian.jbuilder.exceptions.BuilderException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jasonr
 */
public class ListBuilderImplTest extends TestBase {
    
    protected static final int INITIAL_CAPACITY = 10;
    
    protected ListBuilder<String> stringBuilder;   
    protected ListBuilder<SimpleTestClass> simpleBuilder;
    protected ListBuilder<NoEmptyConstructorTestClass> classBuilder;   
    
    public ListBuilderImplTest() {
    }
    
    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        stringBuilder = new ListBuilderImpl<String>(String.class, INITIAL_CAPACITY, reflecUtil);
        simpleBuilder = new ListBuilderImpl<SimpleTestClass>(SimpleTestClass.class, INITIAL_CAPACITY, reflecUtil);
        classBuilder = new ListBuilderImpl<NoEmptyConstructorTestClass>(NoEmptyConstructorTestClass.class, INITIAL_CAPACITY, reflecUtil);
    }

    /**
     * Test of getCapacity method, of class ListBuilderImpl.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        ListBuilder<String> instance = stringBuilder;
        int expResult = INITIAL_CAPACITY;
        int result = instance.size();
        assertEquals(expResult, result);
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
        assertEquals(stringBuilder, stringBuilder.All());
        
    }


    /**
     * Test of TheFirst method, of class ListBuilderImpl.
     */
    @Test
    public void testTheFirst() {
        System.out.println("TheFirst");
        int amount = INITIAL_CAPACITY / 2;
        
        RangeDeclaration<String> theFirst = (RangeDeclaration)stringBuilder.TheFirst(amount);
        assertNotNull(theFirst);
        assertEquals(0,theFirst.getStart());
        assertEquals(amount-1,theFirst.getEnd());
        assertEquals(amount, theFirst.getNumberOfAffectedItems());
        
    }
    
    /**
     * Test of TheFirst method, of class ListBuilderImpl.
     */
    @Test
    public void testTheFirstWholeList() {
        System.out.println("TheFirst");
        int amount = INITIAL_CAPACITY;
        
        RangeDeclaration<String> theFirst = (RangeDeclaration)stringBuilder.TheFirst(amount);
        assertNotNull(theFirst);
        assertEquals(0,theFirst.getStart());
        assertEquals(amount-1,theFirst.getEnd());
        assertEquals(amount, theFirst.getNumberOfAffectedItems());
        
    }
    
    /**
     * Test of TheFirst method, of class ListBuilderImpl.
     */
    @Test
    public void testTheFirstAfterAll() {
        System.out.println("TheFirstAfterAll");
        int amount = INITIAL_CAPACITY / 2;
        
        RangeDeclaration<String> theFirst = (RangeDeclaration)stringBuilder
                .All()
                .With(setString)
                .TheFirst(amount);
        assertNotNull(theFirst);
        assertEquals(0,theFirst.getStart());
        assertEquals(amount-1,theFirst.getEnd());
        assertEquals(amount, theFirst.getNumberOfAffectedItems());
        
    }
    
    /**
     * Test of TheFirst method, of class ListBuilderImpl.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTheFirstTooSmall() {
        System.out.println("TheFirstTooSmall");
        int amount = 0;
        
        Declaration<String> theFirst = stringBuilder.TheFirst(amount);
        fail("Should have thrown an exception");
        
    }
    
    /**
     * Test of TheFirst method, of class ListBuilderImpl.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTheFirstTooBig() {
        System.out.println("TheFirstTooBig");
        int amount = INITIAL_CAPACITY + 1;
        
        Declaration<String> theFirst = stringBuilder.TheFirst(amount);
        fail("Should have thrown an exception");
        
    }

    /**
     * Test of TheLast method, of class ListBuilderImpl.
     */
    @Test
    public void testTheLast() {
        System.out.println("TheLast");
        int amount = INITIAL_CAPACITY / 2;
        
        RangeDeclaration<String> range = (RangeDeclaration)stringBuilder.TheLast(amount);
        assertNotNull(range);
        assertEquals(INITIAL_CAPACITY - amount,range.getStart());
        assertEquals(INITIAL_CAPACITY - 1,range.getEnd());
        assertEquals(amount, range.getNumberOfAffectedItems());
    }
    
    /**
     * Test of TheLast method, of class ListBuilderImpl.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTheLastTooSmall() {
        System.out.println("TheLastTooSmall");
        int amount = 0;
        
        Declaration<String> range = stringBuilder.TheLast(amount);
        fail("Should have thrown an exception");
        
    }
    
    /**
     * Test of TheLast method, of class ListBuilderImpl.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTheLastTooBig() {
        System.out.println("TheLastTooBig");
        int amount = INITIAL_CAPACITY + 1;
        
        Declaration<String> range = stringBuilder.TheLast(amount);
        fail("Should have thrown an exception");
        
    }

    /**
     * Test of Section method, of class ListBuilderImpl.
     */
    @Test
    public void testSection() {
        System.out.println("Section");
        int start = (INITIAL_CAPACITY / 2) - 3;
        int end = (INITIAL_CAPACITY / 2) + 2;
        
        RangeDeclaration<String> range = (RangeDeclaration)stringBuilder.Section(start, end);
        assertNotNull(range);
        assertEquals(start,range.getStart());
        assertEquals(end,range.getEnd());
        assertEquals(end - start + 1, range.getNumberOfAffectedItems());
    }
    
    @Test
    public void testSectionStartEnd0() {
        System.out.println("SectionStartEnd0");
        int start = 0;
        int end = 0;
        
        RangeDeclaration<String> range = (RangeDeclaration)stringBuilder.Section(start, end);
        assertNotNull(range);
        assertEquals(start,range.getStart());
        assertEquals(end,range.getEnd());
        assertEquals(end - start + 1, range.getNumberOfAffectedItems());
    }
    
    /**
     * Test of Section method, of class ListBuilderImpl.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSectionTooSmall() {
        System.out.println("SectionTooSmall");
        int start = -1;
        int end = (INITIAL_CAPACITY / 2) + 2;
        
        Declaration<String> range = stringBuilder.Section(start, end);
        fail("Should have thrown an exception");
    }
    
    /**
     * Test of Section method, of class ListBuilderImpl.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSectionTooBig() {
        System.out.println("SectionTooBig");
        int start = (INITIAL_CAPACITY / 2) - 3;
        int end = INITIAL_CAPACITY + 1;
        
        Declaration<String> range = stringBuilder.Section(start, end);
        fail("Should have thrown an exception");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSectionStartAfterEnd() {
        System.out.println("SectionStartAfterEnd");
        int start = (INITIAL_CAPACITY / 2) + 1;
        int end = (INITIAL_CAPACITY / 2) - 1;
        
        Declaration<String> range = stringBuilder.Section(start, end);
        fail("Should have thrown an exception");
    }

    /**
     * Test of TheNext method, of class ListBuilderImpl.
     */
    @Test
    public void testTheNext() {
        System.out.println("TheNext");
        int first = (INITIAL_CAPACITY / 2);
        int next = 3;
        
        RangeDeclaration<String> range = (RangeDeclaration)stringBuilder.TheFirst(first).TheNext(next);
        assertNotNull(range);
        assertEquals(first,range.getStart());
        assertEquals(first + next - 1,range.getEnd());
        assertEquals(next, range.getNumberOfAffectedItems());
    }
    
    /**
     * Test of TheNext method, of class ListBuilderImpl.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTheNextTooBig() {
        System.out.println("TheNextTooBig");
        int first = (INITIAL_CAPACITY / 2);
        int next = first + 1;
        
        Declaration<String> range = stringBuilder.TheFirst(first).TheNext(next);
        fail("Should have thrown an exception");
    }
    
    /**
     * Test of TheNext method, of class ListBuilderImpl.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTheNextNoMore() {
        System.out.println("TheNextNoMore");
        int first = INITIAL_CAPACITY ;
        int next = 1;
        
        Declaration<String> range = stringBuilder.TheFirst(first).TheNext(next);
        fail("Should have thrown an exception");
    }
    
    @Test(expected = NullPointerException.class)
    public void testTheNextFirst() {
        System.out.println("TheNext");
        int next = 1;
        
        Declaration<String> range = stringBuilder.TheNext(next);
        fail("Should have thrown an exception");
    }

    /**
     * Test of ThePrevious method, of class ListBuilderImpl.
     */
    @Test
    public void testThePrevious() {
        System.out.println("ThePrevious");
        int amount = (INITIAL_CAPACITY / 2);
        int prev = 3;
        
        RangeDeclaration<String> range = (RangeDeclaration)stringBuilder.TheLast(amount).ThePrevious(prev);
        assertNotNull(range);
        assertEquals(INITIAL_CAPACITY - amount - prev,range.getStart());
        assertEquals(INITIAL_CAPACITY - amount - 1,range.getEnd());
        assertEquals(prev, range.getNumberOfAffectedItems());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testThePreviousTooBig() {
        System.out.println("ThePreviousTooBig");
        int amount = (INITIAL_CAPACITY / 2);
        int prev = amount + 1;
        
        Declaration<String> range = stringBuilder.TheLast(amount).ThePrevious(prev);
        fail("Should have thrown an exception");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testThePreviousTooSmall() {
        System.out.println("ThePreviousTooSmall");
        int amount = (INITIAL_CAPACITY / 2);
        int prev = 0;
        
        Declaration<String> range = stringBuilder.TheLast(amount).ThePrevious(prev);
        fail("Should have thrown an exception");
    }
    
    @Test(expected = NullPointerException.class)
    public void testThePreviousFirst() {
        System.out.println("ThePreviousFirst");
        int amount = (INITIAL_CAPACITY / 2);
        int prev = 3;
        
        Declaration<String> range = stringBuilder.ThePrevious(prev);
        fail("Should have thrown an exception");
    }
    
    /**
     * Test of Build method, of class ListBuilderImpl.
     */
    @Test
    public void testBuild() throws BuilderException {
        System.out.println("Build");
        
        List<String> result = stringBuilder.Build();
        assertNotNull(result);
        assertEquals(INITIAL_CAPACITY, result.size());
        
        for (String s : result) {
            assertEquals("", s);
        }
        
    }
    
    @Test
    public void testBuildWithArgs() throws BuilderException {
        System.out.println("BuildWithArgs");
        
        String[] args = new String[]{TEST_VALUE};
        List<String> result = stringBuilder
                .WithConstructorArgs(args)
                .Build();
        assertNotNull(result);
        assertEquals(INITIAL_CAPACITY, result.size());
        
        for (String s : result) {
            assertEquals(TEST_VALUE, s);
        }
        
    }
    
    @Test
    public void testBuildWithAutoName() throws BuilderException {
        System.out.println("BuildWithAutoName");
        
        String[] args = new String[]{TEST_VALUE};
        List<SimpleTestClass> result = simpleBuilder
                .WithAutoNamer(autoNamer)
                .Build();
        assertNotNull(result);
        assertEquals(INITIAL_CAPACITY, result.size());
        
        for (SimpleTestClass s : result) {
            assertEquals(TEST_VALUE, s.myTestField);
        }
        
    }
    
    @Test
    public void testBuildWithAll() throws BuilderException {
        System.out.println("BuildWithAll");
        
        List<String> result = stringBuilder
                .All()
                .Build();
        
        assertNotNull(result);
        assertEquals(INITIAL_CAPACITY, result.size());
        for (String s : result) {
            assertEquals("", s);
        }
        

        result = stringBuilder
                .All()
                .With(setString)
                .Build();
        assertEquals(INITIAL_CAPACITY, result.size());
        for (String s : result) {
            assertEquals(TEST_VALUE, s);
        }
        
    }
    
    @Test
    public void testBuildWithRange() throws BuilderException {
        System.out.println("BuildWithRange");
        
        int amount = INITIAL_CAPACITY / 2;
 
        List<String> result = stringBuilder
                .TheFirst(amount)
                    .With(setString)
                .Build();
        
        assertNotNull(result);
        assertEquals(INITIAL_CAPACITY, result.size());
        
        for (int i=0; i < amount; i++) {
            assertEquals(TEST_VALUE, result.get(i));
        }
        
        for (int i=amount+1; i < INITIAL_CAPACITY; i++) {
            assertEquals("", result.get(i));
        }
        
    }
    
    @Test
    public void testBuildWithAllPlusRange() throws BuilderException {
        System.out.println("BuildWithAllPlusRange");
        
        int start = INITIAL_CAPACITY / 2 - 1;
        int end = INITIAL_CAPACITY / 2 + 1;
        
        String otherString = "OtherString";
        
        List<String> result = stringBuilder
                .All()
                    .With(setString)
                .Section(start, end)
                    .Do(setString2, otherString)
                .Build();
        
        assertNotNull(result);
        assertEquals(INITIAL_CAPACITY, result.size());
        
        for (int i=0; i < start; i++) {
            assertEquals(TEST_VALUE, result.get(i));
        }
        
        for (int i=start+1; i <= end; i++) {
            assertEquals(otherString, result.get(i));
        }
        
        for (int i=end+1; i < INITIAL_CAPACITY; i++) {
            assertEquals(TEST_VALUE, result.get(i));
        }
        
    }
    
    @Test
    public void testBuildWithAllPlusFirstAndNext() throws BuilderException {
        System.out.println("BuildWithAllPlusFirstAndNext");
        
        int amount = INITIAL_CAPACITY - 2;
        
        String otherString = "OtherString";
        String yas = "yetAnotherString";
        
        ListBuilder<String> sb = new ListBuilderImpl<String>(String.class, 3, reflecUtil);
        
        List<String> result = stringBuilder
                .All()
                    .Do(setString2, TEST_VALUE)
                .TheFirst(1)
                    .Do(setString2, otherString)
                    .And(setString2, otherString)
                .TheNext(1)
                    .Do(setString2, yas)
                    .And(setString2, yas)
                .TheNext(1)
                    .Do(setString2, yas)
                    .And(setString2, yas)
                .Build();
        
        assertNotNull(result);
        assertEquals(INITIAL_CAPACITY, result.size());
       
        
    }
}