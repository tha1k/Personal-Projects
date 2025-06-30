import java.io.*;
import java.net.*;
import java.util.ArrayList;

//TODO: NA KLEIENIES KATALYMA kai na filtrarei

public class Worker {
    static String filters = "";
    static String rating = "";
    static ArrayList<Room> roomsArrayList = new ArrayList<Room>();

    public static void main(String[] args) throws Exception{//args[0] to server socker tou worker kai args[1] to port pou dexetai o reducer
        ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));
        System.out.println("----[WORKER SERVER IS UP AND RUNNING]----\n");

        while(true){
            Socket s = null;
            synchronized (Worker.class) {
                //AN EXOUN PERASEI FILTERS STELNEI TA FILTRARISMENA DWMATIA STON REDUCER
                if (!filters.isEmpty() /*&& !roomsArrayList.isEmpty()*/) {
                    System.out.println("paw na steilw");
                    sendToReducer(Integer.parseInt(args[1]), filteredRooms(Worker.filters));
                    System.out.println("Filtered room sent to Reducer");
                    filters = "";
                } else if (!Worker.rating.isEmpty() && !roomsArrayList.isEmpty()) {
                    String testing = rateRoom(Worker.rating);
                    sendToReducer(Integer.parseInt(args[1]), testing);
                    System.out.println("Rated room sent to Reducer " + testing);
                    Worker.rating = "";
                }
            }
            //DEN EXEI FYGEI LOGO DEBUGGING
            /*else{
                System.out.println("DEN EXW TI NA STEILW");
            }*/

            try{
                s = ss.accept();
                System.out.println("A new client is connected : " + s);
                System.out.println("Assigning new thread for this client");
                WorkerClientHandler ch = new WorkerClientHandler(s);
                ch.run();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static synchronized String rateRoom(String rate){
        String[] rateArray = rate.split("\\s");
        int port = Integer.parseInt(rateArray[0]);
        ArrayList<Room> roomsToSend = new ArrayList<>();
        for (Room r : Worker.roomsArrayList) {
            if(r.getRoomName().equals(rateArray[1]) && r.getArea().equals(rateArray[2])){
                int totalRatings = r.getNoOfReviews() + 1;
                double oldTotalRating = r.getStars() * r.getNoOfReviews();
                double newRating = (oldTotalRating + Double.valueOf(rateArray[3]))/totalRatings;
                r.setStars(newRating);
                r.setNoOfReviews(totalRatings);
                roomsToSend.add(r);
                break;
            }
        }
        String rateRoom = "Rate\n" + port + "\n";
        for(int i = 0; i < roomsToSend.size(); i++){
            rateRoom += roomsToSend.get(i).toStringForTCP();
        }
        rateRoom += "TELOSARXEIOY";
        //System.out.println(rateRoom);
        return rateRoom;
    }
    //FILTRAREI TA DWMATIA
    public static synchronized String filteredRooms(String filters){//TODO: CHECKARE AUTO EDW LIGO, DATERANGE
        if(!Worker.roomsArrayList.isEmpty()){
            String[] filtersArray = filters.split("\\s");
            System.out.println(filters);
            ArrayList<Room> roomsToSend = new ArrayList<>();
            for (Room r : Worker.roomsArrayList) {
                System.out.println(r.toStringForTCP());
                boolean area = false, noOfPersons = false, stars = false, price = false, b4 = false;
                if(filtersArray[0].equals(r.getArea())){
                    area = true;
                }
                if(Integer.parseInt(filtersArray[1]) <= r.getNoOfPersons()){
                    noOfPersons = true;
                }
                if(Double.parseDouble(filtersArray[2]) <= r.getStars()){
                    stars = true;
                }
                if(Double.parseDouble(filtersArray[3]) >= r.getPrice()){
                    price = true;
                }

                if(area && noOfPersons && stars && price){
                    roomsToSend.add(r);
                }
            }
            String filteredRooms = "ROOMS\n" + filtersArray[4]/*to port toy app pou 8a steilei meta */ + "\n";
            for(int i = 0; i < roomsToSend.size(); i++){
                filteredRooms += roomsToSend.get(i).toStringForTCP();
            }
            filteredRooms += "TELOSARXEIOY";
            return filteredRooms;
        }
        else{
            String[] filtersArray = filters.split("\\s");
            return "ROOMS\n" + filtersArray[4] + "\nTELOSARXEIOY";
        }
        
    }

    //STELNEI STON REDUCER TA FILTERED ROOMS
    public static void sendToReducer(int port, String out) throws Exception{
        InetAddress ip = InetAddress.getByName("localhost");
        Socket so = new Socket(ip, port);
        System.out.println(out);
        PrintWriter outString = new PrintWriter(so.getOutputStream(), true);
        outString.println(out);
        so.close();
    }
}

class WorkerClientHandler extends Thread{
    Socket s;
    boolean running = true;

    public WorkerClientHandler(Socket s){
        this.s = s;
    }

    @Override
    public void run(){
        try{    
            BufferedReader in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
            String tiMethodKanoume = in.readLine();
            System.out.println("WORKER TIMETHODKANOUME: " + tiMethodKanoume);
            //CHECAKREI AN DEXETAI ROOMS H FILTERS
            if(tiMethodKanoume.contains("ROOMS")){
                System.out.println("\n[Getting rooms]");
                getRooms(in);
            }
            else if(tiMethodKanoume.contains("FILTERS")){
                System.out.println("\n[Getting filters]");
                getFilters(in);
            }
            else if(tiMethodKanoume.contains("RATING")){//na doume mia an leitourgei komple
                System.out.println("\n[Getting rating]");
                getRate(in);
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
    //DEXETAI TA ROOMS
    public void getRooms(BufferedReader in) throws Exception{
        String inputLine;
            while((inputLine = in.readLine()) != null){
                if(inputLine.equals("TELOSARXEIOY")){
                    break;
                }
                String[] roomArray = inputLine.split("\\s+");
                synchronized (Worker.roomsArrayList) {
                    Worker.roomsArrayList.add(new Room(roomArray[0], Integer.parseInt(roomArray[1]), roomArray[2], Double.parseDouble(roomArray[3]), Integer.parseInt(roomArray[4]), Double.parseDouble(roomArray[5]),roomArray[6]));
                }
            }
            System.out.println(Worker.roomsArrayList.toString());
            System.out.println("\n[Phra ola ta rooms]\n-----------------------------------------------------------------------\n");

    }
    public void getRate(BufferedReader in) throws Exception{
        String inputLine;
        synchronized (Worker.class) {
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("TELOSARXEIOY")) {
                    break;
                }
                Worker.rating += inputLine + "\n";
            }
        }
        //System.out.println(Worker.rating);
    }

    //DEXETAI TA FILTERS
    public void getFilters(BufferedReader in) throws Exception{
        String inputLine;
        String appl_port = in.readLine();
        
        synchronized (Worker.class) {
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("TELOSARXEIOY")) {
                    break;
                }
                Worker.filters += inputLine + "\n";
            }
            Worker.filters += appl_port + "\n";
        }
        System.out.println("\n[Phra ola ta filters]\n-----------------------------------------------------------------------\n");
        System.out.println(Worker.filters);

    }
}