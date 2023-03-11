public class Battleship {
  private static int length;
  //coordinates of front of ship
  private static int[] front;
  //coordinates of back of ship
  private static int[] back;

  public Battleship(int length, int frontX, int frontY, int backX, int backY) {
    length = length;
    front = new int[] {frontX, frontY};
    back = new int[] {backX, backY};
  }


}
