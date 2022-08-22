package model;
import java.util.Date;
import java.util.Objects;

/**
 * Reservation class with customer,room,checkin and checkout date
 *
 * @author NishaPathara
 */
public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;
    public Reservation(Customer customer,IRoom room,Date checkInDate,Date checkOutDate){
        this.customer=customer;
        this.room=room;
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }
    @Override
    public String toString(){
        return "Customer: " + customer + "\nRoom: " + room + "\nCheck in Date: " + checkInDate + "\nCheckOut Date: " +checkOutDate;
    }
    @Override
    public boolean equals(Object obj) {

        if (obj == this) return true;
        if (!(obj instanceof Reservation)) {
            return false;
        }
        Reservation reservation = (Reservation) obj;
        return Objects.equals(customer, reservation.customer) &&
                Objects.equals(room, reservation.room) &&
                Objects.equals(checkInDate, reservation.checkInDate) &&
                Objects.equals(checkOutDate, reservation.checkOutDate) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }
}
