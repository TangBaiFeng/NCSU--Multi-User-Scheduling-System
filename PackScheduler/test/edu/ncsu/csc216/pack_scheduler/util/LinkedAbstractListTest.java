package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test the LinkedAbstractList object
 * @author Troy Boone
 *
 */
public class LinkedAbstractListTest {

	/**
	 * Test the LinkedAbstractList constructor
	 */
	@Test
	public void testLinkedAbstractList() {
		//Valid
		LinkedAbstractList<String> test = new LinkedAbstractList<String>(0);
		assertEquals("class edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList", test.getClass().toString() );
		test = new LinkedAbstractList<String>(5);
		
		//Invalid
		try {
			test = new LinkedAbstractList<String>(-1);
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
		
		
	}
	/**
	 * Test LinkedAbstractList add and setCapacity methods
	 */
	@Test 
	public void testAddIntE() {
		LinkedAbstractList<Object> testAdd = new LinkedAbstractList<Object>(20);
		assertEquals(0, testAdd.size());
		// null error
		try {

			testAdd.add(5, null);
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.NullPointerException", e.toString());
		}
		// index error
		try {

			testAdd.add(-1, "testing");
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IndexOutOfBoundsException", e.toString());
		}
		try {
			testAdd.add(11, "testing");
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IndexOutOfBoundsException", e.toString());
		}

		// Valid Cases
		testAdd.add(0, "Hello");
		testAdd.add(1, "how");
		testAdd.add(2, "are");
		testAdd.add(3, "you");
		testAdd.add(4, "doing");
		testAdd.add(5, "today");
		assertEquals(6, testAdd.size());
		
		//Capacity Error
		try {
			testAdd.setCapacity(3);
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
		

		// duplicate error
		try {
			testAdd.add(6, "today");
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
		//Capacity Error
		LinkedAbstractList<Object> testCapacity = new LinkedAbstractList<Object>(1);
		testCapacity.add("Full");		
		try {
			testCapacity.add("Overfull");
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
		// Growing test
		testAdd.add(6, "I");
		testAdd.add(7, "am");
		testAdd.add(8, "fairing");
		testAdd.add(9, "well");
		testAdd.add(10, "thanks"); // should grow here
		testAdd.add(11, "for");
		testAdd.add(12, "asking");
		assertEquals(13, testAdd.size());
	}
	
	/**
	 * Test ArrayList remove method  and get method
	 */
	@Test
	public void testRemoveInt() {
		LinkedAbstractList<Object> testRemove = new LinkedAbstractList<Object>(20);
		assertEquals(0, testRemove.size());

		testRemove.add(0, "Hello");
		testRemove.add(1, "how");
		testRemove.add(2, "are");
		testRemove.add(3, "you");
		testRemove.add(4, "doing");
		testRemove.add(5, "today");
		assertEquals(6, testRemove.size());

		// Remove Start
		assertEquals("Hello", testRemove.remove(0));
		assertEquals(5, testRemove.size());
		assertEquals("how", testRemove.get(0));

		// Remove End
		assertEquals("today", testRemove.remove(4));
		assertEquals(4, testRemove.size());
		
		try {
			assertNull(testRemove.get(4));
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IndexOutOfBoundsException", e.toString());
		}
		// Remove Middle
		assertEquals("you", testRemove.remove(2));
		assertEquals(3, testRemove.size());
		assertEquals("doing", testRemove.get(2));

	}
	/**
	 * Test ArrayList set method
	 */
	@Test
	public void testSetIntE() {
		LinkedAbstractList<Object> testSet = new LinkedAbstractList<Object>(20);
		assertEquals(0, testSet.size());

		testSet.add(0, "Hello");
		testSet.add(1, "how");
		testSet.add(2, "are");
		testSet.add(3, "you");
		testSet.add(4, "doing");
		testSet.add(5, "today");
		assertEquals(6, testSet.size());

		// Set Start
		assertEquals("Hello", testSet.set(0 , "HELLO"));
		assertEquals(6, testSet.size());
		assertEquals("HELLO", testSet.get(0));

		// Set End
		assertEquals("doing", testSet.set(4 , "DOING"));
		assertEquals(6, testSet.size());
		assertEquals("DOING", testSet.get(4));

		// Set Middle
		assertEquals("are", testSet.set(2 , "ARE"));
		assertEquals(6, testSet.size());
		assertEquals("ARE", testSet.get(2));
		//Duplicate
		try {
			testSet.set(4, "ARE");
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
		//Null element
		try {
			testSet.set(4, null);
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.NullPointerException", e.toString());
		}
	}

}
