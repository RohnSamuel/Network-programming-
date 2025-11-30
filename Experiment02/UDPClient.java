import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Create a DatagramSocket to send data
            socket = new DatagramSocket();

            // Server address and port to communicate with
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 9876;

            // Message to send to the server
            String message = "Hello, Server!";

            // Convert the message to bytes
            byte[] sendData = message.getBytes();

            // Create a DatagramPacket to send the data
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

            // Send the packet to the server
            socket.send(sendPacket);
            System.out.println("Message sent to server: " + message);

            // Receive response from the server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            // Print the received message from the server
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Response from server: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
