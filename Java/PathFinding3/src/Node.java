/**
 * 
 * @author Jin Yoo
 * 250964902
 * jyoo222@uwo.ca
 *
 *
 *This class creates an object Node with two private variables, mark(boolean) and name(integer).
 *The name is equal to the index values in arrays used in Graph.java
 *The mark boolean variable is false until the node is used during the path finding sequence.
 */
public class Node {
	private boolean mark;
	private int name;
	
	public Node(int name){
		this.name = name;
		this.mark = false;
	}
	public void setMark(boolean mark){
		this.mark = mark;
	}
	public boolean getMark() {
		return this.mark;
	}
	public int getName() {
		return this.name;
	}
}
