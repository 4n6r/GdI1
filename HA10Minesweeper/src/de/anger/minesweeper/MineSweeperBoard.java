package de.anger.minesweeper;

import java.util.Random;

public class MineSweeperBoard {

	private boolean[][] mineSweeperBoard = null;
	private int[][] visibleMineSweeperBoard = null;
	public int tags;
	public int myRow;
	public int myCol;
	public int myMines;

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
		int row = rows;
		int col = cols;
		myMines = nrMines;

		mineSweeperBoard = new boolean[rows][cols];
		visibleMineSweeperBoard = new int[rows][cols];

		for (int i = nrMines; i > 0; i--) {
			boolean breakpoint = false;
			while (breakpoint == false) {
				int xField = mines.nextInt(rows);
				int yField = mines.nextInt(cols);

				if (mineSweeperBoard[xField][yField] == true) {
					// Wenn dort schon Mine lass Schleife mit neuen Zahlen
					// weiterlaufen
				} else {
					mineSweeperBoard[xField][yField] = true;
					break;
				}
			}
		}

		for (int i = nrMines; i <= 0; i--) {
			int xField = mines.nextInt(rows);
			int yField = mines.nextInt(cols);

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
		int nrMines = 0;
		mineSweeperBoard = mineBoard;
		visibleMineSweeperBoard = new int[mineSweeperBoard.length][mineSweeperBoard[0].length];

		for (int i = 0; i < mineSweeperBoard.length; i++) {
			for (int j = 0; j < mineSweeperBoard[i].length; j++) {
				if (mineBoard[i][j] == true) {
					nrMines++;
				}
			}
		}

		tags = nrMines;
		myMines = nrMines;
		myRow = mineSweeperBoard.length;
		myCol = mineSweeperBoard[0].length;

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
	public int getVisibleValueFor(int row, int col) {

		if (visibleMineSweeperBoard[row][col] == 0){
			return 20;
		}
		
		if (visibleMineSweeperBoard[row][col] == 10) {
			return 10;
		}	
		
		if (visibleMineSweeperBoard[row][col] <= 9) {
			return visibleMineSweeperBoard[row][col];
		}

		return 20;
	}

	private int getValueOfNearMines(int row, int col) {
		int value = 0;

//		if(col == 0){
//			if (mineSweeperBoard[row - 1][col] == true) {
//				value++;
//			}
//			if (mineSweeperBoard[row + 1][col] == true) {
//				value++;
//			}
//			if (mineSweeperBoard[row - 1][col + 1] == true) {
//				value++;
//			}
//			if (mineSweeperBoard[row][col + 1] == true) {
//				value++;
//			}
//			if (mineSweeperBoard[row + 1][col + 1] == true) {
//				value++;
//			}
//			if (mineSweeperBoard[row][col] == true) {
//				value++;
//			}
//		}
//		
//		if (col == myCol)

		if (row != 0 && col != 0 && mineSweeperBoard[row - 1][col - 1] == true) {
			value++;
		}
		if (col != 0 && mineSweeperBoard[row][col - 1] == true) {
			value++;
		}
		if (col != 0 && row < myRow-- && mineSweeperBoard[row + 1][col - 1] == true) {
			value++;
		}
		if (row != 0 && mineSweeperBoard[row - 1][col] == true) {
			value++;
		}
		if (row < myRow-- && mineSweeperBoard[row + 1][col] == true) {
			value++;
		}
		if (row != 0 && col < myCol-- && mineSweeperBoard[row - 1][col + 1] == true) {
			value++;
		}
		if (col < myCol-- && mineSweeperBoard[row][col + 1] == true) {
			value++;
		}
		if (row < myRow-- && col < myCol-- && mineSweeperBoard[row + 1][col + 1] == true) {
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
	public boolean stepOn(int row, int col) {

		if (myRow < row || myCol < col) {
			return false;
		}

		if (row < 0 || col < 0) {
			return false;
		}

		if (mineSweeperBoard[row][col] == true) {
			visibleMineSweeperBoard[row][col] = 9;
			return true;
		} else {
			visibleMineSweeperBoard[row][col] = getValueOfNearMines(row, col);
			return false;
		}
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

		if (rows > myRow || cols > myCol) {
			return false;
		}

		if (rows < 0 || cols < 0) {
			return false;
		}

		if (tags == 0) {
			return false;
		}

		if (visibleMineSweeperBoard[rows][cols] == 10) {
			visibleMineSweeperBoard[rows][cols] = 20;
			tags++;
			return true;
		} else {
			if (visibleMineSweeperBoard[rows][cols] == 20) {
				visibleMineSweeperBoard[rows][cols] = 10;
				tags--;
				return true;
			}
		}
		return true;
	}

	/**
	 * determines if the game was lost
	 * 
	 * @return true if the game was lost by stepping on a mine
	 */
	boolean gameIsLost() {
		for (int i = 0; i < visibleMineSweeperBoard.length; i++) {
			for (int j = 0; j < visibleMineSweeperBoard[i].length; j++) {
				if (visibleMineSweeperBoard[i][j] == 9) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * determines if the game was won
	 * 
	 * @return true if the game was won by tagging exactly all mines
	 */
	boolean gameIsWon() {
		for (int i = 0; i < visibleMineSweeperBoard.length; i++) {
			for (int j = 0; j < visibleMineSweeperBoard[i].length; j++) {
				if (mineSweeperBoard[i][j] == true
						&& visibleMineSweeperBoard[i][j] != 10) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * returns the number of tags the user can still place
	 * 
	 * @return the number of tags left - between 0 (all placed) and the number
	 *         of mines on the field (no tags placed)
	 */
	public int nrOfTagsLeft() {
		return tags;
	}

	/**
	 * returns the number of mines to be found
	 * 
	 * @return the number of mines placed on the board
	 */
	public int nrOfMines() {
		return myMines;
	}

	/**
	 * returns a String rendition of the game board
	 * 
	 * @return a String describing the currently visible status of the game
	 *         board
	 */
	public String toString() {
		StringBuilder myString = new StringBuilder();
		myString.append("   ");
		for (int i = 0; i <= visibleMineSweeperBoard.length; i++) {
			if (i < 10) {
				myString.append(0);
			} else {
				int zahl = i++;
				zahl = Integer.parseInt(String.valueOf(zahl).substring(1));
				myString.append(zahl);
			}
		}
		myString.append("/n");
		myString.append("   ");
		for (int i = 0; i <= visibleMineSweeperBoard.length; i++){
			if (i < 10) {
				myString.append(i++);
			} else {
				int zahl = i++;
				zahl = Integer.parseInt(String.valueOf(zahl).substring(2));
				myString.append(zahl);
			}
		}
		for (int i = 0; i <= visibleMineSweeperBoard[0].length; i++) {
			
			myString.append("/n");

			if (i < 10) {
				myString.append(0);
			} else {
				int zahl = i++;
				zahl = Integer.parseInt(String.valueOf(zahl).substring(1));
				myString.append(zahl);
			}

			if (i < 10) {
				myString.append(i++);
			} else {
				int zahl = i++;
				zahl = Integer.parseInt(String.valueOf(zahl).substring(2));
				myString.append(zahl);
			}

			if (gameIsWon() || gameIsLost()) {
				for (int j = 0; j <= visibleMineSweeperBoard[i].length; j++) {
					if (getVisibleValueFor(j, i) == 9) {
						myString.append("M");
					} else {
						myString.append(getVisibleValueFor(j, i));
					}

				}
			} else {

				for (int j = 0; j <= visibleMineSweeperBoard[i].length; j++) {
					if (getVisibleValueFor(j, i) == 10) {
						myString.append("?");
					}
					if (getVisibleValueFor(j, i) == 9) {
						myString.append("M");
					}
					if (getVisibleValueFor(j, i) < 9) {
						myString.append(getVisibleValueFor(j, i));
					} else {
						myString.append(" ");
					}

				}
			}
		}

		return myString.toString();
	}

	/**
	 * Checks if field is tagged
	 * @param row Reihe des Spielbretts
	 * @param col Spalte des Spielbretts
	 * @return true wenn getagged, andernfalls false
	 */
	public boolean isTagged(int row, int col) {
		if (visibleMineSweeperBoard[row][col] == 10){
			return true;
		}else{
			return false;
		}
	}
}
