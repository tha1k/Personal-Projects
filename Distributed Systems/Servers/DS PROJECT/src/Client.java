import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {
    Manager manager = new Manager();
    private Map<Room, Booking> clientBookings;
    public Client(Manager manager){
        this.manager = manager;
        this.clientBookings = new HashMap<>();
    }
    public synchronized List<Room> filter(List<Room> rooms, String area, String start, String end, int noOfPersons, double price, double stars){
        List<Room> result = new ArrayList<>();
        for (Room room : rooms){
            if (room.getArea().equals(area) &&
                room.getAvailableDates().contains(start) &&
                room.getAvailableDates().contains(end) &&
                room.getNoOfPersons() >= noOfPersons &&
                room.getPrice() <= price &&
                room.getStars() >= stars){
                result.add(room);
            }
        }
        return result;
    }
    public synchronized void makeReservation(Room room, String startDate, String endDate){
        if(manager.addBooking(room, startDate, endDate)) {
            clientBookings.put(room, new Booking(startDate, endDate));
            notifyAll();
        }//tbc
    }

    public synchronized Map<Room, Booking> getClientBookings() {
        return clientBookings;
    }

    public synchronized void rateRoom(Room room, double stars){
        int reviews = room.getNoOfReviews();
        double newRate = ((reviews * room.getStars()) + stars) / (reviews + 1);
        newRate = Math.round(newRate * 10.0) / 10.0; // Round to one decimal point
        room.setNoOfReviews(reviews + 1);
        room.setStars(newRate);
        notifyAll();
    }
}
