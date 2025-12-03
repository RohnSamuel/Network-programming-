import java.io.*;
import java.net.*;
import java.util.Scanner;

public class RPCClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 54321;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to RPC Server at " + SERVER_ADDRESS + ":" + SERVER_PORT);

            while (true) {
                System.out.print("Enter method to call (add/multiply) or 'exit': ");
                String method = scanner.nextLine();
                if ("exit".equalsIgnoreCase(method)) {
                    output.writeObject("exit");
                    output.flush();
                    break;
                }

                System.out.print("Enter first parameter: ");
                int param1 = scanner.nextInt();
                System.out.print("Enter second parameter: ");
                int param2 = scanner.nextInt();
                scanner.nextLine(); // consume newline

                // Send method name
                output.writeObject(method);
                // Send parameters as array
                output.writeObject(new Object[]{param1, param2});
                output.flush();

                // Receive result
                Object result = input.readObject();
                System.out.println("Result from server: " + result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
