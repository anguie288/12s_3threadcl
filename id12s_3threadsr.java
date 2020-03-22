

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



public class id12s_3threadsr {
    
    public static void main(String[] args) {

        ServerSocket server = null;
        
        try {
            server = new ServerSocket(32000);
            server.setReuseAddress(true);
            
            // server socket null
            // try for setting the server
            // while for connected server - new object - new thread
            
            while (true) {
                
                Socket client = server.accept();
                
                System.out.println("New client connected " + client.getInetAddress().getHostAddress());
                
                clienthandler clientsockth = new clienthandler(client); // messages classes

                
                //new Thread(/*clientsock*/).start(); // thread of handler
                
                clientsockth.start();
                
            }
        } catch (IOException e) {e.printStackTrace();} 
          finally {   if (server != null) {
                            try { server.close();} 
                            catch (IOException e) {e.printStackTrace();}
                       }
                   }
    }

    
    private static class clienthandler extends Thread  {

        private final Socket clientsocket;

        public clienthandler(Socket socket) {
            this.clientsocket = socket;      // get client connection
        }

        @Override
        public void run() {   // run printers and writers
            
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                out = new PrintWriter(clientsocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
                
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.printf("Sent from the client: %s\n", line);
                    out.println(line);
                }
            } 
            
            catch (IOException e) {e.printStackTrace();} 
            
             finally {  try {
                               if (out != null) {out.close();}
                               if (in != null) {
                                   in.close();
                                   clientsocket.close();}  } 
                        
                        catch (IOException e) {e.printStackTrace();}
                     }
        }
    }
}

