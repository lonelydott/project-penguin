import java.util.*;
import java.io.*;
public class Weapons{

  //area of impact: 1x1
  public void missile(int[][] board, int row, int col){
    if (row > 0 && row < board.length && col > 0 && col < board[0].length && board[row][col] != 1){
      board[row][col] = 1;
    }
    else {
      throw ArrayIndexOutOfBoundsException();
    }
  }

  //area of impact: 3x3, row & col indicate top left box of the nuke
  public void nuke(int[][] board, int row, int col){
    if (row > 0 && row < board.length && col > 0 && col < board[0].length){
      if (row <= board.length - 3 && col <= board.length - 3){
        for (int i = row; i < row + 3; i++){
          for (int j = col; j < col + 3; j++){
            if(board[row][col] != 1){
              board[row][col] == 1;
            }
          }
        }
      }
      else{
        for (int i = row; i < board.length; i++){
          for (int j = row; j < board[0].length; j++){
            if (board[row][col] != 1){
              board[row][col] == 1;
            }
          }
        }
      }
    }
    else {
      throw ArrayIndexOutOfBoundsException();
    }
  }
  public static Battleship landmine(String coord){ //creates a 1x1 ship as bait to throwoff the enemy player
    Battleship bait = new Battleship(coord, coord);
  }
  public static void sonar(int[][] board, String coord){ //creates a 1x1 ship as bait to throwoff the enemy player
    row = Integer.parseInt(coord.substring(1, coord.length()));
    col = coord.charAt(0) - 64;
    if (row > 0 && row < board.length-1 && col > 0 && col < board[0].length){
      for (int i = row - 1; i < row + 2; i ++){
        for (int j = col - 1; j < col + 2; j++){
          if (data[row][col] > 0){
            // HOW DO I ACCESS THE CURRENT PLAYER'S VIEW OF THE OTHER PLAYER'S BOARD?
          }
        }
      }
    }
  }
}
