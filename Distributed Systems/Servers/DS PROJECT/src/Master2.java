import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Master2 {

    public static void main(String[] args) {
        final int PORT = 8000;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ClientHandler(socket).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true)) {

                String inputLine;
                StringBuilder message = new StringBuilder();

                // Read the client's message
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("TELOSARXEIOY")) break;
                    message.append(inputLine).append("\n");
                }

                System.out.println("Received message: " + message);

                // Send a response back to the client
                String response = "Message received: " + message.toString();
                out.println(response);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
