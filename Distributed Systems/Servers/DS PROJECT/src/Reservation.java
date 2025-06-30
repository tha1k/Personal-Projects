import java.util.Date;

public class Reservation {
    private Date startDate;
    private Date endDate;
    private Room room;

    public Reservation(Date startDate, Date endDate, Room room) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
    }
    public Room getRoom(){
        return room;
    }
    public void setRoom(Room room){
        this.room = room;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
