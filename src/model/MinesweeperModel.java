package model;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import utilities.DataManager;
import utilities.GameState;
import utilities.SQUARE_STATUS;

/**	 
 * @author Taoseef Aziz
 * @author Matteus Wilson
 * @author Luke Genova
 * @author Amimul Zoha
 *
 * @FILE: MinesweeperModel.java
 * 
 * @ASSIGNMENT: Project 6
 * 
 * @COURSE: CSc 335; Section 001; Spring 2022
 * 
 * @PURPOSE: This class defines an object that is used to represent the 
 * model of the Minesweeper game that use the MVC architecture. The 
 * model stores data and alters the data its holding based on events that 
 * happened in the view, which then gets validated in the controller. The data it
 * holds is the grid itself which includes the status of each square, the number of 
 * mines that are left on the grid, and a timer that keeps track of the 
 * time the user has been playing in seconds. The model also follows the 
 * Observer/Observable design where the model is the Observable that notifies 
 * the observer(the view) that it has updated its data. 
 * 
 * @FIELDS:
 * 	gameState : GameState
 * 	
 * @CONSTRUCTORS:
 * 	MinesweeperModel(int gridSize, int numMines)
 * 
 * @METHODS:
 * 	public MinesweeperModel(int, int)
 *  public addObserver(Observer)
 *  public saveGameState()
 *  public loadGameState()
 *  public getGameState()
 *  public getNumMines()
 *  public changeSquare(int, int, SQUARE_STATUS)
 *  public getAdjacentMines(int, int)
 *  public getTime()
 *  public incrementTime()
 *  public makeMove(int, int)
 *  public ()
 *  private guessSpecificSquare(int, int)
 *  private guessNearby(int, int)
 *	
 * @USAGE: 
 * 	Run using Minesweeper.java, never directly run apart from testing purposes.
 */

public class MinesweeperModel extends Observable {

	
	private GameState gameState;


	/** MinesweeperModel
	 * 	Constructor for the minesweeper model object. Initializes a new 
	 * 	GameState object which contains a grid of UNGUESSED enums, timer
	 * 	set  to 0 and hitmine set to false.
	 *
	 * 
	 * @param gridSize The size of the grid that will be used to play.
	 * @param numMines The number of mines that will be placed.
	 */
	public MinesweeperModel(int gridSize, int numMines) {
		//grid is square, so one parameter gridSize determines the shape of the grid
		gameState = new GameState(gridSize, numMines);

	}


	/**
	 * Adds an observer to the set of observers for this object, 
	 * provided that it is not the same as some observer already in 
	 * the set.
	 * 
	 * @param o an observer to be added.
	 *
	 */
	@Override
	public void addObserver(Observer o) {
		super.addObserver(o);
	}
	
	/**
	 * Saves the current state of the game. 
	 * 
	 * @throws IOException If there was a generally failure of reading from the 
	 * stream, such as a file not existing, it throws the exception to the caller.
	 */
	public void saveGameState() throws IOException {
		DataManager.save(gameState,"./savefile.dat");
	}
	
	/**
	 * Loads the current game.
	 * @return true if the board matched sizes, false if it didn't
	 * 
	 * @throws IOException If there was a generally failure of reading from the 
	 * stream, such as a file not existing, it throws the exception to the caller.
	 * @throws ClassNotFoundException If the class that was read from the 
	 * input stream was not found, throw the exception to the caller.
	 */
	public boolean loadGameState() throws IOException, ClassNotFoundException {
		GameState prevState = (GameState) DataManager.load("./savefile.dat");
		if (gameState.getPlayerGrid().length != prevState.getPlayerGrid().length) {
			return false;
		}
		gameState = prevState;
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
		return true;
	}
	


	/**	Getter for the the current state of the game 
	 * 	(gameState state object) maintained in the model
	 *
	 * 
	 * @return a GameState object that is the part of the model keeping
	 * 	track of the current time, board state and hitMine status.
	 * Returns the current state of the game.
	 * 
	 */
	public GameState getGameState() {
		return gameState;
	}
	
	/**
	 * Returns the number of mines that are still in the board.
	 * 
	 * @return The number of mines that are left on the board as an integer.
	 */
	public int getNumMines() {
		return gameState.getNumMines();
	}
	
	/**
	 * Increments the time counter by one. 
	 * 
	 */
	public void incrementTime() {
		gameState.setTimeCounter(gameState.getTimeCounter()+1);
	}
	
	/**	Changes the state of the square at the specified location to one of 
	 * 	the following enum states: 1) UNGUESSED 2) GUESSED 3) FLAGGED 4) MINE
	 * 	5) FLAGGEDMINE
	 * 
	 * @param x integer x coordinate position on the game grid.
	 * @param y integer y coordinate position on the game grid.
	 * @param status represents the new enum status to be applied to the desired
	 * 	location.
	 */
	public void changeSquare(int x, int y, SQUARE_STATUS status) {
		gameState.changeSquare(x, y, status);
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
		
	}
	

