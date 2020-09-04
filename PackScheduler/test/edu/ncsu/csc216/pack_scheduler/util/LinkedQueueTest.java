package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Test the custom LinkedQueue class
 * 
 * @author Troy Boone
 *
 */
public class LinkedQueueTest {

	/**
	 * Test LinkedQueue enqueue method
	 */
	@Test
	public void testEnqueue() {
		LinkedQueue<String> queue = new LinkedQueue<String>(5);

		queue.enqueue("Hello");
		assertEquals(1, queue.size());

		queue.enqueue("how");
		assertEquals(2, queue.size());

		queue.enqueue("are");
		assertEquals(3, queue.size());

		queue.enqueue("you");
		assertEquals(4, queue.size());
		assertFalse(queue.isEmpty());
		// null element
		try {
			queue.enqueue(null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(4, queue.size());
		}
		// capacity error
		try {
			queue.enqueue("today");
			queue.enqueue("friend");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(5, queue.size());
		}

	}

	/**
	 * Test LinkedQueue dequeue method
	 */
	@Test
	public void testDequeue() {
		LinkedQueue<String> queue = new LinkedQueue<String>(10);

		queue.enqueue("Hello");
		queue.enqueue("how");
		queue.enqueue("are");
		queue.enqueue("you");

		assertEquals("Hello", queue.dequeue());
		assertEquals(3, queue.size());
		queue.dequeue();
		queue.dequeue();
		queue.enqueue("today");
		assertEquals(2, queue.size());
		queue.dequeue();
		queue.dequeue();
		assertEquals(0, queue.size());

		try {
			queue.dequeue();
			fail();
		} catch (NoSuchElementException e) {
			assertEquals(0, queue.size());
		}
	}

	/**
	 * Test LinkedQueue setCapacity method
	 */
	@Test
	public void testSetCapacity() {
		LinkedQueue<String> queue = new LinkedQueue<String>(10);

		queue.enqueue("Hello");
		queue.enqueue("how");
		queue.enqueue("are");
		queue.enqueue("you");

		queue.setCapacity(queue.size());

		try {
			queue.setCapacity(queue.size() - 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(4, queue.size());
		}

		try {
			queue.setCapacity(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(4, queue.size());
		}
	}

}
