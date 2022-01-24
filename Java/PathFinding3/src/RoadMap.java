import java.util.Iterator;
import java.io.*;
import java.util.ArrayList;
/**
 * 
 * @author Jin Yoo
 * 250964902
 * jyoo222@uwo.ca
 * 
 * Creates a class that constructs and finds a path(if possible) from the start node to the finish node from a text file given. 
 **/

public class RoadMap {
	private Graph roadMap;
	private int initialMoney;
	private int startingNode, destinationNode;
	private int lose, gain, nodeNum;

	/**
	 * Constructs a map with the textfile given. Throws Map Exception if the file is empty or in the wrong format
	 * @param inputFile
	 * @throws MapException
	 * @throws FileNotFoundException
	 */
	public RoadMap(String inputFile) throws MapException, FileNotFoundException{
		int count = 0;
		int nodeCounter = 0;
		int width = 0;
		try {
			BufferedReader file = new BufferedReader(new FileReader(inputFile));
			BufferedReader countNodes = new BufferedReader(new FileReader(inputFile));
			String countNumNodes = countNodes.readLine();
			String readInput = file.readLine();
			//System.out.println(""+readInput);
			while(countNumNodes!= null) { //Counts how many nodes are in the graph
				if(count >= 8) {
				for(int i = 0; i < countNumNodes.length(); i ++) {
					char c = countNumNodes.charAt(i);
					//System.out.println("Char is: "+c+"    Count is: "+count);
					if (c == '+') {
						this.nodeNum ++;
					}
				}
				}
				count ++;
				countNumNodes = countNodes.readLine();
			}
			count = 0;
			while(readInput != null) {
				//System.out.println("INPUT: "+ readInput+"   COUNT: "+ count+"    NODE COUNTER: "+ nodeCounter);
				if(count == 1) {
					this.startingNode = Integer.parseInt(readInput);
				}else if(count == 2) {
					this.destinationNode = Integer.parseInt(readInput);
				}else if(count == 5) {
					this.initialMoney = Integer.parseInt(readInput);
				}else if(count == 6) {
					this.lose = Integer.parseInt(readInput);
				}else if(count == 7) {
					this.gain = Integer.parseInt(readInput);
				}else if(count == 8) {
					//System.out.println(readInput);
					width = ((readInput.length()/2));
					//System.out.println("NODE NUM : "+nodeNum);
					roadMap = new Graph(nodeNum);
					for(int i = 0; i < readInput.length(); i ++) {
						char c = readInput.charAt(i);
						if(c == ('+')) {
						}else if(c == 'F') {
							roadMap.insertEdge(roadMap.getNode(nodeCounter), roadMap.getNode(nodeCounter + 1), 0);
							nodeCounter++;
						}else if(c == 'T') {
							roadMap.insertEdge(roadMap.getNode(nodeCounter), roadMap.getNode(nodeCounter + 1), 1);
							nodeCounter++;
						}else if(c == 'C') {
							roadMap.insertEdge(roadMap.getNode(nodeCounter), roadMap.getNode(nodeCounter + 1), -1);
							nodeCounter++;
						}else if(c == 'X') {
							nodeCounter++;
						}
					}
				}else if(count > 8) {
					if(count % 2 == 1) {
						int timesX = 0;
						nodeCounter ++;
						for(int i = 0; i < readInput.length(); i ++) {
							char c = readInput.charAt(i);
							//System.out.println("Character in view: "+ c+ "    And Width :"+width);
							if(c == ('+')) {
							}else if(c == 'F') {
								roadMap.insertEdge(roadMap.getNode(nodeCounter), roadMap.getNode(nodeCounter - width -1), 0);
								timesX = 0;
								nodeCounter ++;
							}else if(c == 'T') {
								roadMap.insertEdge(roadMap.getNode(nodeCounter), roadMap.getNode(nodeCounter - width -1), 1);
								timesX = 0;
								nodeCounter ++;
							}else if(c == 'C') {
								roadMap.insertEdge(roadMap.getNode(nodeCounter),roadMap.getNode(nodeCounter - width -1), -1);
								timesX = 0;
								nodeCounter ++;
							}else if(c == 'X') {
								timesX ++;
								if(timesX % 2 == 0 && timesX > 1) {
									nodeCounter ++;
								}
							}
						}
						nodeCounter = nodeCounter - width - 1;
					}else {
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						for(int i = 0; i < readInput.length(); i ++) {
							char c = readInput.charAt(i);
							if(c == ('+')) {
							}else if(c == 'F') {
								roadMap.insertEdge(roadMap.getNode(nodeCounter), roadMap.getNode(nodeCounter + 1), 0);
								nodeCounter ++;
							}else if(c == 'T') {
								roadMap.insertEdge(roadMap.getNode(nodeCounter), roadMap.getNode(nodeCounter + 1), 1);
								nodeCounter ++;
							}else if(c == 'C') {
								roadMap.insertEdge(roadMap.getNode(nodeCounter), roadMap.getNode(nodeCounter +1), -1);
								nodeCounter ++;
							}else if(c == 'X') {
								nodeCounter ++;
							}
						}
					}
				}
				count ++;
				readInput = file.readLine();
			}
		} catch (Exception e) {
			throw new MapException();
		}
	}
		
	public Graph getGraph() {
		return this.roadMap; 
	}

