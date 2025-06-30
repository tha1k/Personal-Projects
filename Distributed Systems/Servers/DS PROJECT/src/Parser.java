import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Parser{
    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            parser.roomsFromJSON(args);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }
	
 public List<Room> roomsFromJSON(String args[]) throws FileNotFoundException {


     String path = "C:\\Users\\veazo\\Desktop\\rooms.json";
     List<Room> rooms = new ArrayList<>();

     JSONParser parser = new JSONParser();

     FileReader reader = new FileReader(path);

     try {

         JSONArray jsonArray = (JSONArray) parser.parse(reader);

         // Iterate through the array and process each object
         for (Object obj : jsonArray) {
             JSONObject jsonObject = (JSONObject) obj;

             String roomName = (String) jsonObject.get("roomName");
             int noOfPersons = ((Long) jsonObject.get("noOfPersons")).intValue();
             String area = (String) jsonObject.get("area");
             double stars = ((Long) jsonObject.get("stars")).intValue();
             int noOfReviews = ((Long) jsonObject.get("noOfReviews")).intValue();
             double price = ((Long) jsonObject.get("price")).intValue();
             String roomImage = (String) jsonObject.get("roomImage");
            
             Room room = new Room(roomName, noOfPersons, area, stars, noOfReviews, price, roomImage);
             rooms.add(room);


         }
     } catch (IOException e) {
         throw new RuntimeException(e);
     } catch (ParseException e) {
         throw new RuntimeException(e);
     }
     return rooms;
 }

 }