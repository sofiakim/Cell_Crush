import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class keeps track of all the important things within the game about
 * stuff .... MUST ELABORATE SOMETIME LATER
 * 
 * @author Sofia Kim, Tracy Lei, Jessie Ma
 * @version June 2013
 */
public class CellCrush extends JPanel
{

	private Movable selectedItem;
	
	private RankingScore score;

	private Point lastPoint;

	private CellCrushBoard myBoard;


	final static int ANIMATION_FRAMES = 3;

	private Point selectedPoint;
	
	private int animated;
	
	private Image background;

	public CellCrush() throws FileNotFoundException
	{
		// Creates a new CellCrush Board
		myBoard = new CellCrushBoard(1);
		score = new RankingScore();

		// Set up the size and background colour
		setPreferredSize(new Dimension(500, 600));
		this.setBackground(new Color(250, 250, 250));
		background = new ImageIcon("images\\background.png").getImage();

		// Add mouse listeners to the table panel
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseMotionHandler());
		
		animated = -1;
	}

	/**
	 * Creates a new cell crush game
	 */
	public void newGame()
	{
		myBoard.shuffle();
		myBoard.setMoves();
		myBoard.newScore();
		repaint();
	}

	/**
	 * Checks if the game has ended
	 */
	public boolean isGameOver()
	{
		if (myBoard.getMovesLeft() == 0)
			return true;
		return false;
	}

	/**
	 * Displays a closing message when the game ends
	 */
	public void gameOver() 
	{
		// Displays a congratulations message when there is a victory
		repaint();
		try
		{
			score.arrangeRankings(myBoard.getScore());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		JOptionPane.showMessageDialog(this,
				"Congratulations! You completed this level with a score of " +
						myBoard.getScore () + "!\nPress \"New Game\" to play again!",
				"Congratulations", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Displays the top five scores
	 */
	public void displayHighScores()
	{
		JOptionPane.showMessageDialog(this, score.toString(),
				"Cell Crush High Scores", JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * Displays the graphics
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage (background, 0, 0, this);
		myBoard.draw(g);

		// Displays the score and the number of moves left
		setFont(new Font("Times New Roman", Font.BOLD, 24));
		g.setColor(Color.BLACK);
		g.drawString("Number of moves left: " + myBoard.getMovesLeft(), 105,
				570);
		g.drawString ("Score: " + myBoard.getScore (), 350, 40);
		
		if(animated==0){
			
			if(myBoard.hasHoles()){
				myBoard.fill();
				animated = 50;
			}else {
				
				if(myBoard.hasValidMoves()){
					
					myBoard.update();
					animated = 50;
					
				}else{
					animated = -1;
					if (isGameOver())
						gameOver();
				}
			}
			
			
		}
		if(animated>0){
			animated--;
		}

	}

	/**
	 * A simple method to delay
	 * 
	 * @param msec
	 *            the time to delay
	 */
	private void delay(int msec)
	{
		try
		{
			Thread.sleep(msec);
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * Inner class to handle mouse events
	 * 
	 */
	private class MouseHandler extends MouseAdapter
	{
		/**
		 * Events that occur when mouse is pressed
		 */
		public void mousePressed(MouseEvent event)
		{
			selectedPoint = event.getPoint();
			if (myBoard.canPickUp(selectedPoint))
				selectedItem = myBoard.pickUp(selectedPoint);
			if (selectedItem != null)
			{
				selectedItem.setStart();
			}

			// If the move is not valid, we want the cells
			// to go back to the way they used to be
			// This ensures that the cell only moves up/down or left/right
			int point = myBoard.indexOfLoc(selectedPoint);
			lastPoint = myBoard.pointAtIndex(point);
			// System.out.println("Pressed");
			repaint();
			return;

		}

		/**
		 * Events that occur when mouse released
		 */
		public void mouseReleased(MouseEvent event)
		{
			Point releasedPoint = event.getPoint();
			// System.out.println("Released");
			int origLoc = myBoard.indexOfLoc(selectedPoint);
			int finalLoc = myBoard.indexOfLoc(releasedPoint);
			// System.out.println(selectedPoint+" "+releasedPoint);
			// System.out.println(origLoc+" "+finalLoc);
			// if (myBoard.isValidMove(origLoc%10, origLoc/10,
			// finalLoc%10,finalLoc/10,true)){
			// System.out.println("isValidMoves? basically if it works, it moves everything");}

			myBoard.isValidMove(origLoc % 10, origLoc / 10, finalLoc % 10,
					finalLoc / 10, true);
			myBoard.fill();
			animated = 50;
			System.out.println("this better be the right fil;");

			if (selectedItem != null && origLoc != -1)
			{
				selectedItem.moveTo((origLoc / 10) * 40 + 60,
						(origLoc % 10) * 40 + 175);
			}else {
				
			}
			

			repaint();
		}
	}

	// Inner Class to handle mouse movements
	private class MouseMotionHandler implements MouseMotionListener
	{
		public void mouseMoved(MouseEvent event)
		{
			setCursor(Cursor.getDefaultCursor());
		}

		public void mouseDragged(MouseEvent event)
		{
			Point currentPoint = event.getPoint();

			if (selectedItem != null)
			{
				selectedItem.drag(lastPoint, currentPoint);
				lastPoint = currentPoint;
				repaint();
			}
		}
	}
}
