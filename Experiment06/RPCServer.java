import java.io.*;
import java.net.*;

public class RPCServer {
    private static final int PORT = 54321;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("RPC Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle each client in a new thread
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
        ) {
            while (true) {
                // Read method name from client
                String methodName = (String) input.readObject();
                
                if ("exit".equalsIgnoreCase(methodName)) {
                    System.out.println("Client disconnected.");
                    break;
                }

                // Read parameters
                Object[] params = (Object[]) input.readObject();
                Object result = null;

                // Execute the requested method
                switch (methodName) {
                    case "add":
                        int a = (int) params[0];
                        int b = (int) params[1];
                        result = add(a, b);
                        break;
                    case "multiply":
                        int x = (int) params[0];
                        int y = (int) params[1];
                        result = multiply(x, y);
                        break;
                    default:
                        result = "Unknown method: " + methodName;
                }

                // Send result back to client
                output.writeObject(result);
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { clientSocket.close(); } catch (IOException ignored) {}
        }
    }

    // Remote methods
    private static int add(int a, int b) {
        return a + b;
    }

    private static int multiply(int a, int b) {
        return a * b;
    }
}
