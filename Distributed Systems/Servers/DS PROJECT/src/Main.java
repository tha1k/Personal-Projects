import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//Authors: Eirini Gaitanatou/3200264 Veazoglou Georgios/3190347 Thodoris Aikaterinis/321006
public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[1]));//to server socket poy dexetai ta dwmatia
        List<Room> rooms = new ArrayList<>();
        Parser parser = new Parser();
        try {
            System.out.println("\nReading JSON..\n");
            rooms = parser.roomsFromJSON(args);
            System.out.println("JSON parsed successfully..");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String roomString = "APP\nROOMS\n";
        for(Room r : rooms){
            roomString += r.toStringForTCP();
        }
        roomString += "TELOSARXEIOY";
        try {
            InetAddress ip = InetAddress.getByName("localhost");
            Socket so = new Socket(ip, Integer.parseInt(args[0]));
            PrintWriter out = new PrintWriter(so.getOutputStream(), true);
            out.println(roomString);
            so.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        Manager manager = new Manager();
        manager.addRooms(rooms);
        Client client = new Client(manager);
        boolean flag = true;
        while(flag) {
            System.out.print("\n**** Room booking services. **** \n" +
                    "\nFor manager mode press '1', for client mode press '2', to shut down press '0'.\n" +
                    "\nChoose mode: ");
            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            switch (input) {
                default:
                    System.out.println("\nInput must be either '0', '1' or '2'!\n");
                    break;
                case 0:
                    System.out.println("Shutting down..");
                    flag = false;
                    break;
                case 1:
                    System.out.print("\n**** Manager mode. ****\n" +
                            "Choose a service:\n" +
                            "1. Add availability to room.\n" +
                            "2. Show room bookings.\n" +
                            "3. Show bookings per area.\n" +
                            "\nService: ");
                    int action = scanner.nextInt();
                    switch (action){
                        default:
                            System.out.println("\nInput must be either '1', '2' or '3'!\n");
                            break;
                        case 1:
                            int count1 = 1;
                            for (Room room1 : rooms){
                                System.out.println(count1 + ". " + room1.getRoomName() + " - " + room1.getArea() + "\n");
                                count1 ++;
                            }
                            System.out.print("Pick room: ");
                            int choice1 = scanner.nextInt();
                            Room room1 = rooms.get(choice1 - 1);
                            System.out.println("\nChosen room:");
                            System.out.println(room1.toString());
                            System.out.print("Enter the starting date (dd/mm/yyyy format): ");
                            Scanner start1 = new Scanner(System.in);
                            String startDate1 = start1.nextLine();
                            System.out.print("Enter the ending date (dd/mm/yyyy format): ");
                            Scanner end1 = new Scanner(System.in);
                            String endDate1 = end1.nextLine();

                            String dates = "APP\nAVAILABLE_DATES\n" +
                                    args[1] + "\n" +
                                    room1.getRoomName() + "\n" +
                                    room1.getArea() + "\n" +
                                    startDate1 + "\n" +
                                    endDate1 + "\n" +
                                    "TELOSARXEIOY";
                            try{
                                InetAddress ip = InetAddress.getByName("localhost");
                                Socket so = new Socket(ip, Integer.parseInt(args[0]));
                                PrintWriter out = new PrintWriter(so.getOutputStream(), true);
                                out.println(dates);
                                so.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String resultAddDates = "\n";
                            try{
                                Socket socket = serverSocket.accept();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                String socketInput;
                                while((socketInput=reader.readLine()) != null){
                                    if(socketInput.contains("TELOSARXEIOY")) break;
                                    resultAddDates += socketInput;
                                }
                                socket.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            System.out.println(resultAddDates);
                            break;
                        case 2:
                            int count2 = 1;
                            for (Room room2 : rooms){
                                System.out.println(count2 + ". " + room2.getRoomName() + " - " + room2.getArea() + "\n");
                                count2 ++;
                            }
                            System.out.print("Pick room: ");
                            int choice2 = scanner.nextInt();
                            Room room2 = rooms.get(choice2 - 1);
                            System.out.println("\nChosen room:");
                            System.out.println(room2.toString());
                            String bookings = "APP\nCOUNT\n" +
                                    args[1] + "\n" +
                                room2.getRoomName() + "\n" +
                                room2.getArea() + "\n" +
                                "TELOSARXEIOY";
                            try{
                                InetAddress ip = InetAddress.getByName("localhost");
                                Socket so = new Socket(ip, Integer.parseInt(args[0]));
                                PrintWriter out = new PrintWriter(so.getOutputStream(), true);
                                out.println(bookings);
                                so.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String bookingsRes ="";
                            try{
                                Socket socket = serverSocket.accept();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                String socketInput;
                                while((socketInput=reader.readLine()) != null){
                                    if(socketInput.contains("TELOSARXEIOY")) break;
                                    bookingsRes += socketInput;
                                }
                                socket.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String[] bookingArray = bookingsRes.split("\\s+");
                            if(!(bookingArray.length < 8)) {
                                for (int i = 0; i < bookingArray.length; i += 8)
                                    System.out.println(bookingArray[i] + " " + bookingArray[i + 1] + " (" + bookingArray[i + 2] + " " + bookingArray[i + 3] + " " +
                                            bookingArray[i + 4] + " " + bookingArray[i + 5] + " " + bookingArray[i + 6] + " " + bookingArray[i + 7] + ")");
                            }else{
                                System.out.println("No bookings.");
                            }
                            break;
                        case 3:
                            System.out.print("Enter the starting date (dd/mm/yyyy format): ");
                            Scanner start2 = new Scanner(System.in);
                            String startDate2 = start2.nextLine();
                            System.out.print("Enter the ending date (dd/mm/yyyy format): ");
                            Scanner end2 = new Scanner(System.in);
                            String endDate2 = end2.nextLine();
                            String areaDates = "APP\nAREA\n" +
                                    args[1] + "\n" +
                                    startDate2 + "\n" +
                                    endDate2 + "\n" +
                                    "TELOSARXEIOY";
                            try{
                                InetAddress ip = InetAddress.getByName("localhost");
                                Socket so = new Socket(ip, Integer.parseInt(args[0]));
                                PrintWriter out = new PrintWriter(so.getOutputStream(), true);
                                out.println(areaDates);
                                so.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String areaRes ="";
                            try{
                                Socket socket = serverSocket.accept();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                String socketInput;
                                while((socketInput=reader.readLine()) != null){
                                    if(socketInput.contains("TELOSARXEIOY")) break;
                                    areaRes += socketInput;
                                }
                                socket.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String[] areaArray = areaRes.split("\\s+");
                            if(!(areaArray.length < 3)){
                                for (int i = 0; i < areaArray.length; i += 4) {
                                    System.out.println(areaArray[i] + " " + areaArray[i + 1] + " " + areaArray[i + 2] + " " + areaArray[i + 3]);
                                }
                            }else{
                                System.out.println("No bookings.");
                            }
                            break;
                    }
                    break;
                case 2:
                    System.out.print("\n**** Client mode. ****\n" +
                            "Choose a service: \n" +
                            "1. Filter rooms.\n" +
                            "2. Rate a room." +
                            "\nService: ");
                    action = scanner.nextInt();
                    switch (action) {
                        default:
                            System.out.println("\nInput must be either '1'or '2'!\n");
                            break;
                        case 1:
                            List<Room> filteredRooms = new ArrayList<>();
                            System.out.print("Enter area: ");
                            Scanner areaScanner = new Scanner(System.in);
                            String area = areaScanner.nextLine();
                            System.out.print("Enter number of people: ");
                            Scanner number = new Scanner(System.in);
                            int noOfPersons = number.nextInt();
                            System.out.print("Enter maximum price: ");
                            Scanner max = new Scanner(System.in);
                            double price = max.nextDouble();
                            System.out.print("Enter minimum rating from 0.0 to 5.0: ");
                            Scanner rating = new Scanner(System.in);
                            double stars = rating.nextDouble();
                            String filters = "APP\nFILTERS\n" +
                                    args[1] + "\n" +
                                    area + "\n" +
                                    noOfPersons + "\n" +
                                    stars + "\n" +
                                    price+ "\n" +
                                    "TELOSARXEIOY";
                            try{
                                InetAddress ip = InetAddress.getByName("localhost");
                                Socket so = new Socket(ip, Integer.parseInt(args[0]));
                                PrintWriter out = new PrintWriter(so.getOutputStream(), true);
                                out.println(filters);
                                so.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String roomsToPrint = "";
                            try{
                                Socket socket = serverSocket.accept();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                String socketInput;
                                while((socketInput=reader.readLine()) != null){
                                    if(socketInput.contains("TELOSARXEIOY")) break;
                                    roomsToPrint += socketInput;
                                }
                                socket.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String[] roomValues = roomsToPrint.split("\\s+");
                            System.out.println("ROOM VALUES");
                            for(String string : roomValues){
                                System.out.println(string);
                            }
                            for (int i = 0; i < roomValues.length; i += 7) {
                                String roomName = roomValues[i];
                                int filterPersons = Integer.parseInt(roomValues[i + 1]);
                                String filterArea = roomValues[i + 2];
                                double filterStars = Double.parseDouble(roomValues[i + 3]);
                                int filterNoOfReviews = Integer.parseInt(roomValues[i + 4]);
                                double filterPrice = Double.parseDouble(roomValues[i + 5]);
                                String roomImage = roomValues[i+6];
                                Room room = new Room(roomName, filterPersons, filterArea, filterStars, filterNoOfReviews, filterPrice, roomImage);
                                filteredRooms.add(room);
                            }
                            for (Room room : filteredRooms){
                                System.out.println(room.toString());
                            }
                            System.out.print("Would you like to book? (Y/N): ");
                            Scanner choose = new Scanner(System.in);
                            String choice = choose.nextLine();
                            int count = 1;
                            if (choice.equals("Y")) {
                                for (Room room : filteredRooms) {
                                    System.out.println(count + ". " + room.getRoomName() + " - " + room.getArea() + "\n");
                                    count++;
                                }
                                System.out.print("Choose room by typing the corresponding number: ");
                                Scanner chooseRoom = new Scanner(System.in);
                                int filteredChoice = chooseRoom.nextInt();
                                for (Room room : rooms){
                                    if(filteredRooms.get(filteredChoice - 1).getRoomName().equals(room.getRoomName()) &&
                                            filteredRooms.get(filteredChoice - 1).getArea().equals(room.getArea())){
                                        System.out.print("Enter start date (dd/mm/yyyy format): ");
                                        Scanner startScanner = new Scanner(System.in);
                                        String startDate = startScanner.nextLine();
                                        System.out.print("Enter end date (dd/mm/yyyy format): ");
                                        Scanner endScanner = new Scanner(System.in);
                                        String endDate = endScanner.nextLine();
                                        String book = "APP\nBOOK\n" +
                                                args[1] + "\n" +
                                                room.getRoomName() + "\n" +
                                                room.getArea() + "\n" +
                                                startDate + "\n" +
                                                endDate + "\n" +
                                                "TELOSARXEIOY";
                                        try{
                                            InetAddress ip = InetAddress.getByName("localhost");
                                            Socket so = new Socket(ip, Integer.parseInt(args[0]));
                                            PrintWriter out = new PrintWriter(so.getOutputStream(), true);
                                            out.println(book);
                                            so.close();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        String resBook = "\n";
                                        try{
                                            Socket socket = serverSocket.accept();
                                            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                            String socketInput;
                                            while((socketInput=reader.readLine()) != null){
                                                if(socketInput.contains("TELOSARXEIOY")) break;
                                                resBook += socketInput;
                                            }
                                            socket.close();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        System.out.println(resBook);
                                    }
                                }

                            }
                            break;
                        case 2:
                            int count1 = 1;
                            for (Room room1 : rooms){
                                System.out.println(count1 + ". " + room1.getRoomName() + " - " + room1.getArea() + "\n");
                                count1 ++;
                            }
                            System.out.print("Pick room: ");
                            int choice1 = scanner.nextInt();
                            Room room1 = rooms.get(choice1 - 1);
                            System.out.println("\nChosen room:");
                            System.out.println(room1.toString());
                            System.out.print("Enter your rating: ");
                            Scanner ratingScanner = new Scanner(System.in);
                            double newRating = ratingScanner.nextDouble();
                            System.out.println();
                            String ratingString = "APP\nRATING\n" +
                                    args[1] + "\n" +
                                    room1.getRoomName() + "\n" +
                                    room1.getArea() + "\n" +
                                    newRating + "\n" +
                                    "TELOSARXEIOY";
                            try{
                                InetAddress ip = InetAddress.getByName("localhost");
                                Socket so = new Socket(ip, Integer.parseInt(args[0]));
                                PrintWriter out = new PrintWriter(so.getOutputStream(), true);
                                out.println(ratingString);
                                so.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String resRate = "\n";
                            try{
                                Socket socket = serverSocket.accept();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                String socketInput;
                                while((socketInput=reader.readLine()) != null){
                                    if(socketInput.contains("TELOSARXEIOY")) break;
                                    resRate += socketInput;
                                }
                                socket.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String[] rateArr = resRate.split("\\s+");
                            room1.setStars(Double.parseDouble(rateArr[4]));
                            room1.setNoOfReviews(Integer.parseInt(rateArr[5]));
                            System.out.println("Room after rate:");
                            System.out.println(room1.toString());
                            break;
                    }
                    break;
            }
        }
    }
}