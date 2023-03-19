import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
public class Game {
  private static int[][] playerOneBoard;
  private static ArrayList<Battleship> playerOneShips;
  private static int playerOneShipsLeft;
  private static int[][] playerTwoBoard;
  private static ArrayList<Battleship> playerTwoShips;
  private static int playerTwoShipsLeft;
//BOARDS: 2 = miss, 0 = empty, 1 = hit


  public static void main(String[] args) {
    int turn = 0;
    int rows = 0;
    int columns = 0;
    int numberShips = 0;
    String input = "";
    Scanner in = new Scanner(System.in);

    System.out.println("How many rows?");
    while(rows <= 0){
      rows = in.nextInt();
    }
    System.out.println("How many columns?");
    while(columns <= 0){
      columns = in.nextInt();;
    }
    System.out.println("How many ships per player?");
    while(numberShips <= 0){
      numberShips = in.nextInt();;
    }
    playerOneShipsLeft = numberShips;
    playerTwoShipsLeft = numberShips;

    System.out.println("Rows: " + rows);
    System.out.println("Columns (at most 26): " + columns);
    System.out.println("Ships: " + numberShips);

    playerOneBoard = createBoard(rows, columns);
    playerTwoBoard = createBoard(rows, columns);
    playerOneShips = new ArrayList<Battleship>(numberShips);
    playerTwoShips = new ArrayList<Battleship>(numberShips);

    //PLACING SHIPS
    clearTerminal();
    gotoTop();

    System.out.println("PLAYER ONE: CREATE YOUR SHIPS");
    printBoard(playerOneBoard, false);
    for (int i = 0; i < numberShips; i ++) { //FOR PLAYER ONE
      String front = "";
      String back = "";
      System.out.println("Front of Ship " + (i + 1) + " (A1 format): ");
      front = in.next();

      System.out.println("Back of Ship " + (i + 1) + " (A1 format): ");
      back = in.next();
      playerOneShips.add(new Battleship(front, back));
      playerOneShips.get(i).place(playerOneBoard, (i + 1) * -1);
      clearTerminal();
      gotoTop();
      printBoard(playerOneBoard, false);
    }

    System.out.println("YOUR FINAL BOARD");
    for (int i = 0; i < playerOneShips.size(); i ++) {
      System.out.println("(Battleship " + (i + 1) + ") " + playerOneShips.get(i));
    }
    System.out.println("PRESS ENTER TO CONTINUE");


    in.nextLine();
    in.nextLine();

    clearTerminal();
    gotoTop();

    System.out.println("PLAYER TWO: CREATE YOUR SHIPS");
    printBoard(playerTwoBoard, false);
    for (int i = 0; i < numberShips; i ++) { //FOR PLAYER ONE
      String front = "";
      String back = "";
      System.out.println("Front of Ship " + (i + 1) + " (A1 format): ");
      front = in.next();

      System.out.println("Back of Ship " + (i + 1) + " (A1 format): ");
      back = in.next();
      playerTwoShips.add(new Battleship(front, back));
      playerTwoShips.get(i).place(playerTwoBoard, (i + 1) * -1);

      clearTerminal();
      gotoTop();
      printBoard(playerTwoBoard, false);
    }

    System.out.println("YOUR FINAL BOARD");
    for (int i = 0; i < playerTwoShips.size(); i ++) {
      System.out.println("(Battleship " + (i + 1) + ") " + playerTwoShips.get(i));
    }
    System.out.println("PRESS ENTER TO CONTINUE");

    in.nextLine();
    in.nextLine();

    //BOMBING PHASE (AFTER PLACING SHIPS)
    while(playerOneShipsLeft > 0 && playerTwoShipsLeft > 0){
      clearTerminal();
      gotoTop();
      turn ++; // odd turns = player 1, even turns = player 2
      if (turn % 2 == 0) {//player 2's turn
        System.out.println("<PLAYER TWO'S TURN>");
        printBoard(playerOneBoard, true);
        System.out.println("<PLAYER ONE'S BOARD>");
        System.out.println("PLEASE CHOOSE COORDINATES (EX: A1)");
        input = in.next();

        int battleshipHit = playerOneBoard[Integer.parseInt(input.substring(1, input.length()))][input.charAt(0) - 64] * -1 - 1;
        if (attack(input, playerOneBoard, playerOneShips)) {//RETURN HIT MESSAGHE
          clearTerminal();
          gotoTop();
          System.out.println("You hit a ship!");
          printBoard(playerOneBoard, true);

          if (playerOneShips.get(battleshipHit).getLength() == 0) {
            playerOneShipsLeft --;
          }

          in.nextLine();
          in.nextLine();
        }
        else { //RETURN MISS MESSAGE
          clearTerminal();
          gotoTop();
          System.out.println("You missed!");
          printBoard(playerOneBoard, true);
          in.nextLine();
          in.nextLine();
        }
      }
      else {//player 1's turn
        System.out.println("<PLAYER ONE'S TURN>");
        printBoard(playerTwoBoard, true);
        System.out.println("<PLAYER TWO'S BOARD>");
        System.out.println("PLEASE CHOOSE COORDINATES (EX: A1)");
        input = in.next();

        int battleshipHit = playerTwoBoard[Integer.parseInt(input.substring(1, input.length()))][input.charAt(0) - 64] * -1 - 1;
        if (attack(input, playerTwoBoard, playerTwoShips)) {//RETURN HIT MESSAGHE
          clearTerminal();
          gotoTop();
          System.out.println("You hit a ship!");
          printBoard(playerTwoBoard, true);

          if (playerTwoShips.get(battleshipHit).getLength() == 0) {
            playerTwoShipsLeft --;
          }

          in.nextLine();
          in.nextLine();
        }
        else { //RETURN MISS MESSAGE
          clearTerminal();
          gotoTop();
          System.out.println("You missed!");
          printBoard(playerTwoBoard, true);
          in.nextLine();
          in.nextLine();
        }
      }
    }

    if (playerOneShipsLeft == 0) {
      System.out.println("PLAYER TWO WINS!");
    }
    else if (playerTwoShipsLeft == 0) {
      System.out.println("PLAYER ONE WINS!");
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
  public static void printBoard(int[][] board, boolean concealed) {
    for (int i = 0; i < board.length; i ++) {
      for (int j = 0; j < board[i].length; j ++) {
        if (board[i][j] >= 'A' && board[i][j] <= 'Z') {
          System.out.print((char)(board[i][j]));
        }
        else {
          if (board[i][j] == 1 && j > 0) { //HIT
            System.out.print("X");
          }
          else if (board[i][j] == 2 && j > 0) { //MISS
            System.out.print("O");
          }
          else {
            if (j == 0) {
              System.out.print(board[i][j]);
            }
            else {
              if (concealed || board[i][j] == 0) {
                System.out.print(" ");
              }
              else {
                System.out.print(board[i][j] * -1);
              }
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
  public static boolean attack(String input, int[][] board, ArrayList<Battleship> beingAttacked) { //ASSUME IT IS IN A1 FORMAT
    int row = Integer.parseInt(input.substring(1, input.length()));
    int col = input.charAt(0) - 64;

    if (board[row][col] >= 0) { // MISS
      if (board[row][col] == 0) {
        board[row][col] = 2;
      }
      return false;
    }
    else { // HIT!!!!!
      int battleshipHit = (board[row][col] * -1) - 1;
      beingAttacked.get(battleshipHit).minusOne();
      board[row][col] = 1;
      return true;
    }
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
