package game;


import game.Enemy;
import game.Level;
import javafx.scene.image.Image;

/**
 * @author DA Chan
 * @version PX
 * Class representing the player-controlled character of the game. 
 * Player jumps, moves and throws knives
 */
public class Player extends Sprite{

    // Image for when the Player is jumping
    private Image jumpingImage;

    /**
     * Method to create a Player at x and y coordinates, according to the level
     * @param xAxis X coordinate of player
     * @param yAxis Y coordinate of player
     * @param level Level being used to reference information
     */
    public Player(double xAxis, double yAxis, Level level) {
        super(xAxis, yAxis, level, "file:player.png");
        jumpingImage = new Image("file:player.png");
    }

    /**
     * Method to throw Knife
     * @param level Level to which Knife is added to keep track of
     */
    public void throwKnife(Level level) {
        Knives knives = new Knives(getX() + getImage().getWidth() - 20, getY() + 10, getLevel());
        level.getKnives().add(knives);
        level.getChildren().add(knives);
    }

    /**
     * Method to kill Player if Player collides with an enemy knife
     * @param level Level that enemy Knives are stored in
     */
    public void collisionWithEnemyKnife(Level level) {
        for (EnemyKnives enemyKnives : level.getEnemyKnives()) {
            if (intersects(enemyKnives.getLayoutBounds())) {
                kill();
            }
        }
    }

    /**
     * Method to kill Player if Player collides with an Enemy
     * @param level Level that enemies are stored in
     */
    public void collisionWithEnemy(Level level) {
        for (Enemy enemy : level.getEnemies()) {
            if (intersects(enemy.getLayoutBounds())) {
                kill();
            }
        }
    }

    /**
     * Method to update the image of Player if Player is jumping
     */
    private void updateJumpAnimation() {
        if (isJumping()) {
            setImage(jumpingImage);
        } else {
            setImage(getInitImage());
        }
    }

    /**
     * Method to update all attributes of Player
     * @param level Level used to reference certain information
     */
    public void updateAll(Level level) {
        updateGravity();
        updateJump();
        updateJumpAnimation();
        collisionWithEnemyKnife(level);
        collisionWithEnemy(level);
    }

}
