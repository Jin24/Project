/**
 * 
 * @author Jin Yoo
 * 250964902
 * jyoo222@uwo.ca
 * 
 * Creates an object type Edge with two nodes, starting point and ending point, and the type of edge between them (0,1,2)
 * 
 **/
public class Edge {
	private Node endPoint1, endPoint2;
	private int type;
	
	public Edge(Node u, Node v, int type) {
		this.endPoint1 = u;
		this.endPoint2 = v;
		this.type = type; //0 if public road, 1 if private road, -1 if reward road
	}
	public Node firstEndpoint() {
		return this.endPoint1;
	}
	public Node secondEndpoint() {
		return this.endPoint2;
	}
	public int getType() {
		return this.type;
	}
}