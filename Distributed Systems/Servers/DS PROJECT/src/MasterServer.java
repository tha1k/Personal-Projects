import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterServer {
    public static void main(String[] args) {
        System.out.println("Waiting for clients...");
        try {
            ServerSocket serverSocket = new ServerSocket(9806);
            Socket soc = serverSocket.accept();
            System.out.println("Connection established.");
            BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            String str = reader.readLine();
            PrintWriter out = new PrintWriter(soc.getOutputStream(), true);
            out.println("Server says: " + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}