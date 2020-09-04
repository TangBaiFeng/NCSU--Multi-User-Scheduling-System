package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Stack Interface to be used to help add methods for different data structures to
 * implement a waitlist capability. Works with elements from a top to bottom approach
 * 
 * @author Troy Boone
 * @param <E> generic type
 *
 */
public interface Stack<E> {
	/**
	 * Adds the element to the top of the stack
	 * 
	 * @param element element to be added
	 * @throws IllegalArgumentException if there is no room remaining
	 */
	void push(E element);

	/**
	 * Removes element at the top of the stack
	 * 
	 * @return the element removed
	 */
	E pop();

	/**
	 * Simple boolean check for if there is an element in the stack
	 * 
	 * @return true if there are no elements in stack
	 */
	boolean isEmpty();

	/**
	 * Simple getter for number of elements in stack
	 * 
	 * @return the size of the stack
	 */
	int size();

	/**
	 * Sets the structures capacity
	 * 
	 * @param capacity the new capacity
	 * @throws IllegalArgumentException if the parameter is negative or is less then
	 *                                  the size
	 */
	void setCapacity(int capacity);
}
