package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Custom application of the linked list storage system. This object extends
 * AbstractList and adds checking for duplicates
 * 
 * @author Troy Boone
 *
 * @param <E> All elements are allowed in this list
 */
public class LinkedAbstractList<E> extends AbstractList<E> {
	/** The size of the list */
	private int size;
	/** The capacity of the list */
	private int capacity;
	/** The front of the linkedlist */
	private ListNode front;
	/** The back of the linkedlist */
	private ListNode back;

	/**
	 * Constructor for the LinkedAbstractList object. Initializes the front, size,
	 * and sets the capacity to the integer provided.
	 * 
	 * @param capacity the starting capacity for the LinkedAbstractList
	 */
	public LinkedAbstractList(int capacity) {
		this.size = 0;
		this.front = null;
		this.back = front;
		setCapacity(capacity);
	}

	/**
	 * Simple setter for capacity
	 * 
	 * @param capacity the capacity for the LinkedAbstractList
	 * @throws IllegalArgumentException is capacity is less then 0 or less then the
	 *                                  current size
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	/**
	 * Getter for element at the index indicated. If the index is 0, then it will
	 * return the front, otherwise it will loop through all nodes to the numbered
	 * node
	 * 
	 * @param index the number of the node where the element is being grabbed
	 * @return the element contained in the index numbered node
	 * @throws NullPointerException is the front node is null
	 */
	@Override
	public E get(int index) {
		indexCheck(index);

		if (front == null) {
			throw new NullPointerException();
		}
		if (index == size) {
			return back.data;
		}

		ListNode traveler = front;
		if (index == 0) {
			return traveler.data;
		}

		for (int i = 0; i < index; i++) {
			traveler = traveler.next;
		}
		return traveler.data;
	}

	/**
	 * Set the node at the index to contain the element. Will not accept null or
	 * duplicate elements. If the index is 0, then it will select the front,
	 * otherwise it will loop through all nodes to the numbered node
	 * 
	 * @param index   the number of the node where the element is being replaced
	 * @param element the element being added
	 * @return the element that was replaced
	 */
	public E set(int index, E element) {
		indexCheck(index);
		nullCheck(element);
		duplicateCheck(element);

		ListNode temp = front;
		if (index == 0) {
			E removedData = temp.data;
			front = new ListNode(element, front.next);
			return removedData;
		}

		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}
		E removedData = temp.data;
		temp.data = element;
		return removedData;
	}

	/**
	 * Add the element at the indicated index. Will not accept null or duplicate
	 * elements.If the index is 0, then it will select the front, otherwise it will
	 * loop through all nodes to the numbered node
	 * 
	 * @param index   the number of the node where the element is being added
	 * @param element the element being added
	 * @throws IllegalArgumentException  if the size now equals the capacity
	 * @throws IndexOutOfBoundsException if the index is not within bounds of the
	 *                                   list
	 */
	public void add(int index, E element) {
		nullCheck(element);
		duplicateCheck(element);

		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		if (size == capacity) {
			throw new IllegalArgumentException();
		}
		if (front == null) {
			front = new ListNode(element);
			back = front;
		} else if (index == size) {
			back.next = new ListNode(element);
			back = back.next;
		} else {
			if (index == 0) {
				front = new ListNode(element, front);
			} else if (front != null && index > 0) {
				ListNode temp = front;
				while (temp != null && index > 1) {
					temp = temp.next;
					index--;
				}
				if (temp != null) {
					temp.next = new ListNode(element, temp.next);
				}
			}
		}

		size++;

	}

	/**
	 * Remove the element at index. If the index is 0, then it will select the
	 * front, otherwise it will loop through all nodes to the numbered node
	 * 
	 * @param index the number of the node which is being removed from the list
	 * @return the element in the node being removed
	 */
	public E remove(int index) {
		indexCheck(index);

		ListNode current = front;
		ListNode previous = null;

		while (current != null && index > 0) {
			previous = current;
			current = current.next;
			index--;
		}
		if (current != null) {
			if (current == front) {
				front = front.next;
			} else if (current == back) {
				back = previous;
			} else {
				previous.next = current.next;
			}
			size--;
			return current.data;
		}
		return null;

	}

	/**
	 * Simple getter for size
	 * 
	 * @return size of list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Helper method. Confirms the index is within bounds
	 * 
	 * @param index the index being tested
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	private void indexCheck(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * Helper method. Confirms the element is not null
	 * 
	 * @param element the element being tested
	 * @throws NullPointerException if the element is null
	 */
	private void nullCheck(E element) {
		if (element == null) {
			throw new NullPointerException();
		}
	}

	/**
	 * Helper method. Confirms the element is not a duplicate
	 * 
	 * @param element the element being tested
	 * @throws IllegalArgumentException if the element is already in the list
	 */
	private void duplicateCheck(E element) {
		for (ListNode p = front; p != null; p = p.next) {
			if (p.data.equals(element))
				throw new IllegalArgumentException();
		}
	}

//	/**
//	 * Updates the back reference
//	 */
//	private void backSet() {
//		back = front;
//		while (back.next != null) {
//			back = back.next;
//		}
//	}

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
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

	}
}
