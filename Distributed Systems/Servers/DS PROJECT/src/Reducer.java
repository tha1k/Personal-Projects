import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Reducer {
    static int totalWorkers = 0;
    static int posoiEinaiOiWorkers;
    static ArrayList<Room> roomsArrayList = new ArrayList<Room>();
    static int outputPort;
    static String outputString = "";//to prwto pragma einai to port toy app pou 8a stal8oun
    //static String ratedRoom = "";
    public static void main(String[] args) throws Exception{
        //args[0] einai to port pou antistoixei ston reducer ws server kai args[1] to port tou master pou mazeuei ta apotelesmata tou map reduce 
        //kai args[2] posoi workers yparxoun
        ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));
        System.out.println("[Reducer server is up and running]");
        posoiEinaiOiWorkers = Integer.parseInt(args[2]);//TODO: isws pernaei to Master.numberofworkers anti gia argument AUTO DEN GINETAI GIATI STON MASTER PERNANE PARAPANW APO OTI ANOIGOUN
        outputPort = Integer.parseInt(args[1]);

        while(true){
            Socket s = null;

            try{
                s = ss.accept();
                System.out.println("A new client is connected : " + s);
                System.out.println("Assigning new thread for this client");
                ReducerClientHandler ch = new ReducerClientHandler(s);
                ch.start();
                
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void sendRoomsToMaster() throws Exception{
        
        //STELNEI TA ROOMS
        synchronized (Reducer.class) {
            if (Reducer.totalWorkers == posoiEinaiOiWorkers) {
                InetAddress ip = InetAddress.getByName("localhost");
                Socket so = new Socket(ip, outputPort);
                PrintWriter out = new PrintWriter(so.getOutputStream(), true);
                out.println("REDUCER\n" + outputString + "TELOSARXEIOY");
                System.out.println("[Rooms sent: " + outputString + " to master with port " + outputPort + "]");
                so.close();
                Reducer.totalWorkers = 0;
                outputString = "";
            }
        }
    }
}

class ReducerClientHandler extends Thread{
    Socket s;

    public ReducerClientHandler(Socket s){
        this.s = s;
    }

    @Override
    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
            String tiMethod = in.readLine();
            //System.out.println(tiMethod);
            String inputLine;
            //DEXETAI TA ROOMS
            if(tiMethod.contains("Rate")){
                //System.out.println("kanw rate");
                //Reducer.totalWorkers--;
                int port = Integer.parseInt(in.readLine());
                //System.out.println(port);
                String ratedroom = in.readLine();
                /*Reducer.ratedRoom += port + "\n";
                System.out.println(in.readLine());
                while((inputLine = in.readLine()) != null){
                    if(inputLine.contains("TELOSARXEIOY")){
                        in.close();
                        continue;
                    }
                    Reducer.ratedRoom += inputLine + "\n";
                }*/
                //System.out.println(Reducer.ratedRoom);
                InetAddress ip = InetAddress.getByName("localhost");
                Socket so = new Socket(ip, Reducer.outputPort);
                PrintWriter out = new PrintWriter(so.getOutputStream(), true);
                out.println("REDUCER\n" + port + "\n" + ratedroom + "\nTELOSARXEIOY");
                System.out.println("Room" + ratedroom + " was rated and new room info sent to master at port" + Reducer.outputPort);
                //Reducer.ratedRoom = "";
            }
            else if(tiMethod.contains("ROOMS")){
                addWorker();
                System.out.println("stelnw rooms");
                while((inputLine = in.readLine()) != null){
                    if(inputLine.contains("TELOSARXEIOY")){
                        in.close();
                        System.out.println("braking");
                        break;
                    }
                    Reducer.outputString += inputLine + "\n";
                    System.out.println(Reducer.outputString);
                }
                checkToSend();
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                s.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    synchronized void addWorker() throws Exception{
        Reducer.totalWorkers++;
    }

    synchronized void checkToSend() throws Exception{
        if(Reducer.totalWorkers == Reducer.posoiEinaiOiWorkers){
            Reducer.sendRoomsToMaster();
        }
    }
}