import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CellCrushBoard
{

	private Cell[][] board;
	
	private Score score;

	private int movesLeft;

	private final int NO_OF_ROWS = 9;

	private final int NO_OF_COLS = 9;

	private final int EMPTY = 0;

	private final int NO_OF_CELLS = 5;

	private final int ONE_MEMBRANE = 1;

	private final int TWO_MEMBRANE = 2;

	private final int CANCER = 7; 

	private final int VIRUS = 8;

	private final int FAT = 9;

	public CellCrushBoard(int level) throws FileNotFoundException
	{
		board = new Cell[NO_OF_ROWS][NO_OF_COLS];
		movesLeft = 15;
		score = new Score ();
		
		// Read in text file which contains layout of the given level
		Scanner sc = new Scanner(new File("level" + level + ".txt"));

		// Fills cells array with the layout of level (fills in fat)
		for (int row = 0; row < NO_OF_ROWS; row++)
		{
			int line = Integer.parseInt(sc.nextLine());
			for (int col = NO_OF_COLS - 1; col >= 0; col--)
			{
				int type = line % 10;
				if (type != FAT && type != CANCER && type != VIRUS)

					if (type == FAT)
						board[row][col] = new Cell(9, col * 40 + 60,
								row * 40 + 175);
					else if (type == CANCER)
						board[row][col] = new Cell(8, col * 40 + 60,
								row * 40 + 175);
					else if (type == VIRUS)
						board[row][col] = new Cell(7, col * 40 + 60,
								row * 40 + 175);
					else
					{
						board[row][col] = new Cell((int) (Math.random()
								* NO_OF_CELLS + 1), col * 40 + 60,
								row * 40 + 175);
						if (type == ONE_MEMBRANE)
							board[row][col].setMembrane(1);
						else if (type == TWO_MEMBRANE)
							board[row][col].setMembrane(2);
					}

				line /= 10;
			}
		}
		sc.close();

	}

	public CellCrushBoard() throws FileNotFoundException
	{
		new CellCrushBoard(1);
	}

	public boolean canPickUp(Point point)
	{
		// Checks if the point clicked is within the board
		// If not, returns false (does not pick up)
		// NOTE: WHEN WE CENTER THE BOARD, THESE MARGINS WILL CHANGE!!!!
		if (point.x > NO_OF_COLS * 40 + 60 || point.y > NO_OF_ROWS * 40 + 175
				|| point.x < 60 || point.y < 175)
			return false;

		// Checks if the type of cell clicked can be moved
		// Finds the cell at the point that was pressed
		int row = (point.x - 60) / 40;
		int col = (point.y - 175) / 40;
		if (board[row][col].getCellType() > 6)
			return false;
		else
			return true;
		// return false;
	}

	public Movable pickUp(Point point)
	{
		if (canPickUp(point))
		{
			// Finds the cell at the point that was pressed
			for (int row = 0; row < NO_OF_ROWS; row++)
			{
				for (int col = 0; col < NO_OF_COLS; col++)
				{
					if (board[row][col].contains(point))
						return board[row][col];
				}
			}
		}
		return null;

	}

	public int indexOfLoc(Point point)
	{

		if (canPickUp(point))
		{

			return ((point.x - 60) / 40) * 10 + ((point.y - 175) / 40);
		}
		return -1;
	}

	/**
	 * Finds the center of the Point position of a piece given the piece's row
	 * and column position in the board
	 * 
	 * @param index
	 *            the position (row and column) of the piece in the board
	 * @return the point of the center of the piece in the board
	 */
	public Point pointAtIndex(int index)
	{
		Point point = new Point();
		point.x = (index / 10) * 40 + 80;
		point.y = (index % 10) * 40 + 195;

		return point;
	}

	// DOESN'T WORK YET CUZ I NEED THE BOTTOM 2 METHODS
	public void shuffle()
	{
		boolean shuffle = true;
		// Shuffles until a board with valid moves is found and makes sure
		// shuffling does not cause cells to be crushed
		while (shuffle)
		{
			int switches = 1;
			while (switches < NO_OF_ROWS * NO_OF_COLS)
			{
				int cellR = (int) (Math.random() * NO_OF_ROWS);
				int cellC = (int) (Math.random() * NO_OF_COLS);
				int cellR2 = (int) (Math.random() * NO_OF_ROWS);
				int cellC2 = (int) (Math.random() * NO_OF_COLS);
				if (board[cellR][cellC].getCellType() != FAT
						&& board[cellR2][cellC2].getCellType() != FAT
						&& board[cellR][cellC].getCellType() != VIRUS
						&& board[cellR2][cellC2].getCellType() != VIRUS
						&& board[cellR][cellC].getCellType() != CANCER
						&& board[cellR2][cellC2].getCellType() != CANCER)
				{
					Cell tempCell = board[cellR][cellC];
					board[cellR][cellC] = board[cellR2][cellC2];
					board[cellR2][cellC2] = tempCell;
					board[cellR][cellC].moveTo(cellC * 40 + 60,
							cellR * 40 + 175);
					board[cellR2][cellC2].moveTo(cellC2 * 40 + 60,
							cellR2 * 40 + 175);
					switches++;
				}
			}
			shuffle = false;
			for (int r = 0; r < NO_OF_ROWS; r++)
			{
				for (int c = 0; c < NO_OF_COLS; c++)
				{
					if (check(r, c, false))
						shuffle = true;

				}
			}

		}

	}

	public void update()
	{

		
		
		for (int r = 0; r < NO_OF_ROWS; r++)
		{
			for (int c = 0; c < NO_OF_COLS; c++)
			{
				check(r, c,true);
			}
		}
		//fill();
		

	}

	/**
	 * Checks to see if the board has valid moves to determine if shuffling is
	 * required
	 * 
	 * @return true if at least one valid move exists, false if not
	 */
	public boolean hasValidMoves()
	{
		boolean done = false;
		for (int r = 0; r < NO_OF_ROWS; r++)
		{
			for (int c = 0; c < NO_OF_COLS; c++)
			{
				if (check(r, c,false))
					done = true;
			}
		}
		return done;
	}
	
	public boolean hasHoles()
	{
		boolean done = false;
		for (int r = 0; r < NO_OF_ROWS; r++)
		{
			for (int c = 0; c < NO_OF_COLS; c++)
			{
				if (board[r][c].isDead())
					done = true;
			}
		}
		return done;
	}

	/**
	 * Fill in missing holes
	 */
	public void fill()
	{
		for (int col = 0; col < NO_OF_COLS; col++)
		{
			for(int row=NO_OF_ROWS-2;row>=0;row--){
				if(board[row+1][col].isDead()){
					Cell temp=board[row+1][col];
					board[row+1][col]=board[row][col];
					board[row][col] = temp;
					board[row][col].moveTo(col * 40 + 60, row * 40 + 175);
					board[row+1][col].moveTo(col * 40 + 60, (row+1) * 40 + 175);
				}
			}
			if(board[0][col].isDead()){
				board[0][col] = new Cell(
						(int) (Math.random() * NO_OF_CELLS + 1), col * 40 + 60,
						175);
				board[0][col].moveTo(col * 40 + 60, 175);
			}
			/*
			ArrayList<Cell> arr = new ArrayList<Cell>();
			for (int row = 0; row < NO_OF_ROWS; row++)
			{
				if (board[row][col].isDead() == false)
				{
					arr.add(board[row][col]);
				}
			}
			for (int row = 0; row <NO_OF_ROWS - arr.size()-1; row++)
			{
				board[row][col] = new Cell(
						(int)  CANCER, col * 40 + 60,
						row * 40 + 175);
				board[row][col].hide();
			}
			for (int row = NO_OF_ROWS - arr.size()-1; row < NO_OF_ROWS - arr.size(); row++)
			{
				if(row<0)continue;
				board[row][col] = new Cell(
						(int) (Math.random() * NO_OF_CELLS + 1), col * 40 + 60,
						row * 40 + 175);
			}
			for (int row = NO_OF_ROWS - arr.size(); row < NO_OF_ROWS; row++)
			{
				board[row][col] = arr.get(row - (NO_OF_ROWS - arr.size()));
				board[row][col].moveTo(col * 40 + 60, row * 40 + 175);
			}*/

		}

	}

	/**
	 * Returns how many moves are left in the game
	 */
	public int getMovesLeft()
	{
		return movesLeft;
	}

	/**
	 * Sets the number of moves left in the game to the starting number;
	 */
	public void setMoves()
	{
		movesLeft = 15;
	}
	
	/**
	 * Returns the total score of the game
	 */
	public int getScore ()
	{
		return score.getScore ();
	}
	
	/**
	 * Sets the score back to zero.
	 */
	public void newScore ()
	{
		score.newScore();
	}

	// INCOMPLETE
	// Add adjustments
	public boolean isValidMove(int row, int col, int row2, int col2,
			boolean adjust)
	{
		if (row < 0 || col < 0 || row2 < 0 || col2 < 0)
			return false;
		if (Math.abs(row - row2) + Math.abs(col - col2) != 1)
			return false;

		Cell temp = board[row][col];
		board[row][col] = board[row2][col2];
		board[row2][col2] = temp;

		if (adjust)
		{
			board[row][col].moveTo(col * 40 + 60, row * 40 + 175);
			board[row2][col2].moveTo(col2 * 40 + 60, row2 * 40 + 175);
		}

		boolean first = check(row, col, adjust);
		boolean second = check(row2, col2, adjust);

		if (first || second)
		{
			movesLeft--;
			return true;
		}
		else
		{
			if (adjust)
			{
				temp = board[row][col];
				board[row][col] = board[row2][col2];
				board[row2][col2] = temp;
				board[row][col].moveTo(col * 40 + 60, row * 40 + 175);
				board[row2][col2].moveTo(col2 * 40 + 60, row2 * 40 + 175);

			}
			else
			{
				temp = board[row][col];
				board[row][col] = board[row2][col2];
				board[row2][col2] = temp;
			}
			return false;
		}
	}

	public boolean check(int row, int col, boolean adjust)
	{
		boolean found = false;
		boolean ret = false;
		int inACol = 1;
		int cellType1 = board[row][col].getCellType();
		// Checks upward in the column
		int r;
		int r2;
		for (r = row - 1, found = false; r >= 0 && !found; r--)
		{
			if (board[r][col].getCellType() == cellType1)
				inACol++;
			else
				found = true;
		}
		if (found)
			r++;

		// Checks downward in the column
		for (r2 = row + 1, found = false; r2 < NO_OF_ROWS && !found; r2++)
		{ // do for all
			if (board[r2][col].getCellType() == cellType1)
				inACol++;
			else
				found = true;
		}
		if (found)
			r2--;

		if (inACol >= 3)
		{
			if (adjust)
			{
				// System.out.println(r + " " + r2);
				for (int r3 = r + 1; r3 < r2; r3++)
				{
					board[r3][col].hide();
					score.addScore();
				}

			}
			ret = true;
		}
		int c;
		int c2;
		int inARow = 1;
		// Checks leftward in the row
		for (c = col - 1, found = false; c >= 0 && !found; c--)
		{
			if (board[row][c].getCellType() == cellType1)
				inARow++;
			else
				found = true;
		}
		if (found)
			c++;
		// Checks rightward in the row
		for (c2 = col + 1, found = false; c2 < NO_OF_COLS && !found; c2++)
		{
			if (board[row][c2].getCellType() == cellType1)
				inARow++;
			else
				found = true;
		}

		if (found)
			c2--;
		if (inARow >= 3)
		{
			if (adjust)
			{
				// System.out.println(c + " " + c2);
				for (int c3 = c + 1; c3 < c2; c3++)
				{
					board[row][c3].hide();
					score.addScore();
				}

			}
			ret = true;
		}
		return ret;

	}

	public void draw(Graphics g)
	{
		for (int row = 0; row < NO_OF_ROWS; row++)
		{
			for (int col = 0; col < NO_OF_COLS; col++)
			{
				board[row][col].draw(g);
			}
		}
	}

}
