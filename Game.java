import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
public class Game {
  private static int[][] playerOneBoard;
  private static ArrayList<Battleship> playerOneShips;
  private static int[][] playerTwoBoard;
  private static ArrayList<Battleship> playerTwoShips;
//BOARDS: -1 = battleship, 0 = empty, 1 = cleared


  public static void main(String[] args) {
    int turn = 0;
    int rows = 0;
    int columns = 0;
    String input = "";
    Scanner in = new Scanner(System.in);

    System.out.println("How many rows?");
    while(rows <= 0){
      //move cursor
      rows = Integer.parseInt(in.nextLine());
    }
    System.out.println("How many columns?");
    while(columns <= 0){
      //move cursor
      columns = Integer.parseInt(in.nextLine());
    }

    System.out.println("Rows: " + rows);
    System.out.println("Columns (at most 26): " + columns);

    playerOneBoard = createBoard(rows, columns);
    playerTwoBoard = createBoard(rows, columns);

    while(!(input.equals("stop"))){
      clearTerminal();
      gotoTop();
      turn ++; // odd turns = player 1, even turns = player 2
      if (turn % 2 == 0) {//player 2's turn
        printBoard(playerOneBoard);
        System.out.println("<PLAYER ONE'S BOARD>");
        System.out.println("PLEASE CHOOSE COORDINATES (EX: A1)");

        input = in.nextLine();

        if (attack(input, playerOneBoard)) {//RETURN HIT MESSAGHE
          //
        }
        else { //RETURN MISS MESSAGE
          clearTerminal();
          gotoTop();
          System.out.println("You missed!");
          printBoard(playerOneBoard);
        }
      }
      else {//player 1's turn
        printBoard(playerTwoBoard);
        System.out.println("<PLAYER TWO'S BOARD>");
        System.out.println("PLEASE CHOOSE COORDINATES (EX: A1)");
        input = in.nextLine();

        if (attack(input, playerTwoBoard)) {//RETURN HIT MESSAGHE
          //
        }
        else { //RETURN MISS MESSAGE
          clearTerminal();
          gotoTop();
          System.out.println("You missed!");
          printBoard(playerTwoBoard);
        }
      }
      System.out.println("Press Enter for Next Turn");
      input = in.nextLine();

    }
  }

  //BOARD METHODS
  public static int[][] createBoard(int row, int col) {
    int[][] board = new int[row + 1][col + 1];
    for (int i = 1; i <= row; i ++) {
      for (int j = 1; j <= col; j ++) {
        board[i][j] = 0;
      }
    }
    for (int i = 1; i <= col; i ++) {
      board[0][i] = i + 64;
    }
    for (int i = 0; i <= row; i ++) {
      board[i][0] = i;
    }
    return board;
  }
  public static void printBoard(int[][] board) {
    for (int i = 0; i < board.length; i ++) {
      for (int j = 0; j < board[i].length; j ++) {
        if (board[i][j] >= 'A' && board[i][j] <= 'Z') {
          System.out.print((char)(board[i][j]));
        }
        else {
          if (board[i][j] == 1 && j > 0) { //HIT
            System.out.print("X");
          }
          else if (board[i][j] == -1) { //MISS
            System.out.print("O");
          }
          else {
            if (j == 0) {
              System.out.print(board[i][j]);
            }
            else {
              System.out.print(" ");
            }
          }
        }
        if (j < board[i].length - 1) {
          System.out.print(", ");
        }
      }
      System.out.println("");
    }
  }

  //GAME METHODS
  public static boolean attack(String input, int[][] beingAttacked) { //ASSUME IT IS IN A1 FORMAT
    int row = Integer.parseInt(input.substring(1, 2));
    int col = input.charAt(0) - 64;

    if (beingAttacked[row][col] == 0) {
      beingAttacked[row][col] = -1;
      return false;
    }
    //CHECK IF COORDINATE IS BATTLESHIP COORDINATE
    return true;
  }



  //THANK YOU MR. K
  public static void wait(int millis){
    try {
      Thread.sleep(millis);
    }
    catch (InterruptedException e) {
    }
  }
  public static void gotoTop(){
    //go to top left of screen
    System.out.println("\033[1;1H");
  }
  public static void clearTerminal(){
    //erase terminal
    System.out.println("\033[2J");
  }
}
