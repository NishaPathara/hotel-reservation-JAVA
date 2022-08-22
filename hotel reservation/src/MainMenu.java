import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;


/**
 * Main menu for the application with menu options for
 * find and reserve room
 * see my reservation
 * create an account
 * Admin
 * Exit
 *
 * @author  Nisha Pathara
 */
public class MainMenu {

    private static final HotelResource hotelResource = HotelResource.getInstanceOfHotelResource();
    private static String optionRegex = "^[1-5]$";
    private static String y_n_Regex = "^[YNyn]{1}$";

    public static void main(String[] args) throws Exception {
        mainMenu();
    }
    public static void mainMenu() throws Exception{
        Scanner scanner = new Scanner(System.in);

        int option;
        boolean isOptionValid=true;
        showMenuOptions();
        try{
            do{
                System.out.println("Please select your option from the menu");

                String optionEntered=scanner.nextLine();
                option =  Integer.valueOf(optionEntered);
                isOptionValid = optionEntered.matches(optionRegex);

                if (isOptionValid == true){
                    switch (option) {
                     case 1:
                        findAndReserveRoom();
                        break;
                     case 2:
                        seeMyReservation();
                        break;
                     case 3:
                        createAccount();
                        break;
                     case 4:
                        AdminMenu.AdminMenu();
                        break;
                     case 5:
                        System.out.println("Exit");
                        break;
                     default:
                        System.out.println("Unknown action\n");
                        break;
                    }
                }else {
                    System.out.println("Error: Invalid input,Please Choose an option from the given menu\n");
                }

            }while(option !=5 || (isOptionValid == false) );

        }catch (StringIndexOutOfBoundsException ex) {
            throw new Exception("Couldn't process the request,Exiting the application..");
        }
    }
    public static void showMenuOptions(){
        System.out.println("\n\n-------WE WELCOME YOU TO THE HOTEL-------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
    }
    private static void findAndReserveRoom() {
        boolean isValid_y_n;
        Scanner scanner;

        System.out.println("Enter CheckIn Date in mm/dd/yyyy format");
        scanner = new Scanner(System.in);
        String strCheckInDate = scanner.nextLine();
        Date checkInDate = isValidDate(strCheckInDate);

        System.out.println("Enter CheckOut Date in mm/dd/yyyy format");
        scanner = new Scanner(System.in);
        String strCheckOutDate = scanner.nextLine();
        Date checkOutDate = isValidDate(strCheckOutDate);
        Collection<IRoom> requestedRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        if (requestedRooms.size() == 0){
            Collection<IRoom> recommendedRooms = hotelResource.findAlternativeRooms(checkInDate, checkOutDate);
            if (recommendedRooms.size() == 0){
                System.out.println("Sorry No Rooms Found,Try with new Dates");
                showMenuOptions();
            }
            else{
                    Date alternativeCheckIn = addDaysToDate(checkInDate);
                    Date alternativeCheckOut = addDaysToDate(checkOutDate);
                    System.out.println("\nSorry rooms not available for selected dates,But Rooms available for " + " Check-In Date:" + alternativeCheckIn + "   Check-Out Date:" + alternativeCheckOut);
                    showRooms(recommendedRooms);
                    do {
                        System.out.println("\nDo you want to book room for this date(Enter y OR n)?");
                        scanner = new Scanner(System.in);
                        String bookAlternative = scanner.nextLine();
                        isValid_y_n = bookAlternative.matches(y_n_Regex);
                        if (isValid_y_n) {
                            if (bookAlternative.equalsIgnoreCase("y")) {
                                reserveRoom(alternativeCheckIn, alternativeCheckOut, recommendedRooms);
                            } else {
                                System.out.println("Sorry for inconvenience caused!Try booking with different dates");
                                showMenuOptions();
                            }
                        }
                        else{
                            System.out.println("Invalid input");
                        }

                    }while(!isValid_y_n);
            }

        }
        else{
            showRooms(requestedRooms);
            System.out.println("Do you want to book room for this date(Enter y OR n)?");
            String bookRoom = scanner.nextLine();
            isValid_y_n = bookRoom.matches(y_n_Regex);
            if (isValid_y_n) {
                if (bookRoom.equalsIgnoreCase("y")) {
                    reserveRoom(checkInDate, checkOutDate, requestedRooms);
                }
                else{
                    showMenuOptions();
                }
            }
            else {
                System.out.println("Invalid input");
                showMenuOptions();
            }

        }

    }
    private static void reserveRoom(Date checkInDate, Date checkOutDate, Collection<IRoom> rooms){
          boolean haveAccountValid;
          String roomAvailable="false";
          System.out.println("Do you have an account with us(Enter y OR n)?");
          Scanner scanner = new Scanner(System.in);
          String haveAccount = scanner.nextLine();
          haveAccountValid = haveAccount.matches(y_n_Regex);
          if (haveAccount.equalsIgnoreCase("y")) {
              System.out.println("Enter the registered email id(in format name@domain.com");
              scanner = new Scanner(System.in);
              String customerEmail = scanner.nextLine();
              if (hotelResource.getCustomer(customerEmail) == null) {
                  System.out.println("Account not found in our system,Please create a new account");
                  showMenuOptions();
              }
              else{
                  System.out.println("Please enter the room number you want to reserve");
                  scanner = new Scanner(System.in);
                  String roomNumber = scanner.nextLine();
                  for (IRoom eachRoom : rooms) {
                      if (eachRoom.getRoomNumber().equals(roomNumber)){
                          roomAvailable="true";
                          IRoom room = hotelResource.getRoom(roomNumber);
                          final Reservation reservation = hotelResource.bookARoom(customerEmail, room, checkInDate, checkOutDate);
                          System.out.println("Your reservation is successfull!");
                          System.out.println("Your Reservation: ");
                          System.out.println(hotelResource.getCustomer(customerEmail).getFirstName() + " " + hotelResource.getCustomer(customerEmail).getLastName());
                          System.out.println("Room: " + room.getRoomNumber() + " " + "RoomType: " +  room.getRoomType());
                          System.out.println("Price: $" + room.getRoomPrice());
                          System.out.println("Checkin Date :" + checkInDate);
                          System.out.println("CheckOut Date :" + checkOutDate);
                          break;
                      }

                  }
                  if(roomAvailable=="false"){
                      System.out.println("Sorry Room not available");
                  }
                  showMenuOptions();
              }
          }
          else {
              System.out.println("Please create an account and try booking again.");
              showMenuOptions();
          }
    }
    public static Date addDaysToDate(Date date){
        int daysToAdd = 7;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,daysToAdd);
        return c.getTime();
    }
    public static Date isValidDate(String strDate){

        SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy");
        sdfrmt.setLenient(false);
        try
        {
            Date formattedDate = sdfrmt.parse(strDate);
            return formattedDate;
        }
        catch (ParseException e)
        {
            System.out.println(strDate+" is Invalid Date format");
            findAndReserveRoom();
        }
        return null;
    }
    private static void seeMyReservation() {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your Email(in format name@domain.com) ");
        final String customerEmail = scanner.nextLine();
        int count=0;
        if (hotelResource.getCustomerReservations(customerEmail)  == null || hotelResource.getCustomerReservations(customerEmail).isEmpty()){
            System.out.println ("No reservation found for mail id "+ customerEmail + "\n");
        }
        else {
            for (Reservation reservation : hotelResource.getCustomerReservations(customerEmail)) {
                count++;
                System.out.println("\nReservation(" + count + ")");
                System.out.println("---------------");
                System.out.println(reservation.getCustomer().getFirstName() + " " + reservation.getCustomer().getLastName());
                System.out.println("Room: " + reservation.getRoom().getRoomNumber() + " " + reservation.getRoom().getRoomType());
                System.out.println("Price: " + reservation.getRoom().getRoomPrice());
                System.out.println("Checkin Date :" + reservation.getCheckInDate());
                System.out.println("CheckOut Date :" + reservation.getCheckOutDate());
            }
        }
        showMenuOptions();

    }

    private static void createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Email (in format name@domain.com) ");
        final String email = scanner.nextLine();
        System.out.println("Enter First Name:");
        final String firstName = scanner.nextLine();
        System.out.println("Enter Last Name:");
        final String lastName = scanner.nextLine();
        System.out.println("after entering last name");
        hotelResource.createCustomer(email, firstName, lastName);
        System.out.println("Your Account has been successfully created!");
        showMenuOptions();


    }
    private static void showRooms(final Collection<IRoom> rooms) {
        System.out.println("inside showroom");
        int count=0;
        for (IRoom room : rooms) {
            count++;
            System.out.println("Available rooms are:-");
            System.out.println("(" + count + ")");
            System.out.println("Room Number: " + room.getRoomNumber() + "\nRoom Type: " + room.getRoomType() + "\nPrice Per Night: $" + room.getRoomPrice());
        }
    }
}
