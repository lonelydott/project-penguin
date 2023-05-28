import java.util.*;
import java.util.ArrayList;
public class Game {
  private static ArrayList<Player> playerList;
  private static String numberPlayers;
  private static int rows = 0;
  private static int columns = 0;
  private static boolean powerUpEnabled;
  private static boolean containsCPU = false;
  private static boolean powerUpChosen;
  private static int numberShips = 0;
  public static final int EMPTY = 0;
  public static final int HIT = 1;
  public static final int MISS = 2;
  public static final int TRAP = 3;
  //createBoard
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
    if (args.length == 6) {
      rows = Integer.parseInt(args[0]);
      columns = Integer.parseInt(args[1]);
      numberShips = Integer.parseInt(args[2]);
      powerUpEnabled = (args[3].equals("Y"));
      playerList = new ArrayList<Player>(Integer.parseInt(args[4]));
      numberPlayers = args[4];
      containsCPU = args[5].equals("Y");
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
      while(!isValid(numberPlayers, 1, -1)) {
        System.out.println("Please enter a valid amount of players!");
        inputRows = in.next();
        numberPlayers = inputRows;
      }
      playerList = new ArrayList<Player>(Integer.parseInt(numberPlayers));

      if (!numberPlayers.equals("1")){
        System.out.println("Add a CPU player? (Y/N)");
        input = in.next();
        while(!((input.equals("Y")) || input.equals("N"))) {
          System.out.println("Please enter Y/N!");
          input = in.next();
        }
        containsCPU = (input.equals("Y"));
      }
      else {
        containsCPU = true;
      }

      if (containsCPU) {
        playerList = new ArrayList<Player>(Integer.parseInt(numberPlayers) + 1);
      }
    }

    for (int i = 1; i < Integer.parseInt(numberPlayers) + 1; i ++) {
      clearTerminal();
      gotoTop();
      playerList.add(new Player(rows, columns, numberShips, powerUpEnabled, false, "Player " + i));
      System.out.println(playerList.get(i - 1) + ": CREATE YOUR SHIPS");
      place(playerList.get(i - 1));
    }
    if (containsCPU) {
      playerList.add(new Player(rows, columns, numberShips, powerUpEnabled, true, "Player " + (Integer.parseInt(numberPlayers) + 1) + " (CPU)"));
      place(playerList.get(playerList.size() - 1));
    }


/*
    System.out.println("Rows (at most 26): " + rows);
    System.out.println("Columns (at most 26): " + columns);
    System.out.println("Ships: " + numberShips);
*/



    // System.out.println("PLAYER ONE: CREATE YOUR SHIPS"); //PLAYER ONE
    // place(playerOneBoard, playerOneShips, playerOne);
    //
    // //NOTE: Player two setup is very similar, if not identical, to player one setup. Therefore, issues with player 1 are consistent with issues with player 2.
    // //NOTE: Due to similarities between each player, transforming this into a function could be possible (3+ players?) (potential custom gamemode?) (allow players to choose who to attack?)
    // System.out.println("PLAYER TWO: CREATE YOUR SHIPS"); //PLAYER TWO
    // place(playerTwoBoard, playerTwoShips, playerTwo);

    //BOMBING PHASE (AFTER PLACING SHIPS)


    while(playerList.size() > 1){ //GAME CONTINUES AS LONG AS ALL PLAYERS HAVE SHIPS LEFT ON THEIR BOARD
      for (int i = 0; i < playerList.size(); i ++) {
        turn ++;
        clearTerminal();
        gotoTop();
        if (playerList.size() > 2) {
          if (playerList.get(i).robotCheck()) {
            int playerChosen = (int)(Math.random() * playerList.size());
            while (playerChosen != i) {
              playerChosen = (int)(Math.random() * playerList.size());
            }
            if (attack(playerList.get(i), playerList.get(playerChosen))) {
              playerList.remove(playerChosen);
            }
            clearTerminal();
            gotoTop();
          }
          else {
            System.out.println("<" + playerList.get(i) + "'S TURN>");
            System.out.println("Who do you want to attack?");
            input = in.next();
            while(!isValid(input, 1, playerList.size()) || Integer.parseInt(input) == i + 1) {
              System.out.println("Please enter a valid player! (1-" + playerList.size() + ")");
              input = in.next();
            }
            if (attack(playerList.get(i), playerList.get(Integer.parseInt(input) - 1))) {
              playerList.remove(Integer.parseInt(input) - 1);
            }
            clearTerminal();
            gotoTop();
          }

        }
        else {
          if (i == 0) {
            if (attack(playerList.get(0), playerList.get(1))) {
              playerList.remove(1);
            }
          }
          else {
            if (attack(playerList.get(1), playerList.get(0))) {
              playerList.remove(0);
            }
          }
        }
      }
    }

