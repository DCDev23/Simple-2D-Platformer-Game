package game;


import game.Enemy;
import game.Level;
import game.Platform;
import game.Definitions;

/**
 * Class representing Knives that the Player throws.
 * @author DA Chan
 * @version PX
 */
public class Knives extends Sprite{

    /**
     * Method to create Knives at x and y coordinates
     * @param xAxis X coordinate of Knives
     * @param yAxis Y coordinate of Knives
     * @param level Level being used to reference information
     */
    Knives(double xAxis, double yAxis, Level level) {
        super(xAxis, yAxis, level, "file:shuriken.gif");
    }

    /**
     * Method to move Knives
     */
    public void move() {
        setX(getX() + Definitions.KNIFE_SPEED);
        collisionWithPlatforms();
        collisionWithEnemy();
    }

    /**
     * Method to remove Knives if they collide with a Platform
     */
    public void collisionWithPlatforms() {
        for (Platform platform : getLevel().getPlatforms()) {
            if (intersects(platform.getLayoutBounds())) {
                kill();
            }
        }
    }

    /**
     * Method to remove Knives if they collide with Enemy
     */
    public void collisionWithEnemy() {
        for (Enemy enemy : getLevel().getEnemies()) {
            if (intersects(enemy.getLayoutBounds())) {
                kill();
            }
        }
    }
}
