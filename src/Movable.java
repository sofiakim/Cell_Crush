import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public interface Movable
{
	/**
	 * Translates this Movable object by the difference between the given
	 * initial and final positions. Used to move an Object when dragging it
	 * given the initial and final mouse positions during the drag.
	 * 
	 * @param initialPos
	 *            the initial position
	 * @param finalPos
	 *            the final position
	 */
	public void drag(Point initialPos, Point finalPos);

	/**
	 * Draws this Object in its current position in the given Graphics context
	 * 
	 * @param g
	 *            the Graphics context to draw the Hand in
	 */
	public void draw(Graphics g);

	public void setStart();
	
	public void moveTo(int x,int y);

}
