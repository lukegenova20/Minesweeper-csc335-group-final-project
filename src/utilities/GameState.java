package utilities;

import java.io.Serializable;

/**
 * @author Taoseef Aziz
 * @author Luke Genova
 * @author Matteus McKinley Wilson
 * @author Amimul Ehsan Zoha
 * 
 * @FILE: GameState.java
 * 
 * @ASSIGNMENT: Project 6
 * 
 * @COURSE: CSc 335; Section 001; Spring 2022
 * 
 * @PURPOSE: This class is used mostly by the model of the game to represent the 
 *  current game state. An instance of this class would store most of the 
 *  primitive data that the game uses to hold data such as the grid, values that
 *  determine game completion, and the timer that is displayed on the gui. This 
 *  object is also used to save the current state of the game and load the previous 
 *  state of the game.  
 * 
 * @FIELDS:
 * 	madeFirstMove : boolean
 * 	
 * @CONSTRUCTORS:
 * 	GameState(int gridSize, int numMines)
 * 
 * @METHODS:
 *  public toString()
 *  public getPlayerGrid()
 *  public getTimeCounter()
 *  public setTimeCounter(int)
 *  public getNumMines()
 *  public changeSquare(int, int, SQUARE_STATUS)
 *  public isMineHit()
 *  public setHitMine()
 *	
 * @USAGE: 
 * 	Run using Minesweeper.java, never directly run apart from testing purposes.
 */
public class GameState implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	private int timeCounter;
	private int numMines;
	private boolean madeFirstMove;
	
	// Used to determine if a mine was hit.
	private boolean hitMine;

	private SQUARE_STATUS[][] playerGrid;

	/**
	 * GameState constructor.
	 * 
	 * @param gridSize The size of the grid that will be used to play.
	 * @param numMines The number of mines that will be placed.
	 */
	public GameState(int gridSize, int numMines) {
		//set timer to zero
		timeCounter = 0;
		madeFirstMove = false;
		this.numMines = numMines;
		
		//create new 2d grid and populate it with the UNGUESSED enum
		playerGrid = new SQUARE_STATUS[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++)
			for (int j = 0; j < gridSize; j++)
				playerGrid[i][j] = SQUARE_STATUS.UNGUESSED;
	}

	@Override
	public String toString() {
		System.out.println("Time: "+timeCounter);
		String returnStr = "";
		for (int i = 0; i < playerGrid.length; i++) {
			for (int j = 0; j < playerGrid.length; j++)
			{
				if (playerGrid[j][i] == SQUARE_STATUS.UNGUESSED)
					returnStr += "U  ";
				else if (playerGrid[j][i] == SQUARE_STATUS.MINE)
					returnStr += "M  ";
				else if (playerGrid[j][i] == SQUARE_STATUS.GUESSED)
					returnStr += "G  ";
				else if (playerGrid[j][i] == SQUARE_STATUS.FLAGGED)
					returnStr += "F  ";
				else if (playerGrid[j][i] == SQUARE_STATUS.FLAGGEDMINE)
					returnStr += "FM ";
			}
			returnStr += "\n";
		}

		return returnStr;
	}

	/**
	 * Returns the current player's grid.
	 * 
	 * @return the current player's grid.
	 */
	public SQUARE_STATUS[][] getPlayerGrid() {
		return playerGrid;
	}
	
	/**
	 * Returns the timer counter that keeps track of the time.
	 * 
	 * @return The length the game has been going in seconds.
	 */
	public int getTimeCounter() {
		return timeCounter;
	}

	/**
	 * Changes the timer counter that keeps track of the time.
	 * 
	 * @param timeCounter The new value that will be stored in the timer value.
	 */
	public void setTimeCounter(int timeCounter) {
		this.timeCounter = timeCounter;
	}
	
	/**
	 * Returns the number of mines that are still in the board.
	 * 
	 * @return The number of mines that are left on the board as an integer.
	 */
	public int getNumMines() {
		return numMines;
	}
	
	/**
	 * This method changes the status of a specific square on the game grid. 
	 * 
	 * @param x The x position on the grid that the change happened. 
	 * @param y The y position on the grid that the change happened
	 * @param status The type of result that happened after an action was made.
	 */
	public void changeSquare(int x, int y, SQUARE_STATUS status) {
		if (playerGrid[x][y] == SQUARE_STATUS.FLAGGEDMINE) {
			numMines++;
			playerGrid[x][y] = status;
		} else if (playerGrid[x][y] == SQUARE_STATUS.MINE) {
			numMines--;
			playerGrid[x][y] = status;
		} else {
			playerGrid[x][y] = status;
		}

	}

	/**
	 * This method determines if a mine was hit after a move was made. 
	 * 
	 * @return True if a mine was hit, false otherwise.
	 */
	public boolean isMineHit() {
		return hitMine;
	}
	
	/**
	 * This method notifies the game that a mine was hit which changes 
	 * the game state.
	 * 
	 */
	public void setHitMine() {
		hitMine = true;
	}
	
	/**
	 * This method changes the state of the game when the user makes the
	 * first move.
	 * 
	 */
	public void makeFirstMove() {
		madeFirstMove = true;
	}
	/**
	 * Returns a boolean that shows if the player made their first move.
	 * 
	 * @return A boolean that shows if the player made their first move.
	 */
	public boolean getMadeFirstMove() {
		return madeFirstMove;
	}
	
}