    //DISPLAY GAME RESULTS
    System.out.println(playerList.get(0) + " WINS!");
    System.out.println("THEIR BOARD:");
    printBoard(playerList.get(0).getBoard(), false);

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
      if (player.robotCheck()) {
        while (!isValidCoordinate(front) || !isValidCoordinate(back) || isDiagonal(front, back) || !lengthCheck(front, back, i+2) || overlap(front, back, player.getShipList())) {
          front = player.chooseRandomTile(rows, columns);
          back = player.chooseRandomTile(rows, columns);
          if (front.charAt(0) > back.charAt(0) || Integer.parseInt(front.substring(1)) > Integer.parseInt(back.substring(1))) {
            String swap = front;
            front = back;
            back = swap;
          }
        }
        System.out.println(front);
        System.out.println(back);
      }
      else {
      //SET UP COORDINATES
        System.out.println("Front of Ship " + (i + 1) + " with length " + (i+2) + " (A1 format): ");
        front = in.next();
        while (!isValidCoordinate(front)) {
          System.out.println("Please enter in valid A1 format");
          front = in.next();
        }


        System.out.println("Back of Ship " + (i + 1) + " with length " + (i+2) + " (A1 format): (type \"cancel\" to reset coordinates)");
        back = in.next();
        if (front.charAt(0) > back.charAt(0) || Integer.parseInt(front.substring(1)) > Integer.parseInt(back.substring(1))) {
          String swap = front;
          front = back;
          back = swap;
        }
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
          if (front.charAt(0) > back.charAt(0) || Integer.parseInt(front.substring(1)) > Integer.parseInt(back.substring(1))) {
            String swap = front;
            front = back;
            back = swap;
          }
        }
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
    System.out.println(player + "'S FINAL BOARD");
    for (int i = 0; i < player.getShipList().size(); i ++) { //coordinates of every ship
      System.out.println("(Battleship " + (i + 1) + ") " + player.getShipList().get(i));
    }
    if (!player.robotCheck()) {
      System.out.println("PRESS ENTER TO CONTINUE");
      in.nextLine();
      in.nextLine();
    }

