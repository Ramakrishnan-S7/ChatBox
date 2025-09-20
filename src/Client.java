import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private GUI gui;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendMessage(String message) {
        out.println(message); // send to server
    }

    public void setGUI() {
        this.gui = new GUI(this);
    }

    public void listenFromServer() {
        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    gui.addMessage(message); // show server-broadcasted message
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server");
            }
        }).start();
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8000); // FIX: declare local variable
            Client client = new Client(socket);
            client.setGUI();
            client.listenFromServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
