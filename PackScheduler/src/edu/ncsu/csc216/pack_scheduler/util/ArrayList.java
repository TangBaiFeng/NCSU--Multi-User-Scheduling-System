package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;
import java.util.Arrays;

/**
 * Custom ArrayList application for the Pack Scheduler System.
 * 
 * @author Troy Boone
 * @param <E> the object the array is holding
 */
public class ArrayList<E> extends AbstractList<E> {
	/** The starting size for the first stage of the array */
	private static final int INTIT_SIZE = 10;
	/** The array holding all the content */
	private E[] list;
	/** The number of non null elements of the array */
	private int size;

	/**
	 * Constructs the ArrayList object. Initializes the List array to a size of 10
	 * and set the size to 0
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[]) new Object[INTIT_SIZE];
		size = 0;
	}

	/**
	 * Override for the add method. Add the element at the specific position of the
	 * array. If the array has reached max size, will call the growArray method
	 * 
	 * @param index   the index of where the element should be added
	 * @param element the element being added
	 * @throws NullPointerException      if the element is null
	 * @throws IndexOutOfBoundsException if the index for the added element is
	 *                                   outside the current bounds
	 * @throws IllegalArgumentException  if the element is already in the array
	 */
	@Override
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		if (size == list.length) {
			growArray();
		}

		System.arraycopy(list, index, list, index + 1, size - index);
		list[index] = element;
		size++;
	}

	/**
	 * Helper method. Used to increase the size of the array by twice its current
	 * size
	 */
	private void growArray() {
		list = Arrays.copyOf(list, list.length * 2);

	}

	/**
	 * Override for the remove method. Will remove the element and the location of
	 * index, and shrink the 'size' of the array to match how many elements are in
	 * the array
	 * 
	 * @param index the index of where the element should be removed
	 */
	@Override
	public E remove(int index) {
		E removedElement = get(index);

		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(list, index + 1, list, index, numMoved);
		list[--size] = null;

		return removedElement;
	}

	/**
	 * Override for the set method. Replaces the element at the specific position of
	 * the array with the a new element.
	 * 
	 * @param index   the index of where the element should be replaced
	 * @param element the element being added
	 * @throws NullPointerException      if the element is null
	 * @throws IndexOutOfBoundsException if the index for the added element is
	 *                                   not within the current size
	 *                                   
	 */
	@Override
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		//Replace with helper method
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		
		E removedElement = get(index);
		list[index] = element;
		return removedElement;

	}

	/**
	 * Simple getter for the element at index location
	 * 
	 * @param index the index being checked
	 * @return the element at index location
	 * @throws IndexOutOfBoundsException if the index for the element is
	 *                                   outside the current size
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}

	/**
	 * Simple getter for size
	 */
	@Override
	public int size() {
		return size;
	}

}
