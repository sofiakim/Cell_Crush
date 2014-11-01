import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CellCrushMain extends JFrame
{

	private CellCrush tableArea;

	private Help helpWindow;
	private RankingScore scores;
	
	public CellCrushMain() throws FileNotFoundException
	{
		super("Cell Crush");
		setResizable(false);

		// Position in the middle of the window
		setLocation(270, 50);

		// Add in an Icon - Ace of Spades
		setIconImage(new ImageIcon("redbloodcell.png").getImage());

		helpWindow = new Help();
		scores = new RankingScore();
		// Add the TablePanel to the centre of the Frame
		setLayout(new BorderLayout());
		tableArea = new CellCrush();
		add(tableArea, BorderLayout.CENTER);
		// Add in the menus
		addMenus();

	}

	/**
	 * Adds the menus to the main frame Includes adding ActionListeners to
	 * respond to menu commands
	 */
	private void addMenus()
	{
		// Creates main menus
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenu helpMenu = new JMenu("Help");
		gameMenu.setMnemonic('G');
		helpMenu.setMnemonic('H');

		// Creates sub menus
		JMenuItem newOption = new JMenuItem("New Game");
		JMenuItem help = new JMenuItem("Help");
		JMenuItem about = new JMenuItem("About");

		newOption.addActionListener(new ActionListener() {
			/**
			 * Responds to the New Menu choice
			 * 
			 * @param event
			 *            The event that selected this menu option
			 */
			public void actionPerformed(ActionEvent event)
			{
				tableArea.newGame();
			}
		});

		help.addActionListener(new ActionListener() {
			/**
			 * Responds to the help choice
			 * 
			 * @param event
			 *            The event that selected this menu option
			 */
			public void actionPerformed(ActionEvent event)
			{
				helpWindow.showHelp();
			}
		});

		about.addActionListener(new ActionListener() {
			/**
			 * Responds to the about choice
			 * 
			 * @param event
			 *            The event that selected this about option
			 */
			public void actionPerformed(ActionEvent event)
			{
				JOptionPane.showMessageDialog(CellCrushMain.this,
						"Version: June 2013\n"
								+ "by Sofia Kim, Tracy Lei, Jessie Ma\n",
						"About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		// Set up the Highest Scores Menu
		JMenu statsMenu = new JMenu("High Scores");
		statsMenu.setMnemonic('S');
		JMenuItem statsMenuItem = new JMenuItem("High Scores", 'S');
		statsMenuItem.addActionListener(new ActionListener() {
			/**
			 * Responds to the Statistics Menu choice
			 * 
			 * @param event
			 *            The event that selected this menu option
			 */
			public void actionPerformed(ActionEvent event)
			{
				tableArea.displayHighScores();
			}
		});
		statsMenu.add(statsMenuItem);


		setJMenuBar(menuBar);

		// Add each sub menus to each main menus
		gameMenu.add(newOption);
		helpMenu.add(help);
		helpMenu.add(about);
		menuBar.add(gameMenu);
		menuBar.add(statsMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
	}

	/**
	 * Gets the table panel for this game
	 * 
	 * @return the table panel
	 */
	public CellCrush getTable()
	{
		return tableArea;
	}

	public static void main(String[] args) throws FileNotFoundException
	{
		CellCrushMain frame = new CellCrushMain();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.getTable().newGame();
	}

}
