package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * A implementation of the Queue data structure onto the Array based list
 * @author Troy Boone
 *
 * @param <E> the element
 */
public class ArrayQueue<E> implements Queue<E> {
	/** the capacity of the queue */
	private int capacity;
	/** The base ArrayList being used by queue */
	private ArrayList<E> array;

	/**
	 * Default constructor. Initialize the array holding the elements
	 * @param capacity the starting capacity
	 */
	public ArrayQueue(int capacity) {
		array = new ArrayList<E>();
		setCapacity(capacity);
	}
	
	/**
	 * Adds the element to the bottom of the queue
	 * @param element the element being added 
	 * @throws IllegalArgumentException if the array has gone over capacity
	 */
	@Override
	public void enqueue(E element) {
		if(size() == capacity) {
			throw new IllegalArgumentException();
		}
		array.add(size(), element);

	}

	/**
	 *Removes the first element in the queue
	 * @throws NoSuchElementException is the array is empty
	 */
	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return array.remove(0);
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
		return array.size();
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
		if (capacity < 0 || capacity < array.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

}
