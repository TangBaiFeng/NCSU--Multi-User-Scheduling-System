package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A custom implementation of the linked list structure with an iterator based
 * methods
 * 
 * @author Troy Boone
 *
 * @param <E>
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	/** The size of the list */
	private int size;
	/** The front of the linkedlist */
	private ListNode front;
	/** The back of the linkedlist */
	private ListNode back;

	/**
	 * Initialize the list
	 */
	public LinkedList() {
		this.front = new ListNode(null);
		this.back = new ListNode(null, null, front);
		front.next = back;
		size = 0;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		ListIterator<E> runner = new LinkedListIterator(index);
		return runner;
	}

	/**
	 * Set the element at the given index to the new element
	 * 
	 * @param index   the index being set
	 * @param element the element replacing the previous
	 * @return the replaced element
	 * 
	 * @throws IndexOutOfBoundsException if the index is not within the valid range
	 * @throws IllegalArgumentException  if the element is a duplicate of one
	 *                                   already in the list
	 */
	public E set(int index, E element) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		for (int i = 0; i < size; i++) {
			if (get(i).equals(element)) {
				throw new IllegalArgumentException();
			}
		}

		LinkedListIterator it = new LinkedListIterator(index);

		E replacedElement = it.next();
		it.set(element);
		return replacedElement;
	}

	/**
	 * Add the element at the provided index
	 * 
	 * @param index   the location where the element should be added
	 * @param element the element being added
	 * @throws IndexOutOfBoundsException if the index is not within the valid range
	 * @throws IllegalArgumentException  if the element is a duplicate of one
	 *                                   already in the list
	 */
	public void add(int index, E element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();

		}
		for (int i = 0; i < size; i++) {
			if (get(i).equals(element)) {
				throw new IllegalArgumentException();
			}
		}

		LinkedListIterator it = new LinkedListIterator(index);
		it.add(element);
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Inner class for creation and movement of nodes in the Linked List
	 * 
	 * @author Troy Boone
	 *
	 */
	private class ListNode {
		/** The data in the node */
		private E data;
		/** The next node in the list */
		private ListNode next;
		/** The previous node in the list */
		private ListNode prev;

		/**
		 * Construct a node with no connections
		 * 
		 * @param data the data being stored in this node
		 */
		public ListNode(E data) {
			this.data = data;
		}

		/**
		 * Construct a node with a connection to the next one
		 * 
		 * @param data the data being stored in this node
		 * @param next the next node in the list
		 */
		public ListNode(E data, ListNode next, ListNode prev) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}

	}

	/**
	 * Private inner class to help iterate over the linked list structure
	 * 
	 * @author troy1
	 *
	 */
	private class LinkedListIterator implements ListIterator<E> {
		private ListNode previous;
		private ListNode next;
		private ListNode lastRetrieved;
		private int previousIndex;
		private int nextIndex;

		public LinkedListIterator(int index) {
			if (index < 0 || index > size()) {
				throw new IndexOutOfBoundsException();
			}
			previous = front;
			next = front.next;

			for (int i = index; i > 0; i--) {
				previous = next;
				next = next.next;
			}
			previousIndex = 1 - index;
			nextIndex = index;
			lastRetrieved = null;

		}

		@Override
		public boolean hasNext() {
			return next.data != null;
		}

		@Override
		public E next() {
			if (next.data == null) {
				throw new NoSuchElementException();
			}
			lastRetrieved = next;
			E returnedElement = next.data;
			previousIndex++;
			nextIndex++;
			next = next.next;
			return returnedElement;
		}

		@Override
		public boolean hasPrevious() {
			return previous.data != null;
		}

		@Override
		public E previous() {
			if (previous.data == null) {
				throw new NoSuchElementException();
			}
			lastRetrieved = previous;
			E returnedElement = previous.data;
			previousIndex--;
			nextIndex--;
			previous = previous.prev;
			return returnedElement;
		}

		@Override
		public int nextIndex() {
			if (next == null) {
				return size();
			} else {
				return nextIndex;
			}
		}

		@Override
		public int previousIndex() {
			if (previous == null) {
				return -1;
			} else {
				return previousIndex;
			}

		}

		@Override
		public void remove() {

			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			lastRetrieved.prev.next = next;
			next = next.next;

			size--;
		}

		@Override
		public void set(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			lastRetrieved.data = e;

		}

		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			lastRetrieved = null;
			ListNode newNode = new ListNode(e);
			previous.next = newNode;
			next.prev = newNode;
			newNode.prev = previous;
			newNode.next = next;
			size++;
		}

	}
}
