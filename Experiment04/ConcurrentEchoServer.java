import java.io.*;
import java.net.*;
import java.util.*;

public class ConcurrentEchoServer {
    private static final int PORT = 12345;
    private static final List<Socket> clientSockets = new ArrayList<>();
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for clients to connect...");

            while (true) {
                Socket clientSocket = serverSocket.accept();  // Accept client connections
                synchronized (clientSockets) {
                    clientSockets.add(clientSocket);  // Add client socket to the list
                }

                // Start a new thread to handle each client
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thread that handles the communication with the client
    private static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private final BufferedReader in;
        private final PrintWriter out;

        public ClientHandler(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received from client: " + message);
                    out.println("Echo: " + message);  // Echo back the received message
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (clientSockets) {
                    clientSockets.remove(clientSocket);  // Remove client socket from the list
                }
            }
        }
    }
}
