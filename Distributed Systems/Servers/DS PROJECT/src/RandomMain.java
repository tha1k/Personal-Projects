import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class RandomMain {
    public static void main(String[] args) throws Exception{
        try{
            /*Socket s = new Socket(InetAddress.getByName("localhost"), 8888);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("APP\nROOMS\nroomname\t4\tathens\t4\t4\t300\teikona\nTELOSARXEIOY");
            System.out.println("sent rooms");
            s.close();*/
            /*Socket s = new Socket(InetAddress.getByName("localhost"), 8888);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("APP\nFILTERS\n7799\nathens\n4\n4\n500\nTELOSARXEIOY");
            System.out.println("sent filters");
            s.close();*/
            Socket s = new Socket(InetAddress.getByName("localhost"), 8888);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("APP\nRATING\n66565\nroomname\nathens\n4\nTELOSARXEIOY");
            System.out.println("sent rating");
            s.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
