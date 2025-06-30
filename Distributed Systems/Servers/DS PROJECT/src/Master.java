import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//TODO: ALLA3A OLA TA app_port KAI TA EBALA TO PRWTO PRAGMA POU 8A DIABAZEI (META TO STRING POU DIXNEI TI METHOD PREPEI NA EKTELESTEI) NA EINAI TO PORT TOU APP POU SYNDEETAI
//SAN ID TOU APP POU SYNDEETAI
//DOULEUEI ME TA FILTERS OPOTE LOGIKA KAI ME TA YPOLOIPA
public class Master{

    static int numOfWorkerNodes;
    static int[] ports;
    static HashMap<Integer, Integer> app_ports = new HashMap<Integer, Integer>();
    static int number_of_apps = 0;
    static int app_port;
    public static void main(String[] args) throws Exception{//args[0] = to serversocket tou master kai args[1] posous workers 8a ftia3ei, to serversocket tis main([3])
        ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));
        app_port = Integer.parseInt(args[3]);//TODO: MALLON 8A TO BGALW AUTO
        System.out.println("----[MASTER SERVER IS UP AND RUNNING]----\n");
        numOfWorkerNodes = Integer.parseInt(args[1]);
        //FTIAXNEI TA PORTS GIA EPIKOINWNIA ME WORKERS
        ports = new int[numOfWorkerNodes];
        for(int i = 0; i < ports.length; i++){
            ports[i] = Integer.parseInt(args[2]) + i;//args[2] is the port of the first worker.
        }

        

        while(true){
            Socket s = null;
            try{
                s = ss.accept();
                System.out.println("A new client is connected : " + s);
                System.out.println("Assigning new thread for this client"); 
                MasterClientHandler ch = new MasterClientHandler(s);
                ch.start();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{
                //s.close();
            }
        }
    }
}

class MasterClientHandler extends Thread{
    static int counter = 0;
    Socket s;
    static ArrayList<Room> roomsArrayList = new ArrayList<Room>();
    static String filtersToSend;
    static String filteredRooms;
    static ArrayList<ArrayList<Room>> listOfLists = new ArrayList<ArrayList<Room>>();
    static Manager manager = new Manager();
    static String resultDates;
    
    //HASHING SYNARTHSH
    public int H(Room room, int numberofnodes){
        return room.getRoomId() % numberofnodes;
    }



