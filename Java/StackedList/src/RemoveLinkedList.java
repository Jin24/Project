/**
 * Build a linked list of integers from 1 to 10
 * 
 * @author CS1027
 */
public class RemoveLinkedList {

    private static LinearNode<Integer> head;

	public static void main(String[] args) {

		// create a linked list that holds 1, 2, ...,5
		// by starting at 5 and adding each node at head of list

		head = null; // create empty linked list
		LinearNode<Integer> intNode;

		for (int i = 5; i >= 1; i--) {
			// create a new node for i
			intNode = new LinearNode<Integer>(new Integer(i));
			// add it at the head of the linked list
			intNode.setNext(head);
			head = intNode;
		}

		printElements();
		// remove the node storing the value 5 from the list
		remove(3);
		printElements();
	}

	// This method removes from the linked list pointed by head the node
	// storing the value specified by the second parameter
	private static void remove(int value) {
		LinearNode<Integer> current = head, previous = null;

		// Find the node storing value
		while (current != null) {
			if (current.getElement().intValue() == value) { // node storing value was found
				previous.setNext(current.getNext()); // Node deleted
				break; // Exit loop
			} else {
				previous = current;
				current = current.getNext();
			}
		}

	}

	// Prints the values stored in the list pointed by head
	private static void printElements() {
		System.out.print("List of elements: ");
		LinearNode<Integer> current = head;
		while (current != null) {
			System.out.print(current.getElement());
			current = current.getNext();
			if (current != null)
				System.out.print(", ");
		}
		System.out.println("");
	}
}
