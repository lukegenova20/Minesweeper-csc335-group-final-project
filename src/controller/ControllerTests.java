package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.MinesweeperModel;
import utilities.SQUARE_STATUS;

class ControllerTests {

	//makeMove tests
	@Test
	void testMakeMove1() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		controller.makeMove(0, 0);
		assertEquals(SQUARE_STATUS.GUESSED, model.getGameState().getPlayerGrid()[0][0]);
	}
	@Test
	void testMakeMove2() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		controller.makeMove(0, 1);
		assertEquals(SQUARE_STATUS.GUESSED, model.getGameState().getPlayerGrid()[0][0]);
	}
	@Test
	void testMakeMove3() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		controller.makeMove(0, 0);
		controller.makeMove(0, 1);
		assertEquals(SQUARE_STATUS.GUESSED, model.getGameState().getPlayerGrid()[0][0]);
	}
	@Test
	void testMakeMove4() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		//Manually make some mines
		model.changeSquare(1, 0, SQUARE_STATUS.MINE);
		model.changeSquare(0, 1, SQUARE_STATUS.MINE);
		controller.makeMove(0, 0);
		assertEquals(SQUARE_STATUS.GUESSED, model.getGameState().getPlayerGrid()[0][0]);
	}
	@Test
	void testMakeMove5() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		//Manually make some mines
		model.changeSquare(1, 0, SQUARE_STATUS.MINE);
		model.changeSquare(0, 1, SQUARE_STATUS.MINE);
		controller.makeMove(0, 0);
		assertEquals(SQUARE_STATUS.MINE, model.getGameState().getPlayerGrid()[0][1]);
	}
	@Test
	void testMakeMove6() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		//Manually make some mines
		model.changeSquare(1, 0, SQUARE_STATUS.MINE);
		model.changeSquare(0, 1, SQUARE_STATUS.MINE);
		controller.makeMove(0, 0);
		assertEquals(SQUARE_STATUS.UNGUESSED, model.getGameState().getPlayerGrid()[3][3]);
	}
	@Test
	void testMakeMove7() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);

		model.changeSquare(0, 0, SQUARE_STATUS.FLAGGED);
		controller.makeMove(0, 0);
		assertEquals(SQUARE_STATUS.FLAGGED, model.getGameState().getPlayerGrid()[0][0]);
	}
	@Test
	void testMakeMove8() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);

		model.changeSquare(0, 0, SQUARE_STATUS.FLAGGEDMINE);
		controller.makeMove(0, 0);
		assertEquals(SQUARE_STATUS.FLAGGEDMINE, model.getGameState().getPlayerGrid()[0][0]);
	}

	//changeFlagPlacement tests
	@Test
	void testChangeFlagPlacement1() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		controller.changeFlagPlacement(0, 0);
		//no move, no flag
		assertEquals(SQUARE_STATUS.UNGUESSED, model.getGameState().getPlayerGrid()[0][0]);
	}
	@Test
	void testChangeFlagPlacement2() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		controller.changeFlagPlacement(0, 0);
		controller.changeFlagPlacement(0, 0);
		assertEquals(SQUARE_STATUS.UNGUESSED, model.getGameState().getPlayerGrid()[0][0]);
	}
	@Test
	void testChangeFlagPlacement3() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		controller.makeMove(0, 0);
		model.changeSquare(0, 0, SQUARE_STATUS.FLAGGEDMINE);
		controller.changeFlagPlacement(0, 0);
		assertEquals(SQUARE_STATUS.MINE, model.getGameState().getPlayerGrid()[0][0]);
	}
	@Test
	void testChangeFlagPlacement4() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		controller.makeMove(0, 0);
		model.changeSquare(0, 0, SQUARE_STATUS.UNGUESSED);
		controller.changeFlagPlacement(0, 0);
		assertEquals(SQUARE_STATUS.FLAGGED, model.getGameState().getPlayerGrid()[0][0]);
	}
	@Test
	void testChangeFlagPlacement5() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		controller.makeMove(0, 0);
		model.changeSquare(0, 0, SQUARE_STATUS.UNGUESSED);
		controller.changeFlagPlacement(0, 0);
		controller.changeFlagPlacement(0, 0);
		assertEquals(SQUARE_STATUS.UNGUESSED, model.getGameState().getPlayerGrid()[0][0]);
	}
	
	//getAdjacentMines tests
	@Test
	void testGetAdjacentMines1() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		assertEquals(0, controller.getAdjacentMines(0,0));
	}
	@Test
	void testGetAdjacentMines2() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		model.changeSquare(0, 1, SQUARE_STATUS.MINE);
		model.changeSquare(1, 0, SQUARE_STATUS.MINE);
		assertEquals(2, controller.getAdjacentMines(0,0));
	}
	@Test
	void testGetAdjacentMines3() {
		MinesweeperModel model = new MinesweeperModel(10, 0);
		MinesweeperController controller = new MinesweeperController(model);
		model.changeSquare(5, 6, SQUARE_STATUS.MINE);
		model.changeSquare(6, 5, SQUARE_STATUS.MINE);
		model.changeSquare(6, 6, SQUARE_STATUS.MINE);
		model.changeSquare(4, 4, SQUARE_STATUS.MINE);
		assertEquals(4, controller.getAdjacentMines(5,5));
	}
	
	//isGameOver tests
	@Test
	void testIsGameOver1() {
		MinesweeperModel model = new MinesweeperModel(10, 1);
		MinesweeperController controller = new MinesweeperController(model);
		model.changeSquare(0, 0, SQUARE_STATUS.MINE);
		assertEquals(false, controller.isGameOver());
	}
	@Test
	void testIsGameOver2() {
		MinesweeperModel model = new MinesweeperModel(10, 1);
		MinesweeperController controller = new MinesweeperController(model);
		model.changeSquare(0, 0, SQUARE_STATUS.MINE);
		controller.makeMove(0, 0);
		assertEquals(true, controller.isGameOver());
	}
	@Test
	void testIsGameOver3() {
		MinesweeperModel model = new MinesweeperModel(10, 1);
		MinesweeperController controller = new MinesweeperController(model);
		controller.makeMove(0, 0);
		int gridSize = model.getGameState().getPlayerGrid().length;
		//Find the randomly placed mine and flag it (simulates a game)
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				if(model.getGameState().getPlayerGrid()[i][j] == SQUARE_STATUS.MINE) {
					controller.changeFlagPlacement(i, j);
				}
				else {
					model.getGameState().getPlayerGrid()[i][j] = SQUARE_STATUS.GUESSED;
				}
			}
		}
		assertEquals(true, controller.isGameOver());
	}

}