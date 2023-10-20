import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MaxCharServer {
    public static void main(String[] args) {
        int port = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());

                ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                String clientMessage = (String) input.readObject();

                Map<Character, Integer> charCount = new HashMap<>();
                int maxCount = 0;

                for (char c : clientMessage.toCharArray()) {
                    if (Character.isLetter(c)) {
                        c = Character.toLowerCase(c);
                        int count = charCount.getOrDefault(c, 0) + 1;
                        charCount.put(c, count);
                        maxCount = Math.max(maxCount, count);
                    }
                }

                StringBuilder response = new StringBuilder();
                for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
                    if (entry.getValue() == maxCount) {
                        if (response.length() > 0) {
                            response.append(" , ");
                        }
                        response.append(entry.getKey() + ":" + entry.getValue());
                    }
                }

                ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
                output.writeObject(response.toString());

                clientSocket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
