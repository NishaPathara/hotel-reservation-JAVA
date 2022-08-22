package api;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Hotelresource api resource for public usage
 *
 * @author Nisha Pathara
 */
public class HotelResource {
    private static HotelResource instanceOfHotelResource= new HotelResource();
    private HotelResource(){
    }
    public static HotelResource getInstanceOfHotelResource(){
      return instanceOfHotelResource;
    }
    private CustomerService customerService = CustomerService.getCustomerInstance();
    private ReservationService reservationService = ReservationService.getReservationInstance();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createCustomer(String email,String firstName,String lastName){
        customerService.addCustomer(email,firstName,lastName);
    }
    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        Customer customer=getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }
    public Collection<Reservation> getCustomerReservations(String customerEmail){
        Customer customer=getCustomer(customerEmail);
        if (customer==null){
            return Collections.emptyList();
        }
        return reservationService.getCustomerReservation(customer);
    }
    public Collection <IRoom> findARoom(Date checkIn,Date checkOut){
        return reservationService.findRooms(checkIn,checkOut);
    }
    public Collection<IRoom> findAlternativeRooms(final Date checkIn, final Date checkOut) {
        return reservationService.findRecommendedRooms(checkIn, checkOut);
    }
}
