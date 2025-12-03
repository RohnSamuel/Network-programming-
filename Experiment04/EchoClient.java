import java.io.*;
import java.net.*;

public class EchoClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server at " + SERVER_ADDRESS + ":" + SERVER_PORT);
            String message;
            while (true) {
                System.out.print("Enter message to send to server (or 'exit' to quit): ");
                message = consoleInput.readLine();
                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }
                out.println(message);  // Send message to server
                String echo = in.readLine();  // Receive echoed message from server
                System.out.println("Server echo: " + echo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
