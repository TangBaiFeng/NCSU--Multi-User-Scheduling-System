package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;
/**
 * A implementation of the Stack data structure onto the Array based list
 * @author Troy Boone
 *
 * @param <E> the element
 */
public class ArrayStack<E> implements Stack<E> {
	/** the capacity of the stack */
	private int capacity;
	/** The base ArrayList being used by stacks */
	private ArrayList<E> array;

	/**
	 * Default constructor. Initialize the array holding the elements
	 * @param capacity the starting capacity
	 */
	public ArrayStack(int capacity) {
		array = new ArrayList<E>();
		setCapacity(capacity);
	}
	/**
	 * Adds the element to the top of the stack
	  * @param element the element being added 
	 * @throws IllegalArgumentException if the array has gone over capacity
	 */
	@Override
	public void push(E element) {
		if(size() == capacity) {
			throw new IllegalArgumentException();
		}
		array.add(0, element);
		
	}
	/**
	 * Removes the first element in the stack
	 * @throws EmptyStackException is the array is empty
	 */
	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}

		return array.remove(0);
	}
	/**
	 * Simple boolean check for if there is an element in the stack
	 * 
	 * @return true if there are no elements in stack
	 */
	@Override
	public boolean isEmpty() {
		return array.size() == 0;
	}
	/**
	 * Simple getter for number of elements in stack
	 * 
	 * @return the size of the stack
	 */
	@Override
	public int size() {
		return array.size();
	}
	/**
	 * Sets the stack capacity
	 * 
	 * @param capacity the new capacity
	 * @throws IllegalArgumentException if the parameter is negative or is less then
	 *                                  the size
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < array.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;		
	}
}
