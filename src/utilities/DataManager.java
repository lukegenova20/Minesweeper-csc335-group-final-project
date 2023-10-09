package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Taoseef Aziz
 * @author Luke Genova
 * @author Matteus McKinley Wilson
 * @author Amimul Ehsan Zoha
 * 
 * @FILE: DataManager.java
 * 
 * @ASSIGNMENT: Project 6
 * 
 * @COURSE: CSc 335; Section 001; Spring 2022
 * 
 * @PURPOSE: This class holds only two public methods that each save the 
 * game state and load the game state.  
 * 
 * @FIELDS:
 * 	none
 * 	
 * @CONSTRUCTORS:
 * 	none
 * 
 * @METHODS:
 *  public save(Serializable, String)
 *  public load(String)
 *	
 * @USAGE: 
 * 	Run using Minesweeper.java, never directly run apart from testing purposes.
 */
public class DataManager {

	/**
	 * Saves the current game.
	 *
	 * @param gameState A serializable object, most likely a GameState object that holds the current 
	 * state of the game. 
	 * @param filename A string that represents the filename of a to be file that will hold the GameState. 
	 * @throws IOException If there is any issues with saving the game, this exception will be thrown.
	 */
	public static void save(Serializable gameState, String filename) throws IOException {
		File saveFile = new File(filename);
	    FileOutputStream fos = new FileOutputStream(saveFile);
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
	    oos.writeObject(gameState);
	    oos.close();
	    
	}

	/**
	 * Loads the current game. 
	 *
	 * @param filename A string that represents the filename of a to be file should hold the GameState.
	 * @return The GameState that the user saved.
	 * @throws IOException One of the exceptions that needs to be checked for when input steams. Can 
	 * handle the exception where the user didn't have a game saved.
	 * @throws ClassNotFoundException One of the exceptions that needs to be checked for when input steams.
	 */
	public static Object load(String filename) throws IOException, ClassNotFoundException {
		File saveFile = new File(filename);
		FileInputStream fis = new FileInputStream(saveFile);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    Serializable gameState = (Serializable) ois.readObject();
	    ois.close();
		return gameState;
	}

}
