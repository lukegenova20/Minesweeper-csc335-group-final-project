package view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import controller.MinesweeperController;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.MinesweeperModel;
import utilities.GameState;
import utilities.SQUARE_STATUS;

/**
 * @author Taoseef Aziz
 * @author Luke Genova
 * @author Matteus McKinley Wilson
 * @author Amimul Ehsan Zoha
 * 
 * @FILE: Minesweeper.java
 * 
 * @ASSIGNMENT: Project 6
 * 
 * @COURSE: CSc 335; Section 001; Spring 2022
 * 
 * @PURPOSE: This class is the main view class of the entire Minesweeper game.  
 * 
 * @FIELDS:
 * 	VBOX_GAP : int
 * 	HBOX_GAP : int
 * 	SCENE_WIDTH : int
 * 	SCENE_HEIGHT : int
 * 	TEXT_FONT_SIZE : int
 * 	LARGE_TEXT_FONT_SIZE : int
 * 	LARGEST_NUMBER_COUNT : int
 * 	BUTTON_WIDTH : double
 * 	BUTTON_HEIGHT : double
 * 	IMAGEVIEW_WIDTH : int
 * 	IMAGEVIEW_HEIGHT : int
 * 	IMAGE : Image
 * 	SHEET_WIDTH : int
 * 	SHEET_HEIGHT : int
 * 	SPRITE_COUNT : int
 * 	COLUMN_COUNT : int
 * 	ROW_COUNT : int
 * 	SPRITE_WIDTH : int
 * 	SPRITE_HEIGHT : int
 * 	OFFSET_X : int
 * 	OFFSET_Y : int
 * 	DURATION : int
 * 	PERIOD : int
 * 	MIN_GRID_SIZE : int
 * 	MAX_GRID_SIZE : int
 * 	GRID_COORD_SIZE : int
 * 	DARK_GREEN : Color
 * 	LIGHT_GREEN : Color
 * 	DARK_TAN : Color
 * 	LIGHT_TAN : Color
 * 	SELECTION_COLOR : Color
 * 	model : MinesweeperModel
 * 	controller : MinesweeperController
 * 	gridRef : Label[][]
 * 	gridSize : int
 * 	flags : int
 * 	clock : Timer
 * 	numToColors : HashMap<Integer, Color>
 * 	
 * @CONSTRUCTORS:
 * 	Minesweeper()
 * 
 * @METHODS:
 *  public view.Minesweeper.main(String[])
 *  update(Observable, Object)
 *  start(Stage)
 *  updateFlagCounter(Label)
 *  createFlagImage(Label)
 *  createAnimation(Label)
 *  updateGrid(GameState, boolean)
 *  setDifficultyButton(String, HBox, VBox)
 *  setCustomGUI(VBox)
 *  inputValid(TextField, TextField)
 *  showAlert(String)
 *  setGameGUI(VBox)
 *  createHeader(HBox)
 *  createTimer(Label)
 *  createTimer(...).new TimerTask() {...}
 *  createGrid(GridPane)
 *  addMenuBar(VBox)
 *  destroyTimer()
 *  changeDifficulty(String, VBox)
 *  showMoveSelection(MouseEvent)
 *  makeMove(MouseEvent)
 *  changeFlagPlacement(MouseEvent)
 *  setBackTileColor()
 *  setUpColorMap()
 *	
 * @USAGE GUIDE: 
 * 	Introductory guide:
 * 
 * If you haven't play Minesweeper at all and don't know the rules, I
 * recommend copying and pasting these URLs on your web browser. These URLS 
 * direct you to websites that give instructions on how to play the game: 
 *  	a.https://en.wikipedia.org/wiki/Minesweeper_(video_game)
 * 
 *  	b.https://www.instructables.com/How-to-play-minesweeper/
 * 
 * How to run the game and play the game:
 * 
 * 1. Once you run the program, a screen will pop up and you will be taken to a starting menu.
 * 
 * 2. The starting menu has you select what difficult you want to play the game in. 
 * 		a. Beginner: GRID: 10x10 && Mines: 10 
 * 		b. Intermediate: GRID: 20x20 && Mines: 40 
 * 		c. Expert: GRID: 30x30 && Mines: 100 
 * 		d. Custom: Any grid size and mine count you want as long as its within the restrictions.
 * 
 * 3. Once you selected a difficulty, you can start playing the game. 
 * 
 * 4. On the screen, there is a menu bar that allows you to either save or load your progress 
 * and change the difficulty of the game mid-game. 
 * 		a. There are many restrictions on the save/load feature, for example, you can't save or load the game unless 
 * 		you make your first move. 
 * 
 * 5. Once the game ends by winning or losing, you can rerun the program or select a difficulty
 * in the menubar if you want to play again.
 *
 */
public class Minesweeper extends Application implements Observer {

	private static final int VBOX_GAP = 40;
	private static final int HBOX_GAP = 30;

	private static final int SCENE_WIDTH = 1100;
	private static final int SCENE_HEIGHT = 800;

	private static final int TEXT_FONT_SIZE = 30;
	
