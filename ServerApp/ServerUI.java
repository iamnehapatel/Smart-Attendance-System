import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Random;
import javax.swing.*;

public class ServerUI extends JFrame {
    private JLabel codeLabel;
    private JButton startButton;
    public static String currentCode = "0000"; // Accessible by ClientHandler

    public ServerUI() {
        setTitle("Professor Dashboard");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        codeLabel = new JLabel("Code: ----", SwingConstants.CENTER);
        codeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        startButton = new JButton("Start Class");

        startButton.addActionListener(e -> startSystem());

        add(startButton);
        add(codeLabel);
        setVisible(true);
    }

    private void startSystem() {
        startButton.setEnabled(false);
        
        // 1. Thread for Code Generator
        new Thread(() -> {
            while (true) {
                currentCode = String.format("%04d", new Random().nextInt(10000));
                codeLabel.setText("Code: " + currentCode);
                try { Thread.sleep(60000); } catch (Exception e) { e.printStackTrace(); }
            }
        }).start();

        // 2. Thread for Socket Server
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(5050)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(new ClientHandler(socket)).start();
                }
            } catch (IOException e) { e.printStackTrace(); }
        }).start();
    }

    public static void main(String[] args) { new ServerUI(); }
}