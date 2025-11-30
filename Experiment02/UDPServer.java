import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Create a DatagramSocket to listen on port 9876
            socket = new DatagramSocket(9876);
            System.out.println("Server is listening on port 9876...");

            while (true) {
                // Buffer to receive incoming packets
                byte[] receiveData = new byte[1024];

                // Create a DatagramPacket to receive the data
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Receive the packet from the client
                socket.receive(receivePacket);

                // Extract the message from the packet
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from client: " + message);

                // Optionally, send a reply back to the client
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                String replyMessage = "Message received: " + message;

                DatagramPacket replyPacket = new DatagramPacket(replyMessage.getBytes(), replyMessage.length(), clientAddress, clientPort);
                socket.send(replyPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