	// Font size for the word Minesweeper in the starting menu.
	private static final int LARGE_TEXT_FONT_SIZE = 70;


	// The Timer stops counting once it gets to 9999.
	private static final int LARGEST_NUMBER_COUNT = 9999;


	private static final double BUTTON_WIDTH = 100.0;
	private static final double BUTTON_HEIGHT = 30.0;

	private static final int IMAGEVIEW_WIDTH = 55;
	private static final int IMAGEVIEW_HEIGHT = 55;
	
	
	/* Constants for sprite animations */
	private static final Image IMAGE = new Image("exp2.png", 
			false);

	private static final int SHEET_WIDTH = 256;
	private static final int SHEET_HEIGHT = 256;

	private static final int SPRITE_COUNT = 16;
	private static final int COLUMN_COUNT = 4;
	private static final int ROW_COUNT = 4;
	
	private static final int SPRITE_WIDTH = SHEET_WIDTH / COLUMN_COUNT;
	private static final int SPRITE_HEIGHT = SHEET_HEIGHT / ROW_COUNT;
	
	private static final int OFFSET_X = 0;
	private static final int OFFSET_Y = 0;
	
	private static final int DURATION = 2000;

	private static final int PERIOD = 1000;
	
	/* Constants for the grid. */
	
	private static final int MIN_GRID_SIZE = 4;
	private static final int MAX_GRID_SIZE = 30;
	
	private static final int GRID_COORD_SIZE = 500;

	/* Gives color to the squares */
	private static final Color DARK_GREEN = Color.rgb(150, 191, 77);
	private static final Color LIGHT_GREEN = Color.rgb(171, 213, 90);
	private static final Color DARK_TAN = Color.rgb(187, 161, 135);
	private static final Color LIGHT_TAN = Color.rgb(228, 194, 161);
	private static final Color SELECTION_COLOR = Color.rgb(192, 224, 130);

	/* Model & Controller */
	private MinesweeperModel model;
	private MinesweeperController controller;

	/* Reference to the node objects in the gui grid */
	private Label[][] gridRef;

	/* Holds an integer that represents the size of the grid. */
	private int gridSize;
	
	/* Represents the flag and mine counter(the flag and mine counter will always be the 
	 * same when the game starts).*/
	private int flags;
	
	/* Holds a reference to a timer object that acts like a thread and increments the timer
	 * when the game is running. */
	private Timer clock;
	
	/* Used to color the values in the squares. */
	private HashMap<Integer, Color> numToColors;
	



	/**
	 *  Minesweeper Constructor.
	 *
	 */
	public Minesweeper() {
		gridSize = 0;
		flags = 0;
		clock = null;
	}


	/**
	 * This main method launches the GUI application.
	 * 
	 * @param args Array of strings that represent command line arguments.
	 */
	public static void main(String[] args) {
		launch(Minesweeper.class, "");
	}

	/**
	 * This method is called when the Observed object has changed and
	 * notifies the observer. This method also notifies the other player
	 * thats connected when there is a change to itself.
	 *
	 * @param o the observable object.
	 * @param arg this argument contains the changed object.
	 *
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		MinesweeperModel newModel = (MinesweeperModel) o;
		GameState game = newModel.getGameState();
		boolean mineHit = newModel.isMineHit();
		updateGrid(game, mineHit);
		if (mineHit) {
			showAlert("END Loser");
		}
		//If we didn't hit a mine and the game is over, we have won
		else if (controller.isGameOver()) {
			showAlert("END Winner");
		}
	}
	
	/**
	 * The main entry point for all JavaFX applications.
	 *
	 * @param stage the primary stage for the application, onto which the
	 * application scene can be set.
	 *
	 */
	@Override
	public void start(Stage stage) {
		stage.setTitle("Minesweeper");

		VBox vbox = new VBox(VBOX_GAP);
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: #A27C5B;");

		Label welcome = new Label("Welcome to");
		welcome.setFont(Font.font("Times New Roman", FontWeight.BLACK,
        		FontPosture.ITALIC, TEXT_FONT_SIZE));

		Label minesweeper = new Label("Minesweeper");
		minesweeper.setFont(Font.font("Times New Roman", FontWeight.BLACK,
        		FontPosture.ITALIC, LARGE_TEXT_FONT_SIZE ));

		Label difficulty = new Label("Select your difficulty");
		difficulty.setFont(Font.font("Times New Roman", FontWeight.BLACK,
        		FontPosture.ITALIC, TEXT_FONT_SIZE));

        vbox.getChildren().addAll(welcome, minesweeper, difficulty);

        HBox hbox = new HBox(HBOX_GAP);
		hbox.setAlignment(Pos.CENTER);
		setDifficultyButton("Beginner", hbox, vbox);
		setDifficultyButton("Intermediate", hbox, vbox);
		setDifficultyButton("Expert", hbox, vbox);
		setDifficultyButton("Custom", hbox, vbox);
		vbox.getChildren().add(hbox);

		Scene scene = new Scene(vbox, SCENE_WIDTH, SCENE_HEIGHT);
		stage.setScene(scene);
		stage.show();


	}
	
