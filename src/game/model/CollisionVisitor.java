package game.model;

import game.Enemy;
import game.Level;

/**
 * Class which contains the CollisionVisitor interface
 * 
 * @author DA Chan
 * @version PX
 */
public interface CollisionVisitor {
	//Collision abstract methods
	public void collisionWithEnemy(Level level);
	public void collisionWithEnemyKnife(Level level);
	public void knifeCollision(Level level);
	public void collisionWithPlatforms();
}
