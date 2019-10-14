/**
 * Use your wrong code 
 * @author Zheng Ying
 * @version 1.0
 */


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;



public class JavaCode {
    public static void main(String arg[])throws IOException{
        InputStream console_in = System.in;
        PrintStream console_out = System.out;

        BufferedInputStream in_t = new BufferedInputStream(new FileInputStream("JavaIn.txt"));
        PrintStream out_t = new PrintStream(new FileOutputStream("JavaOut.txt"));
        System.setIn(in_t);
        System.setOut(out_t);
 
 
        /**
         * Your wrong code
         */
        
         in_t.close();
         out_t.close();
         System.setIn(console_in);
         System.setOut(console_out);

    }
}