package game.model;
/**
 * Concrete class which implements an accept operation
 * @author DA Chan
 * @version PX
 */
public interface ICollidable {
	public void accept(CollisionVisitor visitor);
}