	public int getStartingNode() {
		return this.startingNode;
	}

	public int getDestinationNode() {
		return this.destinationNode;
	}
	
	public int getInitialMoney() {
		return this.initialMoney;
	}

	/**
	 * Finds a path from the starting node to the end node if possible. If there is no path, null is returned, otherwise an iterator of the node path is returned. 
	 * @param start
	 * @param destination
	 * @param intialMoney
	 * @return
	 */
	public Iterator findPath(int start, int destination, int intialMoney) {
		ArrayList<Node> path = new ArrayList<Node>();
		int[] edgeSave = new int[this.nodeNum];
		boolean[] notEnoughMoney = new boolean[this.nodeNum];
		boolean[] alreadyTried = new boolean[this.nodeNum];
		Node starting = roadMap.getNode(start);
		Node finish = roadMap.getNode(destination);
		boolean found = false; 
		boolean ableToReach = false;
		Edge pathRoad = new Edge(new Node(0), new Node(0), 0);
		path.add(starting);
		Iterator pathIterator = roadMap.incidentEdges(starting);
		if(pathIterator.hasNext() == false) {
			return null;
		}
		starting.setMark(true);
		while(path.size() > 0) {
			while(pathIterator.hasNext()) {
				pathRoad = (Edge)pathIterator.next();
				Node next = pathRoad.secondEndpoint();
				//System.out.println("MONEY AT THE MOMENT: "+ this.initialMoney);
				//System.out.println("Edge: "+ pathRoad.getType()+"     Next Node: "+ next.getName()+ "  "+ next.getMark());
				if(pathRoad.getType()== 0 && next.getMark() == false && next.getName() != this.startingNode) {// No touch to money since it is a free road
					path.add(next);
					edgeSave[next.getName()] = pathRoad.getType();
					next.setMark(true);
					pathIterator = roadMap.incidentEdges(next);
					//System.out.println("LATEST NODE ADDED THROUGH PUBLIC : "+ next.getName() + "    SIZE OF ARRAY: "+ path.size());
					ableToReach = true;
				}else if(pathRoad.getType() == 1 && this.initialMoney >= this.lose && next.getMark() == false) {
					this.initialMoney = this.initialMoney - this.lose;//Toll -1 money for going on toll road
					//Node next = pathRoad.secondEndpoint();
					path.add(next);
					edgeSave[next.getName()] = pathRoad.getType();
					next.setMark(true);
					pathIterator = roadMap.incidentEdges(next);
					//System.out.println("LATEST NODE ADDED THROUGH TOLL : "+ next.getName() + "    SIZE OF ARRAY: "+ path.size());
					ableToReach = true;
				}else if(pathRoad.getType() == -1 && next.getMark() == false) {
					this.initialMoney = this.initialMoney + this.gain;//Reward +1 money for going on reward road
					//Node next = pathRoad.secondEndpoint();
					path.add(next);
					edgeSave[next.getName()] = pathRoad.getType();
					next.setMark(true);
					pathIterator = roadMap.incidentEdges(next);
					//System.out.println("LATEST NODE ADDED THROUGH CASH : "+ next.getName() + "    SIZE OF ARRAY: "+ path.size());
					ableToReach = true;
				}else if(pathRoad.getType() == 1 && next.getMark() == false && this.initialMoney < this.lose){//There is not enough money
					//System.out.println("NOT ENOUGH MONEY AT: "+ pathRoad.firstEndpoint().getName());
					notEnoughMoney[pathRoad.firstEndpoint().getName()] = true;
					//pathRoad = (Edge)pathIterator.next();
				}else if(this.initialMoney >= this.lose && notEnoughMoney[next.getName()] == true && alreadyTried[next.getName()] == false) {
					if(pathRoad.getType() ==1) {
						this.initialMoney -= this.lose;
					}
					path.add(next);
					edgeSave[next.getName()] = pathRoad.getType();
					pathIterator = roadMap.incidentEdges(next);
					alreadyTried[next.getName()] = true;
				}
				
				if(next.getName() == this.destinationNode && ableToReach) {
					found = true;
					break;
				}
				ableToReach = false;
			}
			if(found) {
				break;
			}
			

			if(path.size() > 1 ) {
				if(edgeSave[path.get(path.size()-1).getName()] == 1) {
					this.initialMoney += this.lose;
					//System.out.println("\n\nADD BACK MONEY\n\n");
				}else if(edgeSave[path.get(path.size()-1).getName()] == -1) {
					this.initialMoney -= this.gain;
					//System.out.println("\n\nTAKE BACK MONEY\n\n");
				}
				
				//System.out.println("\n\nDeleting one node from array: "+ path.get(path.size()-1).getName());
				//System.out.println();
				/**if(notEnoughMoney[path.get(path.size()-1).getName()] == true) {
					path.get(path.size()-1).setMark(false);
				}
				
				if(notEnoughMoney[path.get(path.size()-1).getName()] == true) {
					path.get(path.size()-1).setMark(false);
				}
				**/
				path.remove(path.size()-1);
				pathIterator = roadMap.incidentEdges(path.get(path.size()-1));
				
			}
			
		}
		
		if(found) {
			Iterator pathFound = path.iterator();
			return pathFound; //ITERATOR
		}else {
			return null;
		}
		
	}
}
