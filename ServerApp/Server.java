import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {
    public static String currentCode = "0000";

    public static void main(String[] args) {
        // Thread to change code every 60 seconds
        new Thread(() -> {
            while (true) {
                currentCode = String.format("%04d", new Random().nextInt(10000));
                System.out.println("System: New Code is " + currentCode);
                try { Thread.sleep(60000); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }).start();

        int port = 5050;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}