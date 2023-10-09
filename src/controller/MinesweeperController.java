package controller;

import java.io.IOException;
import java.util.Random;

import model.MinesweeperModel;
import utilities.SQUARE_STATUS;

/** 
 * @author Taoseef Aziz
 * @author Luke Genova
 * @author Matteus McKinley Wilson
 * @author Amimul Ehsan Zoha
 * 
 * @FILE: MinesweeperController.java
 * 
 * @ASSIGNMENT: Project 6
 * 
 * @COURSE: CSc 335; Section 001; Spring 2022
 * 
 * @PURPOSE: This class represents the controller component of the Minesweeper
 *  game that uses the MVC architecture. The controller is the layer between 
 *  the view and the model. The controller takes in any kind of user input and 
 *  validates it before sending the data to the model which then update it's data
 *  accordingly. Basically, this class handles the majority of the game logic. 
 *  The controller is the layer between the view and the model.
 * 
 * @FIELDS:
 * 	madeFirstMove : boolean
 * 	
 * @CONSTRUCTORS:
 * 	MinesweeperController(MinesweeperModel model)
 * 
 * @METHODS:
 *  private makeSubsequentMove(int, int)
 *  private makeFirstMove(int, int)
 *  private placeMines(int, int)
 *  public makeMove(int, int)
 *  public changeFlagPlacement(int, int)
 *  public getAdjacentMines(int, int)
 *  public saveGameState()
 *  public loadGameState()
 *  public isGameOver()
 *  public getTime()
 *  public incrementTime()
 *  public getMadeFirstMove()
 *	
 * @USAGE: 
 * 	Run using Minesweeper.java, never directly run apart from testing purposes.
 */

public class MinesweeperController {

	
	private MinesweeperModel model;
	
	/**
	 * MinesweeperController
	 * 
	 * @param model A model object that represents the model component of the MVC.
	 */
	public MinesweeperController(MinesweeperModel model) {
		this.model = model;
	}

	/**
	 * Makes a standard move 
	 * 
	 * @param x The x position of the grid that the user selected.
	 * @param y The y position of the grid that the user selected.
	 */
	private void makeSubsequentMove(int x, int y) {
		
		SQUARE_STATUS locationStatus = model.getGameState().getPlayerGrid()[x][y];
		// If already guessed, or marked as a flag, return false (not valid to click)
		if(locationStatus == SQUARE_STATUS.GUESSED || locationStatus == SQUARE_STATUS.FLAGGED || locationStatus == SQUARE_STATUS.FLAGGEDMINE) {
			return;
		}
		model.makeMove(x, y);
		return;
	}
		
	
	/**
	 * Makes the first move of the game by setting the selected tile to
	 * have a mine adjacency value of zero, then randomly place mines and 
	 * show all nearby mines that have a mine adjacency value of zero.
	 * 
	 * @param x The x position of the grid that the user selected.
	 * @param y The y position of the grid that the user selected.\
	 */
	private void makeFirstMove(int x, int y) {
		// Make a move at the location, and then place the mines (to ensure first move is never a mine)
		
		
		placeMines(x,y);
		
		model.makeMove(x, y);
		model.getGameState().makeFirstMove();
		return;
	}
	 
	/**
	 * Places mines randomly through out the grid. 
	 * 
	 * @param x The x position of the grid that the user selected. Used 
	 * to make sure that the method doesn't place a mine on the selected position.
	 * @param y The y position of the grid that the user selected.  Used 
	 * to make sure that the method doesn't place a mine on the selected position.
	 */
	private void placeMines(int x, int y) {
		//Places mines everywhere except x and y
		
		// Place mines randomly on unguessed squares
		int numMines = model.getNumMines();
		int gridSize = model.getGameState().getPlayerGrid().length;
		
		// For each mine to place	
		for(int i = 0; i < numMines; i++) {
					
			// Randomly pick an x and y coordinate
			int randX = new Random().nextInt(gridSize);	
			int randY = new Random().nextInt(gridSize);
			
			//Boolean to see if adjacent to the given square
			boolean xAdj = randX >= x-1 && randX <= x+1;
			boolean yAdj = randY >= y-1 && randY <= y+1;
			boolean adjacent = xAdj && yAdj;
			// While the random place is the given place to not place on
			while((adjacent) || 
					model.getGameState().getPlayerGrid()[randX][randY] == SQUARE_STATUS.MINE) {
				randX = new Random().nextInt(gridSize);
				randY = new Random().nextInt(gridSize);
				//Reset adjacent boolean
				xAdj = randX >= x-1 && randX <= x+1;
				yAdj = randY >= y-1 && randY <= y+1;
				adjacent = xAdj && yAdj;
			}
			// When done we should have suitable random coordinates, so place a mine there
			model.changeSquare(randX, randY, SQUARE_STATUS.MINE);
		}
	}

