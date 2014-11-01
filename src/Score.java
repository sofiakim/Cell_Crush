import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This class keeps track of the score in a CellCrush game. Each cell popped
 * gives 60 points. The objective of the game is to complete the game with the
 * highest score.
 * 
 * @author Jessie Ma, Sofia Kim, Tracy Lei
 * @version June 2013
 */
public class Score
{
	private int totalScore;

	public Score()
	{
		totalScore = 0;
	}

	/**
	 * Adds 60 points to the total score each time a cell is crushed
	 */
	public void addScore()
	{
		totalScore += 60;
	}

	/**
	 * Gives the total score in the game
	 * 
	 * @return the total score of the game
	 */
	public int getScore()
	{
		return totalScore;
	}

	/*public void saveScore () throws IOException
	{
		try
		{
			System.out.println(totalScore);
			Scanner in = new Scanner (new File("ranks.txt"));
			ArrayList <Integer> scores = new ArrayList<Integer>(5);
			for (int scoreNo = 1; scoreNo <=5;scoreNo++)
			{
				scores.add(in.nextInt());
			}
			scores.add(totalScore);
			
			Collections.sort(scores);
			Collections.reverse(scores);
			BufferedWriter w = new BufferedWriter(new FileWriter ("ranks.txt"));
			w.write("");
			for (int scoreNo=0;scoreNo<5;scoreNo++)
			{
				w.append(scores.get(scoreNo)+"\n");

			}
			w.close();
			
			
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	/**
	 * Resets the total score and number of moves to zero
	 */
	public void newScore()
	{
		totalScore = 0;
	}

}