    clearTerminal();
    gotoTop();
  }

  public static boolean attack(Player playerAttacking, Player playerTarget) {
    Scanner in = new Scanner(System.in);
    String input;
    int powerUpSelector = 0;
    //ANNOUNCING GAME DEETS
    System.out.println("<" + playerAttacking + "'S TURN>");
    printBoard(playerTarget.getBoard(), true);
    System.out.println("<" + playerTarget + "'S BOARD>");

    //PROMPT TO CHOOSE TILE
    if (playerAttacking.robotCheck()) {
      playerTarget.updateHeatMap();
      input = playerAttacking.chooseSmartTile(playerTarget.getHeatMap());
      // while (playerTarget.getBoard()[Integer.parseInt(input.substring(1, input.length()))][input.charAt(0) - 64] == HIT || playerTarget.getBoard()[Integer.parseInt(input.substring(1, input.length()))][input.charAt(0) - 64] == MISS) {
      //   input = playerAttacking.chooseSmartTile();
      // }

      System.out.println(playerAttacking + " is thinking...");
      wait(1000 + (int)(Math.random() * 2000));

      if (Math.random() < playerAttacking.getPowerUpProbability() && powerUpEnabled) {
        powerUpSelector = (int)(Math.random() * 3);
        if (powerUpSelector == 1) {
          if (playerAttacking.getPowerUp().getNukes() > 0) {
            powerUpChosen = true;
          }
        }
        if (powerUpSelector == 2) {
          if (playerAttacking.getPowerUp().getSonars() > 0) {
            powerUpChosen = true;
          }
        }
      }
      if (powerUpChosen && powerUpEnabled) {
        if (powerUpSelector == 1) {//Choosing nuke
          if (PowerUp.nuke(input, playerTarget.getBoard(), playerTarget.getShipList())) {//if true, then battleship is hit
            clearTerminal();
            gotoTop();
            System.out.println(playerAttacking + " hit a ship!");
            printBoard(playerTarget.getBoard(), true);
          }
          else { //missed
            clearTerminal();
            gotoTop();
            System.out.println(playerAttacking + " missed!");
            printBoard(playerTarget.getBoard(), true);
          }
          playerAttacking.getPowerUp().useNuke();
        }
        if (powerUpSelector == 2) {
          clearTerminal();
          gotoTop();
          System.out.println("Used a sonar on " + input + "!");
          PowerUp.sonar(input, playerTarget.getBoard());
        }
        powerUpChosen = false;
      }
      else {
        int battleshipHit = playerTarget.getBoard()[Integer.parseInt(input.substring(1, input.length()))][input.charAt(0) - 64] * -1 - 1;
        if (PowerUp.missile(input, playerTarget.getBoard(), playerTarget.getShipList())) {//if true, then battleship is hit
          clearTerminal();
          gotoTop();
          System.out.println(playerAttacking + " hit a ship!");
          printBoard(playerTarget.getBoard(), true);

          //NOTE: the attack() method automatically changes internal length of battleship
          if (battleshipHit != -4 && playerTarget.getShipList().get(battleshipHit).getLength() == 0) { //checking for last hit, -4 is a trap ship
            playerTarget.decreaseShipsLeft(); //last hit = subtract amount of remaining ships (lose condition)
          }
        }
        else { //missed
          clearTerminal();
          gotoTop();
          System.out.println(playerAttacking + " missed!");
          printBoard(playerTarget.getBoard(), true);
        }

      }
    }
    else {
      if (powerUpEnabled) {
        System.out.println("Would you like to use a power up?  (Y/N)");
        input = in.next();
        while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")){
          System.out.println("Would you like to use a power up?  (Y/N)");
          input = in.next();
        }
        if (input.equalsIgnoreCase("Y")) {
          System.out.println("Which power up?");
          System.out.println("(1) Nuke " + "(" + playerAttacking.getPowerUp().getNukes() + " remaining)");
          System.out.println("(2) Sonar " + "(" + playerAttacking.getPowerUp().getSonars() + " remaining)");
          try {
            powerUpSelector = in.nextInt();
            if (powerUpSelector == 1) {
              if (playerAttacking.getPowerUp().getNukes() > 0) {
                powerUpChosen = true;
              }
              else {
                System.out.println("You do not have any more nukes.");
              }
            }
            if (powerUpSelector == 2) {
              if (playerAttacking.getPowerUp().getSonars() > 0) {
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
      while (playerTarget.getBoard()[Integer.parseInt(input.substring(1, input.length()))][input.charAt(0) - 64] == HIT || playerTarget.getBoard()[Integer.parseInt(input.substring(1, input.length()))][input.charAt(0) - 64] == MISS) {
        System.out.println("You cannot choose the same tile twice");
        input = in.next();
      }

      //EVALUATING TILE
      if (powerUpChosen) {
        if (powerUpSelector == 1) {//Choosing nuke
          if (PowerUp.nuke(input, playerTarget.getBoard(), playerTarget.getShipList())) {//if true, then battleship is hit
            clearTerminal();
            gotoTop();
            System.out.println("You hit a ship!");
            printBoard(playerTarget.getBoard(), true);
          }
          else { //missed
            clearTerminal();
            gotoTop();
            System.out.println("You missed!");
            printBoard(playerTarget.getBoard(), true);
          }
          playerAttacking.getPowerUp().useNuke();
        }
        if (powerUpSelector == 2) {
          clearTerminal();
          gotoTop();
          System.out.println("Used a sonar on " + input + "!");
          PowerUp.sonar(input, playerTarget.getBoard());
        }
        powerUpChosen = false;
      }
      else {
        int battleshipHit = playerTarget.getBoard()[Integer.parseInt(input.substring(1, input.length()))][input.charAt(0) - 64] * -1 - 1;
        if (PowerUp.missile(input, playerTarget.getBoard(), playerTarget.getShipList())) {//if true, then battleship is hit
          clearTerminal();
          gotoTop();
          System.out.println("You hit a ship!");
          printBoard(playerTarget.getBoard(), true);

          //NOTE: the attack() method automatically changes internal length of battleship
          if (battleshipHit != -4 && playerTarget.getShipList().get(battleshipHit).getLength() == 0) { //checking for last hit
            playerTarget.decreaseShipsLeft(); //last hit = subtract amount of remaining ships (lose condition)
          }
        }
        else { //missed
          clearTerminal();
          gotoTop();
          System.out.println("You missed!");
          printBoard(playerTarget.getBoard(), true);
        }
      }
    }

    //Game pause, allow players to see turn result
    System.out.println("PRESS ENTER TO CONTINUE");
    in.nextLine();
    if (!playerAttacking.robotCheck()) {
      in.nextLine();
    }

    return isEmpty(playerTarget.getShipList());
  }

  public static int[][] createBoard(int row, int col) {//row, col = dimensions of board
    int[][] board = new int[row + 1][col + 1];
    for (int i = 1; i <= row; i ++) {
      for (int j = 1; j <= col; j ++) {
        board[i][j] = EMPTY; //fill everything with empty plots
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
          if (board[i][j] == HIT && j > 0) { //print hits
            System.out.print("X");
          }
          else if (board[i][j] == MISS && j > 0) { //print misses
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
              if (concealed || board[i][j] == EMPTY) {
                System.out.print(" "); //print empties (concealed)
              }
              else if (board[i][j] == TRAP) {
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
          if (Integer.parseInt(front.substring(1)) <= list.get(index).getBackY() && Integer.parseInt(back.substring(1)) >= list.get(index).getFrontY()) {
            return true;
          }
        }
      }
      else {
        if (Integer.parseInt(front.substring(1)) >= list.get(index).getFrontY() && Integer.parseInt(front.substring(1)) <= list.get(index).getBackY()) {
          if (front.charAt(0) - 64 <= list.get(index).getBackX() && back.charAt(0) - 64 >= list.get(index).getFrontX()) {
            return true;
          }
        }
      }
    }
    return false;
  }
}
