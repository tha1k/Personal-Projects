import java.util.Date;

public class Booking {
    private String startDate;
    private String endDate;
    private Room room;
    public Booking(String startDate, String endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public Booking getBooking(){
        return this;
    }
    public String getStartDate(){
        return startDate;
    }
    public String getEndDate(){
        return endDate;
    }
    public boolean checkBookingRange(String start, String end){
        Date startDate = new Date(start);
        Date endDate = new Date(end);
        return !startDate.after(new Date(this.startDate)) && !endDate.before(new Date(this.endDate));
    }
    public String toString(){
        return "\nStart date: " + startDate + "\n" +
                "End date: " + endDate + "\n";
    }
}