	/**
	 * Makes a move that resulted from the player selecting a tile on the grid.
	 * 
	 * @param x The x position of the grid that the user selected.
	 * @param y The y position of the grid that the user selected.
	 */
	public void makeMove(int x, int y) {
		if (!model.getGameState().getMadeFirstMove()) {
			makeFirstMove(x,y);
		} else {
			makeSubsequentMove(x,y);
		}
	}

	/**
	 * Checks if the user is placing a flag in a valid position, and then
	 * make the changes to the player grid.
	 * 
	 * @param x The x position of the grid that the user selected.
	 * @param y The y position of the grid that the user selected.
	 */
	public void changeFlagPlacement(int x, int y) {
		
		// If the user didn't make a move, the user can't place a flag.
		if (!model.getGameState().getMadeFirstMove()) {
			return;
		}
	
		//If already guessed, return (can't set to flag)
		SQUARE_STATUS locationStatus = model.getGameState().getPlayerGrid()[x][y];
		if(locationStatus == SQUARE_STATUS.GUESSED) {
			return;
		}
		
		//Change the given location to a flag, or a flagged mine if it is a mine
		//If there is a flag in the position, remove it.
		if(locationStatus == SQUARE_STATUS.MINE) {
			model.changeSquare(x, y, SQUARE_STATUS.FLAGGEDMINE);
			
		} else if (locationStatus == SQUARE_STATUS.FLAGGEDMINE) {
			model.changeSquare(x, y, SQUARE_STATUS.MINE);	
			
		} else if (locationStatus == SQUARE_STATUS.FLAGGED){
			model.changeSquare(x, y, SQUARE_STATUS.UNGUESSED);
			
		} else {
			model.changeSquare(x, y, SQUARE_STATUS.FLAGGED);
		}
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
		return model.getAdjacentMines(x, y);
	}
	
	/**
	 * Saves the current state of the game. 
	 * 
	 * @throws IOException If there was a generally failure of reading from the 
	 * stream, such as a file not existing, it throws the exception to the caller.
	 */
	public void saveGameState() throws IOException {
		if (model.getGameState().getMadeFirstMove()) {
		model.saveGameState();
		}
	}
	
	/**
	 * Loads a previous game state.
	 * 
	 * @return true if the board matched sizes, false if it didn't
	 * @throws IOException If there was a generally failure of reading from the 
	 * stream, such as a file not existing, it throws the exception to the caller.
	 * @throws ClassNotFoundException If the class that was read from the 
	 * input stream was not found, throw the exception to the caller.
	 */
	public boolean loadGameState() throws IOException, ClassNotFoundException {
		return model.loadGameState();
	}

	/**
	 * Used to determine if the game is over. 
	 * 
	 * @return True if the game is over, false otherwise.
	 */
	public boolean isGameOver() {
		//First check if all non mine squares have been guessed
		boolean allNonMines = true;
		int gridSize = model.getGameState().getPlayerGrid().length;
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				//If a square is unguessed, or flagged (with no mine), we haven't won
				if(model.getGameState().getPlayerGrid()[i][j] == SQUARE_STATUS.UNGUESSED 
						|| model.getGameState().getPlayerGrid()[i][j] == SQUARE_STATUS.FLAGGED) {
					allNonMines = false;
				}
					
			}
		}
		
		//Game is over if we hit a mine or all non mines were guessed
		return model.isMineHit() || allNonMines;
	}
	
	
	
	/**
	 * Returns the total time that the user has been playing a single game. 
	 * 
	 * @return The time in seconds the user has been playing for the 
	 */
	public int getTime() {
		return model.getTime();
	}

	/**
	 * Increments the time counter by one.
	 * 
	 */
	public void incrementTime() {
		model.incrementTime();
	}
	
	/**
	 * Returns a value that notifies the caller if the first move was made
	 * or not. 
	 *
	 * @return True if the user made the first move, false otherwise.
	 */
	public boolean getMadeFirstMove() {
		return model.getGameState().getMadeFirstMove();
	}

}