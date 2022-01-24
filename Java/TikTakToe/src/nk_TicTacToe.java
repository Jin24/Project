/**
 * 
 * 
 * @author Jin
 * Student Number: 250964902
 *
 *This class is used when the game runs. It has classes to make a new game board and contains functions that allow the game to run. It also checks for winners and scores of certain game boards in order for 
 *the computer to know where to position its next marker.
 *
 *

 */


public class nk_TicTacToe {

	
	private char[][] gameBoard;
	private int inline;
	private int max_levels;
	
	public nk_TicTacToe () {
		this.inline= 0;
		this.max_levels = 0;
		gameBoard = new char[0][0];
		gameBoard[0][0] = ' ';
	}
	
	/**
	 * 
	 * @param board_size (Size of the board in a integer that is INTEGER x INTEGER 2d size)
	 * @param inline (How many number of markers ("O" or "X") in a row determines a winner)
	 * @param max_levels (How many levels of a tree the code will iterate through to see the best score for the computer)
	 */
	public nk_TicTacToe (int board_size, int inline, int max_levels) {
		this.inline = inline;
		this.max_levels = max_levels;
		gameBoard = new char[board_size][board_size];
		for (int i = 0 ; i < board_size ; i ++) {
			for (int j = 0; j < board_size ; j ++) {
				gameBoard[i][j] = ' '; //Initializes all spaces in the INTEGER x INTEGER 2d board created to ' ' (Blank spaces since no player has placed a marker there)
			}
		}
	}
	
	/**
	 * Creates a new Dictionary with size 7151 (Or integer in the argument)
	 * @return Creates a new dictionary of size INTEGER for storing each configuration of the game board
	 */
	
	public Dictionary createDictionary() {
		return new Dictionary(7151);
	}
	
	
	
	/**
	 * This class saves the current game board in a string and tries to get the score of the configuration from the dictionary. 
	 * @param dictionary of configurations of their configuration of the board and its scores
	 * @return score of the configuration given, or -1 if there is no configuration in the dictionary
	 */
	public int repeatedConfig(Dictionary configurations) {
		String boardInPlay = "";
		for (int i = 0; i < gameBoard.length; i ++) {
			for (int j= 0; j< gameBoard[i].length; j ++) {
				boardInPlay = boardInPlay + gameBoard[j][i];//Adds the markers of the game board into a string by iterating through the 2d array.
			}
		}
		if (configurations.get(boardInPlay) == -1) { //if the configuration is not in the dictionary, then it returns -1
			return -1;
		} else {
			return configurations.get(boardInPlay); //if the configuration is in the dictionary, then it returns the respective score of the configuration.
		}
	}
	
	/**
	 * This class inserts configuration and score into the dictionary if there is not a record already storing the same configuration.
	 * @param configurations
	 * @param score of the configuration
	 */
	public void insertConfig(Dictionary configurations, int score) {
		String boardInPlay = "";
		for (int i = 0; i < gameBoard.length; i ++) {
			for (int j= 0; j< gameBoard.length; j ++) {
				boardInPlay = boardInPlay + gameBoard[j][i];
				
			}
		}

		//System.out.println("Game Board: "+ boardInPlay);
		//System.out.println("INSERT CONFIG:" + boardInPlay);
		try {
		configurations.insert(new Record(boardInPlay,score));
		}catch (DictionaryException e) {
		
		}
		//System.out.println(boardInPlay+"INSERT: "+configurations.insert(new Record(boardInPlay,score)));
	}
	
	
	/**
	 * Stores the symbol given in the parameter into the 2d array location using the row and column number given in the parameter
	 * @param row
	 * @param col
	 * @param symbol
	 */
	public void storePlay(int row, int col, char symbol) {
		try {
		gameBoard[row][col] = symbol;
		}catch (ArrayIndexOutOfBoundsException a) {
			
		}
	}
	
	/**
	 * Checks if the space located in the 2d array using row and column, given in the parameter is empty.
	 * @param row
	 * @param col
	 * @return true if empty, false is not empty
	 */
	public boolean squareIsEmpty (int row, int col) {
		if (gameBoard[row][col]== ' ') {
			return true;
		}else {
			return false;
		}
		
	}
	
