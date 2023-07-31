package game;


import game.Definitions;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *Class representing the instructions menu of the game.
 *@author DA Chan
 *@version PX 
 */
public class Instructions extends Scene{

    //Main VBox of the Scene where text will be added to
    private static VBox vBox = new VBox(40);

    /**
     * Default constructor
     */
    public Instructions() {
        super(vBox, Definitions.GAME_WIDTH, Definitions.GAME_HEIGHT);
        initialize();
    }

    /**
     * Method to initialize the text information and add to VBox
     */
    private void initialize() {
        Text title = new Text("Instructions");
        title.setFont(new Font("Lucida Bright", 50));
        title.setFill(Color.WHITE);

        Text about = new Text("Welcome warrior! You must must navigate through hostile Knights on the map until reaching the end of the level.");
        about.setFont(new Font("Lucida Bright", 22));
        about.setFill(Color.WHITE);

        Text controls = new Text("The Controls are: Press A to move left, D to move right, SPACE to jump and ENTER to throw your Knives.");
        controls.setFont(new Font("Lucida Bright", 22));
        controls.setFill(Color.WHITE);

        Text hints = new Text("Your Knives will kill the enemies in 1 hit! The enemies will also throw their knives at you!" +
                " \nYou will die if you get hit by enemy knives. You can also die from touching the enemy!");
        hints.setFont(new Font("Lucida Bright", 22));
        hints.setFill(Color.WHITE);

        Text leaveHelp = new Text("Click on the screen to go back to the main menu.");
        leaveHelp.setFont(new Font("Lucida Bright", 22));
        leaveHelp.setFill(Color.WHITE);

        //Background image for Instructions page
        ImagePattern imagePattern = new ImagePattern(new Image("file:dark background.png"));

        vBox.setBackground(new Background(new BackgroundFill(imagePattern, CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.getChildren().addAll(title, about, controls, hints, leaveHelp);

    }

}
