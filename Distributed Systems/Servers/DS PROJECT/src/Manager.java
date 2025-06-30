import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Manager {
    private List<Room> rooms;
    private Map<String, List<Booking>> areaBookings;
    private Map<Room, List<Booking>> roomBookings;
    private Map<Room, List<String>> bookings;
    private Map<Room, List<String>> availability;
    public Manager(){
        rooms = new ArrayList<>();
        bookings = new HashMap<>();
        availability = new HashMap<>();
        areaBookings = new HashMap<>();
        roomBookings = new HashMap<>();
    }
    public void addRoom(Room room) {
        rooms.add(room);
    }
    public void addRooms(List<Room> rooms){
        this.rooms = rooms;
    }
    public synchronized boolean addAvailability(Room room, String startDate, String endDate){
        try{
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(startDate));
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            if (!availability.containsKey(room)){
                availability.put(room, new ArrayList<>());
            }
            Date endDateObj = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
            while(!calendar.getTime().after(endDateObj)){
                String formattedDate = format.format(calendar.getTime());
                if(!availability.get(room).contains(formattedDate) && (!bookings.containsKey(room) || !bookings.get(room).contains(formattedDate))) {
                    availability.get(room).add(formattedDate);
                    room.getAvailableDates().add(formattedDate);
                    calendar.add(Calendar.DATE, 1);
                } else{
                    System.out.println("\nGiven dates are already available or booked!");
                    return false;
                }
            }
        }catch (ParseException e){
            System.out.println("Invalid date format. Should be dd/mm/yyyy.");
            return false;
        }
        notifyAll();
        return true;
    }
    /*public void getAreaBookings(){
        int count = 0;
        for(String area : areaBookings.keySet()){
            System.out.println("Area: " + area);
            System.out.println(areaBookings.get(area).toString());
            System.out.println(areaBookings.get(area).size());
        }
    }*/
    public synchronized List<Booking> getRoomBookings(Room room){
        List<Booking> bookings = new ArrayList<>();
        for(Booking booking : roomBookings.get(room)){
            bookings.add(booking);
        }
        int count = 1;
        String output = "";
        for(Booking booking : bookings){
            System.out.println("Booking " + count + ": " + booking.toString());
            output += "Booking" + count + ": " + booking.toString();
            count ++;
        }
        return bookings;
    }
    public synchronized String getAmountOfBookingsPerArea(String startDate, String endDate){
        Map<String, List<Booking>> countBookings = new HashMap<>();
        String result = "";
        for(String area : areaBookings.keySet()) {
            if (!countBookings.containsKey(area)) {
                countBookings.put(area, new ArrayList<>());
            }
            for (Booking booking : areaBookings.get(area)) {
                if (booking.checkBookingRange(startDate, endDate)) {
                    countBookings.get(area).add(booking);
                }
            }
            if (!countBookings.get(area).isEmpty()) {
                result += ("\nArea: " + area + " -\t" +
                        " Bookings: " + countBookings.get(area).size() + "\t");
            }else{
                result += ("\nNo bookings.");
            }
        }
        if(countBookings.isEmpty()){
            result += ("\nNo bookings.");
        }
        return result;
    }
    public synchronized boolean addBooking(Room room, String startDate, String endDate){
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(startDate));
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            if (!bookings.containsKey(room)){
                bookings.put(room,new ArrayList<>());
                roomBookings.put(room, new ArrayList<>());
            }
            List<String> availabilityDates = getAvailability(room);
            System.out.println("add booking available dates:" + availabilityDates);
            boolean available = true;
            Date endDateObj = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
            while (!calendar.getTime().after(endDateObj)) {
                String formattedDate = format.format(calendar.getTime());
                if (!availabilityDates.contains(formattedDate)) {
                    available = false;
                    System.out.println("available = " + available);
                    break;
                }
                calendar.add(Calendar.DATE, 1);
            }

            if (available) {
                // Book the room for the entire range
                calendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(startDate)); // Reset calendar
                while (!calendar.getTime().after(endDateObj)) {
                    String formattedDate = format.format(calendar.getTime());
                    bookings.get(room).add(formattedDate);
                    availability.get(room).remove(formattedDate);
                    room.getAvailableDates().remove(formattedDate);
                    calendar.add(Calendar.DATE, 1);
                }
                // Add booking to areaBookings and roomBookings
                if (!areaBookings.containsKey(room.getArea())) {
                    areaBookings.put(room.getArea(), new ArrayList<>());
                }
                areaBookings.get(room.getArea()).add(new Booking(startDate, endDate));
                roomBookings.get(room).add(new Booking(startDate, endDate));
                System.out.println("After " + room.getAvailableDates());
                System.out.println("Room booked successfully!");
                return true;
            } else {
                System.out.println("No availability!");
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Should be dd/mm/yyyy.");
            throw new RuntimeException(e);
        }
        return false;
    }
    public synchronized List<String> getAvailability(Room room){
        return availability.getOrDefault(room, new ArrayList<>());
    }
    public synchronized List<String> getBookings(Room room){
        return bookings.getOrDefault(room, new ArrayList<>());
    }
    public boolean dateRange(String dateStr, String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);

        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
    /*public Map<String, Integer> getTotalBookingsByAreaForPeriod(String startDateStr, String endDateStr) {
        Map<String, Integer> totalBookingsByArea = new HashMap<>();
        for (Room room : rooms) {
            int totalBookingsForRoom = 0;
            for (String bookingDate : getBookings(room)) {
                if (dateRange(bookingDate, startDateStr, endDateStr)) {
                    totalBookingsForRoom++;
                }
            }

            String area = room.getArea();
            int totalBookingsForArea = totalBookingsByArea.getOrDefault(area, 0);
            totalBookingsByArea.put(area, totalBookingsForArea + totalBookingsForRoom);
        }
        return totalBookingsByArea;
    }*/
}