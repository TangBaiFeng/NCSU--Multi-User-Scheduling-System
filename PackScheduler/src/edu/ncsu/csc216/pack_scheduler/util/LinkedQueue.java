package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * A implementation of the Queue data structure onto the Linked node based list
 * 
 * @author Troy Boone
 *
 * @param <E> the element
 */
public class LinkedQueue<E> implements Queue<E> {
	/** The base LinkedAbstractList being used by queue */
	private LinkedAbstractList<E> list;
	/** the capacity of the stack */
	private int capacity;

	/**
	 * Default constructor. Initialize the list holding the elements
	 * @param capacity the starting capacity
	 */
	public LinkedQueue(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
		setCapacity(capacity);
	}
	/**
	 * Adds the element to the end of the queue
	 * @param element the element being added 
	 * @throws IllegalArgumentException if the array has gone over capacity
	 */
	@Override
	public void enqueue(E element) {
		if (size() == capacity) {
			throw new IllegalArgumentException();
		}
		list.add(size(), element);

	}

	/**
	 * Removes the first element in the queue
	 * 
	 * @throws NoSuchElementException is the list is empty
	 */
	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return list.remove(0);
	}

	/**
	 * Simple boolean check for if there is an element in the queue
	 * 
	 * @return true if there are no elements in queue
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Simple getter for number of elements in queue
	 * 
	 * @return the size of the queue
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the queue capacity
	 * 
	 * @param capacity the new capacity
	 * @throws IllegalArgumentException if the parameter is negative or is less then
	 *                                  the size
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
		this.capacity = capacity;

	}
	/**
	 * Searches through the LinkedAbstractList for an occurrence of element
	 * @param element the element which is being looked for
	 * @return true if the list contains the element, false otherwise
	 */
	public boolean contains(E element) {
		return list.contains(element);
	}
}
