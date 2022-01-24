/**
 * @author Jin
 * Student Number: 250964902
 * 
 * This class is used to find the path (if possible) from the WPC cell to the Customer's Cell.
 * The algorithm used to find the path is the bestCell method which finds the best cell to move to from the current cell by sorting through priorities (Customer's cell --> Omni cell --> Horizontal Cell/Vertical Cell)
 * Calls the map by using the filename given and then uses the information from the map to try and find a path to the customer's cell from the WPC.
 */

import java.io.FileNotFoundException;
import java.io.IOException;

public class FindConnection {

	/**
	 * Instance variable 'cityMap' is used to store the map provided by the @param
	 * and uses it to find the starting WPC cell
	 */
	Map cityMap;

	/**
	 * This method is used to try and open a file with the filename provided by
	 * the @param. Then saves the file into a variable 'cityMap'.
	 * 
	 * @param filename - name of the file
	 * @throws InvalidMapException   - exception thrown if map is invalid
	 * @throws FileNotFoundException - exception thrown if file is not found
	 * @throws IOException           - exception thrown for other file errors
	 */
	public FindConnection(String filename) throws InvalidMapException, FileNotFoundException, IOException {
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
	 * main method used to run the entire program.
	 * 
	 * @param can be entered in run configuration in Eclipse. Checked for all 8 maps
	 *            provided, and was able to complete the algorithm to find out if
	 *            there was a path possible from the WPC to the Customer's cell.
	 *            Maps 1-5 had paths- Maps 6-8 did not have paths.
	 */
	public static void main(String[] args) throws InvalidMapException, FileNotFoundException, IOException {

		if (args.length < 1) { // if there is no input in the @param, the system exits.
			System.out.println("You must provide the name of the input file");
			System.exit(0);
		}

		try {
			// String mapFileName = args[0];
			FindConnection connection = new FindConnection(args[0]);
			Map mapUsed = new Map(args[0]);

			// Creating empty Stack 'mapStack' to store the useful/needed cells used to find
			// the path to the customer's cell.
			ArrayStack<MapCell> mapStack = new ArrayStack<>();

			// 'startCell' finds the WPC cell from the 'mapUsed' variable.
			MapCell startCell = mapUsed.getStart();
			// Add the startCell (WPC Cell) into the mapStack as it is a needed cell in
			// finding the path to the customer's cell.
			mapStack.push(startCell);
			/**
			 * Cells marked 'In Stack' are cells that are needed/useful for finding the path
			 * to the cutomer's cell. Cells marked 'Out Stack' are cells that are deemed as
			 * 'dead ends' or not useful for finding the path to the customer's cell.
			 * startCell is marked in stack because it is the WPC cell and is definitely
			 * useful in finding the path to the customer's cell.
			 */
			startCell.markInStack();

			// 'currentCell' stores the top data item from the mapStack stack.
			MapCell currentCell = mapStack.peek();

			/**
			 * This while loop runs as long as the currentCell is not the customer's cell
			 * and the mapStack is not empty. It runs by placing the top data item from the
			 * mapStack stack into the currentCell variable and sends it as a parameter to
			 * the bestCell method. The bestCell method returns null if there were no
			 * neighbors it could move towards, and returns a type other than a null if
			 * there was a neighbor found. If the currentCell cannot move to a neighbor,
			 * then it is marked 'out of stack' and if it can move to a neighbor, then it is
			 * marked 'in stack'. Cells marked 'in stack' are added to the mapStack stack,
			 * and the 'out of stack' cells are removed from the stack.
			 */
			while (!mapStack.isEmpty() && !currentCell.isCustomer()) {
				currentCell = mapStack.peek();
				if (connection.bestCell(currentCell) != null) {
					MapCell nextCell = connection.bestCell(currentCell);
					nextCell.markInStack();
					mapStack.push(nextCell);
				} else if (connection.bestCell(currentCell) == null) {
					mapStack.pop();
					currentCell.markOutStack();
				}

			}
			/**
			 * These two if statements run after the two arguments in the while loop above
			 * are not met: 1. The mapStack storing the 'in stack' cells is empty. 2. The
			 * currentCell is the Customer's cell.
			 * 
			 * The first if statement below is used when the Customer's cell is found and
			 * prints corresponding String that states that a path has been found and the
			 * number of cells used to make a path.
			 * 
			 * The second if statement below is used when there are no data items in the
			 * mapStack stack, and therefore no path is possible from the WPC to the
			 * Customer's cell.
			 * 
			 */
			if (currentCell.isCustomer()) {
				System.out.println("The path to the Customer's cell has been found.");
				System.out.println("The number of cells used was " + mapStack.size());
			}
			if (mapStack.isEmpty()) {
				System.out.println("There is no path to the Customer's cell found");
			}
		} catch (InvalidMapException e) {
			System.out.print("The input file is the wrong format.");
		} catch (FileNotFoundException e) {
			System.out.print("The file name is not found.");
		} catch (IOException e) {
			System.out.print("The output/input was incorrect.");
		}
	}

	/**
	 * This method uses an algorithm to find the best neighbor (By priority:
	 * Customer's Cell --> Omni Switch --> Horizontal/Vertical Switch). The neighbor
	 * cells of @param cell is saved as index 0,1,2,3 (Starting North and going
	 * clockwise). If a path from the cell to its neighbor is found, then the
	 * neighbor cell is returned. If no path from the cell to its neighbor is found,
	 * then the null is returned.
	 * 
	 * @param cell
	 * @return
	 */
	private MapCell bestCell(MapCell cell) {

		// nextCell variable stores either null or the neighbor cell explained above
		// done variable stores a boolean type True/False and is used to break the
		// forloop below, if a neighbor is found.
		MapCell nextCell = null;
		boolean done = false;

		/**
		 * If the current cell is the power station cell (WPC), then it can move in all
		 * directions to legal neighbors.
		 */
		if (cell.isPowerStation()) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					MapCell neighbor = cell.getNeighbour(j);
					if (neighbor != null && !neighbor.isMarked()) {
						// The if statements used here are to find which neighbor cell is the best path
						// by using the priorities (Customer's Cell --> Omni --> Vertical)
						if (neighbor.isCustomer() && i == 0) {
							nextCell = neighbor;
							done = true;
							break;
						} else if (neighbor.isOmniSwitch() && i == 1) {
							nextCell = neighbor;
							done = true;
							break;
						} else if ((neighbor.isVerticalSwitch()) && i == 2) {
							nextCell = neighbor;
							done = true;
							break;
						}
					}
				}
				if (done) {
					break;
				}
			}

		}
		/**
		 * If the current cell is an Omni switch, then it can move in all directions to
		 * legal neighbors.
		 */
		if (cell.isOmniSwitch()) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					MapCell neighbor = cell.getNeighbour(j);
					if (neighbor != null && !neighbor.isMarked()) {
						// The if statements used here are to find which neighbor cell is the best path
						// by using the priorities (Customer's Cell --> Omni --> Vertical/Horizontal)
						if (neighbor.isCustomer() && i == 0) {
							nextCell = neighbor;
							done = true;
							break;
						} else if (neighbor.isOmniSwitch() && i == 1) {
							nextCell = neighbor;
							done = true;
							break;
						}
						if (neighbor.isVerticalSwitch() && i == 2 && (j == 0 || j == 2)) {
							nextCell = neighbor;
							done = true;
							break;
						}
						if (neighbor.isHorizontalSwitch() && i == 2 && (j == 1 || j == 4)) {
							nextCell = neighbor;
							done = true;
							break;
						}

					}
				}
				if (done) {
					break;
				}
			}
		}

		/**
		 * If the current cell is a Vertical switch, then it can move north or south to
		 * its legal neighbors.
		 */
		if (cell.isVerticalSwitch()) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j += 2) {
					MapCell neighbor = cell.getNeighbour(j);
					if (neighbor != null && !neighbor.isMarked()) {
						// The if statements used here are to find which neighbor cell is the best path
						// by using the priorities (Customer's Cell --> Omni --> Vertical)
						if (neighbor.isCustomer() && i == 0) {
							nextCell = neighbor;
							done = true;
							break;
						} else if (neighbor.isOmniSwitch() && i == 1) {
							nextCell = neighbor;
							done = true;
							break;
						} else if ((neighbor.isVerticalSwitch()) && i == 2) {
							nextCell = neighbor;
							done = true;
							break;
						}
					}
				}
				if (done) {
					break;
				}
			}
		}

		/**
		 * If the current cell is a Horizontal switch, then it can move west or east to
		 * its legal neighbors.
		 */
		if (cell.isHorizontalSwitch()) {
			for (int i = 0; i < 3; i++) {
				for (int j = 1; j < 4; j += 2) {
					MapCell neighbor = cell.getNeighbour(j);
					if (neighbor != null && !neighbor.isMarked()) {
						// The if statements used here are to find which neighbor cell is the best path
						// by using the priorities (Customer's Cell --> Omni --> Horizontal)
						if (neighbor.isCustomer() && i == 0) {
							nextCell = neighbor;
							done = true;
							break;
						}
						if (neighbor.isOmniSwitch() && i == 1) {
							nextCell = neighbor;
							done = true;
							break;
						}
						if (neighbor.isHorizontalSwitch() && i == 2) {
							nextCell = neighbor;
							done = true;
							break;
						}
					}
				}
				if (done) {
					break;
				}
			}
		}

		// Returns type mapCell to the main method.
		return nextCell;

	}
}