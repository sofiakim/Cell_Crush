import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * This class keeps track of the property of the cells
 * 
 * @author Sofia Kim, Tracy Lei, Jessie Ma
 * @version June 2013
 */
public class Cell extends Rectangle implements Movable
{
	private int cellType;

	private boolean isGlowing;

	private boolean hasTwoMembranes;

	private boolean hasOneMembrane;

	private Image image;

	private Image glowingImage;

	private Image twoMembranes;

	private Image oneMembrane;

	private Image oneMembraneGlowing;

	private Image twoMembranesGlowing;

	private Point start;

	private boolean hidden;

	public Cell(int cellType, int x, int y)
	{
		// Set up the underlining Rectangle
		// our images are 20x20 pixels
		super(x, y, 40, 40);

		this.cellType = cellType;
		this.isGlowing = false;
		// Load up the appropriate image file for this cell
		String imageFile = "images\\" + cellType + ".png";
		image = new ImageIcon(imageFile).getImage();

		// Load up image for glowing cell (occurs when the cell is selected and
		// when it is being crushed)
		String glowingImageFile = "images\\" + (cellType + 10) + ".png";
		glowingImage = new ImageIcon(glowingImageFile).getImage();

		// Image for one membrane around the cell
		String oneMembraneImageFile = "images\\" + (cellType + 20) + ".png";
		oneMembrane = new ImageIcon(oneMembraneImageFile).getImage();

		// Image for one membrane around the cell
		String twoMembraneImageFile = "images\\" + (cellType + 30) + ".png";
		twoMembranes = new ImageIcon(twoMembraneImageFile).getImage();

		// Image for one membrane around the cell
		String oneMembraneGlowingFile = "images\\" + (cellType + 40) + ".png";
		oneMembraneGlowing = new ImageIcon(oneMembraneGlowingFile).getImage();

		// Image for one membrane around the cell
		String twoMembraneGlowingFile = "images\\" + (cellType + 50) + ".png";
		twoMembranesGlowing = new ImageIcon(twoMembraneGlowingFile).getImage();

		hidden = false;
	}

	public void hide()
	{
		hidden = true;
	}

	public boolean isDead()
	{
		return hidden;
	}

	/**
	 * Draws a card in a Graphics context
	 * 
	 * @param g
	 *            Graphics to draw the card in
	 */
	public void draw(Graphics g)
	{
		if (hidden)
			return;
		// Draws the glowing image of the cell or the image with the membranes
		// around the cell
		if (isGlowing)
		{
			if (hasTwoMembranes)
				g.drawImage(twoMembranesGlowing, x, y, null);
			else if (hasOneMembrane)
				g.drawImage(oneMembraneGlowing, x, y, null);
			else
				g.drawImage(glowingImage, x, y, null);
		}
		else if (hasTwoMembranes)
			g.drawImage(twoMembranes, x, y, null);
		else if (hasOneMembrane)
			g.drawImage(oneMembrane, x, y, null);
		else
			g.drawImage(image, x, y, null);

	}

	/**
	 * Checks if this cell is the same type as another cell
	 * 
	 * @param cell
	 *            the other Cell
	 * @return true if they are the same, false if different
	 */
	public boolean isSameType(Cell cell)
	{
		if (this.cellType == cell.cellType)
			return true;
		return false;
	}

	/**
	 * Tells what type the cell is
	 * 
	 * @return the type of cell
	 */
	public int getCellType()
	{
		return cellType;
	}

	/**
	 * 
	 * @param noOfMembranes
	 */
	public void setMembrane(int noOfMembranes)
	{
		if (noOfMembranes == 1)
			hasOneMembrane = true;
		else if (noOfMembranes == 2)
			hasTwoMembranes = true;
	}

	/**
	 * 
	 */
	public void setStart()
	{
		start = new Point(x, y);
	}

	public Point getPoint()
	{
		Point newPoint = new Point(x, y);
		return newPoint;
	}

	@Override
	public void drag(Point initialPos, Point finalPos)
	{
		// TODO Auto-generated method stub
		// Only drags up to one square around diagonally
		int xShift = finalPos.x - start.x;
		int yShift = finalPos.y - start.y;
		
		if (xShift <= 60 && xShift >= -40 && yShift <= 60 && yShift >= -40)
		{
			translate(finalPos.x - initialPos.x, finalPos.y - initialPos.y);
		}

	}


	@Override
	public void moveTo(int x, int y)
	{
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;

	}

}
