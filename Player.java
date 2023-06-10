import java.util.*;
public class Player {
  private int[][] playerBoard;
  private ArrayList<Battleship> playerShips;
  private int playerShipsLeft;
  private boolean isCPU;
  private int probabilityPowerUp = 1;
  private PowerUp playerPowerUp;
  private String name;
  private int[][] heatMap;
  private String current = "A1";
  private int addBy = 2;
  private int difficulty;
  private static final int MISSED = -1;
  private static final int SUNK = -2;
  private static final int EASY = 1; //RANDOMLY
  private static final int MEDIUM = 1; //GRID TILE
  private static final int HARD = 1; //HEATMAP


  //increase probability of choosing power ups when there are less available tiles
  public Player(int rows, int cols, int ships, boolean powerUps, boolean consideredCPU, String id, int difficulty_) {
    playerBoard = createBoard(rows, cols);
    playerShips = new ArrayList<Battleship>(ships);
    playerShipsLeft = ships;
    isCPU = consideredCPU;
    difficulty = difficulty_;

    heatMap = new int[playerBoard.length][playerBoard[0].length];
    updateHeatMap();


    if (powerUps) {
      playerPowerUp = new PowerUp();
    }
    else {
      playerPowerUp = null;
    }
    name = id;
    if (consideredCPU) {
      name += "(";
      if (difficulty == EASY) {
        name += "EASY ";
      }
      else if (difficulty == MEDIUM) {
        name += "MEDIUM ";
      }
      else if (difficulty == HARD) {
        name += "HARD ";
      }
      name += "CPU)";
    }
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
    return name;
  }


  //for future CPU classes
  public String chooseTile(int[][] board) {
    if (difficulty == EASY) {
      return chooseRandomTile(board);
    }
    else if (difficulty == MEDIUM) {
      return chooseGridTile(board);
    }
    else if (difficulty == HARD) {
      return chooseSmartTile(board);
    }
    return "";
  }

  public int[][] getHeatMap() {
    return heatMap;
  }
  public String chooseSmartTile(int[][] heatMapReference) {
    int max = 0;
    char X = 'A';
    String Y = "1";

    updateHeatMap();
    for (int i = 1; i < heatMapReference.length; i ++) {
      for (int j = 1; j < heatMapReference[i].length; j ++) {
        if (heatMapReference[i][j] == SUNK) {
          if (i + 1 < heatMapReference.length && heatMapReference[i + 1][j] > 0) {
            return "" + (char)(j + 64) + (i + 1);
          }
          if (heatMapReference[i - 1][j] > 0) {
            return "" + (char)(j + 64) + (i - 1);
          }
          if (j + 1 < heatMapReference[i].length && heatMapReference[i][j + 1] > 0) {
            return "" + (char)(j + 65) + i;
          }
          if (heatMapReference[i][j - 1] > 0) {
            return "" + (char)(j + 63) + i;
          }
        }

        if (heatMapReference[i][j] > max) {
          max = heatMapReference[i][j];
          X = (char)(j + 64);
          Y = "" + i;
        }
      }
    }
    return X + Y;
  }
  public void updateHeatMap() {
    heatMap = new int[playerBoard.length][playerBoard[0].length];
    for (int i = 1; i < heatMap.length; i ++) {
      for (int j = 1; j < heatMap[i].length; j ++) {
        if (playerBoard[i][j] == Game.MISS) {
          heatMap[i][j] = MISSED;
        }
        if (playerBoard[i][j] == Game.HIT) {
          heatMap[i][j] = SUNK;
        }

        for (int k = 0; k < playerShipsLeft; k ++) {
          for (int shipLength = 0; shipLength < k + 2; shipLength ++) {


            if (i + shipLength < heatMap.length - shipLength) {
              if (heatMap[i + shipLength][j] >= 0) {
                heatMap[i + shipLength][j] ++;
              }
            }
            if (j + shipLength < heatMap[i].length - shipLength) {
              if (heatMap[i][j + shipLength] >= 0) {
                heatMap[i][j + shipLength] ++;
              }
            }

            if (heatMap[i][j] == MISSED) {
              if (i + shipLength < heatMap.length && heatMap[i + shipLength][j] > 0) {
                heatMap[i + shipLength][j] --;
              }
              if (i - shipLength > 0 && heatMap[i - shipLength][j] > 0) {
                heatMap[i - shipLength][j] --;
              }
              if (j + shipLength < heatMap[i].length && heatMap[i][j + shipLength] > 0) {
                heatMap[i][j + shipLength] --;
              }
              if (j - shipLength > 0 && heatMap[i][j - shipLength] > 0) {
                heatMap[i][j - shipLength] --;
              }
            }


          }
        }

      }
    }
  }

