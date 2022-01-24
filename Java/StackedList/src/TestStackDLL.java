public class TestStackDLL {
	public static void main(String[] args) {
		// create a DoublyLinkedList with 5 nodes
		DoublyLinkedStack<Integer> stack = new DoublyLinkedStack<Integer>();
		for (int i = 0; i < 5; ++i)
			stack.push(Integer.valueOf(i));

		String result = stack.toString();
		System.out.println(result);
	}
}
