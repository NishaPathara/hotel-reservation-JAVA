package service;
import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;

/**
 * Reservation service class
 *
 * @author Nisha Pathara
 */

public class ReservationService {
    Map<String, IRoom> rooms = new HashMap<String, IRoom>();
    Map<String, IRoom> nonOverlapRooms = new HashMap<String, IRoom>();
    Map<String,Collection<Reservation>> reservations = new HashMap<String,Collection<Reservation>>();
    private static ReservationService reservationInstance = new ReservationService();
    //default method
    ReservationService(){
    }
    public static ReservationService getReservationInstance(){
        return reservationInstance;
    }
    private static final int daysToAdd = 7;
    public Date addDaysToDate(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,daysToAdd);
        return c.getTime();
    }
    public void addRoom(final IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId){
        return rooms.get(roomId);
    }
    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Reservation reserveARoom(Customer customer,IRoom room,Date CheckInDate,Date CheckOutDate){
        Reservation reservation=new Reservation(customer,room,CheckInDate,CheckOutDate);
        Collection<Reservation> customerReservation = getCustomerReservation(customer);
        if (customerReservation == null) {
            customerReservation = new LinkedList<>();
        }
        customerReservation.add(reservation);
        reservations.put(customer.getEmail(), customerReservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        int overlapFlag=0;
        nonOverlapRooms.putAll(rooms);
        Collection<Reservation> allReservations = retrieveReservations();
        for (Reservation reservation : allReservations) {
            Date existingReservationCheckOutDate=reservation.getCheckOutDate();
            Date existingReservationCheckInDate=reservation.getCheckInDate();
            if (checkInDate.before(existingReservationCheckOutDate) && checkOutDate.after(existingReservationCheckInDate)) {
                overlapFlag = 1;

                IRoom overlapRoom = reservation.getRoom();
                String overlapRoomNumber = overlapRoom.getRoomNumber();
                nonOverlapRooms.remove(overlapRoomNumber);
            }
        }
        if (overlapFlag==0){
            return rooms.values();
        }
        else{
            return nonOverlapRooms.values();
        }
    }
    public Collection<Reservation> getCustomerReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }
    public void printAllReservation(){
        Collection<Reservation> allReservations=retrieveReservations();
        int noOfReservation = 0;
        if (allReservations.size() != 0) {
            for (Reservation reservation : allReservations) {
                noOfReservation++;
                System.out.println("(" + noOfReservation + ") ");
                System.out.println(reservation.getCustomer().getFirstName() + " " + reservation.getCustomer().getLastName());
                System.out.println("Room: " + reservation.getRoom().getRoomNumber() + " " + reservation.getRoom().getRoomType());
                System.out.println("Price Per Night: " + reservation.getRoom().getRoomPrice());
                System.out.println("Checkin Date :" + reservation.getCheckInDate());
                System.out.println("CheckOut Date :" + reservation.getCheckOutDate());
            }
        }
        else{
            System.out.println("No reservations found in system!");
        }
    }
    public Collection<Reservation> retrieveReservations(){
        Collection<Reservation> collectReservations = new LinkedList<Reservation>();
        for(Collection<Reservation> reservations : reservations.values()) {
            collectReservations.addAll(reservations);
        }
        return collectReservations;
    }

    public Collection<IRoom> findRecommendedRooms(Date checkInDate, Date checkOutDate) {
        return findRooms(addDaysToDate(checkInDate), addDaysToDate(checkOutDate));
    }

}
