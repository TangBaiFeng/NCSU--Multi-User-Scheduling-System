package edu.ncsu.csc216.pack_scheduler.util;

/**
 * A custom implementation of the linked list structure with recursive methods
 * @author Troy Boone
 *
 * @param <E>
 */
public class LinkedListRecursive<E> {
	/** The size of the list */
	private int size;
	/** The front of the linkedlist */
	private ListNode front;

	/**
	 * Constructor for the LinkedAbstractList object. Initializes the front and size
	 */
	public LinkedListRecursive() {
		this.size = 0;
		this.front = new ListNode(null, null);
	}

	/**
	 * Simple getter for size
	 * 
	 * @return size of list
	 */
	public int size() {
		return size;
	}

	/**
	 * Boolean check to see if the list contains content
	 * 
	 * @return true if list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Searches the list for the given element
	 * @param element the element being searched for
	 * @return false if the element is not found, true otherwise
	 */
	public boolean contains(E element) {
		return (isEmpty()) ? false : front.contains(element);
	}

	/**
	 * Adds an element to the end of the list
	 * @param element the element being added
	 * @return true if added, false otherwise
	 */
	public boolean add(E element) {
		nullCheck(element);
		duplicateCheck(element);

		if (isEmpty()) {
			front = new ListNode(element, null);
			size++;
			return true;
		} else {
			front.add(size, element);
			return true;
		}
	}

	/**
	 * Add the element at the provided index
	 * @param index the location where the element should be added
	 * @param element the element being added
	 * 
	 */
	public void add(int index, E element) {
		nullCheck(element);
		duplicateCheck(element);

		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			front = new ListNode(element, front);
			size++;
		} else {
			front.add(index, element);
		}
	}

	/**
	 * Get the element at the index
	 * @param index the location of the item being retrieved
	 * @return the matching element at the index
	 */
	public E get(int index) {
		indexCheck(index);

		return (index == 0) ? front.data : front.get(index);

	}

	/**
	 * Remove the element if it is found in the list
	 * @param index the index of the element being removed
	 * @return true if the element is removed, false otherwise
	 */
	public E remove(int index) {
		indexCheck(index);

		if (index == 0) {
			E remmoveElement = front.data;
			front = front.next;
			size--;
			return remmoveElement;
		} else {
			return front.remove(index - 1); // This is -1 because I have already checked the first node

		}
	}

	/**
	 * Remove the element if it is found in the list
	 * @param element the element being searched for
	 * @return true if the element is removed, false otherwise
	 */
	public boolean remove(E element) {
		if (element == null) {
			return false;
		}

		if (front.data != null && front.data.equals(element)) {
			front = front.next;
			size--;
			return true;
		} else {
			
			return front.remove(element);
		}
	}

	/**
	 * Set the element at the given index to the new element
	 * @param index the index being set
	 * @param element the element replacing the previous
	 * @return the replaced element
	 */
	public E set(int index, E element) {
		indexCheck(index);
		nullCheck(element);
		duplicateCheck(element);

		if (index == 0) {
			E returnElement = front.data;
			front.data = element;
			return returnElement;
		} else {
			return front.set(index, element);
		}
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
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
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

		public boolean contains(E element) {
			if (data == element) {
				return true;
			} else if (next == null) {
				return false;
			}
			return next.contains(element);
		}

		public void add(int index, E element) {
			if (index == 1) {
				
				ListNode current = this;
				ListNode newNode = new ListNode(element, current.next);
				this.next = newNode;
				size++;
			} else {
				index--;
				next.add(index, element);
			}
		}

		public E get(int index) {
			if (index == 0) {
				return data;
			} else {
				index--;
				return next.get(index);
			}
		}

		public E remove(int index) {
			if (next == null) {
				return null;
			}
			if (index == 0) {
				E tempElement = next.data;
				next = next.next;
				size--;
				return tempElement;
			} else {
				index--;
				return next.remove(index);
			}
		}

		public boolean remove(E element) {
			if (next != null ) {
				if (next.data != null && next.data.equals(element)) {
					next = next.next;
					size--;
					return true;
				} else if (next.data != null) {
					next.remove(element);
				}
			}
			return false;
		}

		public E set(int index, E element) {
			if (index == 0) {
				E replacedElement = data;
				data = element;
				return replacedElement;
			} else {
				index--;
				return next.set(index, element);
			}
		}
	}
}
