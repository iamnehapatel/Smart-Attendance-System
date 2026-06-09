import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    public ClientHandler(Socket socket) { this.socket = socket; }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            String studentCode = input.readLine();
            if (studentCode != null && studentCode.equals(Server.currentCode)) {
                output.println("ENTER_NAME");
                String name = input.readLine();
                
                try (FileWriter fw = new FileWriter("attendance.txt", true)) {
                    fw.write(name + "\n");
                    output.println("SUCCESS: Attendance marked for " + name);
                    System.out.println("Saved: " + name);
                }
            } else {
                output.println("FAILURE: Incorrect code.");
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}