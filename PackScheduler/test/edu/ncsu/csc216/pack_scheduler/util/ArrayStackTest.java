package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

/**
 * Test the custom ArrayStack class
 * 
 * @author Troy Boone
 *
 */
public class ArrayStackTest {

	/**
	 * Test ArrayStack push method
	 */
	@Test
	public void testPush() {
		ArrayStack<String> stack = new ArrayStack<String>(5);

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
	 * Test ArrayStack pop method
	 */
	@Test
	public void testPop() {
		ArrayStack<String> stack = new ArrayStack<String>(10);

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
	 * Test ArrayStack setCapacity method
	 */
	@Test
	public void testSetCapacity() {
		ArrayStack<String> stack = new ArrayStack<String>(10);

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
