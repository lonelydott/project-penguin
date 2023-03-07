import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
public class Game {
  private static int[][] playerOneBoard;
  private static int[][] playerOneShips;
  private static int[][] playerTwoBoard;
  private static int[][] playerTwoShips;
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
      turn ++; // odd turns = player 1, even turns = player 2
      if (turn % 2 == 0) {//player2
        printBoard(playerTwoBoard);
        System.out.println("<PLAYER TWO BOARD>");
        System.out.println("PLEASE CHOOSE COORDINATES");
      }
      else {//player1
        printBoard(playerOneBoard);
        System.out.println("<PLAYER ONE BOARD>");
        System.out.println("PLEASE CHOOSE COORDINATES");
      }

      input = in.nextLine();
    }
  }

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
        if (board[i][j] < 65) {
          System.out.print(board[i][j]);
        }
        else {
          System.out.print((char)(board[i][j]));
        }
        if (j < board[i].length - 1) {
          System.out.print(", ");
        }
      }
      System.out.println("");
    }
  }





}
