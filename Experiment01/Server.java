import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            // Create a server socket on port 5000
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started. Waiting for client...");

            // Wait for client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");

            // Get input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Communication loop
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String messageFromClient, messageToClient;

            while ((messageFromClient = in.readLine()) != null) {
                System.out.println("Client: " + messageFromClient);

                if (messageFromClient.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected.");
                    break;
                }

                System.out.print("Server: ");
                messageToClient = userInput.readLine();
                out.println(messageToClient);
            }

            // Close everything
            in.close();
            out.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
