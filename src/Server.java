import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

public class Server {
    private static final int port=8000;
    private static final List<PrintWriter> clientOutputs = Collections.synchronizedList(new ArrayList<>());




    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            new Thread(() -> {
                try (BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in))) {
                    String line;
                    while ((line = consoleIn.readLine()) != null) {
                        broadcast(line);
                        System.out.println(line);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                new Thread(()->handleClient(clientSocket)).start();
            }







        }
    catch(IOException e){
            e.printStackTrace();
    }

    }
    public static void handleClient(Socket clientSocket){
        try{
            BufferedReader in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out=new PrintWriter(clientSocket.getOutputStream(),true);
            clientOutputs.add(out);
            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println("Message received: " + msg); // log once
                System.out.flush();                             // force console to update
                broadcast(msg);                                 // send to all clients
            }




        }
        catch(IOException e){
            System.out.println("Client Disconnected");
        }
        finally{
            try{
                clientSocket.close();
            }
            catch (IOException e){
                System.out.println("Client Disconnected");
            }
        }
    }
    public static void broadcast(String message){
        synchronized(clientOutputs){
            for(PrintWriter out:clientOutputs){

                out.println(message);
            }
        }
    }


}
