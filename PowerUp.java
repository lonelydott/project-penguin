import java.util.*;
import java.io.*;
public class PowerUp{
  private int numberNukes;
  private int numberTraps;

  public PowerUp() {
    numberNukes = 1;
    numberTraps = 1;
  }
  public PowerUp(int nukes, int traps) {
    numberNukes = nukes;
    numberTraps = traps;
  }
  public int getNukes() {
    return numberNukes;
  }
  public void useNuke() {
    numberNukes --;
  }
  public int getTraps() {
    return numberTraps;
  }

  //area of impact: 1x1
  public static boolean missile(String input, int[][] board, ArrayList<Battleship> beingAttacked) { //ASSUME IT IS IN A1 FORMAT
    //DATA FROM COORDINATE
    int row = Integer.parseInt(input.substring(1, input.length()));
    int col = input.charAt(0) - 64;

    if (board[row][col] >= 0) { // MISS
      if (board[row][col] == 0) { //check if tile is empty
        board[row][col] = 2; //turn tile to O (miss)
      }
      if (board[row][col] == 3) {
        board[row][col] = 1;
        return true;
      }
      return false;
    }
    else { // HIT!!!!!
      int battleshipHit = (board[row][col] * -1) - 1; //Identify battleship
      beingAttacked.get(battleshipHit).minusOne(); //Find the battleship that got caught, and subtract 1 from its length.
      board[row][col] = 1; //turn tile to X (hit)
      return true;
    }
  }

  //area of impact: +, row & col indicate top left box of the nuke
  //is this in real coordinates or actual indexes?
  public static boolean nuke(String input, int[][] board, ArrayList<Battleship> beingAttacked) { //ASSUME IT IS IN A1 FORMAT
    int row = Integer.parseInt(input.substring(1, input.length()));
    int col = input.charAt(0) - 64;

    boolean bool = false;
    if (nukeHelper(row, col, board, beingAttacked)) {
      bool = true;
    }
    if (nukeHelper(row - 1, col, board, beingAttacked)) {
      bool = true;
    }
    if (nukeHelper(row + 1, col, board, beingAttacked)) {
      bool = true;
    }
    if (nukeHelper(row, col - 1, board, beingAttacked)) {
      bool = true;
    }
    if (nukeHelper(row, col + 1, board, beingAttacked)) {
      bool = true;
    }
    return bool;
  }
  //Trying to create a version of nuke that invokes attack
  // public void nuke(int[][] board, int row, int col){
  //   if (row > 0 && row < board.length && col > 0 && col < board[0].length){
  //     if (row <= board.length && col <= board[0].length - 3) {
  //       for(int rank = row; rank < row + 3; rank++) {
  //         for (int file = col; file < col + 3; file++) {
  //           Game.attack(??);
  //         }
  //       }
  //     }
  //   }
  // }
  public static Battleship trap(String xcor, String ycor){ //creates a 1x1 ship as bait to throwoff the enemy player
    Battleship bait = new Battleship(xcor, ycor);
    return bait;
  }
  public static void sonar(int[][] board, String coord){ //creates a 1x1 ship as bait to throwoff the enemy player
    int row = Integer.parseInt(coord.substring(1, coord.length()));
    int col = coord.charAt(0) - 64;
    if (row > 0 && row < board.length-1 && col > 0 && col < board[0].length){ //No idea what this loop does - Tim
      for (int i = row - 1; i < row + 2; i ++){
        for (int j = col - 1; j < col + 2; j++){
          if (board[row][col] > 0){
            // HOW DO I ACCESS THE CURRENT PLAYER'S VIEW OF THE OTHER PLAYER'S BOARD?
          }
        }
      }
    }
  }

  public static boolean nukeHelper(int row, int col, int[][] board, ArrayList<Battleship> beingAttacked) { //ASSUME IT IS IN A1 FORMAT
    if (row == 0 || col == 0 || row == board.length || col == board.length) {
      return false;
    }
    if (board[row][col] >= 0) { // MISS
      if (board[row][col] == 0) { //check if tile is empty
        board[row][col] = 2; //turn tile to O (miss)
      }
      if (board[row][col] == 3) {
        board[row][col] = 1;
        return true;
      }
      return false;
    }
    else { // HIT!!!!!
      int battleshipHit = (board[row][col] * -1) - 1; //Identify battleship
      beingAttacked.get(battleshipHit).minusOne(); //Find the battleship that got caught, and subtract 1 from its length.
      board[row][col] = 1; //turn tile to X (hit)
      return true;
    }
  }
}
