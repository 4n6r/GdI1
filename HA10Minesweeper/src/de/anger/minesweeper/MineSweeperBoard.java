package de.darmstadt.tu.gdi1.ha10;

import java.util.Random;

public class MineSweeperBoard {

	private boolean[][] mineSweeperBoard = null;
	private int[][] visibleMineSweeperBoard = null;
	public int tags;
	public int myRow;
	public int myCol;

	// @TODO come up with a "good" model for representing
	// the game board!

	// add appropriate additional __private__ attributes

	/**
	 * creates a new MineSweeper game board
	 * 
	 * @param rows
	 *            the number of rows for the board
	 * @param cols
	 *            the number of columns for the board
	 * @param nrMines
	 *            the number of mines to be placed
	 */
	public MineSweeperBoard(int rows, int cols, int nrMines) {
		Random mines = new Random();
		tags = nrMines;
		row = rows;
		col = cols;

		for (int i = nrMines; i <= 0; i--) {
			int xField = mines.nextInt(cols);
			int yField = mines.nextInt(rows);

			if (mineSweeperBoard[xField][yField] == true) {
				// nochmal aufrufen mit anderem Random
			} else {
				mineSweeperBoard[xField][yField] = true;
			}
		}
	}

	/**
	 * creates a game board based on the mine placement passed in
	 * 
	 * @param mineBoard
	 *            the mine board, true meaning "here is a mine"
	 */
	public MineSweeperBoard(boolean[][] mineBoard) {
		// @TODO
	}

	/**
	 * returns the "visible" value for each element
	 * 
	 * @param row
	 *            the selected row
	 * @param col
	 *            the selected column
	 * @return The output will be determined as follows:
	 *         <ol>
	 *         <li>for a field the user has stepped on, the value will be 0-8
	 *         for a field without a mine, and 9 for a mine the user has stepped
	 *         on.</li>
	 *         <li>for a marked field, the result will be 20 - independent
	 *         whether there is a mine on this field or not.</li>
	 *         <li>an unknown field (unmarked and not stepped on) will return
	 *         30, independent of the presence of a mine and the number of
	 *         neighboring mines. It will thus not "give away secrets".</li>
	 *         </ol>
	 */
	int getVisibleValueFor(int row, int col) {
		return visibleMineSweeperBoard[col][row];
	}

	private int getValueOfNearMines(int row, int col) {
		int value = 0;

		if (mineSweeperBoard[row - 1][col - 1] == true) {
			value++;
		}
		if (mineSweeperBoard[row][col - 1] == true) {
			value++;
		}
		if (mineSweeperBoard[row + 1][col - 1] == true) {
			value++;
		}
		if (mineSweeperBoard[row - 1][col] == true) {
			value++;
		}
		if (mineSweeperBoard[row + 1][col] == true) {
			value++;
		}
		if (mineSweeperBoard[row - 1][col + 1] == true) {
			value++;
		}
		if (mineSweeperBoard[row][col + 1] == true) {
			value++;
		}
		if (mineSweeperBoard[row + 1][col + 1] == true) {
			value++;
		}
		if (mineSweeperBoard[row][col] == true) {
			value++;
		}

		return value;
	}

	/**
	 * simulates stepping on the element at position (row, col). Will make the
	 * information of the field visible, either a mine or the number of
	 * neighboring mines for this field.
	 * 
	 * @param row
	 *            the selected row
	 * @param col
	 *            the selected column
	 * @return true if the user has stepped on a mine; false if it was not a
	 *         mine <em>or</em> an illegal position.
	 */
	boolean stepOn(int row, int col) {
		
		if (myRow < row ||myCol < col) {
			return false;
			}
		
		if (mineSweeperBoard[col][row] == true){
			return true;
		}else{
			visibleMineSweeperBoard[col][row] = getValueOfNearMines(row, col);
		}
			
		
		
		return false; // only here so we do not get compile errors!
	}

	/**
	 * tags or untags a field at the given position as a "mine candidate"
	 * 
	 * @param row
	 *            the selected row
	 * @param col
	 *            the selected column
	 * @return false if the position was illegal or not more tags are available
	 */
	boolean tagField(int rows, int cols) {

		if (rows >= myRow || cols >= myCol) {
			return false;
		}
		if (tags == 0) {
			return false;
		}

		if (visibleMineSweeperBoard[col][row] == 10) {
			visibleMineSweeperBoard[col][row] = 20;
			tags++;
		}
		if (visibleMineSweeperBoard[col][row] == 20) {
			visibleMineSweeperBoard[col][row] = 10;
			tags--;
		}
		return true; // only here so we do not get compile errors!
	}

	/**
	 * determines if the game was lost
	 * 
	 * @return true if the game was lost by stepping on a mine
	 */
	boolean gameIsLost() {
		// @TODO
		return false;
	}

	/**
	 * determines if the game was won
	 * 
	 * @return true if the game was won by tagging exactly all mines
	 */
	boolean gameIsWon() {
		// @TODO
		return false;
	}

	/**
	 * returns the number of tags the user can still place
	 * 
	 * @return the number of tags left - between 0 (all placed) and the number
	 *         of mines on the field (no tags placed)
	 */
	int nrOfTagsLeft() {
		// @TODO
		return 1;
	}

	/**
	 * returns the number of mines to be found
	 * 
	 * @return the number of mines placed on the board
	 */
	int nrOfMines() {
		// @TODO
		return 1;
	}

	/**
	 * returns a String rendition of the game board
	 * 
	 * @return a String describing the currently visible status of the game
	 *         board
	 */
	public String toString() {
		// @TODO
		return ""; // only here to prevent compile errors
	}

}