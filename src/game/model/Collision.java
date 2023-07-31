package game.model;

/**
 * Class which provides an interface for attaching and detaching Observer objects
 * Contains Notify operation
 * @author DA Chan
 * @version PX
 */
interface Collision {
	public void AddObserver(Observer ob);
	public void RemoveObserver(Observer ob);
	public void Notitfy();
}
