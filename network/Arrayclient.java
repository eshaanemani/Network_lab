import java.io.*;
import java.net.*;

public class Arrayclient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        System.out.println("Connected to server.");

        int[] arr = {3, 9, 1, 7, 5};
        int size = arr.length;
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeInt(arr[i]);
        }

        DataInputStream in = new DataInputStream(socket.getInputStream());
        int max = in.readInt();
        System.out.println("The maximum value in the array is: " + max);

        socket.close();
    }
}
