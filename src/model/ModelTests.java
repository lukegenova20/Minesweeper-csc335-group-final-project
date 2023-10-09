package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import utilities.SQUARE_STATUS;

class ModelTests {

	//getGameState tests
	@Test
	void testGetGameState1() {
		MinesweeperModel model = new MinesweeperModel(10, 8);
		assertEquals(SQUARE_STATUS.UNGUESSED, model.getGameState().getPlayerGrid()[0][0]);
	}
	
	//getNumMines tests
	@Test
	void testGetNumMines1() {
		MinesweeperModel model = new MinesweeperModel(10, 8);
		assertEquals(8, model.getNumMines());
	}
	
	//getTime tests
	@Test
	void testGetTimeTest1() {
		MinesweeperModel model = new MinesweeperModel(10, 8);
		assertEquals(0, model.getTime());
	}
	
	//incrementTime tests
	@Test
	void testIncrementTime1() {
		MinesweeperModel model = new MinesweeperModel(10, 8);
		model.incrementTime();
		assertEquals(1, model.getTime());
	}
	@Test
	void testIncrementTime2() {
		MinesweeperModel model = new MinesweeperModel(10, 8);
		model.incrementTime();
		model.incrementTime();
		model.incrementTime();
		assertEquals(3, model.getTime());
	}
	
	//changeSquare tests
	@Test
	void testChangeSquare1() {
		MinesweeperModel model = new MinesweeperModel(10, 8);
		model.changeSquare(0, 0, SQUARE_STATUS.GUESSED);
		assertEquals(SQUARE_STATUS.GUESSED, model.getGameState().getPlayerGrid()[0][0]);
	}
	@Test
	void testChangeSquare2() {
		MinesweeperModel model = new MinesweeperModel(10, 8);
		model.changeSquare(0, 0, SQUARE_STATUS.MINE);
		assertEquals(SQUARE_STATUS.MINE, model.getGameState().getPlayerGrid()[0][0]);
	}
	@Test
	void testChangeSquare3() {
		MinesweeperModel model = new MinesweeperModel(10, 8);
		model.changeSquare(0, 0, SQUARE_STATUS.GUESSED);
		assertEquals(SQUARE_STATUS.UNGUESSED, model.getGameState().getPlayerGrid()[0][1]);
	}
	
	//makeMove tests
	@Test
	void testMakeMove1() {
		//0 mines for testing
		MinesweeperModel model = new MinesweeperModel(10, 0);
		model.makeMove(0, 0);
		assertEquals(SQUARE_STATUS.GUESSED, model.getGameState().getPlayerGrid()[0][0]);
	}
	@Test
	void testMakeMove2() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		model.makeMove(0, 0);
		//Recursive guessing at 0
		assertEquals(SQUARE_STATUS.GUESSED, model.getGameState().getPlayerGrid()[0][1]);
	}
	@Test
	void testMakeMove3() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		model.changeSquare(0, 0, SQUARE_STATUS.FLAGGED);
		assertEquals(false, model.makeMove(0, 0));
	}
	@Test
	void testMakeMove4() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		model.changeSquare(0, 1, SQUARE_STATUS.MINE);
		model.changeSquare(1, 0, SQUARE_STATUS.MINE);
		model.makeMove(0, 0);
		assertEquals(SQUARE_STATUS.UNGUESSED, model.getGameState().getPlayerGrid()[2][2]);
	}
	@Test
	void testMakeMove5() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		model.changeSquare(0, 1, SQUARE_STATUS.MINE);
		model.changeSquare(1, 0, SQUARE_STATUS.MINE);
		model.makeMove(0, 0);
		assertEquals(SQUARE_STATUS.MINE, model.getGameState().getPlayerGrid()[1][0]);
	}
	@Test
	void testMakeMove6() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		model.changeSquare(0, 0, SQUARE_STATUS.FLAGGEDMINE);
		assertEquals(false, model.makeMove(0, 0));
	}
	@Test
	void testMakeMove7() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		assertEquals(false, model.makeMove(11, 11));
	}
	
	//getAdjacentMines tests
	@Test
	void testGetAdjacentMines1() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		assertEquals(0, model.getAdjacentMines(0,0));
	}
	@Test
	void testGetAdjacentMines2() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		model.changeSquare(0, 1, SQUARE_STATUS.MINE);
		model.changeSquare(1, 0, SQUARE_STATUS.MINE);
		assertEquals(2, model.getAdjacentMines(0,0));
	}
	@Test
	void testGetAdjacentMines3() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		model.changeSquare(5, 6, SQUARE_STATUS.MINE);
		model.changeSquare(6, 5, SQUARE_STATUS.MINE);
		model.changeSquare(6, 6, SQUARE_STATUS.MINE);
		model.changeSquare(4, 4, SQUARE_STATUS.MINE);
		assertEquals(4, model.getAdjacentMines(5,5));
	}
	
	//isMineHit tests
	@Test
	void testIsMineHit1() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		assertEquals(false, model.isMineHit());
	}
	@Test
	void testIsMineHit2() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		model.changeSquare(0, 1, SQUARE_STATUS.MINE);
		assertEquals(false, model.isMineHit());
	}
	@Test
	void testIsMineHit3() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		model.changeSquare(0, 1, SQUARE_STATUS.MINE);
		model.makeMove(0, 0);
		assertEquals(false, model.isMineHit());
	}
	@Test
	void testIsMineHit4() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		model.changeSquare(0, 1, SQUARE_STATUS.MINE);
		model.makeMove(0, 1);
		assertEquals(true, model.isMineHit());
	}

}