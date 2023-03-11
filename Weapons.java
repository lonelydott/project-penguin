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
}
