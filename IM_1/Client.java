import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
*   @author Hongchuan CAO
*   @version 1.0
*   @since 4/29/2019
*/
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(args[0], 10001);
        System.out.println("Connect Successful. "+" ( Your can input -1 to exit ) ");

        //get the new conncet and start the new thread
        new Thread(new MyClient(socket)).start();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            String msg;
            while ((msg = bufferedReader.readLine()) != null) {
                System.out.println("--Server--:" + msg);
            }
        } catch (IOException e) {
            System.out.println("Warning:Fail.");
            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e1) {
                System.out.println("Warning:Close.");
            }
        }
        System.exit(1);
    }
}

class MyClient implements Runnable{
    private Socket socket = null;
    private PrintWriter printWriter;
    private Scanner scanner;

    public MyClient(Socket socket) throws IOException {
        this.socket = socket;
        scanner = new Scanner(System.in);
        printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    }

    @Override
    public void run() {
        String msg;
        try {
            while ((msg = scanner.nextLine()) != null) {
                if(!socket.isClosed()){
                    System.out.println("Connecting:");
                }
                
                if(socket.isClosed()) {
                    break;
                } else {
                    if(msg.equals("-1")) {
                        socket.close();
                        break;
                    }
                }
                printWriter.println(msg); //sent to server
            }
        } catch (Exception e) {
           System.out.println("ERROR:Wrong");
        }
        
    }
}