	/**
	 * Makes a move that resulted from the player selecting a tile on the grid.
	 * 
	 * @param x The x position of the grid that the user selected
	 * @param y The y position of the grid that the user selected.
	 * @return True if the move was successful.
	 */
	public boolean makeMove(int x, int y) {
		//Check and guess nearby squares of applicable
		boolean guessResult = guessSpecificSquare(x, y);
				
		return guessResult;
	}
	
	/**
	 * This method returns the number of mines that are adjacent to
	 * a specific tile on the player grid.
	 * 
	 * @param x The x position of the specific tile
	 * @param y The y position of the specific tile
	 * @return The number of mines that are adjacent to a specific tile.
	 */
	public int getAdjacentMines(int x, int y) {
		int mines = 0;
		int gameSize = gameState.getPlayerGrid().length;
		//Check all adjacent squares (if they exist), and return number of adjacent mines
		//For the rows above and below
		for(int i = -1; i <= 1; i++) {
			//For the columns to the side
			for(int j = -1; j <= 1; j++) {
				int tempX = x + i;
				int tempY = y + j;
				//Validate
				if(tempX >= 0 && tempX < gameSize && tempY >= 0 && tempY < gameSize) {
					//if a mine, increment the number of mines
					if(gameState.getPlayerGrid()[tempX][tempY] == SQUARE_STATUS.MINE || gameState.getPlayerGrid()[tempX][tempY] == SQUARE_STATUS.FLAGGEDMINE) {
						mines++;
					}
				}
			}
		}
		
		return mines;
	}

	/**	Getter for the time value stored in the gameState state object
	 * 
	 * @return integer representing the amount of time that has elapsed since
	 * 	the player has made their first move.
	 */
	public int getTime() {
		return gameState.getTimeCounter();
	}

	/**	Called when player left-clicks the grid at a valid location. This method
	 * 	reveals the current square and recursively explores adjacent squares that
	 * 	have zero adjacent mines. Calls changeSquare() if a valid location is
	 * 	selected and returns a boolean indicating whether the guess caused any
	 * 	changes.
	 * 
	 * @param x integer x coordinate position on the game grid.
	 * @param y integer y coordinate position on the game grid.
	 * @return boolean indicating whether the guess caused any changes.
	 */
	private boolean guessSpecificSquare(int x, int y) {		
		// First check move validity
		int gridSize = this.getGameState().getPlayerGrid().length;
		
		if(x >= gridSize || x < 0 || y >= gridSize || y < 0) {
			return false;
		}
		SQUARE_STATUS locationStatus = this.getGameState().getPlayerGrid()[x][y];
		// If already guessed, or marked as a flag, return false (not valid to click)
		if(locationStatus == SQUARE_STATUS.GUESSED || locationStatus == SQUARE_STATUS.FLAGGED || locationStatus == SQUARE_STATUS.FLAGGEDMINE) {
			return false;
		}
		// If the guess is a mine, say we hit the mine and return true (which should lead to the game ending)
		if(locationStatus == SQUARE_STATUS.MINE) {
			gameState.setHitMine();
			this.setChanged();
			this.notifyObservers();
			this.clearChanged();
			return true;
		}
		// Otherwise it is an unguessed square, and we set it to guessed, and return true
		else {
			this.changeSquare(x, y, SQUARE_STATUS.GUESSED);
			//If there are no adjacent mines, guess all adjacent squares
			if(getAdjacentMines(x,y) == 0) {
				guessNearby(x, y);
			}
			//If there are no adjacent mines, guess nearby squares
			return true;
		}
	}

	/**	Calls makeMove at surrounding positions relative to location specified
	 * 	by parameter coords to find other squares with 0 adjacent mines
	 * 
	 * @param x integer x coordinate position on the game grid.
	 * @param y integer y coordinate position on the game grid.
	 */
	private void guessNearby(int x, int y) {
		int gameSize = gameState.getPlayerGrid().length;
		//For the rows above and below
		for(int i = -1; i <= 1; i++) {
			//For the columns to the side
			for(int j = -1; j <= 1; j++) {
				int tempX = x + i;
				int tempY = y + j;
				//Validate
				if(tempX >= 0 && tempX < gameSize && tempY >= 0 && tempY < gameSize) {
					//Ensure that the square hasn't been visited
					if(gameState.getPlayerGrid()[tempX][tempY] == SQUARE_STATUS.UNGUESSED ) {
						makeMove(tempX, tempY);
	
					}
				}
			}
		}
	}
		
	/**
	 * This method determines if a mine was hit after a move was made. 
	 * 
	 * @return True if a mine was hit, false otherwise.
	 */
	public boolean isMineHit() {
		return gameState.isMineHit();
	}
	
	
}