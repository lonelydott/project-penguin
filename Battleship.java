
public class Battleship {
  private int length;
  private int Origlength;
  //coordinates of front of ship
  private int frontRow;
  private int frontCol;
  //coordinates of back of ship
  private int backRow;
  private int backCol;

  public Battleship(String coordsFront, String coordsBack) { //ASSUME NO DIAGONALS
    frontRow = Integer.parseInt(coordsFront.substring(1, coordsFront.length()));
    frontCol = coordsFront.charAt(0) - 64;
    backRow = Integer.parseInt(coordsBack.substring(1, coordsBack.length()));
    backCol = coordsBack.charAt(0) - 64;
    length = Math.abs(frontRow - backRow) + Math.abs(frontCol - backCol) + 1;
    Origlength = length;
  }

  public int getFrontX() {
    return frontCol;
  }
  public int getFrontY() {
    return frontRow;
  }
  public int getBackX() {
    return backCol;
  }
  public int getBackY() {
    return backRow;
  }
  public int getLength() {
    return length;
  }

  public String toString() {
    return "front: " + (char)(getFrontX() + 64) + getFrontY() + ", " + "back: " + (char)(getBackX() + 64) + getBackY();
  }

  public void minusOne() {
    length --;
  }

  public void place(int[][] board, int marker) { //boolean?
    //System.out.println("Front: " + this.getFrontX() + ", " + this.getFrontY());
    //System.out.println("Back: " + this.getBackX() + ", " + this.getBackY());
    if (getFrontX() - getBackX() == 0) {
      for (int row = 0; row <= Math.abs(getFrontY() - getBackY()); row ++) {
        board[getFrontY() + row][getFrontX()] = marker;
      }
    }
    if (getFrontY() - getBackY() == 0) {
      for (int col = 0; col <= Math.abs(getFrontX() - getBackX()); col ++) {
        board[getFrontY()][getFrontX() + col] = marker;
      }
    }
  }


}