  public String chooseGridTile(int[][] board) {
    String hunted = hunt(board);
    if (hunted.length() > 0) {
      return hunted;
    }
    while (board[current.charAt(0) - 64][Integer.parseInt(current.substring(1, current.length()))] == Game.HIT) {
      if (current.charAt(0) - 64 + addBy < board[0].length) {
        current = "" + (char)(current.charAt(0) - 64 + addBy) + current.substring(1, current.length());
      }
      else {
        if (Integer.parseInt(current.substring(1, current.length())) % 2 == 1) {
          current = "" + 'A' + (Integer.parseInt(current.substring(1, current.length())) + 1);
        }
        else {
          current = "" + 'B' + (Integer.parseInt(current.substring(1, current.length())) + 1);
        }
      }
    }
    if (board[current.charAt(0) - 64][Integer.parseInt(current.substring(1, current.length()))] == Game.MISS) {
      if (current.charAt(0) - 64 + addBy < board[0].length) {
        current = "" + (char)(current.charAt(0) - 64 + addBy) + current.substring(1, current.length());
      }
      else {
        if (Integer.parseInt(current.substring(1, current.length())) % 2 == 1) {
          current = "" + 'A' + (Integer.parseInt(current.substring(1, current.length())) + 1);
        }
        else {
          current = "" + 'B' + (Integer.parseInt(current.substring(1, current.length())) + 1);
        }
      }
    }
    return current;
  }

  public String chooseRandomTile(int[][] board) {
    String hunted = hunt(board);
    if (hunted.length() > 0) {
      System.out.println("HUNT");
      return hunted;
    }

    char X = (char)('A' + (int)(Math.random() * board[0].length - 1));
    int Y = (int)(Math.random() * (board.length - 1) + 1);
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
    return isCPU;
  }
  public int getDifficulty() {
    return difficulty;
  }

  public String hunt(int[][] board) {
    for (int i = 1; i < board.length; i ++) {
      for (int j = 1; j < board[i].length; j ++) {
        if (board[i][j] == Game.HIT) {
          System.out.println("See Hit");
          System.out.println(i + 1);
          System.out.println(board[i + 1][j]);
          if (i + 1 < board.length && board[i + 1][j] <= Game.EMPTY) {
            System.out.println("See Hit 1");
            System.out.println("" + (char)(j + 64) + (i + 1));
            return "" + (char)(j + 64) + (i + 1);
          }
          if (i - 1 > 0 && (board[i - 1][j] <= Game.EMPTY)) {
            System.out.println("See Hit 2");
            System.out.println("" + (char)(j + 64) + (i - 1));
            return "" + (char)(j + 64) + (i - 1);
          }
          if (j + 1 < board.length && (board[i][j + 1] <= Game.EMPTY)) {
            System.out.println("See Hit 3");
            return "" + (char)(j + 65) + i;
          }
          if (j - 1 > 0 && (board[i][j - 1] <= Game.EMPTY)) {
            System.out.println("See Hit 4");
            return "" + (char)(j + 63) + i;
          }
        }
      }
    }
    return "";
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
