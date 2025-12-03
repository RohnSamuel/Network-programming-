import java.io.*;
import java.net.*;

public class FileSender {
    public static void main(String[] args) {
        String serverAddress = "localhost";  // Server IP address (use IP or hostname of the server)
        int port = 12345;  // Port number to connect to the server
        String filePath = "file_to_send.txt";  // File to be sent

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connected to server at " + serverAddress + ":" + port);

            // Set up input stream to read the file
            FileInputStream fileInputStream = new FileInputStream(filePath);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            OutputStream outputStream = socket.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

            // Buffer to send the file data
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }

            bufferedOutputStream.flush();
            System.out.println("File sent successfully!");

            // Close streams
            bufferedInputStream.close();
            bufferedOutputStream.close();
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
