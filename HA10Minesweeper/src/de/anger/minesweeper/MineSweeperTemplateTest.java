package de.darmstadt.tu.gdi1.ha10;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MineSweeperTemplateTest {

  MineSweeper ms = new MineSweeper();
  int nrRows, nrCols;
  boolean[][] board;
  MineSweeperBoard msb;
  @Before
  public void init() {
   board = new boolean[][]{
        { false, false },
        { true, false },
        { false, false },
        { false, true }};
    nrRows = board.length;
    nrCols = board[0].length;
    msb = new MineSweeperBoard(board);    
  }
  
  @Test
  public void checkMineCount() {
    assertEquals("Mine count mismatch", 2, msb.nrOfMines());
  }

  @Test
  public void checkTagsLeft() {
    assertEquals("Initial tag count mismatch", 2, msb.nrOfTagsLeft());
  }

  @Test
  public void checkNotWonOrOKAtStart() {
    assertFalse("Game should not be lost at start", msb.gameIsLost());
    assertFalse("Game should not be won at start", msb.gameIsWon());
  }
  
  @Test
  public void checkAllUntagged() {
    for (int r = 0; r < nrRows; r++)
      for (int c = 0; c < nrCols; c++)
        assertFalse("Game field ("+r +"," +c +") should not be tagged", 
              msb.isTagged(r, c));
  }
  
  @Test
  public void checkAllValuesAreInvisible() {
    for (int r = 0; r < nrRows; r++)
      for (int c = 0; c < nrCols; c++)
        assertEquals("All fields should be initially unvisited",
            20, msb.getVisibleValueFor(r, c));
  }
  
  @Test
  public void checkLostGame() {
    assertTrue("Game should be lost when stepping on a mine",
        msb.stepOn(1, 0));
    assertTrue("Game should be flagged as lost", msb.gameIsLost());
  }
   
  @Test
  public void checkLostGame1() {
     assertFalse("Game should not return false when stepping on safe field",
        msb.stepOn(0, 0));
     assertFalse("Game should not be lost after stepping on a safe field",
         msb.gameIsLost());
    assertEquals("Wrong counter for neighbouring mines", 1,
        msb.getVisibleValueFor(0, 0));

    assertFalse("Game should not return false when stepping on safe field",
        msb.stepOn(0, 1));
    assertEquals("Wrong counter for neighbouring mines", 1,
        msb.getVisibleValueFor(0, 1));
    assertFalse("Game should not be lost after stepping on a safe field",
        msb.gameIsLost());

    assertFalse("Game should not return false when stepping on safe field",
        msb.stepOn(1, 1));
    assertEquals("Wrong counter for neighbouring mines", 1,
        msb.getVisibleValueFor(1, 1));
    assertFalse("Game should not be lost after stepping on a safe field",
        msb.gameIsLost());

    
    assertFalse("Game should not return false when stepping on safe field",
        msb.stepOn(2, 0));
    assertEquals("Wrong counter for neighbouring mines", 2,
        msb.getVisibleValueFor(2, 0));
    assertFalse("Game should not be lost after stepping on a safe field",
        msb.gameIsLost());

    
    assertFalse("Game should not return false when stepping on safe field",
        msb.stepOn(2, 1));
    assertEquals("Wrong counter for neighbouring mines", 2,
        msb.getVisibleValueFor(2, 1));
    assertFalse("Game should not be lost after stepping on a safe field",
        msb.gameIsLost());

    
    assertFalse("Game should not return false when stepping on safe field",
        msb.stepOn(3, 0));
    assertEquals("Wrong counter for neighbouring mines", 1,
        msb.getVisibleValueFor(3, 0));
    assertFalse("Game should not be lost after stepping on a safe field",
        msb.gameIsLost());
  }
  
  @Test
  public void checkOutputNoMines() {
    String s = "   00\n   01\n00   \n01   \n02   \n03   ";
    assertEquals("Empty field should be shown correctly", s, msb.toString());
    assertFalse("Stepping on a safe field must work", msb.stepOn(2, 0));
    s = "   00\n   01\n00   \n01   \n02 2 \n03   ";
    assertEquals("Empty field should be shown correctly", s, msb.toString());
  }

  @Test
  public void checkStringAtLoss() {
    assertTrue("Stepping on mine should end the game!", msb.stepOn(1, 0));
    assertTrue("Game should now be lost", msb.gameIsLost());
    assertFalse("Game must not be won after stepping on a mine", msb.gameIsWon());
    String s = "   00\n   01\n00 11\n01 M1\n02 22\n03 1M";
    assertEquals("Final game state must be correct", s, msb.toString());
   }
}