	/**
	 * Checks if there is a winner of the symbol, given in the parameter, in the game board. The winner could win diagonally, vertically, or horizontally.
	 * @param symbol
	 * @return True if there is a winner, false if there isn't.
	 */
	public boolean wins (char symbol) {
		int winCondition = inline;
		int checkWin = 0;
		for (int i = 0; i < gameBoard.length; i ++) {
			for (int j = 0; j < gameBoard[i].length; j ++) {
				if (gameBoard[i][j] == symbol){
					checkWin = 1;
					if(i+(winCondition-1) < gameBoard.length) { //Checks to see if the [i][j] location + k numbers across to meet win condition will not go out of array bounds
						
						for (int k = 1; k < winCondition; k ++) { //Win Across To the Right
							if(gameBoard[i][j] == gameBoard[i+k][j]) {
								checkWin ++;
							}else {
								checkWin = 1;
								break;
							}
						}
					}
					if(checkWin >= winCondition) {
						return true;
					}
					
					
					if(i-(winCondition-1) >= 0) { //Checks to see if the [i][j] location + k numbers across to meet win condition will not go out of array bounds
						
						for (int k = 1; k < winCondition; k ++) { //Win Across To the Left
							if(gameBoard[i][j] == gameBoard[i-k][j]) {
								checkWin ++;
							}else {
								checkWin = 1;
								break;
							}
						}
					}
					if(checkWin >= winCondition) {
						return true;
					}
					
					
					if((j-(winCondition-1)) >= 0) { //Checks that the upwards check will be in bounds of array
						for (int k = 1; k < winCondition; k ++) { //Win Upwards
							if(gameBoard[i][j] == gameBoard[i][j-k]) {
								checkWin ++;
							}else {
								checkWin= 1;
								break;
							}
						}
					}
					
					if(checkWin >= winCondition) {
						return true;
					}
					
					
					if((j+(winCondition-1)) < gameBoard[i].length) { //Checks that the downwards check will be in bounds of array
						for (int k = 1; k < winCondition; k ++) { //Win Downwards
							if(gameBoard[i][j] == gameBoard[i][j+k]) {
								checkWin ++;
							}else {
								checkWin= 1;
								break;
							}
						}
					}
					
					if(checkWin >= winCondition) {
						return true;
					}
					
					if((i+(winCondition-1) < gameBoard.length)&&(j+(winCondition-1)) < gameBoard[i].length) {
						for (int k = 1; k < winCondition; k ++) { //Win Diagonal To the Bottom Right
							if(gameBoard[i][j] == gameBoard[i+k][j+k]) {
								checkWin ++;
							}else {
								checkWin= 1;
								break;
							}
						}
					}
					if(checkWin >= winCondition) {
						return true;
					}
					
					if((i-(winCondition-1) >= 0)&&(j+(winCondition-1)) < gameBoard[i].length) {//Checks of diagonal to the left of the [i][j] array position will not go out of bounds
						for (int k = 1; k < winCondition; k ++) {//Win Diagonal To the Bottom Left
							if(gameBoard[i][j] == gameBoard[i-k][j+k]) {
								checkWin++;
								
							}else {
								checkWin = 1;
								break;
							}
						}
						
					}
					
					if(checkWin >= winCondition) {
						return true;
					}
				}
			}
		}
		if(checkWin >= winCondition) {
			return true;
			
		}else {
			return false;
		}
	}
	
	/**
	 * If all the spaces of the board are filled, and there are no winners, then the game is a draw and will return true. Otherwise will return false
	 * @return true if the game is a draw, false if the game is not a draw (Playable spaces still available)
	 */
	public boolean isDraw() {
		for (int i = 0; i < gameBoard.length; i ++) {
			for (int j= 0; j< gameBoard[i].length; j ++) {
				if (gameBoard[i][j] == ' ') {
					
				}else {
					return false;
				}
				
			}
		}
		return true;
	}
	
	
	
	/**
	 * Evaluates the board in play to see if there are any winners, if there is a draw, or if the game should still continue
	 * @return integer corresponding to the outcome of the board (Winner, draw, continue)
	 */
	public int evalBoard() {
		if (wins('O')) {//computer wins
			return 3;
		}else if (wins('X')) {//human wins
			return 0;
		}else if (isDraw()) {//draw
			return 2;
		}else { //Game still in progress (Empty spaces still available)
			return 1;
		}
	}
	
}


