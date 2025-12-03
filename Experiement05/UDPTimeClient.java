import java.net.*;
import java.util.Scanner;

public class UDPTimeClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9876;

    public static void main(String[] args) {
        try (DatagramSocket clientSocket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            InetAddress serverIP = InetAddress.getByName(SERVER_ADDRESS);

            while (true) {
                System.out.print("Press Enter to request current time from server (or type 'exit' to quit): ");
                String input = scanner.nextLine();
                if ("exit".equalsIgnoreCase(input)) break;

                String requestMessage = "TIME_REQUEST";
                byte[] sendBuffer = requestMessage.getBytes();

                // Send request to server
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverIP, SERVER_PORT);
                clientSocket.send(sendPacket);

                // Receive response from server
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);

                String serverTime = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Current time from server: " + serverTime);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
