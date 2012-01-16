package de.darmstadt.tu.gdi1.ha10;

import acm.program.ConsoleProgram;

public class MineSweeper extends ConsoleProgram {
  MineSweeperBoard board;
  
  /**
   * nothing needs to be done here!
   */
  public MineSweeper() {
    
  }
  
  /**
   * initializes the game board
   */
  void setupGame() {
    int nrRows = 0, nrCols = 0, nrMines = 0;
    while (nrRows <= 0)
      nrRows =readInt("Please enter the number of rows: ");
    while (nrCols <= 0)
      nrCols = readInt("Please enter the number of cols: ");
    while (nrMines <= 0 || nrMines > nrRows * nrCols)
      nrMines = readInt("Please enter the number of mines to be placed [1..."
          +(nrRows * nrCols) +"]: ");

    // set up the game board
    board = new MineSweeperBoard(nrRows, nrCols, nrMines);
  }

  /**
   * runs the program
   */
  public void run() {
    setupGame();
    //@TODO
    // NOTE: Unless you write code below, the game will
    // only ask for the board dimensions and then not do
    // anything further!
  }
  
  public static void main(String[] args) {
    MineSweeper ms = new MineSweeper();
    ms.start(args);
  }
}
