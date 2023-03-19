import java.util.Arrays;
import java.util.ArrayList;
public class Battleship {
  private int length;
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



  //FOR TESTING
  public static void main(String[] args) {
    ArrayList<Battleship> testList = new ArrayList<Battleship>();
    for (int i = 1; i < 3; i ++) {
      Battleship testShip = new Battleship("A" + i, "A" + (i + 1));
      //System.out.println("A" + i + ", " + "A" + (i + 1));
      testList.add(testShip);
      System.out.println(testShip);
    }
    // Battleship testShip = new Battleship("A1", "A2");
    // System.out.println(testShip.getFrontX());
    // Battleship testShip1 = new Battleship("B2", "B3");
    // System.out.println(testShip.getFrontX());
    // System.out.println("A1".charAt(0) - 64);
    //
    //
    // System.out.println(testShip);
    // System.out.println(testShip1);
    //
    // ArrayList<Battleship> testList = new ArrayList<Battleship>();
    // testList.add(testShip);
    // System.out.println(testList);
    // testList.add(testShip1);
    System.out.println(testList);


    /*
    int[][] testBoard = new int[5][5];
    for (int i = 0; i < 5; i ++) {
      for (int j = 0; j < 5; j ++) {
        testBoard[i][j] = 0;
      }
    }
    for (int i = 0; i < 5; i ++) {
      System.out.println(Arrays.toString(testBoard[i]));
    }
    testList.add(testShip);

    //System.out.println("Front: " + testShip.getFrontX() + ", " + testShip.getFrontY());
    //System.out.println("Back: " + testShip.getBackX() + ", " + testShip.getBackY());

    for (int i = 0; i < testList.size(); i ++) {
      testList.get(i).place(testBoard, (i + 1) * -1);
    }

    for (int i = 0; i < 5; i ++) {
      System.out.println(Arrays.toString(testBoard[i]));
    }
    */
  }
}