    public MasterClientHandler(Socket s){
        this.s = s;
    }
    @Override
    public void run(){
        try(BufferedReader in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true)){
            String tiMethodKanoume = in.readLine();
            //DIALEGEI AN DEXETAI DATA APTO APP H APO TON REDUCER
            if(tiMethodKanoume != null && tiMethodKanoume.contains("APP")){
                String tiMethodKanoume2 = in.readLine();
                System.out.println(tiMethodKanoume2);
                //DEXETAI ROOMS
                if ((tiMethodKanoume2 != null)) {
                    if(tiMethodKanoume2.contains("ROOMS")) {
                        if (counter == 0) {
                            System.out.println("\n[Getting rooms from the app]");
                            getRooms(in);//KA8E FORA DIABAZEI TO ARXEIO KAI FTIAXNEI THN LISTA APTHN ARXH
                            if (!listOfLists.isEmpty()) {
                                listOfLists.clear();
                            }
                            //FTIAXNEI LISTA ME LISTES DWMATIWN POU META STELNEI ANALOGA TO ROOMID STON KATALHLO WORKER
                            for (int i = 0; i < Master.numOfWorkerNodes; i++) {
                                ArrayList<Room> arr = new ArrayList<Room>();
                                for (int j = 0; j < roomsArrayList.size(); j++) {
                                    if (H(roomsArrayList.get(j), Master.numOfWorkerNodes) == i) {
                                        arr.add(roomsArrayList.get(j));
                                    }
                                }
                                listOfLists.add(arr);
                            }
                            sendRoomsToWorkers();
                        }
                        counter ++;
                    }
                    //DEXETAI FILTERS
                    else if(tiMethodKanoume2.contains("FILTERS")){
                        System.out.println("\n[Getting filters from the app]");
                        filtersToSend = getFilters(in, out);
                        sendFiltersToWorkers();
                    }
                    else if (tiMethodKanoume2.contains("AVAILABLE_DATES")) {
                        System.out.println("\n[Receiving available dates from the app]");
                        saveAvailableDates(in, out);
                    }
                    else if (tiMethodKanoume2.contains("BOOK")) {
                        System.out.println("\n[Booking room]");
                        bookRoom(in, out);
                    } else if (tiMethodKanoume2.contains("COUNT")){
                        System.out.println("\n[Getting bookings]");
                        getBookings(in, out);
                    } else if (tiMethodKanoume2.contains("AREA")){
                        System.out.println("\n[Getting bookings per area]");
                        getBookingsPerArea(in, out);
                    } else if(tiMethodKanoume2.contains("RATING")){//prwta 8a dinei to room name kai perioxh kai meta to rating
                        try{
                            int port = Integer.parseInt(in.readLine());
                            String roomName = in.readLine();
                            String roomArea = in.readLine();
                            String roomRating = in.readLine();
                            System.out.println(roomName);
                            System.out.println(roomArea);
                            System.out.println(roomRating);
                            System.out.println(listOfLists.size());
                            System.out.println(listOfLists.get(0).size());
                            System.out.println(listOfLists.get(0).get(0).toString());
                            int p = 0;
                            for(int i = 0; i < listOfLists.size(); i++) {
                                for (int j = 0; j < listOfLists.get(i).size(); j++) {
                                    Room r1 = listOfLists.get(i).get(j);
                                    if (r1.getRoomName().equals(roomName) && r1.getArea().equals(roomArea)) {
                                        System.out.println(r1.getRoomName());
                                        System.out.println(r1.getArea());
                                        p = i;
                                        break;
                                    }
                                }
                            }
                                InetAddress ip = InetAddress.getByName("localhost");
                                Socket so = new Socket(ip, Master.ports[p]);
                                PrintWriter out1 = new PrintWriter(so.getOutputStream(), true);
                                out1.println("RATING\n"+ port + "\n" + roomName + "\n" + roomArea + "\n" + roomRating + "\nTELOSARXEIOY");
                                System.out.println("sent to node with port" + Master.ports[p]);
                                out.println("Added rating.");
                                so.close();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            if(tiMethodKanoume != null && tiMethodKanoume.contains("REDUCER")){
                System.out.println("[Getting rooms from reducer]");
                //System.out.println("\n\n===============EINAI AUTA POU ESTEILE O REDUCER===========\n" + getFromReducer(in) + "\n================AUTA ESTEILE============\n\n");
                String fromReduc = getFromReducer(in);
                String[] array_for_port = fromReduc.split("\\s");
                int port = Integer.parseInt(array_for_port[0]);
                String send = "";
                for(int i = 1; i < array_for_port.length; i++){
                    if(array_for_port[i].equals(Integer.toString(port))){
                        continue;
                    }
                    if(array_for_port[i].equals("")){
                        continue;
                    }
                    send += array_for_port[i] + "\t\n";
                }
                sendToApp(send, port, out);
                filteredRooms = send;
            }
            if(tiMethodKanoume != null && tiMethodKanoume.contains("FILTERED")){
                System.out.println("[Sending rooms to android app]");
                System.out.println(filteredRooms);
                out.println(filteredRooms);
                filteredRooms = "";
            }
            if(tiMethodKanoume != null && tiMethodKanoume.contains("RATED")){
                System.out.println("[Sending rated room to android app.]");
                String[] array_response = filteredRooms.split("\\s");
                System.out.println("ARRAY RESPONSE: ");
                for(String string : array_response){
                    System.out.println(string);
                }
                for (Room room : roomsArrayList) {
                    if (room.getRoomName().equals(array_response[0])) {
                        room.setNoOfReviews(Integer.parseInt(array_response[8]));
                        room.setStars(Double.parseDouble(array_response[6]));
                    }
                }
                out.println(filteredRooms);
                filteredRooms ="";
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
    public String getFromReducer(BufferedReader in) throws Exception{
        String outString = "";
        String inputLine;
        String appl_port = in.readLine();
        outString += appl_port + "\n";
        while((inputLine = in.readLine()) != null){
            if(inputLine.equals("TELOSARXEIOY")){
                outString += inputLine;
                in.close();
                break;
            }
            outString += inputLine + "\t" +  "\n";
        }
        return outString;
    }
    public void sendToApp(String s, int port, PrintWriter out) throws Exception{
        System.out.println(port);
        System.out.println(s);
        out.println(s);
        System.out.println("send filtered rooms to app at port: " + port);
    }
    public void getBookingsPerArea(BufferedReader in, PrintWriter out) throws IOException{
        try{
            int port = Integer.parseInt(in.readLine());
            String startDate = in.readLine();
            String endDate = in.readLine();;
            out.println(manager.getAmountOfBookingsPerArea(startDate, endDate));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized void saveAvailableDates(BufferedReader in, PrintWriter out) throws Exception {
        int port = Integer.parseInt(in.readLine());
        String roomName = in.readLine();
        System.out.println("ROOMNAME: " + roomName);
        String area = in.readLine();
        System.out.println("AREA: " + area);
        Room room = null;
        for (Room r : roomsArrayList) {
            if (r.getRoomName().equals(roomName) && r.getArea().equals(area)) {
                    room = r;
                break;
            }
        }
        System.out.println(room.toString());
        System.out.println("Available dates: ");
        System.out.println(room.getAvailableDates());
        if (room != null) {
            String inputLine;
            ArrayList<String> availableDates = new ArrayList<>();

            while ((inputLine = in.readLine()) != null && !inputLine.equals("TELOSARXEIOY")) {
                availableDates.add(inputLine);
            }
            if (!room.getAvailableDates().contains(availableDates.get(0)) || !room.getAvailableDates().contains(availableDates.get(1))) {
                if(manager.addAvailability(room, availableDates.get(0), availableDates.get(1))) {
                    out.println( "Available dates for room " + roomName + " in area " + area + " saved successfully.");
                    System.out.println(room.getAvailableDates());
                }else{
                   out.println("Available dates were not parsed!");
                }
            }else {
                out.println("Available dates were not parsed!");
            }
        } else {
            System.out.println("Room not found.");
        }
    }
    public synchronized void bookRoom(BufferedReader in, PrintWriter out) throws IOException {
        try {
            int port = Integer.parseInt(in.readLine());
            String roomName = in.readLine();
            String area = in.readLine();
            String startDate = in.readLine();
            String endDate = in.readLine();
            Room room = null;

            // Find the room in the list by name and area
            for (Room r : roomsArrayList) {
                if (r.getRoomName().equals(roomName) && r.getArea().equals(area)) {
                    room = r;
                    break;
                }
            }
            if(manager.addBooking(room, startDate, endDate)) {
                out.println("Room booked successfully for dates " + startDate + " to " + endDate);
            }else{
                out.println("Room booking wasn't successful!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized void getBookings(BufferedReader in, PrintWriter out) throws IOException {
        try {
            int port = Integer.parseInt(in.readLine());

            String roomName = in.readLine();
            String area = in.readLine();
            Room room = null;

            for (Room r : roomsArrayList) {
                if (r.getRoomName().equals(roomName) && r.getArea().equals(area)) {
                    room = r;
                    break;
                }
            }
            StringBuilder output = new StringBuilder();
            if (manager.getBookings(room).isEmpty()){
                output.append("No bookings.");
            }else {
                List<Booking> bookings = manager.getRoomBookings(room);
                int count = 1;
                for (Booking booking : bookings) {
                    output.append("Booking ").append(count).append(":").append("\t");
                    output.append("Start date: ").append(booking.getStartDate()).append("\t");
                    output.append("End date: ").append(booking.getEndDate()).append("\t");
                    count++;
                }
            }

            out.println(output.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //PERNEI APO REDUCER TA FILTRARISMENA DWMATIA


    //DIABAZEI TA ROOMS
    public synchronized void getRooms(BufferedReader in) throws Exception{
        System.out.println("Get rooms got called");
        String inputLine;
        
        while((inputLine = in.readLine()) != null){
            if(inputLine.equals("TELOSARXEIOY")){
                in.close();
                break;
            }
            String[] roomArray = inputLine.split("\\s+");
            roomsArrayList.add(new Room(roomArray[0], Integer.parseInt(roomArray[1]), roomArray[2], Double.parseDouble(roomArray[3]), Integer.parseInt(roomArray[4]), Double.parseDouble(roomArray[5]),roomArray[6]));
        }
        manager.addRooms(roomsArrayList);
        System.out.println(roomsArrayList.toString());
        System.out.println(roomsArrayList.size());
    }
 
    //DIABAZEI TA FILTERS
    public String getFilters(BufferedReader in, PrintWriter out) throws Exception{
        out.println("Filters sent");
        String inputLine;
        String filters = "FILTERS \n";
        String appl_port = in.readLine();
        filters += appl_port + "\n";

        while((inputLine = in.readLine()) != null){
            if(inputLine.equals("TELOSARXEIOY")){
                in.close();
                break;
            }
            filters += inputLine + "\n";
        }
        filters += "TELOSARXEIOY";
        System.out.println("THE FILTERS:");
        System.out.println(filters);
        return filters;
    }

    //STELNEI TA ROOMS
    public synchronized void sendRoomsToWorkers(){
        for(int i = 0; i < listOfLists.size(); i++){
            String s = "ROOMS \n";
            for(int j = 0; j < listOfLists.get(i).size(); j++){
                s += listOfLists.get(i).get(j).toStringForTCP();
            }
            s += "TELOSARXEIOY";

            try{
                InetAddress ip = InetAddress.getByName("localhost");
                Socket so = new Socket(ip, Master.ports[i]);
                PrintWriter out = new PrintWriter(so.getOutputStream(), true);
                out.println(s);
                System.out.println("send to node with port" + Master.ports[i]);
                so.close();
            }
            catch(Exception e){
                System.err.println("to server me to port " + Master.ports[i] + " den einai anoixto");
            }
        }
    }

    //STELNEI TA FILTERS
    public synchronized void sendFiltersToWorkers(){
        for(int i = 0; i < Master.numOfWorkerNodes; i++){
            try{
                InetAddress ip = InetAddress.getByName("localhost");
                Socket so = new Socket(ip, Master.ports[i]);
                PrintWriter out = new PrintWriter(so.getOutputStream(), true);
                out.println(filtersToSend);
                System.out.println("send filters to node with port" + Master.ports[i]);
                so.close();
            }
            catch(Exception e){
                System.err.println("to server me to port " + Master.ports[i] + " den einai anoixto");
            }
        }
    }

}