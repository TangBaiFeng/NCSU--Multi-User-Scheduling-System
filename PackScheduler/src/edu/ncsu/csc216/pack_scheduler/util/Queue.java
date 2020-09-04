package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Queue Interface to be used to help add methods for different data structures to
 * Implement a waitlist capability. Works with elements from a bottom to top approach
 * 
 * @author Troy Boone
 * @param <E> generic type
 */
public interface Queue<E> {
	/**
	 * Adds the element to the bottom of the queue
	 * 
	 * @param element element to be added
	 * @throws IllegalArgumentException if there is no room remaining
	 */
	void enqueue(E element);
	/**
	 * Removes element at the bottom of the queue
	 * 
	 * @return the element removed
	 */
	E dequeue();
	/**
	 * Simple boolean check for if there is an element in the queue
	 * 
	 * @return true if there are no elements in queue
	 */
	boolean isEmpty();

	/**
	 * Simple getter for number of elements in queue
	 * 
	 * @return the size of the queue
	 */
	int size();

	/**
	 * Sets the queue's capacity
	 * 
	 * @param capacity the new capacity
	 * @throws IllegalArgumentException if the parameter is negative or is less then
	 *                                  the size
	 */
	void setCapacity(int capacity);
}
