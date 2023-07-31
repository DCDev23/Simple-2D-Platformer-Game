package game;


import game.Level;
import game.Platform;
import game.Definitions;

/**
 * Class representing Knives that Enemies throw.
 * @author DA Chan
 * @version PX
 */
public class EnemyKnives extends Sprite{

    /**
     * Method to create EnemyKnives at x and y coordinates
     * @param xAxis X coordinate of EnemyKnives
     * @param yAxis Y coordinate of EnemyKnives
     * @param level Level being used to reference information
     */
    EnemyKnives(double xAxis, double yAxis, Level level) {
        super(xAxis, yAxis, level, "file:shuriken.gif");
    }

    /**
     * Method to move EnemyKnives
     */
    public void move() {
        setX(getX() - Definitions.KNIFE_SPEED);
        collisionWithPlatforms();
    }

    /**
     * Method to remove EnemyKnives if it collides with a Platform
     */
    public void collisionWithPlatforms() {
        for (Platform platform : getLevel().getPlatforms()) {
            if (intersects(platform.getLayoutBounds())) {
                kill();
            }
        }
    }

}
