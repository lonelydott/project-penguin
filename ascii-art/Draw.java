import java.io.File;
import java.util.Scanner;

public class Draw {
    public static void main(String[] args) throws Exception {
        PrintShips(6);
    }
    public static void PrintShips(int size) throws Exception{
        int count = 0;
        File ascii = new File("Ascii.txt");
        Scanner text = new Scanner(ascii);
        while(text.hasNextLine()) {
            System.out.println(text.nextLine());
            count++;
            if (count % size == 0) {
                //System.out.print("\033[H\033[2J");  
                System.out.flush();  
                Thread.sleep(200);
            }
        }
    }
}

