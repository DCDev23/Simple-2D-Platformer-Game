
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import game.*;

/**
 * Main class that handles all interactions between classes and objects.
 * Launches the game.
 * @author DA Chan
 * @version PX
 */
public class Main extends Application {
	

    // Main timeline for animations
    private Timeline timeline;

    // Main stage
    private Stage stage;

    // Scene for instructions
    private Instructions instructions;

    // Group for main menu
    private Group root;

    // Level being displayed
    private Level level;

    // Current level number
    private int levelNumber;

    // Main player object
    private Player player;

    // Keys being pressed
    private HashSet<KeyCode> pressedKeys;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method which initializes everything and loads the main menu
     * @param primaryStage Main stage
     */
    @Override
    public void start(Stage primaryStage) {


        stage = primaryStage;

        pressedKeys = new HashSet<>();

        levelNumber = 1;

        instructions = new Instructions();

        displayMainMenu();

    }
    

    /**
     * Method for starting the game and loading the level
     * @param levelNum Level that is being played
     */
    private void play(int levelNum) {
        generateLevel(levelNum);

        // Initialize timeline and update the game every time the timeline refreshes
        timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> update())
        );

        // Sets timeline to run indefinitely
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Method which updates all sprites and objects
     */
    private void update() {
        setKeyPresses();
        player.updateAll(level);
        updateLevelSprites();

        // If player passes a level
        if (player.getX() + player.getFitWidth() >= level.getWidth()) {
            timeline.stop();
            if (levelNumber <= 2) {
                //Clearing levels and HashSets, stopping timeline and 
            	//displaying the end screen
                timeline.stop();
                level.getChildren().clear();
                level.setLayoutX(0);
                pressedKeys.clear();
                levelNumber = 1;
                finishGame();
            }
        }
    }

    /**
     * Method to update all sprites in the level
     */
    private void updateLevelSprites() {

        //Move all Knives in level
        for (Knives knives : level.getKnives()) {
            knives.move();
        }
        //Update all enemies in level
        for (Enemy enemy : level.getEnemies()) {
            enemy.updateAll(level);
        }
        //Update all enemy Knives in level
        for (EnemyKnives enemyKnives : level.getEnemyKnives()) {
            enemyKnives.move();
        }

        removeDeadSprites();
    }

    /**
     * Method which removes all dead Sprites from level
     */
    private void removeDeadSprites() {
        
        List<Enemy> deadEnemies = new ArrayList<Enemy>();
        List<Knives> deadKnives = new ArrayList<Knives>();
        List<EnemyKnives> deadEnemyKnives = new ArrayList<EnemyKnives>();

        // Checks for dead sprites in level and adds to respective dead lists
        for (Enemy enemy : level.getEnemies()) {
            if (!enemy.isAlive()) deadEnemies.add(enemy);
        }
        for (Knives knives : level.getKnives()) {
            if (!knives.isAlive()) deadKnives.add(knives);
        }
        for (EnemyKnives enemyKnives : level.getEnemyKnives()) {
            if (!enemyKnives.isAlive()) deadEnemyKnives.add(enemyKnives);
        }

        // Removes dead sprites from level and lists in level
        level.getEnemies().removeAll(deadEnemies);
        level.getChildren().removeAll(deadEnemies);

        level.getKnives().removeAll(deadKnives);
        level.getChildren().removeAll(deadKnives);

        level.getEnemyKnives().removeAll(deadEnemyKnives);
        level.getChildren().removeAll(deadEnemyKnives);

        //If player is dead, clear level and HashSet, stop timeline,
        //reset game progress (change level to first level), reset "camera" (the panning of the level) and show main menu
        if (!player.isAlive()) {
            level.getChildren().clear();
            level.setLayoutX(0);
            //level = null;
            timeline.stop();
            displayMainMenu();
            pressedKeys.clear();
            levelNumber = 1;
        }

    }

    /**
     * Method which shows the end game scene when the game is finished
     */
    private void finishGame() {
        Group endDisplay = new Group();

        Scene endScene = new Scene(endDisplay, Definitions.GAME_WIDTH, Definitions.GAME_HEIGHT);

        endDisplay.getChildren().add(new ImageView(new Image("file:EndScreen.jpg")));

        // Sets mouse click event so that user can return to main menu
        endScene.setOnMouseClicked(event -> displayMainMenu());

        stage.setScene(endScene);
        stage.show();
    }

    /**
     * Method to display the main menu 
     */
    private void displayMainMenu() {

        root = new Group();

        root.getChildren().add(new ImageView(new Image("file:GameMenu.png")));

        Scene mainMenu = new Scene(root, Definitions.GAME_WIDTH, Definitions.GAME_HEIGHT, Color.rgb(51, 51, 51));

        displayButtons();

        stage.setScene(mainMenu);
        stage.show();
    }

    /**
     * Method to add buttons to main group
     */
    private void displayButtons() {

        VBox box = new VBox(36);

        generateMainMenuButtons(box);

        box.setTranslateY(Definitions.GAME_HEIGHT/2);
        box.setTranslateX(Definitions.GAME_WIDTH/2 - Definitions.BUTTONWIDTH/2);

        root.getChildren().add(box);
    }

    /**
     * Method which creates buttons for the main menu with onclick events
     * @param vbox The VBox that the buttons are being added to
     */
    private void generateMainMenuButtons(VBox vbox) {

        Button play = new Button("Play");
        play.setPrefSize(Definitions.BUTTONWIDTH, Definitions.BUTTONHEIGHT);

        play.setOnMouseClicked(event -> play(levelNumber));

        Button instructions = new Button("Instructions");
        instructions.setPrefSize(Definitions.BUTTONWIDTH, Definitions.BUTTONHEIGHT);

        instructions.setOnMouseClicked(event -> displayInstructions());
        
        Button load = new Button("Load");
        load.setPrefSize(Definitions.BUTTONWIDTH, Definitions.BUTTONHEIGHT);
        
        load.setOnMouseClicked(event -> {
			// Instantiating a FileChooser and setting its initial directory.
			final FileChooser chooser = new FileChooser();
			chooser.setInitialDirectory(new File("data"));
			File file = chooser.showOpenDialog(null);
		});

        vbox.getChildren().addAll(play, instructions, load);

    }

    /**
     * Displays the instructions menu
     */
    private void displayInstructions() {

        //Onclick event for returning to the main menu
        instructions.setOnMouseClicked(event -> displayMainMenu());

        stage.setScene(instructions);
        stage.show();
    }

    /**
     * Method which creates a level according to the level number
     * @param levelNumber Number that references a level
     */
    private void generateLevel(int levelNumber) {
        this.level = new Level(levelNumber, Definitions.GAME_HEIGHT);

        this.player = new Player(100, 0, level);

        level.getChildren().add(player);

        Scene s = new Scene(level, Definitions.GAME_WIDTH, Definitions.GAME_HEIGHT);

        s.setFill(new ImagePattern(new Image("file:background6.jpg")));

        //Set key press events and add them to HashSet
        s.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        s.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

        stage.setScene(s);
        stage.show();
    }

    /**
     *Method for player controls based on input from HashSet
     */
    private void setKeyPresses() {
        if (pressedKeys.contains(KeyCode.A)) {player.moveLeft(); moveLevel();}
        if (pressedKeys.contains(KeyCode.D)) {player.moveRight(); moveLevel();}
        if (pressedKeys.contains(KeyCode.SPACE)) player.jump();
        if (pressedKeys.contains(KeyCode.ENTER)) {player.throwKnife(level);}
    }

    /**
     * Pans level based on the location of the player
     */
    private void moveLevel() {
        //Keep the "camera" at a third of the screen
        double shift = Definitions.GAME_WIDTH / 3;

        //Starts moving camera when players reaches the "first third" of the screen in the beginning of the level
        if (!(player.getX() < shift)) {
            level.setLayoutX(-1 * player.getX() + shift);
            double min = -1 * level.getWidth() + Definitions.GAME_WIDTH;
            if (level.getLayoutX() < min) {
                level.setLayoutX(min);
            }
        }
    }
}

