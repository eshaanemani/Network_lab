import java.io.*;
import java.net.*;

public class Arrayserver {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started. Waiting for client...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected.");

        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
        int size = in.readInt();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = in.readInt();
        }

        int max = arr[0];
        for (int i = 1; i < size; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        out.writeInt(max);

        clientSocket.close();
        serverSocket.close();
    }
}
