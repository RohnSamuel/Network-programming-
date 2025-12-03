import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
    private static final int SERVER_PORT = 9876;

    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT)) {
            System.out.println("UDP Time Server started on port " + SERVER_PORT);

            byte[] receiveBuffer = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);  // Receive request from client

                // Handle each client request in a new thread
                new Thread(() -> {
                    try {
                        String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                        System.out.println("Received request: " + clientMessage);

                        // Prepare current time string
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                        String currentTime = formatter.format(new Date());

                        byte[] sendBuffer = currentTime.getBytes();

                        // Send response back to the client
                        DatagramPacket sendPacket = new DatagramPacket(
                                sendBuffer,
                                sendBuffer.length,
                                receivePacket.getAddress(),
                                receivePacket.getPort()
                        );
                        serverSocket.send(sendPacket);
                        System.out.println("Sent current time to client: " + currentTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
