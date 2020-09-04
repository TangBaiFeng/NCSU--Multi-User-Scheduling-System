package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * A implementation of the Stack data structure onto the Linked node based list
 * 
 * @author Troy Boone
 *
 * @param <E> the element
 */
public class LinkedStack<E> implements Stack<E> {
	/** The base LinkedAbstractList being used by stacks */
	private LinkedAbstractList<E> list;
	/** the capacity of the stack */
	private int capacity;

	/**
	 * Default constructor. Initialize the list holding the elements
	 * @param capacity the starting capacity
	 */
	public LinkedStack(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
		setCapacity(capacity);
	}
	/**
	 * Adds the element to the top of the stack
	 * @param element the element being added 
	 * @throws IllegalArgumentException if the array has gone over capacity
	 */
	@Override
	public void push(E element) {
		if (size() == capacity) {
			throw new IllegalArgumentException();
		}
		list.add(0, element);

	}

	/**
	 * Removes the first element in the Stack
	 * 
	 * @throws EmptyStackException is the list is empty
	 */
	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return list.remove(0);
	}

	/**
	 * Simple boolean check for if there is an element in the stack
	 * 
	 * @return true if there are no elements in stack (size is 0)
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;

	}

	/**
	 * Simple getter for number of elements in stack
	 * 
	 * @return the size of the stack
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the stack capacity
	 * 
	 * @param capacity the new capacity
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
		this.capacity = capacity;

	}
}
