import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * This class keeps track of the total number of games the user has played, and
 * the total number of games that the user has won. It also keeps track of the
 * top five scores that users have obtained in this Spider Solitaire game.
 * 
 * @author Jessie
 * @version May 2013
 * 
 */
public class RankingScore extends Score
{

	private static ArrayList <Integer> rank;

	public RankingScore()
	{
		rank = new ArrayList <Integer> (5);
	}


	/**
	 * Displays the top five scores of the game, the total number of games
	 * played, the total number of games won, and the win percentage
	 */
	public String toString()
	{
		// Shows the top five scores
		String highestScores = "";
		for (int highScores = 0; highScores < 5; highScores++)
		{
			highestScores += (highScores + 1 + ". " + rank.get(highScores)+ "\n");

		}
		return highestScores;
	}

	/**
	 * Compares the winning score with the highest top five scores obtained in
	 * the past. If this score is one of the top five scores, it is added to the
	 * list.
	 * 
	 * @param score
	 *            the score to compare with the past scores
	 * @throws FileNotFoundException
	 */
	public void arrangeRankings(int score) throws FileNotFoundException
	{
		// Reads into a file
		Scanner input = new Scanner(new File ("ranks.txt"));
		for (int highScore = 0; highScore < 5; highScore++)
		{
			if (input.hasNextInt())
				rank.add(input.nextInt());
		}
		// Closes file
		input.close();
		rank.add(score);
		Collections.sort(rank);
		Collections.reverse(rank);

		PrintWriter out = new PrintWriter("ranks.txt");
		for (int highScore = 0; highScore < 5; highScore++)
		{
			out.println(rank.get(highScore));
		}
		// Closes file
		out.close();
	}

/*	*//**
	 * Sorts through the highest scores and decides if the player ranks in the
	 * top five
	 * 
	 * @param score
	 *            the given score
	 * @param highestScores
	 *            the list of top 5 scores
	 *//*
	private void highestScore(int score, int[] highestScores)
	{
		// Checks the index of where the current score should be placed
		int scoreIndex = -1;
		for (int highScore = highestScores.length - 1; highScore >= 0; highScore--)
		{
			if (score > highestScores[highScore])
			{
				scoreIndex = highScore;
			}
		}

		// Quits if the current score doesn't make it into the top five
		if (scoreIndex == -1)
			return;

		// Shifts all the scores down and places the score in its place within
		// the top five
		int index = scoreIndex + 1;
		while (index < highestScores.length)
		{
			int currentHigh = highestScores[scoreIndex];
			highestScores[scoreIndex] = currentHigh;
			index++;
		}
		highestScores[scoreIndex] = score;

	}*/
}
