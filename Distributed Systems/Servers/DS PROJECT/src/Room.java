import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Room {
    private String roomName;
    private int noOfPersons;
    private String area;
    private double stars;
    private int noOfReviews;
    private double price;
    private String roomImage;
    private int roomId;
    private static int id = 0;
    private List<String> availableDates;
    public Room(String roomName, int noOfPersons, String area, double stars, int noOfReviews, double price, String roomImage) {
        this.roomId = id++;
        this.roomName = roomName;
        this.noOfPersons = noOfPersons;
        this.area = area;
        this.stars = stars;
        this.noOfReviews = noOfReviews;
        this.price = price;
        this.roomImage = roomImage;
        this.availableDates = new ArrayList<>();
    }
    public List<String> getAvailableDates(){
        Collections.sort(availableDates, new Comparator<String>() {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public int compare(String date1, String date2) {
                try {
                    return dateFormat.parse(date1).compareTo(dateFormat.parse(date2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Invalid date format. Should be dd/MM/yyyy.");
                }
            }
        });
        return availableDates;
    }

    public int getRoomId() {
        return roomId;
    }
    public void setAvailableDates(List<String> dates){
        for(String date: dates){
            availableDates.add(date);
        }
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getNoOfPersons() {
        return noOfPersons;
    }

    public void setNoOfPersons(int noOfPersons) {
        this.noOfPersons = noOfPersons;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = Math.round(stars * 10.0) / 10.0;
    }

    public int getNoOfReviews() {
        return noOfReviews;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNoOfReviews(int noOfReviews) {
        this.noOfReviews = noOfReviews;
    }
    
    public void setImage(String roomImage) {
    	this.roomImage = roomImage;
    }
    
    public String getImage() {
    	return roomImage;
    }
    
    public String toString(){
        return  "\nRoom name: " + getRoomName() + "\n" +
                "Number of persons: " + getNoOfPersons() + "\n" +
                "Area: " + getArea() + "\n" +
                "Rating: " + getStars() + "\n" +
                "Number of reviews: " + getNoOfReviews() + "\n" +
                "Price per night: " + getPrice() + "\n" +
                "Room Image: " + getImage() + "\n" ;
    }
    public String toStringForTCP(){
        return getRoomName() + "\t" + getNoOfPersons() + "\t" +
                getArea() + "\t" + getStars() + "\t" +
                getNoOfReviews() + "\t" + getPrice() + "\t" + getImage() + "\n";
    }
}