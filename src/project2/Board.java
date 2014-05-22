/**
 * Board.java
 *
 * Create Board class with default and complete
 * constructors. Generates a 20x20 board with
 * 100 Ants and 5 Doodlebugs by default.
 * Contains methods to initialize the board,
 * check if a cell is occupied, place a bug in
 * a cell, and print out the board.
 * 
 * 
 * Project 2 due May 26, 2014
 * 
 * @author Stefano Prezioso
 * @date May 15, 2014
 */
package project2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
//import java.util.Scanner;

public class Board {
	
	static Organism[][] field = new Organism[20][20];
	int numberOfAnts;
	int numberOfDoodlebugs;
	Random randomNumGen = new Random();
	
	//Default Constructor
	public Board()
	{
		this.numberOfAnts = 100;
		this.numberOfDoodlebugs = 5;
		initializeBoard(this.numberOfAnts, this.numberOfDoodlebugs);
	}
	
	//Complete Constructor
	public Board(int ants, int doodlebugs)
	{
		this.numberOfAnts = ants;
		this.numberOfDoodlebugs = doodlebugs;
		initializeBoard(this.numberOfAnts, this.numberOfDoodlebugs);
		
	}
	
	//Fill with EmptyBugs first, then Ants, then Doodlebugs
	public void initializeBoard(int numAnt, int numDoodlebugs)
	{		
		//Place Ants in field
		for (int i = 0; i < numAnt; i++)
		{
			placeBug(new Ant(), numAnt);
			//System.out.println("Ant Placement: " + i); //Debugging
		}
		
		//Place Doodlebugs in field
		for (int i = 0; i < numDoodlebugs; i++)
		{
			placeBug(new Doodlebug(), numDoodlebugs);
			//System.out.println("Doodlebug Placement: " + i); //Debugging
		}
		
	}
	
	//Check if cell is occupied, return true if occupied with Bug, otherwise false
	public boolean isOccupied(int row, int col)
	{
		//System.out.println("isOccupied conditional says: " + (this.field[row][col].symbol != ' ')); //Debugging
		
		//Check for null value in given field index
		if (Board.field[row][col] != null)
		{
			//System.out.println("isOccupied returned true"); //Debug
			return true;
		}
		
		else
		{
			//System.out.println("isOccupied returned false"); //Debug
			return false;
		}
	}
	
	//Place bug in empty cell
	public void placeBug(Organism bug, int numBug)
	{
		//Declare and initialize random row and column values
		int row = randomNumGen.nextInt(19);
		int col = randomNumGen.nextInt(19);
		
		//int counter = 0; //debug
		
		//Cycle through field until an empty cell is found
		while (this.isOccupied(row, col))
		{
			row = randomNumGen.nextInt(19);
			col = randomNumGen.nextInt(19);
			//counter++; //Debug
		}

		//System.out.println("Made if out of placeBug while loop: " + counter); //debug

		//Place a new Ant in open cell
		if(bug instanceof Ant)
		{
			//System.out.println("placeBug placed an Ant"); //Debug
			field[row][col] = new Ant(row, col);
			
		}
		
		//Place a new Doodlebug in open cell
		else if(bug instanceof Doodlebug)
		{
			//System.out.println("placeBug placed a DoodleBug");  //Debug
			field[row][col]= new Doodlebug(row, col);
		}
		
		//Error catching
		else
		{
			System.out.println("Error in placeBug method! Did not place a bug in an open cell.");
		}
	}
	
	//Return Organism at given row and column
	public Organism getBug(int row, int col)
	{
		return field[row][col];
	}
	
