import java.util.ArrayList;
public class Player {
  private int[][] playerBoard;
  private ArrayList<Battleship> playerShips;
  private int playerShipsLeft;
  private boolean isCPU;
  private double probabilityPowerUp = .05;
  private PowerUp playerPowerUp;
  private String name;
  //increase probability of choosing power ups when there are less available tiles

  public Player(int rows, int cols, int ships, boolean powerUps, boolean consideredCPU, int id) {
    playerBoard = createBoard(rows, cols);
    playerShips = new ArrayList<Battleship>(ships);
    playerShipsLeft = ships;
    isCPU = consideredCPU;
    if (powerUps) {
      playerPowerUp = new PowerUp();
    }
    else {
      playerPowerUp = null;
    }
    name = id;
  }
  public int[][] getBoard() {
    return playerBoard;
  }
  public ArrayList<Battleship> getShipList() {
    return playerShips;
  }
  public int getShipsLeft() {
    return playerShipsLeft;
  }
  public void decreaseShipsLeft() {
    playerShipsLeft --;
  }
  public PowerUp getPowerUp() {
    return playerPowerUp;
  }
  public String toString() {
    return ID;
  }


  //for future CPU classes
  public void placeRandom() {

  }
  public String chooseRandomTile(int r, int c) {
    char X = (char)('A' + (int)(Math.random() * c));
    int Y = (int)(Math.random() * r);
    return "" + X + Y;
  }
  public void choosePowerUp() {

  }
  public void changeProbabilityPowerUp(double input) {
    probabilityPowerUp += input;
  }
  public double getPowerUpProbability() {
    return probabilityPowerUp;
  }
  public boolean robotCheck() {
    return isCPU();
  }

  public static int[][] createBoard(int row, int col) {//row, col = dimensions of board
    int[][] board = new int[row + 1][col + 1];
    for (int i = 1; i <= row; i ++) {
      for (int j = 1; j <= col; j ++) {
        board[i][j] = 0; //fill everything with empty plots
      }
    }
    for (int i = 1; i <= col; i ++) {
      board[0][i] = i + 64; //fill top row with letters (for coordinates)
    }
    for (int i = 0; i <= row; i ++) {
      board[i][0] = i; //fill leftmost columns with numbers (for coordinates)
    }
    return board; //board finished
  }
}
