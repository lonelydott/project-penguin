import java.util.*;
import java.io.*;
public class PowerUp{
  private int numberNukes;
  private int numberTraps;
  private int numberSonars;

  public PowerUp() {
    numberNukes = 2;
    numberTraps = 1;
    numberSonars = 2;
  }
  public PowerUp(int nukes, int traps, int sonars) {
    numberNukes = nukes;
    numberTraps = traps;
    numberSonars = sonars;
  }
  public int getNukes() {
    return numberNukes;
  }
  public void useNuke() {
    numberNukes --;
  }
  public int getTraps() {
    return numberTraps;
  }
  public int getSonars() {
    return numberSonars;
  }
  public void useSonar() {
    numberSonars --;
  }

  //area of impact: 1x1
  public static boolean missile(String input, int[][] board, ArrayList<Battleship> beingAttacked) { //ASSUME IT IS IN A1 FORMAT
    //DATA FROM COORDINATE
    int row = Integer.parseInt(input.substring(1, input.length()));
    int col = input.charAt(0) - 64;

    if (board[row][col] >= 0) { // MISS
      if (board[row][col] == Game.EMPTY) { //check if tile is empty
        board[row][col] = Game.MISS; //turn tile to O (miss)
      }
      if (board[row][col] == Game.TRAP) {
        board[row][col] = Game.HIT;
        return true;
      }
      return false;
    }
    else { // HIT!!!!!
      int battleshipHit = (board[row][col] * -1) - 1; //Identify battleship
      beingAttacked.get(battleshipHit).minusOne(); //Find the battleship that got caught, and subtract 1 from its length.
      board[row][col] = Game.HIT; //turn tile to X (hit)
      return true;
    }
  }

