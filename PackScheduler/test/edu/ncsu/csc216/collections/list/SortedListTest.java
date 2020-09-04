package edu.ncsu.csc216.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests SortedListTest.
 * @author troy1
 *
 */
public class SortedListTest {

	/**
	 * Test SortedList Constructor
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));

		// Test that the list grows by adding at least 11 elements
		list.add("test 1");
		list.add("test 2");
		list.add("test 3");
		list.add("test 4");
		list.add("test 5");
		list.add("test 6");
		list.add("test 7");
		list.add("test 8");
		list.add("test 9");
		list.add("test 10");
		list.add("test 11");
		list.add("test 12");

		assertEquals(12, list.size());
		assertTrue(list.contains("test 10"));
	}
	/**
	 * Test Add()
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();

		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));

		// Test adding to the front
		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));

		// Test adding to the middle
		list.add("apricot");
		assertEquals(3, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("apricot", list.get(1));
		assertEquals("banana", list.get(2));

		// Test adding to the back of the list
		list.add("cherry");
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("apricot", list.get(1));
		assertEquals("banana", list.get(2));
		assertEquals("cherry", list.get(3));

		// Test adding a null element
		try {
			list.add(null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(4, list.size());
			e.printStackTrace();
		}

		// Test adding a duplicate element
		try {
			list.add("apple");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(4, list.size());
			assertEquals("Element already in list.", e.getMessage());
		}

	}
	/**
	 * Test Get()
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();

		// Since get() is used throughout the tests to check the
		// contents of the list, we don't need to test main flow functionality
		// here. Instead this test method should focus on the error
		// and boundary cases.

		// Test getting an element from an empty list
		try {
			list.get(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		// Add some elements to the list
		list.add("cherry");
		list.add("apricot");
		list.add("banana");
		list.add("apple");
		assertEquals(4, list.size());
		// Test getting an element at an index < 0
		try {
			list.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			e.printStackTrace();
		}
		// Test getting an element at size
		try {
			list.get(list.size());
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			e.printStackTrace();
		}
	}
	/**
	 * Test Remove()
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();

		// Test removing from an empty list
		try {
			list.remove(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		// Add some elements to the list - at least 4
		list.add("cherry");
		list.add("apricot");
		list.add("banana");
		list.add("apple");
		assertEquals(4, list.size());

		// Test removing an element at an index < 0
		try {
			list.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			e.printStackTrace();
		}

		// Test removing an element at size
		try {
			list.remove(list.size());
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			e.printStackTrace();
		}

		// Test removing a middle element
		list.remove(2);
		assertEquals(3, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("apricot", list.get(1));
		assertEquals("cherry", list.get(2));

		// TODO Test removing the last element
		list.remove(2);
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("apricot", list.get(1));

		// TODO Test removing the first element
		list.remove(0);
		assertEquals(1, list.size());
		assertEquals("apricot", list.get(0));

		// TODO Test removing the last element
		list.remove(0);
		assertEquals(0, list.size());
	}
	/**
	 * Test IndexOf()
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
		int test = 0;
		// Test indexOf on an empty list
		test = list.indexOf("apple");
		assertEquals(-1, test);
		// Add some elements to the list
		list.add("cherry");
		list.add("apricot");
		list.add("banana");
		list.add("apple");
		assertEquals(4, list.size());

		// Test various calls to indexOf for elements in the list
		// and not in the list
		test = list.indexOf("apple");
		assertEquals(0, test);
		test = list.indexOf("banana");
		assertEquals(2, test);
		test = list.indexOf("apricots");
		assertEquals(-1, test);
		test = list.indexOf("bananay");
		assertEquals(-1, test);

		// Test checking the index of null
		try {
			test = list.indexOf(null);
			fail();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Test Clear()
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements to the list
		list.add("cherry");
		list.add("apricot");
		list.add("banana");
		list.add("apple");
		assertEquals(4, list.size());

		// Clear the list
		list.clear();

		// Test that the list is empty
		assertEquals(0, list.size());
	}
	/**
	 * Test IsEmpty()
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();

		// Test that the list starts empty
		assertTrue(list.isEmpty());

		// Add some elements to the list
		list.add("cherry");
		list.add("apricot");
		list.add("banana");
		list.add("apple");
		assertEquals(4, list.size());

		// Check that the list is no longer empty
		assertFalse(list.isEmpty());
	}
	/**
	 * Test Contains()
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();

		// Test the empty list case
		assertTrue(list.isEmpty());

		// Add some elements to the list
		list.add("cherry");
		list.add("apricot");
		list.add("banana");
		list.add("apple");
		assertEquals(4, list.size());

		// Test some true and false cases
		assertTrue(list.contains("cherry"));
		assertFalse(list.contains("apricots"));
		assertTrue(list.contains("banana"));
		assertFalse(list.contains("appley"));
	}
	/**
	 * Test Equals()
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// Make two lists the same and one list different
		list1.add("cherry");
		list1.add("apricot");
		list1.add("banana");
		list1.add("apple");
		assertEquals(4, list1.size());

		list3 = list1;
		assertEquals(4, list3.size());

		list2.add("argula");
		list2.add("cabbage");
		list2.add("celery");
		list2.add("corn");
		assertEquals(4, list2.size());

		// Test for equality and non-equality
		assertTrue(list1.equals(list3));
		assertFalse(list1.equals(list2));
	}
	/**
	 * Test HashCode()
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// Make two lists the same and one list different
		list1.add("cherry");
		list1.add("apricot");
		list1.add("banana");
		list1.add("apple");
		assertEquals(4, list1.size());

		list3 = list1;
		assertEquals(4, list3.size());

		list2.add("argula");
		list2.add("cabbage");
		list2.add("celery");
		list2.add("corn");
		assertEquals(4, list2.size());

		// Test for the same and different hashCodes
		assertTrue(list1.hashCode() == list3.hashCode());
		assertFalse(list1.hashCode() == list2.hashCode());
	}

}
