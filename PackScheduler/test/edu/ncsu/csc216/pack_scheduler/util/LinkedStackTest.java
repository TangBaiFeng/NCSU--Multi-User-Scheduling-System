package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

/**
 * Test the custom LinkedStack class
 * 
 * @author Troy Boone
 *
 */
public class LinkedStackTest {

	/**
	 * Test LinkedStack push method
	 */
	@Test
	public void testPush() {
		LinkedStack<String> stack = new LinkedStack<String>(5);

		stack.push("Hello");
		assertEquals(1, stack.size());

		stack.push("how");
		assertEquals(2, stack.size());

		stack.push("are");
		assertEquals(3, stack.size());

		stack.push("you");
		assertEquals(4, stack.size());

		// null element
		try {
			stack.push(null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(4, stack.size());
		}
		// capacity error
		try {
			stack.push("today");
			stack.push("friend");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(5, stack.size());
		}
	}

	/**
	 * Test LinkedStack pop method
	 */
	@Test
	public void testPop() {
		LinkedStack<String> stack = new LinkedStack<String>(10);

		stack.push("Hello");
		stack.push("how");
		stack.push("are");
		stack.push("you");

		assertEquals("you", stack.pop());
		assertEquals(3, stack.size());
		stack.pop();
		stack.pop();
		stack.push("today");
		assertEquals(2, stack.size());
		stack.pop();
		stack.pop();
		assertEquals(0, stack.size());

		try {
			stack.pop();
			fail();
		} catch (EmptyStackException e) {
			assertEquals(0, stack.size());
		}
	}

	/**
	 * Test LinkedStack setCapacity method
	 */
	@Test
	public void testSetCapacity() {
		LinkedStack<String> stack = new LinkedStack<String>(10);

		stack.push("Hello");
		stack.push("how");
		stack.push("are");
		stack.push("you");

		stack.setCapacity(stack.size());

		try {
			stack.setCapacity(stack.size() - 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(4, stack.size());
		}

		try {
			stack.setCapacity(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(4, stack.size());
		}
	}
}
