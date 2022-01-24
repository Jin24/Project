/**
 * @author Jin 
 * Student number: 250964902
 * 
 * This class creates nodes that are of data type <T> to be added to the doubly linked list in DLList.java
 * Initializes nodes as null and value of 0 when they are first created with parameters.
 */

public class DLNode<T>  {

	private T dataItem;
	private DLNode<T> next;
	private DLNode<T> previous;
	private int value;
	
	
	/**
	 * Creates new node with dataItem, next, previous as null and value as 0
	 */
	public DLNode () {
		dataItem = null;
		next = null;
		previous = null;
		value = 0;
	}
	
	/**
	 * Creates new node with the arguments (data, num) given.
	 * @param data
	 * @param num
	 */
	public DLNode (T data, int num) {
		dataItem = data;
		next = null;
		previous = null;
		value = num;
	}
	
	
	/**
	 * Returns the value of the node as an integer
	 * @return
	 */
	public int getValue () {
		return value;
	}
	
	/**
	 * Returns the data of the node given as a data type <T>
	 * @return
	 */
	public T getData(){
		return dataItem;
	}
	
	
	/**
	 * Returns the next node
	 * @return
	 */
	public DLNode<T> getNext() {
		return next;
	}
	
	/**
	 * Returns the previous node
	 * @return
	 */
	public DLNode<T> getPrevious() {
		return previous;
	}
	
	/**
	 * Sets the dataItem of the node to the argument given
	 * @param data
	 */
	public void setData (T data) {
		dataItem = data;
	}
	
	
	/**
	 * Sets the next node 
	 * @param data
	 */
	public void setNext (DLNode<T> data) {
		next = data;
	}
	
	/**
	 * Sets the previous node
	 * @param data
	 */
	public void setPrevious (DLNode<T> data) {
		previous = data;
	}
	
	
	/**
	 * Sets the value to the argument given.
	 * @param num
	 */
	public void setValue (int num) {
		value = num;
	}
	
}
