package utilities;

/**
 * @author Taoseef Aziz
 * @author Luke Genova
 * @author Matteus McKinley Wilson
 * @author Amimul Ehsan Zoha
 * 
 * @FILE: SQUARE_STATUS.java
 * 
 * @ASSIGNMENT: Project 6
 * 
 * @COURSE: CSc 335; Section 001; Spring 2022
 * 
 * @PURPOSE: This enumeration is used to hold various different constants on various
 *  positions in a grid or a 2d array. These enums will be used to determine 
 *  the status of a position. The position can either be unguessed, guessed, 
 *  contain a flag, contain a mine, or it can have a flag placed on a mine. 
 *  The user of the class may use 'getDescription' to get a printable description 
 *  of each enum.
 * 
 * @ENUMERATIONS:
 * 	UNGUESSED("UNGUESSED"),
 * 	GUESSED("GUESSED"),
 * 	FLAGGED("FLAGGED"),
 * 	MINE("MINE"),
 * 	FLAGGEDMINE("FLAGGEDMINE");
 * 	
 * @CONSTRUCTORS:
 * 	SQUARE_STATUS(String description)
 * 
 * @FIELDS:
 * 	private description : String
 * 
 * @METHODS:
 *  public getDescription()
 *	
 * @USAGE: 
 * 	Run using Minesweeper.java, never directly run apart from testing purposes.
 */
public enum SQUARE_STATUS {
	UNGUESSED("UNGUESSED"),
	GUESSED("GUESSED"),
	FLAGGED("FLAGGED"),
	MINE("MINE"),
	FLAGGEDMINE("FLAGGEDMINE");

	private String description;

	/**
	 * SQUARE_STATUS Constructor
	 * 
	 * @param description A string that represents the description of one of the constants.
	 */
	private SQUARE_STATUS(String description) {
		this.description = description;
	}

	/**
	 * Returns a description of the enum value.
	 * 
	 * @return A string containing the description of the enum value.
	 */
	public String getDescription() {
		return this.description;
	}
}
