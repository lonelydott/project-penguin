import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Draw {
    public static void main(String[] args) throws Exception {
        PrintShips(6);
    }
    public static void PrintShips(int size) {
      try{
        int count = 0;
        File ascii = new File("Ascii.txt");
        Scanner text = new Scanner(ascii);
        while(text.hasNextLine()) {
            count++;
            if (count % (size) == 0) {
                Thread.sleep(250);
                clear();
                // System.out.print("\033[H\033[2J");

                // System.out.flush();
            }
            System.out.println(text.nextLine());
        }
      }
      catch (Exception  ex) {
        //File not found
      }
    }

    public static void clear() throws Exception{
        System.out.print("\u001b[2J");
        System.out.print("\u001b[0;0f");
        //Thread.sleep(10);
    }
}
