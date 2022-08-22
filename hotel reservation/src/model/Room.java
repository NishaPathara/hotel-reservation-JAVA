package model;
import model.RoomType;

import java.util.Objects;

/**
 * Room class implements IRoom interface methods
 *
 * @author Nisha Pathara
 */
public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    public Room(String roomNumber,Double price,RoomType enumeration){
        this.roomNumber=roomNumber;
        this.price=price;
        this.enumeration=enumeration;
    }

    public String getRoomNumber(){
        return roomNumber;
    }

    public Double getRoomPrice(){
        return price;
    }

    public RoomType getRoomType(){
        return enumeration;
    }

    public boolean isFree(){
        if (price == 0.0){
            return true;
        }
        else
            return false;
    }

    @Override
    public String toString(){
        return "Room Number: " + roomNumber + "\nRoom Type: " + enumeration + "\nPrice Per Night: $" + price;
    }
    @Override
    public boolean equals(Object obj) {

        if (obj == this) return true;
        if (!(obj instanceof Room)) {
            return false;
        }
        Room room = (Room) obj;
        return Objects.equals(roomNumber, room.roomNumber) &&
                Objects.equals(price, room.price) &&
                enumeration==room.enumeration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, price, enumeration);
    }
}
