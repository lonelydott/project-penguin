import java.util.*;
import java.io.*;
public class Weapons{

  //area of impact: 1x1
  public void missile(int[][] board, int row, int col){
    if (row > 0 && row < board.length && col > 0 && col < board[0].length && board[row][col] != 1){
      board[row][col] = 1;
    }
    else {
      throw new ArrayIndexOutOfBoundsException();
    }
  }

  //area of impact: 3x3, row & col indicate top left box of the nuke
  //is this in real coordinates or actual indexes?
  public void nuke(int[][] board, int row, int col){
    if (row > 0 && row < board.length && col > 0 && col < board[0].length){
      if (row <= board.length - 3 && col <= board.length - 3){
        for (int i = row; i < row + 3; i++){
          for (int j = col; j < col + 3; j++){
            if(board[row][col] != 1){
              board[row][col] = 1;
            }
          }
        }
      }
      else{
        for (int i = row; i < board.length; i++){
          for (int j = row; j < board[0].length; j++){
            if (board[row][col] != 1){
              board[row][col] = 1;
            }
          }
        }
      }
    }
    else {
      throw new ArrayIndexOutOfBoundsException();
    }
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
  public static Battleship landmine(String xcor, String ycor){ //creates a 1x1 ship as bait to throwoff the enemy player
    Battleship bait = new Battleship(xcor, ycor);
    return bait;
  }
  public static void sonar(int[][] board, String coord){ //creates a 1x1 ship as bait to throwoff the enemy player
    int row = Integer.parseInt(coord.substring(1, coord.length()));
    int col = coord.charAt(0) - 64;
    if (row > 0 && row < board.length-1 && col > 0 && col < board[0].length){ //No idea what this loop does
      for (int i = row - 1; i < row + 2; i ++){
        for (int j = col - 1; j < col + 2; j++){
          if (board[row][col] > 0){
            // HOW DO I ACCESS THE CURRENT PLAYER'S VIEW OF THE OTHER PLAYER'S BOARD?
          }
        }
      }
    }
  }
}
