/**
 * @author Jin 
 * Student number: 250964902
 * 
 * This class allows for the user to create a doubly-linked list with nodes of data type from DLNode.
 * Sets the count as 0 when a list is created as count is the number of nodes in the list.
 */



public class DLList<T> implements DLListADT<T> {

	private DLNode<T> front;
	private DLNode<T> rear;
	private int count;

	
	/**
	 * Creates a new List "DLList" of the data type <T>
	 */
	public DLList() {
		count = 0;
	}
	
	/**
	 * Inserts new node at the end of the list. If the list is empty, a new list is created.
	 */

	public void insert(T dataItem, int value) {
		DLNode<T> node = new DLNode<T>(dataItem, value);
		if (!isEmpty()) {
			rear.setNext(node);
			node.setPrevious(rear);
			rear = node;
			rear.setNext(null);
		} else {
			front = node;
			rear = node;
			front.setNext(null);
			front.setPrevious(null);
			rear.setNext(null);
			rear.setPrevious(null);

		}
		count++;
	}

	
	/**
	 * Returns the value of the node given as an integer.
	 */
	public int getDataValue(T dataItem) throws InvalidDataItemException {
		DLNode<T> checkNode = front;
		for (int i = 0; i < count; i++) {
			if (checkNode.getData().equals(dataItem)) {
				return checkNode.getValue();
			}
			checkNode = checkNode.getNext();

		}
		throw new InvalidDataItemException("There is no item in the list equal to the given item.");
	}

	
	/**
	 * Change the value of the node given. Throws invalid data item exception if node is not found in the list.
	 */
	public void changeValue(T dataItem, int newValue) throws InvalidDataItemException {
		DLNode<T> checkNode = front;
		if (isEmpty()) {
			throw new InvalidDataItemException("There is no item in the list equal to the given item.");
		}
		for (int i = 0; i < count; i++) {
			if (checkNode.getData().equals(dataItem)) {
				checkNode.setValue(newValue);
				break;
			}
			checkNode = checkNode.getNext();
			if (i == count - 1)
				throw new InvalidDataItemException("There is no item in the list equal to the given item.");
		}
	}
	
	/**
	 * Finds smallest node in the list by iterating, then removes it from the list
	 * Returns data type <T> of the smallest node found.
	 */

	public T getSmallest() throws EmptyListException, NullPointerException {

		if (isEmpty()) {
			throw new EmptyListException("List is Empty");
		}
		DLNode<T> current = front;
		DLNode<T> smallestNode = current;
		while (current != null) {
			if (current.getValue() <= smallestNode.getValue()) {
				smallestNode = current;
			}
			current = current.getNext();
		}

		// Checking to see if the list only has 1 node, therefore should be null after
		// removing the node.
		if (count == 1) {
			front = null;
			rear = null;
		} else if (smallestNode.equals(front)) { // If the node is the front, the front should be sent to the following
													// node
			front = front.getNext();
			if (front.getPrevious() == null) {
				throw new NullPointerException();
			}
			front.setPrevious(null);
		} else if (smallestNode.equals(rear)) { // Same situation but with the rear node.
			rear = rear.getPrevious();
			if (rear.getNext() == null) {
				throw new NullPointerException();
			}
			rear.setNext(null);
		} else { // If node is in the middle.
			smallestNode.getPrevious().setNext(smallestNode.getNext());
			smallestNode.getNext().setPrevious(smallestNode.getPrevious());
		}
		count--;

		// Returns the data of the smallest node found in the list.
		return smallestNode.getData();

	}

	/**
	 * Returns true if list is empty, otherwise returns false.
	 */
	public boolean isEmpty() {
		if (count == 0)
			return true;
		else
			return false;
	}

	
	/**
	 * Returns an integer that is the size of the list.
	 */
	public int size() {
		return count;
	}

	
	/**
	 * Returns a string that prints the list by iterating through it.
	 */
	public String toString() {
		if (isEmpty())
			return "No items in list.";
		String word = "List: ";
		DLNode<T> node = front;
		word += node.getData() + "," + node.getValue();
		node = node.getNext();
		for (int i = 0; i < count - 1; i++) {
			word += ";" + node.getData() + "," + node.getValue();
			node = node.getNext();
		}
		return word;
	}
}