import java.util.ArrayList;
import java.util.Iterator;
/**
 * 
 * @author Jin Yoo
 * 250964902
 * jyoo222@uwo.ca
 * 
 * Class creates an graph using edge class and node class. The edges are saved in the 2d array and the nodes are saved in a 1d array. However, their respective node 'names' are equal to their index values.
 * 
 *  **/

public class Graph implements GraphADT{
	private Edge[][] edgeGraph;
	private Node[] nodeList;
	
	
	
	/**
	 * Initializes a Graph by creating a 2d array with size n from @param and a 1d array with size n from @param as well. Initializes the 1d node array to nodes with names equal to their indexes. 
	 * @param n
	 */
	public Graph(int n) {
		this.edgeGraph = new Edge[n][n];
		this.nodeList = new Node[n];
		for(int i = 0; i < nodeList.length; i ++) {
			nodeList[i] = new Node (i);
		}
	}

	/**
	 * Inserts an edge between two nodes if possible. If there is already an edge there, or if either of the nodes are not existent, then a Graph exception is thrown.
	 */
	public void insertEdge(Node nodeu, Node nodev, int edgeType) throws GraphException {
		//int x = findIndexNode(nodeu.getName());
		//int y = findIndexNode(nodev.getName());
		int x = nodeu.getName();
		int y = nodev.getName();
		//System.out.println("Inserting edges into two nodes with names: "+x+"   and : "+ y+"    with edgetype: "+ edgeType);
		if(x < 0 || x >= nodeList.length || y < 0 || y >= nodeList.length) {
			throw new GraphException();
		}else {
			if((edgeType > 1 || edgeType < -1)&& (edgeGraph[x][y] != null||edgeGraph[y][x] != null)) {
				throw new GraphException();
			}
			//System.out.println("CHECKING");
			edgeGraph[x][y] = new Edge(nodeu, nodev, edgeType);
			edgeGraph[y][x] = new Edge(nodev, nodeu, edgeType);
		}
		
	}

	/**
	 * Returns the node found with the name. If there is no node with that name, then a Graph exception is thrown.
	 */
	public Node getNode(int name) throws GraphException {
		//int x = findIndexNode(name);
		int x = name;
		//System.out.println("HELLO"+x);
		if(x < 0 || x >= nodeList.length) {
			throw new GraphException();
		}else {
			//System.out.println(""+ nodeList[x]);
			return nodeList[x];
		}
		/**
		for(int i = 0; i < this.nodeList.length; i ++) {
			if(nodeList[i] == null) {
				throw new GraphException();
			}
			if(nodeList[i].getName() == name) {
				return nodeList[i];
			}
		}
		**/
		//throw new GraphException(); //No node with that name is in the graph
	}

	/**
	 * Creates an iterator of all the edges on a node. If there are any null edges, they are removed before the iterator is returned. If there are no edges on the node, then null is returned. If the node is not existent, then a graph exception is thrown.
	 */
	public Iterator incidentEdges(Node u) throws GraphException {
		ArrayList<Edge> list = new ArrayList<Edge>();
		int x = u.getName();
		if(x < 0 || x >= nodeList.length) {
			throw new GraphException();
		}else {
			/**
			if(x == 0) {
				list.add(edgeGraph[x][x+1]);
				list.add(edgeGraph[x+1][x]);
				list.add(edgeGraph[x+1][x+1]);
			}else if(x > 0 && x < edgeGraph.length - 1 ) {
				list.add(edgeGraph[x][x+1]);
				list.add(edgeGraph[x][x-1]);
				list.add(edgeGraph[x+1][x]);
				list.add(edgeGraph[x-1][x]);
				list.add(edgeGraph[x+1][x+1]);
				list.add(edgeGraph[x-1][x-1]);
				list.add(edgeGraph[x+1][x-1]);
				list.add(edgeGraph[x-1][x+1]);
			}else if(x == edgeGraph.length - 1) {
				//list.add(edgeGraph[x][x+1]);
				list.add(edgeGraph[x][x-1]);
				//list.add(edgeGraph[x+1][x]);
				list.add(edgeGraph[x-1][x]);
				//list.add(edgeGraph[x+1][x+1]);
				list.add(edgeGraph[x-1][x-1]);
				//list.add(edgeGraph[x+1][x-1]);
				//list.add(edgeGraph[x-1][x+1]);
			}
			**/
			for (int i = 0; i < edgeGraph.length; i ++) {
				list.add(edgeGraph[x][i]);
			}
			
		}
		for(int i = 0; i < list.size(); i ++) {
			if(list.get(i) == null) {
				list.remove(i);
				i = -1;
			}
		}
		if(list.size() == 0) {
			return null;
		}
		Iterator iterator = list.iterator();
		return iterator;
	}

	public Edge getEdge(Node u, Node v) throws GraphException {
		//int x = findIndexNode(u.getName());
		//int y = findIndexNode(v.getName());
		int x = u.getName();
		int y = v.getName();
		//System.out.println("TEST"+ x+y);
		if(x < 0 || x >= nodeList.length || y < 0 || y >= nodeList.length) {
			//System.out.println("In here");
			throw new GraphException();
		}
		if(edgeGraph[x][y] == null) {
			throw new GraphException();
		}
		if(edgeGraph[x][y].getType() == 0 || edgeGraph[x][y].getType() == 1 || edgeGraph[x][y].getType() == -1) {
			return edgeGraph[x][y];
		}else {
			throw new GraphException();
		}
	}

	public boolean areAdjacent(Node u, Node v) throws GraphException {
		int x = u.getName();
		int y = v.getName();
		if(x < 0 || x >= nodeList.length || y < 0 || y >= nodeList.length) {
			throw new GraphException();
		}
		if(x == y + 1 || x == y - 1 || y == x + 1 || y == x - 1) {
			return true;
		}else {
			return false;
		}
		
	}
}