	// Return int of null location (empty cell). 1 = Up, 2 = Right, 3 = Down, 4 = Left. 0 = No empty cells.
	// 3 = Up/Right, 5 = Up/Down, 6 = Right/Down, 7 = Up/Right/Down,
	// 9 = 8 + 1, 10 = 8 + 2, 11 = 8 + 2 + 1, 12 = 8 + 4
	// 13 = 8 + 4 + 1, 14 = 8 + 4 + 2, 15 = 8 + 4 + 2 + 1.
	public int emptyCellChecker(Organism bug)
	{
		int flag = 0;
		
		//Check four positions for null
		if (getBug(bug.getRowPosition() - 1, bug.getColPosition()) == null)
			flag += 1;
		
		if (getBug(bug.getRowPosition(), bug.getColPosition() + 1) == null)
			flag += 2;
		
		if (getBug(bug.getRowPosition() + 1, bug.getColPosition()) == null)
			flag += 4;
		
		if (getBug(bug.getRowPosition(), bug.getColPosition() - 1) == null)
			flag += 8;
		
		return flag;
	}
	
	// Return int of Ant location. 1 = Up, 2 = Right, 4 = Down, 8 = Left. 0 = No Ant.
	// 3 = Up/Right, 5 = Up/Down, 6 = Right/Down, 7 = Up/Right/Down,
	// 9 = 8 + 1, 10 = 8 + 2, 11 = 8 + 2 + 1, 12 = 8 + 4
	// 13 = 8 + 4 + 1, 14 = 8 + 4 + 2, 15 = 8 + 4 + 2 + 1.
	public int antChecker(Doodlebug db)
	{
		int flag = 0;
		
		//Check four positions for Ant
		if (getBug(db.getRowPosition() - 1, db.getColPosition()) instanceof Ant)
			flag += 1;
		
		if (getBug(db.getRowPosition(), db.getColPosition() + 1) instanceof Ant)
			flag += 2;
		
		if (getBug(db.getRowPosition() + 1, db.getColPosition()) instanceof Ant)
			flag += 4;
		
		if (getBug(db.getRowPosition(), db.getColPosition() - 1) instanceof Ant)
			flag += 8;
		
		return flag;
	}
	
	//Print out the current board with different bug symbols
	public void printBoard()
	{
		//System.out.println(this.field.length); //debug
		
		//Top row border
		System.out.println("----------------------");
		
		for(int i = 0; i < field.length; i++)
		{
			//Left edge border
			System.out.print("|");
			
			for(int j = 0; j < field[i].length; j++)
			{
				//Print whitespace if empty cell
				if (field[i][j] == null)
				{
					System.out.print(" ");
				}
				
				//Print symbol for occupying bug
				else
				{
				System.out.print(field[i][j].symbol);
				}
			}
			//Right edge border
			System.out.print("|");
			
			//Prevent printing last empty line
			if (i < field.length - 1)
				System.out.println();
		}
		//Bottom row border
		System.out.println("\n----------------------");
	}
	
	//Takes position of bug and removes it, filling with null.
	public void removeBug(int row, int col)
	{
		if (field[row][col] == null)
		{
			System.out.println("Error: removeBug tried to remove a bug, but there was none.");
			System.exit(0);
		}
		
		field[row][col] = null;
	}
	
	//Returns Integer array of 1 - 4 in a random order
	public static Integer[] randomDirections()
	{
		Integer[] directionArray = new Integer[] {1, 2, 3, 4};
		
		Collections.shuffle(Arrays.asList(directionArray));
		
		return directionArray;
	}
	
	//Use to scan through field and have each Ant perform actions
	public void antScanner()
	{
		for (int i = 0; i < 20; i++)
		{
			for (int j = 0; j < 20; j++)
			{
				if (field[i][j].getClass() ==  new Ant().getClass()) //Perhaps I should use instanceof?
				{
					//Do Ant Stuff
				}
			}
		}
	}
	
	//Use to scan through field and have each Doodlebug perform actions
	public void doodlebugScanner()
	{
		for (int i = 0; i < 20; i++)
		{
			for (int j = 0; j < 20; j++)
			{
				if (field[i][j].getClass() ==  new Doodlebug().getClass()) //Perhaps I should use instanceof?
				{
					//Do Doodlebug Stuff
				}
			}
		}
	}
	
	
}
