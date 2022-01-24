
/**
 * @author Jin 
 * Student number: 250964902
 * 
 * This class defines the interface/methods for stack data structures.
 * Sets the initial 'top' variable as -1 as the stack data structure does not have any 
 */

import java.util.EmptyStackException;
//import java.util.Stack;

public class ArrayStack<T> implements ArrayStackADT<T> {

	/**
	 * Private instance variables: 'stack' variable creates a new object of a stack
	 * data type 'top' variable stores the data index of the most recent/highest
	 * data item in the corresponding stack
	 */

	private T[] stack;
	private int top;

	/**
	 * Main method for testing different methods (Also tested by TestStack.java
	 * given)
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Creates new ArrayStack as an object with a length of 20 and initialize top as
	 * -1 as there are no data items in this stack.
	 */
	public ArrayStack() {
		top = -1;
		stack = (T[]) (new Object[20]);

	}

	/**
	 * Creates new ArrayStack as an object with the length given in the parameters
	 * as 'initialCapacity' and initializes top as -1 as there are no data items in
	 * this stack.
	 * 
	 * @param initialCapacity
	 */
	public ArrayStack(int initialCapacity) {
		top = -1;
		stack = (T[]) (new Object[initialCapacity]);

	}

	/**
	 * This method adds a new data type at the end/top of the respective stack
	 * structure. If there is not enough space in the stack to add a new element,
	 * the class will increase the stack length in two following ways: 1. If the
	 * stack length is less than 100, then it will increase the length by a factor
	 * of 2 2. If the stack length is more than 100, then it will increase the
	 * length by 50 elements. Does not return any data type
	 */
	public void push(T dataItem) {
		if (top + 1 == stack.length) {
			if (stack.length < 100) {
				T[] larger = (T[]) (new Object[stack.length * 2]);
				for (int index = 0; index < stack.length; index++)
					larger[index] = stack[index];
				stack = larger;
			} else {
				T[] larger = (T[]) (new Object[stack.length + 50]);
				for (int index = 0; index < stack.length; index++)
					larger[index] = stack[index];
				stack = larger;
			}
		}
		top++;
		stack[top] = dataItem;

	}

	/**
	 * This method returns the last/top data item at the end/top of the respective
	 * stack structure. Then it deletes that same data item from the stack. If the
	 * length of the data items remaining after the deletion of the top item is less
	 * than a third of the stack length, then the stack length is reduced by a
	 * factor of 2. Returns a object T data type
	 */
	public T pop() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}

		T result = stack[top];
		stack[top] = null;
		top--;
		/**
		 * Checks if length of stack elements are less than third of stack length
		 */
		if ((top + 1 < (stack.length / 3)) && stack.length > 20) {
			// System.out.println (stack.length/3);
			T[] smaller = (T[]) (new Object[stack.length / 2]);
			for (int index = 0; index < (stack.length / 2); index++)
				smaller[index] = stack[index];
			// System.out.println (stack.length/3);
			stack = smaller;
		}

		return result;
	}

	/**
	 * This method returns the last/top data item at the end/top of the respective
	 * stack structure. It does not delete the data item after viewing it. Returns a
	 * object T data type
	 */
	public T peek() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		T result = stack[top];

		return result;

	}

	/**
	 * This method checks to see if the stack structure has any data items. If there
	 * are no data items in the stack given, then a boolean type True will be
	 * returned. If there are any data items in the stack given, then a boolean type
	 * False will be returned.
	 */
	public boolean isEmpty() {
		if (top == -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method finds the number of data items in the stack and returns it as an
	 * Integer.
	 */
	public int size() {
		return top + 1;
	}

	/**
	 * This method finds the length of the stack and returns it as an Integer.
	 * 
	 * @return
	 */
	public int length() {
		return stack.length;
	}

	/**
	 * This method will return a String that will contain all the data item
	 * information from its respective stack.
	 */
	public String toString() {
		String result = "Stack: ";
		result += stack[0];
		for (int i = 1; i <= top; i++) {
			result += ", " + stack[i];
		}

		return result;
	}
}
