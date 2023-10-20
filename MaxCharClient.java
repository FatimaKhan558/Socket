import java.io.*;
import java.net.Socket;

public class MaxCharClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            String clientMessage = "Hello , Ali! How are you ?";

            output.writeObject(clientMessage);

            String serverResponse = (String) input.readObject();
            System.out.println("Server response: " + serverResponse);

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
