import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


/**
*   @author Hongchuan CAO
*   @version 1.0
*   @since 4/29/2019
*/

public class Service{

    public static final int SERVER_PORK = 10001;

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORK);
        System.out.println("Wait connect......");
        Socket socket;
        BufferedReader bufferedReader;


        while (true) {
            socket = serverSocket.accept();
            System.out.println("Connect successful!");
            Thread writerThread = new Thread(new MyServer(socket));
            writerThread.start();

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            try {
                String msg;
                while ((msg = bufferedReader.readLine()) != null) {
                    System.out.println("--Client--:" + msg);
                }
            } catch (IOException e) {
                try {
                    if (!socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e1) {
                    System.out.println("ERROR");
                }
            }
            System.out.println("Connection closed. "+ " Wait for the next connetion....");
        }

        // if(!serverSocket.isClosed()){
        //     serverSocket.close();
        // }
    }
}

class MyServer implements Runnable {
    private Socket socket = null;
    private PrintWriter printWriter;
    private Scanner scanner;

    public MyServer(Socket socket) throws IOException {
        this.socket = socket;
        scanner = new Scanner(System.in);
        printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    }

    @Override
    public void run() {
        String msg;
        try {
            while ((msg = scanner.nextLine()) != null) {
                if (msg.equals("-1")) {
                    if (!socket.isClosed()) {
                        System.out.println("Conncet closed.");
                        socket.close();
                    }
                    break;
                }
                printWriter.println(msg);
            }
        } catch (IOException io) {
            System.out.println("ERROR");
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }
}