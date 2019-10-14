
/**
 * The code is used to create test data
 * @author Zheng Ying
 * @version 1.0
 */


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

public class DataCreat{
    private static int N = 1000;  //Number of test

    public static void CreateData(){
        Random rand = new Random();
        System.out.printf("%d\n",rand.nextInt(100));
    }
    public static void main(String arg[])throws IOException{        
        PrintStream console = System.out;

        PrintStream out = new PrintStream(new FileOutputStream("JavaIn.txt"));
        System.setOut(out);

        while(N > 0){
            N--;
            CreateData();
        }

        out.close();
        System.setOut(console);
    }
}