	/*
	 *  METHODS THAT UPDATE THE GRID AFTER A PLAYER MOVE:
	 */
	
	/**
	 * This private helper method traverse through the JavaFX Node 
	 * Hierarchy to find the text node that displayed that number of 
	 * flags the user has and updates it.
	 * 
	 * @param label The tile that the flag just placed on. Used to go up
	 * the JavaFX Node hierarchy. 
	 */
	private void updateFlagCounter(Label label) {
		// Get the root of the JavaFX node hierarchy and update the flag counter.
	    GridPane grid = (GridPane) label.getParent();
	    HBox gridHbox = (HBox) grid.getParent();
		VBox root = (VBox) gridHbox.getParent();
		
		HBox hbox = (HBox) root.getChildren().get(1);
		Text flagCounter = (Text) hbox.getChildren().get(1);
		flagCounter.setText("" + flags);
	}
	
	
	/**
	 * This is a private helper method that place a image of 
	 * a flag on a specific tile. 
	 * 
	 * @param label The tile that a image of a flag will be placed on.
	 */
	private void createFlagImage(Label label) {
		int squareSize = GRID_COORD_SIZE/gridSize;
		Image img = new Image("file:triangular-flag_1f6a9.png");
	    ImageView view = new ImageView(img);
	    view.setFitHeight(squareSize);
	    view.setFitWidth(squareSize);
	    view.setPreserveRatio(true);
	    label.setGraphic(view);
	    flags--;
	}
	
	
	/**
	 * This private helper method creates an animation object and is placed 
	 * inside a label or tile. The animation is played within the label. This
	 * is meant to represent that a mine exploded. 
	 * 
	 * @param label The tile that the animation will play on.
	 */
	private void createAnimation(Label label) {
		int squareSize = GRID_COORD_SIZE/gridSize;
		final ImageView imageView = new ImageView(IMAGE);
		imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, SPRITE_WIDTH, SPRITE_HEIGHT));
		
		final Animation animation = new ExplosionAnimation(
				imageView,
				Duration.millis(DURATION),
				SPRITE_COUNT, COLUMN_COUNT,
				OFFSET_X, OFFSET_Y,
				SPRITE_WIDTH, SPRITE_HEIGHT
		);
		
		imageView.setFitHeight(squareSize);
        imageView.setFitWidth(squareSize);
	    imageView.setPreserveRatio(true);
        label.setGraphic(imageView);
				
        animation.setCycleCount(1);
        animation.play();
	}
	
	/**
	 * This is a private helper method that updates the grid in the view
	 * based on the results from a move by the user or when a flag is placed. 
	 * 
	 * @param game An object that hold the current player's game state.
	 * @param mineHit A boolean that determines if a user hit a mine.
	 */
	private void updateGrid(GameState game, boolean mineHit) {
		SQUARE_STATUS[][] grid = game.getPlayerGrid();
		// Fills in the background of the rectangles before updating
		setBackTileColor();
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				SQUARE_STATUS locationStatus = grid[i][j];
				Label curLabel = gridRef[i][j];
				Color labelColor = (Color) curLabel.getBackground().getFills().get(0).getFill();
				
				
				// If a mine was hit, show the location of the mines.
				if (locationStatus == SQUARE_STATUS.MINE) {
					
					// Removes the flag in the gui.
					if (curLabel.getGraphic() != null) {
						curLabel.setGraphic(null);
					}
					
					if (mineHit) {
						// Sets the tile color to black and adds an animation to the tile. 
						curLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
						createAnimation(curLabel);
					}
					else {
						// Updates the color of the label based on the pattern on the grid.
						if (labelColor == DARK_TAN || labelColor == DARK_GREEN) {
							curLabel.setBackground(new Background(new BackgroundFill(DARK_GREEN, null, null)));
						} else {
							curLabel.setBackground(new Background(new BackgroundFill(LIGHT_GREEN, null, null)));
						}
						curLabel.setText("");
					}
					
				} else if (locationStatus == SQUARE_STATUS.GUESSED) {
					
					// Updates the color of the label based on the pattern on the grid.
					if (labelColor == DARK_TAN || labelColor == DARK_GREEN) {
						curLabel.setBackground(new Background(new BackgroundFill(DARK_TAN, null, null)));
					} else {
						curLabel.setBackground(new Background(new BackgroundFill(LIGHT_TAN, null, null)));
					}

					// 
					if (controller.getAdjacentMines(i, j) != 0) {
						curLabel.setTextFill(numToColors.get(controller.getAdjacentMines(i, j)));
						curLabel.setText(String.valueOf(controller.getAdjacentMines(i, j)));
					}
					//If no adjacent mines, mark that
					else {
						curLabel.setText("");
					}
				} else if (locationStatus == SQUARE_STATUS.UNGUESSED) {
					
					// Removes the flag in the gui.
					if (curLabel.getGraphic() != null) {
						curLabel.setGraphic(null);
					}
					
					// Updates the color of the label based on the pattern on the grid.
					if (labelColor == DARK_TAN || labelColor == DARK_GREEN) {
						curLabel.setBackground(new Background(new BackgroundFill(DARK_GREEN, null, null)));
					} else {
						curLabel.setBackground(new Background(new BackgroundFill(LIGHT_GREEN, null, null)));
					}
					curLabel.setText("");
				} else {
					// If the code gets here, the square status is either a flag or a flagmine.
					
					// If the user hit a mine and they had a flag in the incorrect position, set the color 
					// of the tile to red.
					if (mineHit) {
						if (curLabel.getGraphic() != null && locationStatus == SQUARE_STATUS.FLAGGED) {
							curLabel.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
						}
					}
					
					// Places a flag in a tile if the tile on the gui doesn't diplay it.
					if (curLabel.getGraphic() == null) {
						createFlagImage(curLabel);
						updateFlagCounter(curLabel);
					}
				}
			}
		}
	}
	
	/*
	 *  METHODS THAT SET UP THE STARTING MENU GUI:
	 */
	

	/**
	 * This is a private helper method that creates a button that will
	 * display at the start menu. Each menu shows what kind of difficult
	 * to set for the user.
	 *
	 * @param difficulty A String that represents the kind of message that
	 * will be put on the button.
	 * @param hbox A hbox that will hold the newly created button object.
	 * @param vbox A vbox that represents the root node of the node
	 * hierarchy.
	 */
	private void setDifficultyButton(String difficulty, HBox hbox, VBox vbox) {
		Button button = new Button(difficulty);
		button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		hbox.getChildren().add(button);


		// Event Handler - determines what button the user pressed and change the GUI
		button.setOnAction(e -> {
			vbox.getChildren().clear();
			if (button.getText().equals("Beginner")) {
				gridSize = 10;
				flags = 10;
				setGameGUI(vbox);
			} else if (button.getText().equals("Intermediate")) {
				gridSize = 20;
				flags = 40;
				setGameGUI(vbox);
			} else if (button.getText().equals("Expert")){
				gridSize = 30;
				flags = 100;
				setGameGUI(vbox);
			} else {
				setCustomGUI(vbox);
			}
		});
	}
	
	/*
	 *  METHODS THAT SET UP THE CUSTOM OPTION GUI:
	 */
	
	/**
	 * This private helper sets up the custom game option menu.
	 * 
	 * @param vbox The root node of the JavaFX hierarchy. 
	 */
	private void setCustomGUI(VBox vbox) {
		GridPane root = new GridPane();  
		root.setAlignment(Pos.CENTER);
		
		Label grid_size =new Label("Grid Size:"); 
		grid_size.setFont(Font.font("Times New Roman", FontWeight.BLACK, 
        		FontPosture.ITALIC, TEXT_FONT_SIZE-10));
		TextField tf1=new TextField();  
		
	    Label mines = new Label("Mines: ");
	    mines.setFont(Font.font("Times New Roman", FontWeight.BLACK, 
        		FontPosture.ITALIC, TEXT_FONT_SIZE-10));
	    TextField tf2=new TextField(); 
	    
	    Button b = new Button("Start");
	    b.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
	    
	    b.setOnAction(e -> {
	    	
	    	boolean valid = inputValid(tf1, tf2);
	    	if (valid) {
	    		vbox.getChildren().clear();
	    		setGameGUI(vbox);
	    	}
	    	
	    });
	    
	    root.addRow(0, grid_size, tf1);  
	    root.addRow(1, mines, tf2); 
	    
	    Label gridLimit = new Label("Grid Size Limits: 4x4 to 30x30");
	    gridLimit.setFont(Font.font("Times New Roman", FontWeight.BLACK, 
        		FontPosture.ITALIC, TEXT_FONT_SIZE));
	    
	    Label mineLimit = new Label("Mine Limits: Between 1/10th and 2/5th of the total number of squares");
	    mineLimit.setFont(Font.font("Times New Roman", FontWeight.BLACK, 
        		FontPosture.ITALIC, TEXT_FONT_SIZE));
	    
	    vbox.getChildren().addAll(gridLimit, mineLimit, root, b);  
		
	}
	
	/**
	 * This private method that checks if the input the user put for
	 * the custom option is valid or not. 
	 * 
	 * @param grid_size A text field that holds the grid size the user inputed. 
	 * @param mines A text field that holds the number of mines the user inputed.
	 * @return True if the user input is valid, false other wise. 
	 */
	private boolean inputValid(TextField grid_size, TextField mines) {
		String strGrid = grid_size.getText();
		int strGridLength = strGrid.length();
		String strMines = mines.getText();
		int strMinesLength = strMines.length();
		
		// If the user input nothing, return false.
		if (strGrid.equals("") || strMines.equals("")) {
			showAlert("Invalid Input: Didn't put anything.");
			return false;
		}
		
		// If the user input anything other than digits, return false
		for (int i = 0; i < strGridLength; i++) {
			char digit = strGrid.charAt(i);
			if (digit < '0' || digit > '9') {
				showAlert("Invalid Input: Only digits are allowed");
				return false;
			}
		}
		
		for (int i = 0; i < strMinesLength; i++) {
			char digit = strMines.charAt(i);
			if (digit < '0' || digit > '9') {
				showAlert("Invalid Input: Only digits are allowed");
				return false;
			}
		}
		
		int grid = Integer.valueOf(strGrid);
		int flags = Integer.valueOf(strMines);
		
		// If the grid size is less then 4 or greater than 50, return false.
		if (grid < MIN_GRID_SIZE || grid > MAX_GRID_SIZE) {
			showAlert("Invalid Grid Size.");
			return false;
		}
		
		// Minimum amount of mines the user can have: 1/10th of the number of squares.
		int minMines = (grid*grid)/10;
		
		// Maximum amount of mines the user can have: 2/5th of the number of squares.
		int maxMines = ((grid*grid)*2)/5;
		
		// If the number of mines is smaller or bigger than expected, return false.
		if (flags < minMines || flags > maxMines) {
			String message = "Invalid Mine Number:\n";
			message += "Number of mines for a " + grid + "x" + grid + " is between: " + minMines + " and " + maxMines;
			showAlert(message);
			return false;
		}
		
		gridSize = grid;
		this.flags = flags;
		return true;
	}
	
	
	/**
	 * This method creates an alert based on what the header 
	 * states and shows it to the user.
	 * 
	 * @param content a string representing that the content text would look like in the alert.
	 */
	private void showAlert(String content) {
		Alert a = null;
		String displayStr = content;
		if (content.contains("END")) {
			a = new Alert(AlertType.INFORMATION);
			if (content.contains("Winner")) {
				displayStr = " Good game! You Won! \n Press Enter or Ok to dismiss window"
						+ "\n Please launch again or choose from difficulty menu to play again";
			} else {
				displayStr = " Game Over! You Lose! \n Press Enter or Ok to dismiss window"
						+ "\n Please launch again or choose from difficulty menu to play again";
			}
			a.setTitle("Game Over!");
			a.setHeaderText("INFORMATION: Game Ended!");
		}
		else if (content.equals("ClassNotFoundException")) {
			a = new Alert(AlertType.ERROR);
			displayStr = " A ClassNotFoundException has occured! \n Press Enter or Ok to dismiss window" + 
			" \n Make sure a valid class has been serialized as gamestate!";
			a.setTitle("Exception");
			a.setHeaderText("EXCEPTION: ClassNotFoundException");
		}
		else if (content.equals("IOException")) {
			a = new Alert(AlertType.ERROR);
			displayStr = " An IOException has occured! \n Press Enter or Ok to dismiss window" + 
			" \n Make sure a save exists! \n Ensure read/write to home directory!";
			a.setTitle("Exception");
			a.setHeaderText("EXCEPTION: IOException");
		}
		else if (content.equals("INVALID GAMESTATE")) {
			a = new Alert(AlertType.WARNING);
			displayStr = " Cannot save before making first move! \n Press Enter or Ok to dismiss window" + 
			" \n Left click at least once before saving!";
			a.setTitle("Exception");
			a.setHeaderText("WARNING: INVALID GAMESTATE");
		}
		else if (content.contains("Invalid")){
			a = new Alert(AlertType.ERROR);
			a.setTitle("ERROR");
			a.setHeaderText("ERROR");
			
		}
		else if (content.equals("WRONG SIZE")) { 
			a = new Alert(AlertType.ERROR);
			a.setTitle("Wrong Size");
			a.setHeaderText("Cannot load a game of a different size");
		} else {
			a = new Alert(AlertType.ERROR);
			displayStr = " An error has occured! \n Press Enter or Ok to dismiss window";
			a.setTitle("ERROR");
			a.setHeaderText("ERROR");
		}
		a.setContentText(displayStr);
		a.showAndWait();
	}
	
	
	/*
	 *  METHODS THAT SET UP THE ACTUAL GAME GUI
	 */

	/**
	 * This is a private helper method that adds new nodes to the
	 * root node of the JavaFX Composite Hierarchy once the user selected
	 * a difficulty.
	 *
	 * @param vbox A vbox that represents the root node of the node
	 * hierarchy.
	 */
	private void setGameGUI(VBox vbox) {
		
		// The user will start with the same number of flags as there are mines.
		model = new MinesweeperModel(gridSize,flags);
		controller = new MinesweeperController(model);
		model.addObserver(this);
		gridRef = new Label[gridSize][gridSize];
		setUpColorMap();
		vbox.setAlignment(Pos.TOP_CENTER);
		addMenuBar(vbox);

		// Creates an hbox that stores the # of flags and
		HBox firstHBox = new HBox(HBOX_GAP);
		firstHBox.setAlignment(Pos.CENTER);
		
		createHeader(firstHBox);

		// Creates a grid and stores it into a hbox.
		HBox gridHbox = new HBox(HBOX_GAP);
		gridHbox.setAlignment(Pos.CENTER);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		createGrid(grid);
		gridHbox.getChildren().add(grid);

		// Add nodes to the root node which is in this case, the vbox.
		vbox.getChildren().addAll(firstHBox, gridHbox);

		// Event Handlers

		grid.setOnMouseMoved((MouseEvent m) -> showMoveSelection(m));

		grid.setOnMouseClicked((MouseEvent m) -> {
			// Determines which mouse button was pressed.
			// Primary - LMB and Secondary - RMB
			if (m.getButton() == MouseButton.PRIMARY) {
				makeMove(m);
			}
			if (m.getButton() == MouseButton.SECONDARY && controller.getMadeFirstMove()) {
				changeFlagPlacement(m);
			}
		});

	}

	/**
	 * This is a private helper method that creates all of the node objects that will
	 * be displayed at the top of the window such as the timer and the flag count.
	 *
	 * @param hbox A pane object that will hold all the various node objects described
	 * in the method description.
	 */
	private void createHeader(HBox hbox) {

		String imagename = "file:triangular-flag_1f6a9.png";
		ImageView flagImage = new ImageView(new Image(imagename));
		flagImage.setFitHeight(IMAGEVIEW_HEIGHT);
		flagImage.setFitWidth(IMAGEVIEW_WIDTH);

		Text flagCounter = new Text(0, 0, "" + flags);
		flagCounter.setFont(new Font(TEXT_FONT_SIZE));
		flagCounter.setTextAlignment(TextAlignment.CENTER);

		imagename = "file:pngwing.com.png";
		ImageView timerImage = new ImageView(new Image(imagename));
		timerImage.setFitHeight(IMAGEVIEW_HEIGHT);
		timerImage.setFitWidth(IMAGEVIEW_WIDTH);

		Text timeCounter = new Text(0, 0, "" + controller.getTime());
		timeCounter.setFont(new Font(TEXT_FONT_SIZE));
		timeCounter.setTextAlignment(TextAlignment.CENTER);

		hbox.getChildren().addAll(flagImage, flagCounter, timerImage , timeCounter);

	}


	/**
	 * This private method adds all the necessary functionalities need for the GUI
	 * to display a working counter that increments every second.
	 *
	 * @param label a label that will be used to move around the node hierarchy. 
	 */
	private void createTimer(Label label) {
		
		// Get the root of the JavaFX node hierarchy and update the flag counter.
	    GridPane grid = (GridPane) label.getParent();
	    HBox gridHbox = (HBox) grid.getParent();
		VBox root = (VBox) gridHbox.getParent();
		
		HBox hbox = (HBox) root.getChildren().get(1);
		Text timerCounter = (Text) hbox.getChildren().get(3);

		Timer time = new Timer();
		clock = time;
		
		// Runs command that increments the timer value periodically by using a thread.
	    clock.scheduleAtFixedRate(new TimerTask(){

        @Override
	    public void run() {
        	if (controller.getTime() <= LARGEST_NUMBER_COUNT && !controller.isGameOver()) {
        		timerCounter.setText("" + controller.getTime());
        		controller.incrementTime();
        		} else {
        			clock.cancel();
        		}
	          }
	        }, 0, PERIOD);
	    
	    

	}

	/**
	 * This private method sets up the main grid by placing label objects within the
	 * grid which represents the tiles that a user can select.
	 *
	 * @param grid A grid pane that will be used to create the grid.
	 */
	private void createGrid(GridPane grid) {
		int color = -1;
		int squareSize = GRID_COORD_SIZE/gridSize;
		int fontSize = squareSize - 10;
		for (int i = 0; i < gridSize; i++) {

			// For the first tile in the next row, make it have the exact same 
			// color as the last tile in the previous row if the grid size is even.
			if (gridSize % 2 == 0) {
				if (color == 0) {	
					color = 1;
				} else {
					color = 0;
				}
			}
			for (int j = 0; j < gridSize; j++) {
				// Creates a label that represents a single position
				// in the entire grid.
        		Label label = new Label();

        		label.setText("");
        		label.setFont(new Font(fontSize));

                label.setTextFill(Color.WHITE);
                label.setMaxWidth(squareSize);
                label.setMaxHeight(squareSize);
                label.setMinHeight(squareSize);
                label.setMinWidth(squareSize);
                label.setPrefHeight(squareSize); // Set the height of the label
                label.setPrefWidth(squareSize);  // Set the width of the label
                label.setAlignment(Pos.CENTER); // Aligns the label to the center of a row on the grid pane.

                // Alternates the color palette of the squares using a integer value.
                if (color == -1 || color == 0) {
                    label.setBackground(new Background(new BackgroundFill(LIGHT_GREEN, null, null)));
                    color = 1;
                } else {
                    label.setBackground(new Background(new BackgroundFill(DARK_GREEN, null, null)));
                    color = 0;
                }

                grid.add(label, i, j);
                gridRef[i][j] = label;

			}
		}

	}

	
	
	/*
	 *  METHODS THAT HANDLES ACTION EVENTS.
	 */
	
	/**
	 * This is a private method that is called when the game 
	 * difficulty changes mid game. Destroys the current timer and 
	 * have a new timer be created when the user makes the first move in 
	 * the next game.
	 * 
	 * 
	 */
	private void destroyTimer() {
		
		// If a clock exists, then cancel the thread.
		if (clock != null) {
			clock.cancel();
			clock = null;
		}
	}

	/**
	 * This private helper method adds a menu bar at the top of the screen
	 * once the game starts. This menu bar would give the user options to
	 * change the difficulty or save/load the game. Creates menu items and
	 * give them event handlers
	 *
	 * @param vbox A vbox that represents the root node of the node
	 * hierarchy.
	 */
	private void addMenuBar(VBox vbox) {
	
	
		// Displays the settings menu and its sub menus.
		Menu menu = new Menu("Settings");
	
		Menu difficultyMenu = new Menu("Change Difficulty");
		MenuItem beginner = new MenuItem("Beginner");
		MenuItem intermediate = new MenuItem("Intermediate");
		MenuItem expert = new MenuItem("Expert");
	
		Menu gameStateMenu = new Menu("Save/Load Game");
		MenuItem save = new MenuItem("Save");
		MenuItem load = new MenuItem("Load");
	
		// Event handlers for menu items.
	
		// Restart the game
		beginner.setOnAction(e -> {
			destroyTimer();
			changeDifficulty("Beginner", vbox);
			
		});
	
		intermediate.setOnAction(e -> {
			destroyTimer();
			changeDifficulty("Intermediate", vbox);
			
		});
	
		expert.setOnAction(e -> {
			destroyTimer();
			changeDifficulty("Expert", vbox);
		});
	
		// Load and save previous game states.
		save.setOnAction(e -> {
			if (!controller.getMadeFirstMove()) {
				showAlert("INVALID GAMESTATE");
			}
			try {
				if (!controller.isGameOver()) {
					controller.saveGameState();
				}
			} catch (IOException e1) {
				showAlert("IOException");
			}
		});
	
	
		load.setOnAction(e -> {
			try {
				if (!controller.isGameOver()) {
					//try to load, fail if wrong size
					if (!controller.loadGameState()) {
						showAlert("WRONG SIZE");
					}
					else
					{
						if (clock == null) {
							createTimer(gridRef[0][0]);
							}
					}
	
						
					
					
				}
			} catch (IOException e1) {
				showAlert("IOException");
			} catch (ClassNotFoundException e1) {
				showAlert("ClassNotFoundException");
			}
		});
	
	
		// Add menu items into menus and menus into the menubar.
	
		difficultyMenu.getItems().add(beginner);
		difficultyMenu.getItems().add(intermediate);
		difficultyMenu.getItems().add(expert);
	
		gameStateMenu.getItems().add(save);
		gameStateMenu.getItems().add(load);
	
		menu.getItems().add(difficultyMenu);
		menu.getItems().add(gameStateMenu);
		MenuBar menubar = new MenuBar();
		menubar.getMenus().add(menu);
		vbox.getChildren().add(menubar);
	}


	/**
	 * This private method acts as an event handler that restarts the
	 * entire game based on the difficulty the user chosen mid game.
	 *
	 * @param difficulty A String that represents the difficulty
	 * the user will be changing to.
	 * @param vbox A vbox that represents the root node of the node
	 * hierarchy.
	 */
	private void changeDifficulty(String difficulty, VBox vbox) {
		if (difficulty.equals("Beginner")) {
			gridSize = 10;
			flags = 10;
		} else if (difficulty.equals("Intermediate")) {
			gridSize = 20;
			flags = 50;
		} else {
			gridSize = 30;
			flags = 100;
		}
		vbox.getChildren().clear();
		setGameGUI(vbox);
	}

	/**
	 * This private method acts as an event handler that handles the situation
	 * where a mouse moves on the grid. This method changes the color of a
	 * specific tile on the grid based on where the mouse is. Shows where the
	 * user will make their move.
	 *
	 * @param m An object that represents a mouse event that resulted from the
	 * user moving their mouse on the displayed GUI.
	 */
	private void showMoveSelection(MouseEvent m) {
		int MouseX = (int) m.getX();
		int MouseY = (int) m.getY();
		int squareSize = GRID_COORD_SIZE/gridSize;
		int min_index = 0;
		int max_index = gridSize - 1;

		// Convert the mouse x and y coordinate into index values that are used to
		// index into the grid.
		int newX = Math.floorDiv(MouseX, squareSize);
		int newY = Math.floorDiv(MouseY, squareSize);

		// Prevents index out of bound exceptions.
		if (newX >= min_index && newX <= max_index && newY >= min_index && newY <= max_index) {

			// Fills in the background of every rectangle after each mouse moved event.
			setBackTileColor();

			Label curLabel = gridRef[newX][newY];
			Color labelColor = (Color) curLabel.getBackground().getFills().get(0).getFill();

			// Selection doesn't appear for any revealed tiles.
			if (!controller.isGameOver() && labelColor != DARK_TAN && labelColor != LIGHT_TAN) {
				curLabel.setBackground(new Background(new BackgroundFill(SELECTION_COLOR, null, null)));
			}
		}


	}
	

	/**
	 * This private method acts as an event handler that handles the
	 * situation where the user press the left mouse button. When a user
	 * clicks on the left mouse button on any of the unguessed tiles, a
	 * move has been made.
	 *
	 * @param m An object that represents a mouse event that resulted from the
	 * user clicking on the left mouse button.
	 */
	private void makeMove(MouseEvent m) {
		int MouseX = (int) m.getX();
		int MouseY = (int) m.getY();
		int squareSize = GRID_COORD_SIZE/gridSize;
		int min_index = 0;
		int max_index = gridSize - 1;

		// Convert the mouse x and y coordinate into index values that are used to
		// index into the grid.
		int newX = Math.floorDiv(MouseX, squareSize);
		int newY = Math.floorDiv(MouseY, squareSize);


		// Prevents index out of bound exceptions.
		if (newX >= min_index && newX <= max_index && newY >= min_index && newY <= max_index) {

			// Fills in the background of every rectangle after each mouse moved event.
			setBackTileColor();
			
			// 
			if (!controller.isGameOver()) {
				controller.makeMove(newX, newY);
				if (clock == null) {
					createTimer(gridRef[newX][newY]);
				}
			}

		}
	}
	
	/**
	 * This private helper method acts as an event handler that handles 
	 * the case where the user placed a flag on a specific tile. When the 
	 * user clicks on the right mouse button,a flag will be placed on a 
	 * specific tile. The user can take off a flag from a tile as well.
	 * 
	 * @param m An object that represents a mouse event that resulted from the 
	 * user clicking on the right mouse button.
	 */
	private void changeFlagPlacement(MouseEvent m) {
		int MouseX = (int) m.getX();
		int MouseY = (int) m.getY();
		int squareSize = GRID_COORD_SIZE/gridSize;
		int min_index = 0;
		int max_index = gridSize - 1;
		
		// Convert the mouse x and y coordinate into index values that are used to 
		// index into the grid. 
		int newX = Math.floorDiv(MouseX, squareSize);
		int newY = Math.floorDiv(MouseY, squareSize);
				
				
		// Prevents index out of bound exceptions.
		if (newX >= min_index && newX <= max_index && newY >= min_index && newY <= max_index) {
			
			Label curLabel = gridRef[newX][newY];
			
			if (!controller.isGameOver()) {
				
				// If a flag is already placed in the selected position, remove it. 
				// Otherwise, put a flag in that position.
				if (curLabel.getGraphic() != null) {
					controller.changeFlagPlacement(newX, newY);
					flags++;
					updateFlagCounter(curLabel);
				} else {
					
					if (flags != 0) {
					    // Place flag.
					    controller.changeFlagPlacement(newX, newY);
					}
				}
			}
		}
		
	}
	
	
	/*
	 *  MORE UTILITIY METHODS
	 */

	/**
	 * This is a private helper method that is called before changes occurred after
	 * a mouse moved event on the grid. It finds the square that has the unique
	 * tile selection color and changes the tile's color based on the color pattern
	 * of the grid.
	 *
	 */
	private void setBackTileColor() {
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {

				// Checks if the current label color was the selection color.
				Label curLabel = gridRef[i][j];
				Color labelColor = (Color) curLabel.getBackground().getFills().get(0).getFill();
				if (labelColor == SELECTION_COLOR) {
					int prevIndex = j - 1;
					int nextIndex = j + 1;
					Color adjacentColor = null;

					// Checks the color of one of the current tile's adjacent tile. Handle
					// index out of bound exception by checking if the previous index is >= 0.
					if (prevIndex >= 0) {
						Label prevLabel = gridRef[i][prevIndex];
						adjacentColor = (Color) prevLabel.getBackground().getFills().get(0).getFill();
					} else {
						Label nextLabel = gridRef[i][nextIndex];
						adjacentColor = (Color) nextLabel.getBackground().getFills().get(0).getFill();
					}

					// Changes the color of the current tile based on the color of the neighboring tile
					if (adjacentColor == LIGHT_GREEN || adjacentColor == LIGHT_TAN) {
						curLabel.setBackground(new Background(new BackgroundFill(DARK_GREEN,
								null, null)));
					} else {
						curLabel.setBackground(new Background(new BackgroundFill(LIGHT_GREEN,
								null, null)));
					}
				}

			}
		}

	}
	
	
	
	/**
	 * This private helper method initializes a HashMap that maps
	 * integers to colors. This HashMap would be used to give color
	 * to the adjacency values in the squares. 
	 * 
	 */
	private void setUpColorMap() {
		numToColors = new HashMap<Integer, Color>();
		numToColors.put(1, Color.BLUE);
		numToColors.put(2, Color.GREEN);
		numToColors.put(3, Color.RED);
		numToColors.put(4, Color.PURPLE);
		numToColors.put(5, Color.MAROON);
		numToColors.put(6, Color.DARKTURQUOISE);
		numToColors.put(7, Color.BLACK);
		numToColors.put(8, Color.GRAY);
		
	}
	
	

}
