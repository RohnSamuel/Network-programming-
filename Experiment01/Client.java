import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            // Connect to server (use your server’s IP if running on another computer)
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to server.");

            // Get input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String messageToServer, messageFromServer;

            // Communication loop
            while (true) {
                System.out.print("Client: ");
                messageToServer = userInput.readLine();
                out.println(messageToServer);

                if (messageToServer.equalsIgnoreCase("exit")) {
                    System.out.println("Disconnected from server.");
                    break;
                }

                messageFromServer = in.readLine();
                System.out.println("Server: " + messageFromServer);
            }

            // Close everything
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
