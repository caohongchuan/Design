
import java.net.*;
import java.io.*;
import java.util.*;

public class Server{ 
    int port;
    List<Socket> clients;
    ServerSocket server;
	public static void main(String[] args){
		new Server();
    }
    
	public Server(){   
        try{
        
            port=9000;
            clients=new ArrayList<Socket>();
            server=new ServerSocket(port);
                
                while(true){
                    Socket socket=server.accept();
                    if(!clients.contains(socket)){
                        System.out.println(socket.getInetAddress());
                        System.out.println(socket.getPort());
                        clients.add(socket);
                        Mythread mythread=new Mythread(socket);
                        mythread.start();
                    }
                }
            
            
        }catch(Exception ex){ 
            ex.printStackTrace();
        }
   }

    class Mythread extends Thread{
        Socket ssocket;
        private BufferedReader br;  
        private PrintWriter pw;  
        public  String msg; 
        public Mythread(Socket s){
            ssocket=s;
        }

        public void run(){
                    
            try{
                br = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));  
                String msg1 = ssocket.getInetAddress() +" "+ clients.size(); 
                System.out.println(msg1);

                // sendMsg();
                while ((msg = br.readLine()) != null) {  
                    
                        if(msg.equals("CLOSE")){
                            Iterator<Socket> it = clients.iterator();
                            while(it.hasNext()){
                                Socket str = (Socket) it.next();
                                if(ssocket.equals(str)){
                                    it.remove();
                                }        
                            }
                        }else{
                            sendMsg(ssocket); 
                        }
                         
                    
                } 
            }catch(Exception ex){
                ex.printStackTrace();
            }				
        }
        
        public void sendMsg(Socket add){
                try{
                    System.out.println(add.getInetAddress()+"   "+msg);
                
                    for(int i = clients.size() - 1; i >= 0; i--){
                        if(add.getInetAddress().equals(clients.get(i).getInetAddress())){
                            continue;
                        }else{
                            pw=new PrintWriter(clients.get(i).getOutputStream(),true);
                            pw.println(msg);
                            pw.flush();
                        }
                    }
                
                }catch(Exception ex){
                    ex.printStackTrace();
                }
        }	

    } 
}