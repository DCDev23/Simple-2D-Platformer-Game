package game;


import java.util.Random;

import game.Level;

/**
 * Class representing enemy of the game. Throws enemy knives in attempt to kill Player
 * @author DA Chan
 * @version PX
 */
public class Enemy extends Sprite{

    //Used to determine if enemy will throw enemy knives
    private Random random;
    private static int NUMBER = 1;

    //Number of times Enemy is hit by Player's knives
    private int hits;

    /**
     * Creates an Enemy at x and y coordinates, according to the level
     * @param xAxis X coordinate of Enemy
     * @param yAxis Y coordinate of Enemy
     * @param level Level being used to reference information
     */
    Enemy(double xAxis, double yAxis, Level level) {
        super(xAxis, yAxis, level, "file:swordsman.png");
        random = new Random();
        hits = 0;
    }

    /**
     * Method to throw an enemy knife
     * @param level Level to which enemy knife is added to keep track of
     */
    public void throwKnives(Level level) {
        // Throws a knife "randomly"
        // Random was used since this project doesn't use time as a unit for calculations
        if (random.nextInt(100) == NUMBER) {
            EnemyKnives enemyKnives = new EnemyKnives(getX() + getImage().getWidth() - 20, getY() + 10, getLevel());
            level.getEnemyKnives().add(enemyKnives);
            level.getChildren().add(enemyKnives);
           
        }
    }

    /**
     * Method to add hits if Enemy collides with a Player's Knives
     * @param level Level that Player's knives are stored in
     */
    private void knifeCollision(Level level) {
        for (Knives knives : level.getKnives()) {
            if (intersects(knives.getLayoutBounds())) {
                hits++;
            }
        }
    }

    /**
     * Method to kill Enemy in one or more hits
     */
    private void updateAliveStatus() {
        if (hits > 1) kill();
    }

    /**
     * Method to update all attributes of an Enemy
     * @param level Level used to reference certain information
     */
    public void updateAll(Level level) {
        throwKnives(level);
        knifeCollision(level);
        updateAliveStatus();
        updateGravity();
    }

}
