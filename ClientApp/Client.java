import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5050)) {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Class Code: ");
            output.println(sc.nextLine());

            String response = input.readLine();
            if ("ENTER_NAME".equals(response)) {
                System.out.print("Code Correct! Enter your name: ");
                output.println(sc.nextLine());
                System.out.println(input.readLine());
            } else {
                System.out.println(response);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}