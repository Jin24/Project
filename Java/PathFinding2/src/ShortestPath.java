/**
 * @author Jin 
 * Student number: 250964902
 * 
 * This class is used to find the shortest path (if possible) from the WPC cell to the Customer's Cell.
 * The algorithm used to find the path is the nextCell method which finds the next cell to move to from the current cell with no priorities..
 * Calls the map by using the filename given and then uses the information from the map to try and find a path to the customer's cell from the WPC.
 */

import java.io.FileNotFoundException;
import java.io.IOException;

public class ShortestPath {
	Map cityMap;

	/**
	 * Method used to open the map file given in the @param of the main method.
	 * Throws Exception if needed based on the error caused.
	 * 
	 * @param filename
	 * @throws InvalidMapException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ShortestPath(String filename) throws InvalidMapException, FileNotFoundException, IOException {
		try {
			cityMap = new Map(filename);
		} catch (InvalidMapException e) {
			System.out.print("The input file is the wrong format.");
		} catch (FileNotFoundException e) {
			System.out.print("The file name is not found.");
		} catch (IOException e) {
			System.out.print("The output/input was incorrect.");
		}
	}

	/**
	 * Contains the algorithm to find the shortest path from the WPC Cell to the
	 * Customer's Cell. Creates a DLList of DLNodes which are cells used to find the
	 * path. The map used is ran through @param. Throws Exceptions based on the
	 * error caused.
	 * 
	 * @param args
	 * @throws InvalidMapException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InvalidMapException, FileNotFoundException, IOException {

		if (args.length < 1) { // if there is no input in the @param, the system exits.
			System.out.println("You must provide the name of the input file");
			System.exit(0);
		}

		try {
			ShortestPath path = new ShortestPath(args[0]);
			Map mapUsed = new Map(args[0]);
			DLList<MapCell> pathList = new DLList<>();

			// The Power Cell used to start the path.
			MapCell startCell = mapUsed.getStart();
			// Adding power cell into the doubly linked list.
			pathList.insert(startCell, 0);
			startCell.markInList();
			boolean done = false;
			MapCell currentCell = startCell;
			int size = 0;

			while (!pathList.isEmpty() && !currentCell.isCustomer()) {

				currentCell = pathList.getSmallest();
				currentCell.markOutList();

				if (currentCell.isCustomer()) {
					done = true;
					break;
				}

				// for (int i = 0 ; i < 4 ; i ++) {

				while (path.nextCell(currentCell) != null && !path.nextCell(currentCell).isBlock()
						&& !path.nextCell(currentCell).isMarked()) {

					MapCell neighbor = path.nextCell(currentCell);

					int distance = 1 + currentCell.getDistanceToStart();
					// neighbor.markInList();
					// System.out.println(pathList.getDataValue(neighbor));
					if (neighbor.getDistanceToStart() > distance) {
						neighbor.setDistanceToStart(distance);
						neighbor.setPredecessor(currentCell);
					}
					int distanceP = neighbor.getDistanceToStart();
					if (neighbor.isMarkedInList() && distanceP < pathList.getDataValue(neighbor)) {
						pathList.changeValue(neighbor, distanceP);
					}
					if (!neighbor.isMarkedInList()) {
						pathList.insert(neighbor, distanceP);
						// System.out.println("List size: " + pathList.size());
						neighbor.markInList();
					}
					size = distance;

				}
				// if (done)// break;
				// }

				// }
				// if (done)
				// break;

			}

			if (done) {
				System.out.println("A path to the customer's cell has been found.");
				System.out.println("The number of cells used was: " + size);
			} else {
				System.out.println("There is no path found to the customer's cell.");
			}

		} catch (

		InvalidMapException e) {
			System.out.print("The input file is the wrong format.");
		} catch (FileNotFoundException e) {
			System.out.print("The file name is not found.");
		} catch (IOException e) {
			System.out.print("The output/input was incorrect.");
		}
	}

	/**
	 * This method takes a cell as a parameter and finds the a neighbor of that cell that it can move to.
	 * If there are neighbors that the current cell can move to, then that neighbor cell is returned.
	 * If there are no neighbors that the current cell can move to, then 'null' is returned.
	 * 
	 * @param cell
	 * @return
	 */
	private MapCell nextCell(MapCell cell) {
		MapCell nextCell = null;

		/**
		 * If the current cell is the WPC cell, then it can move in all directions to
		 * legal neighbors.
		 */
		if (cell.isPowerStation()) {
			for (int i = 0; i < 4; i++) {
				MapCell neighbor = cell.getNeighbour(i);
				if (neighbor != null && !neighbor.isMarked()) {
					// The if statements used here are to find which neighbor cell is the next path
					if ((neighbor.isCustomer() && i == 0) || (neighbor.isOmniSwitch() && i == 0)
							|| (neighbor.isVerticalSwitch() && i == 0)) {
						nextCell = neighbor;
						break;
					} else if ((neighbor.isCustomer() && i == 1) || (neighbor.isOmniSwitch() && i == 1)
							|| (neighbor.isHorizontalSwitch() && i == 1)) {
						nextCell = neighbor;
						break;
					} else if ((neighbor.isCustomer() && i == 2) || (neighbor.isOmniSwitch() && i == 2)
							|| (neighbor.isVerticalSwitch() && i == 2)) {
						nextCell = neighbor;
						break;
					} else if ((neighbor.isCustomer() && i == 3) || (neighbor.isOmniSwitch() && i == 3)
							|| (neighbor.isHorizontalSwitch() && i == 3)) {
						nextCell = neighbor;
						break;
					}
				}

			}

		}
		/**
		 * If the current cell is an Omni switch, then it can move in all directions to
		 * legal neighbors.
		 */
		if (cell.isOmniSwitch()) {
			for (int i = 0; i < 4; i++) {
				MapCell neighbor = cell.getNeighbour(i);
				if (neighbor != null && !neighbor.isMarked()) {
					// The if statements used here are to find which neighbor cell is the next path
					if ((neighbor.isCustomer() && i == 0) || (neighbor.isOmniSwitch() && i == 0)
							|| (neighbor.isVerticalSwitch() && i == 0)) {
						nextCell = neighbor;
						break;
					} else if ((neighbor.isCustomer() && i == 1) || (neighbor.isOmniSwitch() && i == 1)
							|| (neighbor.isHorizontalSwitch() && i == 1)) {
						nextCell = neighbor;
						break;
					} else if ((neighbor.isCustomer() && i == 2) || (neighbor.isOmniSwitch() && i == 2)
							|| (neighbor.isVerticalSwitch() && i == 2)) {
						nextCell = neighbor;
						break;
					} else if ((neighbor.isCustomer() && i == 3) || (neighbor.isOmniSwitch() && i == 3)
							|| (neighbor.isHorizontalSwitch() && i == 3)) {
						nextCell = neighbor;
						break;
					}
				}

			}
		}

		/**
		 * If the current cell is a Vertical switch, then it can move north or south to
		 * its legal neighbors.
		 */
		if (cell.isVerticalSwitch()) {
			for (int i = 0; i < 4; i++) {
				MapCell neighbor = cell.getNeighbour(i);
				if (neighbor != null && !neighbor.isMarked()) {
					// The if statements used here are to find which neighbor cell is the next path
					if ((neighbor.isCustomer() && i == 0) || (neighbor.isOmniSwitch() && i == 0)
							|| (neighbor.isVerticalSwitch() && i == 0)) {
						nextCell = neighbor;
						break;
					}
					if ((neighbor.isCustomer() && i == 2) || (neighbor.isOmniSwitch() && i == 2)
							|| (neighbor.isVerticalSwitch() && i == 2)) {
						nextCell = neighbor;
						break;
					}
				}

			}
		}

		/**
		 * If the current cell is a Horizontal switch, then it can move west or east to
		 * its legal neighbors.
		 */
		if (cell.isHorizontalSwitch()) {
			for (int i = 0; i < 4; i++) {
				MapCell neighbor = cell.getNeighbour(i);
				if (neighbor != null && !neighbor.isMarked()) {
					// The if statements used here are to find which neighbor cell is the next path
					if ((neighbor.isCustomer() && i == 1) || (neighbor.isOmniSwitch() && i == 1)
							|| (neighbor.isHorizontalSwitch() && i == 1)) {
						nextCell = neighbor;
						break;
					}
					if ((neighbor.isCustomer() && i == 3) || (neighbor.isOmniSwitch() && i == 3)
							|| (neighbor.isHorizontalSwitch() && i == 3)) {
						nextCell = neighbor;
						break;
					}
				}

			}

		}

		return nextCell;
		// Returns type mapCell to the main method.

	}
}
