package game.model;

import game.*;

/**
 * Class which implements each of the visit operations
 * 
 * @author DA Chan
 * @version PX
 */

public class ConcreteVisitor extends Sprite implements CollisionVisitor {
	
	//Number of times Enemy is hit by Player's knives
	private int hits; 
	
	/**
     * Default Constructor
     * @param xAxis X coordinate of sprite
     * @param yAxis Y coordinate of sprite
     * @param level Level being used to reference information
     * @param fileName File name of the image
     */
	ConcreteVisitor(double xAxis, double yAxis, Level level, String fileName) {
		super(xAxis, yAxis, level, fileName);
		
	}
	
	/**
     * Method to kill Player if Player collides with an Enemy
     * @param level Level that enemies are stored in
     */
	@Override
	public void collisionWithEnemy(Level level) {
		for (Enemy enemy : level.getEnemies()) {
            if (intersects(enemy.getLayoutBounds())) {
            	kill();
            }
        }		
	}

	 /**
     * Method to kill Player if Player collides with an enemy knife
     * @param level Level that enemy Knives are stored in
     */
	@Override
	public void collisionWithEnemyKnife(Level level) {
		for (EnemyKnives enemyKnives : level.getEnemyKnives()) {
            if (intersects(enemyKnives.getLayoutBounds())) {
                kill();
            }
        }		
	}

	/**
     * Method to add hits if Enemy collides with a Player's 
     * @param level Level that Player's knives are stored in
     */
	@Override
	public void knifeCollision(Level level) {
		for (Knives knives : level.getKnives()) {
            if (intersects(knives.getLayoutBounds())) {
                hits++;
            }
        }
		
	}

	/**
     * Method to kill EnemyKnives if it collides with a Platform
     */
	@Override
	public void collisionWithPlatforms() {
		for (Platform platform : getLevel().getPlatforms()) {
            if (intersects(platform.getLayoutBounds())) {
                kill();
            }
        }
		
	}

}
