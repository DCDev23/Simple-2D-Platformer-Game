package game;


import game.Level;
import game.Platform;
import game.Definitions;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author DA Chan
 * @version PX
 * Superclass for all sprites in the game. 
 * Allows certain actions to be performed.
 */
public class Sprite extends ImageView {

    // Image of sprite
    private Image image;

    // Basic attributes of sprite
    private boolean canJump, isJumping;
    private boolean gravityOn;
    private boolean alive;

    // Sprite performs actions based on level
    private Level level;

    // Max change in yAxis for jumping
    private static final int DELTA_Y = 10;

    // Current change in yAxis for jumping
    private double measureY;

    /**
     * Method to create a sprite at x and y axis' with respect to level, and create an image based on the fileName
     * @param xAxis X coordinate of sprite
     * @param yAxis Y coordinate of sprite
     * @param level Level being used to reference information
     * @param fileName File name of the image
     */
    protected Sprite(double xAxis, double yAxis, Level level, String fileName) {
        setX(xAxis);
        setY(yAxis);

        this.level = level;

        this.image = new Image(fileName);
        setImage(image);

        gravityOn = true;
        measureY = 0;

        alive = true;

        isJumping = false;

    }

    /**
     * Method to move sprite left
     */
    public void moveLeft() {
        setX(getX() - Definitions.PLAYER_SPEED);

        // Makes sure sprite doesn't pass through the platform
        for (Platform platform : level.getPlatforms()) {
            Bounds bounds = platform.getLayoutBounds();
            if (intersects(bounds)) {
                //make sure sprite stays right of the bound
                setX(bounds.getMaxX() + 0.01);
            }
        }

        // Makes sure sprite doesn't go offscreen
        if (getX() < 0) {
            setX(0);
        }
    }

    /**
     * Method to move sprite right
     */
    public void moveRight() {
        setX(getX() + Definitions.PLAYER_SPEED);

        // Makes sure sprite doesn't pass through the platform
       for (Platform platform : level.getPlatforms()) {
            Bounds bounds = platform.getLayoutBounds();
            if (intersects(bounds)) {
                //Make sure sprite stays left of the bound
                setX(bounds.getMinX() - this.getImage().getWidth() - 0.01);
            }
        }
    }

    /**
     * Method to update gravity affecting the sprite
     */
    public void updateGravity() {
        if (gravityOn) {
            setY(getY() + Definitions.GRAVITY);

            // Makes sure sprite doesn't pass through the platform
            for (Platform platform : level.getPlatforms()) {
                Bounds bounds = platform.getLayoutBounds();
                if(intersects(bounds)) {
                    //make sure the sprite stays above the bound
                    setY(bounds.getMinY() - this.getImage().getHeight() - 0.01);
                    //The sprite can only jump if it is touching the ground
                    canJump = true;
                }
            }
        }
    }

    /**
     * Method to update jump information for the sprite
     */
    public void updateJump() {

        //Makes sprite move up as long as it hasn't reached DELTA_Y
        if (isJumping && measureY < DELTA_Y) {
            setY(getY() - Definitions.PLAYER_JUMP_SPEED);
            measureY += 0.75;
        } else {
            //If sprite reaches DELTA_Y turns gravity on
            gravityOn = true;
            isJumping = false;
        }
    }

    /**
     * Method to make sprite jump
     */
    public void jump() {

        if (canJump) {
            //Sets attributes to handle gravity
            gravityOn = false;
            isJumping = true;
            canJump = false;
            measureY = 0;
        }
    }

    /**
     * Method to kill sprite
     */
    public void kill() {
        alive = false;
    }

    //Accessors

    public Level getLevel() {
        return level;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public Image getInitImage() {
        return image;
    }
}
