public class Player {
  private static int[][] playerBoard;
  private static ArrayList<Battleship> playerShips;
  private static int playerShipsLeft;
  private static boolean isCPU = false;
  private static double probabilityPowerUp = .05;
  //increase probability of choosing power ups when there are less available tiles

  public Player(int[][] board, int ships) {
    playerBoard = board;
    playerShips = new ArrayList<Battleship>(ships);
    playerShipsLeft = ships;
    isCPU = false;
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

  //for future CPU classes
  public void placeRandom() {

  }
  public void chooseRandomTile() {

  }
  public void choosePowerUp() {

  }



}
