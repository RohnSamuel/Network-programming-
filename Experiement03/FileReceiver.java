import java.io.*;
import java.net.*;

public class FileReceiver {
    public static void main(String[] args) {
        int port = 12345;  // Port number for the server to listen on
        String saveDirectory = "received_files/";  // Directory to save received files

        // Create the directory if it doesn't exist
        File directory = new File(saveDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            // Wait for client connection
            try (Socket socket = serverSocket.accept()) {
                System.out.println("Client connected!");

                // Set up input stream to receive file
                InputStream inputStream = socket.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                
                // Create an output file
                FileOutputStream fileOutputStream = new FileOutputStream(saveDirectory + "received_file.txt");
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                    bufferedOutputStream.write(buffer, 0, bytesRead);
                }

                bufferedOutputStream.close();
                System.out.println("File received and saved as 'received_file.txt'");
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