  //area of impact: +, row & col indicate top left box of the nuke
  //is this in real coordinates or actual indexes?
  public static boolean nuke(String input, int[][] board, ArrayList<Battleship> beingAttacked) { //ASSUME IT IS IN A1 FORMAT
    int row = Integer.parseInt(input.substring(1, input.length()));
    int col = input.charAt(0) - 64;

    boolean bool = false;
    if (nukeHelper(row, col, board, beingAttacked)) {
      bool = true;
    }
    if (nukeHelper(row - 1, col, board, beingAttacked)) {
      bool = true;
    }
    if (nukeHelper(row + 1, col, board, beingAttacked)) {
      bool = true;
    }
    if (nukeHelper(row, col - 1, board, beingAttacked)) {
      bool = true;
    }
    if (nukeHelper(row, col + 1, board, beingAttacked)) {
      bool = true;
    }
    return bool;
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
  public static Battleship trap(String xcor, String ycor){ //creates a 1x1 ship as bait to throwoff the enemy player
    Battleship bait = new Battleship(xcor, ycor);
    return bait;
  }
  public static String sonar(String coord, int[][] board, Player player){ //creates a 1x1 ship as bait to throwoff the enemy player
    int row = Integer.parseInt(coord.substring(1, coord.length()));
    int col = coord.charAt(0) - 64;
    for (int i = 0; i < board.length; i ++) {
      for (int j = 0; j < board[i].length; j ++) {
        if (board[i][j] >= 'A' && board[i][j] <= 'Z') { //print column indicators
          System.out.print((char)(board[i][j]));
        }
        else {
          if (board[i][j] == Game.HIT && j > 0) { //print hits
            System.out.print("X");
          }
          else if (board[i][j] == Game.MISS && j > 0) { //print misses
            System.out.print("O");
          }
          else {
            if (j == 0) {
              System.out.print(board[i][j]); //print row indicators
              if (board[i][j] < 10 && j >= 10) { // spacing
                System.out.print(" ");
              }
            }
            else {
              if (board[i][j] == Game.EMPTY || !(i <= row + 1 && i >= row - 1 && j <= col + 1 && j >= col - 1)) {
                System.out.print(" "); //print empties (concealed)
              }
              else if (board[i][j] == Game.TRAP) {
                System.out.print("T"); //prints traps
              }
              else {
                System.out.print(board[i][j] * -1); //print ships (not concealed)
                if (player.robotCheck()) {
                  player.setSonared("" + (char)(j + 64) + i);
                }
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
    return "";
  }

  public static boolean nukeHelper(int row, int col, int[][] board, ArrayList<Battleship> beingAttacked) { //ASSUME IT IS IN A1 FORMAT
    if (row == 0 || col == 0 || row == board.length || col == board.length) {
      return false;
    }
    if (board[row][col] >= 0) { // MISS
      if (board[row][col] == 0) { //check if tile is empty
        board[row][col] = 2; //turn tile to O (miss)
      }
      if (board[row][col] == 3) {
        board[row][col] = 1;
        return true;
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
/*
  String Dragon = """
                      ______________
        ,===:'.,            `-._
            `:.`---.__         `-._
                `:.     `--.         `.
                  \.        `.         `.
          (,,(,    \.         `.   ____,-`.,
      (,'     `/   \.   ,--.___`.'
  ,  ,'  ,--.  `,   \.;'         `
    `{D, {    \  :    \;
      V,,'    /  /    //
      j;;    /  ,' ,-//.    ,---.      ,
      \;'   /  ,' /  _  \  /  _  \   ,'/
            \   `'  / \  `'  / \  `.' /
            `.___,'   `.__,'   `.__,'
      """;


  String[] arr0 = new String[5];
  arr0[0] = "     _    _";
  arr0[1] = "  __|_|__|_|___";
  arr0[2] = "|o o o o o o o o /  ";
  arr0[3] = "~'`~'`~'`~'`~'`~'`~'`~";

  String[] arr1 = new String[11];
  String VikingShip = """
                    ~.
            Ya...___|__..aab     .   .
              Y88a  Y88o  Y88a   (     )
              Y88b  Y88b  Y88b   `.oo'
              :888  :888  :888  ( (`-'
      .---.    d88P  d88P  d88P   `.`.
    / .-._)  d8P'"""|"""'-Y8P      `.`.
    ( (`._) .-.  .-. |.-.  .-.  .-.   ) )
    \ `---( O )( O )( O )( O )( O )-' /
      `.    `-'  `-'  `-'  `-'  `-'  .' CJ
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      """;

  String[] arr2 = new String[21];
  String BlackPearl = """
      --    .-""-.
      ) (     )
    (   )   (
        /     )
      (_    _)                     0_,-.__
        (_  )_                     |_.-._/
          (    )                    |_--..\
          (__)                     |__--_/
        |''   ``\                   |
        |        \                  |      /b.
        |         \  ,,,---===?A`\  |  ,==y'
      ___,,,,,---==""\        |M] \ | ;|\ |>
              _   _   \   ___,|H,,---==""""bno,
      o  O  (_) (_)   \ /          _     AWAW/
                        /         _(+)_  dMM/
        \@_,,,,,,---=="   \      \\|//  MW/
  --''''"                         ===  d/
                                      //
                                      ,'__________________________
      \    \    \     \               ,/~~~~~~~~~~~~~~~~~~~~~~~~~~~
                            _____    ,'  ~~~   .-""-.~~~~~~  .-""-.
        .-""-.           ///==---   /`-._ ..-'      -.__..-'
              `-.__..-' =====\\\\\\ V/  .---\.
    PGMG                 ~~~~~~~~~~~~, _',--/_.\  .-""-.
                              .-""-.___` --  \|         -.__..-


      """;


  public static void PrintShips() {
    int shipNum = RandomNumGen(3);
    String ship = "arr" + shipNum;
    //Thread.sleep(100);
    //continue printing everything except waves, decreasing by one everytime(putting strings in array? or using Scanner?)
    //part where I only print alternating waves.
    //limit of while loop
    int count = 0;
    for(int index = arr0.length - 2; index > 0; index--) {
      System.out.println(ship[index]);
      System.out.println(ship[ship.length - 1]); //wave
      //sys out reflection of wave
    }

  }

  public static int RandomNumGen(int n) {
    Random Rand = new Random();
    int randInt = Rand.nextInt(n);
    return randInt;
  }
  */
}
