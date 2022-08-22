import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.*;

/**
 * Admin menu for hotel staff with options
 * display all customers
 * display all rooms
 * display all reservations
 * adding a new room
 * go back to main menu
 *
 * @author Nisha Pathara
 */
public class AdminMenu{
    private static final AdminResource adminResource = AdminResource.getInstanceOfAdminResource();
    private static String optionRegex = "^[1-6]$";
    private static String y_n_Regex = "^[YNyn]{1}$";
    private static String roomNumberRegex= "^[0-9]{3}$";
    private static String roomPriceRegex="[0-9]+[.][0-9]+";
    private static String roomTypeRegex="1|2";

    public static void AdminMenu() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int option;
        boolean isOptionValid = true;
        showMenuOptions();
        try {
            do {
                System.out.println("Please select your option from the menu");

                String optionEntered = scanner.nextLine();
                option = Integer.valueOf(optionEntered);
                isOptionValid = optionEntered.matches(optionRegex);

                if (isOptionValid == true){
                  switch (option) {
                    case 1:
                        displayAllCustomers();
                        break;
                    case 2:
                        displayAllRooms();
                        break;
                    case 3:
                        displayAllReservations();
                        break;
                    case 4:
                        addRoom();
                        break;
                    case 5:
                        MainMenu.showMenuOptions();
                        break;
                    case 6:
                        addTestDate();
                        break;
                    default:
                        System.out.println("Unknown action\n");
                        break;
                  }
                }else {
                    System.out.println("Error: Invalid input,Please Choose an option from the given menu\n");
                }

            } while (option !=5 || (isOptionValid == false));

        } catch (StringIndexOutOfBoundsException ex) {
            throw new Exception("Couldn't process the request,Exiting the application..");
        }

    }
    private static void displayAllCustomers(){
        int count=0;
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else
        {
            for (Customer customer : customers){
                count++;
                System.out.println("("+ count +")");
                System.out.println("First Name: " + customer.getFirstName() +"\n"+
                               " Last Name: " + customer.getLastName() +"\n"+
                               " Email : " + customer.getEmail()+"\n");
            }
        }
        showMenuOptions();
    }
    private static void displayAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        int count=0;

        if(rooms.isEmpty()) {
            System.out.println("No rooms to display,Please add Rooms.");
        } else
        {
            for (IRoom room : rooms) {
                count++;
                System.out.println("("+count+")");
                System.out.println("Room Number: " + room.getRoomNumber() + "\nRoom Type: " + room.getRoomType() + "\nPrice Per Night: $" + room.getRoomPrice());
            }
        }
        showMenuOptions();
    }

    private static void displayAllReservations() {
        adminResource.displayAllReservations();
        showMenuOptions();
    }
    private static void addRoom() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String roomNumber = enterRoomNumber() ;
        double roomPrice = enterRoomPrice();
        RoomType roomType = enterRoomType();
        Room room = new Room(roomNumber, roomPrice, roomType);
        adminResource.addRoom(Collections.singletonList(room));
        System.out.println("Room added successfully!");
        System.out.println("Would you like to add another room? Y/N");
        addAnotherRoom();

    }
    public static void addAnotherRoom() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String anotherRoom = scanner.nextLine();
        try {
            if (anotherRoom.matches(y_n_Regex)) {

                if (anotherRoom.equalsIgnoreCase("y")) {
                    addRoom();
                } else if (anotherRoom.equalsIgnoreCase("n")) {
                    showMenuOptions();
                }
            } else {
                throw new Exception();
            }
        }catch (Exception E){
            System.out.println(E + "Invalidinput");
            showMenuOptions();
        }

    }

    private static String enterRoomNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter room number(in numeric format 111)");
        String roomNum = scanner.nextLine();
        try {
            if(!roomNum.matches(roomNumberRegex)){
               throw new Exception();
            }
            else{
                return roomNum;
            }
        }catch (Exception E){
            System.out.println(E + "Invalid Room number");
            return enterRoomNumber();
        }

    }
    private static double enterRoomPrice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter room price(in format 0.0)");
        String roomPri =scanner.nextLine();
        try {
            if(!roomPri.matches(roomPriceRegex)){
                throw new Exception();
            }
            else{
                return Double.parseDouble(roomPri);
            }
        }catch (Exception E){
            System.out.println(E + "Invalid Room Price");
            return enterRoomPrice();
        }

    }

    private static RoomType enterRoomType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter room type, enter 1 for single bed,2 for double bed");
        String roomTy = scanner.nextLine();
        try {
            if(!roomTy.matches(roomTypeRegex)){
                throw new Exception();
            }
            else{
                RoomType type = roomTy.equals("1") ? RoomType.SINGLE : RoomType.DOUBLE;
                return type;
            }
        }catch (Exception E){
            System.out.println(E + "Invalid Room Type");
            return enterRoomType();
        }
    }
    private static void showMenuOptions(){
        System.out.println("\n\n-------ADMIN-------");
        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Back to main menu");
        System.out.println("6. Add testData(add customers and rooms)");
    }
    private static void addTestDate(){
         HotelResource hotelResource = HotelResource.getInstanceOfHotelResource();
         List<IRoom> roomList = new ArrayList<>();
         hotelResource.createCustomer("test1@gmail.com","fname1","lname1");
         hotelResource.createCustomer("test2@gmail.com","fname2","lname2");
         hotelResource.createCustomer("test3@gmail.com","fname3","lname3");
         hotelResource.createCustomer("test4@gmail.com","fname4","lname4");

         Room room1 = new Room("100",100.0, RoomType.SINGLE);
         Room room2 = new Room("200",200.0, RoomType.DOUBLE);
         Room room3 = new Room("300",300.0, RoomType.SINGLE);
         Room room4 = new Room("400",400.0, RoomType.DOUBLE);
         roomList.add(room1);
         roomList.add(room2);
         roomList.add(room3);
         roomList.add(room4);
         adminResource.addRoom(roomList);
         System.out.println("\nTestData created successfully(Added customers and rooms)");
         showMenuOptions();
    }

}
