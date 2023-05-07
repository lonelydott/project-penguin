import java.util.*;
import java.util.ArrayList;
public class Game {
  private static ArrayList<Player> playerList;
  private static String numberPlayers;
  private static int[][] playerOneBoard;
  private static ArrayList<Battleship> playerOneShips;
  private static int playerOneShipsLeft;
  private static int[][] playerTwoBoard;
  private static ArrayList<Battleship> playerTwoShips;
  private static int playerTwoShipsLeft;
  private static int rows = 0;
  private static int columns = 0;
  private static boolean powerUpEnabled;
  private static boolean containsCPU;
  private static boolean powerUpChosen;
  //private static ArrayList<String> spotsTaken;
  private static int numberShips = 0;
  //PLAYER BOARDS: 2d array of numbers. 0 = untouched, 1 = hit, 2 = miss.
  //PLAYER SHIPS LISTS: Keep track of every ship created. Access each ship via this list.
  //SHIPS LEFT INT: WIN/LOSE CONDITION. WHEN A SHIP HAS 0 LENGTH (ALL TILES ARE GUESSED), SUBTRACT 1


  /*
  GOALS
  improve isValid cor code
  */
  public static void main(String[] args) {
    int turn = 0;
    //To alternate between player turns
    //Board setup
    //Game setup
    String input = "";
    Scanner in = new Scanner(System.in);
    //Keep track of player inputs

    //INITIAL GAME SETUP
    clearTerminal();
    gotoTop();
    if (args.length == 5) {
      rows = Integer.parseInt(args[0]);
      columns = Integer.parseInt(args[1]);
      numberShips = Integer.parseInt(args[2]);
      powerUpEnabled = (args[3].equals("Y"));
      playerList = new ArrayList<Player>(Integer.parseInt(args[5]));
    }
    else {
      System.out.println("How many rows?"); //ROWS
      String inputRows = in.next();
      while(!isValid(inputRows, 1, 26)) {
        System.out.println("Please enter a valid amount of rows! (1-26)");
        inputRows = in.next();
      }
      rows = Integer.parseInt(inputRows);

      System.out.println("How many columns?"); //COLUMNS
      String inputCols = in.next();
      while(!isValid(inputCols, 1, 26)) {
        System.out.println("Please enter a valid amount of columns! (1-26)");
        inputCols = in.next();
      }
      columns = Integer.parseInt(inputCols);

      System.out.println("How many ships per player? (perferably less than 10)"); //SHIPS
      String inputNumberShips = in.next();
      while(!isValid(inputNumberShips, 1, 10)) {
        System.out.println("Please enter a valid amount of ships! (1-10)");
        inputNumberShips = in.next();
      }
      numberShips = Integer.parseInt(inputNumberShips);

      System.out.println("Enable power ups? (Y/N)");
      input = in.next();
      while(!((input.equals("Y")) || input.equals("N"))) {
        System.out.println("Please enter Y/N!");
        input = in.next();
      }
      powerUpEnabled = (input.equals("Y"));

      System.out.println("How many players?"); //ROWS
      numberPlayers = in.next();
      while(!isValid(numberPlayers, 2, -1)) {
        System.out.println("Please enter a valid amount of players!");
        inputRows = in.next();
      }
      playerList = new ArrayList<Player>(Integer.parseInt(numberPlayers));

      System.out.println("Add a CPU player? (Y/N)");
      input = in.next();
      while(!((input.equals("Y")) || input.equals("N"))) {
        System.out.println("Please enter Y/N!");
        input = in.next();
      }
      containsCPU = (input.equals("Y"));
    }

    for (int i = 0; i < Integer.parseInt(numberPlayers); i ++) {
      clearTerminal();
      gotoTop();
      playerList.add(new Player(rows, columns, numberShips, powerUpEnabled, true));
      System.out.println("PLAYER " + (i + 1) + ": CREATE YOUR SHIPS");
      place(playerList.get(i));
    }

    PowerUp playerOne = new PowerUp();
    PowerUp playerTwo = new PowerUp();
    int powerUpSelector = 0;

    playerOneShipsLeft = numberShips;
    playerTwoShipsLeft = numberShips;
/*
    System.out.println("Rows (at most 26): " + rows);
    System.out.println("Columns (at most 26): " + columns);
    System.out.println("Ships: " + numberShips);
*/
    playerOneBoard = createBoard(rows, columns);
    playerTwoBoard = createBoard(rows, columns);
    playerOneShips = new ArrayList<Battleship>(numberShips);
    playerTwoShips = new ArrayList<Battleship>(numberShips);


    // System.out.println("PLAYER ONE: CREATE YOUR SHIPS"); //PLAYER ONE
    // place(playerOneBoard, playerOneShips, playerOne);
    //
    // //NOTE: Player two setup is very similar, if not identical, to player one setup. Therefore, issues with player 1 are consistent with issues with player 2.
    // //NOTE: Due to similarities between each player, transforming this into a function could be possible (3+ players?) (potential custom gamemode?) (allow players to choose who to attack?)
    // System.out.println("PLAYER TWO: CREATE YOUR SHIPS"); //PLAYER TWO
    // place(playerTwoBoard, playerTwoShips, playerTwo);

    //BOMBING PHASE (AFTER PLACING SHIPS)
    while(playerOneShipsLeft > 0 && playerTwoShipsLeft > 0){ //GAME CONTINUES AS LONG AS ALL PLAYERS HAVE SHIPS LEFT ON THEIR BOARD
      clearTerminal();
      gotoTop();
      turn ++; // odd turns = player 1, even turns = player 2
      if (turn % 2 == 0) {//player 2's turn (even)
        //ANNOUNCING GAME DEETS
        System.out.println("<PLAYER TWO'S TURN>");
        printBoard(playerOneBoard, true);
        System.out.println("<PLAYER ONE'S BOARD>");

        //PROMPT TO CHOOSE TILE
        if (powerUpEnabled) {
          System.out.println("Would you like to use a power up?  (Y/N)");
          input = in.next();
          while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")){
            System.out.println("Would you like to use a power up?  (Y/N)");
            input = in.next();
          }
          if (input.equalsIgnoreCase("Y")) {
            System.out.println("Which power up?");
            System.out.println("(1) Nuke " + "(" + playerTwo.getNukes() + " remaining)");
            System.out.println("(2) Sonar " + "(" + playerOne.getSonars() + " remaining)");
            try {
              powerUpSelector = in.nextInt();
              if (powerUpSelector == 1) {
                if (playerTwo.getNukes() > 0) {
                  powerUpChosen = true;
                }
                else {
                  System.out.println("You do not have any more nukes.");
                }
              }
              if (powerUpSelector == 2) {
                if (playerTwo.getSonars() > 0) {
                  powerUpChosen = true;
                }
                else {
                  System.out.println("You do not have any more sonars.");
                }
              }
            }
            catch (Exception e) {
              input = in.next();
              powerUpChosen = false;
            }
          }
        }
        System.out.println("Please choose coordinates (EX: A1)");
        input = in.next();
        while (!isValidCoordinate(input)) {
          System.out.println("Please choose valid coordinates");
          input = in.next();
        }

        //EVALUATING TILE
        if (powerUpChosen) {
          if (powerUpSelector == 1) {//Choosing nuke
            if (PowerUp.nuke(input, playerOneBoard, playerOneShips)) {//if true, then battleship is hit
              clearTerminal();
              gotoTop();
              System.out.println("You hit a ship!");
              printBoard(playerOneBoard, true);

              if (isEmpty(playerOneShips)) {
                playerOneShipsLeft = 0;
              }
            }
            else { //missed
              clearTerminal();
              gotoTop();
              System.out.println("You missed!");
              printBoard(playerOneBoard, true);
            }
            playerTwo.useNuke();
          }
          if (powerUpSelector == 2) {
            clearTerminal();
            gotoTop();
            System.out.println("Used a sonar on " + input + "!");
            PowerUp.sonar(input, playerOneBoard);
          }
          powerUpChosen = false;
        }
        else {
          int battleshipHit = playerOneBoard[Integer.parseInt(input.substring(1, input.length()))][input.charAt(0) - 64] * -1 - 1;
          if (PowerUp.missile(input, playerOneBoard, playerOneShips)) {//if true, then battleship is hit
            clearTerminal();
            gotoTop();
            System.out.println("You hit a ship!");
            printBoard(playerOneBoard, true);

            //NOTE: the attack() method automatically changes internal length of battleship
            if (battleshipHit != -4 && playerOneShips.get(battleshipHit).getLength() == 0) { //checking for last hit
              playerOneShipsLeft --; //last hit = subtract amount of remaining ships (lose condition)
            }
          }
          else { //missed
            clearTerminal();
            gotoTop();
            System.out.println("You missed!");
            printBoard(playerOneBoard, true);
          }
        }

        //Game pause, allow players to see turn result
        System.out.println("PRESS ENTER TO CONTINUE");
        in.nextLine();
        in.nextLine();
      }
      //NOTE: Due to similarity between player 1 and player 2 code, notes will be kept minimal. If there are any problem with player 1, refer to player 2 code.
      else {//player 1's turn (odd)
        System.out.println("<PLAYER ONE'S TURN>");
        printBoard(playerTwoBoard, true);
        System.out.println("<PLAYER TWO'S BOARD>");


        if (powerUpEnabled) {
          System.out.println("Would you like to use a power up?  (Y/N)");
          input = in.next();
          while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")){
            System.out.println("Would you like to use a power up?  (Y/N)");
            input = in.next();
          }
          if (input.equalsIgnoreCase("Y")) {
            System.out.println("Which power up?");
            System.out.println("(1) Nuke " + "(" + playerOne.getNukes() + " remaining)");
            System.out.println("(2) Sonar " + "(" + playerOne.getSonars() + " remaining)");
            try {
              powerUpSelector = in.nextInt();
              if (powerUpSelector == 1) {
                if (playerOne.getNukes() > 0) {
                  powerUpChosen = true;
                }
                else {
                  System.out.println("You do not have any more nukes.");
                }
              }
              if (powerUpSelector == 2) {
                if (playerTwo.getSonars() > 0) {
                  powerUpChosen = true;
                }
                else {
                  System.out.println("You do not have any more sonars.");
                }
              }
            }
            catch (Exception e) {
              input = in.next();
              powerUpChosen = false;
            }
          }
        }
        System.out.println("Please choose coordinates (EX: A1)");
        input = in.next();
        while (!isValidCoordinate(input)) {
          System.out.println("Please choose valid coordinates");
          input = in.next();
        }

        if (powerUpChosen) {
          if (powerUpSelector == 1) {
            if (PowerUp.nuke(input, playerTwoBoard, playerTwoShips)) {//if true, then battleship is hit
              clearTerminal();
              gotoTop();
              System.out.println("You hit a ship!");
              printBoard(playerTwoBoard, true);

              if (isEmpty(playerTwoShips)) {
                playerTwoShipsLeft = 0;
              }
            }
            else { //missed
              clearTerminal();
              gotoTop();
              System.out.println("You missed!");
              printBoard(playerTwoBoard, true);
            }
            playerOne.useNuke();
          }
          if (powerUpSelector == 2) {
            clearTerminal();
            gotoTop();
            System.out.println("Used a sonar on " + input + "!");
            PowerUp.sonar(input, playerTwoBoard);
          }
          powerUpChosen = false;
        }
        else {//continue to regular shots
          int battleshipHit = playerTwoBoard[Integer.parseInt(input.substring(1, input.length()))][input.charAt(0) - 64] * -1 - 1;
          if (PowerUp.missile(input, playerTwoBoard, playerTwoShips)) {//if true, then battleship is hit
            clearTerminal();
            gotoTop();
            System.out.println("You hit a ship!");
            printBoard(playerTwoBoard, true);

            //NOTE: the attack() method automatically changes internal length of battleship
            if (battleshipHit != -4 && playerTwoShips.get(battleshipHit).getLength() == 0) { //checking for last hit
              playerTwoShipsLeft --; //last hit = subtract amount of remaining ships (lose condition)
            }
          }
          else { //missed
            clearTerminal();
            gotoTop();
            System.out.println("You missed!");
            printBoard(playerTwoBoard, true);
          }
        }

        System.out.println("PRESS ENTER TO CONTINUE");
        in.nextLine();
        in.nextLine();
      }
    }

    //DISPLAY GAME RESULTS
    if (playerOneShipsLeft == 0) {
      System.out.println("PLAYER TWO WINS!");
      System.out.println("THEIR BOARD:");
      printBoard(playerTwoBoard, false);
    }
    else if (playerTwoShipsLeft == 0) {
      System.out.println("PLAYER ONE WINS!");
      System.out.println("THEIR BOARD:");
      printBoard(playerOneBoard, false);
    }

    System.out.println("This game took " + turn + " turns to complete.");
  }


  public static boolean isValid(String x, int low, int high){ //x is the String being tested, low to high is the numeric range (inclusive on both ends)
    int y;
    try{
      y = Integer.parseInt(x);
    }
    catch(NumberFormatException e){
      y = 0;
    }
    if (y >= low && y <= high){
      return true;
    }
    else{
      if (y >= low && high == -1) {
        return true;
      }
      return false;
    }
  }
  public static void place(Player player) {
    Scanner in = new Scanner(System.in);
    String front = "";
    String back = "";
    printBoard(player.getBoard(), false);
    for (int i = 0; i < numberShips; i ++) {
      //SET UP COORDINATES
      System.out.println("Front of Ship " + (i + 1) + " with length " + (i+2) + " (A1 format): ");
      front = in.next();
      while (!isValidCoordinate(front)) {
        System.out.println("Please enter in valid A1 format");
        front = in.next();
      }


      System.out.println("Back of Ship " + (i + 1) + " with length " + (i+2) + " (A1 format): (type \"cancel\" to reset coordinates)");
      back = in.next();
      while (!isValidCoordinate(back) || isDiagonal(front, back) || !lengthCheck(front, back, i+2) || back == "cancel" || overlap(front, back, player.getShipList())) {
        if (back.equals("cancel")) {
          System.out.println("Front of Ship " + (i + 1) + " with length " + (i+2) + " (A1 format): ");
          front = in.next();
          while (!isValidCoordinate(front)) {
            System.out.println("Please enter in valid A1 format");
            front = in.next();
          }
        }
        System.out.println("Please choose a valid coordinate for length " + (i+2));
        back = in.next();
      }

      if (front.charAt(0) > back.charAt(0) || Integer.parseInt(front.substring(1)) > Integer.parseInt(back.substring(1))) {
        String swap = front;
        front = back;
        back = swap;
      }

      //FINISHING GAME SETUP: finishing board layout with ships and populating arraylist
      player.getShipList().add(new Battleship(front, back)); //arraylist
      player.getShipList().get(i).place(player.getBoard(), (i + 1) * -1); //placing ships on board... this place is a place method from another class
//      System.out.println(Arrays.toString(playerOneBoard));
      clearTerminal();
      gotoTop();
      printBoard(player.getBoard(), false);
    }

    if (powerUpEnabled) {
      for (int i = 0; i < player.getPowerUp().getTraps(); i ++) {
        System.out.println("Front of Trap " + (i + 1) + " with length " + (i+2) + " (A1 format): ");
        front = in.next();
        while (!isValidCoordinate(front)) {
          System.out.println("Please enter in valid A1 format");
          front = in.next();
        }


        System.out.println("Back of Trap " + (i + 1) + " with length " + (i+2) + " (A1 format): (type \"cancel\" to reset coordinates)");
        back = in.next();
        while (!isValidCoordinate(back) || isDiagonal(front, back) || !lengthCheck(front, back, i+2) || back == "cancel" || overlap(front, back, player.getShipList())) {
          if (back.equals("cancel")) {
            System.out.println("Front of Trap " + (i + 1) + " with length " + (i+2) + " (A1 format): ");
            front = in.next();
            while (!isValidCoordinate(front)) {
              System.out.println("Please enter in valid A1 format");
              front = in.next();
            }
          }
          System.out.println("Please choose a valid coordinate for length " + (i+2));
          back = in.next();
        }

        if (front.charAt(0) > back.charAt(0) || Integer.parseInt(front.substring(1)) > Integer.parseInt(back.substring(1))) {
          String swap = front;
          front = back;
          back = swap;
        }
        Battleship trap = new Battleship(front, back);
        trap.place(player.getBoard(), 3);

        clearTerminal();
        gotoTop();
        printBoard(player.getBoard(), false);
      }
    }

    //GAME SET UP: Announcing final board to player 1
    System.out.println("YOUR FINAL BOARD");
    for (int i = 0; i < player.getShipList().size(); i ++) { //coordinates of every ship
      System.out.println("(Battleship " + (i + 1) + ") " + player.getShipList().get(i));
    }
    System.out.println("PRESS ENTER TO CONTINUE");
    in.nextLine();
    in.nextLine();

    clearTerminal();
    gotoTop();
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

  //NOTE: 0 = untouched, 1 = hit, 2 = miss, -n = nth battleship, 3 = trap???
  //NOTE: Purpose is to be convert board variables (purely for information) into human-friendly/easier-to-digest data
  public static void printBoard(int[][] board, boolean concealed) {//concealed is true = hide battleships. concealed is false = show battleships.
    for (int i = 0; i < board.length; i ++) {
      for (int j = 0; j < board[i].length; j ++) {
        if (board[i][j] >= 'A' && board[i][j] <= 'Z') { //print column indicators
          System.out.print((char)(board[i][j]));
        }
        else {
          if (board[i][j] == 1 && j > 0) { //print hits
            System.out.print("X");
          }
          else if (board[i][j] == 2 && j > 0) { //print misses
            System.out.print("O");
          }
          else {
            if (j == 0) {
              System.out.print(board[i][j]); //print row indicators
              if (board[i][j] < 10 && rows >= 10) { // spacing
                System.out.print(" ");
              }
            }
            else {
              if (concealed || board[i][j] == 0) {
                System.out.print(" "); //print empties (concealed)
              }
              else if (board[i][j] == 3) {
                System.out.print("T"); //prints traps
              }
              else {
                System.out.print(board[i][j] * -1); //print ships (not concealed)
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
  //NOTE: If you choose a tile that has already been chosen, it will count it as a miss. Fix?
  public static boolean attack(String input, int[][] board, ArrayList<Battleship> beingAttacked) { //ASSUME IT IS IN A1 FORMAT
    //DATA FROM COORDINATE
    int row = Integer.parseInt(input.substring(1, input.length()));
    int col = input.charAt(0) - 64;

    if (board[row][col] >= 0) { // MISS
      if (board[row][col] == 0) { //check if tile is empty
        board[row][col] = 2; //turn tile to O (miss)
      }
      return false;
    }
    else { // HIT!!!!!
      int battleshipHit = (board[row][col] * -1) - 1; //Identify battleship
      beingAttacked.get(battleshipHit).minusOne(); //Find the battleship that got caught, and subtract 1 from its length.
      board[row][col] = 1; //turn tile to X (hit)
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

  /*
  Valid format checker
  */
  private static boolean isValidCoordinate(String coord){ //params: s = input, r = rows, c = columns
    try {
      return (coord.length() >= 1 && coord.charAt(0) >= 'A' && coord.charAt(0) <= 'Z' && Integer.parseInt(coord.substring(1)) <= rows &&  Integer.parseInt(coord.substring(1)) >= 0);
      //checks if coord length > 1 A!, not A, char 1 is between A to Z, check second index if its less than or equal to rows, and greater than 0
    }
    catch (Exception e) {
      return false;
    }
    /*
    if (s.length() != 2 || s.length() != 3){
      return false;
    }
    else{
      if (s.charAt(0) < 65 || s.charAt(0) > 65 + r - 1){
        return false;
      }
      if (Integer.parseInt(s.substring(1)) > c - 1){
        return false;
      }
    }
    return true;
    */
  }
  private static boolean isDiagonal(String front, String back){
    try {
      return (front.charAt(0) != back.charAt(0) && Integer.parseInt(front.substring(1)) != Integer.parseInt(back.substring(1)));
    }
    catch (Exception e) {
      return true;
    }
  }
  private static boolean isEmpty(ArrayList<Battleship> list) {
    boolean bool = true;
      for (int i = 0; i < list.size() && bool; i ++) {
        if (list.get(i).getLength() != 0) {
          bool = false;
        }
      }
      return bool;
  }
  private static boolean lengthCheck(String front, String back, int length){
    try{
      if (front.charAt(0) != back.charAt(0) || Integer.parseInt(front.substring(1)) != Integer.parseInt(back.substring(1))){
//        System.out.println(Math.abs(front.charAt(0) - back.charAt(0)));
//        System.out.println(Math.abs (Integer.parseInt(front.substring(1)) - Integer.parseInt(back.substring(1))) );
        return (Math.abs(front.charAt(0) - back.charAt(0)) == length-1 || Math.abs(Integer.parseInt(front.substring(1)) - Integer.parseInt(back.substring(1))) == length-1);
      }
//      return (front.charAt(0) == back.charAt(0) && Math.abs(Integer.parseInt(front.substring(1)) - Integer.parseInt(back.substring(1))) != length) || (front.charAt(0) - back.charAt(0) != length && Math.abs(Integer.parseInt(front.substring(1)) == Integer.parseInt(back.substring(1))));
    }
    catch (Exception e) {
    }
    return false;
  }
  private static boolean overlap(String front, String back, ArrayList<Battleship> list) {
    boolean vertical = false;
    if (front.substring(0, 1).equals(back.substring(0, 1))) {
      vertical = true;
    }
    for (int index = 0; index < list.size(); index ++) {
      if (vertical) {
        if (front.charAt(0) - 64 <= list.get(index).getBackX() && front.charAt(0) - 64 >= list.get(index).getFrontX()) {
          return true;
        }
      }
      else {
        if (Integer.parseInt(front.substring(1)) <= list.get(index).getBackY() && Integer.parseInt(front.substring(1)) >= list.get(index).getFrontY()) {
          if (front.charAt(0) - 64 <= list.get(index).getBackX() && front.charAt(0) - 64 >= list.get(index).getFrontX()) {
            return true;
          }
        }
      }
    }
    return false;
  }
}
